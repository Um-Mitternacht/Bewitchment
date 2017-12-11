package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
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
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class MycologicalCorruptionBrew extends BlockHitBrew implements IBrew {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	public MycologicalCorruptionBrew() {
		stateMap.put(Blocks.GRASS, Blocks.MYCELIUM.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.MYCELIUM.getDefaultState());
		stateMap.put(Blocks.TALLGRASS, Blocks.RED_MUSHROOM.getDefaultState());
		stateMap.put(Blocks.DEADBUSH, Blocks.BROWN_MUSHROOM.getDefaultState());
		stateMap.put(Blocks.SAND, Blocks.DIRT.getDefaultState());
	}

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity instanceof EntityCow & !(entity instanceof EntityMooshroom)) {
			EntityMooshroom mooshroom = new EntityMooshroom(world);
			mooshroom.setPosition(pos.getX(), pos.getY(), pos.getZ());
			entity.setDead();
			world.spawnEntity(mooshroom);
		}
	}

	@Override
	public int getColor() {
		return 0xFF80DC;
	}

	@Override
	public String getName() {
		return "mycological_corruption";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) ((float) amplifier / 2F);

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
