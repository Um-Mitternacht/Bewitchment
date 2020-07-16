package com.bewitchment.common.integration;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import com.miskatonicmysteries.common.block.BlockCandles;
import com.miskatonicmysteries.registry.ModObjects;

/**
 * Created by Joseph on 4/1/2020.
 */
public class MiskatonicMysteriesCompat {

	public static void registerMiskatonicMysteriesStuff() {
		Util.registerAltarUpgradeItem(ModObjects.black_goats_gutting_dagger, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.43));
		Util.registerAltarUpgradeItem(ModObjects.black_goats_horned_dagger, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.43));
		Util.registerAltarUpgradeItem(ModObjects.yellow_kings_dagger, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.43));
		Util.registerAltarUpgradeItem(ModObjects.gold_oceanic, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));

		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockCandles, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.125));
	}
}
