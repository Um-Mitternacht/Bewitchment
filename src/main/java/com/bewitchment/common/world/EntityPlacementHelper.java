package com.bewitchment.common.world;

import com.bewitchment.common.entity.living.animals.EntityOwl;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

/**
 * Created by Joseph on 8/28/2018.
 */
//Todo: Rework this and SpawnHelper. Probably rename the classes too. This is just Ice and Fire code ripped apart until something remotely passable came out.
public class EntityPlacementHelper {

	public static void init() {
		for (Biome biome : Biome.REGISTRY) {
			if (biome != null && BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
				List<Biome.SpawnListEntry> spawnList = biome.getSpawnableList(EnumCreatureType.CREATURE);
				spawnList.add(new Biome.SpawnListEntry(EntityOwl.class, 25, 1, 4));
			}
		}
	}
}
