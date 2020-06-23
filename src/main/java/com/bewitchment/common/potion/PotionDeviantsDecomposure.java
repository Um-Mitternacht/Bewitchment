package com.bewitchment.common.potion;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.DamageSource;

@SuppressWarnings({"unused"})
public class PotionDeviantsDecomposure extends ModPotion {
    public PotionDeviantsDecomposure() {
        super("deviants_decomposure", true, 0xdc7f10);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (living instanceof AbstractIllager || BewitchmentAPI.isWitch(living))
            living.attackEntityFrom(DamageSource.MAGIC, 8 * (amplifier + 1));
    }
}