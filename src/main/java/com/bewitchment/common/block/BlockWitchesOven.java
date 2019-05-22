package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.CommonProxy.ModGui;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesOven;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockWitchesOven extends ModBlockContainer {
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	private static final AxisAlignedBB BOX = new AxisAlignedBB(1 / 16d, 0, 1 / 16d, 15 / 16d, 1, 15 / 16d);
	
	public BlockWitchesOven() {
		super(Bewitchment.instance, "witches_oven", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 0, ModGui.OVEN.ordinal());
		setDefaultState(blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(LIT, false));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWitchesOven();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return BOX;
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(LIT) ? 13 : 0;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta & 7]).withProperty(LIT, (meta & 8) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex() | (state.getValue(LIT) ? 1 : 0) << 3;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING, LIT);
	}
}