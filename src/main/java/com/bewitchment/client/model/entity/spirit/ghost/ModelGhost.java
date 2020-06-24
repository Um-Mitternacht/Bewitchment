package com.bewitchment.client.model.entity.spirit.ghost;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ghost - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelGhost extends ModelBase {
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedHead;
	public ModelRenderer bipedBody;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer skull01;
	public ModelRenderer RArmWisp;
	public ModelRenderer bodyWisp01;
	public ModelRenderer bodyWisp02;
	public ModelRenderer lArmWisp;
	public ModelRenderer skullJaw;

	public ModelGhost() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.skullJaw = new ModelRenderer(this, 19, 30);
		this.skullJaw.setRotationPoint(0.0F, -1.9F, 0.9F);
		this.skullJaw.addBox(-2.5F, -1.0F, -3.5F, 5, 2, 5, 0.0F);
		this.setRotateAngle(skullJaw, 0.17453292519943295F, 0.0F, 0.0F);
		this.bodyWisp01 = new ModelRenderer(this, 33, 51);
		this.bodyWisp01.setRotationPoint(0.0F, 7.7F, 0.0F);
		this.bodyWisp01.addBox(-4.5F, 0.0F, -2.5F, 9, 6, 5, 0.0F);
		this.setRotateAngle(bodyWisp01, 0.10471975511965977F, 0.0F, 0.0F);
		this.bodyWisp02 = new ModelRenderer(this, 0, 39);
		this.bodyWisp02.setRotationPoint(0.0F, 5.8F, 0.1F);
		this.bodyWisp02.addBox(-5.0F, 0.0F, -3.0F, 10, 9, 6, 0.0F);
		this.setRotateAngle(bodyWisp02, 0.10471975511965977F, 0.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, -0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 13, 4, 0.0F);
		this.setRotateAngle(bipedLeftArm, -1.3962634015954636F, 0.0F, -0.10000736613927509F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 13, 4, 0.0F);
		this.setRotateAngle(bipedRightArm, -1.3962634015954636F, 0.0F, 0.10000736613927509F);
		this.bipedBody = new ModelRenderer(this, 14, 16);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 8, 4, 0.0F);
		this.setRotateAngle(bipedBody, 0.20943951023931953F, 0.0F, 0.0F);
		this.skull01 = new ModelRenderer(this, 34, 0);
		this.skull01.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skull01.addBox(-3.5F, -7.5F, -3.0F, 7, 5, 6, 0.0F);
		this.lArmWisp = new ModelRenderer(this, 40, 34);
		this.lArmWisp.setRotationPoint(1.0F, 2.7F, 1.7F);
		this.lArmWisp.addBox(-1.5F, -4.5F, 0.1F, 3, 11, 4, 0.0F);
		this.RArmWisp = new ModelRenderer(this, 40, 34);
		this.RArmWisp.mirror = true;
		this.RArmWisp.setRotationPoint(-1.0F, 2.7F, 1.7F);
		this.RArmWisp.addBox(-1.5F, -4.5F, 0.1F, 3, 11, 4, 0.0F);
		this.skull01.addChild(this.skullJaw);
		this.bipedBody.addChild(this.bodyWisp01);
		this.bodyWisp01.addChild(this.bodyWisp02);
		this.bipedLeftArm.addChild(this.lArmWisp);
		this.bipedRightArm.addChild(this.RArmWisp);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bipedLeftArm.render(f5);
		this.bipedHead.render(f5);
		this.bipedRightArm.render(f5);
		this.bipedBody.render(f5);
		this.skull01.render(f5);
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
