package com.bewitchment.client.model.entity.spirit.demon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * cambion - cybecat5555
 * Created using Tabula 7.0.1
 */
public class ModelCambion extends ModelBiped {
	public ModelRenderer bipedBody;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedHead;
	public ModelRenderer lClaws;
	public ModelRenderer rClaws;
	public ModelRenderer snout;
	public ModelRenderer upperJaw;
	public ModelRenderer lowerJaw;
	public ModelRenderer lUpperHorn01;
	public ModelRenderer rUpperHorn01;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer hair01;
	public ModelRenderer hair00;
	public ModelRenderer cleft;
	public ModelRenderer lUpperHorn02a;
	public ModelRenderer lUpperHorn02b;
	public ModelRenderer lUpperHorn02c;
	public ModelRenderer lUpperHorn02d;
	public ModelRenderer lUpperHorn03;
	public ModelRenderer rUpperHorn02a;
	public ModelRenderer rUpperHorn02b;
	public ModelRenderer rUpperHorn02c;
	public ModelRenderer rUpperHorn02d;
	public ModelRenderer rUpperHorn03;
	public ModelRenderer lHorn02a;
	public ModelRenderer lHorn02b;
	public ModelRenderer lHorn02c;
	public ModelRenderer lHorn03d;
	public ModelRenderer lHorn03a;
	public ModelRenderer lHorn03b;
	public ModelRenderer lHorn03c;
	public ModelRenderer lHorn03d_1;
	public ModelRenderer lHorn04;
	public ModelRenderer lHorn05;
	public ModelRenderer rHorn02a;
	public ModelRenderer rHorn02b;
	public ModelRenderer rHorn02c;
	public ModelRenderer rHorn03d;
	public ModelRenderer rHorn03a;
	public ModelRenderer rHorn03b;
	public ModelRenderer rHorn03c;
	public ModelRenderer rHorn03d_1;
	public ModelRenderer rHorn04;
	public ModelRenderer rHorn05;
	
