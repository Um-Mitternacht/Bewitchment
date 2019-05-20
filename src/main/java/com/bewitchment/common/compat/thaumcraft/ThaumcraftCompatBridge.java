package com.bewitchment.common.compat.thaumcraft;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by Joseph on 5/20/2019.
 */
@SuppressWarnings("deprecation")
public class ThaumcraftCompatBridge {

	private static final String TC_MOD_ID = "thaumcraft";

	public static void loadThaumcraftCompat() {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			BewitchmentThaumcraft.register();
		}
	}


	//These methods call for various groups of mobs from Thaumcraft, allowing them to be targeted.
	public static boolean isEldritchMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return BewitchmentThaumcraft.isEldritchMob(entity);
		}
		return false;
	}

	public static boolean isTCSpiritMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return BewitchmentThaumcraft.isTCSpiritMob(entity);
		}
		return false;
	}

	public static boolean isCrimsonCultMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return BewitchmentThaumcraft.isCrimsonCultMob(entity);
		}
		return false;
	}

	public static boolean isTaintedMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return BewitchmentThaumcraft.isTaintedMob(entity);
		}
		return false;
	}

	public static boolean isThaumcraftMob(Entity entity) {
		if (Loader.isModLoaded(TC_MOD_ID)) {
			return BewitchmentThaumcraft.isThaumcraftMob(entity);
		}
		return false;
	}

}