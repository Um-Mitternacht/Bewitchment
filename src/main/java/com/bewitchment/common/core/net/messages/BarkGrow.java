package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.core.net.SimpleMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BarkGrow implements IMessage {

	int eid;
	NBTTagCompound tag;

	public BarkGrow() {
	}

	public static Tuple<Integer, NBTTagCompound> encode(BarkGrow msg) {
		System.out.println("Decode");
		return new Tuple<Integer, NBTTagCompound>(msg.eid, msg.tag);
	}

	public static BarkGrow decode(Tuple<Integer, NBTTagCompound> data) {
		BarkGrow msg = new BarkGrow();
		msg.eid = data.getFirst();
		msg.tag = data.getSecond();
		return msg;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		eid = buf.readInt();
		tag = SimpleMessage.readNBT(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(eid);
		SimpleMessage.writeNBT(tag, buf);
	}

}
