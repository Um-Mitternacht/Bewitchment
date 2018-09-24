package com.bewitchment.common.block;

import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockModTileEntity extends BlockMod implements ITileEntityProvider {
	public BlockModTileEntity(String id, Material material) {
		super(id, material);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getTileEntity(pos) != null) {
			return ((ModTileEntity) worldIn.getTileEntity(pos)).onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		return false;
	}


	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.getTileEntity(pos) != null) {
			((ModTileEntity) worldIn.getTileEntity(pos)).onBlockBroken(worldIn, pos, state);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		((ModTileEntity) te).onBlockHarvested(worldIn, player, pos, state, te, stack);
		super.harvestBlock(worldIn, player, pos, state, te, stack);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
