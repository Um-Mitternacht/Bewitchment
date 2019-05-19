package com.bewitchment.api.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("WeakerAccess")
public abstract class Fortune extends IForgeRegistryEntry.Impl<Fortune> {
	/**
	 * does the fortune produce negative effects
	 */
	public final boolean isNegative;

	public Fortune(ResourceLocation name, boolean isNegative) {
		setRegistryName(name);
		this.isNegative = isNegative;
	}

	public Fortune(ResourceLocation name) {
		this(name, false);
	}

	/**
	 * @param player the player that has this fortune
	 * @return true if the fortune successfully activated, false otherwise
	 */
	public abstract boolean apply(EntityPlayer player);

	/**
	 * @param player the player that has this fortune
	 * @return true if the fortune can be applied to the player, false otherwise
	 */
	public boolean isValid(EntityPlayer player) {
		return true;
	}
}