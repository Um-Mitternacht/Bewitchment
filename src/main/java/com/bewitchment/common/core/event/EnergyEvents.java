package com.bewitchment.common.core.event;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.core.capability.energy.player.PlayerMPContainer;
import com.bewitchment.common.core.capability.energy.player.expansion.CapabilityMPExpansion;
import com.bewitchment.common.core.helper.CapabilityHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EnergySync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Mod.EventBusSubscriber
public class EnergyEvents {

	@SubscribeEvent
	public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			CapabilityHelper.copyDataOnPlayerRespawn(event, IMagicPowerContainer.CAPABILITY);
			CapabilityHelper.copyDataOnPlayerRespawn(event, CapabilityMPExpansion.CAPABILITY);
		}
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent evt) {
		if (evt.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) evt.player;
			IMagicPowerContainer mp = player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			syncExpansions(player);
			NetworkHandler.HANDLER.sendTo(new EnergySync(mp.getAmount(), mp.getMaxAmount()), player);
		}
	}

	@SubscribeEvent
	public static void playerUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayerMP) {
			final EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			final PlayerMPContainer energy = (PlayerMPContainer) player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			if (energy.getAmount() < energy.getMaxAmount() && player.ticksExisted % (10 * getRegenTime(player)) == 0) {
				energy.fill(getRegenBurst(player));
			}
			if (energy.isDirty()) {
				syncExpansions(player);
				NetworkHandler.HANDLER.sendTo(new EnergySync(energy.getAmount(), energy.getMaxAmount()), player);
				energy.setClean();
			}
		}
	}

	private static void syncExpansions(EntityPlayer p) {
		CapabilityMPExpansion exps = p.getCapability(CapabilityMPExpansion.CAPABILITY, null);
		if (exps.isDirty()) {
			IMagicPowerContainer impc = p.getCapability(IMagicPowerContainer.CAPABILITY, null);
			int energy = impc.getAmount();
			int futureMaxAmount = PlayerMPContainer.STARTING_PLAYER_POWER + exps.getTotalIncrease();
			if (futureMaxAmount < 0) {
				futureMaxAmount = 0;
			}
			if (energy > futureMaxAmount) {
				energy = futureMaxAmount;
			}
			impc.setAmount(energy);
			impc.setMaxAmount(futureMaxAmount);
			exps.clean();
		}
	}

	private static int getRegenTime(EntityPlayerMP player) {
		// TODO Auto-generated method stub
		if (player.isCreative()) {
			return 1;
		}
		return 10;
	}

	private static int getRegenBurst(EntityPlayerMP player) {
		// TODO Auto-generated method stub
		if (player.isCreative()) {
			return 100;
		}
		return 10;
	}
}
