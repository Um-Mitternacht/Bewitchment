package com.bewitchment.common.content.enchantments;

import com.bewitchment.api.mp.IMagicPowerContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentPotentWard extends BaublesEnchantment {

	protected EnchantmentPotentWard() {
		super("potent_ward", Rarity.VERY_RARE, 3);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onDamage(LivingHurtEvent evt) {
		if (evt.getSource().isMagicDamage() && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) evt.getEntityLiving();
			IMagicPowerContainer mpc = p.getCapability(IMagicPowerContainer.CAPABILITY, null);
			if (mpc.getAmount() * 2 > mpc.getMaxAmount()) {
				int maxLevel = this.getMaxLevelOnPlayer(p);
				if (mpc.drain(150)) {
					evt.setAmount(Math.max(evt.getAmount() - maxLevel, 0f));
				}
			}
		}
	}


	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentDesperateWard);
	}
}
