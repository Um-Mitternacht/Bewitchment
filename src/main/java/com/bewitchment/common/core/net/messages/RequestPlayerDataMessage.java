package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.core.net.PacketHandler;
import com.bewitchment.common.core.net.SimpleMessage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RequestPlayerDataMessage extends SimpleMessage<RequestPlayerDataMessage> {

	public int type;

	public RequestPlayerDataMessage() {
	}

	public RequestPlayerDataMessage(DataType... types) {
		type = 0;
		for (DataType t : types) {
			type |= t.getBinaryID();
		}
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		EntityPlayerMP player = context.getServerHandler().player;
		if (DataType.isRequested(DataType.TRANSFORMATION_DATA, type)) {
			PacketHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), player);
		}
		if (DataType.isRequested(DataType.VAMPIRE_BLOOD, type)) {
			PacketHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), player);
		}
		return null;
	}
	
	public static enum DataType {
		
		TRANSFORMATION_DATA, VAMPIRE_BLOOD;
		
		public int getBinaryID() {
			return 1 << ordinal();
		}
		
		public static boolean isRequested(DataType type, int request) {
			return ((request >> type.ordinal()) & 1) == 1;
		}
		
	}

}
