package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@SuppressWarnings({"unused"})
public class PotionAbsence extends ModPotion {
    public PotionAbsence() {
        super("absence", false, 0xffffff);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        living.clearActivePotions();
    }
}