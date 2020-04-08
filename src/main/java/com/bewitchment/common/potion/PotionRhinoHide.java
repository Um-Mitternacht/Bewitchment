package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

/**
 * Created by Joseph on 4/2/2020.
 */

public class PotionRhinoHide extends ModPotion {
	
	public PotionRhinoHide() {
		super("rhino_hide", false, 0xBEBEBE);
		this.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS, "1845f14c-5411-4380-8be7-85e81317523a", 1D, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, 2, health);
	}
}
