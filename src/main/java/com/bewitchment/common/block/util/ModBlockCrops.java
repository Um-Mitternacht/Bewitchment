package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

@SuppressWarnings("NullableProblems")
public class ModBlockCrops extends BlockCrops {
	private Item seed, crop;
	
	public ModBlockCrops(String name) {
		super();
		Util.registerBlock(this, name, Material.PLANTS, SoundType.PLANT, 0, 0, "", 0);
	}
	
	@Override
	@Nonnull
	public Item getCrop() {
		return crop;
	}
	
	@Override
	@Nonnull
	public Item getSeed() {
		return seed;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
		super.neighborChanged(state, world, to, block, from);
		world.notifyBlockUpdate(to, state, state, 3);
		if (world.getBlockState(to.down()).getBlock() != Blocks.FARMLAND) {
			dropBlockAsItem(world, to, state, 0);
			world.setBlockToAir(to);
		}
	}
	
	public void setItems(Item seed, Item crop) {
		this.seed = seed;
		this.crop = crop;
	}
}