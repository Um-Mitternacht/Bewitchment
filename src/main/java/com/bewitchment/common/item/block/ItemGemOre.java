package com.bewitchment.common.item.block;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.natural.BlockGemOre;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * This class was created by <Arekkuusu> on 27/06/2017.
 * It's distributed as part of Solar Epiphany under
 * the MIT license.
 */
public class ItemGemOre extends ItemBlock implements IModelRegister {

	public ItemGemOre(Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
		setHasSubtypes(true);
		setMaxDamage(0);
		this.setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "_" + BlockGemOre.Gem.values()[stack.getMetadata()].getName();
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < BlockGemOre.Gem.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}


	@Override
	public void registerModel() {
		BlockGemOre.Gem[] values = BlockGemOre.Gem.values();
		for (int i = 0; i < values.length; i++) {
			BlockGemOre.Gem gem = values[i];
			ModelHandler.registerForgeModel(this, i, "gem=" + gem.getName());
		}
	}
}
