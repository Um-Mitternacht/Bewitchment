package com.bewitchment.common.networking;

import com.bewitchment.Bewitchment;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashSet;

public class PacketChangeBiome implements IMessage {
    BlockPos[] positions;
    Biome biome;

    public PacketChangeBiome(World world, BlockPos pos) {
        this(world.getBiomeForCoordsBody(pos), pos);
    }

    public PacketChangeBiome(Biome biome, BlockPos... positions) {
        this.positions = positions;
        this.biome = biome;
    }

    public PacketChangeBiome() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        biome = Biome.getBiome(buf.readByte());
        int length = buf.readShort();
        positions = new BlockPos[length];
        for (int i = 0; i < length; i++) {
            positions[i] = BlockPos.fromLong(buf.readLong());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(Biome.getIdForBiome(biome));
        buf.writeShort(positions.length);
        for (BlockPos position : positions) {
            buf.writeLong(position.toLong());
        }
    }

    public static class Handler implements IMessageHandler<PacketChangeBiome, IMessage> {
        @Override
        public IMessage onMessage(PacketChangeBiome message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Bewitchment.proxy.getPlayer(ctx).world;

                HashSet<ChunkPos> finishedPos = new HashSet<>();
                for (BlockPos pos : message.positions) {
                    if (finishedPos.add(new ChunkPos(pos))) {
                        int chunkX = pos.getX() >> 4;
                        int chunkZ = pos.getZ() >> 4;
                        world.markBlockRangeForRenderUpdate(chunkX << 4, 0, chunkZ << 4, (chunkX << 4) + 15, 256, (chunkZ << 4) + 15);
                    }
                }
            });
            return null;
        }

    }
}