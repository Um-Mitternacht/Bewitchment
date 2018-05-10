package com.bewitchment.api.transformation;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ITransformation extends IForgeRegistryEntry<ITransformation> {

	public boolean canCrossSalt();

}
