package com.bewitchment.common.integration.astralsorcery;

import hellfirepvp.astralsorcery.common.entities.EntityFlare;
import net.minecraft.entity.Entity;

/**
 * Created by Joseph on 8/3/2018.
 */
@Deprecated
public class AstralSorceryCompat {

	// TODO: Vampires can walk during eclipses, and grindstone recipes
	@Deprecated
	static boolean isStellarMob(Entity target) {
		return target instanceof EntityFlare;
	}
}
