package com.bewitchment.api.registry;
import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Brew extends IForgeRegistryEntry.Impl<Brew> {
	public final Ingredient input;
	public final Predicate<ItemStack> outputPredicate;
	public final ItemStack output;
	public final PotionEffect effect;
	
	public Brew(ResourceLocation name, Ingredient input, Predicate<ItemStack> outputPredicate, ItemStack output, PotionEffect effect) {
		setRegistryName(name);
		this.input = input;
		this.outputPredicate = outputPredicate;
		this.output = output;
		this.effect = effect;
	}
	
	public Brew(ResourceLocation name, Ingredient input, ItemStack output, PotionEffect effect) {
		this(name, input, null, output, effect);
	}
	
	public Brew(ResourceLocation name, Ingredient input, PotionEffect effect) {
		this(name, input, ItemStack.EMPTY, effect);
	}
	
	public final boolean matches(ItemStack input) {
		for (ItemStack stack : this.input.getMatchingStacks()) if (OreDictionary.itemMatches(stack, input, true)) return true;
		return false;
	}
}