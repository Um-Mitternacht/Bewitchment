package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;

@SuppressWarnings({"unused"})
public class PotionRevealing extends ModPotion {
    public PotionRevealing() {
        super("revealing", true, 0x00c4ff);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        living.removePotionEffect(MobEffects.INVISIBILITY);
    }
}