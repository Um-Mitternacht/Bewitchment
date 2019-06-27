package com.bewitchment.api.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Brew extends IForgeRegistryEntry.Impl<Brew> {
	public final Ingredient input;
	public final PotionEffect effect;
	
	public Brew(ResourceLocation name, Ingredient input, PotionEffect effect) {
		setRegistryName(name);
		this.input = input;
		this.effect = effect;
	}
	
	public final boolean matches(ItemStack input) {
		for (ItemStack stack : this.input.getMatchingStacks()) if (OreDictionary.itemMatches(stack, input, true)) return true;
		return false;
	}
}