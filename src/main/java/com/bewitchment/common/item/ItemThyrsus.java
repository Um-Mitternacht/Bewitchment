package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

/**
 * Created by Joseph on 4/16/2020.
 */
public class ItemThyrsus extends ItemSword {

    //Todo: Expand repair items
    public ItemThyrsus() {
        super(ModObjects.TOOL_THYRSUS);
        setMaxStackSize(1);
        Util.registerItem(this, "thyrsus");
        setMaxDamage(72);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityAnimal) ((EntityAnimal) target).setInLove(null);
        if (target instanceof EntityTameable) ((EntityTameable) target).setTamedBy(playerIn);
        stack.damageItem(6, playerIn);
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (!target.world.isRemote && (!(target instanceof EntityPlayer) || !(attacker instanceof EntityPlayer))) {
            int i = itemRand.nextInt(100);
            if (i < 10) {
                target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0, false, false));
                stack.damageItem(6, attacker);
            }
        }
        return super.hitEntity(stack, target, attacker);
    }
}
