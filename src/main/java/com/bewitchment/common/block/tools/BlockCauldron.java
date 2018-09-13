package com.bewitchment.common.block.tools;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockModTileEntity;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.core.handler.ModSounds;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import com.bewitchment.common.tile.tiles.TileEntityCauldron.Mode;
import com.bewitchment.common.tile.util.CauldronFluidTank;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
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
		setSound(SoundType.METAL);
		setResistance(5F);
		setHardness(5F);
	}

	@Override
	protected IBlockState defaultState() {
		return super.defaultState()
				.withProperty(FACING, EnumFacing.NORTH)
				.withProperty(Bewitchment.HALF, BlockStairs.EnumHalf.BOTTOM);
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
		iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(5 - (meta & 3)));
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
			CauldronFluidTank tank = (CauldronFluidTank) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			float level = tank.getFluidAmount() / (Fluid.BUCKET_VOLUME * 2F);
			level = pos.getY() + 0.1F + level;
			if (tile.isBoiling()) {
				Fluid fluid = tank.getInnerFluid();
				if (fluid == FluidRegistry.WATER || fluid == Fluids.MUNDANE_OIL || fluid == Fluids.BW_HONEY) {
					for (int i = 0; i < 2; i++) {
						double posX = pos.getX() + 0.2D + world.rand.nextDouble() * 0.6D;
						double posZ = pos.getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
						Bewitchment.proxy.spawnParticle(ParticleF.CAULDRON_BUBBLE, posX, level, posZ, 0, 0, 0, tile.getColorRGB());
					}
					if (rand.nextInt(3) == 0) {
						world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BOIL, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.5F + rand.nextFloat() * 0.8f, true);
					}
				} else if (fluid == FluidRegistry.LAVA) {
					if (rand.nextInt(5) == 0) {
						double posX = pos.getX() + 0.2D + world.rand.nextDouble() * 0.6D;
						double posZ = pos.getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
						world.spawnParticle(EnumParticleTypes.LAVA, posX, level, posZ, 0, 0.1, 0);
					}
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.6F, 0.9F + rand.nextFloat() * 0.15F, false);
				} else {
					for (int i = 0; i < 3; i++) {
						double posX = pos.getX() + 0.2D + world.rand.nextDouble() * 0.6D;
						double posZ = pos.getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, level, posZ, 0, 0, 0);
					}
					if (rand.nextInt(3) == 0) {
						world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BOIL, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 1F + rand.nextFloat() * 0.8f, true);
					}
				}

				if (tile.getMode() == Mode.CLEANING) {
					for (int i = 0; i < 10; i++) {
						double px = pos.getX() + 0.5 + world.rand.nextGaussian() * 0.5;
						double py = pos.getY() + 0.5 + world.rand.nextGaussian();
						double pz = pos.getZ() + 0.5 + world.rand.nextGaussian() * 0.5;
						Bewitchment.proxy.spawnParticle(ParticleF.CAULDRON_BUBBLE, px, py, pz, 0, 0.1, 0, tile.getColorRGB());
					}
				} else if (tile.getMode() == Mode.FAILING) {
					for (int i = 0; i < 10; i++) {
						double px = pos.getX() + 0.4 + world.rand.nextGaussian() * 0.2;
						double pz = pos.getZ() + 0.4 + world.rand.nextGaussian() * 0.2;
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, level+0.1, pz, 0, 0, 0);
					}
				}
			}

			if ((tile.getMode() == Mode.CRAFTING && tile.isLockInputForCrafting()) || (tile.getMode() == Mode.STEW && tile.getProgress() >= tile.getMode().getTime())) {
				final float x = pos.getX() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				final float z = pos.getZ() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				Bewitchment.proxy.spawnParticle(ParticleF.SPARK, x, level, z, 0.0D, 0.1D, 0.0D);
			}
		}
	}

}