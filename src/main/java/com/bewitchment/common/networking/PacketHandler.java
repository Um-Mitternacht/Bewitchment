package com.bewitchment.common.networking;

import com.bewitchment.Bewitchment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper network;

    private static int nextId = 0;

    public static int next() {
        return nextId++;
    }

    public static void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Bewitchment.MODID);

        network.registerMessage(new PacketChangeBiome.Handler(), PacketChangeBiome.class, next(), Side.CLIENT);
    }

    public static void sendTo(EntityPlayer player, IMessage message) {
        network.sendTo(message, (EntityPlayerMP) player);
    }

    public static void updateTE(TileEntity tile) {
        if (tile != null) {
            SPacketUpdateTileEntity packet = tile.getUpdatePacket();

            if (packet != null && tile.getWorld() instanceof WorldServer) {
                PlayerChunkMapEntry chunk = ((WorldServer) tile.getWorld()).getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);
                if (chunk != null) {
                    chunk.sendPacket(packet);
                }
            }
        }
    }

    public static void updateTE(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        updateTE(tile);
    }
}
