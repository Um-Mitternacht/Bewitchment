package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

@SuppressWarnings({"unused"})
public class PotionCorruption extends ModPotion {
    public PotionCorruption() {
        super("corruption", true, 0x5c007f);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (!living.world.isRemote) {
            PotionEffect[] effects = living.getActivePotionEffects().toArray(new PotionEffect[0]);
            living.clearActivePotions();
            for (PotionEffect effect : effects) if (effect.getPotion().isBadEffect()) living.addPotionEffect(effect);
        }
    }
}