package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 5/22/2019.
 */
@SuppressWarnings("unused")
public class ItemHorseshoe extends ModItemBauble {
	public ItemHorseshoe() {
		super("horseshoe", BaubleType.TRINKET);
		MinecraftForge.EVENT_BUS.register(this);
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
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (Util.hasBauble(event.getEntityLiving(), this) && (event.getSource().getTrueSource() instanceof EntityLivingBase && ((EntityLivingBase) event.getSource().getTrueSource()).getCreatureAttribute() == BewitchmentAPI.SPIRIT))
			event.setAmount(event.getAmount() * 0.8f);
		if (Util.hasBauble(event.getEntityLiving(), this) && (event.getSource().getTrueSource() instanceof EntityLivingBase && ((EntityLivingBase) event.getSource().getTrueSource()).getCreatureAttribute() == BewitchmentAPI.DEMON))
			event.setAmount(event.getAmount() * 0.97f);
	}
}