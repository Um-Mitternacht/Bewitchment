package com.bewitchment.api.capability;

import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;

public enum EnumInfusionType {
	NONE(""), OVERWORLD("_overworld"), NETHER("_nether"), END("_end"), DREAM("_dream");
	
	private ResourceLocation path;
	
	private EnumInfusionType(ResourceLocation path) {
		this.path = path;
	}
	
	private EnumInfusionType(String path) {
		this(new ResourceLocation(LibMod.MOD_ID, "textures/gui/energy" + path + ".png"));
	}
	
	public int getMeta() {
		return ordinal() + 1;
	}
	
	public static EnumInfusionType fromMeta(int meta) {
		if (meta < 1 || meta > values().length) {
			return NONE;
		}
		return values()[meta - 1];
	}
	
	public ResourceLocation getTexture() {
		return path;
	}
}
