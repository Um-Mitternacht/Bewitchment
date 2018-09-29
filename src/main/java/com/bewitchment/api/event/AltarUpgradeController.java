package com.bewitchment.api.event;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class AltarUpgradeController {

	private BlockPos[] lockedTypes;

	public AltarUpgradeController() {
		lockedTypes = new BlockPos[AltarUpgradeController.EnumUpgradeClass.values().length];
	}

	public void use(AltarUpgradeController.EnumUpgradeClass obj, BlockPos pos) {
		if (lockedTypes[obj.ordinal()] == null) {
			lockedTypes[obj.ordinal()] = pos;
		}
	}

	@Nullable
	public BlockPos[] getModifierPositions() {
		return lockedTypes.clone();
	}

	public static enum EnumUpgradeClass {
		CUPS, //Container shaped objects (+ their content), boost multiplier
		WANDS, //For now light sources, boost gain. Should be expanded to better represent wands
		PENTACLES, //Anything generally magical. Boost both gain and multiplier
		SWORDS //Ceremonial or magical swords and knives, they change unique features of the altar
	}

}