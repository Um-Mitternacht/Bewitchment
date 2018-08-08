package com.bewitchment.common.crafting.fermenting;

import com.bewitchment.api.crafting.BarrelRecipe;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBarrelRecipes {

	public static BarrelRecipe slime;
	// public static BarrelRecipe potionDetonation;

	public static void init() {

		slime = new SlimeRecipe(new ItemStack(Items.SLIME_BALL), 1200, 0);
		slime.setRegistryName(LibMod.MOD_ID, "slime");
		// potionDetonation = new PotionExplosion(ItemStack.EMPTY, ItemStack.EMPTY, 100, 200);
		// potionDetonation.setRegistryName(Reference.MID, "detonation");
		registerAll();
	}

	public static void registerAll() {
		BarrelRecipe.REGISTRY.registerAll(
				slime //, potionDetonation
		);
	}
}
