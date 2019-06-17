package com.bewitchment.common.block;

import com.bewitchment.common.block.tile.entity.TileEntityCrystalBall;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class BlockCrystalBall extends ModBlockContainer {
	private static final AxisAlignedBB BOX = new AxisAlignedBB(3 / 16d, 0, 3 / 16d, 13 / 16d, 12 / 16d, 13 / 16d);
	
	public BlockCrystalBall() {
		super(null, "crystal_ball", Material.GLASS, SoundType.GLASS, 1, 2, "pickaxe", -1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCrystalBall();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return BOX;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		for (int i = 0; i < 64; i++)
			world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.375 + rand.nextDouble() * 0.225, pos.getY() + 0.25 + rand.nextDouble() * 0.275, pos.getZ() + 0.375 + rand.nextDouble() * 0.225, 1 - rand.nextDouble() * 0.05, 1 - rand.nextDouble() * 0.05, 1 - rand.nextDouble() * 0.05);
	}
}