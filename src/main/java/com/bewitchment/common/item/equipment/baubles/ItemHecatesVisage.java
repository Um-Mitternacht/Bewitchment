package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class ItemHecatesVisage extends ModItemBauble {
    public ItemHecatesVisage() {
        super("hecates_visage", BaubleType.HEAD);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        player.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
    }
}
