package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class ItemHellishBauble extends ModItemBauble {
    public ItemHellishBauble() {
        super("hellish_bauble", BaubleType.TRINKET);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase living) {
        if (living.ticksExisted % 40 == 0 && living.isPotionActive(MobEffects.NAUSEA))
            living.removePotionEffect(MobEffects.NAUSEA);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase living) {
        living.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, .75F, 1.9f);
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        if ((Util.hasBauble(event.getEntityLiving(), this) && (event.getSource().getTrueSource() instanceof EntityLivingBase && (((EntityLivingBase) event.getSource().getTrueSource()).getCreatureAttribute() == BewitchmentAPI.DEMON) || event.getSource().isExplosion() || event.getSource().isFireDamage())) && event.getEntityLiving() instanceof EntityPlayer && MagicPower.attemptDrain(null, (EntityPlayer) event.getEntityLiving(), 20))
            event.setAmount(event.getAmount() * 0.85f);
    }
}