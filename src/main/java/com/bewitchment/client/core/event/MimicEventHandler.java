package com.bewitchment.client.core.event;

import com.bewitchment.client.core.event.custom.MimicEvent;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.lib.LibReflection;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LibMod.MOD_ID)
public class MimicEventHandler {

	@SideOnly(Side.CLIENT)
	private static final Field playerTextures = ReflectionHelper.findField(NetworkPlayerInfo.class, "playerTextures", LibReflection.NETWORK_PLAYER_INFO__PLAYER_TEXTURES);
	@SideOnly(Side.CLIENT)
	private static final HashMap<UUID, ResourceLocation> skinMap = new HashMap<>();
	@SideOnly(Side.CLIENT)
	private static final HashMap<UUID, String> modelType = new HashMap<>();

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void fakeSkin(MimicEvent event) {
		final AbstractClientPlayer player = (AbstractClientPlayer) event.getEntityPlayer();
		if (event.isReverting()) {
			stopMimicking(player);
		} else {
			NetworkPlayerInfo victimInfo = new NetworkPlayerInfo(new GameProfile(event.getVictimID(), event.getVictimName()));
			setPlayerSkin(player, victimInfo.getLocationSkin());
		}
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	private static void setPlayerSkin(AbstractClientPlayer player, ResourceLocation skin) {
		NetHandlerPlayClient connection = Minecraft.getMinecraft().getConnection();
		if (connection == null) {
			Bewitchment.logger.debug("Connection object is null, cannot fake skin");
			return;
		}

		NetworkPlayerInfo info = connection.getPlayerInfo(player.getUniqueID());

		UUID uuid = EntityPlayer.getUUID(player.getGameProfile());
		if (!skinMap.containsKey(uuid)) {
			skinMap.put(uuid, player.getLocationSkin());
			modelType.put(uuid, info.getSkinType());
		}

		try {
			Map<Type, ResourceLocation> ptextures = (Map<Type, ResourceLocation>) playerTextures.get(info);
			ptextures.put(Type.SKIN, skin);
			// TODO find a way to change the model
		} catch (IllegalArgumentException | IllegalAccessException e) {
			Bewitchment.logger.fatal(e);
		}

	}

	@SideOnly(Side.CLIENT)
	public static void stopMimicking(AbstractClientPlayer p) {
		if (skinMap.containsKey(p.getUniqueID())) {
			setPlayerSkin(p, skinMap.get(p.getUniqueID()));
			// TODO find a way to restore model
		}
	}
}
