package com.bewitchment.common.block.misc;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.Random;

/**
 * This class was created by Arekkuusu on 11/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Optional.Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliserExt", modid = "thaumcraft")
public abstract class BlockCandle extends BlockMod implements IInfusionStabiliserExt {

	private boolean isLit;

	public BlockCandle(String id, boolean lit) {
		super(id, Material.CLOTH);
		setSoundType(SoundType.CLOTH);
		this.setDefaultState(blockState.getBaseState().withProperty(Bewitchment.COLOR, EnumDyeColor.WHITE));
		isLit = lit;
		if (isLit) {
			setCreativeTab(null); // No need for them to appear twice
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		if (!isLit) {
			for (int i = 0; i < 16; i++) {
				ModelHandler.registerModel(this, i);
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.getBlockColor(state.getValue(Bewitchment.COLOR));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(Bewitchment.COLOR, EnumDyeColor.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(Bewitchment.COLOR).getMetadata();
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
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(
				pos.down()).isSideSolid(
				worldIn,
				pos.down(),
				EnumFacing.UP
		);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (state.getBlock() == ModBlocks.candle_medium_lit)
			world.setBlockState(pos, ModBlocks.candle_medium.getDefaultState().withProperty(Bewitchment.COLOR, state.getValue(Bewitchment.COLOR)), 3);
		else if (state.getBlock() == ModBlocks.candle_small_lit)
			world.setBlockState(pos, ModBlocks.candle_small.getDefaultState().withProperty(Bewitchment.COLOR, state.getValue(Bewitchment.COLOR)), 3);
		else {
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (!heldItem.isEmpty() && heldItem.getItem() == Items.FLINT_AND_STEEL) {
				heldItem.damageItem(1, playerIn);
				if (state.getBlock() == ModBlocks.candle_medium)
					world.setBlockState(pos, ModBlocks.candle_medium_lit.getDefaultState().withProperty(Bewitchment.COLOR, state.getValue(Bewitchment.COLOR)), 3);
				else if (state.getBlock() == ModBlocks.candle_small)
					world.setBlockState(pos, ModBlocks.candle_small_lit.getDefaultState().withProperty(Bewitchment.COLOR, state.getValue(Bewitchment.COLOR)), 3);
			}
		}
		return true;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, Bewitchment.COLOR);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return isLit ? 9 + getType() * 5 : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(Bewitchment.COLOR, EnumDyeColor.byMetadata(meta));
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (isLit) {
			Vec3d offset = getOffset(state, world, pos);
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5 + offset.x, pos.getY() + 0.7 + getType() * 0.25, pos.getZ() + 0.5 + offset.z, 0, 0, 0);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	public abstract int getType();

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < 16; ++i) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		if (isLit)
			return new ItemStack(state.getBlock() == ModBlocks.candle_medium_lit ? ModBlocks.candle_medium : ModBlocks.candle_small, 1, state.getBlock().getMetaFromState(state));
		return super.getPickBlock(state, target, world, pos, player);
	}

	public boolean isLit() {
		return isLit;
	}


	@Override
	@Optional.Method(modid = "thaumcraft")
	public boolean canStabaliseInfusion(World world, BlockPos pos) {
		return true;
	}

	@Override
	@Optional.Method(modid = "thaumcraft")
	public float getStabilizationAmount(World world, BlockPos pos) {
		return 0;
	}

}
