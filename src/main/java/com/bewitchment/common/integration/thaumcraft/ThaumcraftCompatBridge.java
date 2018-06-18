package com.bewitchment.common.integration.thaumcraft;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;

@SuppressWarnings("deprecation")
public class ThaumcraftCompatBridge {

	private static final String TC_MOD_ID = "thaumcraft";

	public static void registerAspects() {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			ThaumcraftCompat.registerAspectsInternal();
		}
	}

	public static boolean isThaumcraftMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return ThaumcraftCompat.isThaumcraftMob(entity);
		}
		return false;
	}

}
