package com.bewitchment.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

public abstract class ItemSigil extends Item {
	public int cooldown;
	public boolean positive;

	public ItemSigil(int cooldown, boolean positive) {
		this.cooldown = cooldown;
		this.positive = positive;
	}

	public abstract void applyEffects(EntityLivingBase entity);
}
