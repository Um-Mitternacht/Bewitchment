package com.bewitchment.client.core.event;

import com.bewitchment.client.render.entity.renderer.RenderMimicPlayer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.entity.EntityBrew;
import com.bewitchment.common.lib.LibMod;
import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.parallel.ParIterableLike;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = LibMod.MOD_ID)
public class MimicEventHandler {

	private static RenderMimicPlayer cachedRenderer = null;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
		if(event.getEntity().hasCapability(CapabilityMimicData.CAPABILITY, null)) {
			final IMimicData capability = event.getEntity().getCapability(CapabilityMimicData.CAPABILITY, null);
			final AbstractClientPlayer victim = (AbstractClientPlayer) Minecraft.getMinecraft().world.getPlayerEntityByName(capability.getMimickedPlayerName());
			if(capability.isMimicking() && victim != null) {
				event.setCanceled(true);
				if(cachedRenderer == null || cachedRenderer.getMimicID().equals(victim.getUniqueID())) {
					final RenderPlayer victimRender = (RenderPlayer) Minecraft.getMinecraft().getRenderManager().<AbstractClientPlayer>getEntityRenderObject( victim);
					boolean useSmallArms = false;
					try {
						Field armsField = victimRender.getClass().getDeclaredField("smallArms");
						armsField.setAccessible(true);
						useSmallArms = armsField.getBoolean(victimRender);
					} catch (IllegalAccessException | NoSuchFieldException e) {
						Bewitchment.logger.fatal(e);
					}
					cachedRenderer = new RenderMimicPlayer(Minecraft.getMinecraft().getRenderManager(), useSmallArms,
							event.getRenderer(), capability.getMimickedPlayerID(), capability.getMimickedPlayerName());
				}
				float yaw = event.getEntity().prevRotationYaw + (event.getEntity().rotationYaw - event.getEntity().prevRotationYaw) * event.getPartialRenderTick();
				cachedRenderer.doRender((AbstractClientPlayer) event.getEntity(), event.getX(), event.getY(), event.getZ(), yaw, event.getPartialRenderTick());
			}
		}
	}
}
