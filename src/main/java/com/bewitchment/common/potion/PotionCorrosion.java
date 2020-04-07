package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

/**
 * Created by Joseph on 4/2/2020.
 */

public class PotionCorrosion extends ModPotion {
	
	public PotionCorrosion() {
		super("corrosion", true, 0x0BDA51);
		this.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "e8506ffe-e2b4-4f19-8669-becb8e3eb666", -2D, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, -2, health);
	}
}
