package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntityDistillery;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.common.handler.GuiHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockDistillery extends ModBlockContainer {
	public static final PropertyBool IN_USE = PropertyBool.create("in_use");

	private static final AxisAlignedBB BOX_X = new AxisAlignedBB(12 / 16d, 0, 0, 4 / 16d, 15 / 16d, 1), BOX_Z = new AxisAlignedBB(0, 0, 12 / 16d, 1, 15 / 16d, 4 / 16d);

	public BlockDistillery() {
		super(Bewitchment.instance, "distillery", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", GuiHandler.ModGui.DISTILLERY.ordinal());
		setDefaultState(blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(IN_USE, false));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDistillery();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta & 7]).withProperty(IN_USE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex() | (state.getValue(IN_USE) ? 1 : 0) << 3;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(BlockHorizontal.FACING).getAxis() == EnumFacing.Axis.X ? BOX_X : BOX_Z;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING, IN_USE);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw));
	}
}