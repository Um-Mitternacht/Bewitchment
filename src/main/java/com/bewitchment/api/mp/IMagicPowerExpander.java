package com.bewitchment.api.mp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface IMagicPowerExpander {
	public ResourceLocation getID();

	public int getExtraAmount(EntityPlayer p);
}
