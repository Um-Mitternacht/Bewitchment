package com.witchcraft.api.helper;

import java.util.TreeMap;

/**
 * This class was created by Arekkuusu on 22/05/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class RomanNumber {

	private static final TreeMap<Integer, String> TREE_MAP = new TreeMap<>();

	static {
		TREE_MAP.put(1000, "M");
		TREE_MAP.put(900, "CM");
		TREE_MAP.put(500, "D");
		TREE_MAP.put(400, "CD");
		TREE_MAP.put(100, "C");
		TREE_MAP.put(90, "XC");
		TREE_MAP.put(50, "L");
		TREE_MAP.put(40, "XL");
		TREE_MAP.put(10, "X");
		TREE_MAP.put(9, "IX");
		TREE_MAP.put(5, "V");
		TREE_MAP.put(4, "IV");
		TREE_MAP.put(1, "I");
	}

	private RomanNumber() {
	}

	public static String getRoman(int number) {
		int l = TREE_MAP.floorKey(number);
		if (number == 1) {
			return TREE_MAP.get(number);
		}
		return TREE_MAP.get(l) + getRoman(number > 4 ? number - l : number - 1);
	}
}
