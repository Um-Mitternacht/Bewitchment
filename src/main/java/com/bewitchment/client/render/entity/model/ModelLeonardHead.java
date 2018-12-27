package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelLeonardHead - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelLeonardHead extends ModelBase {
	public ModelRenderer bipedHead;
	public ModelRenderer muzzleUpper;
	public ModelRenderer muzzleLower;
	public ModelRenderer snout;
	public ModelRenderer lEar;
	public ModelRenderer rEar;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer mHorn01;
	public ModelRenderer muzzleUpper02;
	public ModelRenderer beard;
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
	public ModelRenderer mHorn02;
	public ModelRenderer mHorn03a;
	public ModelRenderer mHorn03b;
	public ModelRenderer mHorn03c;
	public ModelRenderer mHorn03d;
	public ModelRenderer mHorn04;

	public ModelLeonardHead() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.mHorn02 = new ModelRenderer(this, 40, 11);
		this.mHorn02.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.mHorn02.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(mHorn02, -0.13962634015954636F, 0.0F, 0.0F);
		this.rHorn03c = new ModelRenderer(this, 25, 6);
		this.rHorn03c.mirror = true;
		this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03c.addBox(-0.8F, -2.8F, -0.2F, 1, 3, 1, 0.0F);
		this.rHorn01 = new ModelRenderer(this, 25, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-1.8F, -5.0F, -1.5F);
		this.rHorn01.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(rHorn01, -0.5235987755982988F, 0.0F, -0.5235987755982988F);
		this.rHorn03d = new ModelRenderer(this, 25, 6);
		this.rHorn03d.mirror = true;
		this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d.addBox(-0.2F, -2.8F, -0.2F, 1, 3, 1, 0.0F);
		this.rEar = new ModelRenderer(this, 40, 0);
		this.rEar.mirror = true;
		this.rEar.setRotationPoint(-2.6F, -4.3F, 0.0F);
		this.rEar.addBox(-4.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
		this.setRotateAngle(rEar, -0.5235987755982988F, 0.0F, -0.3141592653589793F);
		this.snout = new ModelRenderer(this, 24, 36);
		this.snout.setRotationPoint(0.0F, -4.1F, -3.3F);
		this.snout.addBox(-2.0F, -1.0F, -4.4F, 4, 2, 5, 0.0F);
		this.setRotateAngle(snout, 0.41887902047863906F, 0.0F, 0.0F);
		this.lHorn03b = new ModelRenderer(this, 25, 6);
		this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03b.addBox(-0.2F, -2.8F, -0.8F, 1, 3, 1, 0.0F);
		this.lHorn02 = new ModelRenderer(this, 25, 0);
		this.lHorn02.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.lHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn02, -0.13962634015954636F, 0.0F, 0.20943951023931953F);
		this.mHorn03a = new ModelRenderer(this, 25, 6);
		this.mHorn03a.setRotationPoint(0.0F, -2.8F, 0.0F);
		this.mHorn03a.addBox(-0.8F, -2.8F, -0.8F, 1, 3, 1, 0.0F);
		this.setRotateAngle(mHorn03a, -0.3141592653589793F, 0.0F, 0.0F);
		this.muzzleUpper = new ModelRenderer(this, 24, 45);
		this.muzzleUpper.setRotationPoint(0.0F, -1.4F, -3.8F);
		this.muzzleUpper.addBox(-1.6F, -1.8F, -3.5F, 4, 2, 4, 0.0F);
		this.setRotateAngle(muzzleUpper, 0.06981317007977318F, 0.0F, 0.0F);
		this.muzzleLower = new ModelRenderer(this, 23, 54);
		this.muzzleLower.setRotationPoint(0.0F, -0.8F, -3.6F);
		this.muzzleLower.addBox(-2.0F, -0.5F, -3.5F, 4, 1, 4, 0.0F);
		this.setRotateAngle(muzzleLower, 0.06981317007977318F, 0.0F, 0.0F);
		this.lHorn03a = new ModelRenderer(this, 25, 6);
		this.lHorn03a.setRotationPoint(0.0F, -1.9F, 0.0F);
		this.lHorn03a.addBox(-0.8F, -2.8F, -0.8F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn03a, -0.3141592653589793F, 0.0F, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.bipedHead.addBox(-3.0F, -6.0F, -3.4F, 6, 6, 6, 0.0F);
		this.beard = new ModelRenderer(this, 53, 0);
		this.beard.setRotationPoint(0.0F, 0.3F, -2.0F);
		this.beard.addBox(-1.5F, 0.0F, -1.0F, 3, 3, 2, 0.0F);
		this.setRotateAngle(beard, -0.10471975511965977F, 0.0F, 0.0F);
		this.lEar = new ModelRenderer(this, 40, 0);
		this.lEar.setRotationPoint(2.6F, -4.3F, 0.0F);
		this.lEar.addBox(0.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
		this.setRotateAngle(lEar, -0.5235987755982988F, 0.0F, 0.3141592653589793F);
		this.mHorn04 = new ModelRenderer(this, 50, 7);
		this.mHorn04.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.mHorn04.addBox(-0.5F, -3.7F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(mHorn04, 0.2617993877991494F, 0.0F, 0.0F);
		this.rHorn02 = new ModelRenderer(this, 25, 0);
		this.rHorn02.mirror = true;
		this.rHorn02.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.rHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn02, -0.13962634015954636F, 0.0F, -0.20943951023931953F);
		this.lHorn03c = new ModelRenderer(this, 25, 6);
		this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03c.addBox(-0.8F, -2.8F, -0.2F, 1, 3, 1, 0.0F);
		this.lHorn04 = new ModelRenderer(this, 31, 6);
		this.lHorn04.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.lHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn04, 0.2617993877991494F, 0.0F, 0.0F);
		this.mHorn03b = new ModelRenderer(this, 25, 6);
		this.mHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mHorn03b.addBox(-0.2F, -2.8F, -0.8F, 1, 3, 1, 0.0F);
		this.rHorn04 = new ModelRenderer(this, 31, 6);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.rHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn04, 0.2617993877991494F, 0.0F, 0.0F);
		this.rHorn03a = new ModelRenderer(this, 25, 6);
		this.rHorn03a.mirror = true;
		this.rHorn03a.setRotationPoint(0.0F, -1.9F, 0.0F);
		this.rHorn03a.addBox(-0.8F, -2.8F, -0.8F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn03a, -0.3141592653589793F, 0.0F, 0.0F);
		this.rHorn03b = new ModelRenderer(this, 25, 6);
		this.rHorn03b.mirror = true;
		this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03b.addBox(-0.2F, -2.8F, -0.8F, 1, 3, 1, 0.0F);
		this.lHorn01 = new ModelRenderer(this, 25, 0);
		this.lHorn01.setRotationPoint(1.8F, -5.0F, -1.5F);
		this.lHorn01.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(lHorn01, -0.5235987755982988F, 0.0F, 0.5235987755982988F);
		this.lHorn03d = new ModelRenderer(this, 25, 6);
		this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d.addBox(-0.2F, -2.8F, -0.2F, 1, 3, 1, 0.0F);
		this.mHorn03c = new ModelRenderer(this, 25, 6);
		this.mHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mHorn03c.addBox(-0.8F, -2.8F, -0.2F, 1, 3, 1, 0.0F);
		this.mHorn03d = new ModelRenderer(this, 25, 6);
		this.mHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mHorn03d.addBox(-0.2F, -2.8F, -0.2F, 1, 3, 1, 0.0F);
		this.mHorn01 = new ModelRenderer(this, 41, 5);
		this.mHorn01.setRotationPoint(0.0F, -5.2F, -0.2F);
		this.mHorn01.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(mHorn01, -0.9599310885968813F, 0.0F, 0.0F);
		this.muzzleUpper02 = new ModelRenderer(this, 12, 45);
		this.muzzleUpper02.setRotationPoint(0.0F, -1.4F, -3.8F);
		this.muzzleUpper02.addBox(-2.3F, -1.8F, -3.5F, 1, 2, 4, 0.0F);
		this.setRotateAngle(muzzleUpper02, 0.06981317007977318F, 0.0F, 0.0F);
		this.mHorn01.addChild(this.mHorn02);
		this.rHorn03a.addChild(this.rHorn03c);
		this.bipedHead.addChild(this.rHorn01);
		this.rHorn03a.addChild(this.rHorn03d);
		this.bipedHead.addChild(this.rEar);
		this.bipedHead.addChild(this.snout);
		this.lHorn03a.addChild(this.lHorn03b);
		this.lHorn01.addChild(this.lHorn02);
		this.mHorn02.addChild(this.mHorn03a);
		this.bipedHead.addChild(this.muzzleUpper);
		this.bipedHead.addChild(this.muzzleLower);
		this.lHorn02.addChild(this.lHorn03a);
		this.muzzleLower.addChild(this.beard);
		this.bipedHead.addChild(this.lEar);
		this.mHorn03a.addChild(this.mHorn04);
		this.rHorn01.addChild(this.rHorn02);
		this.lHorn03a.addChild(this.lHorn03c);
		this.lHorn03a.addChild(this.lHorn04);
		this.mHorn03a.addChild(this.mHorn03b);
		this.rHorn03a.addChild(this.rHorn04);
		this.rHorn02.addChild(this.rHorn03a);
		this.rHorn03a.addChild(this.rHorn03b);
		this.bipedHead.addChild(this.lHorn01);
		this.lHorn03a.addChild(this.lHorn03d);
		this.mHorn03a.addChild(this.mHorn03c);
		this.mHorn03a.addChild(this.mHorn03d);
		this.bipedHead.addChild(this.mHorn01);
		this.bipedHead.addChild(this.muzzleUpper02);
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
