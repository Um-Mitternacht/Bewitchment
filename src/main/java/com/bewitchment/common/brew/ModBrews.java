package com.bewitchment.common.brew;

import com.bewitchment.api.BrewRegistry;
import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.IBrew;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;

import static com.bewitchment.api.BrewRegistry.Brew.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

@ObjectHolder(LibMod.MOD_ID)
public final class ModBrews {

	//Todo: Brew that removes armor.

	public static IBrew SHELL_ARMOR;
	public static IBrew SPIDER_NIGHTMARE;
	public static IBrew EXTINGUISH_FIRES;
	public static IBrew MARS_WATER;
	public static IBrew FROSTBITE;
	public static IBrew HOLY_WATER;
	public static IBrew VOLATILE;
	public static IBrew HARVEST;
	public static IBrew FERTILIZE;
	public static IBrew GROW_FLOWER;
	public static IBrew TILL_LAND;
	public static IBrew ENDER_INHIBITION;
	public static IBrew PATH_OF_THE_DEEP;
	public static IBrew ROCK_PULVERIZE;
	public static IBrew PRUNE_LEAVES;
	public static IBrew AUTO_PLANT;
	public static IBrew SNOW_TRAIL;
	public static IBrew SKIN_TINT;
	public static IBrew CURSED_LEAPING;
	public static IBrew SINKING;
	public static IBrew BANE_ARTHROPODS;
	public static IBrew WOLFSBANE;
	public static IBrew OUTCASTS_SHAME;
	public static IBrew IGNITION;
	public static IBrew GRACE;
	public static IBrew PURIFY;
	public static IBrew NOTCHED;
	public static IBrew MYCOLOGICAL_CORRUPTION;
	public static IBrew GROWTH;
	public static IBrew OZYMANDIAS;
	public static IBrew HELLS_WROTH;
	public static IBrew SETEHS_WASTES;
	public static IBrew HELL_WORLD;
	public static IBrew ICE_WORLD;
	public static IBrew CORRUPTION;
	public static IBrew SALT_LAND;
	public static IBrew ABSENCE;
	public static IBrew BULLETPROOF;
	public static IBrew MORTAL_COIL;
	public static IBrew OVERCOAT;
	public static IBrew DISROBING;
	public static IBrew ROTTING;

	private ModBrews() {
	}

