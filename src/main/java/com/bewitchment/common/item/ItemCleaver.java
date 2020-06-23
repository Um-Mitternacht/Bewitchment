package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Joseph on 3/3/2020.
 */

public class ItemCleaver extends ItemSword {
    public ItemCleaver() {
        super(ModObjects.TOOL_HELLISH);
        Util.registerItem(this, "cleaver_sword");
        setMaxDamage(333);
        setMaxStackSize(1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }
}
