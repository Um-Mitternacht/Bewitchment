package com.bewitchment.common.core.capability.simple;

import com.bewitchment.common.core.net.SimpleMessage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilityMessage extends SimpleMessage<CapabilityMessage> {

	public NBTTagCompound tag;
	public int capabilityId;
	public int entityID;
	
	public CapabilityMessage() {
	}
	
	public CapabilityMessage(int capId, NBTTagCompound tag, int entityId) {
		this.tag = tag;
		this.capabilityId = capId;
		this.entityID = entityId;
	}
	
	@Override
	public IMessage handleMessage(MessageContext context) {
		SimpleCapability.messageReceived(tag, capabilityId, entityID);
		return null;
	}
	
}
