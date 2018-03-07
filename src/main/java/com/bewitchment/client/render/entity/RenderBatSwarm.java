package com.bewitchment.client.render.entity;

import com.bewitchment.common.entity.EntityBatSwarm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderBatSwarm extends Render<EntityBatSwarm> {

	public RenderBatSwarm(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBatSwarm entity) {
		return null;
	}

	@Override
	public void doRender(EntityBatSwarm entity, double x, double y, double z, float entityYaw, float partialTicks) {
		// TODO
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
	}
	
	public static class PlayerHider {
		
		@SubscribeEvent
		public void onRenderPlayer(RenderPlayerEvent.Pre evt) {
			if (evt.getEntityPlayer().getRidingEntity() instanceof EntityBatSwarm) {
				evt.setCanceled(true);
			}
		}
		
		@SubscribeEvent
		public void onRenderHand(RenderHandEvent evt) {
			if (Minecraft.getMinecraft().player.getRidingEntity() instanceof EntityBatSwarm) {
				evt.setCanceled(true);
			}
		}
	}
	
}
