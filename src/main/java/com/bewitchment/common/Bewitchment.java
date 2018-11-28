package com.bewitchment.common;

import com.bewitchment.common.api.ApiInstance;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.plants.BlockMoonbell;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.content.cauldron.ModBrewModifiers;
import com.bewitchment.common.content.cauldron.teleportCapability.CapabilityCauldronTeleport;
import com.bewitchment.common.content.crystalBall.ModFortunes;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import com.bewitchment.common.content.incantation.ModIncantations;
import com.bewitchment.common.content.infusion.ModInfusions;
import com.bewitchment.common.content.infusion.capability.InfusionCapability;
import com.bewitchment.common.content.ritual.ModRituals;
import com.bewitchment.common.content.spell.ModSpells;
import com.bewitchment.common.content.tarot.ModTarots;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.content.transformation.ModTransformations;
import com.bewitchment.common.content.transformation.vampire.CapabilityVampire;
import com.bewitchment.common.content.transformation.vampire.blood.CapabilityBloodReserve;
import com.bewitchment.common.content.transformation.werewolf.CapabilityWerewolfStatus;
import com.bewitchment.common.core.capability.energy.MagicPowerConsumer;
import com.bewitchment.common.core.capability.energy.MagicPowerContainer;
import com.bewitchment.common.core.capability.energy.MagicPowerUsingItem;
import com.bewitchment.common.core.capability.energy.player.expansion.CapabilityMPExpansion;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.simple.BarkCapability;
import com.bewitchment.common.core.capability.simple.SimpleCapability;
import com.bewitchment.common.core.command.*;
import com.bewitchment.common.core.event.LootTableEventHandler;
import com.bewitchment.common.core.gen.ModGen;
import com.bewitchment.common.core.helper.CropHelper;
import com.bewitchment.common.core.helper.MobHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.proxy.ISidedProxy;
import com.bewitchment.common.core.statics.ModLootTables;
import com.bewitchment.common.crafting.FrostFireRecipe;
import com.bewitchment.common.crafting.ModDistilleryRecipes;
import com.bewitchment.common.crafting.ModOvenSmeltingRecipes;
import com.bewitchment.common.crafting.ModSpinningThreadRecipes;
import com.bewitchment.common.entity.ModEntities;
import com.bewitchment.common.integration.patchouli.Patchouli;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompatBridge;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.world.EntityPlacementHelper;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
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
		MinecraftForge.EVENT_BUS.register(new LootTableEventHandler());
		ApiInstance.initAPI();
		MobHelper.init();
		SimpleCapability.setup(NetworkHandler.HANDLER);
		CapabilityFortune.init();
		InfusionCapability.init();
		MagicPowerUsingItem.init();
		MagicPowerContainer.init();
		MagicPowerConsumer.init();
		CapabilityBloodReserve.init();
		CapabilityCauldronTeleport.init();
		CapabilityMimicData.init();
		CapabilityMPExpansion.init();
		ModAbilities.dummyMethodToLoadClass();
		SimpleCapability.preInit(BarkCapability.class);
		SimpleCapability.preInit(CapabilityWerewolfStatus.class);
		SimpleCapability.preInit(CapabilityTransformation.class);
		SimpleCapability.preInit(CapabilityVampire.class);
		NetworkHandler.init();
		ModInfusions.init();
		ModTransformations.init();
		ModEntities.init();
		ModSpells.init();
		ModFortunes.init();
		ModLootTables.registerLootTables();
		FrostFireRecipe.init();
		proxy.preInit(event);
		ThaumcraftCompatBridge.loadThaumcraftCompat();
		EntityPlacementHelper.init();

		logger.info("Remember when I told you how my");
		logger.info("Kin is different in some ways?");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		ModPotions.init();
		SimpleCapability.init(BarkCapability.class, LibMod.MOD_ID, BarkCapability.CAPABILITY, BarkCapability.DEFAULT_INSTANCE);
		SimpleCapability.init(CapabilityWerewolfStatus.class, LibMod.MOD_ID, CapabilityWerewolfStatus.CAPABILITY, CapabilityWerewolfStatus.DEFAULT_INSTANCE);
		SimpleCapability.init(CapabilityTransformation.class, LibMod.MOD_ID, CapabilityTransformation.CAPABILITY, CapabilityTransformation.DEFAULT_INSTANCE);
		SimpleCapability.init(CapabilityVampire.class, LibMod.MOD_ID, CapabilityVampire.CAPABILITY, CapabilityVampire.DEFAULT_INSTANCE);
		ModItems.init();
		ModBlocks.init();
		ModTarots.init();
		CauldronRegistry.init();
		ModDistilleryRecipes.init();
		CropHelper.initSeedDrops();
		ModGen.init();
		ModSpinningThreadRecipes.init();
		ModOvenSmeltingRecipes.init();
		ModRituals.init();
		ModBrewModifiers.init();
		Patchouli.init();
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
		event.registerServerCommand(new CommandCreateTaglock());
	}

	@EventHandler
	public void setFlight(FMLServerStartedEvent evt) {
		FMLCommonHandler.instance().getMinecraftServerInstance().setAllowFlight(true);
	}
}
