package com.bewitchment.common.core.event;

import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.core.capability.mimic.MimicDataProvider;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MimicHandler {
	public static final ResourceLocation cap = new ResourceLocation(LibMod.MOD_ID, "mimic_data");

	@SubscribeEvent
	public static void onAttach(AttachCapabilitiesEvent<Entity> evt) {
		if (evt.getObject() instanceof EntityPlayer) {
			evt.addCapability(cap, new MimicDataProvider());
		}
	}

	@SubscribeEvent
	public static void onChatMessage(ServerChatEvent event) {
		if (event.getPlayer().hasCapability(CapabilityMimicData.CAPABILITY, null)) {
			final IMimicData capability = event.getPlayer().getCapability(CapabilityMimicData.CAPABILITY, null);
			if (capability.isMimicking()) {
				event.setCanceled(true);
				event.getPlayer().getServerWorld().playerEntities.stream().forEach(player ->
						player.sendMessage(new TextComponentString("<" + capability.getMimickedPlayerName() + "> " + event.getMessage())));
			}
		}
	}

	@SubscribeEvent
	public static void onDisplayName(PlayerEvent.NameFormat event) {
		if (event.getEntity().hasCapability(CapabilityMimicData.CAPABILITY, null)) {
			final IMimicData capability = event.getEntity().getCapability(CapabilityMimicData.CAPABILITY, null);
			if (capability.isMimicking()) {
				event.setDisplayname(capability.getMimickedPlayerName());
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			event.getEntityPlayer().getCapability(CapabilityMimicData.CAPABILITY, null).copyFields(event.getOriginal().getCapability(CapabilityMimicData.CAPABILITY, null));
		}
	}
}
