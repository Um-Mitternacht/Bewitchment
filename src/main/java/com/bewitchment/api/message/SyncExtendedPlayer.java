package com.bewitchment.api.message;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused"})
public class SyncExtendedPlayer implements IMessage {
    private NBTTagCompound tag;

    public SyncExtendedPlayer() {
    }

    public SyncExtendedPlayer(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        tag = ByteBufUtils.readTag(byteBuf);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        ByteBufUtils.writeTag(byteBuf, tag);
    }

    @SuppressWarnings("ConstantConditions")
    public static class Handler implements IMessageHandler<SyncExtendedPlayer, IMessage> {
        @Override
        public IMessage onMessage(SyncExtendedPlayer message, MessageContext ctx) {
            if (ctx.side.isClient())
                Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().player.getCapability(ExtendedPlayer.CAPABILITY, null).deserializeNBT(message.tag));
            return null;
        }
    }
}