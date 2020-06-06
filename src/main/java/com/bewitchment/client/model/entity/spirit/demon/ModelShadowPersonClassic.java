package com.bewitchment.client.model.entity.spirit.demon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * shadowpersonClassic - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelShadowPersonClassic extends ModelBiped {
	public ModelRenderer bipedBody;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedHead;
	public ModelRenderer lArm2;
	public ModelRenderer rArm2;
	public ModelRenderer lLeg02;
	public ModelRenderer rLeg02;
	public ModelRenderer hatBrim;
	public ModelRenderer hatPipe;
	
	public ModelShadowPersonClassic() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, -1.8F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		this.hatBrim = new ModelRenderer(this, 0, 35);
		this.hatBrim.setRotationPoint(0.0F, -5.8F, 0.0F);
		this.hatBrim.addBox(-6.0F, -0.5F, -6.0F, 12, 1, 12, 0.0F);
		this.lArm2 = new ModelRenderer(this, 40, 16);
		this.lArm2.setRotationPoint(1.0F, 6.0F, -0.0F);
		this.lArm2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.mirror = true;
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, -0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
		this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.10000736613927509F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
		this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.10000736613927509F);
		this.rLeg02 = new ModelRenderer(this, 0, 16);
		this.rLeg02.mirror = true;
		this.rLeg02.setRotationPoint(0.0F, 7.9F, 0.0F);
		this.rLeg02.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rArm2 = new ModelRenderer(this, 40, 16);
		this.rArm2.mirror = true;
		this.rArm2.setRotationPoint(-1.0F, 6.0F, -0.0F);
		this.rArm2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
		this.lLeg02 = new ModelRenderer(this, 0, 16);
		this.lLeg02.setRotationPoint(0.0F, 7.9F, 0.0F);
		this.lLeg02.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
		this.hatPipe = new ModelRenderer(this, 0, 49);
		this.hatPipe.setRotationPoint(0.0F, -0.3F, 0.0F);
		this.hatPipe.addBox(-4.5F, -3.5F, -4.5F, 9, 4, 9, 0.0F);
		this.bipedHead.addChild(this.hatBrim);
		this.bipedLeftArm.addChild(this.lArm2);
		this.bipedBody.addChild(this.bipedLeftLeg);
		this.bipedBody.addChild(this.bipedRightLeg);
		this.bipedBody.addChild(this.bipedLeftArm);
		this.bipedBody.addChild(this.bipedRightArm);
		this.bipedRightLeg.addChild(this.rLeg02);
		this.bipedBody.addChild(this.bipedHead);
		this.bipedRightArm.addChild(this.rArm2);
		this.bipedLeftLeg.addChild(this.lLeg02);
		this.hatBrim.addChild(this.hatPipe);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bipedBody.render(f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netbipedHeadYaw, bipedHeadPitch, scaleFactor, entity);
		
		boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
		this.bipedHead.rotateAngleY = netbipedHeadYaw * 0.017453292F;
		
		if (flag) {
			this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
		}
		else {
			this.bipedHead.rotateAngleX = bipedHeadPitch * 0.017453292F;
		}
		float swingMod = 0.3F;
		
		this.bipedRightArm.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount;
		
		this.bipedLeftLeg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount;
		
		setLivingAnimations((EntityLivingBase) entity, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
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
