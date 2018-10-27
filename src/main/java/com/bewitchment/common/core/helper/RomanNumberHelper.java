package com.bewitchment.common.core.helper;

/**
 * This class was created by Arekkuusu on 22/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class RomanNumberHelper {

	private static final String[] romans = {"", "I", "II", "III", "IV", "V", "V+"};

	public static String getRoman(int i) {
		if (i < 0) {
			i = 0;
		} else if (i > 6) {
			i = 6;
		}
		return romans[i];
	}

}
