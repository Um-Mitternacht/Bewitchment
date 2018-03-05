package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.net.SimpleMessage;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NightVisionStatus extends SimpleMessage<NightVisionStatus> {
	
	public boolean active;
	
	public NightVisionStatus() {
		// NO-OP
	}
	
	public NightVisionStatus(boolean status) {
		active = status;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null).setNightVision(active);
		return null;
	}
	
}
