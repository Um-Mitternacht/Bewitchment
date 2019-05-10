package com.bewitchment;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ModConfig extends Configuration {
	public final int cypressChance, elderChance, juniperChance, yewChance;

	public final int silverSize, silverChance, silverMin, silverMax, saltSize, saltChance, saltMin, saltMax, amethystSize, amethystChance, amethystMin, amethystMax, garnetSize, garnetChance, garnetMin, garnetMax, moonstoneSize, moonstoneChance, moonstoneMin, moonstoneMax;

	public ModConfig(File file) {
		super(file);
		load();
		cypressChance = getInt("cypressChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for cypress trees to spawn. Set to 0 to disable.");
		elderChance = getInt("elderChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for elder trees to spawn. Set to 0 to disable.");
		juniperChance = getInt("juniperChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for juniper trees to spawn. Set to 0 to disable.");
		yewChance = getInt("yewChance", "treeGen", 20, 0, Byte.MAX_VALUE, "The chance for elder trees to spawn. Set to 0 to disable.");

		silverSize = getInt("silverSize", "oreGen", 7, 0, Byte.MAX_VALUE, "The size of silver ore veins.");
		silverChance = getInt("silverChance", "oreGen", 12, 0, Byte.MAX_VALUE, "The chance for silver ore veins to spawn.");
		silverMin = getInt("silverMin", "oreGen", 10, 0, 255, "The minimum height for silver ore veins to spawn.");
		silverMax = getInt("silverMax", "oreGen", 128, 0, 255, "The maximum height for silver ore veins to spawn.");

		saltSize = getInt("saltSize", "oreGen", 5, 0, Byte.MAX_VALUE, "The size of salt ore veins.");
		saltChance = getInt("saltChance", "oreGen", 10, 0, Byte.MAX_VALUE, "The chance for salt ore veins to spawn.");
		saltMin = getInt("saltMin", "oreGen", 10, 0, 255, "The minimum height for salt ore veins to spawn.");
		saltMax = getInt("saltMax", "oreGen", 120, 0, 255, "The maximum height for salt ore veins to spawn.");

		amethystSize = getInt("amethystSize", "oreGen", 6, 0, Byte.MAX_VALUE, "The size of amethyst ore veins.");
		amethystChance = getInt("amethystChance", "oreGen", 2, 0, Byte.MAX_VALUE, "The chance for amethyst ore veins to spawn.");
		amethystMin = getInt("amethystMin", "oreGen", 10, 0, 255, "The minimum height for amethyst ore veins to spawn.");
		amethystMax = getInt("amethystMax", "oreGen", 64, 0, 255, "The maximum height for amethyst ore veins to spawn.");

		garnetSize = getInt("garnetSize", "oreGen", 6, 0, Byte.MAX_VALUE, "The size of garnet ore veins.");
		garnetChance = getInt("garnetChance", "oreGen", 2, 0, Byte.MAX_VALUE, "The chance for garnet ore veins to spawn.");
		garnetMin = getInt("garnetMin", "oreGen", 12, 0, 255, "The minimum height for garnet ore veins to spawn.");
		garnetMax = getInt("garnetMax", "oreGen", 80, 0, 255, "The maximum height for garnet ore veins to spawn.");

		moonstoneSize = getInt("moonstoneSize", "oreGen", 6, 0, Byte.MAX_VALUE, "The size of moonstone ore veins.");
		moonstoneChance = getInt("moonstoneChance", "oreGen", 2, 0, Byte.MAX_VALUE, "The chance for moonstone ore veins to spawn.");
		moonstoneMin = getInt("moonstoneMin", "oreGen", 16, 0, 255, "The minimum height for moonstone ore veins to spawn.");
		moonstoneMax = getInt("moonstoneMax", "oreGen", 120, 0, 255, "The maximum height for moonstone ore veins to spawn.");
		save();
	}
}