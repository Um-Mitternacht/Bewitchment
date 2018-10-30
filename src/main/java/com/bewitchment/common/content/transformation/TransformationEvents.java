package com.bewitchment.common.content.transformation;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.actionbar.HotbarAction;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.content.transformation.capability.ITransformationData;
import com.bewitchment.common.core.helper.TransformationHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.core.net.messages.NightVisionStatus;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;
import com.bewitchment.common.core.net.messages.PlayerVampireBloodChanged;
import com.bewitchment.common.potion.ModPotions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber
public class TransformationEvents {

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.Clone event) {
		ITransformationData data = event.getOriginal().getCapability(CapabilityTransformationData.CAPABILITY, null);
		BewitchmentAPI.getAPI().setTypeAndLevel(event.getEntityPlayer(), data.getType(), data.getLevel(), false);
		if (event.isWasDeath()) {
			TransformationHelper.setVampireBlood(event.getEntityPlayer(), (int) (data.getMaxBlood() * 0.1));
			if (data.getType() == DefaultTransformations.VAMPIRE) {
				event.getEntityPlayer().addPotionEffect(new PotionEffect(ModPotions.sun_ward, 600, 0));
			}
		} else {
			TransformationHelper.setVampireBlood(event.getEntityPlayer(), data.getMaxBlood());
		}
		HotbarAction.refreshActions(event.getEntityPlayer(), event.getEntityPlayer().world);
	}

	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent evt) {
		if (evt.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) evt.player;
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), player);
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), player);
			NetworkHandler.HANDLER.sendTo(new EntityInternalBloodChanged(player), player);
			NetworkHandler.HANDLER.sendTo(new NightVisionStatus(player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()), player);
		}
	}
	
	@SubscribeEvent
	public static void onStartTracking(StartTracking evt) {
		if (evt.getEntity() instanceof EntityPlayer && evt.getEntityPlayer() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) evt.getEntity();
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), (EntityPlayerMP) evt.getEntityPlayer());
		}
	}
	
	@SubscribeEvent
	public static void livingTick(LivingUpdateEvent evt) {
		if (evt.getEntityLiving() instanceof EntityPlayer && evt.getEntityLiving().getCapability(CapabilityTransformationData.CAPABILITY, null).needsSync()) {
			NetworkHandler.HANDLER.sendToAllTracking(new PlayerTransformationChangedMessage((EntityPlayer) evt.getEntityLiving()), evt.getEntityLiving());
			evt.getEntityLiving().getCapability(CapabilityTransformationData.CAPABILITY, null).markAsSynced();
		}
	}
}
