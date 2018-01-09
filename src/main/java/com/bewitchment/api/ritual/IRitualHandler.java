package com.bewitchment.api.ritual;

import net.minecraft.entity.player.EntityPlayer;

public interface IRitualHandler {
	public boolean consumePower(int power);

	public void stopRitual(EntityPlayer player);
}
