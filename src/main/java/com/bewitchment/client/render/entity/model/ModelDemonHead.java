package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelDemonHead - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelDemonHead extends ModelBase {
	public ModelRenderer bipedHead;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer lHorn02;
	public ModelRenderer lHorn03a;
	public ModelRenderer lHorn03b;
	public ModelRenderer lHorn03c;
	public ModelRenderer lHorn03d;
	public ModelRenderer lHorn04;
	public ModelRenderer rHorn02;
	public ModelRenderer rHorn03a;
	public ModelRenderer rHorn03b;
	public ModelRenderer rHorn03c;
	public ModelRenderer rHorn03d;
	public ModelRenderer rHorn04;

	public ModelDemonHead() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.lHorn04 = new ModelRenderer(this, 35, 10);
		this.lHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn04, -0.13962634015954636F, 0.0F, -0.10471975511965977F);
		this.rHorn03d = new ModelRenderer(this, 35, 5);
		this.rHorn03d.mirror = true;
		this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn04 = new ModelRenderer(this, 35, 10);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn04, -0.13962634015954636F, 0.0F, 0.10471975511965977F);
		this.lHorn02 = new ModelRenderer(this, 32, 0);
		this.lHorn02.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn02, -0.10471975511965977F, 0.0F, -0.19198621771937624F);
		this.rHorn03c = new ModelRenderer(this, 35, 5);
		this.rHorn03c.mirror = true;
		this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.lHorn03a = new ModelRenderer(this, 35, 5);
		this.lHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.lHorn03a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn03a, -0.13962634015954636F, 0.0F, -0.06981317007977318F);
		this.lHorn03b = new ModelRenderer(this, 35, 5);
		this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.rHorn01 = new ModelRenderer(this, 32, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-2.9F, -7.4F, -1.3F);
		this.rHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn01, 0.10471975511965977F, 0.0F, -0.41887902047863906F);
		this.lHorn03c = new ModelRenderer(this, 35, 5);
		this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rHorn03b = new ModelRenderer(this, 35, 5);
		this.rHorn03b.mirror = true;
		this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.lHorn01 = new ModelRenderer(this, 32, 0);
		this.lHorn01.setRotationPoint(2.9F, -7.4F, -1.3F);
		this.lHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn01, 0.10471975511965977F, 0.0F, 0.41887902047863906F);
		this.lHorn03d = new ModelRenderer(this, 35, 5);
		this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn02 = new ModelRenderer(this, 32, 0);
		this.rHorn02.mirror = true;
		this.rHorn02.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn02, -0.10471975511965977F, 0.0F, 0.19198621771937624F);
		this.rHorn03a = new ModelRenderer(this, 35, 5);
		this.rHorn03a.mirror = true;
		this.rHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.rHorn03a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn03a, -0.13962634015954636F, 0.0F, 0.06981317007977318F);
		this.lHorn03a.addChild(this.lHorn04);
		this.rHorn03a.addChild(this.rHorn03d);
		this.rHorn03a.addChild(this.rHorn04);
		this.lHorn01.addChild(this.lHorn02);
		this.rHorn03a.addChild(this.rHorn03c);
		this.lHorn02.addChild(this.lHorn03a);
		this.lHorn03a.addChild(this.lHorn03b);
		this.bipedHead.addChild(this.rHorn01);
		this.lHorn03a.addChild(this.lHorn03c);
		this.rHorn03a.addChild(this.rHorn03b);
		this.bipedHead.addChild(this.lHorn01);
		this.lHorn03a.addChild(this.lHorn03d);
		this.rHorn01.addChild(this.rHorn02);
		this.rHorn02.addChild(this.rHorn03a);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bipedHead.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
