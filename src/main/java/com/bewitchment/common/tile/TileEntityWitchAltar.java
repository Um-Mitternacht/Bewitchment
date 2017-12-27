package com.bewitchment.common.tile;

import java.util.HashMap;
import java.util.Optional;

import javax.annotation.Nullable;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.tools.BlockCandle;
import com.bewitchment.common.block.tools.BlockWitchAltar;
import com.bewitchment.common.block.tools.BlockWitchAltar.AltarMultiblockType;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ITickable;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityWitchAltar extends TileMod implements ITickable {

	private static final int REFRESH_TIME = 200, RADIUS = 18, MAX_SCORE_PER_CATEGORY = 20; // TODO make refresh_time configurable

	int power = 0, maxPower = 0, gain = 1, color = EnumDyeColor.RED.ordinal();
	int refreshTimer = REFRESH_TIME;

	@Nullable
	public static TileEntityWitchAltar getClosest(BlockPos pos, World world) { //Cache the returned value!
		Optional<Tuple<TileEntityWitchAltar, Double>> res = world.loadedTileEntityList.parallelStream()
				.filter(te -> te instanceof TileEntityWitchAltar)
				.map(te -> new Tuple<TileEntityWitchAltar, Double>((TileEntityWitchAltar) te, te.getDistanceSq(pos.getX(), pos.getY(), pos.getZ())))
				.filter(tup -> tup.getSecond() <= 256)
				.min((t1, t2) -> t1.getSecond().compareTo(t2.getSecond()));
		if (res.isPresent()) return res.get().getFirst();
		return null;
	}

	@Override
	protected void readDataNBT(NBTTagCompound tag) {
		power = tag.getInteger("power");
		maxPower = tag.getInteger("maxPower");
		gain = tag.getInteger("gain");
		color = tag.getInteger("color");
	}

	@Override
	protected void writeDataNBT(NBTTagCompound tag) {
		tag.setInteger("power", power);
		tag.setInteger("gain", gain);
		tag.setInteger("maxPower", maxPower);
		tag.setInteger("color", color);
	}

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			refreshTimer++;
			if (refreshTimer >= REFRESH_TIME) {
				refreshTimer = 0;
				refreshNature();
				markDirty();
				world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
			}
		}
		power += gain;
		if (power > maxPower) power = maxPower;
	}

	private void refreshNature() {
		gain = 1;
		maxPower = 0;
		HashMap<Block, Integer> map = new HashMap<Block, Integer>();

		for (int i = -RADIUS; i <= RADIUS; i++) {
			for (int j = -RADIUS; j <= RADIUS; j++) {
				for (int k = -RADIUS; k <= RADIUS; k++) {
					BlockPos checking = getPos().add(i, j, k);
					int score = getPowerValue(checking);
					if (score > 0) {
						Block block = getWorld().getBlockState(checking).getBlock();
						int currentScore = 0;
						if (map.containsKey(block)) currentScore = map.get(block);
						if (currentScore < MAX_SCORE_PER_CATEGORY) map.put(block, currentScore + score);
					}
				}
			}
		}
		map.values().forEach(i -> maxPower += i);
		maxPower += (map.keySet().size() * 80); //Variety is the most important thing
		double multiplier = 1;
		boolean[] typesGain = new boolean[3]; //Types of modifiers. 0=skull, 1=torch/plate, 2=vase
		boolean[] typesMult = new boolean[3]; //Types of modifiers. 0=skull, 1=goblet, 2=plate
		for (int dx = -1; dx <= 1; dx++)
			for (int dz = -1; dz <= 1; dz++) {
				BlockPos ps = getPos().add(dx, 0, dz);
				if (getWorld().getBlockState(ps).getBlock().equals(ModBlocks.witch_altar) && !getWorld().getBlockState(ps).getValue(BlockWitchAltar.ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
					multiplier += getMultiplier(ps.up(), typesMult);
					gain += getGain(ps.up(), typesGain);
				}
			}
		maxPower *= multiplier;
	}

	private int getGain(BlockPos pos, boolean[] types) {
		IBlockState blockState = getWorld().getBlockState(pos);
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
					if (types[2]) return 0;
					types[2] = true;
					return 1;
				}
			}
		} else if (blockState.getBlock().equals(Blocks.DIAMOND_BLOCK)) {
			return 300;
		} else if (blockState.getBlock() instanceof BlockCandle) {
			if (types[1]) return 0;
			types[1] = true;
			return 2;
			// } else if (blockState.getBlock().equals(ModBlocks.ritual_candle)) {
			// if (types[1]) return 0;
			// types[1]=true;
			// return 2;
		}
		return 0;
	}

	private double getMultiplier(BlockPos pos, boolean[] typesMult) {
		IBlockState blockState = getWorld().getBlockState(pos);
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
		} else if (blockState.getBlock().equals(Blocks.DIAMOND_BLOCK)) {
			return 100;
			// } else if (blockState.getBlock().equals(ModBlocks.goblet)) {
			// if (typesMult[1]) return 0;
			// typesMult[1]=true;
			// if (blockState.getValue(BlockGoblet.FULL)) {
			// return 0.6;
			// } else {
			// return 0.3;
			// }
			// } else if (blockState.getBlock().equals(ModBlocks.candle_plate)) {
			// if (typesMult[2]) return 0;
			// typesMult[2]=true;
			// return 0.5;
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
			int[] ids = OreDictionary.getOreIDs(stack);
			for (int id : ids) if (OreDictionary.getOreName(id).equals("logWood")) return 15;
			for (int id : ids) if (OreDictionary.getOreName(id).equals("treeLeaves")) return 10;
		}
		return 0;
	}

	public int getAltarPower() {
		return power;
	}

	public int getGain() {
		return gain;
	}

	public int getMaxPower() {
		return maxPower;
	}

	public int getColor() {
		return color;
	}

	public boolean consumePower(int amount, boolean simulate) {
		if (amount > power) return false;
		if (!simulate) power -= amount;
		return true;
	}

	public void setColor(int newColor) {
		color = newColor;
		markDirty();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return newState.getBlock() != ModBlocks.witch_altar || newState.getValue(BlockWitchAltar.ALTAR_TYPE) != AltarMultiblockType.TILE;
	}

}