package com.bewitchment.common.block.tools;

import com.bewitchment.api.state.StateProperties;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.BlockModTileEntity;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.tile.tiles.TileEntityMagicMirror;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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

import javax.annotation.Nullable;

public class BlockMagicMirror extends BlockModTileEntity {
	private static final AxisAlignedBB BOUNDING_BOX_NORTH = new AxisAlignedBB(0.0f, 0.0f, 0.0f + 13.0f / 16.0f, 1.0f, 1.0f, 1.0f);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f - 13.0f / 16.0f);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f - 13.0f / 16.0f, 1.0f, 1.0f);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = new AxisAlignedBB(0.0f + 13.0f / 16.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);

	public BlockMagicMirror() {
		super(LibBlockName.MAGIC_MIRROR, Material.IRON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(StateProperties.MIRROR_VARIANTS, 0).withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
		this.setLightOpacity(0);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, StateProperties.MIRROR_VARIANTS, BlockHorizontal.FACING);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
			facing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, facing);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) {
			return false;
		}
		return super.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		BlockPos bottomBackPosition = new BlockPos(0, 0, 0);
		if (side == EnumFacing.NORTH) {
			bottomBackPosition = pos.south();
		} else if (side == EnumFacing.WEST) {
			bottomBackPosition = pos.east();
		} else if (side == EnumFacing.EAST) {
			bottomBackPosition = pos.west();
		} else if (side == EnumFacing.SOUTH) {
			bottomBackPosition = pos.north();
		} else if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
			return false;
		}
		IBlockState bottomBack = worldIn.getBlockState(bottomBackPosition);
		IBlockState topBack = worldIn.getBlockState(bottomBackPosition.up());
		if (!bottomBack.isFullBlock() || !topBack.isFullBlock()) {
			return false;
		}
		return super.canPlaceBlockOnSide(worldIn, pos, side);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getBlock().hasTileEntity(state)) {
			TileEntityMagicMirror magicMirror = (TileEntityMagicMirror) worldIn.getTileEntity(pos);
			return state.withProperty(StateProperties.MIRROR_VARIANTS, magicMirror.getShadeType());
		}
		return state;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing facing = state.getValue(BlockHorizontal.FACING);
		if (facing == EnumFacing.NORTH) {
			return BOUNDING_BOX_NORTH;
		} else if (facing == EnumFacing.SOUTH) {
			return BOUNDING_BOX_SOUTH;
		} else if (facing == EnumFacing.EAST) {
			return BOUNDING_BOX_EAST;
		} else if (facing == EnumFacing.WEST) {
			return BOUNDING_BOX_WEST;
		}
		return BOUNDING_BOX_NORTH;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), ModBlocks.magic_mirror_top.getDefaultState().withProperty(BlockHorizontal.FACING, state.getValue(BlockHorizontal.FACING)));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
		super.breakBlock(worldIn, pos, state);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityMagicMirror();
	}

	public static class BlockMagicMirrorTop extends BlockMod {
		public BlockMagicMirrorTop() {
			super(LibBlockName.MAGIC_MIRROR_TOP, Material.IRON);
			this.setDefaultState(this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
			this.setLightOpacity(0);
		}

		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, BlockHorizontal.FACING);
		}

		@Override
		public int getMetaFromState(IBlockState state) {
			return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
		}

		@Override
		@SuppressWarnings("deprecation")
		public IBlockState getStateFromMeta(int meta) {
			return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta));
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isFullBlock(IBlockState state) {
			return false;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isBlockNormalCube(IBlockState state) {
			return false;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isFullCube(IBlockState state) {
			return false;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isOpaqueCube(IBlockState state) {
			return false;
		}

		@Override
		public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
			return false;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getBlockLayer() {
			return BlockRenderLayer.CUTOUT;
		}

		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			return worldIn.getBlockState(pos.down()).getBlock().onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}

		@SuppressWarnings("deprecation")
		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
			EnumFacing facing = state.getValue(BlockHorizontal.FACING);
			if (facing == EnumFacing.NORTH) {
				return BOUNDING_BOX_NORTH;
			} else if (facing == EnumFacing.SOUTH) {
				return BOUNDING_BOX_SOUTH;
			} else if (facing == EnumFacing.EAST) {
				return BOUNDING_BOX_EAST;
			} else if (facing == EnumFacing.WEST) {
				return BOUNDING_BOX_WEST;
			}
			return BOUNDING_BOX_NORTH;
		}

		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
			worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
			EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.magic_mirror));
			worldIn.spawnEntity(item);
			super.breakBlock(worldIn, pos, state);
		}
	}
}
