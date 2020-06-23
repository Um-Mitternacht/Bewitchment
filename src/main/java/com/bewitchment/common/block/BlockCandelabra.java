package com.bewitchment.common.block;

import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.Random;

/**
 * Created by Joseph on 3/5/2020.
 */

@SuppressWarnings({"deprecation", "NullableProblems", "WeakerAccess"})
@Optional.Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliserExt", modid = "thaumcraft")
public class BlockCandelabra extends ModBlock implements IInfusionStabiliserExt {
	
	public static final PropertyBool LIT = PropertyBool.create("lit");
	//Todo: Change this bounding box. Right now, it's just the candle bounding box.
	
	public BlockCandelabra(String name) {
		super("candelabra_" + name, Material.IRON, SoundType.METAL, 1, 1, "pickaxe", 1);
		Blocks.FIRE.setFireInfo(this, 0, 0);
		setLightOpacity(0);
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		return state.getValue(LIT) ? 9 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(LIT, meta == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LIT) ? 1 : 0;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.getBlockState(pos.down()).getBlock().canPlaceTorchOnTop(world.getBlockState(pos.down()), world, pos)) world.destroyBlock(pos, true);
	}
	
	//Todo: Set this to the proper values
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (state.getValue(LIT)) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.175, pos.getZ() + 0.5, 0, 0, 0);
		if (state.getValue(LIT)) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.125, pos.getY() + 1.075, pos.getZ() + 0.5, 0, 0, 0);
		if (state.getValue(LIT)) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.875, pos.getY() + 1.075, pos.getZ() + 0.5, 0, 0, 0);
		if (state.getValue(LIT)) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.075, pos.getZ() + 0.875, 0, 0, 0);
		if (state.getValue(LIT)) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.075, pos.getZ() + 0.125, 0, 0, 0);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (state.getValue(LIT)) {
			world.setBlockState(pos, getDefaultState().withProperty(LIT, false));
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f, false);
			return true;
		}
		else {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == Items.FLINT_AND_STEEL) {
				stack.damageItem(1, player);
				world.setBlockState(pos, getDefaultState().withProperty(LIT, true));
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, world.rand.nextFloat() * 0.4f + 0.8f, false);
				return true;
			}
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LIT);
	}
	
	public float getEnchantPowerBonus(World world, BlockPos pos) {
		return 1.5f;
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
	@Optional.Method(modid = "thaumcraft")
	public boolean canStabaliseInfusion(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	@Optional.Method(modid = "thaumcraft")
	public float getStabilizationAmount(World world, BlockPos pos) {
		return 1.5f;
	}
}
