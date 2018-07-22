package com.bewitchment.common.core.event;

import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.core.capability.energy.storage.CapabilityMagicPoints;
import com.bewitchment.common.core.capability.energy.storage.MagicPointsProvider;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;

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
			event.addCapability(new ResourceLocation(LibMod.MOD_ID, "EnergyData"), new MagicPointsProvider());
		}
	}

	@SuppressWarnings("ConstantConditions")
	@SubscribeEvent
	public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		final EntityPlayer oldPlayer = event.getOriginal();
		final EntityPlayer newPlayer = event.getEntityPlayer();

		if (event.isWasDeath() && oldPlayer.hasCapability(CapabilityMagicPoints.CAPABILITY, null) && newPlayer.hasCapability(CapabilityMagicPoints.CAPABILITY, null)) {
			final CapabilityMagicPoints oldCap = oldPlayer.getCapability(CapabilityMagicPoints.CAPABILITY, null);
			final CapabilityMagicPoints newCap = oldPlayer.getCapability(CapabilityMagicPoints.CAPABILITY, null);
			newCap.set(oldCap.get());
			newCap.setMax(oldCap.getMax());
			newCap.setRegen(oldCap.getRegenTime(), oldCap.getRegenBurst());
			newCap.setUses(oldCap.getUses());
		}
	}

	@SubscribeEvent
	public static void onWorldJoin(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP entity = (EntityPlayerMP) event.getEntity();
			Optional<CapabilityMagicPoints> optional = EnergyHandler.getEnergy(entity);
			optional.ifPresent(iEnergy -> iEnergy.syncTo(entity));
		}
	}

	@SubscribeEvent
	public static void playerUpdate(LivingEvent.LivingUpdateEvent event) {
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
			final EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			final Optional<CapabilityMagicPoints> optional = EnergyHandler.getEnergy(player);
			if (optional.isPresent()) {
				final CapabilityMagicPoints energy = optional.get();
				energyRegen(player, energy);
			}
		}
	}

	private static void energyRegen(EntityPlayerMP player, CapabilityMagicPoints energy) {
		if (energy.getRegenTime() == -1) return;
		if (energy.get() < energy.getMax() && energy.tick() % energy.getRegenTime() == 0) {
			energy.set(energy.get() + energy.getRegenBurst());
			energy.tickReset();

			energy.syncTo(player);
		}
	}
}
