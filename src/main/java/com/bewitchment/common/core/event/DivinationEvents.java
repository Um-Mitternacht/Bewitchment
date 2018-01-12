package com.bewitchment.common.core.event;

import com.bewitchment.api.divination.Fortune;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import com.bewitchment.common.core.capability.divination.DivinationProvider;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class DivinationEvents {

	public static final ResourceLocation PLAYER_DATA = new ResourceLocation(LibMod.MOD_ID, "divination");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(PLAYER_DATA, new DivinationProvider());
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {
		EntityPlayer newPlayer = event.getEntityPlayer();
		EntityPlayer oldPlayer = event.getOriginal();
		CapabilityDivination newPlayerData = newPlayer.getCapability(CapabilityDivination.CAPABILITY, null);
		CapabilityDivination oldPlayerData = oldPlayer.getCapability(CapabilityDivination.CAPABILITY, null);
		newPlayerData.setFortune(oldPlayerData.getFortune());
	}

	@SubscribeEvent
	public void onLivingTick(PlayerTickEvent evt) {
		if (!evt.player.world.isRemote && evt.phase == Phase.END) {
			CapabilityDivination data = evt.player.getCapability(CapabilityDivination.CAPABILITY, null);
			Fortune f = data.getFortune();
			if (f != null) {
				if (f.canShouldBeAppliedNow(evt.player)) {
					if (f.apply(evt.player))
						data.setFortune(null);
				}
			}
		}
	}
}
