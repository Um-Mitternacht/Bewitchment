package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;


public class ItemTriskelion extends ModItemBauble {
	public ItemTriskelion() {
		super("triskelion", BaubleType.AMULET);
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (player.world.getTotalWorldTime() % 200 == 0 && MagicPower.attemptDrain(null, (EntityPlayer) player, 1)) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 240, 0, false, false));
		}
	}
}
