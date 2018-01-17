package com.bewitchment.common.block.misc;

import com.bewitchment.common.block.BlockMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockGoblet extends BlockMod {

	public static final PropertyBool FULL = PropertyBool.create("filled");

	private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.375, 0.625);

	public BlockGoblet(String id) {
		super(id, Material.IRON);
		this.setDefaultState(blockState.getBaseState().withProperty(FULL, false));
		this.setHarvestLevel("pickaxe", 0);
		this.setLightOpacity(0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return bounding_box;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return state.getValue(FULL);
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (blockState.getValue(FULL)) return 8;
		return 0;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FULL);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FULL, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FULL) ? 1 : 0;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
}
