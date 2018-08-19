package com.bewitchment.common.tile;

import com.bewitchment.api.mp.DefaultMPContainer;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.misc.BlockGoblet;
import com.bewitchment.common.block.tools.BlockCandle;
import com.bewitchment.common.block.tools.BlockGemBowl;
import com.bewitchment.common.block.tools.BlockWitchAltar;
import com.bewitchment.common.block.tools.BlockWitchAltar.AltarMultiblockType;
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
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.HashMap;

public class TileEntityWitchAltar extends ModTileEntity implements ITickable {

	private static final int REFRESH_TIME = 200, RADIUS = 18, MAX_SCORE_PER_CATEGORY = 20; // TODO make refresh_time configurable
	int gain = 0, color = EnumDyeColor.RED.ordinal();
	int refreshTimer = REFRESH_TIME;
	private DefaultMPContainer storage = new DefaultMPContainer(0);

	public TileEntityWitchAltar() {
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
	}

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			refreshTimer++;
			if (refreshTimer >= REFRESH_TIME) {
				refreshTimer = 0;
				refreshNature();
				markDirty();
				syncToClient();
			}
			storage.fill(gain);
		}
	}

	private void refreshNature() {
		gain = 1;
		int maxPower;
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
		maxPower = map.values().stream().mapToInt(i -> i).sum();
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
		storage.setMaxAmount(maxPower);
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
					if (types[2]) return 0;
					types[2] = true;
					return 1;
				}
			}
		} else if (blockState.getBlock() instanceof BlockGemBowl) {
			return ((TileEntityGemBowl) world.getTileEntity(pos)).getGain();
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
		} else if (blockState.getBlock() instanceof BlockGemBowl) {
			return ((TileEntityGemBowl) world.getTileEntity(pos)).getMultiplier();
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
			int[] ids = OreDictionary.getOreIDs(stack);
			for (int id : ids) if (OreDictionary.getOreName(id).equals("logWood")) return 15;
			for (int id : ids) if (OreDictionary.getOreName(id).equals("treeLeaves")) return 10;
		}
		return 0;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int newColor) {
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
		tag.setInteger("color", color);
		tag.setTag("mp", storage.saveNBTTag());
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		color = tag.getInteger("color");
		storage.loadFromNBT(tag.getCompoundTag("mp"));
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setInteger("color", color);
		tag.setTag("mp", storage.saveNBTTag());
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		color = tag.getInteger("color");
		storage.loadFromNBT(tag.getCompoundTag("mp"));
	}
}