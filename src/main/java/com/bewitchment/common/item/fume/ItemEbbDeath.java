package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemEbbDeath extends Item {
    public ItemEbbDeath() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "ebb_of_death");
    }
}
