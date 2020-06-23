package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Joseph on 5/27/2020.
 */
public class PotionRage extends ModPotion {
	public PotionRage() {
		super("rage", true, 0x660000);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		EntityPlayer player = (EntityPlayer) indirectSource;
		living.setRevengeTarget(player);
	}
}
