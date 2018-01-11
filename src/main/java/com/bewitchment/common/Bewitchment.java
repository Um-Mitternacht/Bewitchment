package com.bewitchment.common;

import static com.bewitchment.common.lib.LibMod.MOD_NAME;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.plants.BlockMoonbell;
import com.bewitchment.common.brew.ModBrews;
import com.bewitchment.common.core.capability.brew.CapabilityBrewStorage;
import com.bewitchment.common.core.capability.energy.CapabilityEnergy;
import com.bewitchment.common.core.command.CommandIncantation;
import com.bewitchment.common.core.command.ModCommands;
import com.bewitchment.common.core.event.ModEvents;
import com.bewitchment.common.core.gen.ModGen;
import com.bewitchment.common.core.net.PacketHandler;
import com.bewitchment.common.core.proxy.ISidedProxy;
import com.bewitchment.common.crafting.cauldron.CauldronCrafting;
import com.bewitchment.common.entity.ModEntities;
import com.bewitchment.common.fermenting.ModBarrelRecipes;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.food.seed.SeedDropRegistry;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.ritual.ModRituals;
import com.bewitchment.common.spell.ModSpells;
import com.bewitchment.common.spinning.ModSpinningThreadRecipes;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = LibMod.MOD_ID, name = MOD_NAME, version = LibMod.MOD_VER, dependencies = LibMod.DEPENDENCIES, acceptedMinecraftVersions = "[1.12,1.13]", certificateFingerprint = "@FINGERPRINT@")
public class Bewitchment {

	public static final Logger logger = LogManager.getLogger(MOD_NAME);
	@SidedProxy(serverSide = LibMod.PROXY_COMMON, clientSide = LibMod.PROXY_CLIENT)
	public static ISidedProxy proxy;
	@Instance(LibMod.MOD_ID)
	public static Bewitchment instance;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CapabilityEnergy.init();
		CapabilityBrewStorage.init();
		PacketHandler.init();
		ModEvents.init();
		ModEntities.init();
		ModBrews.init();
		ModSpells.init();
		proxy.preInit(event);

		logger.info("Remember when I told you how my");
		logger.info("Kin is different in some ways?");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

		ModItems.init();
		ModBlocks.init();
		CauldronCrafting.init();

		SeedDropRegistry.init();
		ModGen.init();
		ModSpinningThreadRecipes.init();
		ModBarrelRecipes.init();
		ModRituals.init();

		logger.info("It's a fact, she is exactly that!");
		logger.info("A harbinger of death from the world of bewitchment,");
		logger.info("And she's feeding them cakes and her ale to this innocent boy,");
		logger.info("And her magic brings dismay!");

		logger.info("I hear her in the wind, the bane of our town");
		logger.info("Come with me, father, I'm to expose a heathen");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		ForgeRegistries.BIOMES.getValues().parallelStream().filter(b -> b.getBiomeName().toLowerCase().contains("roofed forest")).forEach(b -> {
			BlockMoonbell.addValidMoonbellBiome(b);
		});
	}

	@EventHandler
	public void start(FMLServerStartingEvent event) {
		ModCommands.init();
		event.registerServerCommand(new CommandIncantation());
	}
}
