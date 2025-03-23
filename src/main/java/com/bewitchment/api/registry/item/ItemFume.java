package com.bewitchment.api.registry.item;

import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;

public class ItemFume extends Item {
    public ItemFume() {
        super();
        this.setContainerItem(ModObjects.empty_jar);
    }
}
