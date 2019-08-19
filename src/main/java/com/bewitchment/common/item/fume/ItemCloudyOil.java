package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemCloudyOil extends Item {
    public ItemCloudyOil() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "cloudy_oil");
    }
}
