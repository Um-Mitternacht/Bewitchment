package com.bewitchment.common.entity;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModEntities {

	private ModEntities() {
	}

	public static void init() {
		int id = 0;

		EntityRegistry.registerModEntity(getResource("spell_carrier"), EntitySpellCarrier.class, "spell_carrier", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("broom"), EntityFlyingBroom.class, "broom", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("swarm"), EntityBatSwarm.class, "swarm", id++, Bewitchment.instance, 64, 1, true);
	}

	private static ResourceLocation getResource(String name) {
		return new ResourceLocation(LibMod.MOD_ID, name);
	}
}
