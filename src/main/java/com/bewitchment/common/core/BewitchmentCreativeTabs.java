package com.bewitchment.common.core;

<<<<<<< HEAD
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
=======
import javax.annotation.Nonnull;

import com.bewitchment.api.CropRegistry;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.block.Block;
>>>>>>> ritual-rewrite
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class BewitchmentCreativeTabs {

	//Todo: Organize the tabs. They are very messy.

	public static final PlantsCreativeTab PLANTS_CREATIVE_TAB = new PlantsCreativeTab();
	public static final ItemsCreativeTab ITEMS_CREATIVE_TAB = new ItemsCreativeTab();
	public static final BlocksCreativeTab BLOCKS_CREATIVE_TAB = new BlocksCreativeTab();

	private BewitchmentCreativeTabs() {
	}

	private static class CreativeTab extends CreativeTabs {

		@Nonnull
		NonNullList<ItemStack> list;

		CreativeTab(String name) {
			super(LibMod.MOD_ID + name);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return getIconItemStack();
		}
	}

	private static class PlantsCreativeTab extends CreativeTab {

		PlantsCreativeTab() {
			super("_plants");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
			return new ItemStack(ModItems.aconitum);
		}
	}

	private static class ItemsCreativeTab extends CreativeTab {

		ItemsCreativeTab() {
			super("_items");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
<<<<<<< HEAD
			return new ItemStack(ModItems.mortar_and_pestle);
=======
			return new ItemStack(ModItems.gem, 1, 4);
		}

		@Override
		public boolean hasSearchBar() {
			return true;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
			this.list = list;

			addItem(ModItems.boline);
			addItem(ModItems.athame);
			addItem(ModItems.taglock);
			addItem(ModItems.salt);
			addItem(ModItems.mortar_and_pestle);
			addItem(ModItems.silver_pickaxe);
			addItem(ModItems.silver_axe);
			addItem(ModItems.silver_spade);
			addItem(ModItems.silver_hoe);
			addItem(ModItems.silver_sword);
			addItem(ModItems.silver_helmet);
			addItem(ModItems.silver_chestplate);
			addItem(ModItems.silver_leggings);
			addItem(ModItems.silver_boots);
			addItem(ModItems.spell_page);
			addItem(ModItems.book_of_shadows);
			addItem(ModItems.dusty_grimoire);
			addItem(ModItems.brew_phial_drink);
			addItem(ModItems.brew_phial_splash);
			addItem(ModItems.brew_phial_linger);
			addItem(ModItems.gem_powder);
			addItem(ModItems.gem);
			addItem(ModItems.gemstone_amalgam);
			addItem(ModItems.bee);
			addItem(ModItems.honeycomb);
			addItem(ModItems.empty_honeycomb);
			addItem(ModItems.glass_jar);
			addItem(ModItems.honey);
			addItem(ModItems.wax);
			addItem(ModItems.cold_iron_ingot);
			addItem(ModItems.silver_powder);
			addItem(ModItems.silver_ingot);
			addItem(ModItems.silver_nugget);
			addItem(ModItems.ritual_chalk);
			// addItem(ModItems.unrefined_chalk);
			addItem(ModItems.needle_bone);
			addItem(ModItems.wool_of_bat);
			addItem(ModItems.tongue_of_dog);
			addItem(ModItems.wood_ash);
			addItem(ModItems.ectoplasm);
			addItem(ModItems.spectral_dust);
			addItem(ModItems.silver_scales);
			addItem(ModItems.eye_of_old);
			addItem(ModItems.heart);
			addItem(ModItems.envenomed_fang);
			addItem(ModItems.dimensional_sand);
			addItem(ModItems.chromatic_quill);
			addItem(ModItems.carnivorous_tooth);
			addItem(ModItems.eye_of_ancient);
			addItem(ModItems.hoof);
			addItem(ModItems.equine_tail);
			addItem(ModItems.catechu);
			addItem(ModItems.albedo);
			addItem(ModItems.iron_gall_ink);
			addItem(ModItems.oak_apple_gall);
			addItem(ModItems.absinthe_green);
			addItem(ModItems.fume);
>>>>>>> ritual-rewrite
		}
	}

	private static class BlocksCreativeTab extends CreativeTab {

		BlocksCreativeTab() {
			super("_blocks");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
			return new ItemStack(ModBlocks.cauldron);
		}
<<<<<<< HEAD
=======

		@Override
		public boolean hasSearchBar() {
			return true;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
			this.list = list;

			addBlock(ModBlocks.cauldron);
			addBlock(ModBlocks.witch_altar);
			addBlock(ModBlocks.oven);
			addBlock(ModBlocks.apiary);
			addBlock(ModBlocks.candle_large);
			addBlock(ModBlocks.candle_medium);
			addBlock(ModBlocks.candle_small);
			addBlock(ModBlocks.beehive);
			// addBlock(ModBlocks.chalk);
			addBlock(ModBlocks.coquina);
			addBlock(ModBlocks.gem_ore);
			addBlock(ModBlocks.salt_ore);
			addBlock(ModBlocks.silver_ore);
			addBlock(ModBlocks.moldavite_block);
			addBlock(ModBlocks.bloodstone_block);
			addBlock(ModBlocks.tourmaline_block);
			addBlock(ModBlocks.malachite_block);
			addBlock(ModBlocks.tigers_eye_block);
			addBlock(ModBlocks.nuummite_block);
			addBlock(ModBlocks.alexandrite_block);
			addBlock(ModBlocks.jasper_block);
			addBlock(ModBlocks.amethyst_block);
			addBlock(ModBlocks.garnet_block);
			addBlock(ModBlocks.silver_block);
			addBlock(ModBlocks.nethersteel);
			addBlock(ModBlocks.fake_ice);
			addBlock(ModBlocks.fake_ice_fence);
			addBlock(ModBlocks.fake_ice_stairs);
			addBlock(ModBlocks.fake_ice_slab_half);
		}
>>>>>>> ritual-rewrite
	}
}
