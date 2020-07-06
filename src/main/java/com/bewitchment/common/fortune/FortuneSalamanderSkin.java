package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

public class FortuneSalamanderSkin extends Fortune {
	public FortuneSalamanderSkin() {
		super(new ResourceLocation(Bewitchment.MODID, "salamander_skin"), false, (60), (60 * 10));
	}

	@Override
	public boolean isValid(EntityPlayer player) {
		if (player.world.provider.getDimensionType() == DimensionType.NETHER) {
			return true;
		}
		return false;
	}

	@Override
	public boolean apply(EntityPlayer player) {
		if (this.isValid(player)) {
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1000, 0, false, false));
			return true;
		}
		return false;
	}
}