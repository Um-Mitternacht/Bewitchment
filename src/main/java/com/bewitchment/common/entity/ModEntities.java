package com.bewitchment.common.entity;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.entity.spirits.demons.EntityUran;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModEntities {

	private ModEntities() {
	}

	public static void init() {
		int id = 0;

		// Utility entities
		EntityRegistry.registerModEntity(getResource("spell_carrier"), EntitySpellCarrier.class, "spell_carrier", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("broom"), EntityFlyingBroom.class, "broom", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("swarm"), EntityBatSwarm.class, "swarm", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_arrow"), EntityBrewArrow.class, "brew_arrow", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_bottle"), EntityBrew.class, "brew_bottle", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_lingering_effect"), EntityLingeringBrew.class, "brew_lingering_effect", id++, Bewitchment.instance, 64, 100, false);
		EntityRegistry.registerModEntity(getResource("brew_aoe_effect"), EntityAoE.class, "brew_aoe_effect", id++, Bewitchment.instance, 64, 100, false);

		// Mob entities
		EntityRegistry.registerModEntity(getResource("owl"), EntityOwl.class, "entity_owl", id++, Bewitchment.instance, 64, 1, true, 0xAF813F, 0x6E5127);
		EntityRegistry.registerModEntity(getResource("snake"), EntitySnake.class, "entity_snake", id++, Bewitchment.instance, 64, 1, true, 0x8F9779, 0x696969);
		EntityRegistry.registerModEntity(getResource("raven"), EntityRaven.class, "entity_raven", id++, Bewitchment.instance, 64, 1, true, 0x222222, 0x280638);
		EntityRegistry.registerModEntity(getResource("toad"), EntityToad.class, "entity_toad", id++, Bewitchment.instance, 64, 1, true, 0xA9BA9D, 0xC3B091);
		//Non-familiar animals
		EntityRegistry.registerModEntity(getResource("blindworm"), EntityBlindworm.class, "entity_blindworm", id++, Bewitchment.instance, 64, 1, true, 0x826644, 0xD2B48C);
		EntityRegistry.registerModEntity(getResource("newt"), EntityNewt.class, "entity_newt", id++, Bewitchment.instance, 64, 1, true, 0x000000, 0xFFD300);
		EntityRegistry.registerModEntity(getResource("lizard"), EntityLizard.class, "entity_lizard", id++, Bewitchment.instance, 64, 1, true, 0x568203, 0x0070BB);
		//Demons
		EntityRegistry.registerModEntity(getResource("uran"), EntityUran.class, "entity_uran", id++, Bewitchment.instance, 64, 1, true, 0x568203, 0x0070BB);

		List<Biome> validOwl = BiomeDictionary.getBiomes(Type.FOREST).stream()
				.filter(b -> BiomeDictionary.hasType(b, Type.DENSE))
				.peek(b -> Log.d("Valid owl biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());
		List<Biome> validSnake = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.PLAINS) || BiomeDictionary.hasType(b, Type.HILLS))
				.peek(b -> Log.d("Valid snake biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		Set<Biome> validToad = BiomeDictionary.getBiomes(Type.SWAMP);
		validToad.forEach(b -> Log.d("Valid toad biome found: " + b.getRegistryName()));

		Set<Biome> validNewt = BiomeDictionary.getBiomes(Type.SWAMP);
		validNewt.forEach(b -> Log.d("Valid newt biome found: " + b.getRegistryName()));

		List<Biome> validRaven = Biome.REGISTRY.getKeys().stream()
				.map(rl -> Biome.REGISTRY.getObject(rl))
				.filter(b -> BiomeDictionary.hasType(b, Type.PLAINS) || BiomeDictionary.hasType(b, Type.WASTELAND))
				.peek(b -> Log.d("Valid raven biome found: " + b.getRegistryName()))
				.collect(Collectors.toList());

		Set<Biome> validBlindworm = BiomeDictionary.getBiomes(Type.FOREST);
		validBlindworm.forEach(b -> Log.d("Valid blindworm biome found: " + b.getRegistryName()));

		Set<Biome> validLizard = BiomeDictionary.getBiomes(Type.FOREST);
		validLizard.forEach(b -> Log.d("Valid lizard biome found: " + b.getRegistryName()));

		Biome[] biomesOwl = new Biome[validOwl.size()];
		Biome[] biomesSnake = new Biome[validSnake.size()];
		Biome[] biomesToad = new Biome[validToad.size()];
		Biome[] biomesRaven = new Biome[validRaven.size()];
		Biome[] biomesBlindworm = new Biome[validBlindworm.size()];
		Biome[] biomesNewt = new Biome[validNewt.size()];
		Biome[] biomesLizard = new Biome[validLizard.size()];

		EntityRegistry.addSpawn(EntityOwl.class, 8, 1, 1, EnumCreatureType.CREATURE, validOwl.toArray(biomesOwl));
		EntityRegistry.addSpawn(EntitySnake.class, 5, 1, 1, EnumCreatureType.CREATURE, validSnake.toArray(biomesSnake));
		EntityRegistry.addSpawn(EntityToad.class, 2, 1, 1, EnumCreatureType.CREATURE, validToad.toArray(biomesToad));
		EntityRegistry.addSpawn(EntityRaven.class, 3, 1, 1, EnumCreatureType.CREATURE, validRaven.toArray(biomesRaven));
		EntityRegistry.addSpawn(EntityBlindworm.class, 5, 1, 1, EnumCreatureType.CREATURE, validBlindworm.toArray(biomesBlindworm));
		EntityRegistry.addSpawn(EntityNewt.class, 2, 1, 1, EnumCreatureType.CREATURE, validNewt.toArray(biomesNewt));
		EntityRegistry.addSpawn(EntityLizard.class, 4, 1, 1, EnumCreatureType.CREATURE, validLizard.toArray(biomesLizard));
	}

	private static ResourceLocation getResource(String name) {
		return new ResourceLocation(LibMod.MOD_ID, name);
	}
}
