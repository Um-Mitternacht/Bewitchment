package com.bewitchment.api.message;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Tarot;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TarotMessage implements IMessage {
	private List<TarotInfo> infoList = new ArrayList<>();

	public TarotMessage() {
	}

	public TarotMessage(EntityPlayer player) {
		List<Tarot> valid = GameRegistry.findRegistry(Tarot.class).getValuesCollection().stream().filter(f -> f.isCounted(player)).collect(Collectors.toList());
		if (!valid.isEmpty()) {
			while (!valid.isEmpty() && infoList.size() < 4) {
				int i = player.getRNG().nextInt(valid.size());
				infoList.add(new TarotInfo(valid.get(i), player));
				valid.remove(i);
			}
		}
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		infoList = TarotInfo.fromBuffer(byteBuf);
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeInt(infoList.size());
		for (TarotInfo info : infoList) {
			ByteBufUtils.writeUTF8String(byteBuf, info.getTexture().toString());
			byteBuf.writeBoolean(info.isReversed());
			byteBuf.writeInt(info.getNumber());
		}
	}

	public static class Handler implements IMessageHandler<TarotMessage, IMessage> {
		@Override
		public IMessage onMessage(TarotMessage message, MessageContext ctx) {
			if (ctx.side.isClient()) {
				Bewitchment.proxy.handleTarot(message.infoList);
			}
			return null;
		}
	}
}
