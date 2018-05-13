package com.bewitchment.client.render.entity.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 10/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class EmptyRenderer<T extends Entity> extends Render<T> {

	public EmptyRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}
}
