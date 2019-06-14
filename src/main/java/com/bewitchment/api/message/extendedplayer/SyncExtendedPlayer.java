package com.bewitchment.api.message.extendedplayer;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

@SuppressWarnings({"unused", "ConstantConditions", "WeakerAccess"})
public class SyncExtendedPlayer implements IMessage {
	public ExtendedPlayer cap = ExtendedPlayer.CAPABILITY.getDefaultInstance();
	
	public SyncExtendedPlayer() {
	}
	
	public SyncExtendedPlayer(ExtendedPlayer cap) {
		this.cap = cap;
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		cap.deserializeNBT(ByteBufUtils.readTag(byteBuf));
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		ByteBufUtils.writeTag(byteBuf, cap.serializeNBT());
	}
}