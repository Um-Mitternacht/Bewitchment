package com.bewitchment.api.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings({"unused", "SameReturnValue"})
public abstract class Tarot extends IForgeRegistryEntry.Impl<Tarot> {
    public final ResourceLocation texture;

    public Tarot(ResourceLocation name, ResourceLocation texture) {
        setRegistryName(name);
        this.texture = texture;
    }

    public boolean isCounted(EntityPlayer player) {
        return true;
    }

    public boolean isReversed(EntityPlayer player) {
        return false;
    }

    public int getNumber(EntityPlayer player) {
        return -1;
    }
}