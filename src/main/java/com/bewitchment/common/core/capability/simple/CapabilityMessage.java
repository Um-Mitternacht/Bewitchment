package com.bewitchment.common.core.capability.simple;

import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Register this in your mods' network handler, with Side.CLIENT
public class CapabilityMessage extends SimpleMessage<CapabilityMessage> {

	public NBTTagCompound tag;
	public int capabilityId;
	public int entityID;
	private byte dirt;

	public CapabilityMessage() {
	}

	public CapabilityMessage(int capId, NBTTagCompound tag, int entityId, byte dirt) {
		this.tag = tag;
		this.capabilityId = capId;
		this.entityID = entityId;
		this.dirt = dirt;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		SimpleCapability.messageReceived(tag, capabilityId, entityID, dirt);
		return null;
	}

}
