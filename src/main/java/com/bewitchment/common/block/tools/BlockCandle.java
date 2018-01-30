package com.bewitchment.common.block.tools;

import static com.bewitchment.api.BewitchmentAPI.COLOR;

import java.util.Random;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 11/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public abstract class BlockCandle extends BlockMod {
	
	private boolean isLit;

	public BlockCandle(String id, boolean lit) {
		super(id, Material.CLOTH);
		setSound(SoundType.CLOTH);
		isLit = lit;
		if (isLit)
			setCreativeTab(null); // No need for them to appear twice
	}

	@Override
	protected IBlockState defaultState() {
		return super.defaultState().withProperty(COLOR, EnumDyeColor.WHITE);
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
		return MapColor.getBlockColor(state.getValue(COLOR));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR).getMetadata();
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
			world.setBlockState(pos, ModBlocks.candle_medium.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 3);
		else if (state.getBlock() == ModBlocks.candle_small_lit)
			world.setBlockState(pos, ModBlocks.candle_small.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 3);
		else {
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (!heldItem.isEmpty() && heldItem.getItem() == Items.FLINT_AND_STEEL) {
				heldItem.damageItem(1, playerIn);
				if (state.getBlock() == ModBlocks.candle_medium)
					world.setBlockState(pos, ModBlocks.candle_medium_lit.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 3);
				else if (state.getBlock() == ModBlocks.candle_small)
					world.setBlockState(pos, ModBlocks.candle_small_lit.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 3);
			}
		}
		return true;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return isLit ? 5 + getType() * 5 : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (isLit) {
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.7 + getType() * 0.25, pos.getZ() + 0.5, 0, 0, 0);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		}
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	public abstract int getType();
}
