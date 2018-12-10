package com.bewitchment.common.block.misc;

import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockWitchesLight extends BlockMod {

	private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
	private static final Material WISP_MATERIAL = new Material(MapColor.AIR) {
		@Override
		public boolean blocksLight() {
			return false;
		}

		@Override
		public EnumPushReaction getPushReaction() {
			return EnumPushReaction.DESTROY;
		}

		@Override
		public boolean isReplaceable() {
			return true;
		}

		@Override
		public boolean isOpaque() {
			return false;
		}

		@Override
		public boolean getCanBurn() {
			return false;
		}
	};

	public BlockWitchesLight() {
		super(LibBlockName.WITCHES_LIGHT, WISP_MATERIAL);
		this.setLightOpacity(0);
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return EMPTY_AABB;
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return EMPTY_AABB;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 10;
	}

	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(ModBlocks.revealing_lantern) || Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(ModBlocks.revealing_lantern)) {
			worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5d + rand.nextGaussian() * 0.2, pos.getY() + rand.nextDouble() * 0.6 + 0.2, pos.getZ() + 0.5d + rand.nextGaussian() * 0.2, 0, 0, 0);
		}
	}

	@Override
	public boolean doesSideBlockChestOpening(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
}
