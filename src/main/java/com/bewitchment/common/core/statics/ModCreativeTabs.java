package com.bewitchment.common.core.statics;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.BrewData.BrewEntry;
import com.bewitchment.common.content.cauldron.BrewModifierListImpl;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModCreativeTabs {

	//Todo: Organize the tabs. They are very messy.

	public static final PlantsCreativeTab PLANTS_CREATIVE_TAB = new PlantsCreativeTab();
	public static final ItemsCreativeTab ITEMS_CREATIVE_TAB = new ItemsCreativeTab();
	public static final BrewsCreativeTab BREW_CREATIVE_TAB = new BrewsCreativeTab();
	public static final BlocksCreativeTab BLOCKS_CREATIVE_TAB = new BlocksCreativeTab();

	private ModCreativeTabs() {
	}

	private static class CreativeTab extends CreativeTabs {

		@Nonnull
		NonNullList<ItemStack> list;

		CreativeTab(String name) {
			super(LibMod.MOD_ID + name);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack createIcon() {
			return getIcon();
		}
	}

	private static class PlantsCreativeTab extends CreativeTab {

		PlantsCreativeTab() {
			super("_plants");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon() {
			return new ItemStack(ModItems.aconitum);
		}
	}

	private static class ItemsCreativeTab extends CreativeTab {

		ItemsCreativeTab() {
			super("_items");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon() {
			return new ItemStack(ModItems.mortar_and_pestle);
		}
	}

	private static class BrewsCreativeTab extends CreativeTab {

		BrewsCreativeTab() {
			super("_brews");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon() {
			ItemStack stack = new ItemStack(ModItems.brew_phial_drink);
			BrewData data = new BrewData();
			data.addEntry(new BrewEntry(ModPotions.cursed_leaping, new BrewModifierListImpl()));
			data.saveToStack(stack);
			return stack;
		}
	}

	private static class BlocksCreativeTab extends CreativeTab {

		BlocksCreativeTab() {
			super("_blocks");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon() {
			return new ItemStack(ModBlocks.cauldron);
		}
	}
}
