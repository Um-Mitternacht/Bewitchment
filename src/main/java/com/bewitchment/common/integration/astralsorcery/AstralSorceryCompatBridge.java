package com.bewitchment.common.integration.astralsorcery;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by Joseph on 8/3/2018.
 */
public class AstralSorceryCompatBridge {

	private static final String AS_MOD_ID = "astralsorcery";

	public static boolean isStellarMob(Entity entity) {
		if (Loader.isModLoaded(AS_MOD_ID)) {
			return AstralSorceryCompat.isStellarMob(entity);
		}
		return false;
	}
}
