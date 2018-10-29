package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

/**
 * Vampire's attire - Ingoleth
 * Created using Tabula 5.1.0
 */
public class ModelVampireArmor extends ModelBiped {
	public static final ModelVampireArmor INSTANCE = new ModelVampireArmor();
	public ModelRenderer hatAnchor;
	public ModelRenderer body;
	public ModelRenderer capeBack1;
	public ModelRenderer armRight;
	public ModelRenderer armLeft;
	public ModelRenderer legRight;
	public ModelRenderer legLeft;
	public ModelRenderer shoulderRight;
	public ModelRenderer shoulderLeft;
	public ModelRenderer capeCollarLeft1;
	public ModelRenderer capeCollarRight1;
	public ModelRenderer capeCollarBack1;
	public ModelRenderer hat;
	public ModelRenderer hatWing;
	public ModelRenderer tie;
	public ModelRenderer capeBack2;
	public ModelRenderer capeBackRight1;
	public ModelRenderer capeBackLeft1;
	public ModelRenderer capeBackRight2;
	public ModelRenderer capeRightSide;
	public ModelRenderer capeRightSide_1;
	public ModelRenderer capeRightFront;
	public ModelRenderer capeRightFront_1;
	public ModelRenderer capeBackLeft;
	public ModelRenderer capeLeftSide;
	public ModelRenderer capeLeftSide_1;
	public ModelRenderer capeLeftFront;
	public ModelRenderer capeLeftFront_1;
	public ModelRenderer capeCollarLeft2;
	public ModelRenderer capeCollarRight2;
	public ModelRenderer capeCollarBack2;

