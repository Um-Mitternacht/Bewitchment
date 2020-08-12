package com.bewitchment.api.registry;

import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings({"unused", "SameReturnValue"})
@Data
public abstract class Fortune extends IForgeRegistryEntry.Impl<Fortune> {
    /**
     * does the fortune produce negative effects
     */
    private final boolean isNegative;

    /**
     * the maximum time in seconds it takes for the fortune to activate
     */
    private final int maxTime;

    /**
     * the minimum time in seconds it takes for the fortune to activate
     */
    private final int minTime;

    public Fortune(ResourceLocation name, boolean isNegative, int minTime, int maxTime) {
        setRegistryName(name);
        this.isNegative = isNegative;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    /**
     * @param player the player that has this fortune
     * @return true if the fortune can be applied to the player, false otherwise
     */
    public boolean isValid(EntityPlayer player) {
        return true;
    }

    /**
     * @param player the player that has this fortune
     * @return true if the fortune successfully activated, false otherwise
     */
    public abstract boolean apply(EntityPlayer player);
}