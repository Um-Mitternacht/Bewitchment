package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FortuneSalamanderSkin extends Fortune {
    public FortuneSalamanderSkin() {
        super(new ResourceLocation(Bewitchment.MODID, "salamander_skin"), false, (60), (60 * 10));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean apply(EntityPlayer player) {
        return true;
    }

    @SubscribeEvent
    public void onNetherPotion(PotionEvent.PotionAddedEvent event) {
        ExtendedPlayer cap = event.getEntityLiving().getCapability(ExtendedPlayer.CAPABILITY, null);
        if (cap.fortune == this) {
            if (event.getEntityLiving().world.provider.isNether()) {
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1000, 0, false, false));
            }
        }
    }
}