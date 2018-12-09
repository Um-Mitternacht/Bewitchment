package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.entity.living.animals.EntityBlindworm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * blindworm - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelBlindworm extends ModelBase {
	public ModelRenderer neck01a;
	public ModelRenderer neck01b;
	public ModelRenderer neck02;
	public ModelRenderer body;
	public ModelRenderer neck03;
	public ModelRenderer head;
	public ModelRenderer snout;
	public ModelRenderer lowerJaw;
	public ModelRenderer lEye;
	public ModelRenderer rEye;
	public ModelRenderer headB;
	public ModelRenderer tail0a;
	public ModelRenderer tail01b;
	public ModelRenderer tail02;
	public ModelRenderer tail03;

	public ModelBlindworm() {
		this.textureWidth = 64;
		this.textureHeight = 65;
		this.snout = new ModelRenderer(this, 0, 10);
		this.snout.setRotationPoint(0.0F, -0.8F, -1.9F);
		this.snout.addBox(-2.0F, -1.0F, -2.2F, 4, 2, 2, 0.0F);
		this.setRotateAngle(snout, 0.08726646259971647F, 0.0F, 0.0F);
		this.neck03 = new ModelRenderer(this, 0, 24);
		this.neck03.setRotationPoint(0.0F, 0.0F, -3.6F);
		this.neck03.addBox(-2.0F, -1.5F, -3.2F, 4, 3, 3, 0.0F);
		this.neck01a = new ModelRenderer(this, 25, 15);
		this.neck01a.setRotationPoint(0.0F, 22.5F, 1.9F);
		this.neck01a.addBox(-1.8F, -1.49F, -6.0F, 4, 3, 9, 0.0F);
		this.body = new ModelRenderer(this, 0, 33);
		this.body.setRotationPoint(0.0F, 0.0F, 3.4F);
		this.body.addBox(-2.0F, -1.49F, -0.5F, 4, 3, 7, 0.0F);
		this.lowerJaw = new ModelRenderer(this, 15, 8);
		this.lowerJaw.setRotationPoint(0.0F, 0.9F, 1.0F);
		this.lowerJaw.addBox(-2.0F, -0.5F, -4.8F, 4, 1, 5, 0.0F);
		this.setRotateAngle(lowerJaw, -0.08726646259971647F, 0.0F, 0.0F);
		this.neck01b = new ModelRenderer(this, 25, 28);
		this.neck01b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.neck01b.addBox(-2.2F, -1.49F, -6.0F, 1, 3, 9, 0.0F);
		this.tail0a = new ModelRenderer(this, 23, 45);
		this.tail0a.setRotationPoint(0.0F, 0.0F, 5.9F);
		this.tail0a.addBox(-1.5F, -1.1F, -1.0F, 3, 2, 7, 0.0F);
		this.neck02 = new ModelRenderer(this, 0, 16);
		this.neck02.setRotationPoint(0.0F, 0.0F, -5.9F);
		this.neck02.addBox(-2.0F, -1.5F, -3.9F, 4, 3, 4, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.3F, -3.9F);
		this.head.addBox(-1.6F, -2.0F, -2.5F, 4, 3, 5, 0.0F);
		this.setRotateAngle(head, 0.13962634015954636F, 0.0F, 0.0F);
		this.lEye = new ModelRenderer(this, 32, 0);
		this.lEye.setRotationPoint(1.3F, -0.2F, 0.0F);
		this.lEye.addBox(-0.5F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lEye, 0.0F, 0.12217304763960307F, -0.19198621771937624F);
		this.rEye = new ModelRenderer(this, 32, 0);
		this.rEye.mirror = true;
		this.rEye.setRotationPoint(-1.3F, -0.2F, 0.0F);
		this.rEye.addBox(-1.5F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rEye, 0.0F, -0.12217304763960307F, 0.19198621771937624F);
		this.tail02 = new ModelRenderer(this, 41, 40);
		this.tail02.setRotationPoint(0.0F, 0.5F, 6.7F);
		this.tail02.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 7, 0.0F);
		this.headB = new ModelRenderer(this, 19, 0);
		this.headB.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.headB.addBox(-2.3F, -2.0F, -2.5F, 1, 3, 5, 0.0F);
		this.tail01b = new ModelRenderer(this, 22, 55);
		this.tail01b.setRotationPoint(0.0F, 0.5F, 0.0F);
		this.tail01b.addBox(-1.5F, 0.0F, -1.0F, 3, 1, 7, 0.0F);
		this.tail03 = new ModelRenderer(this, 43, 54);
		this.tail03.setRotationPoint(0.0F, 0.5F, 5.9F);
		this.tail03.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 6, 0.0F);
		this.head.addChild(this.snout);
		this.neck02.addChild(this.neck03);
		this.neck01a.addChild(this.body);
		this.head.addChild(this.lowerJaw);
		this.neck01a.addChild(this.neck01b);
		this.body.addChild(this.tail0a);
		this.neck01a.addChild(this.neck02);
		this.neck03.addChild(this.head);
		this.head.addChild(this.lEye);
		this.head.addChild(this.rEye);
		this.tail0a.addChild(this.tail02);
		this.head.addChild(this.headB);
		this.tail0a.addChild(this.tail01b);
		this.tail02.addChild(this.tail03);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		float pticks = Minecraft.getMinecraft().getRenderPartialTicks();
		float time = ((entity.ticksExisted + pticks) * 0.2F);
		float angle = 0.34906585039F;
		EntityBlindworm blindworm = (EntityBlindworm) entity;
		if (blindworm.motionX != 0 || blindworm.motionZ != 0) {
			this.neck01a.offsetX = 0.3F * MathHelper.cos(time);
			this.neck01a.rotateAngleY = angle * MathHelper.sin(time);
			this.neck02.rotateAngleY = angle * MathHelper.sin(time - 5);
			this.neck02.rotateAngleX = 0;
			this.body.rotateAngleY = angle * MathHelper.sin(time + 5);
			this.tail01b.rotateAngleY = angle * MathHelper.sin(time + 11);
			this.tail02.rotateAngleY = angle * MathHelper.sin(time + 4);
			this.tail03.rotateAngleY = angle * MathHelper.sin(time + 2);
			this.head.rotateAngleY = this.neck02.rotateAngleY;
			this.head.rotateAngleZ = 0.174532925F * MathHelper.cos(time - 5);
			this.head.rotateAngleX = 0;
			this.head.rotateAngleZ = 0;
		} else {
			this.neck01b.addBox(-2.3F, -1.49F, -6.0F, 2, 3, 8, MathHelper.sin(time));

		}
		this.neck01a.render(scale);
		this.head.rotateAngleY = 0.001F * MathHelper.sin(time);
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
