package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class FortuneBadLuck extends Fortune {
    public FortuneBadLuck() {
        super(new ResourceLocation(Bewitchment.MODID, "bad_luck"), true, (60 * 2), (60 * 10));
    }

    @Override
    public boolean apply(EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 415, 1 + player.world.getDifficulty().ordinal(), false, false));
        return true;
    }
}