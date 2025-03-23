package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SpawnParticle implements IMessage {
    public EnumParticleTypes type;
    public double x, y, z, speedX, speedY, speedZ;

    public SpawnParticle() {
    }

    public SpawnParticle(EnumParticleTypes type, double x, double y, double z, double speedX, double speedY, double speedZ) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
    }

    public SpawnParticle(EnumParticleTypes type, double x, double y, double z) {
        this(type, x, y, z, 0, 0, 0);
    }

    public SpawnParticle(EnumParticleTypes type, BlockPos pos, int speedX, int speedY, int speedZ) {
        this(type, pos.getX(), pos.getY(), pos.getZ(), speedX, speedY, speedZ);
    }

    public SpawnParticle(EnumParticleTypes type, BlockPos pos) {
        this(type, pos, 0, 0, 0);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        type = EnumParticleTypes.getParticleFromId(byteBuf.readInt());
        x = byteBuf.readDouble();
        y = byteBuf.readDouble();
        z = byteBuf.readDouble();
        speedX = byteBuf.readDouble();
        speedY = byteBuf.readDouble();
        speedZ = byteBuf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(type.getParticleID());
        byteBuf.writeDouble(x);
        byteBuf.writeDouble(y);
        byteBuf.writeDouble(z);
        byteBuf.writeDouble(speedX);
        byteBuf.writeDouble(speedY);
        byteBuf.writeDouble(speedZ);
    }

    public static class Handler implements IMessageHandler<SpawnParticle, IMessage> {
        @Override
        public IMessage onMessage(SpawnParticle message, MessageContext ctx) {
            if (ctx.side.isClient())
                Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().world.spawnParticle(message.type, message.x, message.y, message.z, message.speedX, message.speedY, message.speedZ));
            return null;
        }
    }
}