package com.bewitchment.common.potion;

import com.bewitchment.common.content.cauldron.brews.*;
import com.bewitchment.common.potion.potions.PotionBloodDrained;
import com.bewitchment.common.potion.potions.PotionMesmerize;
import com.bewitchment.common.potion.potions.PotionSunWard;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModPotions {

	// Normal effects
	public static Potion bloodDrained, sun_ward, mesmerized;

	// Also brews
	public static Potion wolfsbane, arrow_deflect, absence, plant, bane_arthropods, corruption, cursed_leaping, demons_bane;
	public static Potion projectile_resistance, disrobing, ender_inhibition, extinguish_fires, fertilize, fireworld, grace;
	public static Potion mending, flower_growth, harvest, holy_water, ice_world, outcasts_shame, infestation, ozymandias;
	public static Potion purification, path_of_the_deep, prune_leaves, rotting, setehs_wastes, salted_earth, shell_armor;
	public static Potion till_land, snow_trail, spider_nightmare, volatility, pulverize, mowing, love, revealing, adders_kiss;
	public static Potion deaths_ebb, magickal_boon, magickal_boost, magickal_drain, magickal_dampening;

	public static PotionFrostbite freezing;
	public static PotionSinking sinking;

	private ModPotions() {
	}

	public static void init() {
		bloodDrained = new PotionBloodDrained();
		sun_ward = new PotionSunWard();
		wolfsbane = new PotionWolfsbane();
		arrow_deflect = new PotionArrowDeflection();
		absence = new PotionAbsence();
		plant = new PotionPlant();
		bane_arthropods = new PotionBaneArthropods();
		corruption = new PotionCorruption();
		cursed_leaping = new PotionCursedLeaping();
		demons_bane = new PotionDemonsbane();
		projectile_resistance = new PotionProjectileResistance();
		disrobing = new PotionDisrobing();
		ender_inhibition = new PotionEnderInhibition();
		extinguish_fires = new PotionExtinguishFire();
		fertilize = new PotionFertilize();
		fireworld = new PotionFireWorld();
		grace = new PotionGrace();
		freezing = new PotionFrostbite();
		mending = new PotionMending();
		flower_growth = new PotionGrowFlowers();
		harvest = new PotionHarvest();
		holy_water = new PotionHolyWater();
		ice_world = new PotionIceWorld();
		outcasts_shame = new PotionOutcastsShame();
		infestation = new PotionInfestation();
		ozymandias = new PotionOzymandias();
		purification = new PotionPurification();
		path_of_the_deep = new PotionPathOfTheDeep();
		prune_leaves = new PotionPruneLeaves();
		rotting = new PotionRotting();
		setehs_wastes = new PotionSetehsWastes();
		salted_earth = new PotionSaltedEarth();
		shell_armor = new PotionShellArmor();
		till_land = new PotionTillLand();
		sinking = new PotionSinking();
		snow_trail = new PotionSnowTrail();
		spider_nightmare = new PotionSpiderNightmare();
		volatility = new PotionVolatility();
		pulverize = new PotionPulverize();
		mowing = new PotionMowing();
		love = new PotionLove();
		revealing = new PotionRevealing();
		adders_kiss = new PotionAddersKiss();
		deaths_ebb = new PotionDeathsEbb();
		magickal_boon = new PotionPowerRegen();
		magickal_drain = new PotionPowerDrain();
		magickal_boost = new PotionPowerBoost();
		magickal_dampening = new PotionPowerDampening();
		mesmerized = new PotionMesmerize();

		ForgeRegistries.POTIONS.registerAll(//
				bloodDrained, wolfsbane, arrow_deflect, absence, plant, //
				bane_arthropods, corruption, cursed_leaping, demons_bane, //
				projectile_resistance, disrobing, ender_inhibition, extinguish_fires, //
				fertilize, freezing, fireworld, grace, mending, flower_growth, //
				harvest, holy_water, ice_world, outcasts_shame, infestation, ozymandias, //
				purification, path_of_the_deep, prune_leaves, rotting, setehs_wastes, //
				salted_earth, shell_armor, till_land, sinking, snow_trail, spider_nightmare, //
				volatility, pulverize, mowing, sun_ward, love, revealing, adders_kiss, //
				deaths_ebb, magickal_boon, mesmerized, magickal_drain, magickal_boost, //
				magickal_dampening
		);
	}
}
