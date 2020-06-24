package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeDataBasePopulator implements IBiomeDataBasePopulator {
	private static Species cypress;
	private static Species elder;
	private static Species juniper;

	public BiomeDataBasePopulator() {
	}

	private static void createStaticAliases() {
		cypress = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "cypress"));
		elder = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "elder"));
		juniper = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "juniper"));
	}

	@Override
	public void populate(BiomeDataBase dbase) {
		if (cypress == null) createStaticAliases();

		int cypressWeight = (int) (ModConfig.worldGen.treeGen.cypressChance * 500.0F);
		int elderWeight = (int) (ModConfig.worldGen.treeGen.elderChance * 500.0F);
		int juniperWeight = (int) (ModConfig.worldGen.treeGen.juniperChance * 500.0F);
		BiomePropertySelectors.RandomSpeciesSelector cypressSelector = cypressWeight == 0 ? null : new BiomePropertySelectors.RandomSpeciesSelector().add(1000 - cypressWeight).add(cypress, cypressWeight);
		BiomePropertySelectors.RandomSpeciesSelector elderSelector = elderWeight == 0 ? null : (new BiomePropertySelectors.RandomSpeciesSelector()).add(1000 - elderWeight).add(elder, elderWeight);
		BiomePropertySelectors.RandomSpeciesSelector juniperSelector = juniperWeight == 0 ? null : (new BiomePropertySelectors.RandomSpeciesSelector()).add(1000 - juniperWeight).add(juniper, juniperWeight);

		//BiomePropertySelectors.RandomSpeciesSelector bothSelector = oliveWeight != 0 && ironWeight != 0 ? (new BiomePropertySelectors.RandomSpeciesSelector()).add(1000 - (oliveWeight + ironWeight)).add(olive, oliveWeight).add(ironwood, ironWeight) : null;

		Biome.REGISTRY.forEach(b -> {
			boolean cypressSplice = cypressSelector != null && (BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && (BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD) || BiomeDictionary.hasType(b, BiomeDictionary.Type.SPOOKY)));
			boolean elderSplice = elderSelector != null && BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && !BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD);
			boolean juniperSplice = juniperSelector != null && (BiomeDictionary.hasType(b, BiomeDictionary.Type.SAVANNA) || BiomeDictionary.hasType(b, BiomeDictionary.Type.MAGICAL));
			if (cypressSplice || elderSplice || juniperSplice) {
				dbase.setSpeciesSelector(b, cypressSplice ? cypressSelector : elderSplice ? elderSelector : juniperSelector, BiomeDataBase.Operation.SPLICE_BEFORE);
			}
		});
	}
}