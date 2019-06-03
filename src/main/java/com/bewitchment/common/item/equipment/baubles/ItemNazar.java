package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.Util;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class ItemNazar extends ModItemBauble {
	public ItemNazar() {
		super("nazar", BaubleType.AMULET);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if (living.ticksExisted % 40 == 0 && living.isPotionActive(MobEffects.UNLUCK)) living.removePotionEffect(MobEffects.UNLUCK);
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (Util.hasBauble(event.getEntityLiving(), this) && event.getSource().isMagicDamage()) event.setAmount(event.getAmount() * 0.9f);
	}
}