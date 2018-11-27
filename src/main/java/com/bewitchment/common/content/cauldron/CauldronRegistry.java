package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifier.ModifierResult;
import com.bewitchment.api.cauldron.IBrewModifier.ResultType;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.crafting.util.IngredientMultiOreDict;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.lib.LibIngredients;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import com.google.common.collect.HashBiMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.*;

import static com.bewitchment.common.lib.LibIngredients.*;

public class CauldronRegistry {

	public static final HashBiMap<IBrewEffect, Potion> BREW_POTION_MAP = HashBiMap.<IBrewEffect, Potion>create(90);
	public static final IForgeRegistry<IBrewModifier> BREW_MODIFIERS = new RegistryBuilder<IBrewModifier>().setName(new ResourceLocation(LibMod.MOD_ID, "brew modifiers")).setIDRange(0, 200).setType(IBrewModifier.class).create();
	public static final ArrayList<CauldronCraftingRecipe> CRAFTING_REGISTRY = new ArrayList<>();
	private static final HashMap<Ingredient, CauldronFoodValue> STEW_REGISTRY = new HashMap<>();
	private static final HashMap<Ingredient, IBrewEffect> BREW_INGREDIENT_REGISTRY = new HashMap<>();
	// The less entries an Ingredient has, the higher priority it will be in the list
	private static final Comparator<Map.Entry<Ingredient, CauldronFoodValue>> STEW_INGREDIENT_PRIORITY = Map.Entry.<Ingredient, CauldronFoodValue>comparingByKey(Comparator.comparing(i -> i.getMatchingStacks().length)).reversed();

	public static void registerFoodValue(Ingredient ingredient, CauldronFoodValue value) {
		if (ingredient.getMatchingStacks().length > 0) {
			STEW_REGISTRY.put(ingredient, value);
		}
	}

	public static void bindPotionAndEffect(IBrewEffect effect, Potion potion) {
		BREW_POTION_MAP.put(effect, potion);
	}

	public static void registerBrewModifier(IBrewModifier modifier) {
		BREW_MODIFIERS.register(modifier);
	}

	public static void registerBrewIngredient(IBrewEffect effect, Ingredient ingredient) {
		BREW_INGREDIENT_REGISTRY.put(ingredient, effect);
	}

