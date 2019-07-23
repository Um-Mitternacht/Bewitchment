package com.bewitchment.api.message;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.magicpower.MagicPower;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SyncStackCapability implements IMessage {
	public NBTTagCompound tag;
	public int slot;
	
	public SyncStackCapability() {
	}
	
	public SyncStackCapability(NBTTagCompound tag, int slot) {
		this.tag = tag;
		this.slot = slot;
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		tag = ByteBufUtils.readTag(byteBuf);
		slot = byteBuf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		ByteBufUtils.writeTag(byteBuf, tag);
		byteBuf.writeInt(slot);
	}
	
	@SuppressWarnings("ConstantConditions")
	public static class Handler implements IMessageHandler<SyncStackCapability, IMessage> {
		@Override
		public IMessage onMessage(SyncStackCapability message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> Bewitchment.proxy.getEntireInventory(null).get(message.slot).getCapability(MagicPower.CAPABILITY, null).deserializeNBT(message.tag));
			return null;
		}
	}
}