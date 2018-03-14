package com.bewitchment.common.core.event;

import com.bewitchment.api.divination.IFortune;
import com.bewitchment.common.core.capability.CapabilityUtils;
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
		CapabilityUtils.copyDataOnPlayerRespawn(event, CapabilityDivination.CAPABILITY);
	}

	@SubscribeEvent
	public void onLivingTick(PlayerTickEvent evt) {
		if (!evt.player.world.isRemote && evt.phase == Phase.END) {
			CapabilityDivination data = evt.player.getCapability(CapabilityDivination.CAPABILITY, null);
			IFortune f = data.getFortune();
			if (f != null) {
				if (data.isRemovable()) {
					data.setFortune(null);
				} else {
					if (f.canShouldBeAppliedNow(evt.player)) {
						data.setActive();
					}

					if (data.isActive()) {
						if (f.apply(evt.player))
							data.setFortune(null);
					}
				}
			}
		}
	}
}
