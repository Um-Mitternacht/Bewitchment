package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelOwl;
import com.bewitchment.common.entity.living.familiar.EntityOwl;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderOwl extends Render<EntityOwl> {
	
	private static ModelOwl model = new ModelOwl();
	
	private static final ResourceLocation[] textures = new ResourceLocation[1];
	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_1.png");
	}
	
	public RenderOwl(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public void doRender(EntityOwl e, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(e, x, y, z, entityYaw, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + 1, z);
		GlStateManager.scale(0.02d, 0.02d, 0.02d);
		GlStateManager.rotate(180f, 0, 0, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(getEntityTexture(e));
		model.render(e, 0, 0, 0, 0, 0, 1);
		GlStateManager.popMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityOwl entity) {
		return textures[entity.getFamiliarSkin()];
	}
	
}
