package com.bewitchment.common.core.util;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DecimalFormat;

/**
 * Created by Joseph on 2/10/2019.
 *
 * Original code created by matter overdrive dev
 */
public class BWStringHelper {

	private static String[] suffix = new String[]{"", "K", "M", "B", "T"};
	private static int MAX_LENGTH = 16;

	public static String readTextFile(ResourceLocation location) {
		StringBuilder text = new StringBuilder();
		try {
			String path = "/assets/" + location.getNamespace() + "/" + location.getPath();
			InputStream descriptionStream = BWStringHelper.class.getResourceAsStream(path);
			if (descriptionStream == null)
				return text.toString();
			LineNumberReader descriptionReader = new LineNumberReader(new InputStreamReader(descriptionStream));
			String line;

			while ((line = descriptionReader.readLine()) != null) {
				text.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return text.toString();
	}

	public static String addPrefix(String name, String prefix) {
		if (prefix.endsWith("-")) {
			return prefix.substring(0, prefix.length() - 2) + Character.toLowerCase(name.charAt(0)) + name.substring(1);
		} else {
			return prefix + " " + name;
		}
	}

	public static String addSuffix(String name, String suffix) {
		if (suffix.startsWith("-")) {
			return name + suffix.substring(1);
		} else {
			return name + " " + suffix;
		}
	}

	public static String[] formatVariations(String unlocalizedName, String unlocalizedSuffix, int count) {
		String[] variations = new String[count];
		for (int i = 0; i < count; i++) {
			variations[i] = unlocalizedName + "." + i + "." + unlocalizedSuffix;
		}
		return variations;
	}
}
