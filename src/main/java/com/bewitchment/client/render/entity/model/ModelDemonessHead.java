package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelDemonessHead - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelDemonessHead extends ModelBase {
	public ModelRenderer bipedHead;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer hair01;
	public ModelRenderer hair02;
	public ModelRenderer lHorn02a;
	public ModelRenderer lHorn02b;
	public ModelRenderer lHorn02c;
	public ModelRenderer lHorn03d;
	public ModelRenderer lHorn03a;
	public ModelRenderer lHorn03b;
	public ModelRenderer lHorn03c;
	public ModelRenderer lHorn03d_1;
	public ModelRenderer lHorn04;
	public ModelRenderer lHorn05;
	public ModelRenderer rHorn02a;
	public ModelRenderer rHorn02b;
	public ModelRenderer rHorn02c;
	public ModelRenderer rHorn03d;
	public ModelRenderer rHorn03a;
	public ModelRenderer rHorn03b;
	public ModelRenderer rHorn03c;
	public ModelRenderer rHorn03d_1;
	public ModelRenderer rHorn04;
	public ModelRenderer rHorn05;

	public ModelDemonessHead() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.hair01 = new ModelRenderer(this, 25, 0);
		this.hair01.setRotationPoint(0.0F, -4.4F, 3.2F);
		this.hair01.addBox(-4.0F, 0.0F, -0.1F, 8, 8, 1, 0.0F);
		this.setRotateAngle(hair01, 0.20943951023931953F, 0.0F, 0.0F);
		this.rHorn05 = new ModelRenderer(this, 43, 0);
		this.rHorn05.mirror = true;
		this.rHorn05.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.rHorn05.addBox(-0.5F, -2.1F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn05, 0.05235987755982988F, 0.0F, 0.13962634015954636F);
		this.lHorn02b = new ModelRenderer(this, 0, 4);
		this.lHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.lHorn03b = new ModelRenderer(this, 0, 4);
		this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03b.addBox(-0.3F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.lHorn03c = new ModelRenderer(this, 0, 4);
		this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03c.addBox(-0.7F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.lHorn03d_1 = new ModelRenderer(this, 0, 4);
		this.lHorn03d_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d_1.addBox(-0.3F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 1);
		this.bipedHead.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rHorn03b = new ModelRenderer(this, 0, 4);
		this.rHorn03b.mirror = true;
		this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03b.addBox(-0.3F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.rHorn02b = new ModelRenderer(this, 0, 4);
		this.rHorn02b.mirror = true;
		this.rHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.rHorn03c = new ModelRenderer(this, 0, 4);
		this.rHorn03c.mirror = true;
		this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03c.addBox(-0.7F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.lHorn03d = new ModelRenderer(this, 0, 4);
		this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn02a = new ModelRenderer(this, 0, 4);
		this.rHorn02a.mirror = true;
		this.rHorn02a.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn02a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn02a, -0.17453292519943295F, 0.0F, -0.05235987755982988F);
		this.lHorn03a = new ModelRenderer(this, 0, 4);
		this.lHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.lHorn03a.addBox(-0.7F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn03a, -0.10471975511965977F, 0.0F, 0.10471975511965977F);
		this.rHorn04 = new ModelRenderer(this, 4, 4);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn04, 0.05235987755982988F, 0.0F, -0.13962634015954636F);
		this.hair02 = new ModelRenderer(this, 25, 0);
		this.hair02.mirror = true;
		this.hair02.setRotationPoint(0.0F, -6.9F, 3.2F);
		this.hair02.addBox(-4.0F, 0.0F, -0.1F, 8, 8, 1, 0.0F);
		this.setRotateAngle(hair02, 0.3490658503988659F, 0.0F, 0.0F);
		this.lHorn02c = new ModelRenderer(this, 0, 4);
		this.lHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn01 = new ModelRenderer(this, 0, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-2.9F, -7.4F, -0.5F);
		this.rHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn01, -0.17453292519943295F, -0.13962634015954636F, -0.13962634015954636F);
		this.rHorn03d_1 = new ModelRenderer(this, 0, 4);
		this.rHorn03d_1.mirror = true;
		this.rHorn03d_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d_1.addBox(-0.3F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.lHorn02a = new ModelRenderer(this, 0, 4);
		this.lHorn02a.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn02a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn02a, -0.17453292519943295F, 0.0F, 0.05235987755982988F);
		this.lHorn05 = new ModelRenderer(this, 43, 0);
		this.lHorn05.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.lHorn05.addBox(-0.5F, -2.1F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn05, 0.05235987755982988F, 0.0F, -0.13962634015954636F);
		this.rHorn03d = new ModelRenderer(this, 0, 4);
		this.rHorn03d.mirror = true;
		this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn03a = new ModelRenderer(this, 0, 4);
		this.rHorn03a.mirror = true;
		this.rHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.rHorn03a.addBox(-0.7F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn03a, -0.10471975511965977F, 0.0F, -0.10471975511965977F);
		this.lHorn01 = new ModelRenderer(this, 0, 0);
		this.lHorn01.setRotationPoint(2.9F, -7.4F, -0.5F);
		this.lHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn01, -0.17453292519943295F, 0.13962634015954636F, 0.13962634015954636F);
		this.lHorn04 = new ModelRenderer(this, 4, 4);
		this.lHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn04, 0.05235987755982988F, 0.0F, 0.13962634015954636F);
		this.rHorn02c = new ModelRenderer(this, 0, 4);
		this.rHorn02c.mirror = true;
		this.rHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.bipedHead.addChild(this.hair01);
		this.rHorn04.addChild(this.rHorn05);
		this.lHorn02a.addChild(this.lHorn02b);
		this.lHorn03a.addChild(this.lHorn03b);
		this.lHorn03a.addChild(this.lHorn03c);
		this.lHorn03a.addChild(this.lHorn03d_1);
		this.rHorn03a.addChild(this.rHorn03b);
		this.rHorn02a.addChild(this.rHorn02b);
		this.rHorn03a.addChild(this.rHorn03c);
		this.lHorn02a.addChild(this.lHorn03d);
		this.rHorn01.addChild(this.rHorn02a);
		this.lHorn02a.addChild(this.lHorn03a);
		this.rHorn03a.addChild(this.rHorn04);
		this.bipedHead.addChild(this.hair02);
		this.lHorn02a.addChild(this.lHorn02c);
		this.bipedHead.addChild(this.rHorn01);
		this.rHorn03a.addChild(this.rHorn03d_1);
		this.lHorn01.addChild(this.lHorn02a);
		this.lHorn04.addChild(this.lHorn05);
		this.rHorn02a.addChild(this.rHorn03d);
		this.rHorn02a.addChild(this.rHorn03a);
		this.bipedHead.addChild(this.lHorn01);
		this.lHorn03a.addChild(this.lHorn04);
		this.rHorn02a.addChild(this.rHorn02c);
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
