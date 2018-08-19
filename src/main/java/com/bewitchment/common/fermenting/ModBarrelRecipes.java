package com.bewitchment.common.fermenting;

import com.bewitchment.api.fermenting.BarrelRecipe;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBarrelRecipes {

	public static BarrelRecipe slime;
	//public static BarrelRecipe four_thieves_vinegar;
	// public static BarrelRecipe potionDetonation;

	public static void init() {

		slime = new SlimeRecipe(new ItemStack(Items.SLIME_BALL), 1200, 0);
		slime.setRegistryName(LibMod.MOD_ID, "slime");
		//four_thieves_vinegar = new FourThievesVinegarRecipe(new ItemStack(ModItems.four_thieves_vinegar), 2400, 0);
		//four_thieves_vinegar.setRegistryName(LibMod.MOD_ID, "four_thieves_vinegar");
		// potionDetonation = new PotionExplosion(ItemStack.EMPTY, ItemStack.EMPTY, 100, 200);
		// potionDetonation.setRegistryName(Reference.MID, "detonation");
		registerAll();
	}

	public static void registerAll() {
		BarrelRecipe.REGISTRY.registerAll(
				slime //four_thieves_vinegar// , potionDetonation
		);
	}
}
