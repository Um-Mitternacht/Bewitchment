package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Mantle - Ingoleth
 * Created using Tabula 5.1.0
 */
public class ModelPouch extends ModelBiped {

	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/models/pouch.png");
	public ModelRenderer pouch1;
	public ModelRenderer pouch2;
	public ModelRenderer PouchTop1;
	public ModelRenderer pouchTop2;
	public ModelRenderer pouchTop3;

	public ModelPouch() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.pouchTop3 = new ModelRenderer(this, 1, 28);
		this.pouchTop3.setRotationPoint(1.2999999523162842F, 3.0F, -1.0F);
		this.pouchTop3.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
		this.pouch1 = new ModelRenderer(this, 1, 1);
		this.pouch1.setRotationPoint(-2.0F, 12.0F, -2.0F);
		this.pouch1.addBox(-2.0F, 0.0F, -4.0F, 7, 6, 4, 0.0F);
		this.pouchTop2 = new ModelRenderer(this, 1, 23);
		this.pouchTop2.setRotationPoint(0.0F, 0.0F, -4.0F);
		this.pouchTop2.addBox(0.0F, 0.0F, -1.0F, 8, 3, 1, 0.0F);
		this.pouch2 = new ModelRenderer(this, 1, 12);
		this.pouch2.setRotationPoint(-1.0F, 6.0F, -3.0F);
		this.pouch2.addBox(0.0F, 0.0F, 0.0F, 5, 1, 3, 0.0F);
		this.PouchTop1 = new ModelRenderer(this, 1, 17);
		this.PouchTop1.setRotationPoint(-2.5F, 0.0F, 0.0F);
		this.PouchTop1.addBox(0.0F, -1.0F, -4.0F, 8, 1, 4, 0.0F);
		this.pouchTop2.addChild(this.pouchTop3);
		this.PouchTop1.addChild(this.pouchTop2);
		this.pouch1.addChild(this.pouch2);
		this.pouch1.addChild(this.PouchTop1);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.pouch1.render(1);
		if (entityIn.isSneaking()) {
			this.pouch1.rotateAngleX = -0.78539816339F;
		} else if (bipedLeftLeg.rotateAngleX < 0) {
			this.pouch1.rotateAngleX = bipedLeftLeg.rotateAngleX;
		} else {
			this.pouch1.rotateAngleX = 0;
		}
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

