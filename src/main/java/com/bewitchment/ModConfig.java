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