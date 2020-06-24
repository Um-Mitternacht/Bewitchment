package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.Util;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ItemVampiricAmulet extends ModItemBauble {
    public ItemVampiricAmulet() {
        super("vampiric_amulet", BaubleType.AMULET);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTakeDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && Util.hasBauble(event.getEntityLiving(), this) && itemRand.nextDouble() < 0.175 && MagicPower.attemptDrain(null, (EntityPlayer) event.getEntityLiving(), 50)) {
            event.setAmount(0);
            event.setCanceled(true);
            event.getEntityLiving().heal(2);
            event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 160));
        }
    }

}
