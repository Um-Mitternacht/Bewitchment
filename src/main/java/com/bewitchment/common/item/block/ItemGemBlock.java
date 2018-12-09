package com.bewitchment.common.item.block;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.natural.BlockGem;
import com.bewitchment.common.block.natural.BlockGemOre;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ItemGemBlock extends ItemBlock implements IModelRegister {
	public ItemGemBlock(Block block) {
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
		BlockGem.Gem[] values = BlockGem.Gem.values();
		for (int i = 0; i < values.length; i++) {
			BlockGem.Gem gem = values[i];
			ModelHandler.registerForgeModel(this, i, "gem=" + gem.getName());
		}
	}
}
