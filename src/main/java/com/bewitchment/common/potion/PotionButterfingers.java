package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by Joseph on 4/7/2020.
 */
public class PotionButterfingers extends ModPotion {
	public PotionButterfingers() {
		super("butterfingers", true, 0xF8DE7E);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		ItemStack stack = living.getHeldItem(EnumHand.MAIN_HAND);
		living.entityDropItem(stack, 1);
	}
}
