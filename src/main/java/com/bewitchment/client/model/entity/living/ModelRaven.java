package com.bewitchment.client.model.entity.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * raven - Ingoleth, Cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelRaven extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer leftLeg00;
	public ModelRenderer rightLeg00;
	public ModelRenderer leftWing00;
	public ModelRenderer rightWing00;
	public ModelRenderer tail00;
	public ModelRenderer neck;
	public ModelRenderer leftLeg01;
	public ModelRenderer leftfoot;
	public ModelRenderer rightLeg01;
	public ModelRenderer rightfoot;
	public ModelRenderer leftWing01;
	public ModelRenderer leftWing00b;
	public ModelRenderer leftWing01b;
	public ModelRenderer leftWing02;
	public ModelRenderer leftWing02b;
	public ModelRenderer rightWing01;
	public ModelRenderer rightWing00b;
	public ModelRenderer rightWing01b;
	public ModelRenderer rightWing02;
	public ModelRenderer rightWing02b;
	public ModelRenderer middleTailfeather;
	public ModelRenderer leftTailfeather;
	public ModelRenderer rightTailfeather;
	public ModelRenderer tailPlume;
	public ModelRenderer neckfeathers00;
	public ModelRenderer neckfeathers01;
	public ModelRenderer head;
	public ModelRenderer beak00;
	public ModelRenderer beak01a;
	public ModelRenderer beak01b;

	public ModelRaven() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.leftWing01b = new ModelRenderer(this, 9, 49);
		this.leftWing01b.setRotationPoint(0, -0, 1.5f);
		this.leftWing01b.addBox(0, 0, -1.5f, 6, 0, 10, 0);
		this.rightWing00b = new ModelRenderer(this, 0, 49);
		this.rightWing00b.mirror = true;
		this.rightWing00b.setRotationPoint(0, 0, 4.1f);
		this.rightWing00b.addBox(-5, 0.01f, -1.1f, 5, 0, 7, 0);
		this.rightfoot = new ModelRenderer(this, 33, 3);
		this.rightfoot.mirror = true;
		this.rightfoot.setRotationPoint(0, 2.9f, -0.2f);
		this.rightfoot.addBox(-0.5f, 0, -2.3f, 1, 1, 3, 0);
		this.setRotateAngle(rightfoot, 0.17f, 0, 0);
		this.neck = new ModelRenderer(this, 48, 18);
		this.neck.setRotationPoint(0, -3.1f, -3.8f);
		this.neck.addBox(-2, -2.5f, -3, 4, 5, 3, 0);
		this.setRotateAngle(neck, -0.38f, 0, 0);
		this.neckfeathers00 = new ModelRenderer(this, 36, 23);
		this.neckfeathers00.setRotationPoint(0, 2.3f, -1.8f);
		this.neckfeathers00.addBox(-2, -1, -1, 4, 2, 4, 0);
		this.setRotateAngle(neckfeathers00, -0.7f, 0, 0);
		this.beak01b = new ModelRenderer(this, 42, 12);
		this.beak01b.setRotationPoint(0, 0, 0);
		this.beak01b.addBox(-1.1f, 0, -3, 1, 2, 3, 0);
		this.beak01a = new ModelRenderer(this, 42, 12);
		this.beak01a.setRotationPoint(0, 0, -3);
		this.beak01a.addBox(-1, 0, 0, 2, 2, 3, 0);
		this.setRotateAngle(beak01a, -0.26f, 0, 0);
		this.beak00 = new ModelRenderer(this, 42, 12);
		this.beak00.setRotationPoint(0, -1, -5.2f);
		this.beak00.addBox(-0.9f, 0, -3, 2, 2, 3, 0);
		this.setRotateAngle(beak00, 0.35f, 0, 0);
		this.head = new ModelRenderer(this, 42, 0);
		this.head.setRotationPoint(0, -1, -2);
		this.head.addBox(-2.5f, -2.5f, -5.6f, 5, 5, 6, 0);
		this.setRotateAngle(head, 0.54f, 0, 0);
		this.tail00 = new ModelRenderer(this, 34, 39);
		this.tail00.setRotationPoint(0, -1, 5.1f);
		this.tail00.addBox(-2.5f, -4, 0, 5, 3, 3, 0);
		this.setRotateAngle(tail00, 0.05f, 0, 0);
		this.leftWing02 = new ModelRenderer(this, 23, 24);
		this.leftWing02.setRotationPoint(5.9f, 0, 1.1f);
		this.leftWing02.addBox(0, -0.59f, -1, 5, 1, 2, 0);
		this.setRotateAngle(leftWing02, 0, -0.35f, 0);
		this.leftLeg00 = new ModelRenderer(this, 39, 1);
		this.leftLeg00.setRotationPoint(1.5f, -0.5f, 2);
		this.leftLeg00.addBox(-1, 0, -1, 2, 2, 2, 0);
		this.setRotateAngle(leftLeg00, 0.31f, 0, 0);
		this.rightWing01 = new ModelRenderer(this, 1, 11);
		this.rightWing01.mirror = true;
		this.rightWing01.setRotationPoint(-5, 0, 0);
		this.rightWing01.addBox(-6, -0.59f, 0, 6, 1, 3, 0);
		this.setRotateAngle(rightWing01, 0, 0.49f, 0);
		this.rightLeg01 = new ModelRenderer(this, 33, 8);
		this.rightLeg01.mirror = true;
		this.rightLeg01.setRotationPoint(0, 1.8f, 0);
		this.rightLeg01.addBox(-0.5f, 0, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(rightLeg01, -0.31f, 0, 0);
		this.tailPlume = new ModelRenderer(this, 19, 12);
		this.tailPlume.setRotationPoint(0, -0.4f, -0.2f);
		this.tailPlume.addBox(-2, -1.5f, 0, 4, 3, 3, 0);
		this.setRotateAngle(tailPlume, -0.19f, 0, 0);
		this.middleTailfeather = new ModelRenderer(this, 2, 21);
		this.middleTailfeather.setRotationPoint(0, -2.7f, 3);
		this.middleTailfeather.addBox(-1, -0.5f, 0, 2, 1, 7, 0);
		this.setRotateAngle(middleTailfeather, -0.09f, 0, 0);
		this.rightTailfeather = new ModelRenderer(this, 2, 21);
		this.rightTailfeather.setRotationPoint(-1.5f, -2.1f, 3);
		this.rightTailfeather.addBox(-1, -0.5f, 0, 2, 1, 7, 0);
		this.setRotateAngle(rightTailfeather, -0.09f, -0.21f, -0.79f);
		this.leftWing00 = new ModelRenderer(this, 0, 0);
		this.leftWing00.setRotationPoint(3, -3, -4.7f);
		this.leftWing00.addBox(0, -0.5f, 0, 5, 1, 4, 0);
		this.setRotateAngle(leftWing00, -0.035f, -1.05f, 0);
		this.rightWing00 = new ModelRenderer(this, 0, 0);
		this.rightWing00.mirror = true;
		this.rightWing00.setRotationPoint(-3, -3, -4.7f);
		this.rightWing00.addBox(-5, -0.5f, 0, 5, 1, 4, 0);
		this.setRotateAngle(rightWing00, -0.035f, 1.05f, 0);
		this.leftTailfeather = new ModelRenderer(this, 2, 21);
		this.leftTailfeather.setRotationPoint(1.5f, -2, 3);
		this.leftTailfeather.addBox(-1, -0.5f, 0, 2, 1, 7, 0);
		this.setRotateAngle(leftTailfeather, -0.09f, 0.21f, 0.79f);
		this.body = new ModelRenderer(this, 1, 30);
		this.body.setRotationPoint(0, 18.6f, 0);
		this.body.addBox(-3, -6, -5, 6, 6, 10, 0);
		this.setRotateAngle(body, -0.16f, 0, 0);
		this.rightLeg00 = new ModelRenderer(this, 39, 1);
		this.rightLeg00.mirror = true;
		this.rightLeg00.setRotationPoint(-1.5f, -0.5f, 2);
		this.rightLeg00.addBox(-1, 0, -1, 2, 2, 2, 0);
		this.setRotateAngle(rightLeg00, 0.31f, 0, 0);
		this.leftWing02b = new ModelRenderer(this, 23, 49);
		this.leftWing02b.setRotationPoint(0, -0, 1.5f);
		this.leftWing02b.addBox(0, 0.01f, -2.5f, 12, 0, 10, 0);
		this.leftWing01 = new ModelRenderer(this, 1, 11);
		this.leftWing01.setRotationPoint(5, 0, 0);
		this.leftWing01.addBox(0, -0.59f, 0, 6, 1, 3, 0);
		this.setRotateAngle(leftWing01, 0, -0.49f, 0);
		this.rightWing02b = new ModelRenderer(this, 23, 49);
		this.rightWing02b.mirror = true;
		this.rightWing02b.setRotationPoint(0, -0, 1.5f);
		this.rightWing02b.addBox(-12, 0.01f, -2.5f, 12, 0, 10, 0);
		this.leftLeg01 = new ModelRenderer(this, 33, 8);
		this.leftLeg01.setRotationPoint(0, 1.8f, 0);
		this.leftLeg01.addBox(-0.5f, 0, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(leftLeg01, -0.31f, 0, 0);
		this.neckfeathers01 = new ModelRenderer(this, 36, 30);
		this.neckfeathers01.setRotationPoint(0, 2.1f, 0.2f);
		this.neckfeathers01.addBox(-2.5f, -1, -1, 5, 2, 4, 0);
		this.setRotateAngle(neckfeathers01, -0.61f, 0, 0);
		this.rightWing01b = new ModelRenderer(this, 9, 49);
		this.rightWing01b.mirror = true;
		this.rightWing01b.setRotationPoint(0, -0, 1.5f);
		this.rightWing01b.addBox(-6, 0.01f, -1.5f, 6, 0, 10, 0);
		this.leftfoot = new ModelRenderer(this, 33, 3);
		this.leftfoot.setRotationPoint(0, 2.9f, -0.2f);
		this.leftfoot.addBox(-0.5f, 0, -2.3f, 1, 1, 3, 0);
		this.setRotateAngle(leftfoot, 0.17f, 0, 0);
		this.leftWing00b = new ModelRenderer(this, 0, 49);
		this.leftWing00b.setRotationPoint(0, 0, 4.1f);
		this.leftWing00b.addBox(0, 0.01f, -1.1f, 5, 0, 7, 0);
		this.rightWing02 = new ModelRenderer(this, 23, 24);
		this.rightWing02.mirror = true;
		this.rightWing02.setRotationPoint(-5.9f, 0, 1.1f);
		this.rightWing02.addBox(-5, -0.59f, -1, 5, 1, 2, 0);
		this.setRotateAngle(rightWing02, 0, 0.35f, 0);
		this.leftWing01.addChild(this.leftWing01b);
		this.rightWing00.addChild(this.rightWing00b);
		this.rightLeg01.addChild(this.rightfoot);
		this.body.addChild(this.neck);
		this.neck.addChild(this.neckfeathers00);
		this.beak00.addChild(this.beak01b);
		this.beak00.addChild(this.beak01a);
		this.head.addChild(this.beak00);
		this.neck.addChild(this.head);
		this.body.addChild(this.tail00);
		this.leftWing01.addChild(this.leftWing02);
		this.body.addChild(this.leftLeg00);
		this.rightWing00.addChild(this.rightWing01);
		this.rightLeg00.addChild(this.rightLeg01);
		this.tail00.addChild(this.tailPlume);
		this.tail00.addChild(this.middleTailfeather);
		this.tail00.addChild(this.rightTailfeather);
		this.body.addChild(this.leftWing00);
		this.body.addChild(this.rightWing00);
		this.tail00.addChild(this.leftTailfeather);
		this.body.addChild(this.rightLeg00);
		this.leftWing02.addChild(this.leftWing02b);
		this.leftWing00.addChild(this.leftWing01);
		this.rightWing02.addChild(this.rightWing02b);
		this.leftLeg00.addChild(this.leftLeg01);
		this.neck.addChild(this.neckfeathers01);
		this.rightWing01.addChild(this.rightWing01b);
		this.leftLeg01.addChild(this.leftfoot);
		this.leftWing00.addChild(this.leftWing00b);
		this.rightWing01.addChild(this.rightWing02);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.body.render(scale);
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