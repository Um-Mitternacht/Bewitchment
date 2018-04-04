package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.potion.BrewMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;

public class PotionBaneArthropods extends BrewMod {
	
	public PotionBaneArthropods() {
		super("bane_arthropods", true, 0x50C878, true);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		if (entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
			entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), 5 + (5 * amplifier));
		}
	}
	
}
