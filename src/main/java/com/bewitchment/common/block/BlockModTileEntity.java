package com.bewitchment.common.block;

import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockModTileEntity extends BlockMod implements ITileEntityProvider {
	public BlockModTileEntity(String id, Material material) {
		super(id, material);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		final TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof ModTileEntity) {
			return ((ModTileEntity) tile).onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}


	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		final TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof ModTileEntity) {
			((ModTileEntity) tile).onBlockBroken(worldIn, pos, state);
		}
		worldIn.removeTileEntity(pos);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
