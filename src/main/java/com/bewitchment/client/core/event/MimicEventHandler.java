package com.bewitchment.client.core.event;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.lib.LibReflection;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public static void fakeSkin(RenderPlayerEvent.Pre event) {
		if (event.getEntity().hasCapability(CapabilityMimicData.CAPABILITY, null)) {
			final IMimicData capability = event.getEntity().getCapability(CapabilityMimicData.CAPABILITY, null);
			final AbstractClientPlayer victim = (AbstractClientPlayer) Minecraft.getMinecraft().world.getPlayerEntityByName(capability.getMimickedPlayerName());
			final AbstractClientPlayer player = (AbstractClientPlayer) event.getEntityPlayer();
			if (victim != null) {
				if (capability.isMimicking()) {
					setPlayerSkin(player, victim.getLocationSkin());
				}
			} else {
				SkinManager sm = Minecraft.getMinecraft().getSkinManager();
				Map<Type, MinecraftProfileTexture> map = sm.loadSkinFromCache(new GameProfile(capability.getMimickedPlayerID(), capability.getMimickedPlayerName()));
				sm.loadSkin(map.get(Type.SKIN), Type.SKIN, new SkinAvailableCallback() {
					
					@Override
					public void skinAvailable(Type typeIn, ResourceLocation location, MinecraftProfileTexture profileTexture) {
						if (capability.isMimicking()) {
							setPlayerSkin(player, location);
						}
					}
				});
			}
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
