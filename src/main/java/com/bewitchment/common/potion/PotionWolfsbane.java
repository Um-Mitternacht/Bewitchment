package com.bewitchment.common.potion;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityWerewolf;
import com.bewitchment.common.entity.spirit.demon.EntityHellhound;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.DamageSource;

@SuppressWarnings({"unused"})
public class PotionWolfsbane extends ModPotion {
	public PotionWolfsbane() {
		super("wolfsbane", false, 0x3f00ff);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (BewitchmentAPI.isWerewolf(living) || living instanceof EntityWolf || living instanceof EntityWerewolf || living instanceof EntityHellhound || living.getClass().getName().endsWith("EntityCoyote") || living.getClass().getName().endsWith("EntityFeralWolf") || living.getClass().getName().endsWith("EntityFox"))
			living.attackEntityFrom(DamageSource.MAGIC, 8 * (amplifier + 1));
	}
}