package com.bewitchment.api.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IBrewModifier extends IForgeRegistryEntry<IBrewModifier> {

	/**
	 * Decide if a modifier is applicable to a specific potion
	 *
	 * @param brew The brew in question
	 * @return true if the modifier can be applied to this potion, false otherwise
	 */
	public boolean canApply(IBrewEffect brew);

	public boolean hasMultipleLevels();

	public String getTooltipString(int lvl);

	/**
	 * Given the ingredient thrown in the cauldron, decide what level this modifier should be set at
	 *
	 * @param brew             The brew being modified
	 * @param stack            The stack that should be used as a modifier
	 * @param currentModifiers The current list of modifiers applied to this brew.
	 *                         The effect might already be present if the ingredient is thrown in twice,
	 *                         so you should check it before in case this uses multiple levels
	 * @return the ModifierResult representing what should happen. if ResultType is SUCCESS, the modifier gets
	 * set to the level value of the Result, if the result type is FAIL the potion will fail, if the result type
	 * is PASS nothing will be modified, and the next brew modifier will be called.
	 */
	public ModifierResult acceptIngredient(IBrewEffect brew, ItemStack stack, IBrewModifierList currentModifiers);

	public Ingredient getJEIStackRepresentative();

	public static enum ResultType {
		SUCCESS, FAIL, PASS
	}

	public static class ModifierResult {

		private int level;
		private ResultType result;

		public ModifierResult(int level, ResultType result) {
			this.level = level;
			this.result = result;
		}

		public ModifierResult(ResultType result) {
			this(0, result);
		}

		public int getLevel() {
			return level;
		}

		public ResultType getResult() {
			return result;
		}

	}
}
