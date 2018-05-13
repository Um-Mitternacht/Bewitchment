/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.common.entity.EntitySpellCarrier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class SpellRenderer extends Render<EntitySpellCarrier> {

	private static Random rnd = new Random();

	public SpellRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpellCarrier entity) {
		return null;
	}

	@Override
	public void doRender(EntitySpellCarrier entity, double x, double y, double z, float entityYaw, float partialTicks) {
		double ipx = (entity.posX - entity.lastTickPosX) * partialTicks + entity.lastTickPosX;
		double ipy = (entity.posY - entity.lastTickPosY) * partialTicks + entity.lastTickPosY;
		double ipz = (entity.posZ - entity.lastTickPosZ) * partialTicks + entity.lastTickPosZ;
		ParticleEndRod part = new ParticleEndRod(Minecraft.getMinecraft().world, ipx, ipy, ipz, 0.02 * rnd.nextGaussian(), 0.02 * rnd.nextGaussian(), 0.02 * rnd.nextGaussian());
		part.setMaxAge(14);
		Minecraft.getMinecraft().effectRenderer.addEffect(part);
		part = new ParticleEndRod(Minecraft.getMinecraft().world, ipx, ipy, ipz, 0.02 * rnd.nextGaussian(), 0.02 * rnd.nextGaussian(), 0.02 * rnd.nextGaussian());
		part.setMaxAge(5);
		Minecraft.getMinecraft().effectRenderer.addEffect(part);
		Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.SPELL_WITCH, ipx, ipy, ipz, 0, 0, 0);
	}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
	}

}
