package com.bewitchment.common.content.transformation;

import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;

public class SimpleTransformation implements ITransformation {

	private ResourceLocation rn;
	private boolean crossSalt;

	public SimpleTransformation(String name, boolean salt) {
		this.setRegistryName(new ResourceLocation(LibMod.MOD_ID, name));
		crossSalt = salt;
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

	@Override
	public boolean canCrossSalt() {
		return crossSalt;
	}

}
