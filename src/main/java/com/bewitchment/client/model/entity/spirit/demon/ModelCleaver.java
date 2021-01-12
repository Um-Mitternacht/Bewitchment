package com.bewitchment.client.model.entity.spirit.demon;

import com.bewitchment.common.entity.spirit.demon.EntityCleaver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * cleaver - cybercat5555
 * Created using Tabula 7.0.1
 */
public class ModelCleaver extends ModelBiped {
	public ModelRenderer loincloth;
	public ModelRenderer loinclothBack;
	public ModelRenderer leftLeg02;
	public ModelRenderer leftLeg03;
	public ModelRenderer leftHoof;
	public ModelRenderer rightLeg02;
	public ModelRenderer rightLeg03;
	public ModelRenderer rightHoof;
	public ModelRenderer upperJaw;
	public ModelRenderer snout;
	public ModelRenderer lowerJaw;
	public ModelRenderer lEar00;
	public ModelRenderer rEar00;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer upperTeethL;
	public ModelRenderer upperTeethR;
	public ModelRenderer lowerTeethL;
	public ModelRenderer lowerTeethR;
	public ModelRenderer lowerTeethM;
	public ModelRenderer lEar01;
	public ModelRenderer rEar01;
	public ModelRenderer lHorn02a;
	public ModelRenderer lHorn02b;
	public ModelRenderer lHorn02c;
	public ModelRenderer lHorn02d;
	public ModelRenderer lHorn03;
	public ModelRenderer lHorn04;
	public ModelRenderer rHorn02a;
	public ModelRenderer rHorn02b;
	public ModelRenderer rHorn02c;
	public ModelRenderer rHorn02d;
	public ModelRenderer rHorn03;
	public ModelRenderer rHorn04;
	public ModelRenderer loincloth02;

