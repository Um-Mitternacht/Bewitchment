package com.bewitchment.common.handler;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.registry.ModEnchantments;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class ArmorHandler {
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if (!event.getEntityLiving().world.isRemote) {
			// Silver Armor
			int silverArmor = Util.getArmorPieces(event.getEntityLiving(), ModObjects.ARMOR_SILVER);
			if (silverArmor > 0) {
				if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
					if (BewitchmentAPI.isWeakToSilver((EntityLivingBase) event.getSource().getTrueSource())) {
						event.setAmount(event.getAmount() * (1 - Math.min(20, silverArmor * 2) / 25f));
						event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), Util.getArmorPieces(event.getEntityLiving(), ModObjects.ARMOR_SILVER));
					}
				}
			}
			
			// Silver Tools
			if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) event.getSource().getTrueSource();
				if (BewitchmentAPI.isWeakToSilver(event.getEntityLiving()) && Util.isRelated(living.getHeldItemMainhand(), "ingotSilver")) event.setAmount(event.getAmount() * 2.75f);
			}
			
			// Cold Iron Armor
			ModEnchantments.spirit_protection.applyEnchantment(event, Util.getArmorPieces(event.getEntityLiving(), ModObjects.ARMOR_COLD_IRON) / 2);
			
			// Cold Iron Tools
			if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) event.getSource().getTrueSource();
				if (BewitchmentAPI.isWeakToColdIron(event.getEntityLiving()) && Util.isRelated(living.getHeldItemMainhand(), "ingotColdIron")) {
					event.setAmount(event.getAmount() * 2.75f);
					event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), Util.getArmorPieces(event.getEntityLiving(), ModObjects.ARMOR_COLD_IRON));
				}
			}
			
			// Witches' Armor
			ModEnchantments.magic_protection.applyEnchantment(event, Util.getArmorPieces(event.getEntityLiving(), ModObjects.ARMOR_WITCHES));
		}
	}
}