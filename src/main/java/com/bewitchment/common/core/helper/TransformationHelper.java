package com.bewitchment.common.core.helper;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;
import com.bewitchment.common.core.net.messages.PlayerVampireBloodChanged;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;

public class TransformationHelper {

	private TransformationHelper() {
	}

	public static void setTypeAndLevel(EntityPlayer player, EnumTransformationType type, int level, boolean isClient) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setType(type);
		data.setLevel(level);
		if (isClient) {
			HotbarAction.refreshActions(player, player.world);
		} else {
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), (EntityPlayerMP) player);
		}
		MinecraftForge.EVENT_BUS.post(new TransformationModifiedEvent(player, type, level));
	}
	
	public void setVampireBlood(EntityPlayer player, int amount) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setBlood(amount);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
		}
	}
	
	/**
	 * @param player
	 *            The player whose blood reserve is being modified
	 * @param amount
	 *            The amount of blood to add (negative values will decrease the total)
	 * @return <i>When adding</i> blood this will return true if the value changed and false otherwise: this is <b>true</b> if there was
	 *         even a little bit of space in the pool, but the blood added was greater than the amount that could be inserted,
	 *         and <b>false</b> if the pool was maxed.<br>
	 *         <i>When removing</i> blood this will return true if ALL the blood requested was drained.
	 *         If the amount drained is greater than the amount available this will return false, and no blood will be drained from the pool
	 * @throws UnsupportedOperationException
	 *             if the player is not a vampire
	 */
	public boolean addVampireBlood(EntityPlayer player, int amount) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		boolean flag = data.addVampireBlood(amount);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
		}
		return flag;
	}
}
