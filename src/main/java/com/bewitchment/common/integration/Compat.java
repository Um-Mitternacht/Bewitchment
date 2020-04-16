package com.bewitchment.common.integration;

import c4.consecration.common.init.ConsecrationItems;
import c4.consecration.common.init.ConsecrationPotions;
import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.api.registry.Brew;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompat;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.PotionHandler;
import com.miskatonicmysteries.registry.ModObjects;
import com.miskatonicmysteries.registry.ModPotions;
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

import static vazkii.quark.misc.feature.ExtraPotions.dangerSight;
import static vazkii.quark.misc.feature.ExtraPotions.enableDangerSight;
import static vazkii.quark.world.feature.Biotite.biotite;
import static vazkii.quark.world.feature.CaveRoots.*;
import static vazkii.quark.world.feature.Crabs.crabShell;
import static vazkii.quark.world.feature.Crabs.resilience;
import static vazkii.quark.world.feature.Frogs.gildedFrogLeg;
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
	
	//Todo: Find out why this isn't working
	@Optional.Method(modid = "miskatonicmysteries")
	@SubscribeEvent
	public static void registerManiaBrews(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "a_poem_for_byzantium"), Util.get(ModObjects.infested_wheat), new PotionEffect(ModPotions.mania, (600))));
	}
	
	@Optional.Method(modid = "mowziesmobs")
	@SubscribeEvent
	public void registerMowziesBrew(RegistryEvent.Register<Brew> event) {
		Util.registerAltarUpgradeItem(ItemHandler.ICE_CRYSTAL, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "poison_resistance"), Util.get(ItemHandler.NAGA_FANG), new PotionEffect(PotionHandler.POISON_RESIST, (600))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "mowzie_vision"), Util.get(ItemHandler.GLOWING_JELLY), new PotionEffect(MobEffects.NIGHT_VISION, (600))));
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
}
