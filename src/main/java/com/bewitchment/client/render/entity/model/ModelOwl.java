package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.entity.living.familiar.EntityOwl;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelOwl extends ModelBase {

	public ModelRenderer owlBody;
	public ModelRenderer Tail1;
	public ModelRenderer tailLeft;
	public ModelRenderer tailRight;
	public ModelRenderer owlHead;
	public ModelRenderer owlBeak;
	public ModelRenderer owlLeftEar;
	public ModelRenderer owlRightEar;
	public ModelRenderer owlLeftClaw;
	public ModelRenderer owlRightClaw;
	public ModelRenderer wingLeft1;
	public ModelRenderer wingLeft2;
	public ModelRenderer wingLeft3;
	public ModelRenderer wingRight1;
	public ModelRenderer wingRight2;
	public ModelRenderer wingRight3;

	public ModelOwl() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.owlBody = new ModelRenderer(this, 24, 16);
		this.owlBody.setRotationPoint(-6.0F, 8.0F, -4.0F);
		this.owlBody.addBox(0.0F, 0.0F, 0.0F, 12, 14, 8);
		this.setRotationAngles(this.owlBody, 0.08726646259971647F, 0.0F, 0.0F);
		this.Tail1 = new ModelRenderer(this, 25, 45);
		this.Tail1.setRotationPoint(4.0F, 10.5F, 8.0F);
		this.Tail1.addBox(0.0F, 0.0F, 0.0F, 4, 2, 8);
		this.setRotationAngles(this.Tail1, -0.6981317007977318F, 0.0F, 0.0F);
		this.owlBody.addChild(this.Tail1);
		this.tailLeft = new ModelRenderer(this, 42, 40);
		this.tailLeft.setRotationPoint(4.0F, 10.5F, 8.0F);
		this.tailLeft.addBox(-4.0F, 0.0F, 0.0F, 4, 2, 6);
		this.setRotationAngles(this.tailLeft, -0.5943842657920599F, -0.4648705475027897F, -0.4054912098102699F);
		this.owlBody.addChild(this.tailLeft);
		this.tailRight = new ModelRenderer(this, 42, 40);
		this.tailRight.setRotationPoint(8.0F, 10.5F, 8.0F);
		this.tailRight.addBox(0.0F, 0.0F, 0.0F, 4, 2, 6);
		this.setRotationAngles(this.tailRight, -0.5943842657920599F, 0.4648705475027897F, 0.4054912098102699F);
		this.owlBody.addChild(this.tailRight);
		this.owlHead = new ModelRenderer(this, 28, 0);
		this.owlHead.setRotationPoint(6.0F, 0.0F, 4.0F);
		this.owlHead.addBox(-5.0F, -8.0F, -4.0F, 10, 8, 8);
		this.setRotationAngles(this.owlHead, -0.06981317007977318F, 0.0F, 0.0F);
		this.owlBody.addChild(this.owlHead);
		this.owlBeak = new ModelRenderer(this, 14, 1);
		this.owlBeak.setRotationPoint(-1.0F, -4.0F, -5.0F);
		this.owlBeak.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
		this.owlHead.addChild(this.owlBeak);
		this.owlLeftEar = new ModelRenderer(this, 27, 1);
		this.owlLeftEar.setRotationPoint(-4.0F, -7.4F, 0.0F);
		this.owlLeftEar.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2);
		this.setRotationAngles(this.owlLeftEar, -0.3490658503988659F, 0.0F, -0.2617993877991494F);
		this.owlHead.addChild(this.owlLeftEar);
		this.owlRightEar = new ModelRenderer(this, 27, 1);
		this.owlRightEar.setRotationPoint(4.0F, -7.4F, 0.0F);
		this.owlRightEar.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2);
		this.setRotationAngles(this.owlRightEar, -0.3490658503988659F, 0.0F, 0.2617993877991494F);
		this.owlHead.addChild(this.owlRightEar);
		this.owlLeftClaw = new ModelRenderer(this, 28, 56);
		this.owlLeftClaw.setRotationPoint(3.0F, 14.0F, 2.5F);
		this.owlLeftClaw.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 6);
		this.setRotationAngles(this.owlLeftClaw, -0.08726646259971647F, 0.08726646259971647F, 0.0F);
		this.owlBody.addChild(this.owlLeftClaw);
		this.owlRightClaw = new ModelRenderer(this, 28, 56);
		this.owlRightClaw.setRotationPoint(9.0F, 14.0F, 2.5F);
		this.owlRightClaw.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 6);
		this.setRotationAngles(this.owlRightClaw, -0.08726646259971647F, -0.08726646259971647F, 0.0F);
		this.owlBody.addChild(this.owlRightClaw);
		this.wingLeft1 = new ModelRenderer(this, 2, 14);
		this.wingLeft1.setRotationPoint(0.0F, 1.0F, 4.0F);
		this.wingLeft1.addBox(-6.0F, 0.0F, -1.0F, 6, 8, 2);
		this.setRotationAngles(this.wingLeft1, 0.08726646259971647F, 0.6108652381980153F, -1.2217304763960306F);
		this.owlBody.addChild(this.wingLeft1);
		this.wingLeft2 = new ModelRenderer(this, 2, 29);
		this.wingLeft2.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.wingLeft2.addBox(-6.0F, 0.0F, -0.99F, 6, 8, 2);
		this.setRotationAngles(this.wingLeft2, 0.0F, 0.0F, -0.4363323129985824F);
		this.wingLeft1.addChild(this.wingLeft2);
		this.wingLeft3 = new ModelRenderer(this, 2, 44);
		this.wingLeft3.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.wingLeft3.addBox(-6.0F, 0.0F, -1.0F, 6, 8, 2);
		this.setRotationAngles(this.wingLeft3, 0.0F, 0.0F, -0.3490658503988659F);
		this.wingLeft2.addChild(this.wingLeft3);
		this.wingRight1 = new ModelRenderer(this, 2, 14);
		this.wingRight1.setRotationPoint(12.0F, 1.0F, 4.0F);
		this.wingRight1.addBox(0.0F, 0.0F, -1.0F, 6, 8, 2);
		this.setRotationAngles(this.wingRight1, 0.08726646259971647F, -0.6108652381980153F, 1.2217304763960306F);
		this.owlBody.addChild(this.wingRight1);
		this.wingRight2 = new ModelRenderer(this, 2, 29);
		this.wingRight2.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.wingRight2.addBox(0.0F, 0.0F, -0.99F, 6, 8, 2);
		this.setRotationAngles(this.wingRight2, 0.0F, 0.0F, 0.4363323129985824F);
		this.wingRight1.addChild(this.wingRight2);
		this.wingRight3 = new ModelRenderer(this, 2, 44);
		this.wingRight3.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.wingRight3.addBox(0.0F, 0.0F, -1.0F, 6, 8, 2);
		this.setRotationAngles(this.wingRight3, 0.0F, 0.0F, 0.3490658503988659F);
		this.wingRight2.addChild(this.wingRight3);
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
		setRotationAngles(owlHead, (float) (headPitch * Math.PI / 360), (float) (netHeadYaw * Math.PI / 360), 0);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
		EntityOwl owl = (EntityOwl) entitylivingbaseIn;
		if (owl.onGround) {
			if (owl.isSitting()) {
				setSittingStance();
			} else {
				setWanderingStance();
			}
		} else {
			setFlyingStance();
			wingRight1.rotateAngleY = MathHelper.sin(limbSwing * 2) * limbSwingAmount;
			wingLeft1.rotateAngleY = -wingRight1.rotateAngleY;
		}
	}

	private void setFlyingStance() {
		this.setRotationAngles(this.owlLeftClaw, -0.08726646259971647F, 0.08726646259971647F, 0.0F);
		this.setRotationAngles(this.owlRightClaw, -0.08726646259971647F, -0.08726646259971647F, 0.0F);
		this.setRotationAngles(this.owlBody, 0.5F, 0.0F, 0.0F);
		this.setRotationAngles(this.wingLeft1, 0.08726646259971647F, 0.6108652381980153F, 0F);
		this.setRotationAngles(this.wingRight1, 0.08726646259971647F, -0.6108652381980153F, 0F);
	}

	private void setWanderingStance() {
		this.setRotationAngles(this.owlLeftClaw, -0.08726646259971647F, 0.08726646259971647F, 0.0F);
		this.setRotationAngles(this.owlRightClaw, -0.08726646259971647F, -0.08726646259971647F, 0.0F);
		this.setRotationAngles(this.owlBody, 0.2F, 0.0F, 0.0F);
		this.setRotationAngles(this.wingLeft1, 0.08726646259971647F, 0.6108652381980153F, -1.2217304763960306F);
		this.setRotationAngles(this.wingRight1, 0.08726646259971647F, -0.6108652381980153F, 1.2217304763960306F);
	}

	private void setSittingStance() {
		this.setRotationAngles(owlLeftClaw, -0.08726646259971647F, 0.3f, 0);
		this.setRotationAngles(owlRightClaw, -0.08726646259971647F, -0.3f, 0);
		this.setRotationAngles(this.owlBody, 0.08726646259971647F, 0.0F, 0.0F);
		this.setRotationAngles(this.wingLeft1, 0.08726646259971647F, 0.6108652381980153F, -1.2217304763960306F);
		this.setRotationAngles(this.wingRight1, 0.08726646259971647F, -0.6108652381980153F, 1.2217304763960306F);
	}
}
