package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.entity.living.animals.EntityRaven;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ravenv2 - Ingoleth, Cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelRaven extends ModelBase {
	public ModelRenderer ravenBody;
	public ModelRenderer ravenLeftLeg1;
	public ModelRenderer ravenRightLeg;
	public ModelRenderer wingLeft1;
	public ModelRenderer wingRight1;
	public ModelRenderer ravenBodyTail;
	public ModelRenderer ravenNeck;
	public ModelRenderer ravenLeftLeg2;
	public ModelRenderer ravenLeftLeg3;
	public ModelRenderer ravenRightLeg2;
	public ModelRenderer ravenRightLeg3;
	public ModelRenderer wingLeft2;
	public ModelRenderer wingLeft1Feathers;
	public ModelRenderer wingLeft2Feathers;
	public ModelRenderer wingLeft3;
	public ModelRenderer wingRight2;
	public ModelRenderer wingRight1Feathers;
	public ModelRenderer wingRight2Feathers;
	public ModelRenderer wingRight3;
	public ModelRenderer tail;
	public ModelRenderer tailLeft;
	public ModelRenderer tailRight;
	public ModelRenderer rumpRuff;
	public ModelRenderer feathers01;
	public ModelRenderer feathers02;
	public ModelRenderer ravenHead;
	public ModelRenderer ravenBeak1;
	public ModelRenderer ravenBeak2;
	public ModelRenderer ravenBeak1b;
	public ModelRenderer wingLeft3Feathers;
	public ModelRenderer wingRight3Feathers;

	public ModelRaven() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.ravenBeak2 = new ModelRenderer(this, 42, 12);
		this.ravenBeak2.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.ravenBeak2.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(ravenBeak2, -0.2617993877991494F, 0.0F, 0.0F);
		this.tailRight = new ModelRenderer(this, 2, 21);
		this.tailRight.setRotationPoint(-1.5F, -2.0999999046325684F, 3.0F);
		this.tailRight.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 7, 0.0F);
		this.wingRight3 = new ModelRenderer(this, 23, 24);
		this.wingRight3.mirror = true;
		this.wingRight3.setRotationPoint(-5.900000095367432F, 0.0F, 1.100000023841858F);
		this.wingRight3.addBox(-5.0F, -0.5899999737739563F, -1.0F, 5, 1, 2, 0.0F);
		this.ravenBodyTail = new ModelRenderer(this, 34, 39);
		this.ravenBodyTail.setRotationPoint(0.0F, -1.0F, 5.099999904632568F);
		this.ravenBodyTail.addBox(-2.5F, -4.0F, 0.0F, 5, 3, 3, 0.0F);
		this.ravenBeak1b = new ModelRenderer(this, 42, 12);
		this.ravenBeak1b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.ravenBeak1b.addBox(-1.100000023841858F, 0.0F, -3.0F, 1, 2, 3, 0.0F);
		this.tail = new ModelRenderer(this, 2, 21);
		this.tail.setRotationPoint(0.0F, -2.700000047683716F, 3.0F);
		this.tail.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 7, 0.0F);
		this.wingLeft1 = new ModelRenderer(this, 0, 0);
		this.wingLeft1.setRotationPoint(3.0F, -3.0F, -4.699999809265137F);
		this.wingLeft1.addBox(0.0F, -0.5F, 0.0F, 5, 1, 4, 0.0F);
		this.ravenLeftLeg1 = new ModelRenderer(this, 39, 1);
		this.ravenLeftLeg1.setRotationPoint(1.5F, -0.5F, 1.0F);
		this.ravenLeftLeg1.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
		this.wingLeft2 = new ModelRenderer(this, 1, 11);
		this.wingLeft2.setRotationPoint(5.0F, 0.0F, 0.0F);
		this.wingLeft2.addBox(0.0F, -0.5899999737739563F, 0.0F, 6, 1, 3, 0.0F);
		this.ravenRightLeg2 = new ModelRenderer(this, 33, 8);
		this.ravenRightLeg2.mirror = true;
		this.ravenRightLeg2.setRotationPoint(0.0F, 1.7999999523162842F, 1.5F);
		this.ravenRightLeg2.addBox(-0.5F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
		this.rumpRuff = new ModelRenderer(this, 19, 12);
		this.rumpRuff.setRotationPoint(0.0F, -0.4000000059604645F, -0.20000000298023224F);
		this.rumpRuff.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 3, 0.0F);
		this.setRotateAngle(rumpRuff, -0.19198621771937624F, 0.0F, 0.0F);
		this.ravenBody = new ModelRenderer(this, 1, 30);
		this.ravenBody.setRotationPoint(0.0F, 18.200000762939453F, 0.0F);
		this.ravenBody.addBox(-3.0F, -6.0F, -5.0F, 6, 6, 10, 0.0F);
		this.ravenRightLeg3 = new ModelRenderer(this, 33, 3);
		this.ravenRightLeg3.mirror = true;
		this.ravenRightLeg3.setRotationPoint(0.0F, 2.9000000953674316F, -1.0F);
		this.ravenRightLeg3.addBox(-0.5F, 0.0F, -2.0F, 1, 1, 3, 0.0F);
		this.ravenLeftLeg3 = new ModelRenderer(this, 33, 3);
		this.ravenLeftLeg3.setRotationPoint(0.0F, 2.9000000953674316F, -1.0F);
		this.ravenLeftLeg3.addBox(-0.5F, 0.0F, -2.0F, 1, 1, 3, 0.0F);
		this.ravenRightLeg = new ModelRenderer(this, 39, 1);
		this.ravenRightLeg.mirror = true;
		this.ravenRightLeg.setRotationPoint(-1.5F, -0.5F, 1.0F);
		this.ravenRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
		this.ravenBeak1 = new ModelRenderer(this, 42, 12);
		this.ravenBeak1.setRotationPoint(0.0F, -1.0F, -5.199999809265137F);
		this.ravenBeak1.addBox(-0.8999999761581421F, 0.0F, -3.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(ravenBeak1, 0.3490658503988659F, 0.0F, 0.0F);
		this.wingRight2 = new ModelRenderer(this, 1, 11);
		this.wingRight2.mirror = true;
		this.wingRight2.setRotationPoint(-5.0F, 0.0F, 0.0F);
		this.wingRight2.addBox(-6.0F, -0.5899999737739563F, 0.0F, 6, 1, 3, 0.0F);
		this.wingLeft1Feathers = new ModelRenderer(this, 0, 49);
		this.wingLeft1Feathers.setRotationPoint(0.0F, 0.0F, 4.099999904632568F);
		this.wingLeft1Feathers.addBox(0.0F, 0.009999999776482582F, -1.100000023841858F, 5, 0, 7, 0.0F);
		this.wingLeft3 = new ModelRenderer(this, 23, 24);
		this.wingLeft3.setRotationPoint(5.900000095367432F, 0.0F, 1.100000023841858F);
		this.wingLeft3.addBox(0.0F, -0.5899999737739563F, -1.0F, 5, 1, 2, 0.0F);
		this.feathers01 = new ModelRenderer(this, 36, 23);
		this.feathers01.setRotationPoint(0.0F, 2.299999952316284F, -1.7999999523162842F);
		this.feathers01.addBox(-2.0F, -1.0F, -1.0F, 4, 2, 4, 0.0F);
		this.setRotateAngle(feathers01, -0.6981317007977318F, 0.0F, 0.0F);
		this.tailLeft = new ModelRenderer(this, 2, 21);
		this.tailLeft.setRotationPoint(1.5F, -2.0F, 3.0F);
		this.tailLeft.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 7, 0.0F);
		this.wingRight1Feathers = new ModelRenderer(this, 0, 49);
		this.wingRight1Feathers.mirror = true;
		this.wingRight1Feathers.setRotationPoint(0.0F, 0.0F, 4.099999904632568F);
		this.wingRight1Feathers.addBox(-5.0F, 0.009999999776482582F, -1.100000023841858F, 5, 0, 7, 0.0F);
		this.wingRight2Feathers = new ModelRenderer(this, 9, 49);
		this.wingRight2Feathers.mirror = true;
		this.wingRight2Feathers.setRotationPoint(0.0F, -0.0F, 1.5F);
		this.wingRight2Feathers.addBox(-6.0F, 0.009999999776482582F, -1.5F, 6, 0, 10, 0.0F);
		this.wingRight1 = new ModelRenderer(this, 0, 0);
		this.wingRight1.mirror = true;
		this.wingRight1.setRotationPoint(-3.0F, -3.0F, -4.699999809265137F);
		this.wingRight1.addBox(-5.0F, -0.5F, 0.0F, 5, 1, 4, 0.0F);
		this.wingLeft2Feathers = new ModelRenderer(this, 9, 49);
		this.wingLeft2Feathers.setRotationPoint(0.0F, -0.0F, 1.5F);
		this.wingLeft2Feathers.addBox(0.0F, 0.0F, -1.5F, 6, 0, 10, 0.0F);
		this.feathers02 = new ModelRenderer(this, 36, 30);
		this.feathers02.setRotationPoint(0.0F, 2.0999999046325684F, 0.20000000298023224F);
		this.feathers02.addBox(-2.5F, -1.0F, -1.0F, 5, 2, 4, 0.0F);
		this.setRotateAngle(feathers02, -0.6108652381980153F, 0.0F, 0.0F);
		this.ravenHead = new ModelRenderer(this, 42, 0);
		this.ravenHead.setRotationPoint(0.0F, -1.0F, -2.0F);
		this.ravenHead.addBox(-2.5F, -2.5F, -5.599999904632568F, 5, 5, 6, 0.0F);
		this.ravenNeck = new ModelRenderer(this, 48, 18);
		this.ravenNeck.setRotationPoint(0.0F, -3.0999999046325684F, -3.799999952316284F);
		this.ravenNeck.addBox(-2.0F, -2.5F, -3.0F, 4, 5, 3, 0.0F);
		this.ravenLeftLeg2 = new ModelRenderer(this, 33, 8);
		this.ravenLeftLeg2.setRotationPoint(0.0F, 1.7999999523162842F, 1.5F);
		this.ravenLeftLeg2.addBox(-0.5F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
		this.wingLeft3Feathers = new ModelRenderer(this, 23, 49);
		this.wingLeft3Feathers.setRotationPoint(0.0F, -0.0F, 1.5F);
		this.wingLeft3Feathers.addBox(0.0F, 0.01F, -2.5F, 12, 0, 10, 0.0F);
		this.wingRight3Feathers = new ModelRenderer(this, 23, 49);
		this.wingRight3Feathers.mirror = true;
		this.wingRight3Feathers.setRotationPoint(0.0F, -0.0F, 1.5F);
		this.wingRight3Feathers.addBox(-12.0F, 0.01F, -2.5F, 12, 0, 10, 0.0F);

		this.ravenBeak1.addChild(this.ravenBeak2);
		this.ravenBodyTail.addChild(this.tailRight);
		this.wingRight2.addChild(this.wingRight3);
		this.ravenBody.addChild(this.ravenBodyTail);
		this.ravenBeak1.addChild(this.ravenBeak1b);
		this.ravenBodyTail.addChild(this.tail);
		this.ravenBody.addChild(this.wingLeft1);
		this.ravenBody.addChild(this.ravenLeftLeg1);
		this.wingLeft1.addChild(this.wingLeft2);
		this.ravenRightLeg.addChild(this.ravenRightLeg2);
		this.ravenBodyTail.addChild(this.rumpRuff);
		this.ravenRightLeg2.addChild(this.ravenRightLeg3);
		this.ravenLeftLeg2.addChild(this.ravenLeftLeg3);
		this.ravenBody.addChild(this.ravenRightLeg);
		this.ravenHead.addChild(this.ravenBeak1);
		this.wingRight1.addChild(this.wingRight2);
		this.wingLeft1.addChild(this.wingLeft1Feathers);
		this.wingLeft2.addChild(this.wingLeft3);
		this.ravenNeck.addChild(this.feathers01);
		this.ravenBodyTail.addChild(this.tailLeft);
		this.wingRight1.addChild(this.wingRight1Feathers);
		this.wingRight2.addChild(this.wingRight2Feathers);
		this.ravenBody.addChild(this.wingRight1);
		this.wingLeft2.addChild(this.wingLeft2Feathers);
		this.ravenNeck.addChild(this.feathers02);
		this.ravenNeck.addChild(this.ravenHead);
		this.ravenBody.addChild(this.ravenNeck);
		this.ravenLeftLeg1.addChild(this.ravenLeftLeg2);
		this.wingRight2.addChild(this.wingRight2Feathers);
		this.wingRight3.addChild(this.wingRight3Feathers);
		this.wingLeft2.addChild(this.wingLeft2Feathers);
		this.wingLeft3.addChild(this.wingLeft3Feathers);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		if (entityIn.onGround) {
			setRotateAngle(ravenHead, (float) (0.6981317007977318F + headPitch * Math.PI / 360), (float) (netHeadYaw * Math.PI / 360), 0);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
		EntityRaven raven = (EntityRaven) entitylivingbaseIn;
		if (raven.onGround) {
			if (raven.isSitting()) {
				setSittingStance();
				float time = (raven.ticksExisted + partialTickTime) * 0.10471975512F;
				ravenBody.rotateAngleX = -0.64577182323F + 0.00484813681f * MathHelper.sin(time);


			} else {
				setWanderingStance();
				float time = (raven.ticksExisted + partialTickTime) * 0.10471975512F;
				ravenBody.rotateAngleX = -0.64577182323F + 0.00484813681f * MathHelper.sin(time);
			}
		} else {
			setFlyingStance();
			float time = (raven.ticksExisted + partialTickTime) / 4.71238898F;
			wingRight1.rotateAngleZ = 0.26179938779914943f + 1.047166666666666f * MathHelper.cos(time);
			wingLeft1.rotateAngleZ = -wingRight1.rotateAngleZ;
			wingRight2.rotateAngleZ = -0.52359877559F + 0.34906585039f * MathHelper.sin(time);
			wingLeft2.rotateAngleZ = -wingRight2.rotateAngleZ;
			wingRight3.rotateAngleZ = wingRight1.rotateAngleZ / 4;
			wingLeft3.rotateAngleZ = -wingRight3.rotateAngleZ;
			ravenRightLeg2.rotateAngleX = 1.0471975511965976F + tail.rotateAngleX;
			ravenLeftLeg2.rotateAngleX = ravenRightLeg2.rotateAngleX;

		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.ravenBody.render(f5);

	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	private void setFlyingStance() {
		this.setRotateAngle(ravenBody, 0.08726646259971647F, 0.0F, 0.0F);
		this.setRotateAngle(ravenHead, -0.06981317007977318F, 0.0F, 0.0F);
		this.setRotateAngle(wingLeft1, -0.08726646259971647F, 0F, 1.2217304763960306F);
		this.setRotateAngle(wingLeft2, 0.0F, 0.0F, 0.4363323129985824F);
		this.setRotateAngle(wingLeft3, 0.0F, 0.0F, 0.3490658503988659F);
		this.setRotateAngle(wingRight1, -0.08726646259971647F, 0F, -1.2217304763960306F);
		this.setRotateAngle(wingRight2, 0.0F, 0.0F, -0.4363323129985824F);
		this.setRotateAngle(wingRight3, 0.0F, 0.0F, -0.3490658503988659F);
		this.setRotateAngle(ravenLeftLeg1, 0.61086523819F, 0.0F, 0.0F);
		this.setRotateAngle(ravenLeftLeg2, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(ravenLeftLeg3, 0.87266462599F, 0.0F, 0.0F);
		this.setRotateAngle(ravenRightLeg, 0.61086523819F, 0.0F, 0.0F);
		this.setRotateAngle(ravenRightLeg2, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(ravenRightLeg3, 0.87266462599F, 0.0F, 0.0F);
		this.wingLeft1Feathers.isHidden = false;
		this.wingRight1Feathers.isHidden = false;
		this.wingLeft2Feathers.isHidden = false;
		this.wingRight2Feathers.isHidden = false;
		this.wingLeft3Feathers.isHidden = false;
		this.wingRight3Feathers.isHidden = false;

	}

	private void setWanderingStance() {
		this.setRotateAngle(tailRight, -0.17453292519943295F, -0.12217304763960307F, -0.2617993877991494F);
		this.setRotateAngle(wingRight3, 0.0F, 0.2617993877991494F, 0.0F);
		this.setRotateAngle(ravenBodyTail, 0.22689280275926282F, 0.0F, 0.0F);
		this.setRotateAngle(tail, -0.15707963267948966F, 0.0F, 0.0F);
		this.setRotateAngle(wingLeft1, -0.03490658503988659F, -0.7853981633974483F, 0.5235987755982988F);
		this.setRotateAngle(ravenLeftLeg1, 0.41887902047863906F, 0.0F, 0.0F);
		this.setRotateAngle(wingLeft2, 0.0F, -0.9948376736367678F, 0.0F);
		this.setRotateAngle(ravenRightLeg2, -0.08726646259971647F, 0.03490658503988659F, 0.0F);
		this.setRotateAngle(ravenBody, -0.6457718232379019F, 0.0F, 0.0F);
		this.setRotateAngle(ravenRightLeg3, 0.3141592653589793F, 0.0F, 0.0F);
		this.setRotateAngle(ravenLeftLeg3, 0.3141592653589793F, 0.0F, 0.0F);
		this.setRotateAngle(ravenRightLeg, 0.4363323129985824F, 0.0F, 0.0F);
		this.setRotateAngle(wingRight2, 0.0F, 1.0122909661567112F, 0.0F);
		this.setRotateAngle(wingLeft3, 0.0F, -0.2617993877991494F, 0.0F);
		this.setRotateAngle(tailLeft, -0.17453292519943295F, 0.12217304763960307F, 0.2617993877991494F);
		this.setRotateAngle(wingRight1, -0.03490658503988659F, 0.7853981633974483F, -0.5235987755982988F);
		this.setRotateAngle(ravenHead, 0.6981317007977318F, 0.0F, 0.0F);
		this.setRotateAngle(ravenNeck, 0.2617993877991494F, 0.0F, 0.0F);
		this.setRotateAngle(ravenLeftLeg2, -0.08726646259971647F, -0.03490658503988659F, 0.0F);
		this.wingLeft1Feathers.isHidden = true;
		this.wingRight1Feathers.isHidden = true;
		this.wingLeft2Feathers.isHidden = true;
		this.wingRight2Feathers.isHidden = true;
		this.wingLeft3Feathers.isHidden = true;
		this.wingRight3Feathers.isHidden = true;

	}

	private void setSittingStance() {
		setWanderingStance();
		this.setRotateAngle(ravenLeftLeg2, -0.08726646259971647F, -0.26179938779F, 0.0F);
		this.setRotateAngle(ravenRightLeg2, -0.08726646259971647F, 0.26179938779F, 0.0F);
	}
}
