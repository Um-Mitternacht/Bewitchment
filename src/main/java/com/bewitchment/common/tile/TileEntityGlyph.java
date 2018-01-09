
package com.bewitchment.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bewitchment.api.ritual.IRitualHandler;
import com.bewitchment.api.ritual.Ritual;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityGlyph extends TileMod implements ITickable, IRitualHandler {
	
	private Ritual ritual = null;
	private int cooldown = 0;
	private UUID entityPlayer;
	private NBTTagCompound ritualData = null;
	private TileEntityWitchAltar te = null;
	private ArrayList<Tuple<String, String>> entityList = new ArrayList<Tuple<String, String>>();
	
	@Override
	public void readDataNBT(NBTTagCompound tag) {
		cooldown = tag.getInteger("cooldown");
		if (tag.hasKey("ritual"))
			ritual = Ritual.REGISTRY.getValue(new ResourceLocation(tag.getString("ritual")));
		if (tag.hasKey("player"))
			entityPlayer = UUID.fromString(tag.getString("player"));
		if (tag.hasKey("data"))
			ritualData = tag.getCompoundTag("data");
		if (tag.hasKey("entityList")) {
			entityList = new ArrayList<Tuple<String, String>>();
			NBTTagCompound listTag = tag.getCompoundTag("entityList");
			for (String s : listTag.getKeySet()) {
				String unsplit = listTag.getString(s);
				String[] names = unsplit.split("§");
				if (names.length != 2)
					continue;
				entityList.add(new Tuple<String, String>(names[0], names[1]));
			}
			}
		}
	
	@Override
	public void writeDataNBT(NBTTagCompound tag) {
		tag.setInteger("cooldown", cooldown);
		if (ritual != null)
			tag.setString("ritual", ritual.getRegistryName().toString());
		if (entityPlayer != null)
			tag.setString("player", entityPlayer.toString());
		if (ritualData != null)
			tag.setTag("data", ritualData);
		NBTTagCompound list = new NBTTagCompound();
		for (int i = 0; i < entityList.size(); i++) {
			Tuple<String, String> t = entityList.get(i);
			list.setString("entity" + i, t.getFirst() + "§" + t.getSecond());
		}
		tag.setTag("entityList", list);
	}
	
	@Override
	public void update() {
		if (ritual != null) {
			EntityPlayer player = getWorld().getPlayerEntityByUUID(entityPlayer);
			boolean hasPowerToUpdate = consumePower(ritual.getRunningPower());
			if (ritual.getTime() >= 0 && hasPowerToUpdate)
				cooldown++;
			if (ritual.getTime() <= cooldown && ritual.getTime() >= 0) {
				ritual.onFinish(player, this, getWorld(), getPos(), ritualData);
				if (!getWorld().isRemote)
					for (ItemStack stack : ritual.getOutput()) {
						EntityItem ei = new EntityItem(getWorld(), getPos().getX(), getPos().up().getY(), getPos().getZ(), stack);
						getWorld().spawnEntity(ei);
					}
				entityPlayer = null;
				cooldown = 0;
				ritual = null;
				return;
			}
			if (hasPowerToUpdate) {
				ritual.onUpdate(player, this, getWorld(), getPos(), ritualData, cooldown);
			} else {
				ritual.onLowPower(player, this, world, pos, ritualData, cooldown);
				}
			}
		}

	
	public void startRitual(EntityPlayer player) {
		if (player.getEntityWorld().isRemote)
			return;
		List<EntityItem> itemsOnGround = getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(getPos()).grow(3, 0, 3));
		List<ItemStack> recipe = itemsOnGround.stream().map(i -> i.getItem()).collect(Collectors.toList());
		for (Ritual rit : Ritual.REGISTRY) {
			if (rit.isValidInput(recipe, hasCircles(rit))) {
				if (rit.isValid(player, world, pos, recipe)) {
					if (consumePower(rit.getRequiredStartingPower())) {
						this.ritualData = new NBTTagCompound();
						NBTTagCompound itemsUsed = new NBTTagCompound();
						ritualData.setTag("itemsUsed", itemsUsed);
						itemsOnGround.forEach(ei -> {
							NBTTagCompound item = new NBTTagCompound();
							ei.getItem().writeToNBT(item);
							ritualData.getCompoundTag("itemsUsed").setTag("item" + ritualData.getCompoundTag("itemsUsed").getKeySet().size(), item);
							ei.getItem().setCount(ei.getItem().getCount() - 1);
							if (ei.getItem().getCount() < 1)
								ei.setDead();
						});
						this.ritual = rit;
						this.entityPlayer = player.getPersistentID();
						this.cooldown = 0;
						ritual.onStarted(player, this, getWorld(), getPos(), ritualData);
						player.sendStatusMessage(new TextComponentTranslation("ritual." + rit.getRegistryName().toString().replace(':', '.') + ".name", new Object[0]), true);
						world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
						markDirty();
						return;
					} else {
						player.sendStatusMessage(new TextComponentTranslation("ritual.failure.power", new Object[0]), true);
						return;
					}
					} else {
					player.sendStatusMessage(new TextComponentTranslation("ritual.failure.precondition", new Object[0]), true);
						return;
					}
				
				}
			}
		player.sendStatusMessage(new TextComponentTranslation("ritual.failure.unknown", new Object[0]), true);
		}
	
	
	private boolean hasCircles(Ritual rit) {
		int requiredCircles = rit.getCircles() & 3;
		if (requiredCircles == 3)
			return false;
		GlyphType typeFirst = BlockCircleGlyph.GlyphType.values()[rit.getCircles() >> 2 & 3];
		GlyphType typeSecond = BlockCircleGlyph.GlyphType.values()[rit.getCircles() >> 4 & 3];
		GlyphType typeThird = BlockCircleGlyph.GlyphType.values()[rit.getCircles() >> 6 & 3];
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
	
	private boolean checkFirst(GlyphType typeFirst) {
		for (int[] c : small) {
			BlockPos bp = pos.add(c[0], 0, c[1]);
			IBlockState bs = world.getBlockState(bp);
			if (!bs.getBlock().equals(ModBlocks.glyph) || !bs.getValue(BlockCircleGlyph.TYPE).equals(typeFirst)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkSecond(GlyphType typeSecond) {
		for (int[] c : medium) {
			BlockPos bp = pos.add(c[0], 0, c[1]);
			IBlockState bs = world.getBlockState(bp);
			if (!bs.getBlock().equals(ModBlocks.glyph) || !bs.getValue(BlockCircleGlyph.TYPE).equals(typeSecond)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkThird(GlyphType typeThird) {
		for (int[] c : big) {
			BlockPos bp = pos.add(c[0], 0, c[1]);
			IBlockState bs = world.getBlockState(bp);
			if (!bs.getBlock().equals(ModBlocks.glyph) || !bs.getValue(BlockCircleGlyph.TYPE).equals(typeThird)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void stopRitual(EntityPlayer player) {
		if (ritual != null) {
			ritual.onStopped(player, this, world, pos, ritualData);
			entityPlayer = null;
			cooldown = 0;
			ritual = null;
			ritualData = null;
			markDirty();
		}
	}
	
	public boolean hasRunningRitual() {
		return ritual != null;
	}
	
	@Override
	public boolean consumePower(int power) {
		if (power == 0)
			return true;
		if (te == null || te.isInvalid())
			te = TileEntityWitchAltar.getClosest(pos, world);
		if (te == null)
			return false;
		return te.consumePower(power, false);
	}
	
	@Override
	public void invalidate() {
		stopRitual(null);
		super.invalidate();
	}
	
	// @Override
	// public TileEntityWitchAltar getAltar(boolean rebind) {
	// if ((te == null || te.isInvalid()) && rebind)
	// te = TileEntityWitchAltar.getClosest(pos, world);
	// if (te == null || te.isInvalid())
	// return null;
	// return te;
	// }
	
	// ##################################################################################################################
	
	public static ArrayList<int[]> getSmall() {
		return small;
	}
	
	public static ArrayList<int[]> getMedium() {
		return medium;
		}
	
	public static ArrayList<int[]> getBig() {
		return big;
		}
	
	private static final ArrayList<int[]> small = new ArrayList<int[]>();
	private static final ArrayList<int[]> medium = new ArrayList<int[]>();
	private static final ArrayList<int[]> big = new ArrayList<int[]>();
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
	
	private static int[] a(int... ar) {
		return ar;
	}
	
	}
