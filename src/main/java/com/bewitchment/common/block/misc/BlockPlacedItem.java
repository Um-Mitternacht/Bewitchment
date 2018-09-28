package com.bewitchment.common.block.misc;

import java.util.Random;

import com.bewitchment.common.block.BlockModTileEntity;
import com.bewitchment.common.core.MaterialItemPlaced;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.tile.tiles.TileEntityPlacedItem;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlacedItem extends BlockModTileEntity {

	public BlockPlacedItem() {
		super(LibBlockName.PLACED_ITEM, MaterialItemPlaced.ITEM_MATERIAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
		this.setHardness(0f);
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
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta]);
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(((TileEntityPlacedItem) world.getTileEntity(pos)).getItem());
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPlacedItem();
	}

}
