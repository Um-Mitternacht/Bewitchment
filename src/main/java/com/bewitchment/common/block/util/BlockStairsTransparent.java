package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"NullableProblems"})
public class BlockStairsTransparent extends BlockStairs {
	public BlockStairsTransparent(String name, Block base, String... oreDictionaryNames) {
		super(base.getDefaultState());
		Util.registerBlock(this, name, base, oreDictionaryNames);
		this.setLightOpacity(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return Util.isTransparent(world.getBlockState(pos.offset(face))) && super.doesSideBlockRendering(state, world, pos, face);
	}
}