package com.bewitchment;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ModConfig extends Configuration {
	public final List<String> broomSweepables;
	
	public final List<String> heatSources;
	
	public final int altarScansPerTick;
	
	public final int maxGrimoirePower;
	
	public final int cypressChance, elderChance, juniperChance, yewChance;
	
	public final int silverSize, silverChance, silverMin, silverMax, saltSize, saltChance, saltMin, saltMax, amethystSize, amethystChance, amethystMin, amethystMax, garnetSize, garnetChance, garnetMin, garnetMax, opalSize, opalChance, opalMin, opalMax;
	
	public final List<String> lizardBiomes, newtBiomes, owlBiomes, ravenBiomes, snakeBiomes, toadBiomes, blackDogBiomes, hellhoundBiomes, serpentBiomes;
	
	public final boolean enableCatsAndDogsFortune;
	
	public final boolean wednesday;
	
	public ModConfig(File file) {
		super(file);
		load();
		broomSweepables = Arrays.asList(getStringList("broomSweepables", "misc", new String[]{Blocks.REDSTONE_WIRE.getTranslationKey(), "tile.bewitchment.glyph", "tile.bewitchment.salt_barrier"}, "The list of blocks that the broom will sweep when right clicked on"));
		
		heatSources = Arrays.asList(getStringList("heatSources", "misc", new String[]{Blocks.FIRE.getTranslationKey(), Blocks.LAVA.getTranslationKey(), Blocks.MAGMA.getTranslationKey()}, "The list of blocks that the broom will sweep when right clicked on"));
		
		altarScansPerTick = getInt("altarScansPerTick", "misc", 64, 0, Short.MAX_VALUE, "The amount of blocks an altar should scan per tick.");
		
		maxGrimoirePower = getInt("maxGrimoirePower", "misc", 1000, 0, Integer.MAX_VALUE, "The maximum power a Grimoire Magia can have.");
		
		cypressChance = getInt("cypressChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for cypress trees to spawn. Set to 0 to disable.");
		elderChance = getInt("elderChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for elder trees to spawn. Set to 0 to disable.");
		juniperChance = getInt("juniperChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for juniper trees to spawn. Set to 0 to disable.");
		yewChance = getInt("yewChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for elder trees to spawn. Set to 0 to disable.");
		
		silverSize = getInt("silverSize", "oreGen", 7, 0, Byte.MAX_VALUE, "The size of silver ore veins.");
		silverChance = getInt("silverChance", "oreGen", 12, 0, Byte.MAX_VALUE, "The chance for silver ore veins to spawn. 0 to disable.");
		silverMin = getInt("silverMin", "oreGen", 10, 0, 255, "The minimum height for silver ore veins to spawn.");
		silverMax = getInt("silverMax", "oreGen", 128, 0, 255, "The maximum height for silver ore veins to spawn.");
		
		saltSize = getInt("saltSize", "oreGen", 5, 0, Byte.MAX_VALUE, "The size of salt ore veins.");
		saltChance = getInt("saltChance", "oreGen", 10, 0, Byte.MAX_VALUE, "The chance for salt ore veins to spawn. 0 to disable.");
		saltMin = getInt("saltMin", "oreGen", 10, 0, 255, "The minimum height for salt ore veins to spawn.");
		saltMax = getInt("saltMax", "oreGen", 120, 0, 255, "The maximum height for salt ore veins to spawn.");
		
		amethystSize = getInt("amethystSize", "oreGen", 6, 0, Byte.MAX_VALUE, "The size of amethyst ore veins.");
		amethystChance = getInt("amethystChance", "oreGen", 2, 0, Byte.MAX_VALUE, "The chance for amethyst ore veins to spawn. 0 to disable.");
		amethystMin = getInt("amethystMin", "oreGen", 10, 0, 255, "The minimum height for amethyst ore veins to spawn.");
		amethystMax = getInt("amethystMax", "oreGen", 42, 0, 255, "The maximum height for amethyst ore veins to spawn.");
		
		garnetSize = getInt("garnetSize", "oreGen", 6, 0, Byte.MAX_VALUE, "The size of garnet ore veins.");
		garnetChance = getInt("garnetChance", "oreGen", 2, 0, Byte.MAX_VALUE, "The chance for garnet ore veins to spawn. 0 to disable.");
		garnetMin = getInt("garnetMin", "oreGen", 12, 0, 255, "The minimum height for garnet ore veins to spawn.");
		garnetMax = getInt("garnetMax", "oreGen", 42, 0, 255, "The maximum height for garnet ore veins to spawn.");
		
		opalSize = getInt("opalSize", "oreGen", 6, 0, Byte.MAX_VALUE, "The size of opal ore veins.");
		opalChance = getInt("opalChance", "oreGen", 2, 0, Byte.MAX_VALUE, "The chance for opal ore veins to spawn. 0 to disable.");
		opalMin = getInt("opalMin", "oreGen", 16, 0, 255, "The minimum height for opal ore veins to spawn.");
		opalMax = getInt("opalMax", "oreGen", 42, 0, 255, "The maximum height for opal ore veins to spawn.");
		
		lizardBiomes = Arrays.asList(getStringList("lizardBiomes", "mobSpawns", new String[]{Type.FOREST.getName()}, "The list of BiomeDictionary types that the lizard will spawn in."));
		newtBiomes = Arrays.asList(getStringList("newtBiomes", "mobSpawns", new String[]{Type.SWAMP.getName()}, "The list of BiomeDictionary types that the newt will spawn in."));
		owlBiomes = Arrays.asList(getStringList("owlBiomes", "mobSpawns", new String[]{Type.FOREST.getName(), Type.DENSE.getName()}, "The list of BiomeDictionary types that the owl will spawn in."));
		ravenBiomes = Arrays.asList(getStringList("ravenBiomes", "mobSpawns", new String[]{Type.PLAINS.getName(), Type.WASTELAND.getName()}, "The list of BiomeDictionary types that the raven will spawn in."));
		snakeBiomes = Arrays.asList(getStringList("snakeBiomes", "mobSpawns", new String[]{Type.PLAINS.getName(), Type.HILLS.getName()}, "The list of BiomeDictionary types that the snake will spawn in."));
		toadBiomes = Arrays.asList(getStringList("toadBiomes", "mobSpawns", new String[]{Type.SWAMP.getName()}, "The list of BiomeDictionary types that the toad will spawn in."));
		blackDogBiomes = Arrays.asList(getStringList("blackDogBiomes", "mobSpawns", new String[]{Type.PLAINS.getName(), Type.WASTELAND.getName(), Type.FOREST.getName(), Type.SPOOKY.getName()}, "The list of BiomeDictionary types that the black dog will spawn in."));
		hellhoundBiomes = Arrays.asList(getStringList("hellhoundBiomes", "mobSpawns", new String[]{Type.NETHER.getName()}, "The list of BiomeDictionary types that the hellhound will spawn in."));
		serpentBiomes = Arrays.asList(getStringList("serpentBiomes", "mobSpawns", new String[]{Type.NETHER.getName()}, "The list of BiomeDictionary types that the serpent will spawn in."));
		
		enableCatsAndDogsFortune = getBoolean("enableCatsAndDogsFortune", "memes", false, "Decides whether or not the cats and dogs will be enabled");
		wednesday = getBoolean("wednesday", "memes", false, "wednesday");
		save();
	}
}