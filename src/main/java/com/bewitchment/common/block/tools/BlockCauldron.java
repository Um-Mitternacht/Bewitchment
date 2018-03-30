package com.bewitchment.common.block.tools;

import static net.minecraft.block.BlockHorizontal.FACING;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.client.sound.ModSounds;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.tile.TileCauldron;
import com.bewitchment.common.tile.TileCauldron.Mode;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockCauldron extends BlockMod implements ITileEntityProvider {

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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking() && playerIn.getHeldItem(hand).isEmpty()) {
			worldIn.setBlockState(pos, state.cycleProperty(Bewitchment.HALF), 3);
			return true;
		}
		
		final TileCauldron tile = (TileCauldron) worldIn.getTileEntity(pos);
		return tile != null && tile.onCauldronRightClick(playerIn, hand, playerIn.getHeldItem(hand));
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
		return new TileCauldron();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
		TileCauldron tile = (TileCauldron) world.getTileEntity(pos);
		if (tile != null) {
			float level = tile.getTank().getFluidAmount() / (Fluid.BUCKET_VOLUME * 2F);
			level = pos.getY() + 0.1F + level;
			if (tile.isBoiling()) {
				Fluid fluid = tile.getTank().getInnerFluid();
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
			}
			
			if (tile.getMode() == Mode.CRAFTING || (tile.getMode() == Mode.STEW && tile.getProgress() >= TileCauldron.CRAFTING_TIME)) {
				final float x = pos.getX() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				final float z = pos.getZ() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				Bewitchment.proxy.spawnParticle(ParticleF.SPARK, x, level, z, 0.0D, 0.1D, 0.0D);
			}
		}
	}
	
}