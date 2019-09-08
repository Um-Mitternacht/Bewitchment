package com.bewitchment.common.block;

import com.bewitchment.common.block.tile.entity.TileEntityPoppetShelf;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class BlockPoppetShelf extends ModBlockContainer {
	private static final AxisAlignedBB BOX_S = new AxisAlignedBB(0, 0, 0, 1, 1, 4 / 16d), BOX_E = new AxisAlignedBB(0, 0, 0, 4 / 16d, 1, 1), BOX_N = new AxisAlignedBB(0, 0, 1, 1, 1, 12 / 16d), BOX_W = new AxisAlignedBB(1, 0, 0, 12 / 16d, 1, 1);

	public BlockPoppetShelf(String type) {
		super(null, "poppet_shelf_" + type, Blocks.PLANKS, -1);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		switch (state.getValue(BlockHorizontal.FACING).getHorizontalIndex()) {
			case 0:
				return BOX_S;
			case 1:
				return BOX_W;
			case 2:
				return BOX_N;
			case 3:
				return BOX_E;
		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPoppetShelf();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING);
	}
}
