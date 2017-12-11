package com.bewitchment.common.item.magic;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * Created by Joseph on 11/26/2017.
 */
public class ItemFume extends ItemMod {

	public static final PropertyEnum<ItemFume.FumeEnum> FUME = PropertyEnum.create("fume", ItemFume.FumeEnum.class);

	public ItemFume() {
		super(LibItemName.FUME);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(BewitchmentCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "_" + ItemFume.FumeEnum.values()[stack.getMetadata()].getName();
	}

	@Override
	public void registerModel() {
		ItemFume.FumeEnum[] values = ItemFume.FumeEnum.values();
		for (int i = 0; i < values.length; i++) {
			ItemFume.FumeEnum fume = values[i];
			ModelHandler.registerForgeModel(this, i, "fume=" + fume.getName());
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < ItemFume.FumeEnum.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}


	public enum FumeEnum implements IStringSerializable {
		FOUL_FUME,
		BREATH_OF_THE_GODDESS,
		EXHALE_OF_THE_HORNED_ONE,
		HINT_OF_REBIRTH,
		ODOR_OF_PURITY,
		REEK_OF_MISFORTUNE,
		WHIFF_OF_MAGIC,
		SCENT_OF_SHADOWS,
		PROTECTIVE_SCENT,
		REEK_OF_DEATH,
		PURE_TRANQUILITY,
		ESSENCE_OF_LIFE,
		OIL_OF_VITRIOL,
		REDSTONE_DISTILLATE,
		TEAR_OF_THE_GODDESS,
		CHAOTIC_SUBSTANCE,
		ENTROPIC_OIL,
		DIAMOND_VAPOR,
		BOTTLED_DRAGONS_ROAR,
		STRANGE_MIASMA,
		ENDER_DEW,
		OUTER_ESSENCE,
		PURE_INFERNO,
		SOLAR_ESSENCE,
		SOLAR_OIL,
		SACRED_ESSENCE,
		INFERNAL_ESSENCE,
		DISTILLATE_OF_ORDER;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
