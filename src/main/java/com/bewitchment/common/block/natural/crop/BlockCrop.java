package com.bewitchment.common.block.natural.crop;

import com.bewitchment.client.core.IModelRegister;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 28/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockCrop extends BlockCrops implements IModelRegister {

	private int maxAge;
	private Item seed;
	private Item crop;

	public BlockCrop(String id) {
		super();
		setTranslationKey(id);
		setRegistryName(id);
		setCreativeTab(null);
		this.maxAge = 7;
	}

	public BlockCrop(String id, int maxAge) {
		super();
		setTranslationKey(id);
		setRegistryName(id);
		setCreativeTab(null);
		this.maxAge = maxAge;
	}

	@Override
	public int getMaxAge() {
		return maxAge;
	}

	@Override
	public Item getSeed() {
		return seed;
	}

	public void setSeed(Item seed) {
		this.seed = seed;
	}

	@Override
	public Item getCrop() {
		return crop;
	}

	public void setCrop(Item crop) {
		this.crop = crop;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		// NO-OP since crops don't have an itemBlock to texture
	}
}
