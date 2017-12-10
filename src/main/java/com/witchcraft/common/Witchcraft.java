package com.witchcraft.common;

import com.witchcraft.common.block.ModBlocks;
import com.witchcraft.common.brew.ModBrews;
import com.witchcraft.common.core.capability.brew.CapabilityBrewStorage;
import com.witchcraft.common.core.capability.energy.CapabilityEnergy;
import com.witchcraft.common.core.command.CommandIncantation;
import com.witchcraft.common.core.command.ModCommands;
import com.witchcraft.common.core.event.ModEvents;
import com.witchcraft.common.core.gen.ModGen;
import com.witchcraft.common.core.net.PacketHandler;
import com.witchcraft.common.core.proxy.ISidedProxy;
import com.witchcraft.common.crafting.cauldron.CauldronCrafting;
import com.witchcraft.common.entity.ModEntities;
import com.witchcraft.common.item.ModItems;
import com.witchcraft.common.item.food.seed.SeedDropRegistry;
import com.witchcraft.common.lib.LibMod;
import com.witchcraft.common.spell.ModSpells;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.witchcraft.common.lib.LibMod.MOD_NAME;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = LibMod.MOD_ID, name = MOD_NAME, version = LibMod.MOD_VER, dependencies = LibMod.DEPENDENCIES, acceptedMinecraftVersions = "[1.12,1.13]", certificateFingerprint = "@FINGERPRINT@")
public class Witchcraft {

	public static final Logger logger = LogManager.getLogger(MOD_NAME);
	@SidedProxy(serverSide = LibMod.PROXY_COMMON, clientSide = LibMod.PROXY_CLIENT)
	public static ISidedProxy proxy;
	@Instance(LibMod.MOD_ID)
	public static Witchcraft instance;

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

		logger.info("It's a fact, she is exactly that!");
		logger.info("A harbinger of death from the world of witchcraft,");
		logger.info("And she's feeding them cakes and her ale to this innocent boy,");
		logger.info("And her magic brings dismay!");

		logger.info("I hear her in the wind, the bane of our town");
		logger.info("Come with me, father, I'm to expose a heathen");
	}

	@EventHandler
	public void start(FMLServerStartingEvent event) {
		ModCommands.init();
		event.registerServerCommand(new CommandIncantation());
	}
}
