package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.tarot.TarotHandler;
import com.bewitchment.common.content.tarot.TarotHandler.TarotInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class TarotMessage implements IMessage {

	public ArrayList<TarotInfo> info;

	public TarotMessage() {
	}

	public TarotMessage(EntityPlayer p) {
		info = TarotHandler.getTarotsForPlayer(p);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		info = TarotInfo.fromBuffer(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(info.size());
		for (TarotInfo ti : info) {
			buf.writeBoolean(ti.isReversed());
			buf.writeInt(ti.getNumber());
			ByteBufUtils.writeUTF8String(buf, ti.getRegistryName());
		}
	}

	public static class TarotMessageHandler implements IMessageHandler<TarotMessage, IMessage> {

		@Override
		public IMessage onMessage(TarotMessage message, MessageContext ctx) {
			Bewitchment.proxy.handleTarot(message.info);
			return null;
		}
	}
}
