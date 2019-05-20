package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

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
	
	public void setItems(Item seed, Item crop) {
		this.seed = seed;
		this.crop = crop;
	}
}