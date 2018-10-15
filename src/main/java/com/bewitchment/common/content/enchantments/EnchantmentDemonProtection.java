package com.bewitchment.common.content.enchantments;

import com.bewitchment.api.BewitchmentAPI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDemonProtection extends BaublesEnchantment {

	protected EnchantmentDemonProtection() {
		super("protection_demon", Rarity.UNCOMMON, 3);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onDamageReceived(LivingHurtEvent evt) {
		if (evt.getEntityLiving() instanceof EntityPlayer) {
			if (evt.getSource().getTrueSource() instanceof EntityLivingBase && isDemon((EntityLivingBase) evt.getSource().getTrueSource())) {
				int level = this.getTotalLevelOnPlayer((EntityPlayer) evt.getEntityLiving());
				evt.setAmount(evt.getAmount() * (1f - 0.05f * level));
			}
		}
	}

	private boolean isDemon(EntityLivingBase trueSource) {
		//TODO move this to api and generalize with other creatures
		return trueSource.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON;
	}
}
