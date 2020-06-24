package com.bewitchment.api.registry.entity.client;

import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("NullableProblems")
@SideOnly(Side.CLIENT)
public abstract class RenderBroom extends Render<EntityBroom> {
	private static final ModelBroom model = new ModelBroom();

	public RenderBroom(RenderManager manager) {
		super(manager);
	}

	@Override
	public void doRender(EntityBroom entity, double x, double y, double z, float entityYaw, float partialTicks) {
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