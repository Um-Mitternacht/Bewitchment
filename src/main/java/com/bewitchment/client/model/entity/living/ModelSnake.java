package com.bewitchment.client.model.entity.living;

import com.bewitchment.common.entity.living.EntitySnake;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * snake - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelSnake extends ModelBase {
	public final ModelRenderer body;
	public final ModelRenderer tail00;
	public final ModelRenderer neck00;
	public final ModelRenderer tail00b;
	public final ModelRenderer tail01;
	public final ModelRenderer tail02;
	public final ModelRenderer tail02b;
	public final ModelRenderer tail03;
	public final ModelRenderer tail04;
	public final ModelRenderer neck00b;
	public final ModelRenderer neck01;
	public final ModelRenderer head;
	public final ModelRenderer tongue;
	public final ModelRenderer upperJaw;
	public final ModelRenderer leftUpperJaw;
	public final ModelRenderer rightUpperJaw;
	public final ModelRenderer lowerJaw;
	public final ModelRenderer eyes;
	public final ModelRenderer leftfang;
	public final ModelRenderer rightfang;
	
	public ModelSnake() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.tail00b = new ModelRenderer(this, 46, 18);
		this.tail00b.setRotationPoint(0, 0, 0);
		this.tail00b.addBox(-2.3f, -1.5f, 0.5f, 2, 3, 7, 0);
		this.neck00b = new ModelRenderer(this, 43, 28);
		this.neck00b.setRotationPoint(0, 0, 0);
		this.neck00b.addBox(-2.3f, -1.49f, -8, 2, 3, 8, 0);
		this.body = new ModelRenderer(this, 0, 31);
		this.body.setRotationPoint(0, 22.5f, -3.4f);
		this.body.addBox(-2.5f, -1.8f, -0.4f, 5, 4, 8, 0);
		this.tail02 = new ModelRenderer(this, 23, 45);
		this.tail02.setRotationPoint(0, 0, 5.9f);
		this.tail02.addBox(-1.5f, -1.1f, -1, 3, 2, 7, 0);
		this.upperJaw = new ModelRenderer(this, 0, 0);
		this.upperJaw.setRotationPoint(0, -1.3f, -2.8f);
		this.upperJaw.addBox(-1.5f, -1.1f, -4.4f, 3, 2, 5, 0);
		this.setRotateAngle(upperJaw, 0.09f, 0, 0);
		this.tail04 = new ModelRenderer(this, 43, 54);
		this.tail04.setRotationPoint(0, 0.5f, 7.8f);
		this.tail04.addBox(-0.5f, -0.5f, -0.6f, 1, 1, 8, 0);
		this.eyes = new ModelRenderer(this, 40, 0);
		this.eyes.setRotationPoint(0, -2, -3.9f);
		this.eyes.addBox(-2, -0.5f, -0.5f, 4, 1, 1, 0);
		this.leftUpperJaw = new ModelRenderer(this, 17, 0);
		this.leftUpperJaw.setRotationPoint(0.9f, -0.9f, -3);
		this.leftUpperJaw.addBox(-0.5f, -1, -3.7f, 2, 2, 5, 0);
		this.setRotateAngle(leftUpperJaw, 0, 0.19f, 0);
		this.rightUpperJaw = new ModelRenderer(this, 17, 0);
		this.rightUpperJaw.mirror = true;
		this.rightUpperJaw.setRotationPoint(-1.9f, -0.9f, -3);
		this.rightUpperJaw.addBox(-0.5f, -1, -4.07f, 2, 2, 5, 0);
		this.setRotateAngle(rightUpperJaw, 0, -0.19f, 0);
		this.tail02b = new ModelRenderer(this, 22, 55);
		this.tail02b.setRotationPoint(0, 0.5f, 0);
		this.tail02b.addBox(-1.5f, 0, -1, 3, 1, 7, 0);
		this.tail00 = new ModelRenderer(this, 27, 22);
		this.tail00.setRotationPoint(0, 0, 6.7f);
		this.tail00.addBox(-0.7f, -1.5f, 0.6f, 3, 3, 7, 0);
		this.neck01 = new ModelRenderer(this, 0, 45);
		this.neck01.setRotationPoint(0, 0, -8.6f);
		this.neck01.addBox(-2, -1.5f, -6, 4, 3, 7, 0);
		this.tongue = new ModelRenderer(this, 25, 0);
		this.tongue.setRotationPoint(0, 0.4f, -2);
		this.tongue.addBox(-1.5f, 0, -5, 3, 0, 7, 0);
		this.neck00 = new ModelRenderer(this, 27, 33);
		this.neck00.setRotationPoint(0, 0, -0.2f);
		this.neck00.addBox(-0.7f, -1.49f, -8, 3, 3, 8, 0);
		this.tail01 = new ModelRenderer(this, 0, 45);
		this.tail01.setRotationPoint(0, 0, 7.4f);
		this.tail01.addBox(-2, -1.49f, -0.5f, 4, 3, 7, 0);
		this.rightfang = new ModelRenderer(this, 0, 0);
		this.rightfang.setRotationPoint(-1.4f, -0.5f, -3.6f);
		this.rightfang.addBox(0, 0, -0.5f, 0, 2, 1, 0);
		this.tail03 = new ModelRenderer(this, 41, 40);
		this.tail03.setRotationPoint(0, 0.5f, 5.8f);
		this.tail03.addBox(-1, -1, -1, 2, 2, 9, 0);
		this.head = new ModelRenderer(this, 0, 22);
		this.head.setRotationPoint(0, 0.8f, -5.9f);
		this.head.addBox(-2.5f, -2.4f, -3, 5, 3, 3, 0);
		this.lowerJaw = new ModelRenderer(this, 0, 8);
		this.lowerJaw.setRotationPoint(0, 0.1f, -2);
		this.lowerJaw.addBox(-1.5f, -0.5f, -5, 3, 1, 5, 0);
		this.leftfang = new ModelRenderer(this, 0, 0);
		this.leftfang.setRotationPoint(1.4f, -0.5f, -3.6f);
		this.leftfang.addBox(0, 0, -0.5f, 0, 2, 1, 0);
		this.tail00.addChild(this.tail00b);
		this.neck00.addChild(this.neck00b);
		this.tail01.addChild(this.tail02);
		this.head.addChild(this.upperJaw);
		this.tail03.addChild(this.tail04);
		this.head.addChild(this.eyes);
		this.head.addChild(this.leftUpperJaw);
		this.head.addChild(this.rightUpperJaw);
		this.tail02.addChild(this.tail02b);
		this.body.addChild(this.tail00);
		this.neck00.addChild(this.neck01);
		this.head.addChild(this.tongue);
		this.body.addChild(this.neck00);
		this.tail00.addChild(this.tail01);
		this.upperJaw.addChild(this.rightfang);
		this.tail02.addChild(this.tail03);
		this.neck01.addChild(this.head);
		this.head.addChild(this.lowerJaw);
		this.upperJaw.addChild(this.leftfang);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.body.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		float swingModifier = 0.9f;
		if (entity instanceof EntityLivingBase) {
			this.body.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * 0.6F * swingModifier * limbSwingAmount;
			this.tail00.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * 0.6F * swingModifier * limbSwingAmount;
			this.tail01.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * -0.6F * swingModifier * limbSwingAmount;
			this.tail02.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * 0.6F * swingModifier * limbSwingAmount;
			this.tail03.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * -0.6F * swingModifier * limbSwingAmount;
			this.neck00.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * 0.6F * swingModifier * limbSwingAmount;
			this.neck01.rotateAngleY = MathHelper.cos(limbSwing * 0.66F) * -0.6F * swingModifier * limbSwingAmount;
		}
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