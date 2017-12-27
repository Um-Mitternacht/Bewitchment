/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.ritual;

import com.bewitchment.api.ritual.Ritual;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

public class ModRituals {

	public static Ritual highMoon, sandTime, perception, conjureWither, conjureWitch;

	public static void init() {
		
		highMoon = new RitualHighMoon(new ResourceLocation(LibMod.MOD_ID, "high_moon"), of(new OreIngredient("ingotGold"), Ingredient.fromItem(Items.NETHERBRICK)), nops(), 100, circles(GlyphType.NORMAL, null, null), 2000, 0);
		sandTime = new RitualSandsTime(new ResourceLocation(LibMod.MOD_ID, "time_sands"), of(new OreIngredient("sand"), new OreIngredient("oreDiamond")), nops(), 24000, circles(GlyphType.NORMAL, GlyphType.NORMAL, GlyphType.ENDER), 4000, 4);
		perception = new RitualPerception(new ResourceLocation(LibMod.MOD_ID, "perception"), of(Ingredient.fromItem(Item.getItemFromBlock(Blocks.GLOWSTONE)), Ingredient.fromItem(Items.GOLDEN_CARROT)), nops(), -1, circles(GlyphType.ENDER, GlyphType.ENDER, null), 1500, 5);
		conjureWither = new RitualConjurationWither(new ResourceLocation(LibMod.MOD_ID, "conjure_wither_ritual"),
				of(
						// Ingredient.fromStacks(QuickItemStacks.wither_skull), //TODO
						Ingredient.fromItem(Item.getItemFromBlock(Blocks.SOUL_SAND)),
						// Ingredient.fromStacks(QuickItemStacks.charged_talisman), //TODO the charged component
						Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 11))),
				ofs(new ItemStack(Items.APPLE) // TODO output the uncharged component
				), 200, circles(GlyphType.NETHER, GlyphType.NETHER, GlyphType.NETHER), 8000, 2);
		conjureWitch = new RitualConjurationWitch(new ResourceLocation(LibMod.MOD_ID, "conjure_witch_ritual"),
				of(Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 8)), Ingredient.fromItem(ModItems.boline), Ingredient.fromItem(Items.POISONOUS_POTATO),
						// Ingredient.fromStacks(QuickItemStacks.charged_talisman), TODO the charged component
						Ingredient.fromItem(Item.getItemFromBlock(Blocks.RED_MUSHROOM))),
				ofs(new ItemStack(ModItems.boline) // TODO output the uncharged component
				), 60, circles(GlyphType.NORMAL, GlyphType.NETHER, null), 6000, 4);
		registerAll();
	}

	private static void registerAll() {
		Ritual.REGISTRY.registerAll(//
				highMoon, sandTime, perception, conjureWither, conjureWitch //
		);
	}
	
	public static NonNullList<Ingredient> of(Ingredient... list) {
		return NonNullList.<Ingredient>from(Ingredient.EMPTY, list);
	}
	
	public static NonNullList<ItemStack> ofs(ItemStack... list) {
		if (list == null || list.length == 0)
			return nops();
		return NonNullList.<ItemStack>from(ItemStack.EMPTY, list);
	}
	
	public static NonNullList<ItemStack> nops() {
		return NonNullList.<ItemStack>create();
	}
	
	public static int circles(GlyphType small, BlockCircleGlyph.GlyphType medium, BlockCircleGlyph.GlyphType big) {
		if (small == null)
			throw new IllegalArgumentException("Cannot have the smaller circle missing");
		if (medium == null && big != null)
			throw new IllegalArgumentException("Cannot have null middle circle when a big circle is present");
		if (small == GlyphType.GOLDEN || medium == GlyphType.GOLDEN || big == GlyphType.GOLDEN)
			throw new IllegalArgumentException("No golden circles allowed");
		int circleNum = 0;
		if (medium != null)
			circleNum++;
		if (big != null)
			circleNum++;
		return circleNum | small.ordinal() << 2 | (medium == null ? 0 : medium.ordinal() << 4) | (big == null ? 0 : big.ordinal() << 6);
	}

}
