package com.bewitchment.api.capability.transformations;

import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.*;
import com.bewitchment.common.potion.ModPotions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

@SuppressWarnings("deprecation")
public class TransformationHelper {

	private TransformationHelper() {
	}


	public static void setTypeAndLevel(EntityPlayer player, EnumTransformationType type, int level, boolean isClient) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setType(type);
		data.setLevel(level);
		data.setNightVision(data.isNightVisionActive() && (type == EnumTransformationType.WEREWOLF || type == EnumTransformationType.VAMPIRE));
		if ((type == EnumTransformationType.SPECTRE || type == EnumTransformationType.VAMPIRE)) {
			player.getCapability(CapabilityBloodReserve.CAPABILITY, null).setMaxBlood(-1);
			player.removePotionEffect(ModPotions.bloodDrained);
		} else {
			player.getCapability(CapabilityBloodReserve.CAPABILITY, null).setMaxBlood(480);
		}
		if (isClient) {
			HotbarAction.refreshActions(player, player.world);
		} else {
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), (EntityPlayerMP) player);
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
			NetworkHandler.HANDLER.sendTo(new EntityInternalBloodChanged(player), (EntityPlayerMP) player);
			NetworkHandler.HANDLER.sendTo(new NightVisionStatus(player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()), (EntityPlayerMP) player);

		}
		MinecraftForge.EVENT_BUS.post(new TransformationModifiedEvent(player, type, level));
	}

	public static void setVampireBlood(EntityPlayer player, int amount) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setBlood(amount);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
		}
	}

	/**
	 * @param player The player whose blood reserve is being modified
	 * @param amount The amount of blood to add (negative values will decrease the total)
	 * @return <i>When adding</i> blood this will return true if the value changed and false otherwise: this is <b>true</b> if there was
	 * even a little bit of space in the pool, but the blood added was greater than the amount that could be inserted,
	 * and <b>false</b> if the pool was maxed.<br>
	 * <i>When removing</i> blood this will return true if ALL the blood requested was drained.
	 * If the amount drained is greater than the amount available this will return false, and no blood will be drained from the pool
	 * @throws UnsupportedOperationException if the player is not a vampire
	 */
	public static boolean addVampireBlood(EntityPlayer player, int amount) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		boolean flag = data.addVampireBlood(amount);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
		}
		return flag;
	}

	public static void drainBloodFromEntity(EntityPlayer player, EntityLivingBase entity) {
		IBloodReserve br = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (br.getBlood() > 0 && br.getMaxBlood() > 0) {
			int transferred = (int) Math.min(br.getBlood(), br.getBlood() * 0.05 * data.getLevel());
			if (transferred > 0 && (addVampireBlood(player, transferred) || player.isSneaking())) {
				br.setBlood(br.getBlood() - transferred);
				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(entity), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32));
			}
		}
	}
}