	public static void registerCauldronItemCrafting(Fluid fluid, ItemStack output, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronItemCraftingRecipe(fluid, Fluid.BUCKET_VOLUME / 4, output, ingredients));
	}

	public static void registerCauldronItemCrafting(Fluid fluid, int fluidAmount, ItemStack output, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronItemCraftingRecipe(fluid, fluidAmount, output, ingredients));
	}

	public static void registerCauldronFluidCrafting(Fluid fluid, FluidStack output, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronFluidCraftingRecipe(fluid, Fluid.BUCKET_VOLUME, output, ingredients));
	}

	public static void registerCauldronFluidCrafting(Fluid fluid, int exactFluidAmount, FluidStack output, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronFluidCraftingRecipe(fluid, exactFluidAmount, output, ingredients));
	}

	public static void registerCauldronMixedCrafting(Fluid fluid, FluidStack fluidOutput, ItemStack itemOutput, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronMixedCraftingRecipe(fluid, Fluid.BUCKET_VOLUME, fluidOutput, itemOutput, ingredients));
	}

	public static void registerCauldronMixedCrafting(Fluid fluid, int exactFluidAmount, FluidStack fluidOutput, ItemStack itemOutput, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronMixedCraftingRecipe(fluid, exactFluidAmount, fluidOutput, itemOutput, ingredients));
	}

	public static CauldronFoodValue getCauldronFoodValue(ItemStack stack) {
		return STEW_REGISTRY.entrySet().stream().filter(e -> e.getKey().apply(stack)).sorted(STEW_INGREDIENT_PRIORITY).map(e -> e.getValue()).findFirst().orElse(null);
	}

	public static Optional<CauldronCraftingRecipe> getCraftingResult(FluidStack fluid, List<ItemStack> stacks) {
		return CRAFTING_REGISTRY.stream().filter(r -> r.matches(stacks, fluid)).findFirst();
	}

	public static Potion getPotionFromBrew(IBrewEffect effect) {
		return BREW_POTION_MAP.get(effect);
	}

	public static IBrewEffect getBrewFromPotion(Potion potion) {
		if (potion == null) {
			throw new NullPointerException("The potion parameter cannot be null");
		}
		IBrewEffect effect = BREW_POTION_MAP.inverse().get(potion);
		if (effect == null) {
			throw new NoSuchElementException(potion.getName() + " has no associated IBrewEffect");
		}
		return effect;
	}

	public static Optional<IBrewModifierList> getModifierListFromStack(ItemStack stack, IBrewModifierList currentList, IBrewEffect currentEffect) {
		for (IBrewModifier bm : BREW_MODIFIERS) {
			if (bm.canApply(currentEffect)) {
				ModifierResult mr = bm.acceptIngredient(currentEffect, stack, currentList);
				if (mr.getResult() == ResultType.SUCCESS) {
					BrewModifierListImpl newList = new BrewModifierListImpl(currentList);
					newList.addModifier(bm, mr.getLevel());
					return Optional.of(newList);
				} else if (mr.getResult() == ResultType.FAIL) {
					return null;
				}
			}
		}
		return Optional.empty();
	}

	public static Optional<IBrewEffect> getBrewFromStack(ItemStack stack) {
		for (Ingredient i : BREW_INGREDIENT_REGISTRY.keySet()) {
			if (i.apply(stack)) {
				return Optional.ofNullable(BREW_INGREDIENT_REGISTRY.get(i));
			}
		}
		return Optional.empty();
	}

	public static Optional<Ingredient> getIngredientFromBrew(IBrewEffect effect) {
		return BREW_INGREDIENT_REGISTRY.keySet().parallelStream()
				.filter(ing -> BREW_INGREDIENT_REGISTRY.get(ing) == effect)
				.findFirst();
	}

	public static void init() { // TODO: Tune values. Also call for oredicts where possible. Such things have been marked with todos.

		Ingredient salt = new IngredientMultiOreDict("salt", "itemSalt", "dustSalt", "foodSalt", "listAllSalt", "ingredientSalt", "pinchSalt", "portionSalt", "lumpSalt");

		registerFood(Ingredient.fromItem(Items.APPLE), 4, 2.4f);
		registerFood(Ingredient.fromItem(Items.BAKED_POTATO), 5, 6f);
		registerFood(Ingredient.fromItem(Items.BEETROOT), 1, 1.2f);
		registerFood(Ingredient.fromItem(Items.BREAD), 5, 6f);
		registerFood(Ingredient.fromItem(Items.CARROT), 3, 3.6f);
		registerFood(Ingredient.fromItem(Items.FISH), 2, 0.4f);
		registerFood(Ingredient.fromItem(Items.COOKED_CHICKEN), 6, 7.2f);
		registerFood(Ingredient.fromItem(Items.COOKED_FISH), 5, 6f);
		registerFood(Ingredient.fromItem(Items.COOKED_MUTTON), 6, 9.6f);
		registerFood(Ingredient.fromItem(Items.COOKED_PORKCHOP), 8, 12.8f);
		registerFood(Ingredient.fromItem(Items.COOKED_BEEF), 7, 12.8f);
		registerFood(Ingredient.fromItem(Items.COOKED_RABBIT), 5, 6f);
		registerFood(Ingredient.fromItem(Items.SPECKLED_MELON), 3, 7.6f);
		registerFood(Ingredient.fromItem(Items.GOLDEN_APPLE), 4, 9.6f);
		registerFood(Ingredient.fromItem(Items.GOLDEN_CARROT), 6, 14.4f);
		registerFood(Ingredient.fromItem(Items.MELON), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.POTATO), 1, 0.6f);
		registerFood(Ingredient.fromItem(Items.BEEF), 3, 1.8f);
		registerFood(Ingredient.fromItem(Items.CHICKEN), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.MUTTON), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.RABBIT), 3, 1.8f);
		registerFood(Ingredient.fromItem(Items.WHEAT_SEEDS), 2, 1f);
		registerFood(Ingredient.fromItem(Items.PUMPKIN_SEEDS), 2, 1.3f);
		registerFood(Ingredient.fromItem(Items.MELON_SEEDS), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.BEETROOT_SEEDS), 2, 1.1f);
		registerFood(new IngredientMultiOreDict("egg", "foodSimpleEgg", "ingredientEgg", "listAllegg", "bakingEgg"), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.SUGAR), 1, 0.5f);
		registerFood(new IngredientMultiOreDict("cropGarlic"), 2, 0.8f);
		registerFood(Ingredient.fromItem(ModItems.silphium), 3, 1.3f);
		registerFood(new IngredientMultiOreDict("cropSeaweed", "cropKelp"), 4, 3.3f);
		registerFood(Ingredient.fromItem(ModItems.salt), 1, 0.5f);
		registerFood(Ingredient.fromItem(ModItems.mint), 1, 0.7f);
		registerFood(Ingredient.fromItem(ModItems.tulsi), 1, 0.7f);
		registerFood(new IngredientMultiOreDict("cropGinger"), 3, 0.9f);
		registerFood(Ingredient.fromItem(ModItems.lavender), 1, 0.6f);
		registerFood(wormwood, 1, 0.8f);
		registerFood(Ingredient.fromItem(ModItems.white_sage), 2, 0.9f);
		registerFood(new IngredientMultiOreDict("dropHoney", "honeyDrop", "foodHoneydrop"), 2, 1.3f);
		registerFood(salt, 1, 0.5f);
		registerFood(Ingredient.fromItem(ModItems.heart), 6, 6.6f);
		registerFood(Ingredient.fromItem(ModItems.tongue_of_dog), 4, 4.4f);
		registerFood(Ingredient.fromItem(Items.ROTTEN_FLESH), 2, 1.4f);
		registerFood(new IngredientMultiOreDict("listAllvenisoncooked"), 7, 11.9f);
		registerFood(new IngredientMultiOreDict("listAllturkeycooked"), 6, 8.3f);
		registerFood(new IngredientMultiOreDict("listAllbeefcooked"), 7, 12.8f);
		registerFood(new IngredientMultiOreDict("listAllchickencooked"), 6, 7.2f);
		registerFood(new IngredientMultiOreDict("listAllporkcooked"), 8, 12.8f);
		registerFood(new IngredientMultiOreDict("listAllmuttoncooked"), 6, 9.6f);
		registerFood(new IngredientMultiOreDict("listAllrabbitcooked"), 5, 6f);
		registerFood(new IngredientMultiOreDict("listAllfishcooked"), 5, 6f);
		//Todo: Support for more modded foods.

		// Miscellaneous water-based recipes
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(Blocks.PISTON, 1, 0), stickyPiston);
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(Blocks.SPONGE, 1, 1), sponge);
		// Cooking and Processing with Water
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(ModItems.wax), empty_honeycomb);
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(Items.SLIME_BALL), hoof);
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(ModItems.catechu, 3, 0), acaciaLog);
		// Arcane recipes

		registerCauldronItemCrafting(Fluids.BW_HONEY, Fluid.BUCKET_VOLUME, new ItemStack(ModBlocks.goblet, 1, 1), LibIngredients.redstone, LibIngredients.redstone, LibIngredients.redstone, LibIngredients.fumeCloudyOil, LibIngredients.emptyGoblet, LibIngredients.ghastTear);
		registerCauldronItemCrafting(Fluids.MUNDANE_OIL, new ItemStack(ModItems.ritual_chalk, 2, 3), normalRitualChalk, normalRitualChalk, blazePowder, blazePowder, blazePowder, blazePowder, fumeFieryBreeze, fumeFieryBreeze);
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(ModItems.ritual_chalk, 2, 2), normalRitualChalk, normalRitualChalk, dimensionalSand, dimensionalSand, dimensionalSand, dimensionalSand, fumeHeavenlyWind, fumeHeavenlyWind);
		registerCauldronItemCrafting(Fluids.BW_HONEY, new ItemStack(ModItems.ritual_chalk, 2, 1), normalRitualChalk, normalRitualChalk, goldNugget, goldNugget, goldNugget, goldNugget, fumeCleansingAura, fumeCleansingAura);
		registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(ModBlocks.graveyard_dirt, 8, 0), ectoplasm, ectoplasm, graveyardDust, graveyardDust, wormwood, wormwood, dirt, dirt, dirt, dirt);
		registerCauldronItemCrafting(Fluids.MUNDANE_OIL, new ItemStack(ModBlocks.ember_grass, 2, 0), blazePowder, blazePowder, wormwood, wormwood);
		registerCauldronItemCrafting(Fluids.MUNDANE_OIL, new ItemStack(ModBlocks.torchwood, 2, 0), glowstoneDust, glowstoneDust, anyLog, anyLeaf);
		// Banner pattern removal
		for (int i = 0; i < 16; i++) {
			registerCauldronItemCrafting(FluidRegistry.WATER, new ItemStack(Items.BANNER, 1, i), Ingredient.fromStacks(new ItemStack(Items.BANNER, 1, i)));
		}

		registerCauldronMixedCrafting(FluidRegistry.WATER, new FluidStack(Fluids.BW_HONEY, Fluid.BUCKET_VOLUME), new ItemStack(ModItems.empty_honeycomb), Ingredient.fromItem(ModItems.honeycomb));
		registerCauldronFluidCrafting(FluidRegistry.WATER, new FluidStack(Fluids.MUNDANE_OIL, Fluid.BUCKET_VOLUME), Ingredient.fromStacks(new ItemStack(Items.POTATO)), Ingredient.fromStacks(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0)));

		registerVanillaBrewEffect(MobEffects.ABSORPTION, Ingredient.fromStacks(new ItemStack(Items.GOLDEN_APPLE, 1, 0)), 600);
		registerVanillaBrewEffect(MobEffects.FIRE_RESISTANCE, Ingredient.fromItem(Items.MAGMA_CREAM));
		registerVanillaBrewEffect(MobEffects.HUNGER, Ingredient.fromItem(Items.ROTTEN_FLESH), 600);
		registerVanillaBrewEffect(MobEffects.INSTANT_DAMAGE, Ingredient.fromItem(Items.FERMENTED_SPIDER_EYE));
		registerVanillaBrewEffect(MobEffects.INSTANT_HEALTH, Ingredient.fromItem(Items.SPECKLED_MELON));
		registerVanillaBrewEffect(MobEffects.INVISIBILITY, Ingredient.fromItem(Item.getItemFromBlock(Blocks.GLASS)));
		registerVanillaBrewEffect(MobEffects.JUMP_BOOST, Ingredient.fromItem(ModItems.equine_tail));
		registerVanillaBrewEffect(MobEffects.NIGHT_VISION, Ingredient.fromItem(Items.GOLDEN_CARROT));
		registerVanillaBrewEffect(MobEffects.POISON, Ingredient.fromItem(Items.SPIDER_EYE));
		registerVanillaBrewEffect(MobEffects.REGENERATION, Ingredient.fromItem(Items.GHAST_TEAR));
		registerVanillaBrewEffect(MobEffects.SPEED, Ingredient.fromItem(Items.SUGAR));
		registerVanillaBrewEffect(MobEffects.SLOWNESS, Ingredient.fromItem(Items.PAPER));
		registerVanillaBrewEffect(MobEffects.WATER_BREATHING, Ingredient.fromStacks(new ItemStack(Items.FISH, 1, 3)));
		registerVanillaBrewEffect(MobEffects.STRENGTH, Ingredient.fromItem(Items.BLAZE_POWDER));
		registerVanillaBrewEffect(MobEffects.WEAKNESS, Ingredient.fromItem(Items.RABBIT_HIDE));
		registerVanillaBrewEffect(MobEffects.WITHER, Ingredient.fromItem(ModItems.ectoplasm));
		registerVanillaBrewEffect(MobEffects.BLINDNESS, Ingredient.fromItem(ModItems.belladonna));
		registerVanillaBrewEffect(MobEffects.NAUSEA, Ingredient.fromItem(ModItems.oak_apple_gall), 100);
		registerVanillaBrewEffect(MobEffects.HASTE, Ingredient.fromItem(ModItems.lavender));
		registerVanillaBrewEffect(MobEffects.GLOWING, Ingredient.fromItem(ModItems.spectral_dust));
		registerVanillaBrewEffect(MobEffects.UNLUCK, Ingredient.fromItem(ModItems.adders_fork));
		registerVanillaBrewEffect(MobEffects.LUCK, Ingredient.fromItem(Items.RABBIT_FOOT));
		//Resistance comes at a screwed up cost.
		registerVanillaBrewEffect(MobEffects.RESISTANCE, Ingredient.fromItem(ModItems.heart), 100);
		registerVanillaBrewEffect(MobEffects.LEVITATION, Ingredient.fromItem(Items.SHULKER_SHELL));

		registerCombinedBrewEffect(ModPotions.wolfsbane, Ingredient.fromItem(ModItems.aconitum));
		registerCombinedBrewEffect(ModPotions.arrow_deflect, Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.everchanging_presence.ordinal())));
		registerCombinedBrewEffect(ModPotions.absence, Ingredient.fromItem(ModItems.cold_iron_dust_small));
		registerCombinedBrewEffect(ModPotions.plant, Ingredient.fromItem(Item.getItemFromBlock(Blocks.RED_MUSHROOM)));
		registerCombinedBrewEffect(ModPotions.bane_arthropods, Ingredient.fromItem(ModItems.wormwood));
		registerCombinedBrewEffect(ModPotions.corruption, Ingredient.fromItem(Items.BONE));
		registerCombinedBrewEffect(ModPotions.cursed_leaping, Ingredient.fromItem(Items.CHORUS_FRUIT));
		registerCombinedBrewEffect(ModPotions.demons_bane, Ingredient.fromItem(ModItems.hellebore));
		registerCombinedBrewEffect(ModPotions.projectile_resistance, Ingredient.fromItem(ModItems.silver_scales));
		registerCombinedBrewEffect(ModPotions.disrobing, Ingredient.fromItem(ModItems.wax));
		registerCombinedBrewEffect(ModPotions.ender_inhibition, Ingredient.fromItem(ModItems.dimensional_sand));
		registerCombinedBrewEffect(ModPotions.extinguish_fires, Ingredient.fromItem(ModItems.seed_mint));
		registerCombinedBrewEffect(ModPotions.fertilize, Ingredient.fromItem(ModItems.thistle)); // TODO make it bonemeal, but it will collide with the color modifier
		registerCombinedBrewEffect(ModPotions.fireworld, Ingredient.fromItem(ModItems.ginger));
		registerCombinedBrewEffect(ModPotions.grace, Ingredient.fromItem(ModItems.wool_of_bat));
		registerCombinedBrewEffect(ModPotions.mending, Ingredient.fromItem(Items.GOLDEN_APPLE));
		registerCombinedBrewEffect(ModPotions.flower_growth, Ingredient.fromItem(Item.getItemFromBlock(Blocks.RED_FLOWER)));
		registerCombinedBrewEffect(ModPotions.harvest, Ingredient.fromItem(Items.APPLE));
		registerCombinedBrewEffect(ModPotions.holy_water, Ingredient.fromItem(ModItems.white_sage));
		registerCombinedBrewEffect(ModPotions.ice_world, Ingredient.fromItem(ModItems.mint));
		registerCombinedBrewEffect(ModPotions.outcasts_shame, Ingredient.fromItem(ModItems.carnivorous_tooth));
		registerCombinedBrewEffect(ModPotions.infestation, Ingredient.fromItem(Item.getItemFromBlock(Blocks.MYCELIUM)));
		registerCombinedBrewEffect(ModPotions.ozymandias, Ingredient.fromItem(Item.getItemFromBlock(Blocks.SANDSTONE)));
		registerCombinedBrewEffect(ModPotions.purification, Ingredient.fromItem(ModItems.tulsi));
		registerCombinedBrewEffect(ModPotions.path_of_the_deep, new IngredientMultiOreDict("kelp", "seaWeed", "cropSeaweed", "cropKelp"));
		registerCombinedBrewEffect(ModPotions.prune_leaves, Ingredient.fromItem(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)));
		registerCombinedBrewEffect(ModPotions.rotting, Ingredient.fromItem(ModItems.tongue_of_dog));
		registerCombinedBrewEffect(ModPotions.setehs_wastes, Ingredient.fromStacks(new ItemStack(Blocks.SAND, 1, 1)));
		registerCombinedBrewEffect(ModPotions.salted_earth, salt);
		registerCombinedBrewEffect(ModPotions.shell_armor, Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.coquina)));
		registerCombinedBrewEffect(ModPotions.till_land, Ingredient.fromItem(Item.getItemFromBlock(Blocks.DIRT)));
		registerCombinedBrewEffect(ModPotions.snow_trail, Ingredient.fromItem(Item.getItemFromBlock(Blocks.PACKED_ICE)));
		registerCombinedBrewEffect(ModPotions.spider_nightmare, Ingredient.fromItem(Item.getItemFromBlock(Blocks.WEB)));
		registerCombinedBrewEffect(ModPotions.volatility, Ingredient.fromItem(Items.GUNPOWDER));
		registerCombinedBrewEffect(ModPotions.pulverize, Ingredient.fromItem(Item.getItemFromBlock(Blocks.COBBLESTONE)));
		registerCombinedBrewEffect(ModPotions.love, Ingredient.fromItem(ModItems.seed_silphium));
		registerCombinedBrewEffect(ModPotions.revealing, eyes);
		registerCombinedBrewEffect(ModPotions.adders_kiss, Ingredient.fromItem(ModItems.snake_venom));
		registerCombinedBrewEffect(ModPotions.deaths_ebb, Ingredient.fromItem(ModItems.asphodel));
		registerCombinedBrewEffect(ModPotions.magickal_boon, Ingredient.fromItem(ModItems.mandrake_root));
		registerCombinedBrewEffect(ModPotions.magickal_drain, Ingredient.fromItem(ModItems.filet_of_fenny_snake));
		registerCombinedBrewEffect(ModPotions.magickal_dampening, Ingredient.fromItem(ModItems.graveyard_dust));
		registerCombinedBrewEffect(ModPotions.magickal_boost, Ingredient.fromItem(ModItems.juniper_berries));
		registerCombinedBrewEffect(ModPotions.holy_water, Ingredient.fromItem(ModItems.garlic));
		registerCombinedBrewEffect(ModPotions.mowing, new CompoundIngredient(Arrays.asList(Ingredient.fromItem(Item.getItemFromBlock(Blocks.LEAVES)), Ingredient.fromItem(Item.getItemFromBlock(Blocks.LEAVES2)), new OreIngredient("leaves"))) {
		});
	}

	public static void postInit() {
		BewitchmentAPI.getAPI().registerBrewEffect(ModPotions.freezing, ModPotions.freezing.getPotion(), Ingredient.fromItem(Items.SNOWBALL));
		BewitchmentAPI.getAPI().registerBrewEffect(ModPotions.sinking, ModPotions.sinking.getPotion(), Ingredient.fromItem(Items.IRON_NUGGET));
	}

	private static void registerVanillaBrewEffect(Potion potion, Ingredient ingredient) {
		BewitchmentAPI.getAPI().registerBrewEffect(new BrewVanilla(potion), potion, ingredient);
	}

	private static void registerVanillaBrewEffect(Potion potion, Ingredient ingredient, int duration) {
		BewitchmentAPI.getAPI().registerBrewEffect(new BrewVanilla(duration), potion, ingredient);
	}

	private static void registerCombinedBrewEffect(Potion potion, Ingredient ingredient) {
		if (potion == null) {
			throw new NullPointerException("Null potion cannot be registered");
		}
		if (ingredient.getMatchingStacks().length == 0) {
			throw new IllegalArgumentException("Ingredient for " + potion.getRegistryName() + " is empty");
		}
		if (potion instanceof IBrewEffect) {
			BewitchmentAPI.getAPI().registerBrewEffect((IBrewEffect) potion, potion, ingredient);
			return;
		}
		throw new IllegalArgumentException(potion + " is not an IBrewEffect. Use BewitchmentAPI#registerBrewEffect to register them as separate objects");
	}

	private static void registerFood(Ingredient ingredient, int hunger, float saturation) {
		registerFoodValue(ingredient, new CauldronFoodValue(hunger, saturation));
	}
}

