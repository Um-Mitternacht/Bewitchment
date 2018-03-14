package com.bewitchment.api.hotbar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface IHotbarAction {

	public ResourceLocation getName();

	public int getIconIndexX(EntityPlayer player);

	public int getIconIndexY(EntityPlayer player);

	public ResourceLocation getIcon(EntityPlayer player);
}
