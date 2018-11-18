package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.event.AltarModifierCheckEvent;
import com.bewitchment.api.event.AltarModifierCollectionEvent;
import com.bewitchment.api.event.AltarUpgradeController;
import com.bewitchment.api.event.AltarUpgradeController.EnumUpgradeClass;
import com.bewitchment.api.mp.DefaultMPContainer;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.misc.BlockCandle;
import com.bewitchment.common.block.misc.BlockGoblet;
import com.bewitchment.common.block.misc.BlockLantern;
import com.bewitchment.common.block.tiles.BlockWitchAltar;
import com.bewitchment.common.block.tiles.BlockWitchAltar.AltarMultiblockType;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TileEntityWitchAltar extends ModTileEntity implements ITickable {
	
	/*
	 * SWORDS MODIFIERS ALREADY ADDED:
	 * 
	 * 		Boline: nature scan radius +2
	 * 		Athame: Altars recharge players nearby
	 * 		Silver: Increase the plant variety bonus 
	 * 
	 */

	static final int RADIUS = 18;
	static final int MAX_SCORE_PER_CATEGORY = 20;
	static final int RIGHT_CLICK_RECALCULATION_COOLDOWN = 200;
	ItemStack swordItem = ItemStack.EMPTY;
	double multiplier = 1;
	DefaultMPContainer storage = new DefaultMPContainer(0);
	private AltarScanHelper scanHelper;
	private int gain = 1;
	private int recalcCooldown = 0;
	private EnumDyeColor color = EnumDyeColor.RED;

	public TileEntityWitchAltar() {
		scanHelper = new AltarScanHelper(this);
	}

	@SubscribeEvent
	public static void onUpgradeChecked(AltarModifierCheckEvent evt) {
		Block b = evt.getState().getBlock();
		if (b == Blocks.DIAMOND_BLOCK || b == Blocks.SKULL || (b == ModBlocks.placed_item && ((TileEntityPlacedItem) evt.getWorld().getTileEntity(evt.getPos())).getItem().getItem() == ModItems.pentacle)) {
			evt.getController().use(EnumUpgradeClass.PENTACLES, evt.getPos());
			return;
		}
		if (b == Blocks.FLOWER_POT || b == ModBlocks.goblet || b == ModBlocks.gem_bowl) {
			evt.getController().use(EnumUpgradeClass.CUPS, evt.getPos());
			return;
		}
		if (b == Blocks.TORCH || b instanceof BlockCandle || b instanceof BlockLantern) {
			evt.getController().use(EnumUpgradeClass.WANDS, evt.getPos());
			return;
		}
		if (b == ModBlocks.placed_item) {
			ItemStack is = ((TileEntityPlacedItem) evt.getWorld().getTileEntity(evt.getPos())).getItem();
			if (is.getItem() == ModItems.athame || is.getItem() == ModItems.boline || is.getItem() == ModItems.silver_sword || is.getItem() == ModItems.cold_iron_sword) {
				evt.getController().use(EnumUpgradeClass.SWORDS, evt.getPos());
				return;
			}
		}
	}

	@SubscribeEvent
	public static void setModifiers(AltarModifierCollectionEvent evt) {
		Block b = evt.getState().getBlock();
		if (b == Blocks.DIAMOND_BLOCK) {
			evt.extraGain = 1000;
			evt.multiplier = 200;
			return;
		}
		if (b == ModBlocks.placed_item && ((TileEntityPlacedItem) evt.getWorld().getTileEntity(evt.getPos())).getItem().getItem() == ModItems.pentacle) {
			evt.extraGain = 3;
			evt.multiplier = -0.2;
		}
		if (b == Blocks.SKULL) {
			TileEntitySkull tes = (TileEntitySkull) evt.getWorld().getTileEntity(evt.getPos());
			switch (tes.getSkullType()) {
				case 0:
				case 2:
				case 4: //Zombie, Skeleton and creeper
					evt.extraGain = 1;
					evt.multiplier = 0.05;
					break;
				case 1:
				case 3://Wither skull and player skull
					evt.extraGain = 2;
					evt.multiplier = 0.2;
					break;
				case 5: //Dragon
					evt.extraGain = 2;
					evt.multiplier = 0.4;
					break;
				default:
					break;
			}
			return;
		}
		if (b == Blocks.TORCH) {
			evt.extraGain = 1;
			return;
		}
		if (b == ModBlocks.lantern) {
			evt.extraGain = 2;
		}
		if (b == ModBlocks.revealing_lantern) {
			evt.extraGain = 3;
		}
		if (b instanceof BlockCandle) {
			if (((BlockCandle) b).isLit()) {
				evt.extraGain = 2;
			} else {
				evt.extraGain = 1;
			}
			return;
		}
		if (b == Blocks.FLOWER_POT) {
			if (b.hasTileEntity(evt.getState())) {
				TileEntityFlowerPot tefp = (TileEntityFlowerPot) evt.getWorld().getTileEntity(evt.getPos());
				if (!tefp.getFlowerItemStack().isEmpty()) {
					evt.multiplier = 0.1;
				} else {
					evt.multiplier = 0.05;
				}
				return;
			}
		}
		if (b == ModBlocks.goblet) {
			if (evt.getState().getValue(BlockGoblet.FULL)) {
				evt.multiplier = 0.25;
			} else {
				evt.multiplier = 0.05;
			}
			return;
		}
		if (b == ModBlocks.gem_bowl) {
			if (evt.getWorld().getTileEntity(evt.getPos()) != null) {
				int t = ((TileEntityGemBowl) evt.getWorld().getTileEntity(evt.getPos())).getGemValue();
				evt.multiplier = 0.05 * t;
			}
			return;
		}
		if (b == ModBlocks.placed_item) {
			ItemStack is = ((TileEntityPlacedItem) evt.getWorld().getTileEntity(evt.getPos())).getItem();
			if (evt.getType() == EnumUpgradeClass.SWORDS) {
				evt.getlAltar().setSwordItem(is.copy());
				evt.getlAltar().markDirty();
			}
		}
	}

	@Override
	public void onLoad() {
		if (!world.isRemote) {
			scanHelper.forceFullScan();
		}
	}

	public void scheduleUpgrade() {
		scanHelper.upgradeCheckScheduled = true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			scanHelper.scanNature();
			if (storage.getAmount() < storage.getMaxAmount()) {
				storage.fill(gain);
				markDirty();
			}
			if (recalcCooldown > 0) {
				recalcCooldown--;
			}
			if (swordItem.getItem() == ModItems.athame) {
				world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(5))
						.forEach(player -> {
							IMagicPowerContainer playerMP = player.getCapability(IMagicPowerContainer.CAPABILITY, null);
							int transferValue = Math.min(20, playerMP.getMaxAmount() - playerMP.getAmount());
							if (storage.drain(transferValue)) {
								playerMP.fill(transferValue / 10);
							}
						});
			}
		}
	}

	public AltarUpgradeController getUpgrades() {
		AltarUpgradeController controller = new AltarUpgradeController();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				BlockPos ps = getPos().add(dx, 0, dz);
				if (getWorld().getBlockState(ps).getBlock().equals(ModBlocks.witch_altar) && !getWorld().getBlockState(ps).getValue(BlockWitchAltar.ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
					MinecraftForge.EVENT_BUS.post(new AltarModifierCheckEvent(getWorld(), ps.up(), world.getBlockState(ps.up()), controller));
				}
			}
		}
		return controller;
	}

	protected void refreshUpgrades() {
		gain = 1;
		multiplier = 1;
		swordItem = ItemStack.EMPTY;
		AltarUpgradeController controller = getUpgrades();
		for (int i = 0; i < controller.getModifierPositions().length; i++) {
			BlockPos p = controller.getModifierPositions()[i];
			if (p != null) {
				AltarModifierCollectionEvent collector = new AltarModifierCollectionEvent(world, p, world.getBlockState(p), EnumUpgradeClass.values()[i], this);
				MinecraftForge.EVENT_BUS.post(collector);
				multiplier += collector.multiplier;
				gain += collector.extraGain;
			}
		}
		markDirty();
	}

	public int getCurrentGain() {
		return gain;
	}

	public EnumDyeColor getColor() {
		return color;
	}

	public void setColor(EnumDyeColor newColor) {
		color = newColor;
		markDirty();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return newState.getBlock() != ModBlocks.witch_altar || newState.getValue(BlockWitchAltar.ALTAR_TYPE) != AltarMultiblockType.TILE;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerContainer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerContainer.CAPABILITY) {
			return IMagicPowerContainer.CAPABILITY.cast(storage);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("mp", storage.saveNBTTag());
		tag.setInteger("gain", gain);
		tag.setTag("swordItem", swordItem.writeToNBT(new NBTTagCompound()));
		tag.setDouble("multiplier", multiplier);
		tag.setInteger("recalcCooldown", recalcCooldown);
		writeModSyncDataNBT(tag);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		storage.loadFromNBT(tag.getCompoundTag("mp"));
		gain = tag.getInteger("gain");
		swordItem = new ItemStack(tag.getCompoundTag("swordItem"));
		multiplier = tag.getDouble("multiplier");
		recalcCooldown = tag.getInteger("recalcCooldown");
		readModSyncDataNBT(tag);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setInteger("color", color.ordinal());
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		color = EnumDyeColor.values()[tag.getInteger("color")];
	}

	public void setSwordItem(ItemStack swordItem) {
		this.swordItem = swordItem;
	}

	public void forceFullScan() {
		if (recalcCooldown == 0) {
			scanHelper.forceFullScan();
			recalcCooldown = RIGHT_CLICK_RECALCULATION_COOLDOWN;
		}
	}

}