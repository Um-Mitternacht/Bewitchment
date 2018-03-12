package com.bewitchment.common.item.magic;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

import static com.bewitchment.common.core.ModCreativeTabs.ITEMS_CREATIVE_TAB;

/**
 * Created by Joseph on 11/26/2017.
 */
public class ItemGemPowder extends ItemMod {

	public static final PropertyEnum<ItemGemPowder.GemPowderEnum> FUME = PropertyEnum.create("gem_powder", ItemGemPowder.GemPowderEnum.class);

	public ItemGemPowder() {
		super(LibItemName.GEM_POWDER);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(ITEMS_CREATIVE_TAB);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "_" + ItemGemPowder.GemPowderEnum.values()[stack.getMetadata()].getName();
	}

	@Override
	public void registerModel() {
		ItemGemPowder.GemPowderEnum[] values = ItemGemPowder.GemPowderEnum.values();
		for (int i = 0; i < values.length; i++) {
			ItemGemPowder.GemPowderEnum powder = values[i];
			ModelHandler.registerForgeModel(this, i, "powder=" + powder.getName());
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < ItemGemPowder.GemPowderEnum.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}


	public enum GemPowderEnum implements IStringSerializable {
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
