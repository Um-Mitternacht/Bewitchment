package com.bewitchment.common.entity;

import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.entity.spirits.demons.*;
import com.bewitchment.common.entity.spirits.ghosts.EntityBlackDog;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashSet;

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

	static {

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
		entityList.add(new ModEntityContainer(EntityOwl.class, "owl", EnumCreatureType.CREATURE, 0xAF813F, 0x6e5127, 20, 1, 4, Type.FOREST, Type.DENSE, Type.SPOOKY, Type.SNOWY));
		entityList.add(new ModEntityContainer(EntitySnake.class, "snake", EnumCreatureType.CREATURE, 0x8F9779, 0x696969, 20, 1, 4, Type.FOREST, Type.JUNGLE, Type.DENSE, Type.HILLS));
		entityList.add(new ModEntityContainer(EntityRaven.class, "raven", EnumCreatureType.CREATURE, 0x222222, 0x280638, 20, 1, 4, Type.FOREST, Type.SPOOKY, Type.DEAD));
		entityList.add(new ModEntityContainer(EntityToad.class, "toad", EnumCreatureType.CREATURE, 0xA9BA9D, 0xC3B091, 20, 1, 4, Type.SWAMP, Type.LUSH, Type.WET, Type.JUNGLE));
		//Non-Familiar animals
		entityList.add(new ModEntityContainer(EntityBlindworm.class, "blindworm", EnumCreatureType.CREATURE, 0x826644, 0xD2B48C, 20, 1, 4, Type.FOREST));
		entityList.add(new ModEntityContainer(EntityNewt.class, "newt", EnumCreatureType.CREATURE, 0x000000, 0xFFD300, 20, 1, 4, Type.SWAMP, Type.LUSH, Type.WET, Type.JUNGLE));
		entityList.add(new ModEntityContainer(EntityLizard.class, "lizard", EnumCreatureType.CREATURE, 0x568203, 0x0070BB, 20, 1, 4, Type.FOREST, Type.JUNGLE));
		//Naturally Spawning Demons
		entityList.add(new ModEntityContainer(EntityUran.class, "uran", EnumCreatureType.MONSTER, 0x555555, 0xFF9966, 6, 1, 4, Type.NETHER));
		entityList.add(new ModEntityContainer(EntityHellhound.class, "hellhound", EnumCreatureType.MONSTER, 0x555555, 0xED2939, 6, 1, 4, Type.NETHER));
		entityList.add(new ModEntityContainer(EntityHellhoundAlpha.class, "hellhound_alpha", EnumCreatureType.MONSTER, 0x555555, 0xED2939, 1, 1, 1, Type.NETHER));
		//Non-Spawning Demons
		registerWithEgg(EntityDemon.class, "demon", 0x555555, 0xED2939, EnumCreatureType.MONSTER);
		registerWithEgg(EntityDemoness.class, "demoness", 0x555555, 0xED2939, EnumCreatureType.MONSTER);
		registerWithEgg(EntityImp.class, "imp", 0x555555, 0xED2939, EnumCreatureType.MONSTER);
		//Ghosts
		entityList.add(new ModEntityContainer(EntityBlackDog.class, "black_dog", EnumCreatureType.MONSTER, 0x000000, 0x000000, 20, 1, 4, Type.PLAINS, Type.SPOOKY, Type.FOREST, Type.DEAD));
	}

	private ModEntities() {
	}

	public static void reg(ModEntityContainer c) {
		registerWithEgg(c.entityClazz, c.entityName, c.eggColorSolid, c.eggColorSpot, c.type);
	}

	public static void registerWithEgg(Class<? extends Entity> EntityClass, String entityNameIn, int solidColorIn, int spotColorIn, EnumCreatureType type) {
		EntityEntry entry = EntityEntryBuilder.create()
				.entity(EntityClass)
				.id(new ResourceLocation(LibMod.MOD_ID, entityNameIn), modEntities++)
				.name(LOCALIZE_PREFIX + entityNameIn)
				.tracker(64, 1, true)
				.egg(solidColorIn, spotColorIn)
				.build();
		if (type == EnumCreatureType.WATER_CREATURE) {
			EntitySpawnPlacementRegistry.setPlacementType(EntityClass, SpawnPlacementType.IN_WATER);
		}
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