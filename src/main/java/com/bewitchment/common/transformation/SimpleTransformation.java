package com.bewitchment.common.transformation;

import com.bewitchment.api.capability.transformations.ITransformation;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;

public class SimpleTransformation implements ITransformation {

	private ResourceLocation rn;

	public SimpleTransformation(String name) {
		this.setRegistryName(new ResourceLocation(LibMod.MOD_ID, name));
	}

	@Override
	public ITransformation setRegistryName(ResourceLocation name) {
		rn = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return rn;
	}

	@Override
	public Class<ITransformation> getRegistryType() {
		return ITransformation.class;
	}

}
