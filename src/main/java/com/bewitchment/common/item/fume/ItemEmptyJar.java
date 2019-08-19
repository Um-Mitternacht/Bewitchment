package com.bewitchment.common.item.fume;

import com.bewitchment.Util;
import net.minecraft.item.Item;

public class ItemEmptyJar extends Item {
    public ItemEmptyJar() {
        super();
        Util.registerItem(this, "empty_jar");
    }
}
