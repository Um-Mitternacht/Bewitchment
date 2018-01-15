package com.bewitchment.common.crafting.cauldron;

import com.bewitchment.api.BrewRegistry;
import com.bewitchment.api.CauldronRegistry;
import com.bewitchment.api.RitualRegistry;
import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.BrewUtils;
import com.bewitchment.api.cauldron_ritual.ICauldronRitual;
import com.bewitchment.api.recipe.BrewModifier;
import com.bewitchment.api.recipe.BrewSimpleModifier;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.brew.ModBrews;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * This class was created by Arekkuusu on 21/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
public final class CauldronCrafting {

	private CauldronCrafting() {
	}

	public static void init() {
		//------------------------------------Processing------------------------------------//
		//Some recipes that return the non-dyed version of an Item
		registerItemProcess(FluidRegistry.WATER, Items.LEATHER_HELMET, Items.LEATHER_HELMET, false);
		registerItemProcess(FluidRegistry.WATER, Items.LEATHER_CHESTPLATE, Items.LEATHER_CHESTPLATE, false);
		registerItemProcess(FluidRegistry.WATER, Items.LEATHER_LEGGINGS, Items.LEATHER_LEGGINGS, false);
		registerItemProcess(FluidRegistry.WATER, Items.LEATHER_BOOTS, Items.LEATHER_BOOTS, false);
		registerItemProcess(FluidRegistry.WATER, Items.SHIELD, Items.SHIELD, false);
		//Cooking with Oil
		registerItemProcess(Fluids.MUNDANE_OIL, Items.PORKCHOP, Items.COOKED_PORKCHOP, true);
		registerItemProcess(Fluids.MUNDANE_OIL, Items.MUTTON, Items.COOKED_MUTTON, true);
		registerItemProcess(Fluids.MUNDANE_OIL, Items.RABBIT, Items.COOKED_RABBIT, true);
		registerItemProcess(Fluids.MUNDANE_OIL, Items.CHICKEN, Items.COOKED_CHICKEN, true);
		registerItemProcess(Fluids.MUNDANE_OIL, Items.BEEF, Items.COOKED_BEEF, true);
		registerItemProcess(Fluids.MUNDANE_OIL, Items.FISH, Items.COOKED_FISH, true);
		registerItemProcess(Fluids.MUNDANE_OIL, Items.POTATO, Items.BAKED_POTATO, true);
		registerItemProcess(Fluids.MUNDANE_OIL, getStack(Items.FISH, 1, 1), getStack(Items.COOKED_FISH, 1, 1), true);
		//Cooking and Processing with Water
		registerItemProcess(FluidRegistry.WATER, ModItems.empty_honeycomb, ModItems.wax, true);
		registerItemProcess(FluidRegistry.WATER, ModItems.honeycomb, ModItems.honey, true);
		registerItemProcess(FluidRegistry.WATER, ModItems.hoof, Items.SLIME_BALL, true);
		registerItemProcess(FluidRegistry.WATER, getStack(Blocks.LOG2, 1, 0), getStack(ModItems.catechu, 6, 0), true);
		registerItemProcess(FluidRegistry.WATER, getStack(ModItems.wormwood, 1, 0), getStack(ModItems.absinthe_green), true);
		//Banner pattern removal
		for (int i = 0; i < 16; i++) {
			registerItemProcess(FluidRegistry.WATER, getStack(Items.BANNER, 1, i), getStack(Items.BANNER, 1, i), true);
		}

		//------------------------------------Fluid Creation------------------------------------//
		CauldronRegistry.registerFluidIngredient(ModItems.honey, new FluidStack(Fluids.BW_HONEY, 1000));
		CauldronRegistry.registerFluidIngredient(Items.POTATO, new FluidStack(Fluids.MUNDANE_OIL, 1000));

		//------------------------------------Item Rituals------------------------------------//

		//Todo: Better recipe for kelp seeds.
		registerItemRitual("seed_kelp", getStack(ModItems.seed_kelp, 2), 5
				, getStack(Items.WHEAT_SEEDS, 1), Blocks.WATERLILY);

		registerItemRitual("golden_apple", getStack(Items.GOLDEN_APPLE, 1, 1), 8
				, getStack(Blocks.GOLD_BLOCK, 8), Items.APPLE);

		registerItemRitual("cobweb", getStack(Blocks.WEB), 2
				, getStack(Items.STRING, 4), Items.SLIME_BALL);

		registerItemRitual("string", getStack(Items.STRING, 6), 2
				, getStack(ModItems.kenaf, 2));

		registerItemRitual("leather", getStack(Items.LEATHER, 2), 2
				, getStack(Items.ROTTEN_FLESH, 2), ModItems.salt);

		registerItemRitual("wax_from_leather", getStack(ModItems.wax, 2), 2
				, getStack(Items.LEATHER, 2), ModItems.salt);

		registerItemRitual("slime", getStack(Items.SLIME_BALL, 4), 2
				, getStack(Items.DYE, 1, 2), Items.ROTTEN_FLESH, Items.WHEAT, ModItems.salt);

		registerItemRitual("extra_bonemeal", getStack(Items.DYE, 6, 15), 3
				, getStack(Items.BONE, 1));

		registerItemRitual("mycelia", getStack(Blocks.MYCELIUM, 4), 6
				, getStack(Blocks.DIRT, 4), Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Items.SUGAR, Items.FERMENTED_SPIDER_EYE);

		registerItemRitual("prismarine", getStack(Items.PRISMARINE_SHARD, 4), 6
				, getStack(Items.DYE, 8, 4), Blocks.STONE, ModBlocks.coquina);

		registerItemRitual("prismarine_crystals", getStack(Items.PRISMARINE_CRYSTALS, 4), 6
				, getStack(Items.DYE, 8, 4), ModBlocks.coquina, Items.GLOWSTONE_DUST);

		registerItemRitual("vines", getStack(Blocks.VINE, 4), 4
				, getStack(Items.STRING, 4), ModItems.wormwood);

		registerItemRitual("chorus_fruit", getStack(Items.CHORUS_FRUIT, 1), 8
				, getStack(Items.ENDER_EYE, 4), getStack(ModItems.gem, 1, 1), ModItems.mandrake_root, Items.ENDER_PEARL);

		registerItemRitual("grass", getStack(Blocks.GRASS, 4), 4
				, getStack(Blocks.DIRT, 4), Items.WHEAT_SEEDS, ModItems.seed_garlic);

		registerItemRitual("coarse_dirt", getStack(Blocks.DIRT, 4, 1), 2
				, getStack(Blocks.DIRT, 4), ModItems.salt);

		registerItemRitual("podzol", getStack(Blocks.DIRT, 4, 2), 2
				, getStack(Blocks.DIRT, 4), Blocks.LEAVES);

		registerItemRitual("red_sand", getStack(Blocks.SAND, 4, 1), 2
				, getStack(Blocks.SAND, 4));

		registerItemRitual("gemstone_amalgam", getStack(ModItems.gemstone_amalgam, 1, 0), 4
				, getStack(Items.EMERALD, 1, 0), getStack(ModItems.gem, 1, 9), getStack(ModItems.gem, 1, 1));

		registerItemRitual("moldavite", getStack(ModItems.gem, 1, 1), 8
				, getStack(Items.DYE, 1, 2), Blocks.SAND);

		registerItemRitual("alexandrite", getStack(ModItems.gem, 1, 9), 8
				, getStack(Items.DYE, 1, 6), Blocks.SAND);

		registerItemRitual("bloodstone", getStack(ModItems.gem, 1, 5), 8
				, getStack(Items.DYE, 1, 1), Blocks.SAND);

		registerItemRitual("nuummite", getStack(ModItems.gem, 1, 2), 8
				, getStack(Items.DYE, 1, 0), Blocks.SAND);

		registerItemRitual("malachite", getStack(ModItems.gem, 1, 7), 8
				, getStack(Items.DYE, 1, 12), Blocks.SAND);

		registerItemRitual("quartz", getStack(Items.QUARTZ), 8
				, getStack(Items.DYE, 4, 15), Blocks.SAND, Items.GHAST_TEAR);

		registerItemRitual("albedo", getStack(ModItems.albedo, 4, 0), 4
				, getStack(Blocks.STONE, 4), ModItems.white_sage);

		registerItemRitual("torchwood", getStack(ModBlocks.torchwood, 2), 6, getStack(ModItems.fume, 1, 9), Blocks.TORCH, Items.GLOWSTONE_DUST, Blocks.SAPLING);

		registerItemRitual("ember_grass", getStack(ModBlocks.ember_grass, 2), 6, getStack(ModItems.fume, 1, 9), Items.FIRE_CHARGE, Items.BLAZE_POWDER, Blocks.GRASS);

		registerItemRitual("raging_grass", getStack(ModBlocks.raging_grass, 2), 6, getStack(ModItems.fume, 1, 11), Items.NETHER_WART, ModItems.carnivorous_tooth, ModItems.heart, Blocks.GRASS);

		//------------------------------------Brew Recipes------------------------------------//
		registerBrewRecipe(BrewRegistry.Brew.LINGER, new BrewEffect(ModBrews.MARS_WATER, 500, 0)
				, getStack(Items.IRON_NUGGET, 6), Items.POISONOUS_POTATO, Items.ROTTEN_FLESH, ModItems.salt, getStack(ModItems.gem, 1, 6));

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 0)
				, getStack(Items.DYE, 1, 0), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 1)
				, getStack(Items.DYE, 1, 1), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 2)
				, getStack(Items.DYE, 1, 2), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 3)
				, getStack(Items.DYE, 1, 3), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 4)
				, getStack(Items.DYE, 1, 4), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 5)
				, getStack(Items.DYE, 1, 5), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 6)
				, getStack(Items.DYE, 1, 6), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 7)
				, getStack(Items.DYE, 1, 7), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 8)
				, getStack(Items.DYE, 1, 8), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 9)
				, getStack(Items.DYE, 1, 9), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 10)
				, getStack(Items.DYE, 1, 10), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 11)
				, getStack(Items.DYE, 1, 11), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 12)
				, getStack(Items.DYE, 1, 12), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 13)
				, getStack(Items.DYE, 1, 13), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 14)
				, getStack(Items.DYE, 1, 14), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 15)
				, getStack(Items.DYE, 1, 15), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.SKIN_TINT, 500, 3)
				, getStack(ModItems.catechu, 1, 0), Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.OVERCOAT, 2500, 0)
				, getStack(ModItems.wool_of_bat, 1, 0), ModItems.tongue_of_dog, ModItems.silver_scales, ModItems.tulsi, ModItems.dimensional_sand, Items.IRON_NUGGET, Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.DRINK, new BrewEffect(ModBrews.ABSENCE, 1, 0)
				, getStack(ModItems.salt, 1, 0), Items.BONE, Items.IRON_NUGGET, Items.NETHER_WART);

		registerBrewRecipe(BrewRegistry.Brew.SPLASH, new BrewEffect(ModBrews.ABSENCE, 1, 0)
				, getStack(ModItems.salt, 1, 0), Items.BONE, Items.IRON_NUGGET, Items.NETHER_WART, Items.GUNPOWDER);

		registerBrewRecipe(BrewRegistry.Brew.LINGER, new BrewEffect(ModBrews.ABSENCE, 50, 0)
				, getStack(ModItems.salt, 1, 0), Items.BONE, Items.IRON_NUGGET, Items.NETHER_WART, Items.DRAGON_BREATH);


		//------------------------------------Custom Brew Creation------------------------------------//
		registerEffect(getStack(Items.DYE, 1, 0)
				, new PotionEffect(MobEffects.BLINDNESS, 500), true);

		registerEffect(getStack(Items.ROTTEN_FLESH)
				, new PotionEffect(MobEffects.HUNGER, 500), false);

		registerEffect(getStack(Items.SPIDER_EYE)
				, new PotionEffect(MobEffects.POISON, 500), false);

		registerEffect(getStack(Items.GHAST_TEAR)
				, new PotionEffect(MobEffects.REGENERATION, 500), false);

		registerEffect(getStack(Items.GOLDEN_CARROT)
				, new PotionEffect(MobEffects.NIGHT_VISION, 500), false);

		registerEffect(getStack(Items.SUGAR)
				, new PotionEffect(MobEffects.SPEED, 500), false);

		registerEffect(getStack(Items.MAGMA_CREAM)
				, new PotionEffect(MobEffects.FIRE_RESISTANCE, 500), false);

		registerEffect(getStack(Items.BLAZE_POWDER)
				, new PotionEffect(MobEffects.STRENGTH, 500), false);

		registerEffect(getStack(Items.RABBIT_FOOT)
				, new PotionEffect(MobEffects.JUMP_BOOST, 500), false);

		registerEffect(getStack(ModItems.bee)
				, new PotionEffect(MobEffects.INSTANT_DAMAGE, 1), false);

		registerEffect(getStack(Items.SPECKLED_MELON)
				, new PotionEffect(MobEffects.INSTANT_HEALTH, 1), false);

		registerEffect(getStack(Items.PUMPKIN_PIE)
				, new PotionEffect(MobEffects.SATURATION, 10), false);

		registerEffect(getStack(Items.FISH, 1, 3)
				, new PotionEffect(MobEffects.WATER_BREATHING, 500), true);

		registerEffect(getStack(Blocks.RED_FLOWER, 1, 1)
				, new PotionEffect(MobEffects.LUCK, 500), true);

		registerEffect(getStack(Items.FERMENTED_SPIDER_EYE)
				, new PotionEffect(MobEffects.WEAKNESS, 500), false);

		registerEffect(getStack(Items.POISONOUS_POTATO)
				, new PotionEffect(MobEffects.NAUSEA, 500), false);

		registerEffect(getStack(ModItems.belladonna)
				, new PotionEffect(MobEffects.WITHER, 500), false);

		registerEffect(getStack(ModItems.asphodel)
				, new PotionEffect(MobEffects.UNLUCK, 500), false);

		registerEffect(getStack(ModItems.lavender)
				, new PotionEffect(MobEffects.HASTE, 500), false);

		registerEffect(getStack(Items.PRISMARINE_CRYSTALS)
				, new PotionEffect(MobEffects.GLOWING, 500), false);

		registerEffect(getStack(Items.PRISMARINE_SHARD)
				, new PotionEffect(MobEffects.MINING_FATIGUE, 500), false);

		registerEffect(getStack(Items.SHULKER_SHELL)
				, new PotionEffect(MobEffects.LEVITATION, 500), false);

		registerEffect(getStack(Items.NETHER_STAR)
				, new PotionEffect(MobEffects.RESISTANCE, 500), false);

		registerEffect(getStack(ModBlocks.coquina)
				, BrewRegistry.getDefault(ModBrews.SHELL_ARMOR), false);

		registerEffect(getStack(ModItems.thistle)
				, new PotionEffect(MobEffects.HEALTH_BOOST, 150), false);

		registerEffect(getStack(Items.GOLDEN_APPLE)
				, new PotionEffect(MobEffects.ABSORPTION, 150), true);

		registerEffect(getStack(ModItems.heart)
				, new PotionEffect(MobEffects.RESISTANCE, 150), false);

		registerEffect(getStack(ModItems.mint)
				, BrewRegistry.getDefault(ModBrews.EXTINGUISH_FIRES), false);

		registerEffect(getStack(ModItems.white_sage)
				, BrewRegistry.getDefault(ModBrews.HOLY_WATER), false);

		registerEffect(getStack(Blocks.WEB)
				, BrewRegistry.getDefault(ModBrews.SPIDER_NIGHTMARE), false);

		registerEffect(getStack(Items.SNOWBALL)
				, BrewRegistry.getDefault(ModBrews.FROSTBITE), false);

		registerEffect(getStack(Blocks.TNT)
				, BrewRegistry.getDefault(ModBrews.VOLATILE), false);

		registerEffect(getStack(ModItems.aconitum)
				, BrewRegistry.getDefault(ModBrews.WOLFSBANE), false);

		registerEffect(getStack(ModItems.wormwood)
				, BrewRegistry.getDefault(ModBrews.BANE_ARTHROPODS), false);

		registerEffect(getStack(ModItems.kelp)
				, BrewRegistry.getDefault(ModBrews.PATH_OF_THE_DEEP), false);

		registerEffect(getStack(ModItems.silphium)
				, BrewRegistry.getDefault(ModBrews.GROW_FLOWER), false);

		registerEffect(getStack(ModItems.seed_silphium)
				, BrewRegistry.getDefault(ModBrews.HARVEST), false);

		registerEffect(getStack(ModItems.dimensional_sand)
				, BrewRegistry.getDefault(ModBrews.ENDER_INHIBITION), false);

		registerEffect(getStack(ModItems.gem, 1, 8)
				, BrewRegistry.getDefault(ModBrews.TILL_LAND), true);

		registerEffect(getStack(ModItems.gem, 1, 6)
				, BrewRegistry.getDefault(ModBrews.ROCK_PULVERIZE), true);

		registerEffect(getStack(Items.DYE, 1, 15)
				, BrewRegistry.getDefault(ModBrews.FERTILIZE), false);

		registerEffect(getStack(Blocks.PACKED_ICE)
				, BrewRegistry.getDefault(ModBrews.SNOW_TRAIL), false);

		registerEffect(getStack(Items.IRON_NUGGET)
				, BrewRegistry.getDefault(ModBrews.SINKING), false);

		registerEffect(getStack(Blocks.BROWN_MUSHROOM)
				, BrewRegistry.getDefault(ModBrews.PRUNE_LEAVES), false);

		registerEffect(getStack(Blocks.RED_MUSHROOM)
				, BrewRegistry.getDefault(ModBrews.AUTO_PLANT), false);

		registerEffect(getStack(ModItems.ginger)
				, BrewRegistry.getDefault(ModBrews.IGNITION), false);

		registerEffect(getStack(ModItems.carnivorous_tooth)
				, BrewRegistry.getDefault(ModBrews.OUTCASTS_SHAME), false);

		registerEffect(getStack(ModItems.wool_of_bat)
				, BrewRegistry.getDefault(ModBrews.GRACE), false);

		registerEffect(getStack(ModItems.tulsi)
				, BrewRegistry.getDefault(ModBrews.PURIFY), false);

		registerEffect(getStack(Items.GOLDEN_APPLE, 1, 1)
				, BrewRegistry.getDefault(ModBrews.NOTCHED), true);

		registerEffect(getStack(Blocks.MYCELIUM)
				, BrewRegistry.getDefault(ModBrews.MYCOLOGICAL_CORRUPTION), false);

		registerEffect(getStack(Blocks.GRASS)
				, BrewRegistry.getDefault(ModBrews.GROWTH), false);

		registerEffect(getStack(Blocks.MOSSY_COBBLESTONE)
				, BrewRegistry.getDefault(ModBrews.OZYMANDIAS), false);

		registerEffect(getStack(Blocks.RED_NETHER_BRICK)
				, BrewRegistry.getDefault(ModBrews.HELLS_WROTH), false);

		registerEffect(getStack(Blocks.SAND, 1, 1)
				, BrewRegistry.getDefault(ModBrews.SETEHS_WASTES), false);

		registerEffect(getStack(ModBlocks.nethersteel)
				, BrewRegistry.getDefault(ModBrews.HELL_WORLD), false);

		registerEffect(getStack(ModItems.seed_mint)
				, BrewRegistry.getDefault(ModBrews.ICE_WORLD), false);

		registerEffect(getStack(Items.CHORUS_FRUIT)
				, BrewRegistry.getDefault(ModBrews.CURSED_LEAPING), false);

		registerEffect(getStack(Items.BONE)
				, BrewRegistry.getDefault(ModBrews.CORRUPTION), false);

		registerEffect(getStack(ModItems.salt)
				, BrewRegistry.getDefault(ModBrews.SALT_LAND), false);

		registerEffect(getStack(ModItems.silver_scales)
				, BrewRegistry.getDefault(ModBrews.BULLETPROOF), false);

		registerEffect(getStack(ModItems.tongue_of_dog)
				, BrewRegistry.getDefault(ModBrews.ROTTING), false);

		registerEffect(getStack(ModItems.wax)
				, BrewRegistry.getDefault(ModBrews.DISROBING), false);

		registerEffect(getStack(ModItems.fume, 1, 9)
				, BrewRegistry.getDefault(ModBrews.ARROW_DEFLECTION), false);

		registerEffect(getStack(ModItems.oak_apple_gall)
				, new PotionEffect(MobEffects.SLOWNESS, 500), false);

		//temp effect until i figure out what to make for a brew regarding this
		registerEffect(getStack(ModItems.kenaf)
				, new PotionEffect(MobEffects.SLOWNESS, 500), false);


		//Time Extenders
		registerModifier(getStack(Items.REDSTONE)
				, new BrewSimpleModifier(600, 0), true);

		registerModifier(getStack(Blocks.REDSTONE_BLOCK)
				, new BrewSimpleModifier(1200, 0), true);

		registerModifier(getStack(Items.QUARTZ)
				, new BrewSimpleModifier(2400, 0), true);

		//Amplifiers
		registerModifier(getStack(Items.GLOWSTONE_DUST)
				, new BrewSimpleModifier(0, 1), true);

		registerModifier(getStack(Blocks.GLOWSTONE)
				, new BrewSimpleModifier(0, 2), true);

		registerModifier(getStack(ModItems.gem, 1, 2)
				, new BrewSimpleModifier(0, 3), true);

		//Amplitude Decreasers
		//Todo: Create gemstone powders, and add tourmaline powder as a tier 1 amplitude decreaser.
		registerModifier(getStack(ModItems.gem, 1, 4)
				, new BrewSimpleModifier(0, -2), true);

		registerModifier(getStack(ModBlocks.tourmaline_block, 1, 0)
				, new BrewSimpleModifier(0, -3), true);

		//Time Decreasers
		registerModifier(getStack(Items.COAL)
				, new BrewSimpleModifier(-600, 0), true);

		registerModifier(getStack(Items.COAL, 1, 1)
				, new BrewSimpleModifier(-1200, 0), true);

		registerModifier(getStack(Blocks.COAL_BLOCK)
				, new BrewSimpleModifier(-2400, 0), true);

		//Food Values
		registerFood(Items.APPLE, 4, 2.4f);
		registerFood(Items.BAKED_POTATO, 5, 6f);
		registerFood(Items.BEETROOT, 1, 1.2f);
		registerFood(Items.BREAD, 5, 6f);
		registerFood(Items.CARROT, 3, 3.6f);
		registerFood(Items.CHORUS_FRUIT, 4, 2.4f);
		registerFood(Items.FISH, 2, 0.4f);
		registerFood(Items.COOKED_CHICKEN, 6, 7.2f);
		registerFood(Items.COOKED_FISH, 5, 6f);
		registerFood(Items.COOKED_MUTTON, 6, 9.6f);
		registerFood(Items.COOKED_PORKCHOP, 8, 12.8f);
		registerFood(Items.COOKED_RABBIT, 5, 6f);
		registerFood(Items.GOLDEN_APPLE, 4, 9.6f);
		registerFood(Items.GOLDEN_CARROT, 6, 14.4f);
		registerFood(Items.MELON, 2, 1.2f);
		registerFood(Items.POTATO, 1, 0.6f);
		registerFood(Items.BEEF, 3, 1.8f);
		registerFood(Items.CHICKEN, 2, 1.2f);
		registerFood(Items.MUTTON, 2, 1.2f);
		registerFood(Items.RABBIT, 3, 1.8f);

	}

	private static void registerItemProcess(Fluid fluid, Item in, Item out, boolean perfectMatch) {
		CauldronRegistry.registerItemProcessing(fluid, new ItemStack(in), new ItemStack(out), perfectMatch);
	}

	private static void registerItemProcess(Fluid fluid, ItemStack in, ItemStack out, boolean perfectMatch) {
		CauldronRegistry.registerItemProcessing(fluid, in, out, perfectMatch);
	}

	/**
	 * Who needs to write the whole thing?
	 *
	 * @param item The block to make an ItemStack out of
	 * @param size Size of ItemStack
	 * @param meta Meta of ItemStack
	 * @return An ItemStack
	 */
	private static ItemStack getStack(Item item, int size, int meta) {
		return new ItemStack(item, size, meta);
	}

	@SuppressWarnings("rawtypes")
	private static void registerItemRitual(String name, ItemStack spawned, int cost, Object... needed) {
		ICauldronRitual ritual = RitualRegistry.register(new ResourceLocation(LibMod.MOD_ID, name), new ItemRitual(spawned, cost));
		CauldronRegistry.registerItemRitual((ItemRitual) ritual, needed);
	}

	/**
	 * Who needs to write the whole thing?
	 *
	 * @param item The block to make an ItemStack out of
	 * @param size Size of ItemStack
	 * @return An ItemStack
	 */
	private static ItemStack getStack(Item item, int size) {
		return new ItemStack(item, size, 0);
	}

	/**
	 * Who needs to write the whole thing?
	 *
	 * @param block The block to make an ItemStack out of
	 * @param size  Size of ItemStack
	 * @return An ItemStack
	 */
	@SuppressWarnings("ConstantConditions")
	private static ItemStack getStack(Block block, int size) {
		return getStack(Item.getItemFromBlock(block), size, 0);
	}

	/**
	 * Who needs to write the whole thing?
	 *
	 * @param block The block to make an ItemStack out of
	 * @return An ItemStack
	 */
	@SuppressWarnings("ConstantConditions")
	private static ItemStack getStack(Block block) {
		return getStack(Item.getItemFromBlock(block), 1, 0);
	}

	/**
	 * Who needs to write the whole thing?
	 *
	 * @param item The item to make an ItemStack out of
	 * @return An ItemStack
	 */
	private static ItemStack getStack(Item item) {
		return getStack(item, 1, 0);
	}

	/**
	 * Who needs to write the whole thing?
	 *
	 * @param block The block to make an ItemStack out of
	 * @param size  Size of ItemStack
	 * @param meta  Meta of ItemStack
	 * @return An ItemStack
	 */
	@SuppressWarnings("ConstantConditions")
	private static ItemStack getStack(Block block, int size, int meta) {
		return getStack(Item.getItemFromBlock(block), size, meta);
	}

	private static void registerBrewRecipe(BrewRegistry.Brew brew, BrewEffect effect, Object... needed) {
		CauldronRegistry.registerBrewRecipe(BrewUtils.createBrew(brew, effect), needed);
	}

	private static <T> void registerEffect(ItemStack key, T brew, boolean perfectMatch) {
		CauldronRegistry.registerItemEffect(key, brew, perfectMatch);
	}

	private static void registerModifier(ItemStack key, BrewModifier modifier, boolean perfectMatch) {
		CauldronRegistry.registerItemModifier(key, modifier, perfectMatch);
	}

	private static void registerFood(ItemStack key, int hunger, float saturation) {
		CauldronRegistry.registerFoodValue(key, new CauldronFoodValue(hunger, saturation));
	}

	private static void registerFood(Item key, int hunger, float saturation) {
		registerFood(getStack(key), hunger, saturation);
	}
}
