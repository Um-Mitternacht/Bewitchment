package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
public class ItemTokenOfRemedies extends ModItemBauble {
	public ItemTokenOfRemedies() {
		super("token_of_remedies", BaubleType.TRINKET);
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if (living.ticksExisted % 80 == 0 && (living.isPotionActive(MobEffects.BLINDNESS) || living.isPotionActive(MobEffects.NAUSEA) || living.isPotionActive(MobEffects.WEAKNESS) && living instanceof EntityPlayer && MagicPower.attemptDrain(null, (EntityPlayer) living, 20))) {
			living.removePotionEffect(MobEffects.BLINDNESS);
			living.removePotionEffect(MobEffects.NAUSEA);
			living.removePotionEffect(MobEffects.WEAKNESS);
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}
}