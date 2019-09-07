package com.bewitchment;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.Arrays;
import java.util.List;


@Config(modid = Bewitchment.MODID)
public class ModConfig{
	public static final Misc misc = new Misc();
	public static final TreeGen treeGen = new TreeGen();
	public static final OreGen oreGen = new OreGen();

	@Config.RequiresMcRestart
	public static final MobSpawns mobSpawns = new MobSpawns();

	public static final Memes memes = new Memes();

	public static class Misc {
		@Config.Comment("The list of blocks that the broom will sweep when right clicked on")
		public String[] broomSweepables = {Blocks.REDSTONE_WIRE.getTranslationKey(), "tile.bewitchment.glyph", "tile.bewitchment.salt_barrier"};

		@Config.Comment("The list of blocks that the broom will sweep when right clicked on")
		public String[] heatSources = {Blocks.FIRE.getTranslationKey(), Blocks.LAVA.getTranslationKey(), Blocks.MAGMA.getTranslationKey()};

		@Config.RangeInt(min = 0, max = Short.MAX_VALUE)
		@Config.Comment("The amount of blocks an altar should scan per tick.")
		public int altarScansPerTick = 64;

		@Config.RangeInt(min = 0)
		@Config.Comment("The maximum power a Grimoire Magia can have.")
		public int maxGrimoirePower = 1000;
	}

	public static class TreeGen {
		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for cypress trees to spawn. Set to 0 to disable.")
		public int cypressChance;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for elder trees to spawn. Set to 0 to disable.")
		public int elderChance;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for juniper trees to spawn. Set to 0 to disable.")
		public int juniperChance;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for yew trees to spawn. Set to 0 to disable.")
		public int yewChance; //replace static with nothing once static classes are in
	}

	public static class OreGen {
		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The size of silver ore veins.")
		public int silverSize = 7;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for silver ore veins to spawn. 0 to disable.")
		public int silverChance = 12;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The minimum height for silver ore veins to spawn.")
		public int silverMin = 10;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The maximum height for silver ore veins to spawn.")
		public int silverMax = 128;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The size of salt ore veins.")
		public int saltSize = 5;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for salt ore veins to spawn. 0 to disable.")
		public int saltChance = 10;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The minimum height for salt ore veins to spawn.")
		public int saltMin = 10;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The maximum height for salt ore veins to spawn.")
		public int saltMax = 120;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The size of amethyst ore veins.")
		public int amethystSize = 6;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for amethyst ore veins to spawn. 0 to disable.")
		public int amethystChance = 2;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The minimum height for amethyst ore veins to spawn.")
		public int amethystMin = 10;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The maximum height for amethyst ore veins to spawn.")
		public int amethystMax = 42;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The size of garnet ore veins.")
		public int garnetSize = 6;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for garnet ore veins to spawn. 0 to disable.")
		public int garnetChance = 2;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The minimum height for garnet ore veins to spawn.")
		public int garnetMin = 12;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The maximum height for garnet ore veins to spawn.")
		public int garnetMax = 42;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The size of moonstone ore veins.")
		public int moonstoneSize = 6;

		@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
		@Config.Comment("The chance for moonstone ore veins to spawn. 0 to disable.")
		public int moonstoneChance = 2;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The minimum height for moonstone ore veins to spawn.")
		public int moonstoneMin = 16;

		@Config.RangeInt(min = 0, max = 255)
		@Config.Comment("The maximum height for moonstone ore veins to spawn.")
		public int moonstoneMax = 42;
	}

	public static class MobSpawns {
		@Config.Comment("The list of BiomeDictionary types that the lizard will spawn in.")
		public String[] lizardBiomes = {Type.FOREST.getName()};

		@Config.Comment("The list of BiomeDictionary types that the newt will spawn in.")
		public String[] newtBiomes = {Type.SWAMP.getName()};

		@Config.Comment("The list of BiomeDictionary types that the owl will spawn in.")
		public String[] owlBiomes = {Type.FOREST.getName(), Type.DENSE.getName()};

		@Config.Comment("The list of BiomeDictionary types that the raven will spawn in.")
		public String[] ravenBiomes = {Type.PLAINS.getName(), Type.WASTELAND.getName()};

		@Config.Comment("The list of BiomeDictionary types that the snake will spawn in.")
		public String[] snakeBiomes = {Type.PLAINS.getName(), Type.HILLS.getName()};

		@Config.Comment("The list of BiomeDictionary types that the toad will spawn in.")
		public String[] toadBiomes = {Type.SWAMP.getName()};

		@Config.Comment("The list of BiomeDictionary types that the black dog will spawn in.")
		public String[] blackDogBiomes = {Type.PLAINS.getName(), Type.WASTELAND.getName(), Type.FOREST.getName(), Type.SPOOKY.getName()};

		@Config.Comment("The list of BiomeDictionary types that the hellhound will spawn in.")
		public String[] hellhoundBiomes = {Type.NETHER.getName()};

		@Config.Comment("The list of BiomeDictionary types that the serpent will spawn in.")
		public String[] serpentBiomes = {Type.NETHER.getName()};
	}

	public static class Memes {
		@Config.Comment("Decides whether or not the cats and dogs will be enabled.")
		public boolean enableCatsAndDogsFortune = false;
		@Config.Comment("It's Wednesday my dudes")
		public boolean wednesday = false;
	}

