package com.bewitchment.client.model.entity.spirit.demon;

import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * demon2 - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class ModelDemon extends ModelBiped {
	public ModelRenderer bipedBody;
	public ModelRenderer tail01;
	public ModelRenderer lWing01;
	public ModelRenderer rWing01;
	public ModelRenderer bipedHead;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightArm;
	public ModelRenderer tail02;
	public ModelRenderer tail03;
	public ModelRenderer tail04;
	public ModelRenderer tail05;
	public ModelRenderer tailTip01;
	public ModelRenderer tailTip02;
	public ModelRenderer lWing02;
	public ModelRenderer lWing03;
	public ModelRenderer lWingMembrane;
	public ModelRenderer lWing04;
	public ModelRenderer rWing02;
	public ModelRenderer rWing03;
	public ModelRenderer rWingMembrane;
	public ModelRenderer rWing04;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer lHorn02;
	public ModelRenderer lHorn03a;
	public ModelRenderer lHorn03b;
	public ModelRenderer lHorn03c;
	public ModelRenderer lHorn03d;
	public ModelRenderer lHorn04;
	public ModelRenderer rHorn02;
	public ModelRenderer rHorn03a;
	public ModelRenderer rHorn03b;
	public ModelRenderer rHorn03c;
	public ModelRenderer rHorn03d;
	public ModelRenderer rHorn04;
	public ModelRenderer leftLeg01;
	public ModelRenderer leftLeg02;
	public ModelRenderer leftHoof;
	public ModelRenderer rightLeg01;
	public ModelRenderer rightLeg02;
	public ModelRenderer rightHoof;
	
	public ModelDemon() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.lHorn02 = new ModelRenderer(this, 32, 0);
		this.lHorn02.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn02, -0.10471975511965977F, 0.0F, -0.19198621771937624F);
		this.tail02 = new ModelRenderer(this, 13, 37);
		this.tail02.setRotationPoint(0.0F, 0.0F, 3.8F);
		this.tail02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 4, 0.0F);
		this.setRotateAngle(tail02, -0.13962634015954636F, 0.0F, 0.0F);
		this.lWingMembrane = new ModelRenderer(this, 41, 26);
		this.lWingMembrane.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lWingMembrane.addBox(0.0F, 0.4F, -2.2F, 0, 14, 11, 0.0F);
		this.setRotateAngle(lWingMembrane, -0.091106186954104F, 0.0F, 0.0F);
		this.lWing01 = new ModelRenderer(this, 26, 35);
		this.lWing01.setRotationPoint(2.5F, 3.2F, 1.4F);
		this.lWing01.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 5, 0.0F);
		this.setRotateAngle(lWing01, 0.27314402793711257F, 0.5235987755982988F, 0.0F);
		this.tail03 = new ModelRenderer(this, 15, 45);
		this.tail03.setRotationPoint(0.0F, 0.0F, 2.9F);
		this.tail03.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F);
		this.setRotateAngle(tail03, 0.06981317007977318F, 0.0F, 0.0F);
		this.rHorn03d = new ModelRenderer(this, 35, 5);
		this.rHorn03d.mirror = true;
		this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rWing01 = new ModelRenderer(this, 26, 35);
		this.rWing01.mirror = true;
		this.rWing01.setRotationPoint(-2.5F, 3.2F, 1.4F);
		this.rWing01.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 5, 0.0F);
		this.setRotateAngle(rWing01, 0.27314402793711257F, -0.5235987755982988F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 17);
		this.bipedLeftLeg.setRotationPoint(2.1F, 11.6F, 0.1F);
		this.bipedLeftLeg.addBox(-2.0F, -1.0F, -2.5F, 4, 8, 5, 0.0F);
		this.setRotateAngle(bipedLeftLeg, -0.2617993877991494F, 0.0F, -0.10471975511965977F);
		this.rWing02 = new ModelRenderer(this, 27, 44);
		this.rWing02.mirror = true;
		this.rWing02.setRotationPoint(-0.1F, 0.2F, 4.3F);
		this.rWing02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
		this.setRotateAngle(rWing02, 0.5235987755982988F, 0.0F, 0.0F);
		this.rWing04 = new ModelRenderer(this, 24, 55);
		this.rWing04.mirror = true;
		this.rWing04.setRotationPoint(0.0F, 7.7F, 0.0F);
		this.rWing04.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
		this.setRotateAngle(rWing04, -0.41887902047863906F, 0.0F, 0.0F);
		this.rHorn01 = new ModelRenderer(this, 32, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-2.9F, -7.4F, -1.3F);
		this.rHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn01, 0.10471975511965977F, 0.0F, -0.41887902047863906F);
		this.rHorn03b = new ModelRenderer(this, 35, 5);
		this.rHorn03b.mirror = true;
		this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.lHorn03d = new ModelRenderer(this, 35, 5);
		this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.lWing03 = new ModelRenderer(this, 29, 54);
		this.lWing03.setRotationPoint(0.1F, -0.5F, 7.1F);
		this.lWing03.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(lWing03, 0.20943951023931953F, 0.0F, 0.0F);
		this.tailTip02 = new ModelRenderer(this, 15, 58);
		this.tailTip02.setRotationPoint(0.0F, 0.1F, 0.8F);
		this.tailTip02.addBox(-1.5F, -0.5F, -0.5F, 2, 1, 2, 0.0F);
		this.setRotateAngle(tailTip02, 0.0F, 0.7853981633974483F, 0.0F);
		this.lHorn01 = new ModelRenderer(this, 32, 0);
		this.lHorn01.setRotationPoint(2.9F, -7.4F, -1.3F);
		this.lHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn01, 0.10471975511965977F, 0.0F, 0.41887902047863906F);
		this.tail04 = new ModelRenderer(this, 15, 45);
		this.tail04.setRotationPoint(0.0F, 0.0F, 4.9F);
		this.tail04.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F);
		this.setRotateAngle(tail04, 0.13962634015954636F, 0.0F, 0.0F);
		this.tail05 = new ModelRenderer(this, 15, 45);
		this.tail05.setRotationPoint(0.0F, 0.0F, 4.9F);
		this.tail05.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F);
		this.setRotateAngle(tail05, 0.22689280275926282F, 0.0F, 0.0F);
		this.rightLeg01 = new ModelRenderer(this, 0, 30);
		this.rightLeg01.mirror = true;
		this.rightLeg01.setRotationPoint(0.0F, 5.7F, -0.4F);
		this.rightLeg01.addBox(-1.5F, 0.0F, -2.0F, 3, 6, 4, 0.0F);
		this.setRotateAngle(rightLeg01, 0.6981317007977318F, 0.0F, -0.10471975511965977F);
		this.lWing02 = new ModelRenderer(this, 27, 44);
		this.lWing02.setRotationPoint(0.1F, 0.2F, 4.3F);
		this.lWing02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
		this.setRotateAngle(lWing02, 0.5235987755982988F, 0.0F, 0.0F);
		this.rWingMembrane = new ModelRenderer(this, 41, 26);
		this.rWingMembrane.mirror = true;
		this.rWingMembrane.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rWingMembrane.addBox(0.0F, 0.4F, -2.2F, 0, 14, 11, 0.0F);
		this.setRotateAngle(rWingMembrane, -0.091106186954104F, 0.0F, 0.0F);
		this.lHorn04 = new ModelRenderer(this, 35, 10);
		this.lHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn04, -0.13962634015954636F, 0.0F, -0.10471975511965977F);
		this.rightLeg02 = new ModelRenderer(this, 0, 41);
		this.rightLeg02.mirror = true;
		this.rightLeg02.setRotationPoint(0.0F, 5.2F, 0.2F);
		this.rightLeg02.addBox(-1.0F, 0.0F, -1.5F, 2, 7, 3, 0.0F);
		this.setRotateAngle(rightLeg02, -0.41887902047863906F, 0.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 17);
		this.bipedRightLeg.mirror = true;
		this.bipedRightLeg.setRotationPoint(-2.1F, 11.6F, 0.1F);
		this.bipedRightLeg.addBox(-2.0F, -1.0F, -2.5F, 4, 8, 5, 0.0F);
		this.setRotateAngle(bipedRightLeg, -0.2617993877991494F, 0.0F, 0.10471975511965977F);
		this.lHorn03b = new ModelRenderer(this, 35, 5);
		this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.rHorn02 = new ModelRenderer(this, 32, 0);
		this.rHorn02.mirror = true;
		this.rHorn02.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn02, -0.10471975511965977F, 0.0F, 0.19198621771937624F);
		this.rightHoof = new ModelRenderer(this, 0, 51);
		this.rightHoof.mirror = true;
		this.rightHoof.setRotationPoint(0.0F, 6.8F, 0.0F);
		this.rightHoof.addBox(-1.5F, 0.0F, -2.9F, 3, 2, 4, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 44, 16);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
		this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.10000736613927509F);
		this.rWing03 = new ModelRenderer(this, 29, 54);
		this.rWing03.mirror = true;
		this.rWing03.setRotationPoint(-0.1F, -0.5F, 7.1F);
		this.rWing03.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(rWing03, 0.20943951023931953F, 0.0F, 0.0F);
		this.rHorn04 = new ModelRenderer(this, 35, 10);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn04, -0.13962634015954636F, 0.0F, 0.10471975511965977F);
		this.leftHoof = new ModelRenderer(this, 0, 51);
		this.leftHoof.setRotationPoint(0.0F, 6.8F, 0.0F);
		this.leftHoof.addBox(-1.5F, 0.0F, -2.9F, 3, 2, 4, 0.0F);
		this.rHorn03a = new ModelRenderer(this, 35, 5);
		this.rHorn03a.mirror = true;
		this.rHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.rHorn03a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn03a, -0.13962634015954636F, 0.0F, 0.06981317007977318F);
		this.lHorn03a = new ModelRenderer(this, 35, 5);
		this.lHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.lHorn03a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn03a, -0.13962634015954636F, 0.0F, -0.06981317007977318F);
		this.bipedLeftArm = new ModelRenderer(this, 44, 16);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, -0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
		this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.10000736613927509F);
		this.rHorn03c = new ModelRenderer(this, 35, 5);
		this.rHorn03c.mirror = true;
		this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.tail01 = new ModelRenderer(this, 13, 37);
		this.tail01.setRotationPoint(0.0F, 11.2F, 1.3F);
		this.tail01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 4, 0.0F);
		this.setRotateAngle(tail01, -0.8726646259971648F, 0.0F, 0.0F);
		this.tailTip01 = new ModelRenderer(this, 16, 53);
		this.tailTip01.setRotationPoint(0.0F, 0.0F, 4.5F);
		this.tailTip01.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 2, 0.0F);
		this.setRotateAngle(tailTip01, 0.2617993877991494F, 0.0F, 0.0F);
		this.lWing04 = new ModelRenderer(this, 24, 55);
		this.lWing04.setRotationPoint(0.0F, 7.7F, 0.0F);
		this.lWing04.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
		this.setRotateAngle(lWing04, -0.41887902047863906F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 19, 17);
		this.bipedBody.setRotationPoint(0.0F, -6.4F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 13, 4, 0.0F);
		this.leftLeg02 = new ModelRenderer(this, 0, 41);
		this.leftLeg02.setRotationPoint(0.0F, 5.2F, 0.2F);
		this.leftLeg02.addBox(-1.0F, 0.0F, -1.5F, 2, 7, 3, 0.0F);
		this.setRotateAngle(leftLeg02, -0.41887902047863906F, 0.0F, 0.0F);
		this.lHorn03c = new ModelRenderer(this, 35, 5);
		this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.leftLeg01 = new ModelRenderer(this, 0, 30);
		this.leftLeg01.setRotationPoint(0.0F, 5.7F, -0.4F);
		this.leftLeg01.addBox(-1.5F, 0.0F, -2.0F, 3, 6, 4, 0.0F);
		this.setRotateAngle(leftLeg01, 0.6981317007977318F, 0.0F, 0.10471975511965977F);
		this.lHorn01.addChild(this.lHorn02);
		this.tail01.addChild(this.tail02);
		this.lWing02.addChild(this.lWingMembrane);
		this.bipedBody.addChild(this.lWing01);
		this.tail02.addChild(this.tail03);
		this.rHorn03a.addChild(this.rHorn03d);
		this.bipedBody.addChild(this.bipedHead);
		this.bipedBody.addChild(this.rWing01);
		this.bipedBody.addChild(this.bipedLeftLeg);
		this.rWing01.addChild(this.rWing02);
		this.rWing03.addChild(this.rWing04);
		this.bipedHead.addChild(this.rHorn01);
		this.rHorn03a.addChild(this.rHorn03b);
		this.lHorn03a.addChild(this.lHorn03d);
		this.lWing02.addChild(this.lWing03);
		this.tailTip01.addChild(this.tailTip02);
		this.bipedHead.addChild(this.lHorn01);
		this.tail03.addChild(this.tail04);
		this.tail04.addChild(this.tail05);
		this.bipedRightLeg.addChild(this.rightLeg01);
		this.lWing01.addChild(this.lWing02);
		this.rWing02.addChild(this.rWingMembrane);
		this.lHorn03a.addChild(this.lHorn04);
		this.rightLeg01.addChild(this.rightLeg02);
		this.bipedBody.addChild(this.bipedRightLeg);
		this.lHorn03a.addChild(this.lHorn03b);
		this.rHorn01.addChild(this.rHorn02);
		this.rightLeg02.addChild(this.rightHoof);
		this.bipedBody.addChild(this.bipedRightArm);
		this.rWing02.addChild(this.rWing03);
		this.rHorn03a.addChild(this.rHorn04);
		this.leftLeg02.addChild(this.leftHoof);
		this.rHorn02.addChild(this.rHorn03a);
		this.lHorn02.addChild(this.lHorn03a);
		this.bipedBody.addChild(this.bipedLeftArm);
		this.rHorn03a.addChild(this.rHorn03c);
		this.bipedBody.addChild(this.tail01);
		this.tail05.addChild(this.tailTip01);
		this.lWing03.addChild(this.lWing04);
		this.leftLeg01.addChild(this.leftLeg02);
		this.lHorn03a.addChild(this.lHorn03c);
		this.bipedLeftLeg.addChild(this.leftLeg01);
	}
	
	private static float triangleWave(float x, float y) {
		return (Math.abs(x % y - y * 0.5f) - y * 0.25f) / (y * 0.25f);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.bipedBody.render(scale);
	}
	
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		float add = entity.getUniqueID().hashCode() * 0.003F;
		float mul = 0.1F;
		float div = 40F;
		boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
		
		if (flag) {
			this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
		}
		else {
			this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		}
		float swingMod = 0.3F;
		this.bipedLeftLeg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount - 0.26F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount - 0.26F;
		
		this.bipedRightArm.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount;
		
		tail01.rotateAngleY = MathHelper.sin(limbSwing * 0.25f) * 0.65F * limbSwingAmount + 0f;
		this.tail01.rotateAngleZ = (float) Math.cos(ageInTicks * (mul + 0.06F) + add) / div + 0F;
		this.lWing01.rotateAngleZ = (float) Math.cos(ageInTicks * (mul + 0.06F) + add) / div + 0F;
		this.rWing01.rotateAngleZ = -(float) Math.cos(ageInTicks * (mul + 0.06F) + add) / div + 0F;
		setLivingAnimations((EntityLivingBase) entity, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
	}
	
	public void setLivingAnimations(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTickTime) {
		EntityDemon demon = (EntityDemon) living;
		int i = demon.attackTimer;
		if (i > 0) {
			bipedRightArm.rotateAngleX = -2 + 1.5f * triangleWave((float) i - partialTickTime, 10);
			bipedLeftArm.rotateAngleX = -2 + 1.5f * triangleWave((float) i - partialTickTime, 10);
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