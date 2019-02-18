package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

/**
 * WitchsHood - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelWitchsHood extends ModelBiped {
	public ModelRenderer hood01;
	public ModelRenderer hoodFringe01;
	public ModelRenderer hoodFringeL01;
	public ModelRenderer hoodFringeR01;
	public ModelRenderer hoodFringeL02;
	public ModelRenderer hoodFringeR02;
	public ModelRenderer hoodFringeL03;
	public ModelRenderer hoodFringeR03;
	public ModelRenderer hoodShade;
	public ModelRenderer hoodFringeL04;
	public ModelRenderer hoodFringeR04;
	public ModelRenderer hoodPipe01;
	public ModelRenderer hoodPipe02;

	public ModelWitchsHood() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.hood01 = new ModelRenderer(this, 70, 0);
		this.hood01.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hood01.addBox(-4.5F, -9.0F, -4.7F, 9, 9, 10, 0.0F);

		this.hoodShade = new ModelRenderer(this, 105, 0);
		this.hoodShade.setRotationPoint(0.0F, -7.3F, -5.2F);
		this.hoodShade.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 0, 0.0F);
		this.setRotateAngle(hoodShade, 0.05235987755982988F, 0.0F, 0.0F);

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);

		this.hoodPipe02 = new ModelRenderer(this, 65, 54);
		this.hoodPipe02.setRotationPoint(0.0F, -0.3F, 3.1F);
		this.hoodPipe02.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(hoodPipe02, -0.45378560551852565F, 0.0F, 0.0F);

		this.hoodFringeL01 = new ModelRenderer(this, 77, 24);
		this.hoodFringeL01.setRotationPoint(1.9F, -8.2F, 0.1F);
		this.hoodFringeL01.addBox(0.0F, -0.3F, -5.39F, 2, 8, 10, 0.0F);
		this.setRotateAngle(hoodFringeL01, 0.0F, 0.0F, -0.2792526803190927F);

		this.hoodFringeL02 = new ModelRenderer(this, 97, 19);
		this.hoodFringeL02.setRotationPoint(4.85F, -1.2F, 0.1F);
		this.hoodFringeL02.addBox(0.0F, -0.5F, -5.38F, 1, 2, 10, 0.0F);
		this.setRotateAngle(hoodFringeL02, 0.0F, 0.0F, 0.20943951023931953F);

		this.hoodFringeR02 = new ModelRenderer(this, 97, 19);
		this.hoodFringeR02.mirror = true;
		this.hoodFringeR02.setRotationPoint(-4.85F, -1.2F, 0.1F);
		this.hoodFringeR02.addBox(-1.0F, -0.5F, -5.38F, 1, 2, 10, 0.0F);
		this.setRotateAngle(hoodFringeR02, 0.0F, 0.0F, -0.20943951023931953F);

		this.hoodFringeL03 = new ModelRenderer(this, 97, 33);
		this.hoodFringeL03.setRotationPoint(5.15F, -0.7F, 0.1F);
		this.hoodFringeL03.addBox(-5.0F, -0.9F, -5.37F, 5, 2, 10, 0.0F);
		this.setRotateAngle(hoodFringeL03, 0.0F, 0.0F, -0.3141592653589793F);

		this.hoodPipe01 = new ModelRenderer(this, 65, 43);
		this.hoodPipe01.setRotationPoint(0.0F, -7.0F, 4.2F);
		this.hoodPipe01.addBox(-3.0F, -2.5F, 0.0F, 6, 6, 4, 0.0F);
		this.setRotateAngle(hoodPipe01, -0.40142572795869574F, 0.0F, 0.0F);

		this.hoodFringe01 = new ModelRenderer(this, 66, 0);
		this.hoodFringe01.setRotationPoint(0.0F, 0.0F, 0.1F);
		this.hoodFringe01.addBox(-3.0F, -8.9F, -5.4F, 6, 2, 1, 0.0F);

		this.hoodFringeR03 = new ModelRenderer(this, 97, 33);
		this.hoodFringeR03.mirror = true;
		this.hoodFringeR03.setRotationPoint(-5.15F, -0.7F, 0.1F);
		this.hoodFringeR03.addBox(0.0F, -0.9F, -5.37F, 5, 2, 10, 0.0F);
		this.setRotateAngle(hoodFringeR03, 0.0F, 0.0F, 0.3141592653589793F);

		this.hoodFringeR04 = new ModelRenderer(this, 57, 20);
		this.hoodFringeR04.mirror = true;
		this.hoodFringeR04.setRotationPoint(-2.7F, -8.4F, 0.1F);
		this.hoodFringeR04.addBox(-0.9F, -1.0F, -5.38F, 4, 2, 11, 0.0F);
		this.setRotateAngle(hoodFringeR04, 0.0F, 0.0F, -0.3141592653589793F);

		this.hoodFringeR01 = new ModelRenderer(this, 77, 24);
		this.hoodFringeR01.mirror = true;
		this.hoodFringeR01.setRotationPoint(-1.9F, -8.2F, 0.1F);
		this.hoodFringeR01.addBox(-2.0F, -0.3F, -5.39F, 2, 8, 10, 0.0F);
		this.setRotateAngle(hoodFringeR01, 0.0F, 0.0F, 0.2792526803190927F);

		this.hoodFringeL04 = new ModelRenderer(this, 57, 20);
		this.hoodFringeL04.setRotationPoint(2.7F, -8.4F, 0.1F);
		this.hoodFringeL04.addBox(-3.2F, -1.0F, -5.38F, 4, 2, 11, 0.0F);
		this.setRotateAngle(hoodFringeL04, 0.0F, 0.0F, 0.3141592653589793F);

		this.bipedHead.addChild(this.hood01);

		this.hood01.addChild(this.hoodShade);
		this.hoodPipe01.addChild(this.hoodPipe02);
		this.hood01.addChild(this.hoodFringeL01);
		this.hood01.addChild(this.hoodFringeL02);
		this.hood01.addChild(this.hoodFringeR02);
		this.hood01.addChild(this.hoodFringeL03);
		this.hood01.addChild(this.hoodPipe01);
		this.hood01.addChild(this.hoodFringe01);
		this.hood01.addChild(this.hoodFringeR03);
		this.hood01.addChild(this.hoodFringeR04);
		this.hood01.addChild(this.hoodFringeR01);
		this.hood01.addChild(this.hoodFringeL04);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
	                              float headPitch, float scaleFactor, Entity entity) {
		if (entity instanceof EntityArmorStand) {
			EntityArmorStand entityarmorstand = (EntityArmorStand) entity;
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
			super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
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
