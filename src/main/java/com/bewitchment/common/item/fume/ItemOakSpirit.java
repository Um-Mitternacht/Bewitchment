package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemOakSpirit extends Item {
    public ItemOakSpirit() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
        Util.registerItem(this, "oak_spirit");
    }
}
