package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntitySpinningWheel;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.common.handler.GuiHandler;
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

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockSpinningWheel extends ModBlockContainer {
	private static final AxisAlignedBB BOX_X = new AxisAlignedBB(10 / 16d, 0, 0, 6 / 16d, 1, 1), BOX_Z = new AxisAlignedBB(0, 0, 10 / 16d, 1, 1, 6 / 16d);
	
	public BlockSpinningWheel() {
		super(Bewitchment.instance, "spinning_wheel", Blocks.PLANKS, GuiHandler.ModGui.SPINNING_WHEEL.ordinal());
		setDefaultState(blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySpinningWheel();
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(BlockHorizontal.FACING).getAxis() == EnumFacing.Axis.X ? BOX_X : BOX_Z;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw));
	}
}