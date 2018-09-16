package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.DefaultMPContainer;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.misc.BlockGoblet;
import com.bewitchment.common.block.tools.BlockCandle;
import com.bewitchment.common.block.tools.BlockGemBowl;
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
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashMap;

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

	private void refreshNature() {
		gain = 1;
		int maxPower = map.values().parallelStream().reduce(0, (a, b) -> a + b);
		maxPower += (map.keySet().size() * 80); //Variety is the most important thing
		double multiplier = 1;
		boolean[] typesGain = new boolean[3]; //Types of modifiers. 0=skull, 1=torch/candle, 2=vase/gemBowl
		boolean[] typesMult = new boolean[2]; //Types of modifiers. 0=skull, 1=goblet
		for (int dx = -1; dx <= 1; dx++)
			for (int dz = -1; dz <= 1; dz++) {
				BlockPos ps = getPos().add(dx, 0, dz);
				if (getWorld().getBlockState(ps).getBlock().equals(ModBlocks.witch_altar) && !getWorld().getBlockState(ps).getValue(BlockWitchAltar.ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
					multiplier += getMultiplier(ps.up(), typesMult);
					gain += getGain(ps.up(), typesGain);
				}
			}
		maxPower *= multiplier;
		storage.setMaxAmount(maxPower);
		map.clear();
		markDirty();
	}

	public int getCurrentGain() {
		return gain;
	}

	private int getGain(BlockPos pos, boolean[] types) {
		IBlockState blockState = getWorld().getBlockState(pos);
		if (blockState.getBlock() == Blocks.DIAMOND_BLOCK) {
			return 10;
		}
		if (blockState.getBlock().equals(Blocks.SKULL)) {
			if (types[0]) return 0;
			types[0] = true;
			TileEntitySkull tes = (TileEntitySkull) world.getTileEntity(pos);
			switch (tes.getSkullType()) {
				case 0:
				case 2:
				case 4:
					return 1; //Zombie, Skeleton and creeper
				case 1:
				case 3:
					return 2; //Wither skull and player skull
				case 5:
					return 4; //Ender dragon
				default:
					return 0;
			}
		} else if (blockState.getBlock().equals(Blocks.TORCH)) {
			if (types[1]) return 0;
			types[1] = true;
			return 1;
		} else if (blockState.getBlock().equals(Blocks.FLOWER_POT)) {
			if (blockState.getBlock().hasTileEntity(blockState)) {
				TileEntityFlowerPot tefp = (TileEntityFlowerPot) world.getTileEntity(pos);
				if (!tefp.getFlowerItemStack().isEmpty()) {
					if (types[2]) {
						return 0;
					}
					types[2] = true;
					return 1;
				}
			}
		} else if (blockState.getBlock() instanceof BlockGemBowl) {
			if (types[2]) {
				return 0;
			}
			types[2] = true;
			return ((TileEntityGemBowl) world.getTileEntity(pos)).getGain();
		} else if (blockState.getBlock() instanceof BlockCandle) {
			if (types[1]) return 0;
			types[1] = true;
			return 2;
		}
		return 0;
	}

	private double getMultiplier(BlockPos pos, boolean[] typesMult) {
		IBlockState blockState = getWorld().getBlockState(pos);
		if (blockState.getBlock() == Blocks.DIAMOND_BLOCK) {
			return 10000;
		}
		if (blockState.getBlock().equals(Blocks.SKULL)) {
			if (typesMult[0]) return 0;
			typesMult[0] = true;
			TileEntitySkull tes = (TileEntitySkull) world.getTileEntity(pos);
			switch (tes.getSkullType()) {
				case 0:
					return 0.05; //Skeleton
				case 1:
				case 3:
					return 0.3; //Wither skull and player skull
				case 5:
					return 0.8; //Ender dragon
				default:
					return 0;
			}
		} else if (blockState.getBlock().equals(ModBlocks.goblet)) {
			if (typesMult[1])
				return 0;
			typesMult[1] = true;
			if (blockState.getValue(BlockGoblet.FULL)) {
				return 0.6;
			}
			return 0.3;
		}
		return 0;
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