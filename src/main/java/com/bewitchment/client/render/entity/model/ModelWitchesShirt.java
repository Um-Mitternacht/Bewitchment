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
public class ModelWitchesShirt extends ModelBase {
	public ModelRenderer chest;
	public ModelRenderer armRight;
	public ModelRenderer shoulderRight;
	public ModelRenderer armLeft;
	public ModelRenderer shoulderLeft;

	public ModelWitchesShirt() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.chest = new ModelRenderer(this, 0, 13);
		this.chest.setRotationPoint(0.0F, 0.0F, 0.1F);
		this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
		this.armRight = new ModelRenderer(this, 26, 2);
		this.armRight.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.armRight.addBox(-1.0F, 2.2F, -2.0F, 4, 8, 4);
		this.armRight.mirror = true;
		this.shoulderRight = new ModelRenderer(this, 0, 0);
		this.shoulderRight.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.shoulderRight.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
		this.setRotationAngles(this.shoulderRight, 0.0F, 0.0F, 0.05235987755982988F);
		this.armRight.addChild(this.shoulderRight);
		this.armLeft = new ModelRenderer(this, 48, 19);
		this.armLeft.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.armLeft.addBox(-3.0F, 2.2F, -2.0F, 4, 8, 4);
		this.shoulderLeft = new ModelRenderer(this, 0, 0);
		this.shoulderLeft.setRotationPoint(-0.9993906F, 0.0F, -0.0348995F);
		this.shoulderLeft.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
		this.setRotationAngles(this.shoulderLeft, 0.0018277815515810234F, -0.034858729283454626F, -0.0523916607229643F);
		this.armLeft.addChild(this.shoulderLeft);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		this.chest.render(scale);
		this.armRight.render(scale);
		this.armLeft.render(scale);
	}

	private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
