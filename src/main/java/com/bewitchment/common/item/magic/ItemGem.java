package com.bewitchment.common.item.magic;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.natural.BlockGemOre;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * This class was created by <Arekkuusu> on 28/06/2017.
 * It's distributed as part of Solar Epiphany under
 * the MIT license.
 */
public class ItemGem extends ItemMod {

	public ItemGem() {
		super(LibItemName.GEM);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.getMetadata() < 0 || stack.getMetadata() >= BlockGemOre.Gem.values().length) {
			return super.getTranslationKey(stack);
		}
		return super.getTranslationKey(stack) + "_" + BlockGemOre.Gem.values()[stack.getMetadata()].getName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < BlockGemOre.Gem.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, BlockGemOre.Gem.class);
	}
}
