package com.bewitchment.common.item.sigils;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

public abstract class ItemSigil extends Item {
	public abstract void applyEffects(EntityLivingBase entity);
}
