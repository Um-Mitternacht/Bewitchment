package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * lizard - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelLizard extends ModelBase {
	public ModelRenderer body01;
	public ModelRenderer body02;
	public ModelRenderer neck;
	public ModelRenderer lArm01;
	public ModelRenderer rArm01;
	public ModelRenderer tail01;
	public ModelRenderer lLeg01;
	public ModelRenderer rLeg01;
	public ModelRenderer tail02;
	public ModelRenderer tail03;
	public ModelRenderer tail04;
	public ModelRenderer tail05;
	public ModelRenderer lLeg02;
	public ModelRenderer lFoot;
	public ModelRenderer lBToes;
	public ModelRenderer rLeg02;
	public ModelRenderer rFoot;
	public ModelRenderer rBToes;
	public ModelRenderer head;
	public ModelRenderer snout;
	public ModelRenderer lowerJaw;
	public ModelRenderer lEye;
	public ModelRenderer rEye;
	public ModelRenderer snoutb;
	public ModelRenderer lArm02;
	public ModelRenderer lForeFoot;
	public ModelRenderer lFToes01;
	public ModelRenderer rArm02;
	public ModelRenderer rForeFoot;
	public ModelRenderer rFToes01;

	public ModelLizard() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.rArm01 = new ModelRenderer(this, 0, 28);
		this.rArm01.mirror = true;
		this.rArm01.setRotationPoint(-2.4F, -0.5F, -5.5F);
		this.rArm01.addBox(-3.5F, -1.0F, -1.0F, 4, 2, 2, 0.0F);
		this.setRotateAngle(rArm01, 0.06981317007977318F, 0.3490658503988659F, -0.2792526803190927F);
		this.tail03 = new ModelRenderer(this, 46, 20);
		this.tail03.setRotationPoint(0.0F, 0.0F, 4.8F);
		this.tail03.addBox(-1.0F, -1.0F, -0.2F, 2, 2, 6, 0.0F);
		this.setRotateAngle(tail03, 0.05235987755982988F, 0.0F, 0.0F);
		this.body01 = new ModelRenderer(this, 0, 0);
		this.body01.setRotationPoint(0.0F, 21.2F, 1.0F);
		this.body01.addBox(-2.5F, -2.7F, -8.0F, 5, 4, 7, 0.0F);
		this.setRotateAngle(body01, -0.13962634015954636F, 0.0F, 0.0F);
		this.lBToes = new ModelRenderer(this, 12, 52);
		this.lBToes.setRotationPoint(0.0F, 0.4F, -0.6F);
		this.lBToes.addBox(-2.4F, -0.5F, -5.2F, 5, 0, 6, 0.0F);
		this.lLeg01 = new ModelRenderer(this, 15, 29);
		this.lLeg01.setRotationPoint(2.0F, -1.3F, 6.2F);
		this.lLeg01.addBox(-0.5F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
		this.setRotateAngle(lLeg01, 0.03490658503988659F, 0.6108652381980153F, 0.0F);
		this.rBToes = new ModelRenderer(this, 12, 52);
		this.rBToes.mirror = true;
		this.rBToes.setRotationPoint(0.0F, 0.4F, -0.6F);
		this.rBToes.addBox(-3.5F, -0.5F, -5.2F, 5, 0, 6, 0.0F);
		this.setRotateAngle(rBToes, -0.017453292519943295F, 0.0F, 0.0F);
		this.neck = new ModelRenderer(this, 29, 0);
		this.neck.setRotationPoint(0.0F, -0.7F, -7.7F);
		this.neck.addBox(-2.0F, -2.0F, -2.6F, 4, 4, 3, 0.0F);
		this.setRotateAngle(neck, 0.13962634015954636F, 0.0F, 0.0F);
		this.lEye = new ModelRenderer(this, 37, 38);
		this.lEye.setRotationPoint(1.5F, -0.2F, 0.0F);
		this.lEye.addBox(-0.5F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lEye, 0.0F, 0.12217304763960307F, -0.19198621771937624F);
		this.rEye = new ModelRenderer(this, 37, 38);
		this.rEye.mirror = true;
		this.rEye.setRotationPoint(-1.5F, -0.2F, 0.0F);
		this.rEye.addBox(-1.5F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rEye, 0.0F, -0.12217304763960307F, 0.19198621771937624F);
		this.snoutb = new ModelRenderer(this, 31, 28);
		this.snoutb.setRotationPoint(0.0F, -0.8F, -1.9F);
		this.snoutb.addBox(1.2F, -1.0F, -2.8F, 1, 2, 3, 0.0F);
		this.setRotateAngle(snoutb, 0.08726646259971647F, 0.0F, 0.0F);
		this.tail05 = new ModelRenderer(this, 51, 39);
		this.tail05.setRotationPoint(0.0F, 0.0F, 5.8F);
		this.tail05.addBox(-0.5F, -0.5F, -0.2F, 1, 1, 5, 0.0F);
		this.setRotateAngle(tail05, 0.08726646259971647F, 0.0F, 0.0F);
		this.body02 = new ModelRenderer(this, 0, 15);
		this.body02.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.body02.addBox(-2.5F, -2.6F, -0.2F, 5, 4, 8, 0.0F);
		this.setRotateAngle(body02, 0.10471975511965977F, 0.0F, 0.0F);
		this.rArm02 = new ModelRenderer(this, 0, 33);
		this.rArm02.mirror = true;
		this.rArm02.setRotationPoint(-2.7F, 0.0F, 0.0F);
		this.rArm02.addBox(-1.0F, -0.8F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(rArm02, -0.17453292519943295F, 0.0F, 0.2617993877991494F);
		this.snout = new ModelRenderer(this, 31, 22);
		this.snout.setRotationPoint(0.0F, -0.8F, -1.9F);
		this.snout.addBox(-2.3F, -1.0F, -2.8F, 4, 2, 3, 0.0F);
		this.setRotateAngle(snout, 0.08726646259971647F, 0.0F, 0.0F);
		this.lFToes01 = new ModelRenderer(this, 0, 48);
		this.lFToes01.setRotationPoint(0.0F, 0.2F, -0.6F);
		this.lFToes01.addBox(-1.5F, 0.0F, -5.0F, 3, 0, 5, 0.0F);
		this.setRotateAngle(lFToes01, 0.05235987755982988F, 0.0F, 0.0F);
		this.lowerJaw = new ModelRenderer(this, 34, 29);
		this.lowerJaw.setRotationPoint(0.0F, 1.3F, 1.1F);
		this.lowerJaw.addBox(-2.0F, -0.5F, -5.6F, 4, 1, 6, 0.0F);
		this.setRotateAngle(lowerJaw, -0.15707963267948966F, 0.0F, 0.0F);
		this.tail01 = new ModelRenderer(this, 43, 0);
		this.tail01.setRotationPoint(0.0F, -0.8F, 7.7F);
		this.tail01.addBox(-2.0F, -1.5F, -0.2F, 4, 3, 4, 0.0F);
		this.setRotateAngle(tail01, -0.08726646259971647F, 0.0F, 0.0F);
		this.rLeg02 = new ModelRenderer(this, 17, 36);
		this.rLeg02.mirror = true;
		this.rLeg02.setRotationPoint(-3.5F, -0.3F, 0.1F);
		this.rLeg02.addBox(-1.0F, -0.3F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(rLeg02, 0.3665191429188092F, 0.0F, 0.20943951023931953F);
		this.head = new ModelRenderer(this, 25, 13);
		this.head.setRotationPoint(0.0F, 0.0F, -4.2F);
		this.head.addBox(-2.5F, -2.0F, -2.5F, 5, 3, 5, 0.0F);
		this.setRotateAngle(head, 0.13962634015954636F, 0.0F, 0.0F);
		this.lFoot = new ModelRenderer(this, 17, 45);
		this.lFoot.setRotationPoint(0.2F, 4.5F, 0.1F);
		this.lFoot.addBox(-1.0F, -0.5F, -3.0F, 2, 1, 4, 0.0F);
		this.setRotateAngle(lFoot, -0.3490658503988659F, -0.6806784082777886F, 0.45378560551852565F);
		this.rFoot = new ModelRenderer(this, 17, 45);
		this.rFoot.mirror = true;
		this.rFoot.setRotationPoint(0.2F, 4.5F, -0.1F);
		this.rFoot.addBox(-1.5F, -0.5F, -3.0F, 2, 1, 4, 0.0F);
		this.setRotateAngle(rFoot, -0.3490658503988659F, 0.6806784082777886F, -0.45378560551852565F);
		this.lForeFoot = new ModelRenderer(this, 0, 42);
		this.lForeFoot.setRotationPoint(0.1F, 2.6F, 0.0F);
		this.lForeFoot.addBox(-1.0F, 0.0F, -1.9F, 2, 1, 3, 0.0F);
		this.setRotateAngle(lForeFoot, 0.17453292519943295F, 0.0F, 0.0F);
		this.rFToes01 = new ModelRenderer(this, 0, 48);
		this.rFToes01.mirror = true;
		this.rFToes01.setRotationPoint(-0.0F, 0.2F, -0.6F);
		this.rFToes01.addBox(-1.5F, 0.0F, -5.0F, 3, 0, 5, 0.0F);
		this.setRotateAngle(rFToes01, 0.05235987755982988F, 0.0F, 0.0F);
		this.lLeg02 = new ModelRenderer(this, 17, 36);
		this.lLeg02.setRotationPoint(3.5F, -0.3F, 0.1F);
		this.lLeg02.addBox(-1.0F, -0.3F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(lLeg02, 0.3665191429188092F, 0.0F, -0.20943951023931953F);
		this.rLeg01 = new ModelRenderer(this, 15, 29);
		this.rLeg01.mirror = true;
		this.rLeg01.setRotationPoint(-2.0F, -1.3F, 6.2F);
		this.rLeg01.addBox(-4.5F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
		this.setRotateAngle(rLeg01, 0.03490658503988659F, -0.6108652381980153F, 0.0F);
		this.lArm02 = new ModelRenderer(this, 0, 33);
		this.lArm02.setRotationPoint(2.7F, 0.0F, 0.0F);
		this.lArm02.addBox(-1.0F, -0.8F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(lArm02, -0.17453292519943295F, 0.0F, -0.2617993877991494F);
		this.lArm01 = new ModelRenderer(this, 0, 28);
		this.lArm01.setRotationPoint(2.4F, -0.5F, -5.5F);
		this.lArm01.addBox(-0.5F, -1.0F, -1.0F, 4, 2, 2, 0.0F);
		this.setRotateAngle(lArm01, 0.06981317007977318F, -0.3490658503988659F, 0.2792526803190927F);
		this.tail02 = new ModelRenderer(this, 45, 10);
		this.tail02.setRotationPoint(0.0F, 0.0F, 3.8F);
		this.tail02.addBox(-1.5F, -1.5F, -0.2F, 3, 3, 5, 0.0F);
		this.setRotateAngle(tail02, -0.08726646259971647F, 0.0F, 0.0F);
		this.rForeFoot = new ModelRenderer(this, 0, 42);
		this.rForeFoot.mirror = true;
		this.rForeFoot.setRotationPoint(-0.1F, 2.6F, 0.0F);
		this.rForeFoot.addBox(-1.0F, 0.0F, -1.9F, 2, 1, 3, 0.0F);
		this.setRotateAngle(rForeFoot, 0.17453292519943295F, 0.0F, 0.0F);
		this.tail04 = new ModelRenderer(this, 50, 30);
		this.tail04.setRotationPoint(0.0F, 0.0F, 5.9F);
		this.tail04.addBox(-0.5F, -1.0F, -0.2F, 1, 2, 6, 0.0F);
		this.setRotateAngle(tail04, 0.08726646259971647F, 0.0F, 0.0F);
		this.body01.addChild(this.rArm01);
		this.tail02.addChild(this.tail03);
		this.lFoot.addChild(this.lBToes);
		this.body02.addChild(this.lLeg01);
		this.rFoot.addChild(this.rBToes);
		this.body01.addChild(this.neck);
		this.head.addChild(this.lEye);
		this.head.addChild(this.rEye);
		this.head.addChild(this.snoutb);
		this.tail04.addChild(this.tail05);
		this.body01.addChild(this.body02);
		this.rArm01.addChild(this.rArm02);
		this.head.addChild(this.snout);
		this.lForeFoot.addChild(this.lFToes01);
		this.head.addChild(this.lowerJaw);
		this.body02.addChild(this.tail01);
		this.rLeg01.addChild(this.rLeg02);
		this.neck.addChild(this.head);
		this.lLeg02.addChild(this.lFoot);
		this.rLeg02.addChild(this.rFoot);
		this.lArm02.addChild(this.lForeFoot);
		this.rForeFoot.addChild(this.rFToes01);
		this.lLeg01.addChild(this.lLeg02);
		this.body02.addChild(this.rLeg01);
		this.lArm01.addChild(this.lArm02);
		this.body01.addChild(this.lArm01);
		this.tail01.addChild(this.tail02);
		this.rArm02.addChild(this.rForeFoot);
		this.tail03.addChild(this.tail04);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body01.render(f5);
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
