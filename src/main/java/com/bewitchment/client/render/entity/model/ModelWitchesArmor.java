package com.bewitchment.client.render.entity.model;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

/**
 * Witches_attire - Ingoleth
 * Created using Tabula 5.1.0
 */
public class ModelWitchesArmor extends ModelBiped {
	public static final ModelWitchesArmor INSTANCE = new ModelWitchesArmor();
	public ModelRenderer legLeft;
	public ModelRenderer legRight;
	public ModelRenderer hat1;
	public ModelRenderer body;
	public ModelRenderer tunicLeftBack;
	public ModelRenderer tunicLeftFront;
	public ModelRenderer tunicLeft;
	public ModelRenderer tunicLeftBack_1;
	public ModelRenderer tunicFront;
	public ModelRenderer tunicLeft_1;
	public ModelRenderer tunicRightFront;
	public ModelRenderer tunicRightBack;
	public ModelRenderer tunicRight;
	public ModelRenderer tunicRightFront_1;
	public ModelRenderer tunicRightBack_1;
	public ModelRenderer tunicRight_1;
	public ModelRenderer hat2;
	public ModelRenderer hatWing;
	public ModelRenderer hat3;
	public ModelRenderer armRight;
	public ModelRenderer armLeft;
	public ModelRenderer shoulderRight;
	public ModelRenderer sleeveRight;
	public ModelRenderer shoulderLeft;
	public ModelRenderer sleeveLeft;
	public ModelRenderer legLeftTop;
	public ModelRenderer legRightTop;

