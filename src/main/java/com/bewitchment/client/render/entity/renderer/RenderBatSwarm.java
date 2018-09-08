package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.entity.EntityBatSwarm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class RenderBatSwarm extends Render<EntityBatSwarm> {

	private static Random rng = new Random();

	public RenderBatSwarm(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBatSwarm entity) {
		return null;
	}

	@Override
	public void doRender(EntityBatSwarm entity, double x, double y, double z, float entityYaw, float partialTicks) {
		for (int i = 0; i < 4; i++) {
			double ipx = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
			double ipy = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
			double ipz = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
			ipx += rng.nextGaussian() * 0.3;
			ipy += rng.nextGaussian() * 0.3;
			ipz += rng.nextGaussian() * 0.3;
			if (rng.nextInt(4) == 0) {
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, ipx, ipy, ipz, 0, 0, 0);
			} else {
				Bewitchment.proxy.spawnParticle(ParticleF.BAT, ipx, ipy, ipz, 0, 0, 0);
			}
		}
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
