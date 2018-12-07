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

public class BlockNetherSteelChiseled extends BlockMod {

	public static final PropertyEnum<BlockSteelVariant> VARIANT = PropertyEnum.create("variant", BlockSteelVariant.class);

	public BlockNetherSteelChiseled(Material material, SoundType sound) {
		super(LibBlockName.NETHERSTEEL + "_chisel", material, sound);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, BlockSteelVariant.values()[meta]);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < BlockSteelVariant.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void registerModel() {
		for (BlockSteelVariant v : BlockSteelVariant.values()) {
			ModelHandler.registerForgeModel(this, v.ordinal(), "variant=" + v.getName());
		}
	}

	public static enum BlockSteelVariant implements IStringSerializable {

		SYMBOL, SENTIENT, POLISHED, SKULL, PENTACLE, PENTAGRAM, BEVEL, EYE_WATCHING, EYE, HELLISH, HELLISH_WATCHING;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}

}
