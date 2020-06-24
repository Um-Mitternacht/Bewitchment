package com.bewitchment.common.integration;

import com.bewitchment.Util;
import com.bewitchment.api.registry.AltarUpgrade;
import its_meow.betteranimalsplus.util.HeadTypes;

public class BAPCompat {
    public static void registerBAPHeadAlter() {
        Util.registerAltarUpgradeItem(HeadTypes.REINDEERHEAD.getItem(1), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
        Util.registerAltarUpgradeItem(HeadTypes.REINDEERHEAD.getItem(2), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
    }
}
