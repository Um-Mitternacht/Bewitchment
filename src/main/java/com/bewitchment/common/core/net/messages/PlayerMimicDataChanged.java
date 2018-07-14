package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.core.net.SimpleMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PlayerMimicDataChanged implements IMessage {
	public UUID id;
	public NBTTagCompound compound;

	public PlayerMimicDataChanged() {

	}

	public PlayerMimicDataChanged(EntityPlayer player) {
		this.id = player.getUniqueID();
		final IMimicData capability = player.getCapability(CapabilityMimicData.CAPABILITY, null);
		compound = (NBTTagCompound) CapabilityMimicData.CAPABILITY.getStorage().writeNBT(CapabilityMimicData.CAPABILITY, capability, null);
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		this.id = SimpleMessage.readUUID(byteBuf);
		this.compound = SimpleMessage.readNBT(byteBuf);
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		SimpleMessage.writeUUID(id, byteBuf);
		SimpleMessage.writeNBT(compound, byteBuf);
	}

	public static class PlayerMimicDataHandler implements IMessageHandler<PlayerMimicDataChanged, IMessage> {
		@Override
		public IMessage onMessage(PlayerMimicDataChanged playerMimicDataChanged, MessageContext messageContext) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if (Minecraft.getMinecraft().world != null) {
					EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(playerMimicDataChanged.id);
					final IMimicData capability = player.getCapability(CapabilityMimicData.CAPABILITY, null);
					CapabilityMimicData.CAPABILITY.getStorage().readNBT(CapabilityMimicData.CAPABILITY, capability, null, playerMimicDataChanged.compound);
					player.refreshDisplayName();
				}
			});
			return null;
		}
	}
}
