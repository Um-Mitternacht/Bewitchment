package com.bewitchment.common.core.util;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialItemPlaced extends Material {

	public static final Material ITEM_MATERIAL = new MaterialItemPlaced(MapColor.AIR);

	private MaterialItemPlaced(MapColor color) {
		super(color);
		this.setImmovableMobility();
	}

	@Override
	public boolean blocksLight() {
		return false;
	}

	@Override
	public boolean blocksMovement() {
		return true;
	}

	@Override
	public boolean getCanBurn() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public boolean isToolNotRequired() {
		return true;
	}
}
