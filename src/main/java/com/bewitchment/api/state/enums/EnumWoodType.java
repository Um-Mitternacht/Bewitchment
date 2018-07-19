package com.bewitchment.api.state.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumWoodType implements IStringSerializable {
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, BIG_OAK, ELDER, JUNIPER, YEW, CYPRESS;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

}
