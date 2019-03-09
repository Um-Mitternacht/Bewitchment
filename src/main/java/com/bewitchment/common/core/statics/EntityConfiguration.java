package com.bewitchment.common.core.statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.entity.ModEntityContainer;
import com.bewitchment.common.entity.ModEntities;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;

/**
 * @author its_meow
 *
 * Mar 8, 2019
 */
public class EntityConfiguration {

	public static HashMap<ModEntityContainer, EntityConfigurationSection> sections = new HashMap<ModEntityContainer, EntityConfigurationSection>();

	public static void readConfig(){
		Configuration cfg = Bewitchment.entityConfig;
		try {
			cfg.load();
			initConfig(cfg);
		} catch (Exception e1) {
			Log.e("Mod " + LibMod.MOD_ID + " failed to load configuration. Report this here: https://github.com/Um-Mitternacht/Bewitchment/issues/new\n\n");
			e1.printStackTrace();
			Log.e("Mod " + LibMod.MOD_ID + " failed to load configuration. Report this here: https://github.com/Um-Mitternacht/Bewitchment/issues/new\n\n");
		} finally {
			if(cfg.hasChanged()){
				cfg.save();
			}
		}
	}

	public static void initConfig(Configuration cfg) {
		for(ModEntityContainer container : ModEntities.entityList) {
			String[] biomeStrings = new String[container.spawnBiomes.length];
			for(int i = 0; i < container.spawnBiomes.length; i++) {
				biomeStrings[i] = container.spawnBiomes[i].getRegistryName().toString();
			}
			EntityConfigurationSection configSection = new EntityConfigurationSection(container.entityClazz, container.entityName, container.minGroup, container.maxGroup, container.weight, biomeStrings);
			sections.put(container, configSection);
		}
		for(ModEntityContainer container : sections.keySet()) {
			EntityConfigurationSection section = sections.get(container);
			container.maxGroup = section.max;
			container.minGroup = section.min;
			container.weight = section.weight;
			container.doRegister = section.doRegister;
			container.doSpawning = section.doSpawning;

			// Parse biomes
			List<Biome> biomesList = new ArrayList<Biome>();
			for(String biomeID : section.biomesList) {
				Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(biomeID));
				if(biome == null) { // Could not get biome with ID
					Log.e("Invalid biome configuration entered for entity \"" + container.entityName + "\" (biome was mistyped or a biome mod was removed?): " + biomeID);
				} else { // Valid biome
					biomesList.add(biome);
				}
			}
			// Get as array
			Biome[] biomes = new Biome[biomesList.size()];
			for (int i = 0; i < biomesList.size(); i++)
			{
				biomes[i] = biomesList.get(i);
			}

			container.spawnBiomes = biomes;
		}
	}

}
