package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.PlayerMimicDataChanged;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import javax.annotation.Nullable;

public class PotionRevealing extends BrewMod {
	public PotionRevealing() {
		super("revealing", false, 0x00FFFF, true, 0);
	}

	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		if (!entityLivingBaseIn.getEntityWorld().isRemote) {
			if (entityLivingBaseIn.isPotionActive(MobEffects.INVISIBILITY)) {
				entityLivingBaseIn.removePotionEffect(MobEffects.INVISIBILITY);
			}
			if (entityLivingBaseIn.hasCapability(CapabilityMimicData.CAPABILITY, null)) {
				final IMimicData capability = entityLivingBaseIn.getCapability(CapabilityMimicData.CAPABILITY, null);
				if (capability.isMimicking()) {
					capability.setMimicking(false, (EntityPlayer) entityLivingBaseIn);
				}
				NetworkHandler.HANDLER.sendToAll(new PlayerMimicDataChanged((EntityPlayer) entityLivingBaseIn));
			}
		}
	}
}
