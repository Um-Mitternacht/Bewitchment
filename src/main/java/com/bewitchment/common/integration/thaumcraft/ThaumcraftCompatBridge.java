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

	public static boolean isEldritchMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return ThaumcraftCompat.isEldritchMob(entity);
		}
		return false;
	}

	public static boolean isTCSpiritMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return ThaumcraftCompat.isTCSpiritMob(entity);
		}
		return false;
	}

	public static boolean isCrimsonCultMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return ThaumcraftCompat.isCrimsonCultMob(entity);
		}
		return false;
	}

	public static boolean isTaintedMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return ThaumcraftCompat.isTaintedMob(entity);
		}
		return false;
	}

	public static boolean isThaumcraftMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return ThaumcraftCompat.isThaumcraftMob(entity);
		}
		return false;
	}

}