	public ModelWitchesArmor() {
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.tunicRightBack_1 = new ModelRenderer(this, 53, 77);
		this.tunicRightBack_1.setRotationPoint(-0.01F, 10.0F, -1.0F);
		this.tunicRightBack_1.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicRightBack_1, 0.26F, 0.0F, 0.0F);
		this.tunicFront = new ModelRenderer(this, 53, 77);
		this.tunicFront.setRotationPoint(-0.01F, 10.0F, -1.0F);
		this.tunicFront.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicFront, 0.26F, 0.0F, 0.0F);
		this.body = new ModelRenderer(this, 3, 81);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, -0.01F, -2.0F, 8, 12, 4, 0.3F);
		this.legRight = new ModelRenderer(this, 0, 49);
		this.legRight.mirror = true;
		this.legRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.legRight.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, 0.0F);
		this.tunicRightFront_1 = new ModelRenderer(this, 53, 77);
		this.tunicRightFront_1.setRotationPoint(-0.01F, 10.0F, 1F);
		this.tunicRightFront_1.addBox(0.0F, 0.0F, -1.0F, 4, 2, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicRightFront_1, -0.26F, 0.0F, 0.0F);
		this.armRight = new ModelRenderer(this, 47, 81);
		this.armRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.armRight.addBox(-3.0F, -1.9F, -2.0F, 4, 12, 4, 0.251F);
		this.hat1 = new ModelRenderer(this, 2, 100);
		this.hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hat1.addBox(-4.5F, -10.5F, -4.5F, 9, 5, 9, 0.01F);
		this.tunicLeft = new ModelRenderer(this, 23, 65);
		this.tunicLeft.setRotationPoint(-2.51F, 0.0F, -2.1F);
		this.tunicLeft.addBox(0.0F, 0.0F, -1.0F, 4, 10, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicLeft, 0.122F, -1.57F, 0.0F);
		this.legLeft = new ModelRenderer(this, 3, 44);
		this.legLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, 0.0F);
		this.tunicLeftFront = new ModelRenderer(this, 53, 65);
		this.tunicLeftFront.setRotationPoint(2.01F, 0.0F, -2.51F);
		this.tunicLeftFront.addBox(0.0F, 0.0F, -1.0F, 4, 10, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicLeftFront, -3.02F, 0.0F, 3.141592653589793F);
		this.tunicLeft_1 = new ModelRenderer(this, 23, 77);
		this.tunicLeft_1.setRotationPoint(-0.01F, 10.0F, -1.0F);
		this.tunicLeft_1.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicLeft_1, 0.26F, 0.0F, 0.0F);
		this.tunicRightFront = new ModelRenderer(this, 53, 65);
		this.tunicRightFront.setRotationPoint(-1.99F, 0.0F, -2.51F);
		this.tunicRightFront.addBox(0.0F, 0.0F, 0.0F, 4, 10, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicRightFront, -0.122F, 0.0F, 0.0F);
		this.hat2 = new ModelRenderer(this, 39, 107);
		this.hat2.setRotationPoint(0.0F, -10.5F, -3.0F);
		this.hat2.addBox(-3.0F, -4.0F, 0.0F, 6, 4, 6, 0.0F);
		ModelHelper.setRotateAngle(this.hat2, -0.26F, 0.0F, 0.0F);
		this.tunicRight_1 = new ModelRenderer(this, 23, 77);
		this.tunicRight_1.setRotationPoint(-0.01F, 10.0F, 1F);
		this.tunicRight_1.addBox(0.0F, 0.0F, -1.0F, 4, 2, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicRight_1, -0.26F, 0.0F, 0.0F);
		this.sleeveRight = new ModelRenderer(this, 49, 98);
		this.sleeveRight.setRotationPoint(1.01F, 10.1F, 2.0F);
		this.sleeveRight.addBox(-4.0F, -4.0F, 0.0F, 4, 4, 2, 0.2F);
		ModelHelper.setRotateAngle(this.sleeveRight, 0.524F, 0.0F, 0.0F);
		this.hatWing = new ModelRenderer(this, 0, 115);
		this.hatWing.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.hatWing.addBox(-6.0F, 0.0F, -6.0F, 12, 1, 12, 0.0F);
		ModelHelper.setRotateAngle(this.hatWing, 0.035F, 0.0F, 0.07F);
		this.tunicRight = new ModelRenderer(this, 23, 65);
		this.tunicRight.setRotationPoint(2.51F, 0.0F, -2.1F);
		this.tunicRight.addBox(0.0F, 0.0F, 0.0F, 4, 10, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicRight, -0.122F, -1.57F, 0.0F);
		this.shoulderRight = new ModelRenderer(this, 0, 64);
		this.shoulderRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shoulderRight.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5, 0.251F);
		ModelHelper.setRotateAngle(this.shoulderRight, 0.0F, 3.141592653589793F, -0.087F);
		this.tunicLeftBack = new ModelRenderer(this, 53, 65);
		this.tunicLeftBack.setRotationPoint(2.01F, 0.0F, 2.51F);
		this.tunicLeftBack.addBox(0.0F, 0.0F, 0.0F, 4, 10, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicLeftBack, 3.02F, 0.0F, 3.141592653589793F);
		this.armLeft = new ModelRenderer(this, 47, 81);
		this.armLeft.mirror = true;
		this.armLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.armLeft.addBox(-1.0F, -1.9F, -2.0F, 4, 12, 4, 0.251F);
		this.hat3 = new ModelRenderer(this, 51, 118);
		this.hat3.setRotationPoint(1.4F, -4.0F, 1.5F);
		this.hat3.addBox(-3.0F, -4.0F, 0.0F, 3, 4, 3, 0.0F);
		ModelHelper.setRotateAngle(this.hat3, -0.349F, 0.0F, 0.0F);
		this.tunicLeftBack_1 = new ModelRenderer(this, 53, 77);
		this.tunicLeftBack_1.setRotationPoint(-0.01F, 10.0F, 1F);
		this.tunicLeftBack_1.addBox(0.0F, 0.0F, -1.0F, 4, 2, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicLeftBack_1, -0.262F, 0.0F, 0.0F);
		this.tunicRightBack = new ModelRenderer(this, 53, 65);
		this.tunicRightBack.setRotationPoint(-1.99F, 0.0F, 2.51F);
		this.tunicRightBack.addBox(0.0F, 0.0F, -1.0F, 4, 10, 1, 0.0F);
		ModelHelper.setRotateAngle(this.tunicRightBack, 0.122F, 0.0F, 0.0F);
		this.sleeveLeft = new ModelRenderer(this, 49, 98);
		this.sleeveLeft.setRotationPoint(0.99F, 10.1F, 2.0F);
		this.sleeveLeft.addBox(-2.0F, -4.0F, 0.0F, 4, 4, 2, 0.2F);
		ModelHelper.setRotateAngle(this.sleeveLeft, 0.524F, 0.0F, 0.0F);
		this.shoulderLeft = new ModelRenderer(this, 0, 64);
		this.shoulderLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shoulderLeft.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5, 0.251F);
		ModelHelper.setRotateAngle(this.shoulderLeft, 0.0F, 0.0F, 0.087F);
		this.legLeftTop = new ModelRenderer(this, 19, 93);
		this.legLeftTop.setRotationPoint(-2.0F, -0.01F, -2.0F);
		this.legLeftTop.addBox(0.0F, 0.0F, 0.0F, 4, 0, 4);
		this.legRightTop = new ModelRenderer(this, 19, 93);
		this.legRightTop.setRotationPoint(-2.0F, -0.01F, -2.0F);
		this.legRightTop.addBox(0.0F, 0.0F, 0.0F, 4, 0, 4);

		this.bipedHead = new ModelRenderer(this);
		this.bipedBody = new ModelRenderer(this);

		this.legLeft.addChild(this.tunicLeftBack);
		this.tunicLeftBack.addChild(this.tunicLeftBack_1);
		this.legLeft.addChild(this.tunicLeft);
		this.tunicLeft.addChild(this.tunicLeft_1);
		this.legLeft.addChild(this.tunicLeftFront);
		this.tunicLeftFront.addChild(this.tunicFront);
		this.legLeft.addChild(this.legLeftTop);
		this.legRight.addChild(this.legRightTop);

		this.legRight.addChild(this.tunicRight);
		this.tunicRight.addChild(this.tunicRight_1);
		this.legRight.addChild(this.tunicRightBack);
		this.tunicRightBack.addChild(this.tunicRightBack_1);
		this.legRight.addChild(this.tunicRightFront);
		this.tunicRightFront.addChild(this.tunicRightFront_1);

		this.hat1.addChild(this.hat2);
		this.hat2.addChild(this.hat3);
		this.hat1.addChild(this.hatWing);

		this.armLeft.addChild(this.sleeveLeft);
		this.armLeft.addChild(this.shoulderLeft);
		this.armRight.addChild(this.sleeveRight);
		this.armRight.addChild(this.shoulderRight);

		this.bipedBody.addChild(this.body);
		this.bipedHead.addChild(this.hat1);
		this.bipedLeftArm.addChild(this.armLeft);
		this.bipedRightArm.addChild(this.armRight);
		this.bipedRightLeg.addChild(this.legLeft);
		this.bipedLeftLeg.addChild(this.legRight);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

		super.render(entity, f, f1, f2, f3, f4, f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		// this prevents helmets from always facing south, and the armor "breathing" on
		// the stand
		if (entityIn instanceof EntityArmorStand) {
			EntityArmorStand entityarmorstand = (EntityArmorStand) entityIn;
			this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);

			this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();

			this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();

			this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();

			this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
			this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);

			this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
			this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
			copyModelAngles(this.bipedHead, this.bipedHeadwear);
		} else {
			super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		}
	}
}