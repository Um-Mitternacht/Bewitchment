package com.bewitchment.common.block.tools;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.core.helper.NBTHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.PlayerMimicDataChanged;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.tile.TileEntityMagicMirror;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlockMagicMirror extends BlockMod implements ITileEntityProvider {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection BOTTOM_FACING = BlockHorizontal.FACING;

	private static final AxisAlignedBB BOUNDING_BOX_NORTH = new AxisAlignedBB(0.0f, 0.0f, 0.0f + 13.0f/16.0f, 1.0f, 1.0f, 1.0f);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f - 13.0f/16.0f);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f - 13.0f/16.0f, 1.0f, 1.0f);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = new AxisAlignedBB(0.0f + 13.0f/16.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);

	public BlockMagicMirror() {
		super(LibBlockName.MAGIC_MIRROR, Material.IRON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false).withProperty(BOTTOM_FACING, EnumFacing.NORTH));
		this.setLightOpacity(0);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE, BOTTOM_FACING);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if(facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
			facing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(ACTIVE, false).withProperty(BOTTOM_FACING, facing);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if(worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) {
			return false;
		} else {
			return super.canPlaceBlockAt(worldIn, pos);
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		BlockPos bottomBackPosition = new BlockPos(0, 0, 0);
		if(side == EnumFacing.NORTH) {
			bottomBackPosition = pos.south();
		} else if(side == EnumFacing.WEST) {
			bottomBackPosition = pos.east();
		} else if(side == EnumFacing.EAST) {
			bottomBackPosition = pos.west();
		} else if(side == EnumFacing.SOUTH) {
			bottomBackPosition = pos.north();
		} else if(side == EnumFacing.UP || side == EnumFacing.DOWN) {
			return false;
		}
		IBlockState bottomBack = worldIn.getBlockState(bottomBackPosition);
		IBlockState topBack = worldIn.getBlockState(bottomBackPosition.up());
		if(!bottomBack.isFullBlock() || !topBack.isFullBlock()) {
			return false;
		} else {
			return super.canPlaceBlockOnSide(worldIn, pos, side);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		final boolean active = state.getValue(ACTIVE);
		return active ? state.getValue(BOTTOM_FACING).getHorizontalIndex() + 6 : state.getValue(BOTTOM_FACING).getHorizontalIndex();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean active = false;
		if(meta >= 6) {
			active = true;
			meta -= 6;
		}
		return this.getDefaultState().withProperty(ACTIVE, active)
				.withProperty(BOTTOM_FACING, EnumFacing.getHorizontal(meta));
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
		EnumFacing facing = state.getValue(BOTTOM_FACING);
		if(facing == EnumFacing.NORTH) {
			return BOUNDING_BOX_NORTH;
		} else if(facing == EnumFacing.SOUTH) {
			return BOUNDING_BOX_SOUTH;
		} else if(facing == EnumFacing.EAST) {
			return BOUNDING_BOX_EAST;
		} else if(facing == EnumFacing.WEST) {
			return BOUNDING_BOX_WEST;
		}
		return BOUNDING_BOX_NORTH;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), ModBlocks.magic_mirror_top.getDefaultState().withProperty(BlockMagicMirrorTop.TOP_FACING, state.getValue(BOTTOM_FACING)));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			ItemStack held = playerIn.getHeldItem(hand);
			if(held.getItem() == ModItems.taglock) {
				final UUID playerID = NBTHelper.getUniqueID(held, Bewitchment.TAGLOCK_ENTITY);
				final String playerName = NBTHelper.getString(held, Bewitchment.TAGLOCK_ENTITY_NAME);
				final IMimicData capability = playerIn.getCapability(CapabilityMimicData.CAPABILITY, null);
				capability.setMimickedPlayerID(playerID);
				capability.setMimickedPlayerName(playerName);
				if(playerIn.getUniqueID().equals(playerID)) {
					capability.setMimicking(false);
				} else {
					capability.setMimicking(true);
				}
				NetworkHandler.HANDLER.sendToAll(new PlayerMimicDataChanged(playerIn));
			}
		}
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityMagicMirror();
	}

	public static class BlockMagicMirrorTop extends BlockMod {
		public static final PropertyDirection TOP_FACING = BlockHorizontal.FACING;

		public BlockMagicMirrorTop() {
			super(LibBlockName.MAGIC_MIRROR_TOP, Material.IRON);
			this.setDefaultState(this.getDefaultState().withProperty(TOP_FACING, EnumFacing.NORTH));
			this.setLightOpacity(0);
		}

		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, TOP_FACING);
		}

		@Override
		public int getMetaFromState(IBlockState state) {
			return state.getValue(BOTTOM_FACING).getHorizontalIndex();
		}

		@Override
		public IBlockState getStateFromMeta(int meta) {
			return this.getDefaultState().withProperty(BOTTOM_FACING, EnumFacing.getHorizontal(meta));
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

		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getBlockLayer()
		{
			return BlockRenderLayer.CUTOUT;
		}

		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			return worldIn.getBlockState(pos.down()).getBlock().onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}

		@SuppressWarnings("deprecation")
		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
			EnumFacing facing = state.getValue(TOP_FACING);
			if(facing == EnumFacing.NORTH) {
				return BOUNDING_BOX_NORTH;
			} else if(facing == EnumFacing.SOUTH) {
				return BOUNDING_BOX_SOUTH;
			} else if(facing == EnumFacing.EAST) {
				return BOUNDING_BOX_EAST;
			} else if(facing == EnumFacing.WEST) {
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