	public ModelCleaver(float modelSize) {
		super(modelSize);
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.lHorn03 = new ModelRenderer(this, 38, 43);
		this.lHorn03.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.lHorn03.addBox(-0.5F, -2.5F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn03, 0.13962634015954636F, 0.0F, -0.3490658503988659F);
		this.rHorn02d = new ModelRenderer(this, 39, 38);
		this.rHorn02d.mirror = true;
		this.rHorn02d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02d.addBox(-0.2F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
		this.leftHoof = new ModelRenderer(this, 25, 52);
		this.leftHoof.setRotationPoint(0.0F, 5.2F, -0.7F);
		this.leftHoof.addBox(-1.5F, -0.7F, -1.9F, 3, 2, 4, 0.0F);
		this.rEar00 = new ModelRenderer(this, 47, 0);
		this.rEar00.mirror = true;
		this.rEar00.setRotationPoint(-3.4F, -5.5F, 0.0F);
		this.rEar00.addBox(-5.0F, -0.5F, -1.5F, 5, 1, 3, 0.0F);
		this.setRotateAngle(rEar00, -0.6981317007977318F, 0.08726646259971647F, -0.3490658503988659F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
		this.setRotateAngle(bipedLeftArm, -0.0F, 0.0F, -0.10000736613927509F);
		this.upperJaw = new ModelRenderer(this, 0, 46);
		this.upperJaw.mirror = true;
		this.upperJaw.setRotationPoint(0.0F, -3.8F, -4.1F);
		this.upperJaw.addBox(-3.0F, -0.65F, -4.7F, 6, 3, 5, 0.0F);
		this.rightLeg03 = new ModelRenderer(this, 24, 43);
		this.rightLeg03.mirror = true;
		this.rightLeg03.setRotationPoint(0.0F, 5.4F, 0.0F);
		this.rightLeg03.addBox(-1.51F, -0.4F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(rightLeg03, -0.4363323129985824F, 0.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.mirror = true;
		this.bipedRightLeg.setRotationPoint(-2.0F, 12.4F, 0.1F);
		this.bipedRightLeg.addBox(-2.2F, -0.9F, -2.0F, 4, 9, 4, 0.0F);
		this.setRotateAngle(bipedRightLeg, -0.3490658503988659F, 0.0F, 0.10471975511965977F);
		this.lHorn02c = new ModelRenderer(this, 39, 38);
		this.lHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02c.addBox(-0.8F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
		this.leftLeg03 = new ModelRenderer(this, 24, 43);
		this.leftLeg03.setRotationPoint(0.0F, 5.4F, 0.0F);
		this.leftLeg03.addBox(-1.49F, -0.4F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(leftLeg03, -0.4363323129985824F, 0.0F, 0.0F);
		this.rHorn01 = new ModelRenderer(this, 37, 38);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-2.8F, -6.8F, 2.0F);
		this.rHorn01.addBox(-1.0F, -2.5F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn01, 0.08726646259971647F, 0.0F, -0.7853981633974483F);
		this.snout = new ModelRenderer(this, 0, 35);
		this.snout.setRotationPoint(0.0F, -5.0F, -2.3F);
		this.snout.addBox(-2.5F, -2.05F, -6.4F, 5, 3, 6, 0.0F);
		this.setRotateAngle(snout, 0.3490658503988659F, 0.0F, 0.0F);
		this.lowerTeethR = new ModelRenderer(this, 24, 58);
		this.lowerTeethR.mirror = true;
		this.lowerTeethR.setRotationPoint(-2.2F, -0.4F, -3.5F);
		this.lowerTeethR.addBox(-0.2F, -2.0F, -1.4F, 1, 2, 4, 0.0F);
		this.lowerTeethM = new ModelRenderer(this, 35, 61);
		this.lowerTeethM.setRotationPoint(0.0F, -0.4F, -3.5F);
		this.lowerTeethM.addBox(-1.5F, -1.1F, -1.4F, 3, 1, 0, 0.0F);
		this.rHorn02b = new ModelRenderer(this, 39, 38);
		this.rHorn02b.mirror = true;
		this.rHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02b.addBox(-0.2F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rHorn02a = new ModelRenderer(this, 39, 38);
		this.rHorn02a.mirror = true;
		this.rHorn02a.setRotationPoint(-0.1F, -1.9F, 0.2F);
		this.rHorn02a.addBox(-0.8F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn02a, 0.2792526803190927F, 0.0F, 0.2617993877991494F);
		this.lEar01 = new ModelRenderer(this, 48, 5);
		this.lEar01.setRotationPoint(0.0F, -0.9F, 0.0F);
		this.lEar01.addBox(0.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
		this.setRotateAngle(lEar01, 0.0F, 0.0F, 0.22759093446006054F);
		this.lHorn02d = new ModelRenderer(this, 39, 38);
		this.lHorn02d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02d.addBox(-0.2F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
		this.leftLeg02 = new ModelRenderer(this, 24, 33);
		this.leftLeg02.setRotationPoint(0.3F, 6.7F, -0.7F);
		this.leftLeg02.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(leftLeg02, 0.767944870877505F, 0.0F, 0.10471975511965977F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.setRotationPoint(2.1F, 12.4F, 0.1F);
		this.bipedLeftLeg.addBox(-1.8F, -0.9F, -2.0F, 4, 9, 4, 0.0F);
		this.setRotateAngle(bipedLeftLeg, -0.3490658503988659F, 0.0F, -0.10471975511965977F);
		this.rEar01 = new ModelRenderer(this, 48, 5);
		this.rEar01.mirror = true;
		this.rEar01.setRotationPoint(0.0F, -0.9F, 0.0F);
		this.rEar01.addBox(-4.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
		this.setRotateAngle(rEar01, 0.0F, 0.0F, -0.22759093446006054F);
		this.lHorn02b = new ModelRenderer(this, 39, 38);
		this.lHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02b.addBox(-0.2F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
		this.rHorn04 = new ModelRenderer(this, 43, 43);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, -2.3F, 0.0F);
		this.rHorn04.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn04, 0.13962634015954636F, 0.0F, 0.3490658503988659F);
		this.loincloth02 = new ModelRenderer(this, 48, 43);
		this.loincloth02.setRotationPoint(0.0F, 7.7F, 0.0F);
		this.loincloth02.addBox(-3.0F, 0.05F, -0.55F, 6, 6, 1, 0.0F);
		this.setRotateAngle(loincloth02, 0.3490658503988659F, 0.0F, 0.0F);
		this.upperTeethL = new ModelRenderer(this, 52, 58);
		this.upperTeethL.setRotationPoint(2.0F, 2.2F, -2.8F);
		this.upperTeethL.addBox(-1.2F, 0.0F, -1.7F, 2, 2, 4, 0.0F);
		this.lHorn01 = new ModelRenderer(this, 37, 38);
		this.lHorn01.setRotationPoint(2.8F, -6.8F, 2.0F);
		this.lHorn01.addBox(-1.0F, -2.5F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn01, 0.08726646259971647F, 0.0F, 0.7853981633974483F);
		this.rightLeg02 = new ModelRenderer(this, 24, 33);
		this.rightLeg02.mirror = true;
		this.rightLeg02.setRotationPoint(-0.3F, 6.7F, -0.7F);
		this.rightLeg02.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.setRotateAngle(rightLeg02, 0.767944870877505F, 0.0F, -0.10471975511965977F);
		this.lHorn04 = new ModelRenderer(this, 43, 43);
		this.lHorn04.setRotationPoint(0.0F, -2.3F, 0.0F);
		this.lHorn04.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn04, 0.13962634015954636F, 0.0F, -0.3490658503988659F);
		this.rightHoof = new ModelRenderer(this, 25, 52);
		this.rightHoof.mirror = true;
		this.rightHoof.setRotationPoint(0.0F, 5.2F, -0.7F);
		this.rightHoof.addBox(-1.5F, -0.7F, -1.9F, 3, 2, 4, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, -5.7F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 13, 4, 0.0F);
		this.lEar00 = new ModelRenderer(this, 47, 0);
		this.lEar00.setRotationPoint(3.4F, -5.5F, 0.0F);
		this.lEar00.addBox(0.0F, -0.5F, -1.5F, 5, 1, 3, 0.0F);
		this.setRotateAngle(lEar00, -0.6981317007977318F, 0.08726646259971647F, 0.3490658503988659F);
		this.lowerTeethL = new ModelRenderer(this, 24, 58);
		this.lowerTeethL.setRotationPoint(2.2F, -0.4F, -3.5F);
		this.lowerTeethL.addBox(-0.8F, -2.0F, -1.4F, 1, 2, 4, 0.0F);
		this.loincloth = new ModelRenderer(this, 48, 34);
		this.loincloth.setRotationPoint(0.0F, 10.6F, -1.5F);
		this.loincloth.addBox(-3.0F, 0.0F, -0.5F, 6, 8, 1, 0.0F);
		this.setRotateAngle(loincloth, -0.3490658503988659F, 0.0F, 0.0F);
		this.loinclothBack = new ModelRenderer(this, 48, 51);
		this.loinclothBack.setRotationPoint(0.0F, 10.6F, 1.6F);
		this.loinclothBack.addBox(-3.5F, 0.05F, -0.25F, 7, 6, 1, 0.0F);
		this.lHorn02a = new ModelRenderer(this, 39, 38);
		this.lHorn02a.setRotationPoint(0.1F, -1.9F, 0.2F);
		this.lHorn02a.addBox(-0.8F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn02a, 0.2792526803190927F, 0.0F, -0.2617993877991494F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
		this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.10000736613927509F);
		this.lowerJaw = new ModelRenderer(this, 0, 55);
		this.lowerJaw.setRotationPoint(0.0F, -0.9F, -3.5F);
		this.lowerJaw.addBox(-2.5F, -0.5F, -5.0F, 5, 1, 6, 0.0F);
		this.setRotateAngle(lowerJaw, -0.03490658503988659F, 0.0F, 0.0F);
		this.upperTeethR = new ModelRenderer(this, 52, 58);
		this.upperTeethR.mirror = true;
		this.upperTeethR.setRotationPoint(-2.0F, 2.2F, -2.8F);
		this.upperTeethR.addBox(-0.8F, 0.0F, -1.7F, 2, 2, 4, 0.0F);
		this.rHorn02c = new ModelRenderer(this, 39, 38);
		this.rHorn02c.mirror = true;
		this.rHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02c.addBox(-0.8F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
		this.rHorn03 = new ModelRenderer(this, 38, 43);
		this.rHorn03.mirror = true;
		this.rHorn03.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.rHorn03.addBox(-0.5F, -2.5F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn03, 0.13962634015954636F, 0.0F, 0.3490658503988659F);
		this.lHorn02a.addChild(this.lHorn03);
		this.rHorn02a.addChild(this.rHorn02d);
		this.leftLeg03.addChild(this.leftHoof);
		this.bipedHead.addChild(this.rEar00);
		this.bipedBody.addChild(this.bipedLeftArm);
		this.bipedHead.addChild(this.upperJaw);
		this.rightLeg02.addChild(this.rightLeg03);
		this.bipedBody.addChild(this.bipedRightLeg);
		this.lHorn02a.addChild(this.lHorn02c);
		this.leftLeg02.addChild(this.leftLeg03);
		this.bipedHead.addChild(this.rHorn01);
		this.bipedHead.addChild(this.snout);
		this.lowerJaw.addChild(this.lowerTeethR);
		this.lowerJaw.addChild(this.lowerTeethM);
		this.rHorn02a.addChild(this.rHorn02b);
		this.bipedBody.addChild(this.bipedHead);
		this.rHorn01.addChild(this.rHorn02a);
		this.lEar00.addChild(this.lEar01);
		this.lHorn02a.addChild(this.lHorn02d);
		this.bipedLeftLeg.addChild(this.leftLeg02);
		this.bipedBody.addChild(this.bipedLeftLeg);
		this.rEar00.addChild(this.rEar01);
		this.lHorn02a.addChild(this.lHorn02b);
		this.rHorn03.addChild(this.rHorn04);
		this.loincloth.addChild(this.loincloth02);
		this.upperJaw.addChild(this.upperTeethL);
		this.bipedHead.addChild(this.lHorn01);
		this.bipedRightLeg.addChild(this.rightLeg02);
		this.lHorn03.addChild(this.lHorn04);
		this.rightLeg03.addChild(this.rightHoof);
		this.bipedHead.addChild(this.lEar00);
		this.lowerJaw.addChild(this.lowerTeethL);
		this.bipedBody.addChild(this.loincloth);
		this.bipedBody.addChild(this.loinclothBack);
		this.lHorn01.addChild(this.lHorn02a);
		this.bipedBody.addChild(this.bipedRightArm);
		this.bipedHead.addChild(this.lowerJaw);
		this.upperJaw.addChild(this.upperTeethR);
		this.rHorn02a.addChild(this.rHorn02c);
		this.rHorn02a.addChild(this.rHorn03);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bipedBody.render(f5);
	}

	@SuppressWarnings("incomplete-switch")
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getTicksElytraFlying() > 4;
		EntityCleaver cleaver = (EntityCleaver) entityIn;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

		if (flag) {
			this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
		} else {
			this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		}

		this.bipedBody.rotateAngleY = 0.0F;
		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedRightArm.rotationPointX = -5.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointX = 5.0F;
		float f = 1.0F;

		if (flag) {
			f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		float swingMod = 0.6F;
		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount - 0.26F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount - 0.26F;

		if (this.isRiding) {
			this.bipedRightArm.rotateAngleX += -((float) Math.PI / 5F);
			this.bipedLeftArm.rotateAngleX += -((float) Math.PI / 5F);
			this.bipedRightLeg.rotateAngleX = -1.4137167F;
			this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 10F);
			this.bipedRightLeg.rotateAngleZ = 0.07853982F;
			this.bipedLeftLeg.rotateAngleX = -1.4137167F;
			this.bipedLeftLeg.rotateAngleY = -((float) Math.PI / 10F);
			this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
		}

		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;

		switch (this.leftArmPose) {
			case EMPTY:
				this.bipedLeftArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
				this.bipedLeftArm.rotateAngleY = 0.5235988F;
				break;
			case ITEM:
				this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
				this.bipedLeftArm.rotateAngleY = 0.0F;
		}

		switch (this.rightArmPose) {
			case EMPTY:
				this.bipedRightArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
				this.bipedRightArm.rotateAngleY = -0.5235988F;
				break;
			case ITEM:
				this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
				this.bipedRightArm.rotateAngleY = 0.0F;
		}

		if (this.swingProgress > 0.0F) {
			EnumHandSide enumhandside = this.getMainHand(entityIn);
			ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
			float f1 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

			if (enumhandside == EnumHandSide.LEFT) {
				this.bipedBody.rotateAngleY *= -1.0F;
			}

			this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float) Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
			modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
		}

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
		} else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}

		int i = cleaver.attackTimer;
		if (i > 0) {
			this.lowerJaw.rotateAngleX = 0.75f;
		}

		copyModelAngles(this.bipedHead, this.bipedHeadwear);

		setLivingAnimations((EntityLivingBase) entityIn, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
	}

	@Override
	public void postRenderArm(float scale, EnumHandSide side) {
		GlStateManager.translate(0.025F, -0.3, 0);
		super.postRenderArm(scale, side);
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
