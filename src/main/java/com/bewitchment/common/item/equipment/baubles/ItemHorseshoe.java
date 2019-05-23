package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

/**
 * Created by Joseph on 5/22/2019.
 */
public class ItemHorseshoe extends ModItemBauble {
	public ItemHorseshoe() {
		super("horseshoe", BaubleType.TRINKET);
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		if (player.ticksExisted % 40 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 120, 0, true, true));
		}
	}
}