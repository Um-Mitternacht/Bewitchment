package com.bewitchment.common.handler;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
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

    @SubscribeEvent
    public void wasting (LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_wasting)) {
                EntityLiving source = (EntityLiving) event.getSource().getTrueSource();
                source.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 200, 1));
            }
        }
    }

    @SubscribeEvent
    public void earthProtection (LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getSource() == DamageSource.FALL) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_earthprotection)) {
                event.setAmount(event.getAmount() / 4);
            }
        }
    }

    @SubscribeEvent
    public void fireProtection (LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE)) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_flameprotection)) {
                event.setAmount(event.getAmount() * 80 / 100);
            }
        }
    }

    @SubscribeEvent
    public void hungerProtection (LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getSource() == DamageSource.STARVE) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_hungerprotection)) {
                event.setAmount(0);
                player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 100, 1));
            }
        }
    }

    @SubscribeEvent
    public void waterProtection (LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getSource() == DamageSource.DROWN) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Util.hasPoppet(player, ModObjects.poppet_waterprotection)) {
                event.setAmount(0);
                event.setCanceled(true);
                player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 200, 0));
            }
        }
    }
}
