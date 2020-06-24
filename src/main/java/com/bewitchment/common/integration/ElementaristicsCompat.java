package com.bewitchment.common.integration;

import com.bewitchment.Util;
import com.bewitchment.api.registry.AltarUpgrade;
import de.aelpecyem.elementaristics.init.ModItems;

/**
 * Created by Joseph on 2/22/2020.
 */
public class ElementaristicsCompat {

	public static void registerElementaristicsStuff() {
		Util.registerAltarUpgradeItem(de.aelpecyem.elementaristics.init.ModItems.thaumagral_wood, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.24));
		Util.registerAltarUpgradeItem(de.aelpecyem.elementaristics.init.ModItems.thaumagral_stone, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.29));
		Util.registerAltarUpgradeItem(de.aelpecyem.elementaristics.init.ModItems.thaumagral_iron, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.35));
		Util.registerAltarUpgradeItem(de.aelpecyem.elementaristics.init.ModItems.thaumagral_gold, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.45));
		Util.registerAltarUpgradeItem(de.aelpecyem.elementaristics.init.ModItems.thaumagral_diamond, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.43));
		Util.registerAltarUpgradeItem(ModItems.catalyst_ordering, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeItem(ModItems.catalyst_entropizing, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeItem(ModItems.gem_arcane, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
	}
}
