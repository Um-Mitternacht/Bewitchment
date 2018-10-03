package com.bewitchment.common.entity;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.entity.living.familiar.EntityOwl;
import com.bewitchment.common.entity.living.familiar.EntitySnake;
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

		// Utility entities
		EntityRegistry.registerModEntity(getResource("spell_carrier"), EntitySpellCarrier.class, "spell_carrier", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("broom"), EntityFlyingBroom.class, "broom", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("swarm"), EntityBatSwarm.class, "swarm", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_arrow"), EntityBrewArrow.class, "brew_arrow", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_bottle"), EntityBrew.class, "brew_bottle", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_lingering_effect"), EntityLingeringBrew.class, "brew_lingering_effect", id++, Bewitchment.instance, 64, 1, true);
		EntityRegistry.registerModEntity(getResource("brew_aoe_effect"), EntityAoE.class, "brew_aoe_effect", id++, Bewitchment.instance, 64, 1, false);

		// Mob entities
		EntityRegistry.registerModEntity(getResource("owl"), EntityOwl.class, "entity_owl", id++, Bewitchment.instance, 64, 1, true, 0xAF813F, 0x6E5127);
		EntityRegistry.registerModEntity(getResource("snake"), EntitySnake.class, "entity_snake", id++, Bewitchment.instance, 64, 1, true, 0xAF813F, 0x6E5127);
	}

	private static ResourceLocation getResource(String name) {
		return new ResourceLocation(LibMod.MOD_ID, name);
	}
}
