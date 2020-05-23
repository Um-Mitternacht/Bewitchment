package com.bewitchment.client.model.entity.spirit.demon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * bafometyr - cybecat5555
 * Created using Tabula 7.0.1
 */
public class ModelBafometyr extends ModelBiped {
	public ModelRenderer body;
	public ModelRenderer head;
	public ModelRenderer leftArm;
	public ModelRenderer rightArm;
	public ModelRenderer lowerBody;
	public ModelRenderer flame01;
	public ModelRenderer flame02;
	public ModelRenderer flame03;
	public ModelRenderer flame04;
	public ModelRenderer flame05;
	public ModelRenderer lEar;
	public ModelRenderer rEar;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer snout;
	public ModelRenderer snout_1;
	public ModelRenderer jawLower;
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
	public ModelRenderer beard;
	public ModelRenderer leftLeg;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg01;
	public ModelRenderer leftLeg02;
	public ModelRenderer leftHoof;
	public ModelRenderer rightLeg01;
	public ModelRenderer rightLeg02;
	public ModelRenderer rightHoof;
	
	public ModelBafometyr() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.body = new ModelRenderer(this, 19, 17);
		this.body.setRotationPoint(0.0F, -7.1F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 6, 4, 0.0F);
		this.lHorn01 = new ModelRenderer(this, 24, 0);
		this.lHorn01.setRotationPoint(2.4F, -8.0F, 1.5F);
		this.lHorn01.addBox(-1.0F, -1.0F, -1.8F, 2, 2, 4, 0.0F);
		this.setRotateAngle(lHorn01, 0.593411945678072F, 0.13962634015954636F, 0.0F);
		this.rHorn01 = new ModelRenderer(this, 24, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-2.4F, -8.0F, 1.5F);
		this.rHorn01.addBox(-1.0F, -1.0F, -1.8F, 2, 2, 4, 0.0F);
		this.setRotateAngle(rHorn01, 0.593411945678072F, -0.13962634015954636F, 0.0F);
		this.lowerBody = new ModelRenderer(this, 19, 27);
		this.lowerBody.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.lowerBody.addBox(-3.5F, 0.0F, -2.0F, 7, 7, 4, 0.0F);
		this.rHorn02d = new ModelRenderer(this, 33, 7);
		this.rHorn02d.mirror = true;
		this.rHorn02d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02d.addBox(-0.8F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.rightHoof = new ModelRenderer(this, 14, 56);
		this.rightHoof.mirror = true;
		this.rightHoof.setRotationPoint(0.0F, 6.8F, 0.0F);
		this.rightHoof.addBox(-1.5F, 0.0F, -2.6F, 3, 2, 4, 0.0F);
		this.flame04 = new ModelRenderer(this, 27, 38);
		this.flame04.mirror = true;
		this.flame04.setRotationPoint(0.0F, 3.4F, 0.0F);
		this.flame04.addBox(-5.0F, 0.0F, 0.0F, 10, 0, 16, 0.0F);
		this.setRotateAngle(flame04, 0.4363323129985824F, 1.3962634015954636F, 0.0F);
		this.beard = new ModelRenderer(this, 0, 56);
		this.beard.setRotationPoint(0.0F, 0.3F, -2.2F);
		this.beard.addBox(-1.5F, 0.0F, -1.0F, 3, 4, 4, 0.0F);
		this.setRotateAngle(beard, 0.03490658503988659F, 0.0F, 0.0F);
		this.rHorn04 = new ModelRenderer(this, 39, 12);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, 0.0F, 2.4F);
		this.rHorn04.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(rHorn04, 0.06981317007977318F, 0.22689280275926282F, 0.0F);
		this.flame03 = new ModelRenderer(this, 27, 38);
		this.flame03.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.flame03.addBox(-5.0F, 0.0F, 0.0F, 10, 0, 16, 0.0F);
		this.setRotateAngle(flame03, 0.5235987755982988F, -0.6981317007977318F, 0.0F);
		this.rightLeg02 = new ModelRenderer(this, 54, 8);
		this.rightLeg02.mirror = true;
		this.rightLeg02.setRotationPoint(0.0F, 4.2F, 0.2F);
		this.rightLeg02.addBox(-1.0F, 0.0F, -1.5F, 2, 7, 3, 0.0F);
		this.setRotateAngle(rightLeg02, -0.41887902047863906F, 0.0F, 0.0F);
		this.lHorn03 = new ModelRenderer(this, 42, 7);
		this.lHorn03.setRotationPoint(0.0F, 0.0F, 2.4F);
		this.lHorn03.addBox(-0.5F, -0.5F, -0.4F, 1, 1, 3, 0.0F);
		this.setRotateAngle(lHorn03, -0.17453292519943295F, 0.13962634015954636F, 0.0F);
		this.rHorn02b = new ModelRenderer(this, 33, 7);
		this.rHorn02b.mirror = true;
		this.rHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02b.addBox(-0.8F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.leftHoof = new ModelRenderer(this, 14, 56);
		this.leftHoof.setRotationPoint(0.0F, 6.8F, 0.0F);
		this.leftHoof.addBox(-1.5F, 0.0F, -2.6F, 3, 2, 4, 0.0F);
		this.rEar = new ModelRenderer(this, 50, 0);
		this.rEar.mirror = true;
		this.rEar.setRotationPoint(-3.4F, -6.2F, 0.8F);
		this.rEar.addBox(-3.7F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
		this.setRotateAngle(rEar, -0.5235987755982988F, 0.0F, 0.5235987755982988F);
		this.snout_1 = new ModelRenderer(this, 29, 56);
		this.snout_1.setRotationPoint(0.0F, -4.9F, -2.9F);
		this.snout_1.addBox(-2.0F, -1.9F, -5.1F, 4, 3, 5, 0.0F);
		this.setRotateAngle(snout_1, 0.5235987755982988F, 0.0F, 0.0F);
		this.rightArm = new ModelRenderer(this, 44, 14);
		this.rightArm.mirror = true;
		this.rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 14, 4, 0.0F);
		this.setRotateAngle(rightArm, 0.0F, 0.0F, 0.10000736613927509F);
		this.lEar = new ModelRenderer(this, 50, 0);
		this.lEar.setRotationPoint(3.4F, -6.0F, 0.8F);
		this.lEar.addBox(-0.3F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
		this.setRotateAngle(lEar, -0.5235987755982988F, 0.0F, -0.5235987755982988F);
		this.lHorn02c = new ModelRenderer(this, 33, 7);
		this.lHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02c.addBox(-0.2F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.rHorn02c = new ModelRenderer(this, 33, 7);
		this.rHorn02c.mirror = true;
		this.rHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02c.addBox(-0.2F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.lHorn02a = new ModelRenderer(this, 33, 7);
		this.lHorn02a.setRotationPoint(0.0F, 0.0F, 2.1F);
		this.lHorn02a.addBox(-0.2F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.setRotateAngle(lHorn02a, -0.3141592653589793F, 0.12217304763960307F, 0.0F);
		this.lHorn02b = new ModelRenderer(this, 33, 7);
		this.lHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02b.addBox(-0.8F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.head = new ModelRenderer(this, 0, 1);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rHorn02a = new ModelRenderer(this, 33, 7);
		this.rHorn02a.mirror = true;
		this.rHorn02a.setRotationPoint(0.0F, 0.0F, 2.1F);
		this.rHorn02a.addBox(-0.2F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.setRotateAngle(rHorn02a, -0.3141592653589793F, -0.12217304763960307F, 0.0F);
		this.leftLeg = new ModelRenderer(this, 0, 17);
		this.leftLeg.setRotationPoint(2.1F, 6.5F, -0.1F);
		this.leftLeg.addBox(-2.0F, -1.0F, -2.5F, 4, 9, 5, 0.0F);
		this.setRotateAngle(leftLeg, -0.2617993877991494F, 0.0F, -0.10471975511965977F);
		this.leftArm = new ModelRenderer(this, 44, 14);
		this.leftArm.setRotationPoint(5.0F, 2.0F, -0.0F);
		this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 14, 4, 0.0F);
		this.setRotateAngle(leftArm, 0.0F, 0.0F, -0.10000736613927509F);
		this.lHorn04 = new ModelRenderer(this, 39, 12);
		this.lHorn04.setRotationPoint(0.0F, 0.0F, 2.4F);
		this.lHorn04.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(lHorn04, 0.06981317007977318F, -0.22689280275926282F, 0.0F);
		this.lHorn02d = new ModelRenderer(this, 33, 7);
		this.lHorn02d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02d.addBox(-0.8F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.flame05 = new ModelRenderer(this, 5, 40);
		this.flame05.mirror = true;
		this.flame05.setRotationPoint(0.0F, 3.4F, 0.0F);
		this.flame05.addBox(-5.0F, 0.0F, 0.0F, 10, 0, 16, 0.0F);
		this.setRotateAngle(flame05, 0.4363323129985824F, -1.3962634015954636F, 0.0F);
		this.snout = new ModelRenderer(this, 44, 55);
		this.snout.setRotationPoint(0.0F, -1.8F, -0.6F);
		this.snout.addBox(-2.5F, -1.5F, -7.1F, 5, 2, 4, 0.0F);
		this.rightLeg01 = new ModelRenderer(this, 0, 31);
		this.rightLeg01.mirror = true;
		this.rightLeg01.setRotationPoint(0.0F, 6.6F, -0.4F);
		this.rightLeg01.addBox(-1.5F, 0.0F, -2.0F, 3, 5, 4, 0.0F);
		this.setRotateAngle(rightLeg01, 0.6981317007977318F, 0.0F, -0.10471975511965977F);
		this.flame02 = new ModelRenderer(this, 5, 40);
		this.flame02.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.flame02.addBox(-5.0F, 0.0F, 0.0F, 10, 0, 16, 0.0F);
		this.setRotateAngle(flame02, 0.5235987755982988F, 0.6981317007977318F, 0.0F);
		this.jawLower = new ModelRenderer(this, 37, 0);
		this.jawLower.setRotationPoint(0.0F, -0.8F, -3.5F);
		this.jawLower.addBox(-2.0F, -0.5F, -4.0F, 4, 1, 4, 0.0F);
		this.setRotateAngle(jawLower, -0.03490658503988659F, 0.0F, 0.0F);
		this.leftLeg01 = new ModelRenderer(this, 0, 31);
		this.leftLeg01.setRotationPoint(0.0F, 6.6F, -0.4F);
		this.leftLeg01.addBox(-1.5F, 0.0F, -2.0F, 3, 5, 4, 0.0F);
		this.setRotateAngle(leftLeg01, 0.6981317007977318F, 0.0F, 0.10471975511965977F);
		this.rHorn03 = new ModelRenderer(this, 42, 7);
		this.rHorn03.mirror = true;
		this.rHorn03.setRotationPoint(0.0F, 0.0F, 2.4F);
		this.rHorn03.addBox(-0.5F, -0.5F, -0.4F, 1, 1, 3, 0.0F);
		this.setRotateAngle(rHorn03, -0.17453292519943295F, -0.13962634015954636F, 0.0F);
		this.rightLeg = new ModelRenderer(this, 0, 17);
		this.rightLeg.mirror = true;
		this.rightLeg.setRotationPoint(-2.1F, 6.5F, -0.1F);
		this.rightLeg.addBox(-2.0F, -1.0F, -2.5F, 4, 9, 5, 0.0F);
		this.setRotateAngle(rightLeg, -0.2617993877991494F, 0.0F, 0.10471975511965977F);
		this.flame01 = new ModelRenderer(this, -16, 40);
		this.flame01.setRotationPoint(0.0F, 1.1F, 0.9F);
		this.flame01.addBox(-5.0F, 0.0F, 0.0F, 10, 0, 16, 0.0F);
		this.setRotateAngle(flame01, 0.6981317007977318F, 0.0F, 0.0F);
		this.leftLeg02 = new ModelRenderer(this, 54, 8);
		this.leftLeg02.setRotationPoint(0.0F, 4.2F, 0.2F);
		this.leftLeg02.addBox(-1.0F, 0.0F, -1.5F, 2, 7, 3, 0.0F);
		this.setRotateAngle(leftLeg02, -0.41887902047863906F, 0.0F, 0.0F);
		this.head.addChild(this.lHorn01);
		this.head.addChild(this.rHorn01);
		this.body.addChild(this.lowerBody);
		this.rHorn02a.addChild(this.rHorn02d);
		this.rightLeg02.addChild(this.rightHoof);
		this.body.addChild(this.flame04);
		this.jawLower.addChild(this.beard);
		this.rHorn03.addChild(this.rHorn04);
		this.body.addChild(this.flame03);
		this.rightLeg01.addChild(this.rightLeg02);
		this.lHorn02a.addChild(this.lHorn03);
		this.rHorn02a.addChild(this.rHorn02b);
		this.leftLeg02.addChild(this.leftHoof);
		this.head.addChild(this.rEar);
		this.head.addChild(this.snout_1);
		this.body.addChild(this.rightArm);
		this.head.addChild(this.lEar);
		this.lHorn02a.addChild(this.lHorn02c);
		this.rHorn02a.addChild(this.rHorn02c);
		this.lHorn01.addChild(this.lHorn02a);
		this.lHorn02a.addChild(this.lHorn02b);
		this.body.addChild(this.head);
		this.rHorn01.addChild(this.rHorn02a);
		this.lowerBody.addChild(this.leftLeg);
		this.body.addChild(this.leftArm);
		this.lHorn03.addChild(this.lHorn04);
		this.lHorn02a.addChild(this.lHorn02d);
		this.body.addChild(this.flame05);
		this.head.addChild(this.snout);
		this.rightLeg.addChild(this.rightLeg01);
		this.body.addChild(this.flame02);
		this.head.addChild(this.jawLower);
		this.leftLeg.addChild(this.leftLeg01);
		this.rHorn02a.addChild(this.rHorn03);
		this.lowerBody.addChild(this.rightLeg);
		this.body.addChild(this.flame01);
		this.leftLeg01.addChild(this.leftLeg02);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body.render(f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netheadYaw, float headPitch, float scaleFactor, Entity entity) {
		boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
		this.head.rotateAngleY = netheadYaw * 0.017453292F;
		
		if (flag) {
			this.head.rotateAngleX = -((float) Math.PI / 4F);
		}
		else {
			this.head.rotateAngleX = headPitch * 0.017453292F;
		}
		
		this.body.rotateAngleY = 0.0F;
		float f = 1.0F;
		
		if (flag) {
			f = (float) (entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}
		
		if (f < 1.0F) {
			f = 1.0F;
		}
		
		float swingMod = 0.6F;
		this.leftLeg01.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount - -0.69F;
		this.rightLeg01.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount - -0.69F;
		
		
		this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightArm.rotateAngleZ = 0.10000736613927509F;
		this.leftArm.rotateAngleZ = -0.10000736613927509F;
		
		if (this.isRiding) {
			this.rightArm.rotateAngleX += -((float) Math.PI / 5F);
			this.leftArm.rotateAngleX += -((float) Math.PI / 5F);
			this.rightLeg01.rotateAngleX = -1.4137167F;
			this.rightLeg01.rotateAngleY = ((float) Math.PI / 10F);
			this.rightLeg01.rotateAngleZ = 0.07853982F;
			this.leftLeg01.rotateAngleX = -1.4137167F;
			this.leftLeg01.rotateAngleY = -((float) Math.PI / 10F);
			this.leftLeg01.rotateAngleZ = -0.07853982F;
		}
		
		this.rightArm.rotateAngleY = 0.0F;
		this.rightArm.rotateAngleZ = 0.0F;
		
		switch (this.leftArmPose) {
			case EMPTY:
				this.leftArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - 0.9424779F;
				this.leftArm.rotateAngleY = 0.5235988F;
				break;
			case ITEM:
				this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
				this.leftArm.rotateAngleY = 0.0F;
		}
		
		switch (this.rightArmPose) {
			case EMPTY:
				this.rightArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - 0.9424779F;
				this.rightArm.rotateAngleY = -0.5235988F;
				break;
			case ITEM:
				this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
				this.rightArm.rotateAngleY = 0.0F;
		}
		
		if (this.swingProgress > 0.0F) {
			EnumHandSide enumhandside = this.getMainHand(entity);
			ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
			float f1 = this.swingProgress;
			this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
			
			if (enumhandside == EnumHandSide.LEFT) {
				this.body.rotateAngleY *= -1.0F;
			}
			
			this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			this.rightArm.rotateAngleY += this.body.rotateAngleY;
			this.leftArm.rotateAngleY += this.body.rotateAngleY;
			this.leftArm.rotateAngleY += this.body.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float) Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
			modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
			modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
			modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
		}
		
		
		this.body.rotateAngleX = 0.0F;
		this.head.rotationPointY = 0.0F;
		
		this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		
		if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
			this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY + 0.4F;
			this.rightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
			this.leftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
		}
		else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY - 0.4F;
			this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY;
			this.rightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
			this.leftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
		}
		
		copyModelAngles(this.head, this.bipedHeadwear);
		
		setLivingAnimations((EntityLivingBase) entity, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
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
