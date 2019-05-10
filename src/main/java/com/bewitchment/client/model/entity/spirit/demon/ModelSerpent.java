package com.bewitchment.client.model.entity.spirit.demon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * serpent - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelSerpent extends ModelBase {
	public ModelRenderer neck00a;
	public ModelRenderer neck00b;
	public ModelRenderer neck01;
	public ModelRenderer bodyMain;
	public ModelRenderer neck02;
	public ModelRenderer neck03;
	public ModelRenderer neck04;
	public ModelRenderer head;
	public ModelRenderer tongue;
	public ModelRenderer upperJaw;
	public ModelRenderer leftUpperJaw;
	public ModelRenderer rightUpperJaw;
	public ModelRenderer lowerJaw;
	public ModelRenderer leftHornTop00a;
	public ModelRenderer rightHornTop00a;
	public ModelRenderer leftHorn00a;
	public ModelRenderer rightHorn00a;
	public ModelRenderer leftfang;
	public ModelRenderer rightfang;
	public ModelRenderer leftHornTop00b;
	public ModelRenderer leftHornTop01;
	public ModelRenderer leftHornTop02;
	public ModelRenderer rightHornTop00b;
	public ModelRenderer rightHornTop01;
	public ModelRenderer rightHornTop02;
	public ModelRenderer leftHorn00b;
	public ModelRenderer leftHorn01;
	public ModelRenderer leftHorn02;
	public ModelRenderer leftHorn03;
	public ModelRenderer rightHorn00b;
	public ModelRenderer rightHorn01;
	public ModelRenderer rightHorn02;
	public ModelRenderer rightHorn03;
	public ModelRenderer tail00a;
	public ModelRenderer leftWing00;
	public ModelRenderer rightWing00;
	public ModelRenderer tail00b;
	public ModelRenderer tail01;
	public ModelRenderer tail02a;
	public ModelRenderer tail02b;
	public ModelRenderer tail03;
	public ModelRenderer tail04;
	public ModelRenderer leftWing0a;
	public ModelRenderer leftWing02;
	public ModelRenderer leftWing03;
	public ModelRenderer rightWing01;
	public ModelRenderer rightWing02;
	public ModelRenderer rightWing03;

	public ModelSerpent() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.tail00a = new ModelRenderer(this, 27, 22);
		this.tail00a.setRotationPoint(0, 0, 6.7f);
		this.tail00a.addBox(-0.7f, -1.5f, 0.6f, 3, 3, 7, 0);
		this.rightHorn02 = new ModelRenderer(this, 52, 6);
		this.rightHorn02.mirror = true;
		this.rightHorn02.setRotationPoint(0, -1.8f, 0.1f);
		this.rightHorn02.addBox(-0.5f, -0.7f, -0.5f, 1, 1, 1, 0);
		this.setRotateAngle(rightHorn02, -0.35f, 0, 0.17f);
		this.rightWing03 = new ModelRenderer(this, 28, 9);
		this.rightWing03.mirror = true;
		this.rightWing03.setRotationPoint(0, -3.4f, -0.1f);
		this.rightWing03.addBox(-0.5f, -4, -0.5f, 1, 4, 1, 0);
		this.setRotateAngle(rightWing03, -1f, 0, 0);
		this.rightUpperJaw = new ModelRenderer(this, 17, 0);
		this.rightUpperJaw.mirror = true;
		this.rightUpperJaw.setRotationPoint(-1.9f, -0.7f, -3);
		this.rightUpperJaw.addBox(-0.5f, -1, -3.87f, 2, 2, 3, 0);
		this.setRotateAngle(rightUpperJaw, 0, -0.2f, 0);
		this.lowerJaw = new ModelRenderer(this, 0, 8);
		this.lowerJaw.setRotationPoint(0, 0.5f, -2);
		this.lowerJaw.addBox(-1.5f, -0.5f, -5, 3, 1, 5, 0);
		this.leftHornTop01 = new ModelRenderer(this, 47, 0);
		this.leftHornTop01.setRotationPoint(0, -2, 0);
		this.leftHornTop01.addBox(-0.5f, -1.8f, -0.5f, 1, 2, 1, 0);
		this.setRotateAngle(leftHornTop01, -0.26f, 0, 0);
		this.tail03 = new ModelRenderer(this, 41, 40);
		this.tail03.setRotationPoint(0, 0.5f, 5.8f);
		this.tail03.addBox(-1, -1, -1, 2, 2, 9, 0);
		this.leftUpperJaw = new ModelRenderer(this, 17, 0);
		this.leftUpperJaw.setRotationPoint(0.9f, -0.7f, -3);
		this.leftUpperJaw.addBox(-0.5f, -1, -3.5f, 2, 2, 3, 0);
		this.setRotateAngle(leftUpperJaw, 0, 0.2f, 0);
		this.rightWing02 = new ModelRenderer(this, 28, 9);
		this.rightWing02.mirror = true;
		this.rightWing02.setRotationPoint(0, -2.7f, 0);
		this.rightWing02.addBox(-0.5f, -3.8f, -0.5f, 1, 4, 1, 0);
		this.setRotateAngle(rightWing02, -0.61f, 0, 0);
		this.leftWing00 = new ModelRenderer(this, 20, 9);
		this.leftWing00.setRotationPoint(1.2f, -1, 1.3f);
		this.leftWing00.addBox(-0.5f, -3, -1, 1, 3, 2, 0);
		this.setRotateAngle(leftWing00, -0.7f, 0, 0.44f);
		this.leftHorn03 = new ModelRenderer(this, 57, 6);
		this.leftHorn03.mirror = true;
		this.leftHorn03.setRotationPoint(0, -0.6f, 0);
		this.leftHorn03.addBox(-0.5f, -1.8f, -0.5f, 1, 2, 1, 0);
		this.setRotateAngle(leftHorn03, -0.44f, 0, 0);
		this.neck00a = new ModelRenderer(this, 27, 33);
		this.neck00a.setRotationPoint(0, 22.5f, -3.9f);
		this.neck00a.addBox(-0.7f, -1.49f, -3.5f, 3, 3, 5, 0);
		this.neck01 = new ModelRenderer(this, 0, 56);
		this.neck01.setRotationPoint(0, 0, -3.6f);
		this.neck01.addBox(-2, -1.5f, -3.3f, 4, 3, 4, 0);
		this.setRotateAngle(neck01, -0.35f, 0, 0);
		this.neck04 = new ModelRenderer(this, 0, 56);
		this.neck04.setRotationPoint(0, 0.1f, -3.1f);
		this.neck04.addBox(-2, -1.5f, -3.3f, 4, 3, 4, 0);
		this.setRotateAngle(neck04, 0.52f, 0, 0);
		this.leftHorn01 = new ModelRenderer(this, 47, 6);
		this.leftHorn01.mirror = true;
		this.leftHorn01.setRotationPoint(0, -1.7f, 0);
		this.leftHorn01.addBox(-0.5f, -1.8f, -0.5f, 1, 2, 1, 0);
		this.setRotateAngle(leftHorn01, -0.35f, 0, -0.35f);
		this.rightHornTop02 = new ModelRenderer(this, 52, 0);
		this.rightHornTop02.mirror = true;
		this.rightHornTop02.setRotationPoint(0, -1.8f, 0);
		this.rightHornTop02.addBox(-0.5f, -2.8f, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(rightHornTop02, -0.26f, 0, 0);
		this.rightHorn01 = new ModelRenderer(this, 47, 6);
		this.rightHorn01.setRotationPoint(0, -1.7f, 0);
		this.rightHorn01.addBox(-0.5f, -1.8f, -0.5f, 1, 2, 1, 0);
		this.setRotateAngle(rightHorn01, -0.35f, 0, 0.35f);
		this.leftWing02 = new ModelRenderer(this, 28, 9);
		this.leftWing02.setRotationPoint(0, -2.7f, 0);
		this.leftWing02.addBox(-0.5f, -3.8f, -0.5f, 1, 4, 1, 0);
		this.setRotateAngle(leftWing02, -0.61f, 0, 0);
		this.upperJaw = new ModelRenderer(this, 0, 0);
		this.upperJaw.setRotationPoint(0, -1, -2.8f);
		this.upperJaw.addBox(-1.5f, -1.1f, -4.4f, 3, 2, 5, 0);
		this.setRotateAngle(upperJaw, 0.09f, 0, 0);
		this.rightHorn00b = new ModelRenderer(this, 42, 6);
		this.rightHorn00b.setRotationPoint(0, 0, 0);
		this.rightHorn00b.addBox(-0.6f, -2, -0.3f, 1, 2, 1, 0);
		this.rightfang = new ModelRenderer(this, 0, 0);
		this.rightfang.setRotationPoint(-1.4f, 0.5f, -3.6f);
		this.rightfang.addBox(0, -0.8f, -0.5f, 0, 2, 1, 0);
		this.leftfang = new ModelRenderer(this, 0, 0);
		this.leftfang.setRotationPoint(1.4f, 0.5f, -3.6f);
		this.leftfang.addBox(0, -0.8f, -0.5f, 0, 2, 1, 0);
		this.head = new ModelRenderer(this, 0, 22);
		this.head.setRotationPoint(0, -0.4f, -1.3f);
		this.head.addBox(-2.5f, -3.4f, -4, 5, 4, 4, 0);
		this.setRotateAngle(head, 1.05f, 0, 0);
		this.rightHornTop00a = new ModelRenderer(this, 42, 0);
		this.rightHornTop00a.mirror = true;
		this.rightHornTop00a.setRotationPoint(-1.3f, -2.7f, -1.2f);
		this.rightHornTop00a.addBox(-0.3f, -2, -0.7f, 1, 2, 1, 0);
		this.setRotateAngle(rightHornTop00a, -0.87f, -0.17f, 0);
		this.rightHorn00a = new ModelRenderer(this, 42, 6);
		this.rightHorn00a.setRotationPoint(-1.9f, -2, -1);
		this.rightHorn00a.addBox(-0.6f, -2, -0.7f, 1, 2, 1, 0);
		this.setRotateAngle(rightHorn00a, -1.4f, -0.61f, 0);
		this.bodyMain = new ModelRenderer(this, 0, 31);
		this.bodyMain.setRotationPoint(0, 0, 1.2f);
		this.bodyMain.addBox(-2.5f, -1.8f, -0.4f, 5, 4, 8, 0);
		this.tongue = new ModelRenderer(this, 25, 0);
		this.tongue.setRotationPoint(0, 0.3f, -1.9f);
		this.tongue.addBox(-1.5f, 0, -5, 3, 0, 7, 0);
		this.rightHornTop01 = new ModelRenderer(this, 47, 0);
		this.rightHornTop01.mirror = true;
		this.rightHornTop01.setRotationPoint(0, -2, 0);
		this.rightHornTop01.addBox(-0.5f, -1.8f, -0.5f, 1, 2, 1, 0);
		this.setRotateAngle(rightHornTop01, -0.26f, 0, 0);
		this.leftHorn00b = new ModelRenderer(this, 42, 6);
		this.leftHorn00b.mirror = true;
		this.leftHorn00b.setRotationPoint(0, 0, 0);
		this.leftHorn00b.addBox(-0.4f, -2, -0.3f, 1, 2, 1, 0);
		this.leftWing0a = new ModelRenderer(this, 28, 9);
		this.leftWing0a.setRotationPoint(0, -2.6f, -0.1f);
		this.leftWing0a.addBox(-0.49f, -3, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(leftWing0a, 0.52f, 0, 0);
		this.rightWing00 = new ModelRenderer(this, 20, 9);
		this.rightWing00.mirror = true;
		this.rightWing00.setRotationPoint(-1.2f, -1, 1.3f);
		this.rightWing00.addBox(-0.5f, -3, -1, 1, 3, 2, 0);
		this.setRotateAngle(rightWing00, -0.7f, 0, -0.44f);
		this.leftHorn00a = new ModelRenderer(this, 42, 6);
		this.leftHorn00a.mirror = true;
		this.leftHorn00a.setRotationPoint(1.9f, -2, -1);
		this.leftHorn00a.addBox(-0.4f, -2, -0.7f, 1, 2, 1, 0);
		this.setRotateAngle(leftHorn00a, -1.4f, 0.61f, 0);
		this.tail02b = new ModelRenderer(this, 22, 55);
		this.tail02b.setRotationPoint(0, 0.5f, 0);
		this.tail02b.addBox(-1.5f, 0, -1, 3, 1, 7, 0);
		this.rightHornTop00b = new ModelRenderer(this, 42, 0);
		this.rightHornTop00b.mirror = true;
		this.rightHornTop00b.setRotationPoint(0, 0, 0);
		this.rightHornTop00b.addBox(-0.7f, -2, -0.7f, 1, 2, 1, 0);
		this.leftHorn02 = new ModelRenderer(this, 52, 6);
		this.leftHorn02.mirror = true;
		this.leftHorn02.setRotationPoint(0, -1.8f, 0.1f);
		this.leftHorn02.addBox(-0.5f, -0.7f, -0.5f, 1, 1, 1, 0);
		this.setRotateAngle(leftHorn02, -0.35f, 0, -0.17f);
		this.rightHorn03 = new ModelRenderer(this, 57, 6);
		this.rightHorn03.setRotationPoint(0, -0.6f, 0);
		this.rightHorn03.addBox(-0.5f, -1.8f, -0.5f, 1, 2, 1, 0);
		this.setRotateAngle(rightHorn03, -0.44f, 0, 0);
		this.tail01 = new ModelRenderer(this, 0, 45);
		this.tail01.setRotationPoint(0, 0, 7.4f);
		this.tail01.addBox(-2, -1.49f, -0.5f, 4, 3, 7, 0);
		this.rightWing01 = new ModelRenderer(this, 28, 9);
		this.rightWing01.mirror = true;
		this.rightWing01.setRotationPoint(0, -2.6f, -0.1f);
		this.rightWing01.addBox(-0.51f, -3, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(rightWing01, 0.52f, 0, 0);
		this.neck00b = new ModelRenderer(this, 43, 28);
		this.neck00b.setRotationPoint(0, 0, 0);
		this.neck00b.addBox(-2.3f, -1.49f, -3.5f, 2, 3, 5, 0);
		this.leftHornTop00a = new ModelRenderer(this, 42, 0);
		this.leftHornTop00a.setRotationPoint(1.3f, -2.7f, -1.2f);
		this.leftHornTop00a.addBox(-0.3f, -2, -0.7f, 1, 2, 1, 0);
		this.setRotateAngle(leftHornTop00a, -0.87f, 0.17f, 0);
		this.tail02a = new ModelRenderer(this, 23, 45);
		this.tail02a.setRotationPoint(0, 0, 5.9f);
		this.tail02a.addBox(-1.5f, -1.1f, -1, 3, 2, 7, 0);
		this.tail04 = new ModelRenderer(this, 43, 54);
		this.tail04.setRotationPoint(0, 0.5f, 7.8f);
		this.tail04.addBox(-0.5f, -0.5f, -0.6f, 1, 1, 8, 0);
		this.neck03 = new ModelRenderer(this, 0, 56);
		this.neck03.setRotationPoint(0, -0.4f, -2.7f);
		this.neck03.addBox(-2, -1.5f, -3.3f, 4, 3, 4, 0);
		this.setRotateAngle(neck03, -0.7f, 0, 0);
		this.leftHornTop00b = new ModelRenderer(this, 42, 0);
		this.leftHornTop00b.setRotationPoint(0, 0, 0);
		this.leftHornTop00b.addBox(-0.7f, -2, -0.7f, 1, 2, 1, 0);
		this.tail00b = new ModelRenderer(this, 46, 18);
		this.tail00b.setRotationPoint(0, 0, 0);
		this.tail00b.addBox(-2.3f, -1.5f, 0.5f, 2, 3, 7, 0);
		this.neck02 = new ModelRenderer(this, 0, 56);
		this.neck02.setRotationPoint(0, 0, -3.6f);
		this.neck02.addBox(-2, -1.8f, -3, 4, 3, 4, 0);
		this.setRotateAngle(neck02, -0.52f, 0, 0);
		this.leftWing03 = new ModelRenderer(this, 28, 9);
		this.leftWing03.setRotationPoint(0, -3.4f, -0.1f);
		this.leftWing03.addBox(-0.5f, -4, -0.5f, 1, 4, 1, 0);
		this.setRotateAngle(leftWing03, -1f, 0, 0);
		this.leftHornTop02 = new ModelRenderer(this, 52, 0);
		this.leftHornTop02.setRotationPoint(0, -1.8f, 0);
		this.leftHornTop02.addBox(-0.5f, -2.7f, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(leftHornTop02, -0.26f, 0, 0);
		this.bodyMain.addChild(this.tail00a);
		this.rightHorn01.addChild(this.rightHorn02);
		this.rightWing02.addChild(this.rightWing03);
		this.head.addChild(this.rightUpperJaw);
		this.head.addChild(this.lowerJaw);
		this.leftHornTop00a.addChild(this.leftHornTop01);
		this.tail02a.addChild(this.tail03);
		this.head.addChild(this.leftUpperJaw);
		this.rightWing01.addChild(this.rightWing02);
		this.bodyMain.addChild(this.leftWing00);
		this.leftHorn02.addChild(this.leftHorn03);
		this.neck00a.addChild(this.neck01);
		this.neck03.addChild(this.neck04);
		this.leftHorn00a.addChild(this.leftHorn01);
		this.rightHornTop01.addChild(this.rightHornTop02);
		this.rightHorn00a.addChild(this.rightHorn01);
		this.leftWing0a.addChild(this.leftWing02);
		this.head.addChild(this.upperJaw);
		this.rightHorn00a.addChild(this.rightHorn00b);
		this.upperJaw.addChild(this.rightfang);
		this.upperJaw.addChild(this.leftfang);
		this.neck04.addChild(this.head);
		this.head.addChild(this.rightHornTop00a);
		this.head.addChild(this.rightHorn00a);
		this.neck00a.addChild(this.bodyMain);
		this.head.addChild(this.tongue);
		this.rightHornTop00a.addChild(this.rightHornTop01);
		this.leftHorn00a.addChild(this.leftHorn00b);
		this.leftWing00.addChild(this.leftWing0a);
		this.bodyMain.addChild(this.rightWing00);
		this.head.addChild(this.leftHorn00a);
		this.tail02a.addChild(this.tail02b);
		this.rightHornTop00a.addChild(this.rightHornTop00b);
		this.leftHorn01.addChild(this.leftHorn02);
		this.rightHorn02.addChild(this.rightHorn03);
		this.tail00a.addChild(this.tail01);
		this.rightWing00.addChild(this.rightWing01);
		this.neck00a.addChild(this.neck00b);
		this.head.addChild(this.leftHornTop00a);
		this.tail01.addChild(this.tail02a);
		this.tail03.addChild(this.tail04);
		this.neck02.addChild(this.neck03);
		this.leftHornTop00a.addChild(this.leftHornTop00b);
		this.tail00a.addChild(this.tail00b);
		this.neck01.addChild(this.neck02);
		this.leftWing02.addChild(this.leftWing03);
		this.leftHornTop01.addChild(this.leftHornTop02);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.neck00a.render(scale);
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