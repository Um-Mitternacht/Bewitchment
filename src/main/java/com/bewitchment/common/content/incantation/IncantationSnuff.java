package com.bewitchment.common.content.incantation;

import com.bewitchment.api.incantation.IIncantation;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 4/20/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class IncantationSnuff implements IIncantation {

	@Override
	public void cast(EntityPlayer sender, String[] args) {
		World world = sender.getEntityWorld();
		BlockPos source = sender.getPosition();
		for (BlockPos pos : BlockPos.getAllInBox(source.add(7, 3, 7), source.add(-7, -3, -7))) {
			IBlockState state = world.getBlockState(pos);
			boolean flag = false;
			if (state.getBlock() == ModBlocks.candle_medium_lit) {
				world.setBlockState(pos, ModBlocks.candle_medium.getDefaultState().withProperty(Bewitchment.COLOR, state.getValue(Bewitchment.COLOR)), 3);
				flag = true;
			}
			if (state.getBlock() == ModBlocks.candle_small_lit) {
				world.setBlockState(pos, ModBlocks.candle_small.getDefaultState().withProperty(Bewitchment.COLOR, state.getValue(Bewitchment.COLOR)), 3);
				flag = true;
			}
			if (state.getBlock() == ModBlocks.witches_light) {
				world.setBlockToAir(pos);
			}
			if (flag) {
				for (int i = 0; i < 5; i++) {
					double x = pos.getX() + world.rand.nextFloat();
					double y = pos.getY() + world.rand.nextFloat();
					double z = pos.getZ() + world.rand.nextFloat();
					world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
				}
			}
		}
	}

	@Override
	public int getCost() {
		return 100;
	}
}
