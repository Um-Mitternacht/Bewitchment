package com.bewitchment.common.block.natural.crop;

import com.bewitchment.api.crop.ICrop;
import com.bewitchment.api.helper.IModelRegister;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
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
	private ItemStack iconCache = ItemStack.EMPTY;

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
		iconCache = new ItemStack(crop);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return iconCache.copy();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		// NO-OP since crops don't have an itemBlock to texture
	}
}
