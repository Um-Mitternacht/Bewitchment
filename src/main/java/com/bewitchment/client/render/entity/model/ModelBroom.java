package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * ModelBroom - zabi94
 * Created using Tabula 6.0.0
 */
public class ModelBroom extends ModelBase {
	public ModelRenderer handle;
	public ModelRenderer twigs_bound;
	public ModelRenderer twigs;

	public ModelBroom() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.handle = new ModelRenderer(this, 0, 0);
		this.handle.setRotationPoint(-13.0F, 16.0F, 0.0F);
		this.handle.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.4F);
		this.twigs_bound = new ModelRenderer(this, 0, 2);
		this.twigs_bound.setRotationPoint(8.0F, 15.0F, -1.0F);
		this.twigs_bound.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.5F);
		this.twigs = new ModelRenderer(this, 39, 0);
		this.twigs.setRotationPoint(9.7F, 14.0F, -2.0F);
		this.twigs.addBox(0.0F, 0.0F, 0.0F, 6, 5, 5, 0.5F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.handle.offsetX, this.handle.offsetY, this.handle.offsetZ);
		GlStateManager.translate(this.handle.rotationPointX * f5, this.handle.rotationPointY * f5, this.handle.rotationPointZ * f5);
		GlStateManager.scale(1.3D, 1.0D, 1.0D);
		GlStateManager.translate(-this.handle.offsetX, -this.handle.offsetY, -this.handle.offsetZ);
		GlStateManager.translate(-this.handle.rotationPointX * f5, -this.handle.rotationPointY * f5, -this.handle.rotationPointZ * f5);
		this.handle.render(f5);
		GlStateManager.popMatrix();
		this.twigs_bound.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.twigs.offsetX, this.twigs.offsetY, this.twigs.offsetZ);
		GlStateManager.translate(this.twigs.rotationPointX * f5, this.twigs.rotationPointY * f5, this.twigs.rotationPointZ * f5);
		GlStateManager.scale(1.2D, 1.0D, 1.0D);
		GlStateManager.translate(-this.twigs.offsetX, -this.twigs.offsetY, -this.twigs.offsetZ);
		GlStateManager.translate(-this.twigs.rotationPointX * f5, -this.twigs.rotationPointY * f5, -this.twigs.rotationPointZ * f5);
		this.twigs.render(f5);
		GlStateManager.popMatrix();
	}

}
