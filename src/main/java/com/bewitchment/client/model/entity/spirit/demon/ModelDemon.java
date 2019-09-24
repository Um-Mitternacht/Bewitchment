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
	public final ModelRenderer body;
	public final ModelRenderer tail00;
	public final ModelRenderer leftWing00;
	public final ModelRenderer rightWing00;
	public final ModelRenderer bipedHead;
	public final ModelRenderer bipedLeftArm;
	public final ModelRenderer bipedRightArm;
	public final ModelRenderer bipedLeftLeg;
	public final ModelRenderer bipedRightLeg;
	public final ModelRenderer tail01;
	public final ModelRenderer tail02;
	public final ModelRenderer tail03;
	public final ModelRenderer tail04;
	public final ModelRenderer tail05;
	public final ModelRenderer tail06;
	public final ModelRenderer leftWing01;
	public final ModelRenderer leftWing02;
	public final ModelRenderer leftWingMembrane;
	public final ModelRenderer leftWing03;
	public final ModelRenderer rightWing01;
	public final ModelRenderer rightWing02;
	public final ModelRenderer rightWingMembrane;
	public final ModelRenderer rightWing03;
	public final ModelRenderer leftHorn00;
	public final ModelRenderer rightHorn00;
	public final ModelRenderer leftHorn01;
	public final ModelRenderer leftHorn02a;
	public final ModelRenderer leftHorn02b;
	public final ModelRenderer leftHorn02c;
	public final ModelRenderer leftHorn02d;
	public final ModelRenderer leftHorn03;
	public final ModelRenderer rightHorn01;
	public final ModelRenderer rightHorn02a;
	public final ModelRenderer rightHorn02b;
	public final ModelRenderer rightHorn02c;
	public final ModelRenderer rightHorn02d;
	public final ModelRenderer rightHorn03;
	public final ModelRenderer leftLeg01;
	public final ModelRenderer leftLeg02;
	public final ModelRenderer leftHoof;
	public final ModelRenderer rightLeg01;
	public final ModelRenderer rightLeg02;
	public final ModelRenderer rightHoof;
	
	public ModelDemon() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.leftWing00 = new ModelRenderer(this, 26, 35);
		this.leftWing00.setRotationPoint(2.5f, 3.2f, 1.4f);
		this.leftWing00.addBox(-1, -1.5f, 0, 2, 3, 5, 0);
		this.setRotateAngle(leftWing00, 0.27f, 0.52f, 0);
		this.tail01 = new ModelRenderer(this, 13, 37);
		this.tail01.setRotationPoint(0, 0, 3.8f);
		this.tail01.addBox(-1, -1, 0, 2, 2, 4, 0);
		this.setRotateAngle(tail01, -0.14f, 0, 0);
		this.tail03 = new ModelRenderer(this, 15, 45);
		this.tail03.setRotationPoint(0, 0, 4.9f);
		this.tail03.addBox(-0.5f, -0.5f, 0, 1, 1, 5, 0);
		this.setRotateAngle(tail03, 0.14f, 0, 0);
		this.rightHorn03 = new ModelRenderer(this, 35, 10);
		this.rightHorn03.mirror = true;
		this.rightHorn03.setRotationPoint(0, -1.7f, 0);
		this.rightHorn03.addBox(-0.5f, -3, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(rightHorn03, -0.14f, 0, 0.1f);
		this.rightLeg02 = new ModelRenderer(this, 0, 41);
		this.rightLeg02.mirror = true;
		this.rightLeg02.setRotationPoint(0, 5.2f, 0.2f);
		this.rightLeg02.addBox(-1, 0, -1.5f, 2, 7, 3, 0);
		this.setRotateAngle(rightLeg02, -0.42f, 0, 0);
		this.rightWing02 = new ModelRenderer(this, 29, 54);
		this.rightWing02.mirror = true;
		this.rightWing02.setRotationPoint(-0.1f, -0.5f, 7.1f);
		this.rightWing02.addBox(-1, 0, -1, 2, 8, 2, 0);
		this.setRotateAngle(rightWing02, 0.21f, 0, 0);
		this.leftWing01 = new ModelRenderer(this, 27, 44);
		this.leftWing01.setRotationPoint(0.1f, 0.2f, 4.3f);
		this.leftWing01.addBox(-1, -1, 0, 2, 2, 8, 0);
		this.setRotateAngle(leftWing01, 0.52f, 0, 0);
		this.leftHorn02d = new ModelRenderer(this, 35, 5);
		this.leftHorn02d.setRotationPoint(0, 0, 0);
		this.leftHorn02d.addBox(-0.2f, -2, -0.2f, 1, 2, 1, 0);
		this.rightWing00 = new ModelRenderer(this, 26, 35);
		this.rightWing00.mirror = true;
		this.rightWing00.setRotationPoint(-2.5f, 3.2f, 1.4f);
		this.rightWing00.addBox(-1, -1.5f, 0, 2, 3, 5, 0);
		this.setRotateAngle(rightWing00, 0.27f, -0.52f, 0);
		this.leftHorn02b = new ModelRenderer(this, 35, 5);
		this.leftHorn02b.setRotationPoint(0, 0, 0);
		this.leftHorn02b.addBox(-0.2f, -2, -0.8f, 1, 2, 1, 0);
		this.rightWing01 = new ModelRenderer(this, 27, 44);
		this.rightWing01.mirror = true;
		this.rightWing01.setRotationPoint(-0.1f, 0.2f, 4.3f);
		this.rightWing01.addBox(-1, -1, 0, 2, 2, 8, 0);
		this.setRotateAngle(rightWing01, 0.52f, 0, 0);
		this.rightHorn02d = new ModelRenderer(this, 35, 5);
		this.rightHorn02d.mirror = true;
		this.rightHorn02d.setRotationPoint(0, 0, 0);
		this.rightHorn02d.addBox(-0.2f, -2, -0.2f, 1, 2, 1, 0);
		this.rightHorn00 = new ModelRenderer(this, 32, 0);
		this.rightHorn00.mirror = true;
		this.rightHorn00.setRotationPoint(-2.9f, -7.4f, -1.3f);
		this.rightHorn00.addBox(-1, -2, -1, 2, 2, 2, 0);
		this.setRotateAngle(rightHorn00, 0.1f, 0, -0.42f);
		this.tail02 = new ModelRenderer(this, 15, 45);
		this.tail02.setRotationPoint(0, 0, 2.9f);
		this.tail02.addBox(-0.5f, -0.5f, 0, 1, 1, 5, 0);
		this.setRotateAngle(tail02, 0.07f, 0, 0);
		this.leftWing02 = new ModelRenderer(this, 29, 54);
		this.leftWing02.setRotationPoint(0.1f, -0.5f, 7.1f);
		this.leftWing02.addBox(-1, 0, -1, 2, 8, 2, 0);
		this.setRotateAngle(leftWing02, 0.2f, 0, 0);
		this.tail00 = new ModelRenderer(this, 13, 37);
		this.tail00.setRotationPoint(0, 11.4f, 1.3f);
		this.tail00.addBox(-1, -1, 0, 2, 2, 4, 0);
		this.setRotateAngle(tail00, -0.87f, 0, 0);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 17);
		this.bipedLeftLeg.setRotationPoint(2.1f, 12.2f, 0.1f);
		this.bipedLeftLeg.addBox(-2, -1, -2.5f, 4, 8, 5, 0);
		this.setRotateAngle(bipedLeftLeg, -0.26f, 0, -0.1f);
		this.leftHorn02c = new ModelRenderer(this, 35, 5);
		this.leftHorn02c.setRotationPoint(0, 0, 0);
		this.leftHorn02c.addBox(-0.8f, -2, -0.2f, 1, 2, 1, 0);
		this.tail06 = new ModelRenderer(this, 15, 58);
		this.tail06.setRotationPoint(0, 0.1f, 0.8f);
		this.tail06.addBox(-1.5f, -0.5f, -0.5f, 2, 1, 2, 0);
		this.setRotateAngle(tail06, 0, 0.79f, 0);
		this.bipedLeftArm = new ModelRenderer(this, 44, 16);
		this.bipedLeftArm.setRotationPoint(5, 2, -0);
		this.bipedLeftArm.addBox(-1, -2, -2, 4, 14, 4, 0);
		this.setRotateAngle(bipedLeftArm, 0, 0, -0.1f);
		this.leftWing03 = new ModelRenderer(this, 24, 55);
		this.leftWing03.setRotationPoint(0, 7.7f, 0);
		this.leftWing03.addBox(-0.5f, 0, -0.5f, 1, 8, 1, 0);
		this.setRotateAngle(leftWing03, -0.42f, 0, 0);
		this.leftHorn01 = new ModelRenderer(this, 32, 0);
		this.leftHorn01.setRotationPoint(0, -1.7f, 0);
		this.leftHorn01.addBox(-1, -2, -1, 2, 2, 2, 0);
		this.setRotateAngle(leftHorn01, -0.1f, 0, -0.19f);
		this.rightHoof = new ModelRenderer(this, 0, 53);
		this.rightHoof.mirror = true;
		this.rightHoof.setRotationPoint(0, 6.8f, 0);
		this.rightHoof.addBox(-1.5f, 0, -2.9f, 3, 1, 4, 0);
		this.leftWingMembrane = new ModelRenderer(this, 41, 26);
		this.leftWingMembrane.setRotationPoint(0, 0, 0);
		this.leftWingMembrane.addBox(0, 0.4f, -2.2f, 0, 14, 11, 0);
		this.setRotateAngle(leftWingMembrane, -0.087f, 0, 0);
		this.leftLeg01 = new ModelRenderer(this, 0, 30);
		this.leftLeg01.setRotationPoint(0, 5.7f, -0.4f);
		this.leftLeg01.addBox(-1.5f, 0, -2, 3, 6, 4, 0);
		this.setRotateAngle(leftLeg01, 0.67f, 0, 0.1f);
		this.leftHorn02a = new ModelRenderer(this, 35, 5);
		this.leftHorn02a.setRotationPoint(0, -1.6f, 0);
		this.leftHorn02a.addBox(-0.8f, -2, -0.8f, 1, 2, 1, 0);
		this.setRotateAngle(leftHorn02a, -0.14f, 0, -0.07f);
		this.rightWingMembrane = new ModelRenderer(this, 41, 26);
		this.rightWingMembrane.mirror = true;
		this.rightWingMembrane.setRotationPoint(0, 0, 0);
		this.rightWingMembrane.addBox(0, 0.4f, -2.2f, 0, 14, 11, 0);
		this.setRotateAngle(rightWingMembrane, -0.087f, 0, 0);
		this.rightHorn02c = new ModelRenderer(this, 35, 5);
		this.rightHorn02c.mirror = true;
		this.rightHorn02c.setRotationPoint(0, 0, 0);
		this.rightHorn02c.addBox(-0.8f, -2, -0.2f, 1, 2, 1, 0);
		this.leftLeg02 = new ModelRenderer(this, 0, 41);
		this.leftLeg02.setRotationPoint(0, 5.2f, 0.2f);
		this.leftLeg02.addBox(-1, 0, -1.5f, 2, 7, 3, 0);
		this.setRotateAngle(leftLeg02, -0.42f, 0, 0);
		this.leftHoof = new ModelRenderer(this, 0, 53);
		this.leftHoof.setRotationPoint(0, 6.8f, 0);
		this.leftHoof.addBox(-1.5f, 0, -2.9f, 3, 1, 4, 0);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0, 0, 0);
		this.bipedHead.addBox(-4, -8, -4, 8, 8, 8, 0);
		this.bipedRightLeg = new ModelRenderer(this, 0, 17);
		this.bipedRightLeg.mirror = true;
		this.bipedRightLeg.setRotationPoint(-2.1f, 12.2f, 0.1f);
		this.bipedRightLeg.addBox(-2, -1, -2.5f, 4, 8, 5, 0);
		this.setRotateAngle(bipedRightLeg, -0.26f, 0, 0.1f);
		this.body = new ModelRenderer(this, 19, 17);
		this.body.setRotationPoint(0, -6, 0);
		this.body.addBox(-4, 0, -2, 8, 13, 4, 0);
		this.rightWing03 = new ModelRenderer(this, 24, 55);
		this.rightWing03.mirror = true;
		this.rightWing03.setRotationPoint(0, 7.7f, 0);
		this.rightWing03.addBox(-0.5f, 0, -0.5f, 1, 8, 1, 0);
		this.setRotateAngle(rightWing03, -0.42f, 0, 0);
		this.tail05 = new ModelRenderer(this, 16, 53);
		this.tail05.setRotationPoint(0, 0, 4.5f);
		this.tail05.addBox(-1, -0.5f, 0, 2, 1, 2, 0);
		this.setRotateAngle(tail05, 0.26f, 0, 0);
		this.rightHorn02a = new ModelRenderer(this, 35, 5);
		this.rightHorn02a.mirror = true;
		this.rightHorn02a.setRotationPoint(0, -1.6f, 0);
		this.rightHorn02a.addBox(-0.8f, -2, -0.8f, 1, 2, 1, 0);
		this.setRotateAngle(rightHorn02a, -0.14f, 0, 0.07f);
		this.tail04 = new ModelRenderer(this, 15, 45);
		this.tail04.setRotationPoint(0, 0, 4.9f);
		this.tail04.addBox(-0.5f, -0.5f, 0, 1, 1, 5, 0);
		this.setRotateAngle(tail04, 0.23f, 0, 0);
		this.leftHorn00 = new ModelRenderer(this, 32, 0);
		this.leftHorn00.setRotationPoint(2.9f, -7.4f, -1.3f);
		this.leftHorn00.addBox(-1, -2, -1, 2, 2, 2, 0);
		this.setRotateAngle(leftHorn00, 0.1f, 0, 0.42f);
		this.bipedRightArm = new ModelRenderer(this, 44, 16);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-5, 2, 0);
		this.bipedRightArm.addBox(-3, -2, -2, 4, 14, 4, 0);
		this.setRotateAngle(bipedRightArm, 0, 0, 0.1f);
		this.rightHorn01 = new ModelRenderer(this, 32, 0);
		this.rightHorn01.mirror = true;
		this.rightHorn01.setRotationPoint(0, -1.7f, 0);
		this.rightHorn01.addBox(-1, -2, -1, 2, 2, 2, 0);
		this.setRotateAngle(rightHorn01, -0.1f, 0, 0.19f);
		this.rightHorn02b = new ModelRenderer(this, 35, 5);
		this.rightHorn02b.mirror = true;
		this.rightHorn02b.setRotationPoint(0, 0, 0);
		this.rightHorn02b.addBox(-0.2f, -2, -0.8f, 1, 2, 1, 0);
		this.rightLeg01 = new ModelRenderer(this, 0, 30);
		this.rightLeg01.mirror = true;
		this.rightLeg01.setRotationPoint(0, 5.7f, -0.4f);
		this.rightLeg01.addBox(-1.5f, 0, -2, 3, 6, 4, 0);
		this.setRotateAngle(rightLeg01, 0.7f, 0, -0.1f);
		this.leftHorn03 = new ModelRenderer(this, 35, 10);
		this.leftHorn03.setRotationPoint(0, -1.7f, 0);
		this.leftHorn03.addBox(-0.5f, -3, -0.5f, 1, 3, 1, 0);
		this.setRotateAngle(leftHorn03, -0.14f, 0, -0.1f);
		this.body.addChild(this.leftWing00);
		this.tail00.addChild(this.tail01);
		this.tail02.addChild(this.tail03);
		this.rightHorn02a.addChild(this.rightHorn03);
		this.rightLeg01.addChild(this.rightLeg02);
		this.rightWing01.addChild(this.rightWing02);
		this.leftWing00.addChild(this.leftWing01);
		this.leftHorn02a.addChild(this.leftHorn02d);
		this.body.addChild(this.rightWing00);
		this.leftHorn02a.addChild(this.leftHorn02b);
		this.rightWing00.addChild(this.rightWing01);
		this.rightHorn02a.addChild(this.rightHorn02d);
		this.bipedHead.addChild(this.rightHorn00);
		this.tail01.addChild(this.tail02);
		this.leftWing01.addChild(this.leftWing02);
		this.body.addChild(this.tail00);
		this.body.addChild(this.bipedLeftLeg);
		this.leftHorn02a.addChild(this.leftHorn02c);
		this.tail05.addChild(this.tail06);
		this.body.addChild(this.bipedLeftArm);
		this.leftWing02.addChild(this.leftWing03);
		this.leftHorn00.addChild(this.leftHorn01);
		this.rightLeg02.addChild(this.rightHoof);
		this.leftWing01.addChild(this.leftWingMembrane);
		this.bipedLeftLeg.addChild(this.leftLeg01);
		this.leftHorn01.addChild(this.leftHorn02a);
		this.rightWing01.addChild(this.rightWingMembrane);
		this.rightHorn02a.addChild(this.rightHorn02c);
		this.leftLeg01.addChild(this.leftLeg02);
		this.leftLeg02.addChild(this.leftHoof);
		this.body.addChild(this.bipedHead);
		this.body.addChild(this.bipedRightLeg);
		this.rightWing02.addChild(this.rightWing03);
		this.tail04.addChild(this.tail05);
		this.rightHorn01.addChild(this.rightHorn02a);
		this.tail03.addChild(this.tail04);
		this.bipedHead.addChild(this.leftHorn00);
		this.body.addChild(this.bipedRightArm);
		this.rightHorn00.addChild(this.rightHorn01);
		this.rightHorn02a.addChild(this.rightHorn02b);
		this.bipedRightLeg.addChild(this.rightLeg01);
		this.leftHorn02a.addChild(this.leftHorn03);
	}
	
	private static float triangleWave(float x, float y) {
		return (Math.abs(x % y - y * 0.5f) - y * 0.25f) / (y * 0.25f);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.body.render(scale);
	}
	
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		
		boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
		
		if (flag) {
			this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
		}
		else {
			this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		}
		float swingMod = 0.6F;
		this.bipedLeftLeg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount - 0.26F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount - 0.26F;
		
		this.bipedRightArm.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount;
		
		tail00.rotateAngleY = MathHelper.sin(limbSwing * 0.25f) * 0.65F * limbSwingAmount + 0f;
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