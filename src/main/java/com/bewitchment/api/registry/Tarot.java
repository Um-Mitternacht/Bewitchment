package com.bewitchment.api.registry;

import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings({"unused", "SameReturnValue"})
@Data
public abstract class Tarot extends IForgeRegistryEntry.Impl<Tarot> {
    private final ResourceLocation texture;

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