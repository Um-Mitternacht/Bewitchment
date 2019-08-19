package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemEssenceVitality extends Item {
    public ItemEssenceVitality() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "essence_of_vitality");
    }
}
