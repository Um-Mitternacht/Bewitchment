package com.bewitchment.common.block.natural;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

import static com.bewitchment.common.core.BewitchmentCreativeTabs.BLOCKS_CREATIVE_TAB;

/**
 * This class was created by <Arekkuusu> on 27/06/2017.
 * It's distributed as part of Solar Epiphany under
 * the MIT license.
 */
public class BlockGemOre extends BlockMod {

	public static final PropertyEnum<Gem> GEM = PropertyEnum.create("gem", Gem.class);

	public BlockGemOre() {
		super(LibBlockName.GEM_ORE, Material.ROCK);
		setHardness(2.0F);
		setCreativeTab(BLOCKS_CREATIVE_TAB);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(GEM, Gem.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(GEM).ordinal();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(GEM).ordinal();
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void getSubBlocks(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		{
			for (int i = 0; i < Gem.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, GEM);
	}

	@Override
	public void registerModel() {
		Gem[] values = Gem.values();
		for (int i = 0; i < values.length; i++) {
			Gem gem = values[i];
			ModelHandler.registerForgeModel(this, i, "gem=" + gem.getName());
		}
	}

	public enum Gem implements IStringSerializable {
		GARNET,
		MOLDAVITE,
		NUUMMITE,
		TIGERS_EYE,
		TOURMALINE,
		BLOODSTONE,
		JASPER,
		MALACHITE,
		AMETHYST,
		ALEXANDRITE;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
