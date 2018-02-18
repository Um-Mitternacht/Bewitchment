package com.bewitchment.common.brew;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class GrassGrowBrew extends BlockHitBrew {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	public GrassGrowBrew() {
		stateMap.put(Blocks.MYCELIUM, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.RED_MUSHROOM, Blocks.TALLGRASS.getDefaultState());
		stateMap.put(Blocks.DEADBUSH, Blocks.YELLOW_FLOWER.getDefaultState());
		stateMap.put(Blocks.SAND, Blocks.DIRT.getDefaultState());
	}

	//Todo: Put Animania compat here, Animania replaces vanilla cows and mooshrooms
	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity instanceof EntityMooshroom) {
			EntityCow cow = new EntityCow(world);
			cow.setPosition(pos.getX(), pos.getY(), pos.getZ());
			entity.setDead();
			world.spawnEntity(cow);
		}
	}

	@Override
	public int getColor() {
		return 0x4CBB17;
	}

	@Override
	public String getName() {
		return "growth";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			}
		}
	}
}
