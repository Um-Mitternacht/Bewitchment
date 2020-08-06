package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.Util;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.item.util.ModItemBauble;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
		maxStackSize = 1;
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if (living.ticksExisted % 40 == 0 && living.isPotionActive(MobEffects.UNLUCK) && living instanceof EntityPlayer && MagicPower.attemptDrain(null, (EntityPlayer) living, 20))
			living.removePotionEffect(MobEffects.UNLUCK);
		if (living.ticksExisted % 40 == 0 && living.isPotionActive(ModPotions.fear) && living instanceof EntityPlayer && MagicPower.attemptDrain(null, (EntityPlayer) living, 20))
			living.removePotionEffect(ModPotions.fear);
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (Util.hasBauble(event.getEntityLiving(), this) && event.getSource().isMagicDamage() && event.getEntityLiving() instanceof EntityPlayer && MagicPower.attemptDrain(null, (EntityPlayer) event.getEntityLiving(), 20))
			event.setAmount(event.getAmount() * 0.9f);
	}
}