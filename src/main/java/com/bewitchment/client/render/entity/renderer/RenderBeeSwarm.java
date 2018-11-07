package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.entity.EntityBees;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBeeSwarm extends Render<EntityBees> {

	public RenderBeeSwarm(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBees entity) {
		return null;
	}

	@Override
	public void doRender(EntityBees entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (entity.getParticles().isEmpty()) {
			for (int i = 0; i < (3 - Minecraft.getMinecraft().gameSettings.particleSetting) * 10; i++) {
				Particle bee = ParticleF.BEE.newInstance(x, y, z, 0, 0, 0);
				entity.getParticles().add(bee);
				Minecraft.getMinecraft().effectRenderer.addEffect(bee);
			}
		}
	}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
	}
}
