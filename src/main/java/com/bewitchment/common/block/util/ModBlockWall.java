package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by Joseph on 3/6/2020.
 */

//Credit to Vazkii for code that helped fix this up.
@SuppressWarnings({"deprecation", "NullableProblems"})
public class ModBlockWall extends BlockWall {
	public ModBlockWall(String name, Block base, String... oreDictionaryNames) {
		super(base);
		setDefaultState(pickDefaultState());
		Util.registerBlock(this, name, base, oreDictionaryNames);
	}

	public IBlockState pickDefaultState() {
		return blockState.getBaseState().withProperty(UP, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(EAST, false).withProperty(VARIANT, EnumType.NORMAL);
	}

	@Override
	public void getSubBlocks(CreativeTabs tabs, @Nonnull NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return (!Util.isTransparent(state) || world.getBlockState(pos.offset(face)).getBlock() != this) && super.shouldSideBeRendered(state, world, pos, face);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Nonnull
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return super.getActualState(state, world, pos).withProperty(VARIANT, EnumType.NORMAL);
	}

	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT, UP, NORTH, SOUTH, WEST, EAST);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
		return true;
	}
}
