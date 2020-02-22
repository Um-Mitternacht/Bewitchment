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
	}
}
