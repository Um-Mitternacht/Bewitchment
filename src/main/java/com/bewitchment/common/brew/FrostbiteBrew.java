package com.bewitchment.common.brew;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class FrostbiteBrew extends BlockHitBrew {

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
						world.setBlockState(pos1, Blocks.PACKED_ICE.getDefaultState());
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
		return 0xB0E0E6;
	}

	@Override
	public String getName() {
		return "frostbite";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && state.getBlock() == Blocks.WATER && world.isAirBlock(spot.up())) {
				world.setBlockState(spot, Blocks.ICE.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.ICE) {
				world.setBlockState(spot, Blocks.PACKED_ICE.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.SNOW_LAYER) {
				world.setBlockState(spot, Blocks.SNOW.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.SNOW) {
				world.setBlockState(spot, Blocks.PACKED_ICE.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.FROSTED_ICE) {
				world.setBlockState(spot, Blocks.ICE.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.LAVA) {
				world.setBlockState(spot, Blocks.OBSIDIAN.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.FLOWING_LAVA) {
				world.setBlockState(spot, Blocks.OBSIDIAN.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.FLOWING_WATER) {
				world.setBlockState(spot, Blocks.ICE.getDefaultState(), 3);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 3);
	}
}