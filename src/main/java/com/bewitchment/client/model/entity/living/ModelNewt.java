package com.bewitchment.client.model.entity.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * newt - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelNewt extends ModelBase {
	public ModelRenderer body00;
	public ModelRenderer body01;
	public ModelRenderer neck;
	public ModelRenderer leftArm00;
	public ModelRenderer rightArm00;
	public ModelRenderer tail00;
	public ModelRenderer leftLeg00;
	public ModelRenderer rightLeg00;
	public ModelRenderer tail01;
	public ModelRenderer tail02;
	public ModelRenderer tail03;
	public ModelRenderer tail04;
	public ModelRenderer leftLeg01;
	public ModelRenderer leftfoot;
	public ModelRenderer leftToes;
	public ModelRenderer rightLeg01;
	public ModelRenderer rightfoot;
	public ModelRenderer rightToes;
	public ModelRenderer head;
	public ModelRenderer snout;
	public ModelRenderer lowerJaw;
	public ModelRenderer leftEye;
	public ModelRenderer rightEye;
	public ModelRenderer leftArm01;
	public ModelRenderer leftHand;
	public ModelRenderer leftfingers;
	public ModelRenderer rightArm01;
	public ModelRenderer rightHand;
	public ModelRenderer rightfingers;
	
	public ModelNewt() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.snout = new ModelRenderer(this, 31, 22);
		this.snout.setRotationPoint(0, -0.4f, -1.4f);
		this.snout.addBox(-2, -1, -2, 4, 2, 2, 0);
		this.setRotateAngle(snout, 0.19f, 0, 0);
		this.leftHand = new ModelRenderer(this, 0, 42);
		this.leftHand.setRotationPoint(0.1f, 3.7f, 0);
		this.leftHand.addBox(-1, 0, -1.9f, 2, 1, 3, 0);
		this.setRotateAngle(leftHand, 0.17f, 0, 0.14f);
		this.leftArm00 = new ModelRenderer(this, 0, 28);
		this.leftArm00.setRotationPoint(2.4f, -0.7f, -5.5f);
		this.leftArm00.addBox(-0.5f, -1, -1, 4, 2, 2, 0);
		this.setRotateAngle(leftArm00, 0.07f, -0.09f, 0);
		this.leftLeg01 = new ModelRenderer(this, 17, 36);
		this.leftLeg01.setRotationPoint(3.1f, -0.7f, 0.1f);
		this.leftLeg01.addBox(-1.5f, -0.3f, -1.5f, 3, 5, 3, 0);
		this.setRotateAngle(leftLeg01, 0, 0, -0.21f);
		this.tail03 = new ModelRenderer(this, 50, 30);
		this.tail03.setRotationPoint(0, 0, 4.8f);
		this.tail03.addBox(-0.5f, -1, -0.2f, 1, 2, 5, 0);
		this.setRotateAngle(tail03, 0.09f, 0, 0);
		this.neck = new ModelRenderer(this, 29, 0);
		this.neck.setRotationPoint(0, -0.4f, -7.7f);
		this.neck.addBox(-2, -2, -2, 4, 4, 2, 0);
		this.setRotateAngle(neck, -0.05f, 0, 0);
		this.head = new ModelRenderer(this, 25, 13);
		this.head.setRotationPoint(0, 0, -2.9f);
		this.head.addBox(-2.5f, -2, -2, 5, 3, 4, 0);
		this.setRotateAngle(head, 0.14f, 0, 0);
		this.rightLeg00 = new ModelRenderer(this, 17, 29);
		this.rightLeg00.mirror = true;
		this.rightLeg00.setRotationPoint(-2, -0.8f, 5.7f);
		this.rightLeg00.addBox(-4.5f, -1.5f, -1.5f, 5, 3, 3, 0);
		this.setRotateAngle(rightLeg00, 0.04f, 0, 0);
		this.tail04 = new ModelRenderer(this, 51, 39);
		this.tail04.setRotationPoint(0, 0, 4.7f);
		this.tail04.addBox(-0.5f, -0.5f, -0.2f, 1, 1, 4, 0);
		this.setRotateAngle(tail04, 0.09f, 0, 0);
		this.body01 = new ModelRenderer(this, 0, 15);
		this.body01.setRotationPoint(0, 0, 0);
		this.body01.addBox(-2.5f, -2.6f, -0.2f, 5, 4, 8, 0);
		this.setRotateAngle(body01, 0.04f, 0, 0);
		this.tail01 = new ModelRenderer(this, 45, 10);
		this.tail01.setRotationPoint(0, 0, 3.8f);
		this.tail01.addBox(-1.5f, -1.5f, -0.2f, 3, 3, 5, 0);
		this.setRotateAngle(tail01, -0.09f, 0, 0);
		this.rightArm01 = new ModelRenderer(this, 0, 33);
		this.rightArm01.mirror = true;
		this.rightArm01.setRotationPoint(-2.7f, 0, 0);
		this.rightArm01.addBox(-1, -0.8f, -1, 2, 5, 2, 0);
		this.setRotateAngle(rightArm01, -0.17f, 0, 0.14f);
		this.leftfingers = new ModelRenderer(this, 0, 48);
		this.leftfingers.setRotationPoint(0, 0.7f, -0.6f);
		this.leftfingers.addBox(-1.5f, 0, -3.3f, 3, 0, 4, 0);
		this.setRotateAngle(leftfingers, 0.09f, 0, 0);
		this.body00 = new ModelRenderer(this, 0, 0);
		this.body00.setRotationPoint(0, 20.5f, 1);
		this.body00.addBox(-2.5f, -2.8f, -8, 5, 5, 8, 0);
		this.setRotateAngle(body00, -0.07f, 0, 0);
		this.rightfingers = new ModelRenderer(this, 0, 48);
		this.rightfingers.mirror = true;
		this.rightfingers.setRotationPoint(-0, 0.7f, -0.6f);
		this.rightfingers.addBox(-1.5f, 0, -3.3f, 3, 0, 4, 0);
		this.setRotateAngle(rightfingers, 0.09f, 0, 0);
		this.lowerJaw = new ModelRenderer(this, 34, 29);
		this.lowerJaw.setRotationPoint(0, 1.2f, 0.8f);
		this.lowerJaw.addBox(-2, -0.5f, -4, 4, 1, 4, 0);
		this.setRotateAngle(lowerJaw, -0.09f, 0, 0);
		this.tail02 = new ModelRenderer(this, 46, 20);
		this.tail02.setRotationPoint(0, 0, 4.8f);
		this.tail02.addBox(-1, -1.5f, -0.2f, 2, 3, 5, 0);
		this.setRotateAngle(tail02, 0.05f, 0, 0);
		this.rightArm00 = new ModelRenderer(this, 0, 28);
		this.rightArm00.mirror = true;
		this.rightArm00.setRotationPoint(-2.4f, -0.7f, -5.5f);
		this.rightArm00.addBox(-3.5f, -1, -1, 4, 2, 2, 0);
		this.setRotateAngle(rightArm00, 0.07f, 0.09f, 0);
		this.rightToes = new ModelRenderer(this, 12, 52);
		this.rightToes.mirror = true;
		this.rightToes.setRotationPoint(0, 0.4f, -0.6f);
		this.rightToes.addBox(-2, -0.5f, -4.5f, 4, 0, 5, 0);
		this.setRotateAngle(rightToes, 0.09f, 0, 0);
		this.leftEye = new ModelRenderer(this, 37, 36);
		this.leftEye.setRotationPoint(1.8f, -0.7f, 0.1f);
		this.leftEye.addBox(-1, -2, -1, 2, 2, 2, 0);
		this.setRotateAngle(leftEye, 0, 0.23f, 0.28f);
		this.rightEye = new ModelRenderer(this, 37, 36);
		this.rightEye.mirror = true;
		this.rightEye.setRotationPoint(-1.8f, -0.7f, 0.1f);
		this.rightEye.addBox(-1, -2, -1, 2, 2, 2, 0);
		this.setRotateAngle(rightEye, 0, -0.23f, -0.28f);
		this.rightHand = new ModelRenderer(this, 0, 42);
		this.rightHand.mirror = true;
		this.rightHand.setRotationPoint(-0.1f, 3.7f, 0);
		this.rightHand.addBox(-1, 0, -1.9f, 2, 1, 3, 0);
		this.setRotateAngle(rightHand, 0.17f, 0, -0.14f);
		this.tail00 = new ModelRenderer(this, 43, 0);
		this.tail00.setRotationPoint(0, -0.4f, 7.7f);
		this.tail00.addBox(-2, -2, -0.2f, 4, 4, 4, 0);
		this.setRotateAngle(tail00, -0.09f, 0, 0);
		this.rightLeg01 = new ModelRenderer(this, 17, 36);
		this.rightLeg01.mirror = true;
		this.rightLeg01.setRotationPoint(-3.1f, -0.7f, 0.1f);
		this.rightLeg01.addBox(-1.5f, -0.3f, -1.5f, 3, 5, 3, 0);
		this.setRotateAngle(rightLeg01, 0, 0, 0.21f);
		this.leftLeg00 = new ModelRenderer(this, 17, 29);
		this.leftLeg00.setRotationPoint(2, -0.8f, 5.7f);
		this.leftLeg00.addBox(-0.5f, -1.5f, -1.5f, 5, 3, 3, 0);
		this.setRotateAngle(leftLeg00, 0.04f, 0, 0);
		this.leftToes = new ModelRenderer(this, 12, 52);
		this.leftToes.setRotationPoint(0, 0.4f, -0.6f);
		this.leftToes.addBox(-2, -0.5f, -4.5f, 4, 0, 5, 0);
		this.setRotateAngle(leftToes, 0.09f, 0, 0);
		this.rightfoot = new ModelRenderer(this, 17, 45);
		this.rightfoot.mirror = true;
		this.rightfoot.setRotationPoint(-0.1f, 4.5f, 0.5f);
		this.rightfoot.addBox(-1.5f, -0.5f, -3, 3, 1, 4, 0);
		this.setRotateAngle(rightfoot, 0, 0, -0.21f);
		this.leftArm01 = new ModelRenderer(this, 0, 33);
		this.leftArm01.setRotationPoint(2.7f, 0, 0);
		this.leftArm01.addBox(-1, -0.8f, -1, 2, 5, 2, 0);
		this.setRotateAngle(leftArm01, -0.17f, 0, -0.14f);
		this.leftfoot = new ModelRenderer(this, 17, 45);
		this.leftfoot.setRotationPoint(0.1f, 4.5f, 0.5f);
		this.leftfoot.addBox(-1.5f, -0.5f, -3, 3, 1, 4, 0);
		this.setRotateAngle(leftfoot, 0, 0, 0.21f);
		this.head.addChild(this.snout);
		this.leftArm01.addChild(this.leftHand);
		this.body00.addChild(this.leftArm00);
		this.leftLeg00.addChild(this.leftLeg01);
		this.tail02.addChild(this.tail03);
		this.body00.addChild(this.neck);
		this.neck.addChild(this.head);
		this.body01.addChild(this.rightLeg00);
		this.tail03.addChild(this.tail04);
		this.body00.addChild(this.body01);
		this.tail00.addChild(this.tail01);
		this.rightArm00.addChild(this.rightArm01);
		this.leftHand.addChild(this.leftfingers);
		this.rightHand.addChild(this.rightfingers);
		this.head.addChild(this.lowerJaw);
		this.tail01.addChild(this.tail02);
		this.body00.addChild(this.rightArm00);
		this.rightfoot.addChild(this.rightToes);
		this.head.addChild(this.leftEye);
		this.head.addChild(this.rightEye);
		this.rightArm01.addChild(this.rightHand);
		this.body01.addChild(this.tail00);
		this.rightLeg00.addChild(this.rightLeg01);
		this.body01.addChild(this.leftLeg00);
		this.leftfoot.addChild(this.leftToes);
		this.rightLeg01.addChild(this.rightfoot);
		this.leftArm00.addChild(this.leftArm01);
		this.leftLeg01.addChild(this.leftfoot);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.body00.render(scale);
	}
	
	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer renderer, float x, float y, float z) {
		renderer.rotateAngleX = x;
		renderer.rotateAngleY = y;
		renderer.rotateAngleZ = z;
	}
}