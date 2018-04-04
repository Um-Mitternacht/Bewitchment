package com.bewitchment.common.potion;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.potion.potions.PotionBloodDrained;
import com.bewitchment.common.potion.potions.brews.*;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModPotions {

	// Normal effects
	public static Potion bloodDrained;
	
	// Also brews
	public static Potion wolfsbane, arrow_deflect, absence, plant, bane_arthropods, corruption;

	private ModPotions() {
	}

	public static void init() {
		bloodDrained = new PotionBloodDrained();
		wolfsbane = new PotionWolfsbane();
		arrow_deflect = new PotionArrowDeflection();
		absence = new PotionAbsence();
		plant = new PotionPlant();
		bane_arthropods = new PotionBaneArthropods();
		corruption = new PotionCorruption();
		
		registerCombinedBrewEffect(wolfsbane, Ingredient.fromItem(ModItems.aconitum));
		registerCombinedBrewEffect(arrow_deflect, Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.everchanging_presence.ordinal())));
		registerCombinedBrewEffect(absence, Ingredient.fromItem(ModItems.sagebrush));// FIXME recipe conflict with radius modifier
		registerCombinedBrewEffect(plant, Ingredient.fromItem(Item.getItemFromBlock(Blocks.RED_MUSHROOM)));
		registerCombinedBrewEffect(bane_arthropods, Ingredient.fromItem(ModItems.wormwood));
		registerCombinedBrewEffect(corruption, Ingredient.fromItem(Items.BONE));
		
		ForgeRegistries.POTIONS.registerAll(bloodDrained, wolfsbane, arrow_deflect, absence, plant, bane_arthropods, corruption);
	}

	private static void registerCombinedBrewEffect(Potion potion, Ingredient ingredient) {
		if (potion instanceof IBrewEffect) {
			BewitchmentAPI.getAPI().registerBrewEffect((IBrewEffect) potion, potion, ingredient);
		}
		throw new IllegalArgumentException(potion + " is not an IBrewEffect. Use BewitchmentAPI#registerBrewEffect for register them as separate objects");
	}
	
}
