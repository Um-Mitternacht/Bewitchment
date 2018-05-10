package com.bewitchment.common.attributes;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.math.MathHelper;

public class BarkAmountAttribute implements IAttribute {

	public static BarkAmountAttribute INSTANCE = new BarkAmountAttribute();

	@Override
	public String getName() {
		return "bark_amount";
	}

	@Override
	public double clampValue(double value) {
		return MathHelper.clamp(value, 0, 5);
	}

	@Override
	public double getDefaultValue() {
		return 0;
	}

	@Override
	public boolean getShouldWatch() {
		return true;
	}

	@Override
	public IAttribute getParent() {
		return SharedMonsterAttributes.ARMOR;
	}

}
