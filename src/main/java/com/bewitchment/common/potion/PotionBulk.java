package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

/**
 * Created by Joseph on 4/2/2020.
 */

public class PotionBulk extends ModPotion {
	
	public PotionBulk() {
		super("bulk", false, 0x660000);
		this.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "e02d546b-c672-4ae6-a241-38be9bb20bb5", 2D, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, 2, health);
	}
}
