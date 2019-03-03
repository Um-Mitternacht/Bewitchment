package com.bewitchment.common.integration.optifine;

import net.minecraftforge.fml.common.Loader;

/**
 * Created by Joseph on 3/1/2019.
 * <p>
 * Original code created by Zabi94
 */
public class Optifine {
	private static boolean isLoaded = false;

	public static void init() {
		isLoaded = Loader.isModLoaded("optifine");
	}

	public static boolean isLoaded() {
		return isLoaded;
	}
}