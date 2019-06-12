package com.bewitchment.api.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Tarot extends IForgeRegistryEntry.Impl<Tarot> {
	public final ResourceLocation texture;
	
	public Tarot(ResourceLocation name, ResourceLocation texture) {
		setRegistryName(name);
		this.texture = texture;
	}
	
	public abstract boolean isValid(EntityPlayer player);
}