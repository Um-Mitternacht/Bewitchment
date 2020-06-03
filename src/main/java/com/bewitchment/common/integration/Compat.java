package com.bewitchment.common.integration;

import c4.consecration.common.init.ConsecrationItems;
import c4.consecration.common.init.ConsecrationPotions;
import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.api.registry.Brew;
import com.bewitchment.api.registry.CauldronRecipe;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompat;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.PotionHandler;
import com.miskatonicmysteries.registry.ModObjects;
import com.miskatonicmysteries.registry.ModPotions;
import its_meow.betteranimalsplus.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.world.feature.*;

import java.util.Arrays;

import static vazkii.quark.misc.feature.ExtraPotions.dangerSight;
import static vazkii.quark.misc.feature.ExtraPotions.enableDangerSight;
import static vazkii.quark.world.feature.Biotite.biotite;
import static vazkii.quark.world.feature.CaveRoots.*;
import static vazkii.quark.world.feature.Crabs.*;
import static vazkii.quark.world.feature.Frogs.*;
import static vazkii.quark.world.feature.UndergroundBiomes.glowceliumEnabled;
import static vazkii.quark.world.feature.UndergroundBiomes.glowshroom;

public class Compat {
	public static void init() {
		if (Loader.isModLoaded("betteranimalsplus")) {
			BAPCompat.registerBAPHeadAlter();
		}
		if (Loader.isModLoaded("thaumcraft")) {
			ThaumcraftCompat.init();
		}
		if (Loader.isModLoaded("elementaristics")) {
			ElementaristicsCompat.registerElementaristicsStuff();
		}
		if (Loader.isModLoaded("miskatonicmysteries")) {
			MiskatonicMysteriesCompat.registerMiskatonicMysteriesStuff();
		}
	}
	
