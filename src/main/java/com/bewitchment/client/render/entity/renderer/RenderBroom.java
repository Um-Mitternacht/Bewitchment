package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelBroom;
import com.bewitchment.common.entity.EntityFlyingBroom;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBroom extends Render<EntityFlyingBroom> {

	private static final ModelBroom model = new ModelBroom();

	private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
			new ResourceLocation(LibMod.MOD_ID, "textures/entity/broom_elder.png"),
			new ResourceLocation(LibMod.MOD_ID, "textures/entity/broom_juniper.png"),
			new ResourceLocation(LibMod.MOD_ID, "textures/entity/broom_yew.png"),
			new ResourceLocation(LibMod.MOD_ID, "textures/entity/broom_cypress.png")
	};

	public RenderBroom(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingBroom entity) {
		return TEXTURES[entity.getType() - 1];
	}

	@Override
	public void doRender(EntityFlyingBroom entity, double x, double y, double z, float entityYaw, float partialTicks) {

		GlStateManager.pushMatrix();
		bindEntityTexture(entity);

		float smoothYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

		GlStateManager.translate(x, y - 0.5d, z);
		GlStateManager.scale(0.0625d, 0.0625d, 0.0625d);
		GlStateManager.rotate(90 - smoothYaw, 0, 1, 0);
		GlStateManager.disableLighting();
		model.render(entity, 0f, 0f, 0f, 0f, 0f, 1f);
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

}
