package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;

@SuppressWarnings({"unused"})
public class PotionCursedLeaps extends ModPotion {
	public PotionCursedLeaps() {
		super("cursed_leaps", true, 0x10c440);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		living.motionY += 1 + ((amplifier + 1) / 4d);
		if (living instanceof EntityPlayer)
			((EntityPlayerMP) living).connection.sendPacket(new SPacketEntityVelocity(living));
	}
}