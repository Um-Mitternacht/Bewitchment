package com.bewitchment.common.integration;

import com.bewitchment.Util;
import com.bewitchment.api.registry.AltarUpgrade;
import net.minecraftforge.event.RegistryEvent;

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
	}
}