	public ModelCambion() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.rHorn02a = new ModelRenderer(this, 0, 4);
		this.rHorn02a.mirror = true;
		this.rHorn02a.setRotationPoint(0.1F, -1.5F, -0.1F);
		this.rHorn02a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn02a, -0.2617993877991494F, 0.0F, -0.40142572795869574F);
		this.hair00 = new ModelRenderer(this, 38, 46);
		this.hair00.setRotationPoint(0.0F, -5.5F, 3.2F);
		this.hair00.addBox(-3.99F, 0.0F, -1.0F, 8, 11, 2, 0.0F);
		this.setRotateAngle(hair00, 0.13962634015954636F, 0.0F, 0.0F);
		this.lHorn03b = new ModelRenderer(this, 0, 4);
		this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03b.addBox(-0.3F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
		this.rHorn05 = new ModelRenderer(this, 43, 0);
		this.rHorn05.mirror = true;
		this.rHorn05.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.rHorn05.addBox(-0.5F, -2.1F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn05, 0.05235987755982988F, 0.0F, 0.3141592653589793F);
		this.lHorn05 = new ModelRenderer(this, 43, 0);
		this.lHorn05.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.lHorn05.addBox(-0.5F, -2.1F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn05, 0.05235987755982988F, 0.0F, -0.3141592653589793F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.snout = new ModelRenderer(this, 21, 35);
		this.snout.setRotationPoint(0.0F, -2.5F, -3.9F);
		this.snout.addBox(-1.5F, -1.0F, -2.0F, 3, 2, 3, 0.0F);
		this.setRotateAngle(snout, 0.41887902047863906F, 0.0F, 0.0F);
		this.lClaws = new ModelRenderer(this, 0, 34);
		this.lClaws.setRotationPoint(2.4F, 11.5F, 0.0F);
		this.lClaws.addBox(-1.1F, 0.0F, -2.1F, 2, 3, 4, 0.0F);
		this.setRotateAngle(lClaws, 0.0F, 0.0F, 0.2792526803190927F);
		this.rHorn02b = new ModelRenderer(this, 0, 4);
		this.rHorn02b.mirror = true;
		this.rHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.rUpperHorn02b = new ModelRenderer(this, 33, 7);
		this.rUpperHorn02b.mirror = true;
		this.rUpperHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rUpperHorn02b.addBox(-0.8F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.setRotationPoint(1.9F, 13.0F, 0.0F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 13, 4, 0.0F);
		this.lHorn02a = new ModelRenderer(this, 0, 4);
		this.lHorn02a.setRotationPoint(-0.1F, -1.5F, -0.1F);
		this.lHorn02a.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn02a, -0.2617993877991494F, 0.0F, 0.40142572795869574F);
		this.lHorn01 = new ModelRenderer(this, 0, 0);
		this.lHorn01.setRotationPoint(2.9F, -7.3F, 0.6F);
		this.lHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn01, -0.6108652381980153F, 0.0F, 1.3089969389957472F);
		this.lUpperHorn02a = new ModelRenderer(this, 33, 7);
		this.lUpperHorn02a.setRotationPoint(0.0F, 0.0F, 0.9F);
		this.lUpperHorn02a.addBox(-0.2F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.setRotateAngle(lUpperHorn02a, -0.3141592653589793F, 0.2617993877991494F, 0.0F);
		this.rUpperHorn02a = new ModelRenderer(this, 33, 7);
		this.rUpperHorn02a.mirror = true;
		this.rUpperHorn02a.setRotationPoint(0.0F, 0.0F, 0.9F);
		this.rUpperHorn02a.addBox(-0.2F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.setRotateAngle(rUpperHorn02a, -0.3141592653589793F, -0.2617993877991494F, 0.0F);
		this.rClaws = new ModelRenderer(this, 0, 34);
		this.rClaws.mirror = true;
		this.rClaws.setRotationPoint(-2.4F, 11.5F, 0.0F);
		this.rClaws.addBox(-1.1F, 0.0F, -2.1F, 2, 3, 4, 0.0F);
		this.setRotateAngle(rClaws, 0.0F, 0.0F, -0.2792526803190927F);
		this.rHorn01 = new ModelRenderer(this, 0, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-2.9F, -7.3F, 0.6F);
		this.rHorn01.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn01, -0.6108652381980153F, 0.0F, -1.3089969389957472F);
		this.lUpperHorn03 = new ModelRenderer(this, 42, 7);
		this.lUpperHorn03.setRotationPoint(0.0F, 0.0F, 2.4F);
		this.lUpperHorn03.addBox(-0.5F, -0.5F, -0.4F, 1, 1, 3, 0.0F);
		this.setRotateAngle(lUpperHorn03, -0.17453292519943295F, 0.17453292519943295F, 0.0F);
		this.rHorn03d_1 = new ModelRenderer(this, 0, 4);
		this.rHorn03d_1.mirror = true;
		this.rHorn03d_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d_1.addBox(-0.3F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.rHorn03d = new ModelRenderer(this, 0, 4);
		this.rHorn03d.mirror = true;
		this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 14, 4, 0.0F);
		this.lUpperHorn02b = new ModelRenderer(this, 33, 7);
		this.lUpperHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lUpperHorn02b.addBox(-0.8F, -0.8F, -0.5F, 1, 1, 3, 0.0F);
		this.lUpperHorn02d = new ModelRenderer(this, 33, 7);
		this.lUpperHorn02d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lUpperHorn02d.addBox(-0.8F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.lHorn03d = new ModelRenderer(this, 0, 4);
		this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn04 = new ModelRenderer(this, 49, 0);
		this.rHorn04.mirror = true;
		this.rHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.rHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn04, 0.05235987755982988F, 0.0F, -0.13962634015954636F);
		this.rHorn03b = new ModelRenderer(this, 0, 4);
		this.rHorn03b.mirror = true;
		this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03b.addBox(-0.3F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.cleft = new ModelRenderer(this, 31, 43);
		this.cleft.setRotationPoint(0.0F, 0.0F, -0.02F);
		this.cleft.addBox(-0.5F, -1.0F, -1.7F, 1, 2, 0, 0.0F);
		this.rHorn03a = new ModelRenderer(this, 0, 4);
		this.rHorn03a.mirror = true;
		this.rHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.rHorn03a.addBox(-0.7F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn03a, -0.17453292519943295F, 0.0F, -0.4363323129985824F);
		this.lHorn02b = new ModelRenderer(this, 0, 4);
		this.lHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02b.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.rUpperHorn02d = new ModelRenderer(this, 33, 7);
		this.rUpperHorn02d.mirror = true;
		this.rUpperHorn02d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rUpperHorn02d.addBox(-0.8F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.rHorn02c = new ModelRenderer(this, 0, 4);
		this.rHorn02c.mirror = true;
		this.rHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn02c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.upperJaw = new ModelRenderer(this, 21, 41);
		this.upperJaw.setRotationPoint(0.0F, -1.4F, -4.0F);
		this.upperJaw.addBox(-2.0F, -1.0F, -1.7F, 4, 2, 2, 0.0F);
		this.lUpperHorn02c = new ModelRenderer(this, 33, 7);
		this.lUpperHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lUpperHorn02c.addBox(-0.2F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.lHorn03d_1 = new ModelRenderer(this, 0, 4);
		this.lHorn03d_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d_1.addBox(-0.3F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.lHorn03a = new ModelRenderer(this, 0, 4);
		this.lHorn03a.setRotationPoint(0.0F, -1.6F, 0.0F);
		this.lHorn03a.addBox(-0.7F, -2.0F, -0.7F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn03a, -0.17453292519943295F, 0.0F, 0.4363323129985824F);
		this.lowerJaw = new ModelRenderer(this, 21, 46);
		this.lowerJaw.setRotationPoint(0.0F, -0.4F, -4.0F);
		this.lowerJaw.addBox(-1.5F, -0.5F, -1.6F, 3, 1, 2, 0.0F);
		this.rHorn03c = new ModelRenderer(this, 0, 4);
		this.rHorn03c.mirror = true;
		this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03c.addBox(-0.7F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.hair01 = new ModelRenderer(this, 40, 36);
		this.hair01.mirror = true;
		this.hair01.setRotationPoint(0.0F, -7.2F, 3.2F);
		this.hair01.addBox(-4.01F, 0.0F, -0.1F, 8, 8, 1, 0.0F);
		this.setRotateAngle(hair01, 0.3490658503988659F, 0.0F, 0.0F);
		this.rUpperHorn02c = new ModelRenderer(this, 33, 7);
		this.rUpperHorn02c.mirror = true;
		this.rUpperHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rUpperHorn02c.addBox(-0.2F, -0.2F, -0.5F, 1, 1, 3, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.mirror = true;
		this.bipedRightLeg.setRotationPoint(-1.9F, 13.0F, 0.0F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 13, 4, 0.0F);
		this.rUpperHorn03 = new ModelRenderer(this, 42, 7);
		this.rUpperHorn03.mirror = true;
		this.rUpperHorn03.setRotationPoint(0.0F, 0.0F, 2.4F);
		this.rUpperHorn03.addBox(-0.5F, -0.5F, -0.4F, 1, 1, 3, 0.0F);
		this.setRotateAngle(rUpperHorn03, -0.17453292519943295F, -0.17453292519943295F, 0.0F);
		this.lHorn03c = new ModelRenderer(this, 0, 4);
		this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03c.addBox(-0.7F, -2.0F, -0.3F, 1, 2, 1, 0.0F);
		this.rUpperHorn01 = new ModelRenderer(this, 24, 0);
		this.rUpperHorn01.mirror = true;
		this.rUpperHorn01.setRotationPoint(-2.0F, -8.7F, -0.5F);
		this.rUpperHorn01.addBox(-1.0F, -1.0F, -1.8F, 2, 2, 3, 0.0F);
		this.setRotateAngle(rUpperHorn01, 0.8726646259971648F, -0.17453292519943295F, 0.0F);
		this.lHorn02c = new ModelRenderer(this, 0, 4);
		this.lHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn02c.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 13, 4, 0.0F);
		this.lHorn04 = new ModelRenderer(this, 49, 0);
		this.lHorn04.setRotationPoint(0.0F, -1.7F, 0.0F);
		this.lHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn04, 0.05235987755982988F, 0.0F, 0.13962634015954636F);
		this.lUpperHorn01 = new ModelRenderer(this, 24, 0);
		this.lUpperHorn01.setRotationPoint(2.0F, -8.7F, -0.5F);
		this.lUpperHorn01.addBox(-1.0F, -1.0F, -1.8F, 2, 2, 3, 0.0F);
		this.setRotateAngle(lUpperHorn01, 0.8726646259971648F, 0.17453292519943295F, 0.0F);
		this.rHorn01.addChild(this.rHorn02a);
		this.bipedHead.addChild(this.hair00);
		this.lHorn03a.addChild(this.lHorn03b);
		this.bipedBody.addChild(this.bipedLeftArm);
		this.rHorn04.addChild(this.rHorn05);
		this.lHorn04.addChild(this.lHorn05);
		this.bipedBody.addChild(this.bipedHead);
		this.bipedHead.addChild(this.snout);
		this.bipedLeftArm.addChild(this.lClaws);
		this.rHorn02a.addChild(this.rHorn02b);
		this.rUpperHorn02a.addChild(this.rUpperHorn02b);
		this.bipedBody.addChild(this.bipedLeftLeg);
		this.lHorn01.addChild(this.lHorn02a);
		this.bipedHead.addChild(this.lHorn01);
		this.lUpperHorn01.addChild(this.lUpperHorn02a);
		this.rUpperHorn01.addChild(this.rUpperHorn02a);
		this.bipedRightArm.addChild(this.rClaws);
		this.bipedHead.addChild(this.rHorn01);
		this.lUpperHorn02a.addChild(this.lUpperHorn03);
		this.rHorn03a.addChild(this.rHorn03d_1);
		this.rHorn02a.addChild(this.rHorn03d);
		this.bipedBody.addChild(this.bipedRightArm);
		this.lUpperHorn02a.addChild(this.lUpperHorn02b);
		this.lUpperHorn02a.addChild(this.lUpperHorn02d);
		this.lHorn02a.addChild(this.lHorn03d);
		this.rHorn03a.addChild(this.rHorn04);
		this.rHorn03a.addChild(this.rHorn03b);
		this.upperJaw.addChild(this.cleft);
		this.rHorn02a.addChild(this.rHorn03a);
		this.lHorn02a.addChild(this.lHorn02b);
		this.rUpperHorn02a.addChild(this.rUpperHorn02d);
		this.rHorn02a.addChild(this.rHorn02c);
		this.bipedHead.addChild(this.upperJaw);
		this.lUpperHorn02a.addChild(this.lUpperHorn02c);
		this.lHorn03a.addChild(this.lHorn03d_1);
		this.lHorn02a.addChild(this.lHorn03a);
		this.bipedHead.addChild(this.lowerJaw);
		this.rHorn03a.addChild(this.rHorn03c);
		this.bipedHead.addChild(this.hair01);
		this.rUpperHorn02a.addChild(this.rUpperHorn02c);
		this.bipedBody.addChild(this.bipedRightLeg);
		this.rUpperHorn02a.addChild(this.rUpperHorn03);
		this.lHorn03a.addChild(this.lHorn03c);
		this.bipedHead.addChild(this.rUpperHorn01);
		this.lHorn02a.addChild(this.lHorn02c);
		this.lHorn03a.addChild(this.lHorn04);
		this.bipedHead.addChild(this.lUpperHorn01);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bipedBody.render(f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch, float scaleFactor, Entity entity) {
		boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
		this.bipedHead.rotateAngleY = netbipedHeadYaw * 0.017453292F;
		
		if (flag) {
			this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
		}
		else {
			this.bipedHead.rotateAngleX = bipedHeadPitch * 0.017453292F;
		}
		
		this.bipedBody.rotateAngleY = 0.0F;
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
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.3F * limbSwingAmount / f;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.3F * limbSwingAmount / f;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.rotateAngleZ = 0.0F;
		
		
		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedRightArm.rotateAngleZ = 0.10000736613927509F;
		this.bipedLeftArm.rotateAngleZ = -0.10000736613927509F;
		
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
			EnumHandSide enumhandside = this.getMainHand(entity);
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
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
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
		
		
		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedHead.rotationPointY = 0.0F;
		
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		
		if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}
		else if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}
		
		copyModelAngles(this.bipedHead, this.bipedHeadwear);
		
		setLivingAnimations((EntityLivingBase) entity, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
	}
	
	@Override
	public void postRenderArm(float scale, EnumHandSide side) {
		GlStateManager.translate(0.023F, 0.1, 0);
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
