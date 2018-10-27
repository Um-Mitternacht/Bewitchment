package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.core.helper.RomanNumberHelper;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public abstract class SimpleModifier implements IBrewModifier {

	private final ResourceLocation name;
	private final Ingredient ingredient;

	public SimpleModifier(String name, Ingredient ingredient) {
		this.name = new ResourceLocation(LibMod.MOD_ID, name);
		if (ingredient.getMatchingStacks().length == 0) {
			throw new IllegalArgumentException("Empty ingredient when registering the following brew modifier: " + name);
		}
		this.ingredient = ingredient;
	}

	@Override
	public IBrewModifier setRegistryName(ResourceLocation nameIn) {
		throw new UnsupportedOperationException("Don't mess with bewitchment default implementation of modifiers!");
	}

	@Override
	public ResourceLocation getRegistryName() {
		return name;
	}

	@Override
	public Class<IBrewModifier> getRegistryType() {
		return IBrewModifier.class;
	}

	@Override
	public ModifierResult acceptIngredient(IBrewEffect brew, ItemStack stack, IBrewModifierList currentModifiers) {
		int currentLevel = currentModifiers.getLevel(this).orElse(0);
		if (ingredient.apply(stack)) {
			if (currentLevel < 5) {
				return new ModifierResult(currentLevel + 1, ResultType.SUCCESS);
			}
			return new ModifierResult(ResultType.FAIL);
		}
		return new ModifierResult(ResultType.PASS);
	}

	@Override
	public Ingredient getJEIStackRepresentative() {
		return ingredient;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IBrewModifier) {
			return this.getRegistryName().equals(((IBrewModifier) obj).getRegistryName());
		}
		return false;
	}

	@Override
	public boolean hasMultipleLevels() {
		return true;
	}

	@Override
	public String getTooltipString(int lvl) {
		if (hasMultipleLevels()) {
			return I18n.format("modifier." + getRegistryName().toString().replace(':', '.'), RomanNumberHelper.getRoman(lvl));
		}
		return I18n.format("modifier." + getRegistryName().toString().replace(':', '.'));
	}
}
