package com.bewitchment.common.core.net.messages;

import java.util.ArrayList;

import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.common.Bewitchment;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ActionRefreshedMessage implements IMessage {

	public ArrayList<String> info = new ArrayList<String>();

	public ActionRefreshedMessage() {
	}

	public ActionRefreshedMessage(ArrayList<HotbarAction> p) {
		p.stream().map(h -> h.getName().toString()).forEach(s -> info.add(s));
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			info.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(info.size());
		for (String s : info)
			ByteBufUtils.writeUTF8String(buf, s);
	}

	public static class ActionRefreshedMessageHandler implements IMessageHandler<ActionRefreshedMessage, IMessage> {

		@Override
		public IMessage onMessage(ActionRefreshedMessage message, MessageContext ctx) {
			Bewitchment.proxy.loadActionsClient(message.info);
			return null;
		}
	}
}
