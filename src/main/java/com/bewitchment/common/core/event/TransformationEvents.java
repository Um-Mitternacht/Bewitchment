package com.bewitchment.common.core.event;

import com.bewitchment.api.capability.transformations.ITransformationData;
import com.bewitchment.api.capability.transformations.TransformationHelper;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.TransformationDataProvider;
import com.bewitchment.common.core.hotbar.HotbarAction;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.core.net.messages.NightVisionStatus;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;
import com.bewitchment.common.core.net.messages.PlayerVampireBloodChanged;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class TransformationEvents {

	public static final ResourceLocation PLAYER_DATA = new ResourceLocation(LibMod.MOD_ID, "transformations");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(PLAYER_DATA, new TransformationDataProvider());
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {
		ITransformationData data = event.getOriginal().getCapability(CapabilityTransformationData.CAPABILITY, null);
		TransformationHelper.setTypeAndLevel(event.getEntityPlayer(), data.getType(), data.getLevel(), false);
		if (event.isWasDeath()) {
			TransformationHelper.setVampireBlood(event.getEntityPlayer(), (int) (data.getMaxBlood() * 0.1));
		} else {
			TransformationHelper.setVampireBlood(event.getEntityPlayer(), data.getMaxBlood());
		}
		HotbarAction.refreshActions(event.getEntityPlayer(), event.getEntityPlayer().world);
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent evt) {
		if (evt.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) evt.player;
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), player);
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), player);
			NetworkHandler.HANDLER.sendTo(new EntityInternalBloodChanged(player), player);
			NetworkHandler.HANDLER.sendTo(new NightVisionStatus(player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()), player);
		}
	}
}