	//misc
	/*@Config.Comment("The list of blocks that the broom will sweep when right clicked on")
	public static List<String> broomSweepables = Arrays.asList(Blocks.REDSTONE_WIRE.getTranslationKey(), "tile.bewitchment.glyph", "tile.bewitchment.salt_barrier", "The list of blocks that the broom will sweep when right clicked on");

	@Config.Comment("The list of blocks that the broom will sweep when right clicked on")
	public static List<String> heatSources = Arrays.asList(Blocks.FIRE.getTranslationKey(), Blocks.LAVA.getTranslationKey(), Blocks.MAGMA.getTranslationKey());

	@Config.RangeInt(min = 0, max = Short.MAX_VALUE)
	@Config.Comment("The amount of blocks an altar should scan per tick.")
	public static int altarScansPerTick = 64;

	@Config.RangeInt(min = 0)
	@Config.Comment("The maximum power a Grimoire Magia can have.")
	public static int maxGrimoirePower = 1000;*/

	//tree gen
	/*@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for cypress trees to spawn. Set to 0 to disable.")
	public static int cypressChance;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for elder trees to spawn. Set to 0 to disable.")
	public static int elderChance;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for juniper trees to spawn. Set to 0 to disable.")
	public static int juniperChance;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for yew trees to spawn. Set to 0 to disable.")
	public static int yewChance; //replace static with nothing once static classes are in
*/
	//ore gen

	/*@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The size of silver ore veins.")
	public static int silverSize = 7;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for silver ore veins to spawn. 0 to disable.")
	public static int silverChance = 12;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The minimum height for silver ore veins to spawn.")
	public static int silverMin = 10;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The maximum height for silver ore veins to spawn.")
	public static int silverMax = 128;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The size of salt ore veins.")
	public static int saltSize = 5;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for salt ore veins to spawn. 0 to disable.")
	public static int saltChance = 10;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The minimum height for salt ore veins to spawn.")
	public static int saltMin = 10;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The maximum height for salt ore veins to spawn.")
	public static int saltMax = 120;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The size of amethyst ore veins.")
	public static int amethystSize = 6;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for amethyst ore veins to spawn. 0 to disable.")
	public static int amethystChance = 2;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The minimum height for amethyst ore veins to spawn.")
	public static int amethystMin = 10;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The maximum height for amethyst ore veins to spawn.")
	public static int amethystMax = 42;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The size of garnet ore veins.")
	public static int garnetSize = 6;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for garnet ore veins to spawn. 0 to disable.")
	public static int garnetChance = 2;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The minimum height for garnet ore veins to spawn.")
	public static int garnetMin = 12;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The maximum height for garnet ore veins to spawn.")
	public static int garnetMax = 42;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The size of moonstone ore veins.")
	public static int moonstoneSize = 6;

	@Config.RangeInt(min = 0, max = Byte.MAX_VALUE)
	@Config.Comment("The chance for moonstone ore veins to spawn. 0 to disable.")
	public static int moonstoneChance = 2;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The minimum height for moonstone ore veins to spawn.")
	public static int moonstoneMin = 16;

	@Config.RangeInt(min = 0, max = 255)
	@Config.Comment("The maximum height for moonstone ore veins to spawn.")
	public static int moonstoneMax = 42;*/

	//mob spawns
	/*@Config.Comment("The list of BiomeDictionary types that the lizard will spawn in.")
	public static List<String> lizardBiomes = Arrays.asList(Type.FOREST.getName());

	@Config.Comment("The list of BiomeDictionary types that the newt will spawn in.")
	public static List<String> newtBiomes = Arrays.asList(Type.SWAMP.getName());

	@Config.Comment("The list of BiomeDictionary types that the owl will spawn in.")
	public static List<String> owlBiomes = Arrays.asList(Type.FOREST.getName(), Type.DENSE.getName());

	@Config.Comment("The list of BiomeDictionary types that the raven will spawn in.")
	public static List<String> ravenBiomes = Arrays.asList(Type.PLAINS.getName(), Type.WASTELAND.getName());

	@Config.Comment("The list of BiomeDictionary types that the snake will spawn in.")
	public static List<String> snakeBiomes = Arrays.asList(Type.PLAINS.getName(), Type.HILLS.getName());

	@Config.Comment("The list of BiomeDictionary types that the toad will spawn in.")
	public static List<String> toadBiomes = Arrays.asList(Type.SWAMP.getName());

	@Config.Comment("The list of BiomeDictionary types that the black dog will spawn in.")
	public static List<String> blackDogBiomes = Arrays.asList(Type.PLAINS.getName(), Type.WASTELAND.getName(), Type.FOREST.getName(), Type.SPOOKY.getName());

	@Config.Comment("The list of BiomeDictionary types that the hellhound will spawn in.")
	public static List<String> hellhoundBiomes = Arrays.asList(Type.NETHER.getName());

	@Config.Comment("The list of BiomeDictionary types that the serpent will spawn in.")
	public static List<String> serpentBiomes = Arrays.asList(Type.NETHER.getName());*/

	//maymays
	/*@Config.Comment("Decides whether or not the cats and dogs will be enabled.")
	public static boolean enableCatsAndDogsFortune = false;
	@Config.Comment("It's Wednesday my dudes")
	public static boolean wednesday = false;
	
	public ModConfig(File file) {
		super(file);
		load();
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
	}*/

	@Mod.EventBusSubscriber(modid = Bewitchment.MODID)
	private static class EventHandler {
		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Bewitchment.MODID)) {
				ConfigManager.sync(Bewitchment.MODID, Config.Type.INSTANCE);
			}
		}
	}
}