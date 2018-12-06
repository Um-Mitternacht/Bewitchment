package com.bewitchment.common.block.chisel;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockSilverChiseled extends BlockMod {

	public static final PropertyEnum<BlockSilverVariant> VARIANT = PropertyEnum.create("variant", BlockSilverVariant.class);

	public BlockSilverChiseled(Material material, SoundType sound) {
		super(LibBlockName.SILVER_BLOCK + "_chisel", material, sound);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, BlockSilverVariant.values()[meta]);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < BlockSilverVariant.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void registerModel() {
		for (BlockSilverVariant v : BlockSilverVariant.values()) {
			ModelHandler.registerForgeModel(this, v.ordinal(), "variant=" + v.getName());
		}
	}

	public static enum BlockSilverVariant implements IStringSerializable {

		SYMBOL, SUN, MOON, CUP, WAND, SWORD, PENTACLE, PENTAGRAM, BEVEL;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}

}
