package com.bewitchment.api.message;

import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncExtendedWorld implements IMessage {
	
	private NBTTagCompound nbt;
	
	public SyncExtendedWorld() {
	}
	
	public SyncExtendedWorld(NBTTagCompound nbt) {
		this.nbt = nbt;
	}
	
	@Override
	public void fromBytes(final ByteBuf byteBuf) {
		nbt = ByteBufUtils.readTag(byteBuf);
	}
	
	@Override
	public void toBytes(final ByteBuf byteBuf) {
		ByteBufUtils.writeTag(byteBuf, nbt);
	}
	
	public static class Handler implements IMessageHandler<SyncExtendedWorld, IMessage> {
		@Override
		public IMessage onMessage(final SyncExtendedWorld syncExtendedWorld, final MessageContext messageContext) {
			if (messageContext.side.isClient()) Minecraft.getMinecraft().addScheduledTask(() -> ExtendedWorld.get(Minecraft.getMinecraft().world).readFromNBT(syncExtendedWorld.nbt));
			return null;
		}
	}
}
