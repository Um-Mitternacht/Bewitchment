package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.entity.living.animals.EntityOwl;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelOwl extends ModelBase {

	public ModelRenderer owlBody;
	public ModelRenderer tail;
	public ModelRenderer tailRight;
	public ModelRenderer tailLeft;
	public ModelRenderer owlHead;
	public ModelRenderer owlRightClaw;
	public ModelRenderer owlLeftClaw;
	public ModelRenderer wingLeft01;
	public ModelRenderer wingRight01;
	public ModelRenderer owlBodyFluff;
	public ModelRenderer owlBeak;
	public ModelRenderer owlRightEar;
	public ModelRenderer owlLeftEar;
	public ModelRenderer rTalon01;
	public ModelRenderer rTalon02;
	public ModelRenderer rTalon03;
	public ModelRenderer rTalon04;
	public ModelRenderer lTalon01;
	public ModelRenderer lTalon02;
	public ModelRenderer lTalon03;
	public ModelRenderer lTalon04;
	public ModelRenderer wingLeft02;
	public ModelRenderer wingLeftAlt01a;
	public ModelRenderer wingLeft03;
	public ModelRenderer wingLeftAlt02a;
	public ModelRenderer wingLeftAlt03a;
	public ModelRenderer wingLeftAlt03b;
	public ModelRenderer wingLeftAlt03c;
	public ModelRenderer wingLeftAlt02b;
	public ModelRenderer wingLeftAlt02c;
	public ModelRenderer wingLeftAlt01b;
	public ModelRenderer wingLeftAlt01c;
	public ModelRenderer wingRight02;
	public ModelRenderer wingRightAlt01a;
	public ModelRenderer wingRight03;
	public ModelRenderer wingRightAlt02a;
	public ModelRenderer wingRightAlt03a;
	public ModelRenderer wingRightAlt03b;
	public ModelRenderer wingRightAlt03c;
	public ModelRenderer wingRightAlt02b;
	public ModelRenderer wingRightAlt02c;
	public ModelRenderer wingRightAlt01b;
	public ModelRenderer wingRightAlt01c;

	public ModelOwl() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.lTalon03 = new ModelRenderer(this, 56, 0);
		this.lTalon03.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 3, 0.0F);
		this.wingRightAlt02a = new ModelRenderer(this, 0, 45);
		this.wingRightAlt02a.mirror = true;
		this.wingRightAlt02a.addBox(-6.0F, 0.0F, -0.9900000095367432F, 6, 2, 2, 0.0F);
		this.owlHead = new ModelRenderer(this, 28, 0);
		this.owlHead.addBox(-5.0F, -4.0F, -4.0F, 10, 8, 8, 0.0F);
		this.owlBeak = new ModelRenderer(this, 19, 1);
		this.owlBeak.addBox(0.0F, 0.0F, 0.4000000059604645F, 2, 4, 1, 0.0F);
		this.wingLeftAlt01c = new ModelRenderer(this, 18, 16);
		this.wingLeftAlt01c.addBox(0.0F, 0.0F, 0.0F, 6, 8, 0, 0.0F);
		this.lTalon02 = new ModelRenderer(this, 19, 8);
		this.lTalon02.addBox(-0.5F, -1.0F, -3.0F, 1, 2, 3, 0.0F);
		this.wingLeft03 = new ModelRenderer(this, 0, 30);
		this.wingLeft03.addBox(0.0F, 0.0F, -1.0F, 6, 8, 2, 0.0F);
		this.wingRight03 = new ModelRenderer(this, 0, 30);
		this.wingRight03.mirror = true;
		this.wingRight03.addBox(-6.0F, 0.0F, -2.0F, 6, 8, 2, 0.0F);
		this.wingRightAlt03b = new ModelRenderer(this, 49, 60);
		this.wingRightAlt03b.mirror = true;
		this.wingRightAlt03b.addBox(-3.0F, 0.0F, -0.5F, 6, 2, 1, 0.0F);
		this.tail = new ModelRenderer(this, 25, 45);
		this.tail.addBox(0.0F, 0.0F, 0.0F, 4, 2, 8, 0.0F);
		this.rTalon01 = new ModelRenderer(this, 19, 8);
		this.rTalon01.mirror = true;
		this.rTalon01.addBox(-0.5F, -1.0F, -3.0F, 1, 2, 3, 0.0F);
		this.owlBody = new ModelRenderer(this, 24, 16);
		this.owlBody.addBox(-6.0F, -8.0F, -4.0F, 12, 14, 8, 0.0F);
		this.wingRightAlt03a = new ModelRenderer(this, 0, 49);
		this.wingRightAlt03a.mirror = true;
		this.wingRightAlt03a.addBox(-6.0F, 0.0F, -1.0F, 6, 2, 2, 0.0F);
		this.tailRight = new ModelRenderer(this, 42, 40);
		this.tailRight.mirror = true;
		this.tailRight.addBox(-4.0F, 0.0F, 0.0F, 4, 2, 6, 0.0F);
		this.wingRight01 = new ModelRenderer(this, 0, 8);
		this.wingRight01.mirror = true;
		this.wingRight01.addBox(-6.0F, 0.0F, -1.0F, 6, 8, 2, 0.0F);
		this.wingLeftAlt02a = new ModelRenderer(this, 0, 45);
		this.wingLeftAlt02a.addBox(0.0F, 0.0F, -0.9900000095367432F, 6, 2, 2, 0.0F);
		this.wingRightAlt02b = new ModelRenderer(this, 49, 57);
		this.wingRightAlt02b.mirror = true;
		this.wingRightAlt02b.addBox(-3.0F, 0.0F, -0.5099999904632568F, 6, 2, 1, 0.0F);
		this.owlLeftClaw = new ModelRenderer(this, 28, 56);
		this.owlLeftClaw.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 6, 0.0F);
		this.lTalon04 = new ModelRenderer(this, 56, 0);
		this.lTalon04.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 3, 0.0F);
		this.owlLeftEar = new ModelRenderer(this, 27, 1);
		this.owlLeftEar.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.wingRightAlt01b = new ModelRenderer(this, 49, 54);
		this.wingRightAlt01b.mirror = true;
		this.wingRightAlt01b.addBox(-3.0F, 0.0F, -0.5F, 6, 2, 1, 0.0F);
		this.wingRightAlt01a = new ModelRenderer(this, 0, 41);
		this.wingRightAlt01a.mirror = true;
		this.wingRightAlt01a.addBox(-6.0F, 0.0F, -1.0F, 6, 2, 2, 0.0F);
		this.owlBodyFluff = new ModelRenderer(this, 0, 0);
		this.owlBodyFluff.addBox(-3.5F, 0.0F, -1.0F, 7, 5, 2, 0.0F);
		this.wingLeftAlt01b = new ModelRenderer(this, 49, 54);
		this.wingLeftAlt01b.addBox(-3.0F, 0.0F, -0.5F, 6, 2, 1, 0.0F);
		this.owlRightClaw = new ModelRenderer(this, 28, 56);
		this.owlRightClaw.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 6, 0.0F);
		this.wingLeft02 = new ModelRenderer(this, 0, 19);
		this.wingLeft02.addBox(0.0F, 0.0F, -0.9900000095367432F, 6, 8, 2, 0.0F);
		this.wingLeftAlt03a = new ModelRenderer(this, 0, 49);
		this.wingLeftAlt03a.addBox(0.0F, 0.0F, -2.0F, 6, 2, 2, 0.0F);
		this.wingRightAlt02c = new ModelRenderer(this, 19, 42);
		this.wingRightAlt02c.mirror = true;
		this.wingRightAlt02c.addBox(0.0F, 0.0F, 0.10000000149011612F, 6, 8, 0, 0.0F);
		this.wingRightAlt01c = new ModelRenderer(this, 18, 16);
		this.wingRightAlt01c.mirror = true;
		this.wingRightAlt01c.addBox(0.0F, 0.0F, 0.0F, 6, 8, 0, 0.0F);
		this.wingLeftAlt02c = new ModelRenderer(this, 19, 42);
		this.wingLeftAlt02c.addBox(0.0F, 0.0F, 0.10000000149011612F, 6, 8, 0, 0.0F);
		this.wingRightAlt03c = new ModelRenderer(this, 0, 53);
		this.wingRightAlt03c.mirror = true;
		this.wingRightAlt03c.addBox(0.0F, -2.9000000953674316F, 0.0F, 12, 11, 0, 0.0F);
		this.wingLeft01 = new ModelRenderer(this, 0, 8);
		this.wingLeft01.addBox(0.0F, 0.0F, -1.0F, 6, 8, 2, 0.0F);
		this.wingRight02 = new ModelRenderer(this, 0, 19);
		this.wingRight02.mirror = true;
		this.wingRight02.addBox(-6.0F, 0.0F, -1.9900000095367432F, 6, 8, 2, 0.0F);
		this.lTalon01 = new ModelRenderer(this, 19, 8);
		this.lTalon01.addBox(-0.5F, -1.0F, -3.0F, 1, 2, 3, 0.0F);
		this.wingLeftAlt01a = new ModelRenderer(this, 0, 41);
		this.wingLeftAlt01a.addBox(0.0F, 0.0F, -1.0F, 6, 2, 2, 0.0F);
		this.rTalon04 = new ModelRenderer(this, 56, 0);
		this.rTalon04.mirror = true;
		this.rTalon04.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 3, 0.0F);
		this.wingLeftAlt03b = new ModelRenderer(this, 49, 60);
		this.wingLeftAlt03b.addBox(-3.0F, 0.0F, -0.5F, 6, 2, 1, 0.0F);
		this.wingLeftAlt02b = new ModelRenderer(this, 49, 57);
		this.wingLeftAlt02b.addBox(-3.0F, 0.0F, -0.5099999904632568F, 6, 2, 1, 0.0F);
		this.owlRightEar = new ModelRenderer(this, 27, 1);
		this.owlRightEar.mirror = true;
		this.owlRightEar.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.tailLeft = new ModelRenderer(this, 42, 40);
		this.tailLeft.addBox(0.0F, 0.0F, 0.0F, 4, 2, 6, 0.0F);
		this.wingLeftAlt03c = new ModelRenderer(this, 0, 53);
		this.wingLeftAlt03c.addBox(0.0F, -2.9000000953674316F, 0.0F, 12, 11, 0, 0.0F);
		this.rTalon02 = new ModelRenderer(this, 19, 8);
		this.rTalon02.mirror = true;
		this.rTalon02.addBox(-0.5F, -1.0F, -3.0F, 1, 2, 3, 0.0F);
		this.rTalon03 = new ModelRenderer(this, 56, 0);
		this.rTalon03.mirror = true;
		this.rTalon03.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 3, 0.0F);
		this.owlLeftClaw.addChild(this.lTalon03);
		this.wingRight02.addChild(this.wingRightAlt02a);
		this.owlBody.addChild(this.owlHead);
		this.owlHead.addChild(this.owlBeak);
		this.wingLeftAlt01b.addChild(this.wingLeftAlt01c);
		this.owlLeftClaw.addChild(this.lTalon02);
		this.wingLeft02.addChild(this.wingLeft03);
		this.wingRight02.addChild(this.wingRight03);
		this.wingRightAlt03a.addChild(this.wingRightAlt03b);
		this.owlBody.addChild(this.tail);
		this.owlRightClaw.addChild(this.rTalon01);
		this.wingRight03.addChild(this.wingRightAlt03a);
		this.owlBody.addChild(this.tailRight);
		this.owlBody.addChild(this.wingRight01);
		this.wingLeft02.addChild(this.wingLeftAlt02a);
		this.wingRightAlt02a.addChild(this.wingRightAlt02b);
		this.owlBody.addChild(this.owlLeftClaw);
		this.owlLeftClaw.addChild(this.lTalon04);
		this.owlHead.addChild(this.owlLeftEar);
		this.wingRightAlt01a.addChild(this.wingRightAlt01b);
		this.wingRight01.addChild(this.wingRightAlt01a);
		this.owlBody.addChild(this.owlBodyFluff);
		this.wingLeftAlt01a.addChild(this.wingLeftAlt01b);
		this.owlBody.addChild(this.owlRightClaw);
		this.wingLeft01.addChild(this.wingLeft02);
		this.wingLeft03.addChild(this.wingLeftAlt03a);
		this.wingRightAlt02b.addChild(this.wingRightAlt02c);
		this.wingRightAlt01b.addChild(this.wingRightAlt01c);
		this.wingLeftAlt02b.addChild(this.wingLeftAlt02c);
		this.wingRightAlt03b.addChild(this.wingRightAlt03c);
		this.owlBody.addChild(this.wingLeft01);
		this.wingRight01.addChild(this.wingRight02);
		this.owlLeftClaw.addChild(this.lTalon01);
		this.wingLeft01.addChild(this.wingLeftAlt01a);
		this.owlRightClaw.addChild(this.rTalon04);
		this.wingLeftAlt03a.addChild(this.wingLeftAlt03b);
		this.wingLeftAlt02a.addChild(this.wingLeftAlt02b);
		this.owlHead.addChild(this.owlRightEar);
		this.owlBody.addChild(this.tailLeft);
		this.wingLeftAlt03b.addChild(this.wingLeftAlt03c);
		this.owlRightClaw.addChild(this.rTalon02);
		this.owlRightClaw.addChild(this.rTalon03);

		setWanderingStance();
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.owlBody.offsetX, this.owlBody.offsetY, this.owlBody.offsetZ);
		GlStateManager.translate(this.owlBody.rotationPointX * scale, this.owlBody.rotationPointY * scale, this.owlBody.rotationPointZ * scale);
		GlStateManager.scale(1.0F, 1.0F, 1.0F);
		GlStateManager.translate(-this.owlBody.offsetX, -this.owlBody.offsetY, -this.owlBody.offsetZ);
		GlStateManager.translate(-this.owlBody.rotationPointX * scale, -this.owlBody.rotationPointY * scale, -this.owlBody.rotationPointZ * scale);
		this.owlBody.render(scale);
		GlStateManager.popMatrix();
	}

	public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		if (entityIn.onGround) {
			setRotationAngles(owlHead, (float) (headPitch * Math.PI / 360), (float) (netHeadYaw * Math.PI / 360), 0);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
		EntityOwl owl = (EntityOwl) entitylivingbaseIn;
		if (owl.onGround) {
			if (owl.isSitting()) {
				setSittingStance();
				float time = (owl.ticksExisted + partialTickTime) * 0.10471975512F;
				owlBody.rotateAngleX = 0.08726646259F + 0.00484813681f * MathHelper.sin(time);


			} else {
				setWanderingStance();
				float time = (owl.ticksExisted + partialTickTime) * 0.10471975512F;
				owlBody.rotateAngleX = 0.08726646259F + 0.00484813681f * MathHelper.sin(time);
			}
		} else {
			setFlyingStance();
			float time = (owl.ticksExisted + partialTickTime) / 4.71238898F;
			wingRight01.rotateAngleY = 0.26179938779914943f + 1.047166666666666f * MathHelper.cos(time);
			wingLeft01.rotateAngleY = -wingRight01.rotateAngleY;
			wingRight02.rotateAngleY = -0.52359877559F + 0.34906585039f * MathHelper.sin(time);
			wingLeft02.rotateAngleY = -wingRight02.rotateAngleY;
			wingRight03.rotateAngleY = wingRight01.rotateAngleY / 4;
			wingLeft03.rotateAngleY = -wingRight03.rotateAngleY;
			tail.rotateAngleX = -1.0471975511965976F + wingRight03.rotateAngleY / 4;
			tailRight.rotateAngleX = -0.8726646259971648F + tail.rotateAngleX + 1.0471975511965976F;
			tailLeft.rotateAngleX = tailRight.rotateAngleX;
			owlRightClaw.rotateAngleX = wingRight03.rotateAngleY / 4;
			owlLeftClaw.rotateAngleX = owlRightClaw.rotateAngleX;

		}
	}

	private void setFlyingStance() {
		this.wingLeftAlt03a.setRotationPoint(0.0F, 0.0F, 1.0F);
		this.wingRightAlt01a.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.owlRightClaw.setRotationPoint(-3.0F, 6.0F, -1.5F);
		this.setRotationAngles(owlRightClaw, 0.8726646259971648F, 0.08726646259971647F, 0.0F);
		this.wingRight02.setRotationPoint(-6.0F, 0.0F, 1.0F);
		this.setRotationAngles(wingRight02, 0.0F, -0.4363323129985824F, 0.0F);
		this.wingRightAlt03b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.owlBeak.setRotationPoint(-1.0F, 0.0F, -5.0F);
		this.setRotationAngles(owlBeak, -0.08726646259971647F, 0.0F, 0.0F);
		this.owlLeftEar.setRotationPoint(4.0F, -3.4000000953674316F, 0.0F);
		this.setRotationAngles(owlLeftEar, -0.3490658503988659F, 0.0F, 0.2617993877991494F);
		this.wingRight03.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.setRotationAngles(wingRight03, 0.0F, -0.3490658503988659F, 0.0F);
		this.owlHead.setRotationPoint(0.0F, -12.0F, 0.0F);
		this.setRotationAngles(owlHead, -1.48352986419518F, 0.0F, 0.0F);
		this.lTalon01.setRotationPoint(0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(lTalon01, -0.17453292519943295F, -0.10471975511965977F, 0.0F);
		this.wingRightAlt01b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.wingLeftAlt01a.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.wingLeftAlt03c.setRotationPoint(-3.0F, 0.30000001192092896F, 0.0F);
		this.wingRightAlt02c.setRotationPoint(-3.0F, 1.2999999523162842F, 0.0F);
		this.wingLeft02.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.setRotationAngles(wingLeft02, 0.0F, 0.4363323129985824F, 0.0F);
		this.owlRightEar.setRotationPoint(-4.0F, -3.4000000953674316F, 0.0F);
		this.setRotationAngles(owlRightEar, -0.3490658503988659F, 0.0F, -0.2617993877991494F);
		this.wingRight01.setRotationPoint(-6.0F, -7.0F, 0.0F);
		this.setRotationAngles(wingRight01, -0.08726646259971647F, 0.6108652381980153F, 0.0F);
		this.tail.setRotationPoint(-2.0F, 2.5F, 4.0F);
		this.setRotationAngles(tail, -1.0471975511965976F, 0.0F, 0.0F);
		this.wingRightAlt02a.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.wingRightAlt02b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.rTalon03.setRotationPoint(-0.699999988079071F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(rTalon03, -0.17453292519943295F, -0.06981317007977318F, 0.0F);
		this.wingLeftAlt03b.setRotationPoint(3.0F, 1.7999999523162842F, -1.0F);
		this.tailRight.setRotationPoint(-2.0F, 2.5F, 4.0F);
		this.setRotationAngles(tailRight, -0.8726646259971648F, -0.4363323129985824F, -0.17453292519943295F);
		this.lTalon02.setRotationPoint(-0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(lTalon02, -0.17453292519943295F, 0.10471975511965977F, 0.0F);
		this.wingLeftAlt02c.setRotationPoint(-3.0F, 1.2999999523162842F, 0.0F);
		this.wingLeftAlt01b.setRotationPoint(3.0F, 1.7999999523162842F, 0.0F);
		this.wingLeft01.setRotationPoint(6.0F, -7.0F, 0.0F);
		this.setRotationAngles(wingLeft01, -0.08726646259971647F, -0.6108652381980153F, 0.0F);
		this.owlBodyFluff.setRotationPoint(0.0F, -6.400000095367432F, -3.0999999046325684F);
		this.setRotationAngles(owlBodyFluff, -0.3665191429188092F, 0.0F, 0.0F);
		this.wingLeftAlt01c.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.wingRightAlt03c.setRotationPoint(-9.0F, 0.30000001192092896F, 0.0F);
		this.owlBody.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.setRotationAngles(owlBody, 1.48352986419518F, 0.0F, 0.0F);
		this.wingRightAlt03a.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.wingLeftAlt02b.setRotationPoint(3.0F, 1.7999999523162842F, 0.0F);
		this.tailLeft.setRotationPoint(2.0F, 2.5F, 4.0F);
		this.setRotationAngles(tailLeft, -0.8726646259971648F, 0.4363323129985824F, 0.17453292519943295F);
		this.rTalon04.setRotationPoint(0.699999988079071F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(rTalon04, -0.17453292519943295F, 0.06981317007977318F, 0.0F);
		this.wingLeft03.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.setRotationAngles(wingLeft03, 0.0F, 0.3490658503988659F, 0.0F);
		this.wingLeftAlt02a.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lTalon04.setRotationPoint(-0.699999988079071F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(lTalon04, -0.17453292519943295F, -0.06981317007977318F, 0.0F);
		this.wingRightAlt01c.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.owlLeftClaw.setRotationPoint(3.0F, 6.0F, -1.5F);
		this.setRotationAngles(owlLeftClaw, 0.8726646259971648F, -0.08726646259971647F, 0.0F);
		this.lTalon03.setRotationPoint(0.6000000238418579F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(lTalon03, -0.17453292519943295F, 0.06981317007977318F, 0.0F);
		this.rTalon01.setRotationPoint(-0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(rTalon01, -0.17453292519943295F, 0.10471975511965977F, 0.0F);
		this.rTalon02.setRotationPoint(0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(rTalon02, -0.17453292519943295F, -0.10471975511965977F, 0.0F);


	}

	private void setWanderingStance() {
		this.lTalon03.setRotationPoint(0.6000000238418579F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(lTalon03, -0.17453292519943295F, 0.06981317007977318F, 0.0F);
		this.wingRightAlt02a.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.wingRightAlt02b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.owlHead.setRotationPoint(0.0F, -12.0F, 0.0F);
		this.setRotationAngles(owlHead, -0.06981317007977318F, 0.0F, 0.0F);
		this.owlBeak.setRotationPoint(-1.0F, 0.0F, -5.0F);
		this.setRotationAngles(owlBeak, -0.08726646259971647F, 0.0F, 0.0F);
		this.wingLeftAlt01c.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.lTalon02.setRotationPoint(-0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(lTalon02, -0.17453292519943295F, 0.10471975511965977F, 0.0F);
		this.wingLeft03.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.setRotationAngles(wingLeft03, 0.0F, 0.0F, 0.3490658503988659F);
		this.wingRight03.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.setRotationAngles(wingRight03, 0.0F, 0.0F, -0.3490658503988659F);
		this.wingRightAlt03b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.tail.setRotationPoint(-2.0F, 2.5F, 4.0F);
		this.setRotationAngles(tail, -1.0471975511965976F, 0.0F, 0.0F);
		this.rTalon01.setRotationPoint(-0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(rTalon01, -0.17453292519943295F, 0.10471975511965977F, 0.0F);
		this.owlBody.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.setRotationAngles(owlBody, 0.08726646259971647F, 0.0F, 0.0F);
		this.wingRightAlt03a.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.tailRight.setRotationPoint(-2.0F, 2.5F, 4.0F);
		this.setRotationAngles(tailRight, -0.8726646259971648F, -0.4363323129985824F, -0.17453292519943295F);
		this.wingRight01.setRotationPoint(-6.0F, -7.0F, 0.0F);
		this.setRotationAngles(wingRight01, -0.08726646259971647F, 0.6108652381980153F, -1.2217304763960306F);
		this.wingLeftAlt02a.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.wingRightAlt02b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.owlLeftClaw.setRotationPoint(3.0F, 6.0F, -1.5F);
		this.setRotationAngles(owlLeftClaw, 0.08726646259971647F, -0.08726646259971647F, 0.0F);
		this.lTalon04.setRotationPoint(-0.699999988079071F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(lTalon04, -0.17453292519943295F, -0.06981317007977318F, 0.0F);
		this.owlLeftEar.setRotationPoint(4.0F, -3.4000000953674316F, 0.0F);
		this.setRotationAngles(owlLeftEar, -0.3490658503988659F, 0.0F, 0.2617993877991494F);
		this.wingRightAlt01b.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.wingRightAlt01a.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.owlBodyFluff.setRotationPoint(0.0F, -6.400000095367432F, -3.0999999046325684F);
		this.setRotationAngles(owlBodyFluff, -0.3665191429188092F, 0.0F, 0.0F);
		this.wingLeftAlt01b.setRotationPoint(3.0F, 1.7999999523162842F, 0.0F);
		this.owlRightClaw.setRotationPoint(-3.0F, 6.0F, -1.5F);
		this.setRotationAngles(owlRightClaw, 0.08726646259971647F, 0.08726646259971647F, 0.0F);
		this.wingLeft02.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.setRotationAngles(wingLeft02, 0.0F, 0.0F, 0.4363323129985824F);
		this.wingLeftAlt03a.setRotationPoint(0.0F, 0.0F, 1.0F);
		this.wingRightAlt02c.setRotationPoint(-3.0F, 1.2999999523162842F, 0.0F);
		this.wingRightAlt01c.setRotationPoint(-3.0F, 1.7999999523162842F, 0.0F);
		this.wingLeftAlt02c.setRotationPoint(-3.0F, 1.2999999523162842F, 0.0F);
		this.wingRightAlt03c.setRotationPoint(-9.0F, 0.30000001192092896F, 0.0F);
		this.wingLeft01.setRotationPoint(6.0F, -7.0F, 0.0F);
		this.setRotationAngles(wingLeft01, -0.08726646259971647F, -0.6108652381980153F, 1.2217304763960306F);
		this.wingRight02.setRotationPoint(-6.0F, 0.0F, 1.0F);
		this.setRotationAngles(wingRight02, 0.0F, 0.0F, -0.4363323129985824F);
		this.lTalon01.setRotationPoint(0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(lTalon01, -0.17453292519943295F, -0.10471975511965977F, 0.0F);
		this.wingLeftAlt01a.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rTalon04.setRotationPoint(0.699999988079071F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(rTalon04, -0.17453292519943295F, 0.06981317007977318F, 0.0F);
		this.wingLeftAlt03b.setRotationPoint(3.0F, 1.7999999523162842F, -1.0F);
		this.wingLeftAlt02b.setRotationPoint(3.0F, 1.7999999523162842F, 0.0F);
		this.owlRightEar.setRotationPoint(-4.0F, -3.4000000953674316F, 0.0F);
		this.setRotationAngles(owlRightEar, -0.3490658503988659F, 0.0F, -0.2617993877991494F);
		this.tailLeft.setRotationPoint(2.0F, 2.5F, 4.0F);
		this.setRotationAngles(tailLeft, -0.8726646259971648F, 0.4363323129985824F, 0.17453292519943295F);
		this.wingLeftAlt03c.setRotationPoint(-3.0F, 0.30000001192092896F, 0.0F);
		this.rTalon02.setRotationPoint(0.699999988079071F, 1.0F, -3.5999999046325684F);
		this.setRotationAngles(rTalon02, -0.17453292519943295F, -0.10471975511965977F, 0.0F);
		this.rTalon03.setRotationPoint(-0.699999988079071F, 1.2000000476837158F, 0.5F);
		this.setRotationAngles(rTalon03, -0.17453292519943295F, -0.06981317007977318F, 0.0F);

	}

	private void setSittingStance() {
		setWanderingStance();
		this.setRotationAngles(owlLeftClaw, -0.08726646259971647F, -0.3f, 0);
		this.setRotationAngles(owlRightClaw, -0.08726646259971647F, 0.3f, 0);
		this.setRotationAngles(this.owlBody, 0.08726646259971647F, 0.0F, 0.0F);
	}
}
