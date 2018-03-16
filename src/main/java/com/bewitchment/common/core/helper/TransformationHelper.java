package com.bewitchment.common.core.helper;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.transformations.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.core.net.messages.PlayerVampireBloodChanged;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

@SuppressWarnings("deprecation")
public class TransformationHelper {

	private TransformationHelper() {
	}


	public static void setVampireBlood(EntityPlayer player, int amount) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setBlood(amount);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
		}
	}

	public static void drainBloodFromEntity(EntityPlayer player, EntityLivingBase entity) {
		IBloodReserve br = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (br.getBlood() > 0 && br.getMaxBlood() > 0) {
			int transferred = (int) Math.min(br.getBlood(), br.getBlood() * 0.05 * data.getLevel());
			if (transferred > 0 && (BewitchmentAPI.getAPI().addVampireBlood(player, transferred) || player.isSneaking())) {
				br.setBlood(br.getBlood() - transferred);
				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(entity), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32));
			}
		}
	}
}
