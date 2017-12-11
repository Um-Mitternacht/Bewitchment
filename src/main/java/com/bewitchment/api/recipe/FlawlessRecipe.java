package com.bewitchment.api.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

/**
 * This class was created by Arekkuusu on 21/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class FlawlessRecipe implements IOreMatchRecipe {

	private final ItemStack result;
	private final ImmutableList<Object> neededItems;

	public FlawlessRecipe(ItemStack result, Object... inputs) {
		this.result = result;

		final List<Object> stackedList = Arrays.stream(inputs).map(obj -> {
			if (obj instanceof Item) return new ItemStack((Item) obj);
			else if (obj instanceof Block) return new ItemStack((Block) obj);
			else return obj;
		}).collect(Collectors.toList());

		neededItems = ImmutableList.copyOf(stackedList);
	}

	@Override
	public boolean matches(List<ItemStack> usedItems) {
		if (usedItems.size() == neededItems.size()) {
			boolean matches = true;
			for (int i = 0; i < usedItems.size(); i++) {
				final Object needed = neededItems.get(i);
				final ItemStack used = usedItems.get(i);
				if (needed instanceof ItemStack && !ItemStack.areItemStacksEqual(used, (ItemStack) needed)) {
					matches = false;
					break;
				} else if (needed instanceof String && !containsMatch(OreDictionary.getOres((String) needed), used)) {
					matches = false;
					break;
				}
			}
			return matches;
		}
		return false;
	}

	@Override
	public ImmutableList<Object> getNeededItems() {
		return neededItems;
	}

	@Override
	public ItemStack getResult() {
		return result.copy();
	}

	@Override
	public boolean itemMatches(ItemStack target, ItemStack input) {
		return input != null && target != null && target.getItem() == input.getItem() && target.getCount() == input.getCount() && (target.getItemDamage() == input.getItemDamage()
				|| input.getItemDamage() == WILDCARD_VALUE);
	}
}
