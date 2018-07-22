package com.bewitchment.common.core.event;

import com.bewitchment.api.mp.IMagicPowerStorage;
import com.bewitchment.common.core.capability.CapabilityUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Mod.EventBusSubscriber
public class EnergyEvents {

	@SubscribeEvent
	public static void attachPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			// event.addCapability(new ResourceLocation(LibMod.MOD_ID, "M"), new MagicPointsProvider()); TODO
		}
	}

	@SuppressWarnings("ConstantConditions")
	@SubscribeEvent
	public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			CapabilityUtils.copyDataOnPlayerRespawn(event, IMagicPowerStorage.CAPABILITY);
		}
	}

	// TODO:
	// @SubscribeEvent
	// public static void playerUpdate(LivingEvent.LivingUpdateEvent event) {
	// if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
	// final EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
	// final Optional<CapabilityMagicPoints> optional = EnergyHandler.getEnergy(player);
	// if (optional.isPresent()) {
	// final CapabilityMagicPoints energy = optional.get();
	// energyRegen(player, energy);
	// }
	// }
	// }
	//
	// private static void energyRegen(EntityPlayerMP player, CapabilityMagicPoints energy) {
	// if (energy.getRegenTime() == -1) return;
	// if (energy.get() < energy.getMax() && energy.tick() % energy.getRegenTime() == 0) {
	// energy.set(energy.get() + energy.getRegenBurst());
	// energy.tickReset();
	//
	// energy.syncTo(player);
	// }
	// }
}
