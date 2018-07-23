package com.bewitchment.common.core.event;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.core.capability.CapabilityUtils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Mod.EventBusSubscriber
public class EnergyEvents {

	@SuppressWarnings("ConstantConditions")
	@SubscribeEvent
	public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			CapabilityUtils.copyDataOnPlayerRespawn(event, IMagicPowerContainer.CAPABILITY);
		}
	}

	@SubscribeEvent
	public static void playerUpdate(LivingEvent.LivingUpdateEvent event) {
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
			final EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			final IMagicPowerContainer energy = player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			if (energy.getAmount() < energy.getMaxAmount() && player.ticksExisted % getRegenTime(player) == 0) {
				energy.fill(getRegenBurst(player));
			}
			
			// energy.syncTo(player); TODO
		}
	}
	
	private static int getRegenTime(EntityPlayerMP player) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static int getRegenBurst(EntityPlayerMP player) {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}
}