	public static void init() {
		SHELL_ARMOR = registerBrew(new ShellArmorBrew());
		SPIDER_NIGHTMARE = registerBrew(new SpiderNightmareBrew());
		EXTINGUISH_FIRES = registerBrew(new ExtinguishFiresBrew());
		MARS_WATER = registerBrew(new MarsWaterBrew());
		FROSTBITE = registerBrew(new FrostbiteBrew());
		HOLY_WATER = registerBrew(new HolyWaterBrew());
		VOLATILE = registerBrew(new VolatileBrew());
		HARVEST = registerBrew(new HarvestBrew());
		FERTILIZE = registerBrew(new FertilizeBrew());
		GROW_FLOWER = registerBrew(new GrowFlowersBrew());
		TILL_LAND = registerBrew(new TillLandBrew());
		ENDER_INHIBITION = registerBrew(new EnderInhibitionBrew());
		PATH_OF_THE_DEEP = registerBrew(new PathOfTheDeep());
		ROCK_PULVERIZE = registerBrew(new RockPulverizeBrew());
		PRUNE_LEAVES = registerBrew(new PruneLeavesBrew());
		AUTO_PLANT = registerBrew(new AutoPlantBrew());
		SNOW_TRAIL = registerBrew(new SnowTrailBrew());
		SKIN_TINT = registerBrew(new SkinTintBrew());
		CURSED_LEAPING = registerBrew(new CursedLeapingBrew());
		SINKING = registerBrew(new SinkingBrew());
		BANE_ARTHROPODS = registerBrew(new BaneArthropodsBrew());
		WOLFSBANE = registerBrew(new WolfsbaneBrew());
		OUTCASTS_SHAME = registerBrew(new OutcastsShameBrew());
		IGNITION = registerBrew(new IgnitionBrew());
		GRACE = registerBrew(new GraceBrew());
		PURIFY = registerBrew(new PurifyBrew());
		NOTCHED = registerBrew(new NotchedBrew());
		MYCOLOGICAL_CORRUPTION = registerBrew(new MycologicalCorruptionBrew());
		GROWTH = registerBrew(new GrassGrowBrew());
		OZYMANDIAS = registerBrew(new OzymandiasBrew());
		HELLS_WROTH = registerBrew(new HellsWrothBrew());
		SETEHS_WASTES = registerBrew(new SetehsWastesBrew());
		HELL_WORLD = registerBrew(new HellWorldBrew());
		ICE_WORLD = registerBrew(new IceWorldBrew());
		CORRUPTION = registerBrew(new CorruptionBrew());
		SALT_LAND = registerBrew(new SaltLandBrew());
		ABSENCE = registerBrew(new AbsenceBrew());
		BULLETPROOF = registerBrew(new BulletProofBrew());
		MORTAL_COIL = registerBrew(new MortalCoilBrew());
		OVERCOAT = registerBrew(new OvercoatBrew());
		DISROBING = registerBrew(new DisrobingBrew());
		ROTTING = registerBrew(new RottingBrew());

		//--------------------------------DRINKS--------------------------------//
		addDrink(SHELL_ARMOR, 2500, 0);
		addDrink(ENDER_INHIBITION, 1000, 0);
		addDrink(PATH_OF_THE_DEEP, 1000, 0);
		addDrink(SNOW_TRAIL, 1500, 0);
		addDrink(SKIN_TINT, 1500, 0);
		addDrink(IGNITION, 350, 0);
		addDrink(GRACE, 200, 0);
		addDrink(PURIFY, 1, 0);
		addDrink(NOTCHED, 1, 0);
		addDrink(CORRUPTION, 1, 0);
		addDrink(DISROBING, 1, 0);
		addDrink(ABSENCE, 1, 0);
		addDrink(BULLETPROOF, 2500, 0);
		addDrink(ROTTING, 2500, 0);
		addDrink(OVERCOAT, 2500, 0);

		//--------------------------------SPLASH--------------------------------//
		addSplash(SPIDER_NIGHTMARE, 500, 0);
		addSplash(IGNITION, 500, 0);
		addSplash(EXTINGUISH_FIRES, 0, 0);
		addSplash(MARS_WATER, 500, 0);
		addSplash(FROSTBITE, 500, 0);
		addSplash(HOLY_WATER, 1, 0);
		addSplash(VOLATILE, 500, 0);
		addSplash(HARVEST, 0, 0);
		addSplash(FERTILIZE, 0, 0);
		addSplash(GROW_FLOWER, 0, 0);
		addSplash(TILL_LAND, 0, 0);
		addSplash(ENDER_INHIBITION, 500, 0);
		addSplash(PATH_OF_THE_DEEP, 500, 0);
		addSplash(ROCK_PULVERIZE, 0, 0);
		addSplash(PRUNE_LEAVES, 0, 0);
		addSplash(AUTO_PLANT, 0, 0);
		addSplash(SNOW_TRAIL, 1000, 0);
		addSplash(SKIN_TINT, 1000, 0);
		addSplash(CURSED_LEAPING, 1000, 0);
		addSplash(SINKING, 1000, 0);
		addSplash(BANE_ARTHROPODS, 1, 0);
		addSplash(WOLFSBANE, 1, 0);
		addSplash(OUTCASTS_SHAME, 1, 0);
		addSplash(IGNITION, 750, 0);
		addSplash(PURIFY, 1, 0);
		addSplash(MYCOLOGICAL_CORRUPTION, 0, 0);
		addSplash(GROWTH, 0, 0);
		addSplash(OZYMANDIAS, 0, 0);
		addSplash(HELLS_WROTH, 500, 0);
		addSplash(SETEHS_WASTES, 0, 0);
		addSplash(HELL_WORLD, 0, 0);
		addSplash(ICE_WORLD, 0, 0);
		addSplash(CORRUPTION, 1, 0);
		addSplash(SALT_LAND, 0, 0);
		addSplash(ABSENCE, 1, 0);
		addSplash(MORTAL_COIL, 2500, 0);
		addSplash(DISROBING, 0, 0);
		addSplash(ROTTING, 750, 0);

		//--------------------------------LINGER--------------------------------//
		addLinger(SPIDER_NIGHTMARE, 1000, 0);
		addLinger(HOLY_WATER, 50, 0);
		addLinger(VOLATILE, 100, 0);
		addLinger(ENDER_INHIBITION, 250, 0);
		addLinger(SNOW_TRAIL, 500, 0);
		addLinger(SKIN_TINT, 500, 0);
		addLinger(CURSED_LEAPING, 500, 0);
		addLinger(SINKING, 500, 0);
		addLinger(BANE_ARTHROPODS, 50, 0);
		addLinger(WOLFSBANE, 50, 0);
		addLinger(IGNITION, 750, 0);
		addLinger(OUTCASTS_SHAME, 50, 0);
		addLinger(PURIFY, 50, 0);
		addLinger(CORRUPTION, 50, 0);
		addLinger(ABSENCE, 50, 0);
		addLinger(ROTTING, 750, 0);
	}

	private static IBrew registerBrew(IBrew brew) {
		return BrewRegistry.register(new ResourceLocation(LibMod.MOD_ID, brew.getName()), brew);
	}

	private static void addDrink(IBrew brew, int duration, int amplifier) {
		BrewRegistry.setDefault(DRINK, new BrewEffect(brew, duration, amplifier));
	}

	private static void addSplash(IBrew brew, int duration, int amplifier) {
		BrewRegistry.setDefault(SPLASH, new BrewEffect(brew, duration, amplifier));
	}

	private static void addLinger(IBrew brew, int duration, int amplifier) {
		BrewRegistry.setDefault(LINGER, new BrewEffect(brew, duration, amplifier));
	}
}
