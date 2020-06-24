package com.bewitchment.common.potion;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;

@SuppressWarnings({"unused"})
public class PotionHolyWater extends ModPotion {
    public PotionHolyWater() {
        super("holy_water", false, 0x7f7fff);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (living.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || living.isImmuneToFire() || BewitchmentAPI.getColdIronWeakness(living) > 1)
            living.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), 8 * (amplifier + 1));
    }
}