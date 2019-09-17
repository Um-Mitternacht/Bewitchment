package com.bewitchment.common.handler;

import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArmorHandler {
    @SubscribeEvent
    public void reducePotionDamage(LivingDamageEvent event) {
        if(!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (event.getSource().getDamageType().equals("indirectMagic") && (player.inventory.armorItemInSlot(3).getItem() == ModObjects.alchemist_hat || player.inventory.armorItemInSlot(3).getItem() == ModObjects.alchemist_cowl)
                    && player.inventory.armorItemInSlot(2).getItem() == ModObjects.alchemist_robes && player.inventory.armorItemInSlot(1).getItem() == ModObjects.alchemist_pants) {
                event.setAmount(event.getAmount() * 0.5f);
            }
        }
    }

    @SubscribeEvent
    public void reduceNegativeEffectLevel(LivingEvent.LivingUpdateEvent event) {
        if(!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if ((player.inventory.armorItemInSlot(3).getItem() == ModObjects.alchemist_hat || player.inventory.armorItemInSlot(3).getItem() == ModObjects.alchemist_cowl)
                    && player.inventory.armorItemInSlot(2).getItem() == ModObjects.alchemist_robes && player.inventory.armorItemInSlot(1).getItem() == ModObjects.alchemist_pants) {
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    if (effect.getPotion().isBadEffect() && effect.getAmplifier() > 0) {
                        PotionEffect newEffect = new PotionEffect(effect.getPotion(), effect.getDuration(), 0);
                        player.removePotionEffect(effect.getPotion());
                        player.addPotionEffect(newEffect);
                    }
                }
            }
        }
    }
}
