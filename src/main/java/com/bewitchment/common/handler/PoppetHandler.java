package com.bewitchment.common.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PoppetHandler {
    @SubscribeEvent
    public void deathProtection (LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_deathprotection)) {
                event.setCanceled(true);
                player.setHealth(4f);
                player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1000, 1));
            }
        }
    }

    @SubscribeEvent
    public void binding (LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLiving) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_binding)) {
                EntityLiving source = (EntityLiving) event.getSource().getTrueSource();
                source.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 2));
            }
        }
    }
}
