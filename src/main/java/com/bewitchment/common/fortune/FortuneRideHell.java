package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("ConstantConditions")
public class FortuneRideHell extends Fortune {
	public FortuneRideHell() {
		super(new ResourceLocation(Bewitchment.MODID, "ride_hell"), true, 10, 4000);
	}
	
	@Override
	public boolean apply(EntityPlayer player) {
		if (player instanceof EntityPlayerMP && player.dimension != -1) player.changeDimension(-1);
		return true;
	}
}