package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

/**
 * Created by Joseph on 4/2/2020.
 */

public class PotionPaperSkin extends ModPotion {
	
	public PotionPaperSkin() {
		super("paper_skin", true, 0xE3DAC9);
		this.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS, "a2cdb3f3-8277-45a0-8a3c-2dcc53a59b61", -1D, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, 2, health);
	}
}
