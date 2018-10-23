package com.bewitchment.common.content.enchantments;

import com.bewitchment.api.mp.IMagicPowerContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDesperateWard extends BaublesEnchantment {

	protected EnchantmentDesperateWard() {
		super("desperate_ward", Rarity.COMMON, 5);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onDamage(LivingHurtEvent evt) {
		if (evt.getSource().isMagicDamage() && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) evt.getEntityLiving();
			IMagicPowerContainer mpc = p.getCapability(IMagicPowerContainer.CAPABILITY, null);
			if (mpc.getAmount() * 5 < mpc.getMaxAmount()) {
				int level = this.getMaxLevelOnPlayer(p);
				if (mpc.drain(10)) {
					evt.setAmount(evt.getAmount() * (1f - 0.04f * level));
				}
			}
		}
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentPotentWard);
	}
}
