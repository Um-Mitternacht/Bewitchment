package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.event.AltarModifierCheckEvent;
import com.bewitchment.api.event.AltarModifierCollectionEvent;
import com.bewitchment.api.event.AltarUpgradeController;
import com.bewitchment.api.event.AltarUpgradeController.EnumUpgradeClass;
import com.bewitchment.api.mp.DefaultMPContainer;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.misc.BlockGoblet;
import com.bewitchment.common.block.tools.BlockCandle;
import com.bewitchment.common.block.tools.BlockWitchAltar;
import com.bewitchment.common.block.tools.BlockWitchAltar.AltarMultiblockType;
import com.bewitchment.common.core.handler.ConfigHandler;
import com.bewitchment.common.lib.LibIngredients;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.HashMap;

@Mod.EventBusSubscriber
public class TileEntityWitchAltar extends ModTileEntity implements ITickable {

	private static final int RADIUS = 18, MAX_SCORE_PER_CATEGORY = 20;

	private int gain = 1;

	//Scan variables, no need to save these
	private int dx = -RADIUS, dy = -RADIUS, dz = -RADIUS;
	private HashMap<Block, Integer> map = new HashMap<Block, Integer>();
	private BlockPos.MutableBlockPos checking = new BlockPos.MutableBlockPos(0, 0, 0);
	private boolean complete = false;


	private EnumDyeColor color = EnumDyeColor.RED;
	private DefaultMPContainer storage = new DefaultMPContainer(0);

	public TileEntityWitchAltar() {
	}

	@SubscribeEvent
	public static void onUpgradeChecked(AltarModifierCheckEvent evt) {
		Block b = evt.getState().getBlock();
		if (b == Blocks.DIAMOND_BLOCK || b == Blocks.SKULL) {
			evt.getController().use(EnumUpgradeClass.PENTACLES, evt.getPos());
			return;
		}
		if (b == Blocks.FLOWER_POT || b == ModBlocks.goblet || b == ModBlocks.gem_bowl) {
			evt.getController().use(EnumUpgradeClass.CUPS, evt.getPos());
			return;
		}
		if (b == Blocks.TORCH || b instanceof BlockCandle/* || b == Blocks.LIT_PUMPKIN || b == Blocks.GLOWSTONE || b == Blocks.SEA_LANTERN*/) {
			evt.getController().use(EnumUpgradeClass.WANDS, evt.getPos());
			return;
		}
	}

	@SubscribeEvent
	public static void setModifiers(AltarModifierCollectionEvent evt) {
		Block b = evt.getState().getBlock();
		if (b == Blocks.DIAMOND_BLOCK) {
			evt.extraGain = 100;
			evt.multiplier = 200;
			return;
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
					evt.extraGain = 3;
					evt.multiplier = 0.3;
					break;
				default:
					break;
			}
			return;
		}
		if (b == Blocks.TORCH) {
			evt.extraGain = 1;
			return;
		}/*
		if (b == Blocks.LIT_PUMPKIN) {
			evt.extraGain = 1;
			return;
		}
		if (b == Blocks.GLOWSTONE) {
			evt.extraGain = 2;
			return;
		}
		if (b == Blocks.SEA_LANTERN) {
			evt.extraGain = 2;
			return;
		}*/
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
	}

	@Override
	public void onLoad() {
		if (!world.isRemote) {
			forceFullScan();
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			scanNature();
			if (storage.getAmount() < storage.getMaxAmount()) {
				storage.fill(gain);
				markDirty();
			}
		}
	}

	private void scanNature() {
		for (int i = 0; i < ConfigHandler.altar_scan_blocks_per_tick; i++) {
			getNextCycle();
			performCurrentCycle();
		}
	}

	public void forceFullScan() {
		dx = -RADIUS;
		dy = -RADIUS;
		dz = -RADIUS;
		complete = false;
		while (!complete) {
			getNextCycle();
			performCurrentCycle();
		}
	}

	private void performCurrentCycle() {
		updateScore();
		if (complete) {
			refreshNature();
		}
	}

	private void getNextCycle() {
		complete = false;
		dx++;
		if (dx > RADIUS) {
			dx = -RADIUS;
			dy++;
		}
		if (dy > RADIUS) {
			dy = -RADIUS;
			dz++;
		}
		if (dz > RADIUS) {
			dz = -RADIUS;
			complete = true;
		}
		checking.setPos(getPos().getX() + dx, getPos().getY() + dy, getPos().getZ() + dz);
	}

	private void updateScore() {
		int score = getPowerValue(checking);
		if (score > 0) {
			Block block = getWorld().getBlockState(checking).getBlock();
			int currentScore = 0;
			if (map.containsKey(block)) currentScore = map.get(block);
			if (currentScore < MAX_SCORE_PER_CATEGORY) map.put(block, currentScore + score);
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

	private void refreshNature() {
		gain = 1;
		double multiplier = 1;
		AltarUpgradeController controller = getUpgrades();
		for (BlockPos p : controller.getModifierPositions()) {
			if (p != null) {
				AltarModifierCollectionEvent collector = new AltarModifierCollectionEvent(world, p, world.getBlockState(p));
				MinecraftForge.EVENT_BUS.post(collector);
				multiplier += collector.multiplier;
				gain += collector.extraGain;
			}
		}
		int maxPower = map.values().parallelStream().reduce(0, (a, b) -> a + b);
		int varietyMultiplier = 40;
		//TODO	If controller.getModifierPositions[EnumUpgradeClass.SWORDS]!=null && isAthame... varietyMultiplier = 90

		maxPower += (map.keySet().size() * varietyMultiplier); //Variety is the most important thing
		maxPower *= multiplier;
		storage.setMaxAmount(maxPower);
		map.clear();
		markDirty();
	}

	public int getCurrentGain() {
		return gain;
	}

	private int getPowerValue(BlockPos add) {
		IBlockState blockState = world.getBlockState(add);
		if (blockState.getBlock().equals(Blocks.AIR)) return 0;
		if (blockState.getBlock() instanceof IPlantable) return 30;
		if (blockState.getBlock() instanceof IGrowable) return 30;
		if (blockState.getBlock().equals(Blocks.MELON_BLOCK)) return 30;
		if (blockState.getBlock().equals(Blocks.PUMPKIN)) return 30;
		ItemStack stack = new ItemStack(blockState.getBlock());
		if (!stack.isEmpty()) {
			if (LibIngredients.anyLog.apply(stack)) return 15;
			if (LibIngredients.anyLeaf.apply(stack)) return 8;
		}
		return 0;
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
		writeModSyncDataNBT(tag);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		storage.loadFromNBT(tag.getCompoundTag("mp"));
		gain = tag.getInteger("gain");
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
}