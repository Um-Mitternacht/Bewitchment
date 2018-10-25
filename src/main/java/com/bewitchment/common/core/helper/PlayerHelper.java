package com.bewitchment.common.core.helper;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PlayerHelper {
	
	public static @Nullable EntityPlayer getPlayerAcrossDimensions(UUID uuid) {
		for (WorldServer ws:DimensionManager.getWorlds()) {
			EntityPlayer player = ws.getPlayerEntityByUUID(uuid);
			if (player != null) {
				return player;
			}
		}
		return null;
	}
	
	public static @Nullable EntityPlayer getPlayerAcrossDimensions(String name) {
		for (WorldServer ws:DimensionManager.getWorlds()) {
			EntityPlayer player = ws.getPlayerEntityByName(name);
			if (player != null) {
				return player;
			}
		}
		return null;
	}
}
