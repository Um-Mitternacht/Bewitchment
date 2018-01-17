package com.bewitchment.common.block.natural.crop;

import com.bewitchment.api.crop.ICrop;
import com.bewitchment.api.helper.IModelRegister;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 28/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockCrop extends BlockCrops implements ICrop, IModelRegister {

	private int maxAge;
	private Item seed;
	private Item crop;

	public BlockCrop(String id) {
		super();
		setUnlocalizedName(id);
		setRegistryName(id);
		setCreativeTab(null);
		this.maxAge = 7;
	}

	public BlockCrop(String id, int maxAge) {
		super();
		setUnlocalizedName(id);
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

	@Override
	public void setSeed(Item seed) {
		this.seed = seed;
	}

	@Override
	public Item getCrop() {
		return crop;
	}

	@Override
	public void setCrop(Item crop) {
		this.crop = crop;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		// NO-OP since crops don't have an itemBlock to texture
	}
}
