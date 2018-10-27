package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.state.StateProperties;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.core.helper.BlockStreamHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.SmokeSpawn;
import com.bewitchment.common.core.util.DimensionalPosition;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemLocationStone;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TileEntityGlyph extends ModTileEntity implements ITickable {

	public static final ArrayList<int[]> small = new ArrayList<int[]>();
	public static final ArrayList<int[]> medium = new ArrayList<int[]>();
	public static final ArrayList<int[]> big = new ArrayList<int[]>();
	private static final double DISTANCE_SQUARED_BEFORE_COST_INCREASES = 400d;

	static {
		for (int i = -1; i <= 1; i++) {
			small.add(a(i, 3));
			small.add(a(3, i));
			small.add(a(i, -3));
			small.add(a(-3, i));
		}
		small.add(a(2, 2));
		small.add(a(2, -2));
		small.add(a(-2, 2));
		small.add(a(-2, -2));
		for (int i = -2; i <= 2; i++) {
			medium.add(a(i, 5));
			medium.add(a(5, i));
			medium.add(a(i, -5));
			medium.add(a(-5, i));
		}
		medium.add(a(3, 4));
		medium.add(a(-3, 4));
		medium.add(a(3, -4));
		medium.add(a(-3, -4));
		medium.add(a(4, 3));
		medium.add(a(-4, 3));
		medium.add(a(4, -3));
		medium.add(a(-4, -3));
		for (int i = -3; i <= 3; i++) {
			big.add(a(i, 7));
			big.add(a(7, i));
			big.add(a(i, -7));
			big.add(a(-7, i));
		}
		big.add(a(4, 6));
		big.add(a(6, 4));
		big.add(a(5, 5));
		big.add(a(-4, 6));
		big.add(a(-6, 4));
		big.add(a(-5, 5));
		big.add(a(4, -6));
		big.add(a(6, -4));
		big.add(a(5, -5));
		big.add(a(-4, -6));
		big.add(a(-6, -4));
		big.add(a(-5, -5));
	}

	private AdapterIRitual ritual = null; // If a ritual is active it is stored here
	private int cooldown = 0; // The times that passed since activation
	private BlockPos runningPos = null; // The effective position where to run the ritual, or null if on the spot
	private UUID entityPlayer; // The player that casted it
	private NBTTagCompound ritualData = null; // Extra data for the ritual, includes a list of items used
	private IMagicPowerConsumer altarTracker = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();

	// A list of entities for which some rituals behaves differently, depending on the ritual
	// For instance in Covens there was a ritual that hijacked all tp attempt in an area and
	// redirected them somewhere else. This was used as a blacklist, in order to allow the owner
	// to safely teleport back to their base. The use is left to who codes the specific ritual.
	// The adding of entities is done via a specific ritual (Used to be called Identification rit)
	private ArrayList<Tuple<String, String>> entityList = new ArrayList<Tuple<String, String>>();

	public static ArrayList<int[]> getSmall() {
		return small;
	}

	public static ArrayList<int[]> getMedium() {
		return medium;
	}

	public static ArrayList<int[]> getBig() {
		return big;
	}

	private static int[] a(int... ar) {
		return ar;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand.equals(EnumHand.OFF_HAND)) {
			return false;
		}

		ItemStack held = playerIn.getHeldItem(hand);

		if (held.isEmpty()) {
			if (this.hasRunningRitual()) {
				this.stopRitual(playerIn);
				return true;
			}
			this.startRitual(playerIn, null);
			return true;
		} else if (held.getItem() == ModItems.location_stone && !this.hasRunningRitual()) {
			Optional<DimensionalPosition> odp = ItemLocationStone.getLocationAndDamageStack(held, playerIn);
			if (odp.isPresent() && odp.get().getDim() == worldIn.provider.getDimension()) {
				this.startRitual(playerIn, odp.get().getPosition());
				return true;
			}
			playerIn.sendStatusMessage(new TextComponentTranslation("ritual.failure.wrong_redirection"), true);
			return true;
		}
		return false;
	}

	@Override
	public void update() {
		if (!world.isRemote && ritual != null) {
			EntityPlayer player = getWorld().getPlayerEntityByUUID(entityPlayer);
			double powerDrainMult = 1;
			BlockPos effPos = getPos();
			if (runningPos != null) {
				powerDrainMult = MathHelper.ceil(runningPos.distanceSq(getPos()) / DISTANCE_SQUARED_BEFORE_COST_INCREASES);
				effPos = runningPos;
			}

			boolean hasPowerToUpdate = altarTracker.drainAltarFirst(player, pos, world.provider.getDimension(), (int) (ritual.getRunningPower() * powerDrainMult));
			if (hasPowerToUpdate) {
				cooldown++;
				markDirty();
			}
			if (ritual.getTime() <= cooldown && ritual.getTime() >= 0) {
				ritual.onFinish(player, this, getWorld(), getPos(), ritualData, effPos, 1);
				for (ItemStack stack : ritual.getOutput(AdapterIRitual.getItemsUsedForInput(ritualData), ritualData)) {
					EntityItem ei = new EntityItem(getWorld(), effPos.getX(), effPos.up().getY(), effPos.getZ(), stack);
					getWorld().spawnEntity(ei);
				}
				entityPlayer = null;
				cooldown = 0;
				ritual = null;
				runningPos = null;
				world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
				markDirty();
				return;
			}
			if (hasPowerToUpdate) {
				ritual.onUpdate(player, this, getWorld(), getPos(), ritualData, cooldown, effPos, 1);
			} else {
				if (ritual.onLowPower(player, this, world, pos, ritualData, cooldown, effPos, 1)) {
					stopRitual(player);
				}
			}
		}
	}

	public void startRitual(EntityPlayer player, BlockPos startAt) {
		if (player.getEntityWorld().isRemote) {
			return;
		}

		double powerDrainMult = 1;
		BlockPos effPos = getPos();
		if (startAt != null) {
			powerDrainMult = MathHelper.ceil(startAt.distanceSq(getPos()) / DISTANCE_SQUARED_BEFORE_COST_INCREASES);
			effPos = startAt;
		}

		List<EntityItem> itemsOnGround = getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(getPos()).grow(3, 0, 3));
		List<BlockPos> placedOnGround = BlockStreamHelper.ofPos(getPos().add(3, 0, 3), getPos().add(-3, 0, -3))
				.filter(t -> (world.getTileEntity(t) instanceof TileEntityPlacedItem))
				.collect(Collectors.toList());
		ArrayList<ItemStack> recipe = new ArrayList<>();
		itemsOnGround.stream().map(i -> i.getItem()).forEach(is -> recipe.add(is));
		placedOnGround.stream().map(t -> (TileEntityPlacedItem) world.getTileEntity(t))
				.forEach(te -> recipe.add(te.getItem()));

		for (AdapterIRitual rit : AdapterIRitual.REGISTRY) { // Check every ritual
			if (rit.isValidInput(recipe, hasCircles(rit))) { // Check if circles and items match
				if (rit.isValid(player, world, pos, recipe, effPos, 1)) { // Checks of extra conditions are met

					if (altarTracker.drainAltarFirst(player, pos, world.provider.getDimension(), (int) (rit.getRequiredStartingPower() * powerDrainMult))) { // Check if there is enough starting power (and uses it in case there is)

						this.ritualData = new NBTTagCompound();
						NBTTagList itemsUsed = new NBTTagList();
						itemsOnGround.forEach(ei -> {
							NBTTagCompound item = new NBTTagCompound();
							ei.getItem().writeToNBT(item);
							itemsUsed.appendTag(item);
							NetworkHandler.HANDLER.sendToDimension(new SmokeSpawn(ei.posX, ei.posY, ei.posZ), world.provider.getDimension());
							ei.setDead();
						});
						placedOnGround.forEach(bp -> {
							TileEntityPlacedItem te = (TileEntityPlacedItem) world.getTileEntity(bp);
							NBTTagCompound item = new NBTTagCompound();
							te.pop().writeToNBT(item);
							itemsUsed.appendTag(item);
							NetworkHandler.HANDLER.sendToDimension(new SmokeSpawn(bp.getX() + 0.5d, bp.getY() + 0.1, bp.getZ() + 0.5d), world.provider.getDimension());
						});
						ritualData.setTag("itemsUsed", itemsUsed);

						// Sets the ritual up
						this.runningPos = startAt;
						this.ritual = rit;
						this.entityPlayer = player.getPersistentID();
						this.cooldown = 1;
						ritual.onStarted(player, this, getWorld(), getPos(), ritualData, effPos, 1);
						//TODO get a better sound
						world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.7f, 0.7f);
						player.sendStatusMessage(new TextComponentTranslation("ritual." + rit.getRegistryName().toString().replace(':', '.') + ".name"), true);
						world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
						markDirty();
						return;
					}
					player.sendStatusMessage(new TextComponentTranslation("ritual.failure.power"), true);
					return;
				}
				player.sendStatusMessage(new TextComponentTranslation("ritual.failure.precondition"), true);
				return;

			}
		}
		if (!(itemsOnGround.isEmpty() && placedOnGround.isEmpty())) {
			player.sendStatusMessage(new TextComponentTranslation("ritual.failure.unknown"), true);
		}
	}

	private boolean hasCircles(AdapterIRitual rit) {
		int requiredCircles = rit.getCircles() & 3;
		if (requiredCircles == 3)
			return false;
		EnumGlyphType typeFirst = EnumGlyphType.fromMeta(rit.getCircles() >> 2 & 3);
		EnumGlyphType typeSecond = EnumGlyphType.fromMeta(rit.getCircles() >> 4 & 3);
		EnumGlyphType typeThird = EnumGlyphType.fromMeta(rit.getCircles() >> 6 & 3);
		if (requiredCircles > 1)
			if (!checkThird(typeThird)) {
				return false;
			}
		if (requiredCircles > 0)
			if (!checkSecond(typeSecond)) {
				return false;
			}
		if (!checkFirst(typeFirst)) {
			return false;
		}
		return true;
	}

	public boolean isInList(Entity entity) {
		return entityList.stream().map(t -> t.getFirst()).anyMatch(s -> s.equals(entity.getUniqueID().toString()));
	}

	public List<Tuple<String, String>> getWhitelistEntries() {
		return entityList;
	}

	public void addEntityToList(Entity entity) {
		entityList.add(new Tuple<String, String>(entity.getUniqueID().toString(), entity.getName()));
		markDirty();
	}

	public void addEntityUUIDToList(String uuid, String name) {
		entityList.add(new Tuple<String, String>(uuid, name));
		markDirty();
	}

	private boolean checkFirst(EnumGlyphType typeFirst) {
		EnumGlyphType lastFound = null;
		for (int[] c : small) {
			BlockPos bp = pos.add(c[0], 0, c[1]);
			IBlockState bs = world.getBlockState(bp);
			if (!bs.getBlock().equals(ModBlocks.ritual_glyphs) || bs.getValue(StateProperties.GLYPH_TYPE).equals(EnumGlyphType.GOLDEN) || (!bs.getValue(StateProperties.GLYPH_TYPE).equals(typeFirst) && !typeFirst.equals(EnumGlyphType.ANY))) {
				return false;
			}
			EnumGlyphType thisOne = bs.getValue(StateProperties.GLYPH_TYPE);
			if (lastFound != null && lastFound != thisOne) {
				return false;
			}
			lastFound = thisOne;
		}
		return true;
	}

	private boolean checkSecond(EnumGlyphType typeSecond) {
		EnumGlyphType lastFound = null;
		for (int[] c : medium) {
			BlockPos bp = pos.add(c[0], 0, c[1]);
			IBlockState bs = world.getBlockState(bp);
			if (!bs.getBlock().equals(ModBlocks.ritual_glyphs) || bs.getValue(StateProperties.GLYPH_TYPE).equals(EnumGlyphType.GOLDEN) || (!bs.getValue(StateProperties.GLYPH_TYPE).equals(typeSecond) && !typeSecond.equals(EnumGlyphType.ANY))) {
				return false;
			}
			EnumGlyphType thisOne = bs.getValue(StateProperties.GLYPH_TYPE);
			if (lastFound != null && lastFound != thisOne) {
				return false;
			}
			lastFound = thisOne;
		}
		return true;
	}

	private boolean checkThird(EnumGlyphType typeThird) {
		EnumGlyphType lastFound = null;
		for (int[] c : big) {
			BlockPos bp = pos.add(c[0], 0, c[1]);
			IBlockState bs = world.getBlockState(bp);
			if (!bs.getBlock().equals(ModBlocks.ritual_glyphs) || bs.getValue(StateProperties.GLYPH_TYPE).equals(EnumGlyphType.GOLDEN) || (!bs.getValue(StateProperties.GLYPH_TYPE).equals(typeThird) && !typeThird.equals(EnumGlyphType.ANY))) {
				return false;
			}
			EnumGlyphType thisOne = bs.getValue(StateProperties.GLYPH_TYPE);
			if (lastFound != null && lastFound != thisOne) {
				return false;
			}
			lastFound = thisOne;
		}
		return true;
	}

	public void stopRitual(EntityPlayer player) {
		if (ritual != null) {
			ritual.onStopped(player, this, world, pos, ritualData, runningPos == null ? getPos() : runningPos, 1);
			entityPlayer = null;
			cooldown = 0;
			ritual = null;
			ritualData = null;
			runningPos = null;
			IBlockState glyph = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, glyph, glyph, 3);
			markDirty();
		}
	}

	public boolean hasRunningRitual() {
		return cooldown > 0;
	}

	@Override
	public void invalidate() {
		stopRitual(null);
		super.invalidate();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(altarTracker);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		cooldown = tag.getInteger("cooldown");
		if (tag.hasKey("ritual")) {
			ritual = AdapterIRitual.REGISTRY.getValue(new ResourceLocation(tag.getString("ritual")));
		}
		if (tag.hasKey("player")) {
			entityPlayer = UUID.fromString(tag.getString("player"));
		}
		if (tag.hasKey("runningPos")) {
			NBTTagCompound rp = tag.getCompoundTag("runningPos");
			runningPos = new BlockPos(rp.getInteger("x"), rp.getInteger("y"), rp.getInteger("z"));
		}
		if (tag.hasKey("data")) {
			ritualData = tag.getCompoundTag("data");
		}
		altarTracker.readFromNbt(tag.getCompoundTag("altar"));
		if (tag.hasKey("entityList")) {
			entityList = new ArrayList<Tuple<String, String>>();
			tag.getTagList("entityList", NBT.TAG_STRING).forEach(nbts -> {
				String[] names = ((NBTTagString) nbts).getString().split("!");
				if (names.length == 2)
					entityList.add(new Tuple<String, String>(names[0], names[1]));
			});
		}
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setInteger("cooldown", cooldown);
		if (ritual != null) {
			tag.setString("ritual", ritual.getRegistryName().toString());
		}
		if (entityPlayer != null) {
			tag.setString("player", entityPlayer.toString());
		}
		if (ritualData != null) {
			tag.setTag("data", ritualData);
		}
		if (runningPos != null) {
			NBTTagCompound rp = new NBTTagCompound();
			rp.setInteger("x", runningPos.getX());
			rp.setInteger("y", runningPos.getY());
			rp.setInteger("z", runningPos.getZ());
			tag.setTag("runningPos", rp);
		}
		tag.setTag("altar", altarTracker.writeToNbt());
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < entityList.size(); i++) {
			Tuple<String, String> t = entityList.get(i);
			list.appendTag(new NBTTagString(t.getFirst() + "!" + t.getSecond()));
		}
		tag.setTag("entityList", list);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setInteger("cooldown", cooldown); // cooldown > 0 --> Particles
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		cooldown = tag.getInteger("cooldown");
	}

}
