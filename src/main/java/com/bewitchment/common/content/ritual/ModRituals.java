package com.bewitchment.common.content.ritual;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.ritual.rituals.*;
import com.bewitchment.common.crafting.util.IngredientMultiOreDict;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.tiles.TileEntityGlyph;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Arrays;

import static com.bewitchment.api.ritual.EnumGlyphType.*;

public class ModRituals {

	private static final NonNullList<ItemStack> none = NonNullList.<ItemStack>create();

	public static RitualImpl night, fast_day, glowing, spawn_witch, spawn_wither, draw_circle_small, draw_circle_medium, draw_circle_large, infusion_overworld, infusion_nether, infusion_end, infusion_dream, flames, sanctuary, spawn_vex, deck, table, crystal_ball, elder_broom, juniper_broom, yew_broom, cypress_broom, gateway;

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

		spawn_vex = new RitualConjurationWitch(
				rl("conjure_vex"),
				of(
						Ingredient.fromItem(ModItems.athame),
						Ingredient.fromItem(ModItems.wormwood),
						Ingredient.fromItem(Items.APPLE)
				),
				ofs(new ItemStack(ModItems.athame)),
				100,
				circles(NORMAL, ENDER, null),
				1000,
				2
		);

		spawn_wither = new RitualConjurationWither(
				rl("conjure_wither"),
				of(
						Ingredient.fromItem(ModItems.athame),
						Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1)),
						Ingredient.fromItem(Item.getItemFromBlock(Blocks.SOUL_SAND))

				),
				ofs(new ItemStack(ModItems.athame)),
				400,
				circles(NETHER, NETHER, NETHER),
				5000,
				4);
		draw_circle_small = new RitualDrawing(
				rl("draw_circle_small"),
				of(
						Ingredient.fromItem(ModItems.wood_ash)
				),
				none,
				40,
				circles(ANY, null, null),
				100,
				0,
				TileEntityGlyph.small);
		draw_circle_medium = new RitualDrawing(
				rl("draw_circle_medium"),
				of(
						Ingredient.fromItem(ModItems.wood_ash),
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
						Ingredient.fromItem(ModItems.wood_ash),
						Ingredient.fromItem(ModItems.wood_ash),
						Ingredient.fromItem(Items.CLAY_BALL),
						Ingredient.fromItem(Items.CLAY_BALL) // VERY balanced
				),
				none,
				40,
				circles(ANY, ANY, null),
				100,
				0,
				TileEntityGlyph.big);
		gateway = new RitualGateway(rl("gateway"), of(Ingredient.fromItem(ModItems.location_stone)), ofs(), -1, circles(ENDER, NORMAL, ENDER), 4000, 8);

		ResourceLocation infusions = new ResourceLocation(LibMod.MOD_ID, "infusion");
		infusion_overworld = new RitualInfusion(infusions, of(Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.petrichor_odour.ordinal()))), none, 60, circles(NORMAL, NORMAL, NORMAL), 6000, 1, DefaultInfusions.OVERWORLD);
		infusion_nether = new RitualInfusion(infusions, of(Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.fiery_breeze.ordinal()))), none, 60, circles(NETHER, NETHER, NETHER), 6000, 1, DefaultInfusions.NETHER);
		infusion_end = new RitualInfusion(infusions, of(Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.heavenly_winds.ordinal()))), none, 60, circles(ENDER, ENDER, ENDER), 6000, 1, DefaultInfusions.END);
		infusion_dream = new RitualInfusion(infusions, of(Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.zephyr_of_the_depths.ordinal()))), none, 60, circles(NORMAL, NETHER, ENDER), 6000, 1, DefaultInfusions.DREAM);
		flames = new RitualFlames(new ResourceLocation(LibMod.MOD_ID, "flames"), of(Ingredient.fromItem(Items.BLAZE_ROD), Ingredient.fromItem(Items.COAL)), none, 3600, circles(NETHER, null, null), 300, 4);
		sanctuary = new RitualImpl(rl("sanctuary"), of(Ingredient.fromItem(ModItems.white_sage), (Ingredient.fromItem(ModItems.sagebrush)), Ingredient.fromItem(ModItems.salt), Ingredient.fromStacks(new ItemStack(Blocks.DIRT, 1, 0))), ofs(new ItemStack(ModBlocks.purifying_earth)), 130, circles(NORMAL, NORMAL, null), 500, 4);
		deck = new RitualImpl(rl("deck"), of(new IngredientMultiOreDict("dye"), (new IngredientMultiOreDict("dye")), (Ingredient.fromItem(Items.PAPER)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 3)), Ingredient.fromItem(ModItems.wax)), ofs(new ItemStack(ModItems.tarots)), 50, circles(NORMAL, null, null), 350, 1);
		table = new RitualImpl(rl("table"), of(new IngredientMultiOreDict("string"), (new IngredientMultiOreDict("dye")), Ingredient.fromStacks(new ItemStack(Blocks.CRAFTING_TABLE, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 20)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 19)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 19))), ofs(new ItemStack(ModBlocks.tarot_table)), 50, circles(NORMAL, NORMAL, null), 350, 1);
		crystal_ball = new RitualImpl(rl("crystal_ball"), of(new IngredientMultiOreDict("gemQuartz"), (new IngredientMultiOreDict("blockGlass")), (new IngredientMultiOreDict("blockGlass")), (new IngredientMultiOreDict("blockGlass")), (new IngredientMultiOreDict("blockGlass")), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 20))), ofs(new ItemStack(ModBlocks.crystal_ball)), 50, circles(NORMAL, ENDER, null), 750, 3);
		elder_broom = new RitualImpl(rl("elder_broom"), of(Ingredient.fromStacks(new ItemStack(ModBlocks.log_elder, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.broom, 1, 0)), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.magic_salve, 1, 0)), Ingredient.fromStacks(new ItemStack(Items.ELYTRA, 1, 0))), ofs(new ItemStack(ModItems.broom, 1, 1)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		juniper_broom = new RitualImpl(rl("juniper_broom"), of(Ingredient.fromStacks(new ItemStack(ModBlocks.log_juniper, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.broom, 1, 0)), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 1)), Ingredient.fromStacks(new ItemStack(ModItems.magic_salve, 1, 0)), Ingredient.fromStacks(new ItemStack(Items.ELYTRA, 1, 0))), ofs(new ItemStack(ModItems.broom, 1, 2)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		yew_broom = new RitualImpl(rl("yew_broom"), of(Ingredient.fromStacks(new ItemStack(ModBlocks.log_yew, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.broom, 1, 0)), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 2)), Ingredient.fromStacks(new ItemStack(ModItems.magic_salve, 1, 0)), Ingredient.fromStacks(new ItemStack(Items.ELYTRA, 1, 0))), ofs(new ItemStack(ModItems.broom, 1, 3)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		cypress_broom = new RitualImpl(rl("cypress_broom"), of(Ingredient.fromStacks(new ItemStack(ModBlocks.log_cypress, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.broom, 1, 0)), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 3)), Ingredient.fromStacks(new ItemStack(ModItems.magic_salve, 1, 0)), Ingredient.fromStacks(new ItemStack(Items.ELYTRA, 1, 0))), ofs(new ItemStack(ModItems.broom, 1, 4)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		registerAll();
	}

	public static void registerAll() {
		Arrays.asList(night, fast_day, glowing, spawn_witch, spawn_wither, 
				draw_circle_large, draw_circle_medium, draw_circle_small, 
				infusion_overworld, infusion_nether, infusion_end, infusion_dream, 
				flames, sanctuary, spawn_vex, deck, table, crystal_ball, elder_broom, 
				juniper_broom, yew_broom, cypress_broom, gateway
				
				
				
				).stream().map(r -> new AdapterIRitual(r)).forEach(r -> AdapterIRitual.REGISTRY.register(r));

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

	public static int circles(EnumGlyphType small, EnumGlyphType medium, EnumGlyphType big) {
		if (small == null) throw new IllegalArgumentException("Cannot have the smaller circle missing");
		if (medium == null && big != null)
			throw new IllegalArgumentException("Cannot have null middle circle when a big circle is present");
		if (small == EnumGlyphType.GOLDEN || medium == EnumGlyphType.GOLDEN || big == EnumGlyphType.GOLDEN)
			throw new IllegalArgumentException("No golden circles allowed");
		int circleNum = 0;
		if (medium != null) circleNum++;
		if (big != null) circleNum++;
		return circleNum | small.meta() << 2 | (medium == null ? 0 : medium.meta() << 4) | (big == null ? 0 : big.meta() << 6);
	}
}
