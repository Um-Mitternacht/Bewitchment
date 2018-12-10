package com.bewitchment.common.block.tiles;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockModTileEntity;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.block.BlockHorizontal.FACING;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockCauldron extends BlockModTileEntity {

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 15 * 0.0625, 11 * 0.0625, 15 * 0.0625);
	private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
	private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 11 * 0.0625, 0.125D);
	private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 11 * 0.0625, 1.0D);
	private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 11 * 0.0625, 1.0D);
	private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 11 * 0.0625, 1.0D);

	public BlockCauldron() {
		super(LibBlockName.CAULDRON, Material.IRON);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(Bewitchment.HALF, BlockStairs.EnumHalf.BOTTOM));
		setSoundType(SoundType.METAL);
		setResistance(5F);
		setHardness(5F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = getDefaultState().withProperty(Bewitchment.HALF, (meta & 4) > 0 ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM);
		iblockstate = iblockstate.withProperty(FACING, EnumFacing.byIndex(5 - (meta & 3)));
		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(Bewitchment.HALF) == BlockStairs.EnumHalf.TOP) {
			i |= 4;
		}

		i = i | 5 - state.getValue(FACING).getIndex();
		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, Bewitchment.HALF);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState iblockstate = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		iblockstate = iblockstate.withProperty(FACING, placer.getHorizontalFacing());
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5F) ?
				iblockstate.withProperty(Bewitchment.HALF, BlockStairs.EnumHalf.BOTTOM) :
				iblockstate.withProperty(Bewitchment.HALF, BlockStairs.EnumHalf.TOP);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCauldron();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
		TileEntityCauldron tile = (TileEntityCauldron) world.getTileEntity(pos);
		if (tile != null) {
			tile.handleParticles();
		}
	}

}