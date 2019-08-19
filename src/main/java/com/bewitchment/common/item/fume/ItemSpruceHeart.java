package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemSpruceHeart extends Item {
    public ItemSpruceHeart() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "spruce_heart");
    }
}
