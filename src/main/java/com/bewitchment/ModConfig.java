package com.bewitchment;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ModConfig extends Configuration {
	public final List<String> broomSweepables;
	
	public final List<String> heatSources;
	
	public final int altarScansPerTick;
	
	public final int maxGrimoirePower;
	
	public final List<Integer> worldGenWhitelist;
	public final int cypressChance, elderChance, juniperChance, yewChance;
	
	public final int silverSize, silverChance, silverMin, silverMax, saltSize, saltChance, saltMin, saltMax, amethystSize, amethystChance, amethystMin, amethystMax, garnetSize, garnetChance, garnetMin, garnetMax, opalSize, opalChance, opalMin, opalMax;
	
	public final List<String> lizardBiomes, owlBiomes, ravenBiomes, snakeBiomes, toadBiomes, blackDogBiomes, hellhoundBiomes, feuerwurmBiomes;
	public final int lizardWeight, lizardMin, lizardMax, owlWeight, owlMin, owlMax, ravenWeight, ravenMin, ravenMax, snakeWeight, snakeMin, snakeMax, toadWeight, toadMin, toadMax, blackDogWeight, blackDogMin, blackDogMax, hellhoundWeight, hellhoundMin, hellhoundMax, feuerwurmWeight, feuerwurmMin, feuerwurmMax;
	
	public final boolean enableCatsAndDogsFortune;
	
	public final boolean wednesday;
	
	public ModConfig(File file) {
		super(file);
		load();
		broomSweepables = Arrays.asList(getStringList("broomSweepables", "misc", new String[]{Blocks.REDSTONE_WIRE.getTranslationKey(), "tile.bewitchment.glyph", "tile.bewitchment.salt_barrier"}, "The list of blocks that the broom will sweep when right clicked on"));
		
		heatSources = Arrays.asList(getStringList("heatSources", "misc", new String[]{Blocks.FIRE.getTranslationKey(), Blocks.LAVA.getTranslationKey(), Blocks.MAGMA.getTranslationKey()}, "The list of blocks that the witches' cauldron will count as heat sources"));
		
		altarScansPerTick = getInt("altarScansPerTick", "misc", 64, 0, Short.MAX_VALUE, "The amount of blocks an altar should scan per tick.");
		
		maxGrimoirePower = getInt("maxGrimoirePower", "misc", 1000, 0, Integer.MAX_VALUE, "The maximum power a Grimoire Magia can have.");
		
		worldGenWhitelist = new ArrayList<>();
		for (int i : get("worldGenWhitelist", "worldGen", new int[]{0}, "The dimensions that trees, ores, and coquina are allowed to spawn in.").getIntList()) worldGenWhitelist.add(i);
		cypressChance = getInt("cypressChance", "worldGen/tree", 20, 0, Byte.MAX_VALUE, "The chance for cypress trees to spawn. Set to 0 to disable.");
		elderChance = getInt("elderChance", "worldGen/tree", 20, 0, Byte.MAX_VALUE, "The chance for elder trees to spawn. Set to 0 to disable.");
		juniperChance = getInt("juniperChance", "worldGen/tree", 20, 0, Byte.MAX_VALUE, "The chance for juniper trees to spawn. Set to 0 to disable.");
		yewChance = getInt("yewChance", "worldGen/tree", 20, 0, Byte.MAX_VALUE, "The chance for elder trees to spawn. Set to 0 to disable.");
		
		silverSize = getInt("silverSize", "worldGen/ore", 7, 0, Byte.MAX_VALUE, "The size of silver ore veins.");
		silverChance = getInt("silverChance", "worldGen/ore", 12, 0, Byte.MAX_VALUE, "The chance for silver ore veins to spawn. 0 to disable.");
		silverMin = getInt("silverMin", "worldGen/ore", 10, 0, 255, "The minimum height for silver ore veins to spawn.");
		silverMax = getInt("silverMax", "worldGen/ore", 128, 0, 255, "The maximum height for silver ore veins to spawn.");
		
		saltSize = getInt("saltSize", "worldGen/ore", 5, 0, Byte.MAX_VALUE, "The size of salt ore veins.");
		saltChance = getInt("saltChance", "worldGen/ore", 10, 0, Byte.MAX_VALUE, "The chance for salt ore veins to spawn. 0 to disable.");
		saltMin = getInt("saltMin", "worldGen/ore", 10, 0, 255, "The minimum height for salt ore veins to spawn.");
		saltMax = getInt("saltMax", "worldGen/ore", 120, 0, 255, "The maximum height for salt ore veins to spawn.");
		
		amethystSize = getInt("amethystSize", "worldGen/ore", 6, 0, Byte.MAX_VALUE, "The size of amethyst ore veins.");
		amethystChance = getInt("amethystChance", "worldGen/ore", 2, 0, Byte.MAX_VALUE, "The chance for amethyst ore veins to spawn. 0 to disable.");
		amethystMin = getInt("amethystMin", "worldGen/ore", 10, 0, 255, "The minimum height for amethyst ore veins to spawn.");
		amethystMax = getInt("amethystMax", "worldGen/ore", 42, 0, 255, "The maximum height for amethyst ore veins to spawn.");
		
		garnetSize = getInt("garnetSize", "worldGen/ore", 6, 0, Byte.MAX_VALUE, "The size of garnet ore veins.");
		garnetChance = getInt("garnetChance", "worldGen/ore", 2, 0, Byte.MAX_VALUE, "The chance for garnet ore veins to spawn. 0 to disable.");
		garnetMin = getInt("garnetMin", "worldGen/ore", 12, 0, 255, "The minimum height for garnet ore veins to spawn.");
		garnetMax = getInt("garnetMax", "worldGen/ore", 42, 0, 255, "The maximum height for garnet ore veins to spawn.");
		
		opalSize = getInt("opalSize", "worldGen/ore", 6, 0, Byte.MAX_VALUE, "The size of opal ore veins.");
		opalChance = getInt("opalChance", "worldGen/ore", 2, 0, Byte.MAX_VALUE, "The chance for opal ore veins to spawn. 0 to disable.");
		opalMin = getInt("opalMin", "worldGen/ore", 16, 0, 255, "The minimum height for opal ore veins to spawn.");
		opalMax = getInt("opalMax", "worldGen/ore", 42, 0, 255, "The maximum height for opal ore veins to spawn.");
		
		lizardBiomes = Arrays.asList(getStringList("lizardBiomes", "mobSpawns/lizard", new String[]{Type.FOREST.getName()}, "The list of BiomeDictionary types that the lizard will spawn in."));
		lizardWeight = getInt("lizardWeight", "mobSpawns/lizard", 20, 0, Byte.MAX_VALUE, "The weight chance for lizards to spawn");
		lizardMin = getInt("lizardMin", "mobSpawns/lizard", 1, 0, Byte.MAX_VALUE, "The minimum amount of lizards to spawn at once");
		lizardMax = getInt("lizardMax", "mobSpawns/lizard", 4, 0, Byte.MAX_VALUE, "The maximum amount of lizards to spawn at once");
		
		owlBiomes = Arrays.asList(getStringList("owlBiomes", "mobSpawns/owl", new String[]{Type.FOREST.getName(), Type.DENSE.getName()}, "The list of BiomeDictionary types that the owl will spawn in."));
		owlWeight = getInt("owlWeight", "mobSpawns/owl", 20, 0, Byte.MAX_VALUE, "The weight chance for owls to spawn");
		owlMin = getInt("owlMin", "mobSpawns/owl", 1, 0, Byte.MAX_VALUE, "The minimum amount of owls to spawn at once");
		owlMax = getInt("owlMax", "mobSpawns/owl", 4, 0, Byte.MAX_VALUE, "The maximum amount of owls to spawn at once");
		
		ravenBiomes = Arrays.asList(getStringList("ravenBiomes", "mobSpawns/raven", new String[]{Type.PLAINS.getName(), Type.WASTELAND.getName()}, "The list of BiomeDictionary types that the raven will spawn in."));
		ravenWeight = getInt("ravenWeight", "mobSpawns/raven", 20, 0, Byte.MAX_VALUE, "The weight chance for ravens to spawn");
		ravenMin = getInt("ravenMin", "mobSpawns/raven", 1, 0, Byte.MAX_VALUE, "The minimum amount of ravens to spawn at once");
		ravenMax = getInt("ravenMax", "mobSpawns/raven", 4, 0, Byte.MAX_VALUE, "The maximum amount of ravens to spawn at once");
		
		snakeBiomes = Arrays.asList(getStringList("snakeBiomes", "mobSpawns/snake", new String[]{Type.PLAINS.getName(), Type.HILLS.getName()}, "The list of BiomeDictionary types that the snake will spawn in."));
		snakeWeight = getInt("snakeWeight", "mobSpawns/snake", 20, 0, Byte.MAX_VALUE, "The weight chance for snakes to spawn");
		snakeMin = getInt("snakeMin", "mobSpawns/snake", 1, 0, Byte.MAX_VALUE, "The minimum amount of snakes to spawn at once");
		snakeMax = getInt("snakeMax", "mobSpawns/snake", 4, 0, Byte.MAX_VALUE, "The maximum amount of snakes to spawn at once");
		
		toadBiomes = Arrays.asList(getStringList("toadBiomes", "mobSpawns/toad", new String[]{Type.SWAMP.getName()}, "The list of BiomeDictionary types that the toad will spawn in."));
		toadWeight = getInt("toadWeight", "mobSpawns/toad", 20, 0, Byte.MAX_VALUE, "The weight chance for toads to spawn");
		toadMin = getInt("toadMin", "mobSpawns/toad", 1, 0, Byte.MAX_VALUE, "The minimum amount of toads to spawn at once");
		toadMax = getInt("toadMax", "mobSpawns/toad", 4, 0, Byte.MAX_VALUE, "The maximum amount of toads to spawn at once");
		
		blackDogBiomes = Arrays.asList(getStringList("blackDogBiomes", "mobSpawns/blackDog", new String[]{Type.PLAINS.getName(), Type.WASTELAND.getName(), Type.FOREST.getName(), Type.SPOOKY.getName()}, "The list of BiomeDictionary types that the black dog will spawn in."));
		blackDogWeight = getInt("blackDogWeight", "mobSpawns/blackDog", 6, 0, Byte.MAX_VALUE, "The weight chance for black dogs to spawn");
		blackDogMin = getInt("blackDogMin", "mobSpawns/blackDog", 1, 0, Byte.MAX_VALUE, "The minimum amount of black dogs to spawn at once");
		blackDogMax = getInt("blackDogMax", "mobSpawns/blackDog", 4, 0, Byte.MAX_VALUE, "The maximum amount of black dogs to spawn at once");
		
		hellhoundBiomes = Arrays.asList(getStringList("hellhoundBiomes", "mobSpawns/hellhound", new String[]{Type.NETHER.getName()}, "The list of BiomeDictionary types that the hellhound will spawn in."));
		hellhoundWeight = getInt("hellhoundWeight", "mobSpawns/hellhound", 6, 0, Byte.MAX_VALUE, "The weight chance for hellhounds to spawn");
		hellhoundMin = getInt("hellhoundMin", "mobSpawns/hellhound", 1, 0, Byte.MAX_VALUE, "The minimum amount of hellhounds to spawn at once");
		hellhoundMax = getInt("hellhoundMax", "mobSpawns/hellhound", 4, 0, Byte.MAX_VALUE, "The maximum amount of hellhounds to spawn at once");
		
		feuerwurmBiomes = Arrays.asList(getStringList("feuerwurmBiomes", "mobSpawns/feuerwurm", new String[]{Type.NETHER.getName()}, "The list of BiomeDictionary types that the feuerwurm will spawn in."));
		feuerwurmWeight = getInt("feuerwurmWeight", "mobSpawns/feuerwurm", 6, 0, Byte.MAX_VALUE, "The weight chance for feuerwurms to spawn");
		feuerwurmMin = getInt("feuerwurmMin", "mobSpawns/feuerwurm", 1, 0, Byte.MAX_VALUE, "The minimum amount of feuerwurms to spawn at once");
		feuerwurmMax = getInt("feuerwurmMax", "mobSpawns/feuerwurm", 4, 0, Byte.MAX_VALUE, "The maximum amount of feuerwurms to spawn at once");
		
		enableCatsAndDogsFortune = getBoolean("enableCatsAndDogsFortune", "memes", false, "Decides whether or not the cats and dogs will be enabled");
		wednesday = getBoolean("wednesday", "memes", false, "wednesday");
		save();
	}
}