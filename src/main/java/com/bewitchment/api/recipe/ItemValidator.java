package com.bewitchment.api.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This class was created by Arekkuusu on 12/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
public class ItemValidator<T> {

	private final ArrayList<Holder<ItemStack, T>> list = new ArrayList<>();

	@SuppressWarnings("ConstantConditions")
	public static boolean itemMatches(ItemStack target, ItemStack input, boolean strict) {
		return !(input.isEmpty() && !target.isEmpty() || !input.isEmpty() && target.isEmpty()) && target.getItem() == input.getItem() && (!strict || target.getItemDamage() == input.getItemDamage());
	}

	public ItemValidator<T> add(ItemStack stack, T t, boolean strict) {
		list.add(new Holder<>(stack, t, strict));
		return this;
	}

	public Optional<T> getMatchFor(ItemStack input) {
		Optional<Holder<ItemStack, T>> optional = getHolder(input);
		return optional.map(Holder::getOutput);
	}

	public Optional<Holder<ItemStack, T>> getHolder(ItemStack input) {
		return list.stream().filter(tuple -> itemMatches(tuple.getInput(), input, tuple.isStrict())).findAny();
	}

	private class Holder<J, K> {

		private final J input;
		private final K output;
		private final boolean strict;

		Holder(J input, K output, boolean strict) {
			this.input = input;
			this.output = output;
			this.strict = strict;
		}

		public J getInput() {
			return input;
		}

		public K getOutput() {
			return output;
		}

		public boolean isStrict() {
			return strict;
		}
	}
}
