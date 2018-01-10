package com.bewitchment.common.ritual;

import static com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType.ANY;
import static com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType.ENDER;
import static com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType.NETHER;
import static com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType.NORMAL;

import com.bewitchment.api.ritual.Ritual;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.block.tools.BlockCircleGlyph.GlyphType;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.TileEntityGlyph;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

public class ModRituals {

	private static final NonNullList<ItemStack> none = NonNullList.<ItemStack>create();

	public static Ritual night, fast_day, glowing, spawn_witch, spawn_wither, draw_circle_medium, draw_circle_large;

	public static void init() {

		night = new RitualHighMoon(
				rl("high_moon"), //Reg name
				of( //Recipe
						new OreIngredient("ingotGold"),
						Ingredient.fromItem(Items.NETHERBRICK)
				),
				none, // Output
				100, //Initial cast time
				circles(NORMAL, null, null),
				800, //Initial cost
				0 //Cost per tick
		);

		fast_day = new RitualSandsTime(
				rl("time_sands"),
				of(
						new OreIngredient("sand"),
						new OreIngredient("oreDiamond")
				),
				none,
				-1,
				circles(NORMAL, NORMAL, NORMAL),
				1000,
				5
		);

		glowing = new RitualPerception(
				rl("perception"),
				of(
						Ingredient.fromItem(Item.getItemFromBlock(Blocks.GLOWSTONE)),
						Ingredient.fromItem(Items.GOLDEN_CARROT)
				),
				none,
				-1,
				circles(NORMAL, NORMAL, NORMAL),
				700,
				3
		);

		spawn_witch = new RitualConjurationWitch(
				rl("conjure_witch"),
				of(
						Ingredient.fromItem(ModItems.athame),
						Ingredient.fromItem(Items.APPLE),
						Ingredient.fromItem(Items.POISONOUS_POTATO)
				),
				ofs(new ItemStack(ModItems.athame)),
				200,
				circles(NETHER, ENDER, NETHER),
				3000,
				3
		);

		spawn_wither = new RitualConjurationWither(
				rl("conjure_wither"),
				of(
						Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1)),
						Ingredient.fromItem(ModItems.athame),
						Ingredient.fromItem(Item.getItemFromBlock(Blocks.SOUL_SAND))

				),
				none,
				400,
				circles(NETHER, NETHER, NETHER),
				5000,
				4);
		
		draw_circle_medium = new RitualDrawing(
				rl("draw_circle_medium"), 
				of(
						Ingredient.fromItem(Items.CLAY_BALL) // balanced
				), 
				none, 
				40, 
				circles(ANY, null, null), 
				100, 
				0, 
				TileEntityGlyph.medium);
		draw_circle_large = new RitualDrawing(
				rl("draw_circle_large"), 
				of(
						Ingredient.fromItem(Items.CLAY_BALL),
						Ingredient.fromItem(Items.CLAY_BALL) // VERY balanced
				), 
				none, 
				40, 
				circles(ANY, ANY, null), 
				100, 
				0, 
				TileEntityGlyph.big);

		registerAll();
	}

	public static void registerAll() {
		Ritual.REGISTRY.registerAll(
				night, fast_day, glowing, spawn_witch, spawn_wither, draw_circle_large, draw_circle_medium
		);
	}

	public static NonNullList<Ingredient> of(Ingredient... list) {
		return NonNullList.<Ingredient>from(Ingredient.EMPTY, list);
	}

	public static NonNullList<ItemStack> ofs(ItemStack... list) {
		if (list == null || list.length == 0)
			return none;
		return NonNullList.<ItemStack>from(ItemStack.EMPTY, list);
	}

	public static ResourceLocation rl(String s) {
		return new ResourceLocation(LibMod.MOD_ID, s);
	}

	public static int circles(GlyphType small, BlockCircleGlyph.GlyphType medium, BlockCircleGlyph.GlyphType big) {
		if (small == null) throw new IllegalArgumentException("Cannot have the smaller circle missing");
		if (medium == null && big != null)
			throw new IllegalArgumentException("Cannot have null middle circle when a big circle is present");
		if (small == GlyphType.GOLDEN || medium == GlyphType.GOLDEN || big == GlyphType.GOLDEN)
			throw new IllegalArgumentException("No golden circles allowed");
		int circleNum = 0;
		if (medium != null) circleNum++;
		if (big != null) circleNum++;
		return circleNum | small.meta() << 2 | (medium == null ? 0 : medium.meta() << 4) | (big == null ? 0 : big.meta() << 6);
	}
}