	public ModelVampireArmor() {
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.capeRightFront_1 = new ModelRenderer(this, 0, 89);
		this.capeRightFront_1.setRotationPoint(1.0F, 20.0F, -3.0F);
		this.capeRightFront_1.addBox(-3.0F, 0.0F, -1.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(capeRightFront_1, -0.17453292519943295F, 0.0F, 0.0F);
		this.legRight = new ModelRenderer(this, 71, 31);
		this.legRight.mirror = true;
		this.legRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.1F);
		this.shoulderRight = new ModelRenderer(this, 28, 56);
		this.shoulderRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shoulderRight.addBox(-1.5F, -2.5F, -3F, 5, 5, 6, 0.0F);
		ModelHelper.setRotateAngle(shoulderRight, 0.0F, 3.141592653589793F, -0.08726646259971647F);
		this.capeCollarBack1 = new ModelRenderer(this, 52, 60);
		this.capeCollarBack1.setRotationPoint(-4.0F, 3.0F, 3.0F);
		this.capeCollarBack1.addBox(0.0F, -6.0F, -1.0F, 8, 6, 1, 0.0F);
		this.setRotateAngle(capeCollarBack1, -0.4363323129985824F, 0.0F, 0.0F);
		this.capeRightFront = new ModelRenderer(this, 0, 67);
		this.capeRightFront.mirror = true;
		this.capeRightFront.setRotationPoint(-3.0F, 0.0F, -2.0F);
		this.capeRightFront.addBox(-2.0F, 0.0F, -4.0F, 5, 20, 1, 0.0F);
		this.capeLeftSide_1 = new ModelRenderer(this, 14, 68);
		this.capeLeftSide_1.mirror = true;
		this.capeLeftSide_1.setRotationPoint(1.0F, 20.0F, -1.0F);
		this.capeLeftSide_1.addBox(-1.0F, 0.0F, -4.0F, 1, 4, 5, 0.0F);
		this.setRotateAngle(capeLeftSide_1, 0.0F, 0.0F, 0.19198621771937624F);
		this.capeBackLeft1 = new ModelRenderer(this, 0, 39);
		this.capeBackLeft1.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.capeBackLeft1.addBox(-5.0F, 0.0F, 0.0F, 5, 20, 1, 0.0F);
		this.setRotateAngle(capeBackLeft1, -0.05235987755982988F, 0.0F, 0.0F);
		this.capeRightSide = new ModelRenderer(this, 14, 42);
		this.capeRightSide.setRotationPoint(5.0F, 0.0F, 0.0F);
		this.capeRightSide.addBox(-1.0F, 0.0F, -5.0F, 1, 20, 5, 0.0F);
		this.setRotateAngle(capeRightSide, 0.0F, 0.06981317007977318F, -0.06108652381F);
		this.capeBackLeft = new ModelRenderer(this, 0, 61);
		this.capeBackLeft.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.capeBackLeft.addBox(-5.0F, 0.0F, 0.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(capeBackLeft, 0.19198621771937624F, 0.0F, 0.0F);
		this.capeCollarRight2 = new ModelRenderer(this, 43, 70);
		this.capeCollarRight2.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.capeCollarRight2.addBox(0.0F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
		this.setRotateAngle(capeCollarRight2, 0.0F, 0.0F, 0.4363323129985824F);
		this.capeLeftFront_1 = new ModelRenderer(this, 0, 89);
		this.capeLeftFront_1.mirror = true;
		this.capeLeftFront_1.setRotationPoint(0.0F, 20.0F, -3.0F);
		this.capeLeftFront_1.addBox(-3.0F, 0.0F, -1.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(capeLeftFront_1, -0.17453292519943295F, 0.0F, 0.0F);
		this.tie = new ModelRenderer(this, 28, 37);
		this.tie.setRotationPoint(-1.5F, 0.0F, -3.0F);
		this.tie.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
		this.setRotateAngle(tie, 0.2617993877991494F, 0.0F, 0.0F);
		this.capeBackRight1 = new ModelRenderer(this, 0, 39);
		this.capeBackRight1.setRotationPoint(4.0F, 0.0F, 0.0F);
		this.capeBackRight1.addBox(0.0F, 0.0F, 0.0F, 5, 20, 1, 0.0F);
		this.setRotateAngle(capeBackRight1, -0.05235987755982988F, 0.0F, 0.0F);
		this.body = new ModelRenderer(this, 39, 31);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.1F);
		this.capeBack2 = new ModelRenderer(this, 1, 31);
		this.capeBack2.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.capeBack2.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 1, 0.0F);
		this.setRotateAngle(capeBack2, 0.17453292519943295F, 0.0F, 0.0F);
		this.capeRightSide_1 = new ModelRenderer(this, 14, 68);
		this.capeRightSide_1.setRotationPoint(-1.0F, 20.0F, -1.0F);
		this.capeRightSide_1.addBox(0.0F, 0.0F, -4.0F, 1, 4, 5, 0.0F);
		this.setRotateAngle(capeRightSide_1, 0.0F, 0.0F, -0.19198621771937624F);
		this.capeCollarLeft1 = new ModelRenderer(this, 30, 69);
		this.capeCollarLeft1.setRotationPoint(-3.0F, 0.0F, -2.0F);
		this.capeCollarLeft1.addBox(-1.0F, -3.0F, 0.0F, 1, 3, 5, 0.0F);
		this.setRotateAngle(capeCollarLeft1, 0.0F, 0.0F, -0.5235987755982988F);
		this.hat = new ModelRenderer(this, 32, 0);
		this.hat.setRotationPoint(0.0F, -6.0F, -4.0F);
		this.hat.addBox(-4.0F, -8.0F, 0.0F, 8, 8, 8, 0.1F);
		this.setRotateAngle(hat, -0.017453292519943295F, 0.0F, 0.0F);
		this.capeBackRight2 = new ModelRenderer(this, 0, 61);
		this.capeBackRight2.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.capeBackRight2.addBox(0.0F, 0.0F, 0.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(capeBackRight2, 0.19198621771937624F, 0.0F, 0.0F);
		this.capeCollarLeft2 = new ModelRenderer(this, 43, 70);
		this.capeCollarLeft2.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.capeCollarLeft2.addBox(-1.0F, -2.0F, 0.0F, 1, 2, 5, 0.0F);
		this.setRotateAngle(capeCollarLeft2, 0.0F, 0.0F, -0.4363323129985824F);
		this.shoulderLeft = new ModelRenderer(this, 28, 56);
		this.shoulderLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shoulderLeft.addBox(-1.5F, -2.5F, -3F, 5, 5, 6, 0.0F);
		ModelHelper.setRotateAngle(shoulderLeft, 0.0F, 0.0F, 0.08726646259971647F);
		this.capeCollarBack2 = new ModelRenderer(this, 52, 55);
		this.capeCollarBack2.setRotationPoint(0.0F, -6.0F, -1.0F);
		this.capeCollarBack2.addBox(0.0F, -3.0F, 0.0F, 8, 3, 1, 0.0F);
		this.setRotateAngle(capeCollarBack2, -0.4363323129985824F, 0.0F, 0.0F);
		this.legLeft = new ModelRenderer(this, 71, 31);
		this.legLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.1F);
		this.capeBack1 = new ModelRenderer(this, 1, 8);
		this.capeBack1.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.capeBack1.addBox(-4.0F, 0.0F, 0.0F, 8, 20, 1, 1.0F);
		this.setRotateAngle(capeBack1, 0.08726646259971647F, 0.0F, 0.0F);
		this.capeLeftFront = new ModelRenderer(this, 0, 67);
		this.capeLeftFront.setRotationPoint(3.0F, 0.0F, -2.0F);
		this.capeLeftFront.addBox(-3.0F, 0.0F, -4.0F, 5, 20, 1, 0.0F);
		this.hatAnchor = new ModelRenderer(this, 32, 0);
		this.hatAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hatAnchor.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0, 0.0F);
		this.armLeft = new ModelRenderer(this, 71, 13);
		this.armLeft.mirror = true;
		this.armLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.armLeft.addBox(-1.0F, -1.9F, -2.0F, 4, 12, 4, 0.2F);
		this.hatWing = new ModelRenderer(this, 20, 17);
		this.hatWing.setRotationPoint(0.0F, -0.9391108155250549F, 3.9696435928344727F);
		this.hatWing.addBox(-5.5F, 0.0F, -5.5F, 11, 1, 11, 0.0F);
		this.setRotateAngle(hatWing, 0.05227491439867795F, -0.0024344683732524883F, 0.03490658503988659F);
		this.capeLeftSide = new ModelRenderer(this, 14, 42);
		this.capeLeftSide.mirror = true;
		this.capeLeftSide.setRotationPoint(-5.0F, 0.0F, 0.0F);
		this.capeLeftSide.addBox(0.0F, 0.0F, -5.0F, 1, 20, 5, 0.0F);
		this.setRotateAngle(capeLeftSide, 0.0F, -0.06981317007977318F, 0.06108652381F);
		this.capeCollarRight1 = new ModelRenderer(this, 30, 69);
		this.capeCollarRight1.setRotationPoint(3.0F, 0.0F, -2.0F);
		this.capeCollarRight1.addBox(0.0F, -3.0F, 0.0F, 1, 3, 5, 0.0F);
		this.setRotateAngle(capeCollarRight1, 0.0F, 0.0F, 0.5235987755982988F);
		this.armRight = new ModelRenderer(this, 71, 13);
		this.armRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.armRight.addBox(-3.0F, -1.9F, -2.0F, 4, 12, 4, 0.2F);

		this.bipedHead = new ModelRenderer(this);
		this.bipedBody = new ModelRenderer(this);
		this.bipedHeadwear = new ModelRenderer(this);
		this.bipedLeftLeg = new ModelRenderer(this);
		this.bipedRightLeg = new ModelRenderer(this);

		this.capeRightFront.addChild(this.capeRightFront_1);
		this.capeRightSide.addChild(this.capeRightFront);
		this.capeLeftSide.addChild(this.capeLeftSide_1);
		this.capeBack1.addChild(this.capeBackLeft1);
		this.capeBackRight1.addChild(this.capeRightSide);
		this.capeBackLeft1.addChild(this.capeBackLeft);
		this.capeCollarRight1.addChild(this.capeCollarRight2);
		this.capeLeftFront.addChild(this.capeLeftFront_1);
		this.body.addChild(this.tie);
		this.capeBack1.addChild(this.capeBackRight1);
		this.capeBack1.addChild(this.capeBack2);
		this.capeRightSide.addChild(this.capeRightSide_1);
		this.hatAnchor.addChild(this.hat);
		this.capeBackRight1.addChild(this.capeBackRight2);
		this.capeCollarLeft1.addChild(this.capeCollarLeft2);
		this.capeCollarBack1.addChild(this.capeCollarBack2);
		this.capeLeftSide.addChild(this.capeLeftFront);
		this.hat.addChild(this.hatWing);
		this.capeBackLeft1.addChild(this.capeLeftSide);
		this.armLeft.addChild(shoulderLeft);
		this.armRight.addChild(this.shoulderRight);

		this.bipedBody.addChild(body);
		this.bipedBody.addChild(capeCollarBack1);
		this.bipedBody.addChild(capeCollarLeft1);
		this.bipedBody.addChild(capeCollarRight1);
		this.bipedLeftArm.addChild(armLeft);
		this.bipedRightArm.addChild(armRight);
		this.bipedBody.addChild(capeBack1);
		this.bipedHead.addChild(hatAnchor);
		this.bipedLeftLeg.addChild(legLeft);
		this.bipedRightLeg.addChild(legRight);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);

	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		// this prevents helmets from always facing south, and the armor "breathing" on the stand
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
			this.capeBack1.rotateAngleX = 0.26179938779F;
			this.capeRightFront.rotateAngleY = -1.57079632679F;
			this.capeLeftFront.rotateAngleY = 1.57079632679F;


			copyModelAngles(this.bipedHead, this.bipedHeadwear);
		} else {
			super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
			capeBack1.rotateAngleX = 0.08726646259971647F + limbSwingAmount;
			capeLeftFront.rotateAngleY = limbSwingAmount * 1.5F;
			capeRightFront.rotateAngleY = -capeLeftFront.rotateAngleY;
			//To do, make front bits rotate when an item is being held...

		}

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
