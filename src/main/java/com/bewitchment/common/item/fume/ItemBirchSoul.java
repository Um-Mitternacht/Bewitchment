package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemBirchSoul extends Item {
    public ItemBirchSoul() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "birch_soul");
    }
}
