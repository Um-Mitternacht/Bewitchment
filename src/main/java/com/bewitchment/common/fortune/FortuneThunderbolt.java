package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class FortuneThunderbolt extends Fortune {
	public FortuneThunderbolt() {
		super(new ResourceLocation(Bewitchment.MODID, "thunderbolt"), true, (60 * 2), (60 * 10));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		if (player.world.isRainingAt(player.getPosition())) {
			player.world.addWeatherEffect(new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, true));
			player.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 1f);
		}
		return true;
	}
}