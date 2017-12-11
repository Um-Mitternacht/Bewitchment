package com.bewitchment.api.helper;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class was created by Arekkuusu on 27/05/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class ItemNullHelper {

	private ItemNullHelper() {
	}

	public static List<ItemStack> asList(int size) {
		ArrayList<ItemStack> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(ItemStack.EMPTY);
		}
		return list;
	}

	public static ItemStack[] asArray(int size) {
		ItemStack[] array = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			array[i] = ItemStack.EMPTY;
		}
		return array;
	}

	public static boolean isEmpty(Collection<ItemStack> collection) {
		return collection.stream().allMatch(Predicate.isEqual(ItemStack.EMPTY));
	}
}
