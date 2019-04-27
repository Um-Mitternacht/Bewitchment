package com.bewitchment.common.entity;

import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.entity.spirits.demons.*;
import com.bewitchment.common.entity.spirits.ghosts.EntityBlackDog;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 * <p>
 * Rewritten by its_meow on
 * March 8th, 2019 - Uses EntityEntry
 */
public final class ModEntities {

	private static final String LOCALIZE_PREFIX = LibMod.MOD_ID + ".";
	public static int modEntities = 0;
	public static LinkedHashSet<EntityEntry> entrySet = new LinkedHashSet<EntityEntry>();
	public static LinkedHashSet<ModEntityContainer> entityList = new LinkedHashSet<ModEntityContainer>();
	private ModEntities() {
	}

	public static void fillEntityContainers() {

		/* Biome lists */
		List<Biome> validOwl = BiomeDictionary.getBiomes(Type.FOREST).stream()
				.filter(b -> BiomeDictionary.hasType(b, Type.DENSE) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid owl biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validSnake = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.PLAINS) || BiomeDictionary.hasType(b, Type.HILLS) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid snake biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validToad = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.SWAMP) || !BiomeDictionary.hasType(b, Type.DRY) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid toad biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validNewt = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.SWAMP) || !BiomeDictionary.hasType(b, Type.DRY) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid newt biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validRaven = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.PLAINS) || BiomeDictionary.hasType(b, Type.WASTELAND) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid raven biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validBlindworm = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.FOREST) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid blindworm biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validLizard = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.FOREST) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid lizard biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validUran = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid uran biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validHellhound = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid hellhound biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validHellhoundAlpha = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.SNOWY) || !BiomeDictionary.hasType(b, Type.COLD) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid hellhound alpha biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		List<Biome> validBlackDog = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.PLAINS) || BiomeDictionary.hasType(b, Type.WASTELAND) || BiomeDictionary.hasType(b, Type.FOREST) || !BiomeDictionary.hasType(b, Type.NETHER) || !BiomeDictionary.hasType(b, Type.VOID))
				.peek(b -> Log.d("Valid black dog biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		// Nonliving entities
		register(EntitySpellCarrier.class, "spell_carrier");
		register(EntityFlyingBroom.class, "broom");
		register(EntityBatSwarm.class, "swarm");
		register(EntityBrewArrow.class, "brew_arrow");
		register(EntityBrew.class, "brew_bottle");
		register(EntityLingeringBrew.class, "brew_lingering_effect", 64, 100, false);
		register(EntityAoE.class, "brew_aoe_effect", 64, 100, false);

		/* Living Entities */

		// Familiar Animals
		entityList.add(new ModEntityContainer(EntityOwl.class, "owl", EnumCreatureType.CREATURE, 0xAF813F, 0x6e5127, 20, 1, 4, validOwl));
		entityList.add(new ModEntityContainer(EntitySnake.class, "snake", EnumCreatureType.CREATURE, 0x8F9779, 0x696969, 20, 1, 4, validSnake));
		entityList.add(new ModEntityContainer(EntityRaven.class, "raven", EnumCreatureType.CREATURE, 0x222222, 0x280638, 20, 1, 4, validRaven));
		entityList.add(new ModEntityContainer(EntityToad.class, "toad", EnumCreatureType.CREATURE, 0xA9BA9D, 0xC3B091, 20, 1, 4, validToad));
		//Non-Familiar animals
		entityList.add(new ModEntityContainer(EntityBlindworm.class, "blindworm", EnumCreatureType.CREATURE, 0x826644, 0xD2B48C, 20, 1, 4, validBlindworm));
		entityList.add(new ModEntityContainer(EntityNewt.class, "newt", EnumCreatureType.CREATURE, 0x000000, 0xFFD300, 20, 1, 4, validNewt));
		entityList.add(new ModEntityContainer(EntityLizard.class, "lizard", EnumCreatureType.CREATURE, 0x568203, 0x0070BB, 20, 1, 4, validLizard));
		//Naturally Spawning Demons
		entityList.add(new ModEntityContainer(EntityUran.class, "uran", EnumCreatureType.MONSTER, 0x555555, 0xFF9966, 6, 1, 4, validUran));
		entityList.add(new ModEntityContainer(EntityHellhound.class, "hellhound", EnumCreatureType.MONSTER, 0x555555, 0xED2939, 6, 1, 4, validHellhound));
		entityList.add(new ModEntityContainer(EntityHellhoundAlpha.class, "hellhound_alpha", EnumCreatureType.MONSTER, 0x555555, 0xED2939, 1, 1, 1, validHellhoundAlpha));
		//Non-Spawning Demons
		registerWithEgg(EntityDemon.class, "demon", 0x555555, 0xED2939);
		registerWithEgg(EntityDemoness.class, "demoness", 0x555555, 0xED2939);
		registerWithEgg(EntityImp.class, "imp", 0x555555, 0xED2939);
		//Ghosts
		entityList.add(new ModEntityContainer(EntityBlackDog.class, "black_dog", EnumCreatureType.MONSTER, 0x000000, 0x000000, 20, 1, 2, validBlackDog));
	}

	public static void reg(ModEntityContainer c) {
		if (c.doSpawning) {
			registerWithSpawnAndEgg(c.entityClazz, c.entityName, c.eggColorSolid, c.eggColorSpot, c.type, c.weight, c.minGroup, c.maxGroup, c.spawnBiomes);
		} else {
			registerWithEgg(c.entityClazz, c.entityName, c.eggColorSolid, c.eggColorSpot);
		}
	}

	public static void registerWithSpawnAndEgg(Class<? extends Entity> EntityClass, String entityNameIn, int solidColorIn, int spotColorIn, EnumCreatureType typeIn, int prob, int min, int max, Biome[] biomes) {
		EntityEntry entry = EntityEntryBuilder.create()
				.entity(EntityClass)
				.id(new ResourceLocation(LibMod.MOD_ID, entityNameIn), modEntities++)
				.name(LOCALIZE_PREFIX + entityNameIn)
				.tracker(64, 1, true)
				.egg(solidColorIn, spotColorIn)
				.spawn(typeIn, prob, min, max, biomes)
				.build();
		if (typeIn == EnumCreatureType.WATER_CREATURE) {
			EntitySpawnPlacementRegistry.setPlacementType(EntityClass, SpawnPlacementType.IN_WATER);
		}
		entrySet.add(entry);
	}

	public static void registerWithEgg(Class<? extends Entity> EntityClass, String entityNameIn, int solidColorIn, int spotColorIn) {
		EntityEntry entry = EntityEntryBuilder.create()
				.entity(EntityClass)
				.id(new ResourceLocation(LibMod.MOD_ID, entityNameIn), modEntities++)
				.name(LOCALIZE_PREFIX + entityNameIn)
				.tracker(64, 1, true)
				.egg(solidColorIn, spotColorIn)
				.build();
		entrySet.add(entry);
	}

	public static void register(Class<? extends Entity> EntityClass, String entityNameIn) {
		EntityEntry entry = EntityEntryBuilder.create()
				.entity(EntityClass)
				.id(new ResourceLocation(LibMod.MOD_ID, entityNameIn), modEntities++)
				.name(LOCALIZE_PREFIX + entityNameIn)
				.tracker(64, 1, true)
				.build();

		entrySet.add(entry);
	}

	public static void register(Class<? extends Entity> EntityClass, String entityNameIn, int range, int updateFrequency, boolean sendVelocity) {
		EntityEntry entry = EntityEntryBuilder.create()
				.entity(EntityClass)
				.id(new ResourceLocation(LibMod.MOD_ID, entityNameIn), modEntities++)
				.name(LOCALIZE_PREFIX + entityNameIn)
				.tracker(range, updateFrequency, sendVelocity)
				.build();

		entrySet.add(entry);
	}


	//####################################################################################

	@EventBusSubscriber(modid = LibMod.MOD_ID)
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void onEvent(final RegistryEvent.Register<EntityEntry> event) {
			final IForgeRegistry<EntityEntry> registry = event.getRegistry();

			for (ModEntityContainer container : entityList) {
				if (container.doRegister)
					reg(container);
			}

			if (!entrySet.isEmpty()) {
				for (final EntityEntry entityEntry : entrySet) {

					registry.register(entityEntry);
				}
			}
		}
	}
}
