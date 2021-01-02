package com.bewitchment.common.block.plants;

import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFrostFlower extends BlockBushSpreading {
	public BlockFrostFlower() {
		super("frostflower");
		setSoundType(SoundType.SNOW);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextBoolean())
			world.spawnParticle(EnumParticleTypes.SNOWBALL, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}

	@Override
	public boolean canSustainBush(IBlockState state) {
		return super.canSustainBush(state) || state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.SNOW || state.getMaterial() == Material.CRAFTED_SNOW;
	}
}
