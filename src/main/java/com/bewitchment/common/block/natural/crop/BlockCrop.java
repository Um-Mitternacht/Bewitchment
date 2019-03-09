package com.bewitchment.common.block.natural.crop;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
		setTranslationKey(LibMod.MOD_ID + "." + id);
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

	@SuppressWarnings("deprecation")
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
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
