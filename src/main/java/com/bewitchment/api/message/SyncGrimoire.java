package com.bewitchment.api.message;

import com.bewitchment.Util;
import com.bewitchment.api.capability.magicpower.MagicPower;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SyncGrimoire implements IMessage {
	public NBTTagCompound tag;
	public int slot;
	
	public SyncGrimoire() {
	}
	
	public SyncGrimoire(NBTTagCompound tag, int slot) {
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
	public static class Handler implements IMessageHandler<SyncGrimoire, IMessage> {
		@Override
		public IMessage onMessage(SyncGrimoire message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) Minecraft.getMinecraft().addScheduledTask(() -> Util.getEntireInventory(Minecraft.getMinecraft().player).get(message.slot).getCapability(MagicPower.CAPABILITY, null).deserializeNBT(message.tag));
			return null;
		}
	}
}