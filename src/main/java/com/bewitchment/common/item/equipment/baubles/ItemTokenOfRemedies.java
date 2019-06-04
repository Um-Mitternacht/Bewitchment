package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
public class ItemTokenOfRemedies extends ModItemBauble {
	public ItemTokenOfRemedies() {
		super("token_of_remedies", BaubleType.TRINKET);
		setMaxDamage(8);
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		if (player.ticksExisted % 40 == 0 && (player.isPotionActive(MobEffects.BLINDNESS) || player.isPotionActive(MobEffects.NAUSEA) || player.isPotionActive(MobEffects.POISON) || player.isPotionActive(MobEffects.WEAKNESS) || player.isPotionActive(MobEffects.WITHER))) {
			player.removePotionEffect(MobEffects.BLINDNESS);
			player.removePotionEffect(MobEffects.NAUSEA);
			player.removePotionEffect(MobEffects.POISON);
			player.removePotionEffect(MobEffects.WEAKNESS);
			player.removePotionEffect(MobEffects.WITHER);
			stack.damageItem(1, player);
		}
	}
}