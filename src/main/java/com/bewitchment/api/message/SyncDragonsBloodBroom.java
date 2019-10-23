package com.bewitchment.api.message;

import com.bewitchment.common.entity.misc.EntityDragonsBloodBroom;
import com.bewitchment.common.item.ItemSigil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SyncDragonsBloodBroom implements IMessage {
	private ItemSigil sigil;
	private int id;
	
	public SyncDragonsBloodBroom() {
	}
	
	public SyncDragonsBloodBroom(EntityDragonsBloodBroom entity, ItemSigil sigil) {
		this.sigil = sigil;
		this.id = entity.getEntityId();
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		String sigilName = ByteBufUtils.readUTF8String(byteBuf);
		sigil = sigilName.equals("") ? null : (ItemSigil) GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(sigilName));
		id = byteBuf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		ByteBufUtils.writeUTF8String(byteBuf, sigil == null ? "" : sigil.getRegistryName().toString());
		byteBuf.writeInt(id);
	}
	
	public static class Handler implements IMessageHandler<SyncDragonsBloodBroom, IMessage> {
		@Override
		public IMessage onMessage(SyncDragonsBloodBroom message, MessageContext ctx) {
			if (ctx.side.isClient()) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					EntityDragonsBloodBroom entity = (EntityDragonsBloodBroom) Minecraft.getMinecraft().player.world.getEntityByID(message.id);
					if (entity != null) entity.setSigil(message.sigil);
				});
			}
			return null;
		}
	}
}
