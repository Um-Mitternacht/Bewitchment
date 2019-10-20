package com.bewitchment.common.block;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntitySigil;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSigil extends ModBlock implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyInteger VARIATION = PropertyInteger.create("variation", 0, 9);

	private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.01, 1);
	private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0, 0.99, 0, 1, 1, 1);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0, 0.0D, 0.01, 1, 1.0D);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(1 - 0.01, 0, 0.0D, 1.0D, 1, 1.0D);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0, 0.0D, 1.0D, 1, 0.01);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0, 1 - 0.01, 1.0D, 1, 1.0D);

	public BlockSigil() {
		super(Material.CIRCUITS);
		Util.registerBlock(this, "sigil", Blocks.WOOL);
		setLightLevel(12);
		setLightOpacity(0);
		setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
			case EAST:
				return EAST_AABB;
			case WEST:
				return WEST_AABB;
			case SOUTH:
				return SOUTH_AABB;
			case NORTH:
				return NORTH_AABB;
			case UP:
				return UP_AABB;
			case DOWN:
				return DOWN_AABB;
		}
		return UP_AABB;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (state.getValue(FACING).getAxis() == EnumFacing.Axis.Y) {
			if (!canPlaceOnTop(worldIn, pos)) {
				worldIn.setBlockToAir(pos);
			}
		} else {
			EnumFacing enumfacing = state.getValue(FACING);
			if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid()){
				worldIn.setBlockToAir(pos);
			}
		}
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}

	private boolean canPlaceOnTop(IBlockAccess world, BlockPos pos){
		IBlockState state = world.getBlockState(pos.down());
		return state.isTopSolid() || state.getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return super.canPlaceBlockOnSide(worldIn, pos, side) &&  worldIn.getBlockState(pos.offset(side.getOpposite())).getBlockFaceShape(worldIn, pos.offset(side.getOpposite()), side) == BlockFaceShape.SOLID;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
		return PathNodeType.OPEN;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(VARIATION, Math.abs(pos.getX() + pos.getZ() * 2) % 10);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIATION, FACING);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, facing);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntitySigil();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return ((TileEntitySigil) worldIn.getTileEntity(pos)).activate(worldIn, pos, playerIn, hand, facing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + rand.nextGaussian() / 3, pos.getY(), pos.getZ() + rand.nextGaussian() / 3, 0, 0, 0);
	}
}
