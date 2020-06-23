package com.bewitchment.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * statue_purifying - cybercat5555
 * Created using Tabula 7.1.0
 */
public class ModelStatueGoddess extends ModelBase {
	public ModelRenderer plinth00;
	public ModelRenderer plinth01;
	public ModelRenderer plinth02;
	public ModelRenderer BipedBody;
	public ModelRenderer BipedHead;
	public ModelRenderer BipedLeftArm;
	public ModelRenderer BipedRightArm;
	public ModelRenderer stomach;
	public ModelRenderer boobs;
	
	public ModelStatueGoddess() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.BipedRightArm = new ModelRenderer(this, 43, 38);
		this.BipedRightArm.mirror = true;
		this.BipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.BipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
		this.setRotateAngle(BipedRightArm, -0.5235987755982988F, 0.0F, 0.4363323129985824F);
		this.BipedLeftArm = new ModelRenderer(this, 43, 38);
		this.BipedLeftArm.setRotationPoint(5.0F, 2.0F, -0.0F);
		this.BipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
		this.setRotateAngle(BipedLeftArm, -0.5235987755982988F, 0.0F, -0.4363323129985824F);
		this.stomach = new ModelRenderer(this, 36, 26);
		this.stomach.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.stomach.addBox(-3.5F, 0.0F, -2.0F, 7, 4, 4, 0.0F);
		this.boobs = new ModelRenderer(this, 0, 0);
		this.boobs.setRotationPoint(0.0F, 1.5F, -0.1F);
		this.boobs.addBox(-3.5F, 0.0F, -2.0F, 7, 4, 3, 0.0F);
		this.setRotateAngle(boobs, -0.6108652381980153F, 0.0F, 0.0F);
		this.plinth02 = new ModelRenderer(this, 0, 29);
		this.plinth02.setRotationPoint(0.0F, 19.0F, 0.0F);
		this.plinth02.addBox(-5.5F, -10.0F, -3.5F, 11, 10, 7, 0.0F);
		this.BipedHead = new ModelRenderer(this, 0, 12);
		this.BipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.BipedBody = new ModelRenderer(this, 35, 16);
		this.BipedBody.setRotationPoint(0.0F, 0.2F, 0.0F);
		this.BipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.0F);
		this.plinth00 = new ModelRenderer(this, 0, 49);
		this.plinth00.setRotationPoint(0.0F, 21.0F, 0.0F);
		this.plinth00.addBox(-7.5F, 0.0F, -5.5F, 15, 3, 11, 0.0F);
		this.plinth01 = new ModelRenderer(this, 20, 0);
		this.plinth01.setRotationPoint(0.0F, 19.0F, 0.0F);
		this.plinth01.addBox(-6.5F, 0.0F, -4.5F, 13, 2, 9, 0.0F);
		this.BipedBody.addChild(this.BipedRightArm);
		this.BipedBody.addChild(this.BipedLeftArm);
		this.BipedBody.addChild(this.stomach);
		this.BipedBody.addChild(this.boobs);
		this.BipedBody.addChild(this.BipedHead);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.plinth02.render(f5);
		this.BipedBody.render(f5);
		this.plinth00.render(f5);
		this.plinth01.render(f5);
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