	@Optional.Method(modid = "miskatonicmysteries")
	@SubscribeEvent
	public void registerManiaBrews(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "a_poem_for_byzantium"), Util.get(ModObjects.infested_wheat), new PotionEffect(ModPotions.mania, (600))));
	}
	
	@Optional.Method(modid = "covetedmobs")
	@SubscribeEvent
	public void registerCMBrews(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "eye_of_newt_sight"), Util.get("eyeOfNewt"), new PotionEffect(MobEffects.NIGHT_VISION, (20 * 120))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "sting"), Util.get("blindwormsSting"), new PotionEffect(MobEffects.POISON, (20 * 20))));
	}
	
	@Optional.Method(modid = "covetedmobs")
	@SubscribeEvent
	public void registerCMOvenRecipes(RegistryEvent.Register<OvenRecipe> event) {
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oven_bushmeat"), new ItemStack(com.covetedmobs.registry.ModObjects.bushmeat_raw, 1), new ItemStack(com.covetedmobs.registry.ModObjects.bushmeat_cooked), new ItemStack(com.bewitchment.registry.ModObjects.tallow), 0.35f, false));
	}
	
	@Optional.Method(modid = "betteranimalsplus")
	@SubscribeEvent
	public void registerBAPOvenRecipes(RegistryEvent.Register<OvenRecipe> event) {
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_1"), new ItemStack(ModItems.VENISON_RAW, 1), new ItemStack(ModItems.VENISON_COOKED), new ItemStack(com.bewitchment.registry.ModObjects.tallow), 0.35f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_2"), new ItemStack(ModItems.PHEASANT_RAW, 1), new ItemStack(ModItems.PHEASANT_COOKED), new ItemStack(com.bewitchment.registry.ModObjects.tallow), 0.35f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_3"), new ItemStack(ModItems.TURKEY_LEG_RAW, 1), new ItemStack(ModItems.TURKEY_LEG_COOKED), new ItemStack(com.bewitchment.registry.ModObjects.tallow), 0.35f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_4"), new ItemStack(ModItems.TURKEY_RAW, 1), new ItemStack(ModItems.TURKEY_COOKED), new ItemStack(com.bewitchment.registry.ModObjects.tallow), 0.35f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_5"), new ItemStack(ModItems.GOLDEN_GOOSE_EGG, 1), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET, 5), 0.35f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_6"), new ItemStack(ModItems.EEL_MEAT_RAW, 1), new ItemStack(ModItems.EEL_MEAT_COOKED), new ItemStack(com.bewitchment.registry.ModObjects.cloudy_oil), 0.35f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bap_recipe_7"), new ItemStack(ModItems.CRAB_MEAT_RAW, 1), new ItemStack(ModItems.CRAB_MEAT_COOKED), new ItemStack(com.bewitchment.registry.ModObjects.cloudy_oil), 0.35f));
	}
	
	@Optional.Method(modid = "betteranimalsplus")
	@SubscribeEvent
	public void registerBAPBrew(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "trillium_brew"), Util.get(ModItems.TRILLIUM), new PotionEffect(new PotionEffect(MobEffects.HASTE, (20 * 30)))));
	}
	
	@Optional.Method(modid = "covetedmobs")
	@SubscribeEvent
	public void registerCMRecipes(RegistryEvent.Register<CauldronRecipe> event) {
		//Alts that don't use lizard legs
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "stew_of_the_grotesque_alt_1"), Arrays.asList(Util.get(com.bewitchment.registry.ModObjects.demonic_elixir), Util.get(com.bewitchment.registry.ModObjects.heart), Util.get(Items.MUTTON), Util.get(Items.SLIME_BALL), Util.get(com.bewitchment.registry.ModObjects.toe_of_frog), Util.get("blindwormsSting"), Util.get(com.bewitchment.registry.ModObjects.adders_fork), Util.get(com.bewitchment.registry.ModObjects.belladonna), Util.get(com.bewitchment.registry.ModObjects.hellebore)), Arrays.asList(new ItemStack(com.bewitchment.registry.ModObjects.stew_of_the_grotesque), new ItemStack(com.bewitchment.registry.ModObjects.empty_jar))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "stew_of_the_grotesque_alt_2"), Arrays.asList(Util.get(com.bewitchment.registry.ModObjects.demonic_elixir), Util.get(com.bewitchment.registry.ModObjects.heart), Util.get(Items.MUTTON), Util.get(Items.SLIME_BALL), Util.get(com.bewitchment.registry.ModObjects.toe_of_frog), Util.get("eyeOfNewt"), Util.get(com.bewitchment.registry.ModObjects.adders_fork), Util.get(com.bewitchment.registry.ModObjects.belladonna), Util.get(com.bewitchment.registry.ModObjects.hellebore)), Arrays.asList(new ItemStack(com.bewitchment.registry.ModObjects.stew_of_the_grotesque), new ItemStack(com.bewitchment.registry.ModObjects.empty_jar))));
	}
	
	@Optional.Method(modid = "mowziesmobs")
	@SubscribeEvent
	public void registerMowziesBrew(RegistryEvent.Register<Brew> event) {
		Util.registerAltarUpgradeItem(ItemHandler.ICE_CRYSTAL, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ItemHandler.NAGA_FANG_DAGGER, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.25));
		Util.registerAltarUpgradeItem(ItemHandler.WROUGHT_AXE, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.35));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "poison_resistance"), Util.get(ItemHandler.NAGA_FANG), new PotionEffect(PotionHandler.POISON_RESIST, (600))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "mowzie_vision"), Util.get(ItemHandler.GLOWING_JELLY), new PotionEffect(MobEffects.NIGHT_VISION, (20 * 120))));
	}
	
	@Optional.Method(modid = "consecration")
	@SubscribeEvent
	public void registerConsecrationBrew(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "consecrated_water"), Util.get(ConsecrationItems.blessedDust), new PotionEffect(ConsecrationPotions.HOLY_POTION, 1)));
	}
	
	@Optional.Method(modid = "quark")
	@SubscribeEvent
	public void registerQuarkBrew(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "prismarine_haste"), Util.get(Items.PRISMARINE_CRYSTALS), new PotionEffect(MobEffects.HASTE, (20 * 30))));
		if (ModuleLoader.isFeatureEnabled(Frogs.class)) event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "frog_boost"), Util.get(gildedFrogLeg), new PotionEffect(MobEffects.JUMP_BOOST, (20 * 30))));
		if (ModuleLoader.isFeatureEnabled(Biotite.class)) event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "biotite_resistance"), Util.get(biotite), new PotionEffect(MobEffects.RESISTANCE, (20 * 30))));
		if (ModuleLoader.isFeatureEnabled(UndergroundBiomes.class) && enableDangerSight && glowceliumEnabled)
			event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "danger_sight"), Util.get(glowshroom), new PotionEffect(dangerSight, (20 * 30))));
		if (ModuleLoader.isFeatureEnabled(CaveRoots.class) && enableFlowers) {
			event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blueberry"), Util.get(new ItemStack(root_flower, 1, 0)), new PotionEffect(blue_effect, (20 * 30))));
			event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blackening"), Util.get(new ItemStack(root_flower, 1, 1)), new PotionEffect(black_effect, (20 * 30))));
			event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "whitening"), Util.get(new ItemStack(root_flower, 1, 2)), new PotionEffect(white_effect, (20 * 30))));
		}
		if (ModuleLoader.isFeatureEnabled(Crabs.class)) event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "stability"), Util.get(crabShell), new PotionEffect(resilience, (20 * 30))));
	}
	
	@Optional.Method(modid = "quark")
	@SubscribeEvent
	public void registerQuarkOvenRecipes(RegistryEvent.Register<OvenRecipe> event) {
		if (ModuleLoader.isFeatureEnabled(Frogs.class))
			event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "frog_leg_recipe"), new ItemStack(frogLeg, 1, 0), new ItemStack(cookedFrogLeg, 1, 0), new ItemStack(com.bewitchment.registry.ModObjects.cloudy_oil), 0.35f));
		if (ModuleLoader.isFeatureEnabled(Crabs.class))
			event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "crab_leg_recipe"), new ItemStack(crabLeg, 1, 0), new ItemStack(cookedCrabLeg, 1, 0), new ItemStack(com.bewitchment.registry.ModObjects.cloudy_oil), 0.35f));
	}
}
