package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemLiquidWitchcraft extends Item {
    public ItemLiquidWitchcraft() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "liquid_witchcraft");
    }
}
