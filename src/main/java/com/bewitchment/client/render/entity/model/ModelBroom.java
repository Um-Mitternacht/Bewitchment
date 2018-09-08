package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelBroom - zabi94
 * Created using Tabula 6.0.0
 */
public class ModelBroom extends ModelBase {
	public ModelRenderer handle;
	public ModelRenderer twigs_bound;
	public ModelRenderer twigs;
	float time1;
	float time2;

	public ModelBroom() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.handle = new ModelRenderer(this, 0, 0);
		this.handle.setRotationPoint(-13.0F, 16.0F, 0.0F);
		this.handle.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.4F);
		this.twigs_bound = new ModelRenderer(this, 0, 2);
		this.twigs_bound.setRotationPoint(17.0F, -1.0F, -1.0F);
		this.twigs_bound.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.5F);
		this.twigs = new ModelRenderer(this, 39, 0);
		this.twigs.setRotationPoint(18F, -2.0F, -2.0F);
		this.twigs.addBox(0.0F, 0.0F, 0.0F, 6, 5, 5, 0.5F);

		this.handle.addChild(twigs_bound);
		this.handle.addChild(twigs);
	}

	@Override
	public void render(Entity entity, float limbSwingAmount, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.handle.offsetX, this.handle.offsetY, this.handle.offsetZ);
		GlStateManager.translate(this.handle.rotationPointX * f5, this.handle.rotationPointY * f5, this.handle.rotationPointZ * f5);
		GlStateManager.scale(1.2D, 1.0D, 1.0D);
		GlStateManager.translate(-this.handle.offsetX, -this.handle.offsetY, -this.handle.offsetZ);
		GlStateManager.translate(-this.handle.rotationPointX * f5, -this.handle.rotationPointY * f5, -this.handle.rotationPointZ * f5);
		this.handle.render(f5);
		GlStateManager.popMatrix();

		time1 = MathHelper.sin(entity.ticksExisted * 0.10471975512F);
		time2 = MathHelper.cos(entity.ticksExisted * 0.10471975512F);

		if (entity.getPassengers().isEmpty()) {
			handle.setRotationPoint(-13.0F + 0.2F * time1, 16F + 0.3F * time1, 0.2F * time2);
			handle.rotateAngleX = (time1 * 0.05235987755F - limbSwingAmount * time1 * 0.17453292516F);
			handle.rotateAngleY = time2 * 0.05235987755F - limbSwingAmount * time1 * 0.17453292516F;
		} else {
			handle.setRotationPoint(-13.0F + 0.02F * time1, 16F + 0.03F * time1, 0.02F * time2);
			handle.rotateAngleX = time1 * 0.01745329251F;
			handle.rotateAngleY = time2 * 0.01745329251F;
		}
	}

}
