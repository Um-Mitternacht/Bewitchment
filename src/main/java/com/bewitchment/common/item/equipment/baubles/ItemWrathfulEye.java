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

/**
 * Created by Joseph on 5/23/2019.
 */
@SuppressWarnings("unused")
public class ItemWrathfulEye extends ModItemBauble {
	//This will be used for trading in the diabolic update, and also provide a minute amount of protection from fire. Not as good as the hellish bauble though.
	public ItemWrathfulEye() {
		super("wrathful_eye", BaubleType.AMULET);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, .75F, 1.9f);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if (living.ticksExisted % 40 == 0 && living.isPotionActive(MobEffects.WEAKNESS)) living.removePotionEffect(MobEffects.WEAKNESS);
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (Util.hasBauble(event.getEntityLiving(), this) && event.getSource().isFireDamage()) event.setAmount(event.getAmount() * 0.95f);
	}
}
