package com.bewitchment.api.infusion;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IInfusion extends IForgeRegistryEntry<IInfusion> {

	public int getDimensionAffinity();

	public ResourceLocation getTexture();

}
