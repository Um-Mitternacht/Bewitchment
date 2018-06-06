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
public class ModelWitchesPants extends ModelBase {
	public ModelRenderer legRight;
	public ModelRenderer legRight2;
	public ModelRenderer legLeft;
	public ModelRenderer legLeft2;

	public ModelWitchesPants() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.legRight = new ModelRenderer(this, 25, 15);
		this.legRight.setRotationPoint(1.9F, 12.0F, 0.1F);
		this.legRight.addBox(-2.1F, 0.0F, -3.01F, 5, 10, 6);
		this.legRight.mirror = true;
		this.legRight2 = new ModelRenderer(this, 38, 32);
		this.legRight2.setRotationPoint(-1.9F, 10.0F, 0.0F);
		this.legRight2.addBox(-0.1F, 0.0F, -3.51F, 6, 2, 7);
		this.legRight2.mirror = true;
		this.setRotationAngles(this.legRight2, 0.0F, 0.0F, -0.03490658503988659F);
		this.legRight.addChild(this.legRight2);
		this.legLeft = new ModelRenderer(this, 25, 15);
		this.legLeft.setRotationPoint(-1.9F, 12.0F, 0.1F);
		this.legLeft.addBox(-2.9F, -0.01F, -3.0F, 5, 10, 6);
		this.legLeft2 = new ModelRenderer(this, 38, 32);
		this.legLeft2.setRotationPoint(1.9F, 10.0F, 0.0F);
		this.legLeft2.addBox(-6.1F, 0.0F, -3.51F, 6, 2, 7);
		this.legLeft2.mirror = true;
		this.setRotationAngles(this.legLeft2, 0.0F, 0.0F, 0.03490658503988659F);
		this.legLeft.addChild(this.legLeft2);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		this.legRight.render(scale);
		this.legLeft.render(scale);
	}

	private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
