/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.content.spell;

import com.bewitchment.api.spell.ISpell.EnumSpellType;
import com.bewitchment.common.content.spell.spells.*;
import com.bewitchment.common.lib.LibMod;

public class ModSpells {

	public static Spell magnet, poke, water, activation, slowness, lesser_blink, blink, explosion, disarming, infuse_life, self_heal, call_storm;

	public static void init() {
		magnet = new SpellMagnet(1, 0xa5cec9, EnumSpellType.PROJECTILE_BLOCK, "magnet", LibMod.MOD_ID);
		poke = new SpellPoke(1, 0x6d1e10, EnumSpellType.PROJECTILE_ALL, "poke", LibMod.MOD_ID);
		water = new SpellWater(2, 0x1644a7, EnumSpellType.PROJECTILE_BLOCK, "water", LibMod.MOD_ID);
		activation = new SpellActivation(1, 0x42b3bd, EnumSpellType.PROJECTILE_BLOCK, "activation", LibMod.MOD_ID);
		slowness = new SpellSlowness(10, 0x4d6910, EnumSpellType.PROJECTILE_ENTITY, "slowness", LibMod.MOD_ID);
		lesser_blink = new SpellLesserBlinking(5, 0x9042bd, EnumSpellType.INSTANT, "lesser_blink", LibMod.MOD_ID);
		blink = new SpellBlink(10, 0xcb33e7, EnumSpellType.PROJECTILE_BLOCK, "blink", LibMod.MOD_ID);
		explosion = new SpellDestabilization(12, 0x5e0505, EnumSpellType.PROJECTILE_ALL, "explosion", LibMod.MOD_ID);
		disarming = new SpellDisarming(15, 0xffbb7c, EnumSpellType.PROJECTILE_ALL, "disarming", LibMod.MOD_ID);
		infuse_life = new SpellInfuseLife(5, 0xf6546a, EnumSpellType.PROJECTILE_ALL, "infuse_life", LibMod.MOD_ID);
		self_heal = new SpellSelfHeal(4, 0xd20057, EnumSpellType.INSTANT, "self_heal", LibMod.MOD_ID);
		call_storm = new SpellCallThunderstorm(15, 0x000033, EnumSpellType.INSTANT, "call_storm", LibMod.MOD_ID);
		registerAll();
	}

	private static void registerAll() {
		Spell.SPELL_REGISTRY.registerAll(
				magnet, poke, water, activation, slowness, lesser_blink, blink, explosion, disarming, infuse_life, self_heal, call_storm
		);
	}

}
