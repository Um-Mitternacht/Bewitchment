package com.bewitchment.common.brew;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.bewitchment.common.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 12/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class HellsWrothBrew extends BlockHitBrew {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	public HellsWrothBrew() {
		stateMap.put(Blocks.SNOW, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.PACKED_ICE, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.FROSTED_ICE, Blocks.MAGMA.getDefaultState());
		stateMap.put(Blocks.ICE, Blocks.MAGMA.getDefaultState());
		stateMap.put(Blocks.SAND, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.STONE, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.GRASS, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.MYCELIUM, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.MAGMA, Blocks.OBSIDIAN.getDefaultState());
		stateMap.put(Blocks.WATER, Blocks.COBBLESTONE.getDefaultState());
		stateMap.put(Blocks.FLOWING_WATER, Blocks.COBBLESTONE.getDefaultState());
		stateMap.put(Blocks.FARMLAND, Blocks.SOUL_SAND.getDefaultState());
	}

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO-OP
	}

	@Override
	public void onFinish(World world, BlockPos pos, EntityLivingBase entity, int amplifier) {
		int box = 1 + amplifier;

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);
		BlockPos.getAllInBox(posI, posF).forEach(
				pos1 -> {
					if (world.getBlockState(pos1).getBlock() == Blocks.AIR)
						world.setBlockState(pos1, Blocks.MAGMA.getDefaultState());
				}
		);
	}

	@Override
	public boolean isBad() {
		return true;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0xE0115F;
	}

	@Override
	public String getName() {
		return "hells_wroth";
	}

	@Override
	void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			} else if (state.getBlock() == Blocks.LOG) {
				world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.LOG2) {
				world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
			}
		}
	}
}
