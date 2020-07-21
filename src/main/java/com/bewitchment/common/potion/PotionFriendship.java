package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Joseph on 5/27/2020.
 */
public class PotionFriendship extends ModPotion {
	public PotionFriendship() {
		super("friendship", false, 0xF4C2C2);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		EntityPlayer player = (EntityPlayer) indirectSource;
		if (living instanceof EntityTameable && !living.getClass().getName().contains("Dragon"))
			((EntityTameable) living).setTamedBy(player);
	}
}
