package com.bewitchment.common.block.magic;


import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibBlockName;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class was created by BerciTheBeast on 27.3.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess", "deprecation"})
public class BlockSaltBarrier extends BlockMod {

	public static final PropertyEnum<BlockSaltBarrier.EnumAttachPosition> NORTH = PropertyEnum.create("north", BlockSaltBarrier.EnumAttachPosition.class);
	public static final PropertyEnum<BlockSaltBarrier.EnumAttachPosition> EAST = PropertyEnum.create("east", BlockSaltBarrier.EnumAttachPosition.class);
	public static final PropertyEnum<BlockSaltBarrier.EnumAttachPosition> SOUTH = PropertyEnum.create("south", BlockSaltBarrier.EnumAttachPosition.class);
	public static final PropertyEnum<BlockSaltBarrier.EnumAttachPosition> WEST = PropertyEnum.create("west", BlockSaltBarrier.EnumAttachPosition.class);
	private static final AxisAlignedBB[] SALT_BARRIER_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D)};
	private final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();

	public BlockSaltBarrier() {
		super(LibBlockName.SALT_BARRIER, Material.CIRCUITS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockSaltBarrier.EnumAttachPosition.NONE).withProperty(EAST, BlockSaltBarrier.EnumAttachPosition.NONE).withProperty(SOUTH, BlockSaltBarrier.EnumAttachPosition.NONE).withProperty(WEST, BlockSaltBarrier.EnumAttachPosition.NONE));
		setSound(SoundType.CLOTH);
		setCreativeTab(null);
	}

	private static boolean canConnectTo(IBlockState blockState) {
		final Block block = blockState.getBlock();
		return block == ModBlocks.salt_barrier;
	}

	private static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
		return canConnectTo(worldIn.getBlockState(pos));
	}

	private static int getAABBIndex(IBlockState state) {
		int i = 0;
		final boolean flag = state.getValue(NORTH) != BlockSaltBarrier.EnumAttachPosition.NONE;
		final boolean flag1 = state.getValue(EAST) != BlockSaltBarrier.EnumAttachPosition.NONE;
		final boolean flag2 = state.getValue(SOUTH) != BlockSaltBarrier.EnumAttachPosition.NONE;
		final boolean flag3 = state.getValue(WEST) != BlockSaltBarrier.EnumAttachPosition.NONE;

		if (flag || flag2 && !flag1 && !flag3) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if (flag1 || flag3 && !flag && !flag2) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if (flag2 || flag && !flag1 && !flag3) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if (flag3 || flag1 && !flag && !flag2) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
		return state;
	}

	private BlockSaltBarrier.EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		final BlockPos blockpos = pos.offset(direction);
		final IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));

		if (!canConnectTo(worldIn.getBlockState(blockpos)) && (iblockstate.isNormalCube() || !canConnectUpwardsTo(worldIn, blockpos.down()))) {
			final IBlockState iblockstate1 = worldIn.getBlockState(pos.up());

			if (!iblockstate1.isNormalCube()) {
				final boolean flag = worldIn.getBlockState(blockpos).isSideSolid(worldIn, blockpos, EnumFacing.UP) || worldIn.getBlockState(blockpos).getBlock() == Blocks.GLOWSTONE;

				if (flag && canConnectUpwardsTo(worldIn, blockpos.up())) {
					if (iblockstate.isBlockNormalCube()) {
						return BlockSaltBarrier.EnumAttachPosition.UP;
					}

					return BlockSaltBarrier.EnumAttachPosition.SIDE;
				}
			}

			return BlockSaltBarrier.EnumAttachPosition.NONE;
		}
		return BlockSaltBarrier.EnumAttachPosition.SIDE;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
			case CLOCKWISE_180:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
			case CLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
			default:
				return state;
		}
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
			case LEFT_RIGHT:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
			case FRONT_BACK:
				return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
			default:
				return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SALT_BARRIER_AABB[getAABBIndex(state.getActualState(source, pos))];
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double x = pos.getX() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
		double y = pos.getY() + 0.0625F;
		double z = pos.getZ() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
		final float f = 3 / 15.0F;
		final float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
		worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x, y, z, 0D, f2, 0D);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos toPos) {
		if (!worldIn.isRemote) {
			if (this.canPlaceBlockAt(worldIn, pos)) {
				this.updateSurroundingSalt(worldIn, state);
			} else {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			this.updateSurroundingSalt(worldIn, state);

			for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, true);
			}

			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
				this.notifyBarrierNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
			}

			for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
				final BlockPos blockpos = pos.offset(enumfacing2);

				if (worldIn.getBlockState(blockpos).isNormalCube()) {
					this.notifyBarrierNeighborsOfStateChange(worldIn, blockpos.up());
				} else {
					this.notifyBarrierNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}
		}
	}

	private IBlockState updateSurroundingSalt(World worldIn, IBlockState state) {
		final List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
		this.blocksNeedingUpdate.clear();

		for (BlockPos blockpos : list) {
			worldIn.notifyNeighborsOfStateChange(blockpos, this, true);
		}

		return state;
	}

	private void notifyBarrierNeighborsOfStateChange(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getBlock() == this) {
			worldIn.notifyNeighborsOfStateChange(pos, this, true);

			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, true);
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);

		if (!worldIn.isRemote) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, true);
			}

			this.updateSurroundingSalt(worldIn, state);

			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
				this.notifyBarrierNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
			}

			for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
				final BlockPos blockpos = pos.offset(enumfacing2);

				if (worldIn.getBlockState(blockpos).isNormalCube()) {
					this.notifyBarrierNeighborsOfStateChange(worldIn, blockpos.up());
				} else {
					this.notifyBarrierNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (entityIn instanceof EntityLivingBase && (((EntityLivingBase) entityIn).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)) {
			collidingBoxes.add(new AxisAlignedBB(pos).expand(0, 255, 0));
		}
		if (entityIn instanceof EntityLivingBase && (((EntityLivingBase) entityIn).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)) {
			entityIn.attackEntityFrom(DamageSource.MAGIC, 1);
		}
		if (entityIn instanceof EntityBlaze) {
			collidingBoxes.add(new AxisAlignedBB(pos).expand(0, 255, 0));
		}
		if (entityIn instanceof EntityEnderman) {
			collidingBoxes.add(new AxisAlignedBB(pos).expand(0, 255, 0));
		}
		if (entityIn instanceof EntityGhast) {
			collidingBoxes.add(new AxisAlignedBB(pos).expand(0, 255, 0));
		}
		if (entityIn instanceof EntityVex) {
			collidingBoxes.add(new AxisAlignedBB(pos).expand(0, 255, 0));
		}
	}


	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.salt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isTopSolid() || worldIn.getBlockState(pos.down()).getBlock() == Blocks.GLOWSTONE;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(ModItems.salt);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
	}

	private enum EnumAttachPosition implements IStringSerializable {
		UP("up"),
		SIDE("side"),
		NONE("none");

		private final String name;

		EnumAttachPosition(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.getName();
		}

		@Override
		public String getName() {
			return this.name;
		}
	}
}
