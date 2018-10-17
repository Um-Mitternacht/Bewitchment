package com.bewitchment.common.content.ritual;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.ritual.rituals.*;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibIngredients;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.tiles.TileEntityGlyph;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

import static com.bewitchment.api.ritual.EnumGlyphType.*;

public class ModRituals {

	private static final NonNullList<ItemStack> none = NonNullList.<ItemStack>create();

	public static RitualImpl night, fast_day, glowing, spawn_witch, spawn_wither,
			draw_circle_small, draw_circle_medium, draw_circle_large, infusion_overworld,
			infusion_nether, infusion_end, infusion_dream, flames, sanctuary, spawn_vex,
			deck, table, crystal_ball, elder_broom, juniper_broom, yew_broom, cypress_broom, gateway,
			nether_portal, spawn_blaze, spawn_ghast, spawn_magma_cube, shift_biome, vampire_lair;

	public static void init() {

		night = new RitualHighMoon(
				rl("high_moon"), //Reg name
				of( //Recipe
						LibIngredients.goldIngot,
						LibIngredients.netherBrickItem
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
						LibIngredients.sand,
						LibIngredients.diamondOre
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
						LibIngredients.glowstoneBlock,
						LibIngredients.goldenCarrot
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
						LibIngredients.athame,
						LibIngredients.apple,
						LibIngredients.pentacle,
						LibIngredients.poisonousPotato
				),
				ofs(new ItemStack(ModItems.athame)),
				200,
				circles(NETHER, ENDER, NETHER),
				3000,
				3
		);

		spawn_magma_cube = new RitualConjurationMagmaCube(
				rl("conjure_magma_cube"),
				of(
						LibIngredients.athame,
						LibIngredients.blazePowder,
						LibIngredients.slime
				),
				ofs(new ItemStack(ModItems.athame)),
				120,
				circles(NETHER, NETHER, null),
				1200,
				2
		);

		spawn_vex = new RitualConjurationVex(
				rl("conjure_vex"),
				of(
						LibIngredients.apple,
						LibIngredients.wormwood,
						LibIngredients.athame
				),
				ofs(new ItemStack(ModItems.athame)),
				100,
				circles(NORMAL, ENDER, null),
				1000,
				2
		);

		spawn_blaze = new RitualConjurationBlaze(
				rl("conjure_blaze"),
				of(
						LibIngredients.anyLog,
						LibIngredients.netherBrickItem,
						LibIngredients.athame
				),
				ofs(new ItemStack(ModItems.athame)),
				120,
				circles(NETHER, NETHER, null),
				1200,
				2
		);

		spawn_ghast = new RitualConjurationGhast(
				rl("conjure_ghast"),
				of(
						LibIngredients.soulSand,
						LibIngredients.soulSand,
						LibIngredients.soulSand,
						LibIngredients.ectoplasm,
						LibIngredients.fumeReekOfDeath,
						LibIngredients.glowstoneDust,
						LibIngredients.athame
				),
				ofs(new ItemStack(ModItems.athame)),
				250,
				circles(NETHER, NETHER, NETHER),
				3400,
				2
		);

		spawn_wither = new RitualConjurationWither(
				rl("conjure_wither"),
				of(
						LibIngredients.athame,
						LibIngredients.witherSkull,
						LibIngredients.soulSand

				),
				ofs(new ItemStack(ModItems.athame)),
				400,
				circles(NETHER, NETHER, NETHER),
				5000,
				4);
		draw_circle_small = new RitualDrawing(
				rl("draw_circle_small"),
				of(
						LibIngredients.woodAsh
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
						LibIngredients.woodAsh,
						LibIngredients.clayBall // balanced
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
						LibIngredients.woodAsh,
						LibIngredients.woodAsh,
						LibIngredients.clayBall,
						LibIngredients.clayBall // VERY balanced
				),
				none,
				40,
				circles(ANY, ANY, null),
				100,
				0,
				TileEntityGlyph.big);
		gateway = new RitualGateway(rl("gateway"), of(LibIngredients.locationStone), ofs(), -1, circles(ENDER, NORMAL, ENDER), 4000, 8);
		nether_portal = new RitualNetherPortal(rl("nether_portal"), of(LibIngredients.obsidian, LibIngredients.obsidian, LibIngredients.obsidian, LibIngredients.obsidian, LibIngredients.fire_charge), ofs(), 200, circles(NETHER, null, null), 4000, 1);

		shift_biome = new RitualBiomeShift(rl("shift_biome"), of(LibIngredients.anyGlass, LibIngredients.boline), ofs(new ItemStack(ModItems.boline)), 400, circles(NORMAL, NORMAL, NORMAL), 2000, 8);

		ResourceLocation infusions = new ResourceLocation(LibMod.MOD_ID, "infusion");
		infusion_overworld = new RitualInfusion(infusions, of(LibIngredients.fumePetrichorOdour), none, 60, circles(NORMAL, NORMAL, NORMAL), 6000, 1, DefaultInfusions.OVERWORLD);
		infusion_nether = new RitualInfusion(infusions, of(LibIngredients.fumeFieryBreeze), none, 60, circles(NETHER, NETHER, NETHER), 6000, 1, DefaultInfusions.NETHER);
		infusion_end = new RitualInfusion(infusions, of(LibIngredients.fumeHeavenlyWind), none, 60, circles(ENDER, ENDER, ENDER), 6000, 1, DefaultInfusions.END);
		infusion_dream = new RitualInfusion(infusions, of(LibIngredients.fumeZephyrOfDepths), none, 60, circles(NORMAL, NETHER, ENDER), 6000, 1, DefaultInfusions.DREAM);
		flames = new RitualFlames(rl("flames"), of(LibIngredients.blazeRod, LibIngredients.coal), none, 3600, circles(NETHER, null, null), 300, 4);
		sanctuary = new RitualImpl(rl("sanctuary"), of(LibIngredients.whiteSage, LibIngredients.sagebrush, LibIngredients.salt, LibIngredients.dirt, LibIngredients.dirt, LibIngredients.dirt), ofs(new ItemStack(ModBlocks.purifying_earth, 3)), 130, circles(NORMAL, NORMAL, null), 500, 4);
		deck = new RitualImpl(rl("deck"), of(LibIngredients.anyDye, LibIngredients.anyDye, LibIngredients.paper, LibIngredients.fumeBirchSoul, LibIngredients.wax), ofs(new ItemStack(ModItems.tarots)), 50, circles(NORMAL, null, null), 350, 1);
		table = new RitualImpl(rl("table"), of(LibIngredients.anyString, LibIngredients.anyDye, LibIngredients.craftingTable, Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 20)), LibIngredients.fumeDropletOfWisdom, LibIngredients.fumeDropletOfWisdom), ofs(new ItemStack(ModBlocks.tarot_table)), 50, circles(NORMAL, NORMAL, null), 350, 1);
		crystal_ball = new RitualImpl(rl("crystal_ball"), of(LibIngredients.quartz, LibIngredients.anyGlass, LibIngredients.anyGlass, LibIngredients.anyGlass, LibIngredients.anyGlass, LibIngredients.fumeBottledMagic), ofs(new ItemStack(ModBlocks.crystal_ball)), 50, circles(NORMAL, ENDER, null), 750, 3);
		elder_broom = new RitualImpl(rl("elder_broom"), of(LibIngredients.logElder, LibIngredients.broomMundane, Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 0)), LibIngredients.magicSalve, LibIngredients.elytra), ofs(new ItemStack(ModItems.broom, 1, 1)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		juniper_broom = new RitualImpl(rl("juniper_broom"), of(LibIngredients.logJuniper, LibIngredients.broomMundane, Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 1)), LibIngredients.magicSalve, LibIngredients.elytra), ofs(new ItemStack(ModItems.broom, 1, 2)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		yew_broom = new RitualImpl(rl("yew_broom"), of(LibIngredients.logYew, LibIngredients.broomMundane, Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 2)), LibIngredients.magicSalve, LibIngredients.elytra), ofs(new ItemStack(ModItems.broom, 1, 3)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		cypress_broom = new RitualImpl(rl("cypress_broom"), of(LibIngredients.logCypress, LibIngredients.broomMundane, Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 3)), LibIngredients.magicSalve, LibIngredients.elytra), ofs(new ItemStack(ModItems.broom, 1, 4)), 130, circles(NORMAL, NORMAL, ENDER), 1000, 4);
		vampire_lair = new RitualCreateVampireLair(rl("vampire_lair"), of(LibIngredients.bloodyRags, LibIngredients.bloodyRags, LibIngredients.anySapling, LibIngredients.blazePowder, LibIngredients.boline), ofs(), 200, circles(NORMAL, NETHER, NETHER), 5000, 5);
		registerAll();
	}

	public static void registerAll() {
		Arrays.asList(night, fast_day, glowing, spawn_witch, spawn_wither,
				draw_circle_large, draw_circle_medium, draw_circle_small,
				infusion_overworld, infusion_nether, infusion_end, infusion_dream,
				flames, sanctuary, spawn_vex, deck, table, crystal_ball, elder_broom,
				juniper_broom, yew_broom, cypress_broom, gateway, nether_portal, spawn_blaze,
				spawn_ghast, spawn_magma_cube, shift_biome, vampire_lair


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
