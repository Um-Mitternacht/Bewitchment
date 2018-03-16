package com.bewitchment.api.capability;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IInfusion extends IForgeRegistryEntry<IInfusion> {

	public int getDimensionAffinity();

	public ResourceLocation getTexture();

}
