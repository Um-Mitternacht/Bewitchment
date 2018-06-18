package com.bewitchment.common;

import com.bewitchment.common.abilities.ModAbilities;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.plants.BlockMoonbell;
import com.bewitchment.common.cauldron.ModBrewModifiers;
import com.bewitchment.common.core.ModLootTables;
import com.bewitchment.common.core.capability.cauldronTeleports.CapabilityCauldronTeleport;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import com.bewitchment.common.core.capability.energy.CapabilityEnergy;
import com.bewitchment.common.core.capability.energy.energy_item.CapabilityEnergyUser;
import com.bewitchment.common.core.capability.simple.BarkCapability;
import com.bewitchment.common.core.capability.simple.SimpleCapability;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.command.CommandForceFortune;
import com.bewitchment.common.core.command.CommandFortuneActivator;
import com.bewitchment.common.core.command.CommandIncantation;
import com.bewitchment.common.core.command.CommandTransformationModifier;
import com.bewitchment.common.core.event.ModEvents;
import com.bewitchment.common.core.gen.ModGen;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.BarkGrow;
import com.bewitchment.common.core.proxy.ISidedProxy;
import com.bewitchment.common.crafting.cauldron.CauldronRegistry;
import com.bewitchment.common.divination.ModFortunes;
import com.bewitchment.common.divination.ModTarots;
import com.bewitchment.common.entity.ModEntities;
import com.bewitchment.common.fermenting.ModBarrelRecipes;
import com.bewitchment.common.incantation.ModIncantations;
import com.bewitchment.common.infusion.ModInfusions;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompatBridge;
import com.bewitchment.common.internalApi.ApiInstance;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.food.seed.SeedDropRegistry;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.ritual.ModRituals;
import com.bewitchment.common.spell.ModSpells;
import com.bewitchment.common.spinning.ModSpinningThreadRecipes;
import com.bewitchment.common.transformation.ModTransformations;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.bewitchment.common.lib.LibMod.MOD_NAME;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = LibMod.MOD_ID, name = MOD_NAME, version = LibMod.MOD_VER, dependencies = LibMod.DEPENDENCIES, acceptedMinecraftVersions = "[1.12,1.13]", certificateFingerprint = "@FINGERPRINT@")
public class Bewitchment {

	public static final Logger logger = LogManager.getLogger(MOD_NAME);
	//Constants
	public static final String TAGLOCK_ENTITY = "tag_entity";
	public static final String TAGLOCK_ENTITY_NAME = "tag_entity_name";
	//States
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
	public static final PropertyEnum<BlockStairs.EnumHalf> HALF = PropertyEnum.create("half", BlockStairs.EnumHalf.class);
	@SidedProxy(serverSide = LibMod.PROXY_COMMON, clientSide = LibMod.PROXY_CLIENT)
	public static ISidedProxy proxy;
	@Instance(LibMod.MOD_ID)
	public static Bewitchment instance;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ApiInstance.initAPI();
		CapabilityEnergy.init();
		CapabilityDivination.init();
		CapabilityEnergyUser.init();
		CapabilityTransformationData.init();
		CapabilityBloodReserve.init();
		CapabilityCauldronTeleport.init();
		SimpleCapability.preInit(BarkCapability.class, LibMod.MOD_ID, BarkCapability.CAPABILITY, BarkCapability.DEFAULT_INSTANCE);
		NetworkHandler.init();
		ModInfusions.init();
		ModTransformations.init();
		ModEvents.init();
		ModEntities.init();
		ModSpells.init();
		ModFortunes.init();
		ModAbilities.dummyMethodToLoadClass();
		ModPotions.init();
		ModLootTables.registerLootTables();
		proxy.preInit(event);
		if (Loader.isModLoaded("thaumcraft")) {
			ThaumcraftCompatBridge.registerAspects();
		}

		logger.info("Remember when I told you how my");
		logger.info("Kin is different in some ways?");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		IMessageHandler<BarkGrow, IMessage> mh = SimpleCapability.init(BarkCapability.class, LibMod.MOD_ID, BarkCapability.CAPABILITY, BarkCapability.DEFAULT_INSTANCE, NetworkHandler.HANDLER, BarkGrow::decode, BarkGrow::encode);
		NetworkHandler.HANDLER.registerMessage(mh, BarkGrow.class, NetworkHandler.next(), Side.CLIENT);
		ModItems.init();
		ModBlocks.init();
		ModTarots.init();
		CauldronRegistry.init();
		SeedDropRegistry.init();
		ModGen.init();
		ModSpinningThreadRecipes.init();
		ModBarrelRecipes.init();
		ModRituals.init();
		ModBrewModifiers.init();

		logger.info("It's a fact, she is exactly that!");
		logger.info("A harbinger of death from the world of witchcraft,");
		logger.info("And she's feeding them cakes and her ale to this innocent boy,");
		logger.info("And her magic brings dismay!");

		logger.info("I hear her in the wind, the bane of our town");
		logger.info("Come with me, father, I'm to expose a heathen");
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST).parallelStream().filter(b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.DENSE)).forEach(b -> {
			BlockMoonbell.addValidMoonbellBiome(b);
		});

		CauldronRegistry.postInit();
	}

	@EventHandler
	public void start(FMLServerStartingEvent event) {
		ModIncantations.init();
		event.registerServerCommand(new CommandIncantation());
		event.registerServerCommand(new CommandTransformationModifier());
		event.registerServerCommand(new CommandFortuneActivator());
		event.registerServerCommand(new CommandForceFortune());
	}

	@EventHandler
	public void setFlight(FMLServerStartedEvent evt) {
		FMLCommonHandler.instance().getMinecraftServerInstance().setAllowFlight(true);
	}
}
