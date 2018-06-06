package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Witches attire (Wicked witch) by Ingoleth
 */
@SideOnly(Side.CLIENT)
public class ModelWitchesHat extends ModelBase {
	public ModelRenderer hat1;
	public ModelRenderer hat2;
	public ModelRenderer hatWing;
	public ModelRenderer hatWingBack;
	public ModelRenderer hatWingFront;

	public ModelWitchesHat() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.hat1 = new ModelRenderer(this, 2, 36);
		this.hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hat1.addBox(-4.5F, -10.5F, -4.5F, 9, 5, 9);
		this.hat2 = new ModelRenderer(this, 44, 42);
		this.hat2.setRotationPoint(0.0F, -15.5F, 0.0F);
		this.hat2.addBox(-2.5F, -1.0F, -2.5F, 5, 6, 5);
		this.hat1.addChild(this.hat2);
		this.hatWing = new ModelRenderer(this, 0, 51);
		this.hatWing.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.hatWing.addBox(-8.0F, -1.0F, -6.0F, 16, 1, 12);
		this.setRotationAngles(this.hatWing, 0.03490658503988659F, 0.0F, 0.06981317007977318F);
		this.hat1.addChild(this.hatWing);
		this.hatWingBack = new ModelRenderer(this, 10, 61);
		this.hatWingBack.setRotationPoint(0.09429643F, -6.205146F, 5.961446F);
		this.hatWingBack.addBox(-8.0F, 0.0F, 0.0F, 16, 1, 2);
		this.setRotationAngles(this.hatWingBack, -0.22689281940401965F, 0.0F, 0.06981317007977318F);
		this.hat1.addChild(this.hatWingBack);
		this.hatWingFront = new ModelRenderer(this, 10, 61);
		this.hatWingFront.setRotationPoint(0.065082826F, -5.787372F, -6.0312443F);
		this.hatWingFront.addBox(-8.0F, 0.0F, -2.0F, 16, 1, 2);
		this.setRotationAngles(this.hatWingFront, 0.29670600612854964F, 0.0F, 0.06981317007977318F);
		this.hat1.addChild(this.hatWingFront);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		this.hat1.render(scale);
	}

	private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
