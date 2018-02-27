package com.bewitchment.common.core.helper;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.IBloodReserve;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;
import com.bewitchment.common.core.net.messages.PlayerVampireBloodChanged;
import com.bewitchment.common.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TransformationHelper {

	private TransformationHelper() {
	}

	public static void setTypeAndLevel(EntityPlayer player, EnumTransformationType type, int level, boolean isClient) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setType(type);
		data.setLevel(level);
		data.setNightVision(data.isNightVisionActive() && (type == EnumTransformationType.WEREWOLF || type == EnumTransformationType.VAMPIRE));
		if (isClient) {
			HotbarAction.refreshActions(player, player.world);
		} else {
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), (EntityPlayerMP) player);
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

	public static void drainBloodFromEntity(EntityPlayer player, EntityLivingBase entity, int amount) {
		IBloodReserve br = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		if (br.getBlood() > 0 && br.getMaxBlood() > 0) {
			int transferred = Math.min(br.getBlood(), amount);
			if (transferred > 0 && addVampireBlood(player, transferred)) {
				br.setBlood(br.getBlood() - transferred);
				float stored = br.getPercentFilled();
				if (stored > 0 && stored < 0.4f) {
					entity.addPotionEffect(new PotionEffect(ModPotions.bloodDrained, 200, 0));
				}
				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(entity), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32));
			}
		}
	}
}
