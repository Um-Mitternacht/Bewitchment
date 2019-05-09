package com.bewitchment.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void furnacePreInit() {
		GameRegistry.addSmelting(ModObjects.amethyst_ore, new ItemStack(ModObjects.amethyst), 1);
		GameRegistry.addSmelting(ModObjects.garnet_ore, new ItemStack(ModObjects.garnet), 1);
		GameRegistry.addSmelting(ModObjects.moonstone_ore, new ItemStack(ModObjects.moonstone), 1);
		GameRegistry.addSmelting(ModObjects.silver_ore, new ItemStack(ModObjects.silver_ingot), 1);
		GameRegistry.addSmelting(ModObjects.salt_ore, new ItemStack(ModObjects.salt), 1);
	}
}