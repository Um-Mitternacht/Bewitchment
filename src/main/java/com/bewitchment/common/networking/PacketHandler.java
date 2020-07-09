package com.bewitchment.common.networking;

import com.bewitchment.Bewitchment;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {
    public static SimpleNetworkWrapper network;

    private static int nextId = 0;

    public static int next() {
        return nextId++;
    }

    public static void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Bewitchment.MODID);
    }
}
