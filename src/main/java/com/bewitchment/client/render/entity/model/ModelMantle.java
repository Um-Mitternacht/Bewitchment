package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;


/**
 * Mantle - Ingoleth
 * Created using Tabula 5.1.0
 */
public class ModelMantle extends ModelBiped {

	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/models/mantle.png");

	public ModelRenderer hood;
	public ModelRenderer cape;
	public ModelRenderer hoodBack;
	public ModelRenderer hoodRight1;
	public ModelRenderer hoodTop2;
	public ModelRenderer hoodLeft1;
	public ModelRenderer hoodRight2;
	public ModelRenderer hoodLeft2;
	public ModelRenderer hoodTop1;
	public ModelRenderer armLeft;
	public ModelRenderer armRight;
	public ModelRenderer capeBack1;
	public ModelRenderer shoulderLeft;
	public ModelRenderer shoulderRight;
	public ModelRenderer CapeBack2;
	public ModelRenderer capeRight1;
	public ModelRenderer capeLeft1;
	public ModelRenderer CapeRightDown1;
	public ModelRenderer capeRight2;
	public ModelRenderer CapeRightDown2;
	public ModelRenderer capeRightFront1;
	public ModelRenderer CapeRightFront2;
	public ModelRenderer CapeLeftDown1;
	public ModelRenderer capeLeft2;
	public ModelRenderer capeLeftDown2;
	public ModelRenderer capeLeftFront1;
	public ModelRenderer CapeLeftFront2;
	public ModelRenderer neck;

	public ModelPlayer playerModel;
	private HashMap<String, Float> valuesMap = new HashMap<>();

	public ModelMantle(ModelPlayer model) {
		playerModel = model;
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.hoodLeft2 = new ModelRenderer(this, 22, 43);
		this.hoodLeft2.mirror = true;
		this.hoodLeft2.setRotationPoint(-7.0F, 0.0F, 0.0F);
		this.hoodLeft2.addBox(0.0F, 0.0F, -5.0F, 1, 4, 9, 0.0F);
		this.setRotateAngle(hoodLeft2, 0.0F, 3.141592653589793F, 0.12217304763960307F);
		this.CapeRightDown2 = new ModelRenderer(this, 33, 23);
		this.CapeRightDown2.setRotationPoint(-1.0F, 20.0F, 0.0F);
		this.CapeRightDown2.addBox(-5.0F, 0.0F, -1.0F, 6, 4, 1, 0.0F);
		this.setRotateAngle(CapeRightDown2, -0.19198621771937624F, 0.0F, 0.0F);
		this.cape = new ModelRenderer(this, 36, 42);
		this.cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cape.addBox(-4.01F, -1.0F, 1.5F, 8, 6, 3, 0.0F);
		this.shoulderRight = new ModelRenderer(this, 1, 29);
		this.shoulderRight.mirror = true;
		this.shoulderRight.setRotationPoint(-1.0F, -3.0F, 0.1F);
		this.shoulderRight.addBox(0.0F, -1.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(shoulderRight, 0.0F, 0.0F, 0.12217304763960307F);
		this.capeLeftDown2 = new ModelRenderer(this, 33, 23);
		this.capeLeftDown2.setRotationPoint(-1.0F, 20.0F, 0.0F);
		this.capeLeftDown2.addBox(-5.0F, 0.0F, 0.0F, 6, 4, 1, 0.0F);
		this.setRotateAngle(capeLeftDown2, 0.19198621771937624F, 0.0F, 0.0F);
		this.hoodTop1 = new ModelRenderer(this, 27, 31);
		this.hoodTop1.setRotationPoint(-3.5F, -4.5F, -4.010000228881836F);
		this.hoodTop1.addBox(-4.0F, 0.0F, -9.0F, 4, 1, 9, 0.0F);
		this.setRotateAngle(hoodTop1, 3.141592653589793F, 0.0F, -3.01941960595019F);
		this.armLeft = new ModelRenderer(this, 40, 16);
		this.armLeft.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.armLeft.addBox(-3.0F, -2.0F, -2.0F, 0, 0, 0, 0.0F);
		this.capeRight1 = new ModelRenderer(this, 48, 1);
		this.capeRight1.setRotationPoint(4.0F, 0.0F, 0.0F);
		this.capeRight1.addBox(-5.0F, 0.0F, 0.0F, 5, 20, 1, 0.0F);
		this.setRotateAngle(capeRight1, 0.06981317007977318F, 3.141592653589793F, 0.0F);
		this.CapeRightFront2 = new ModelRenderer(this, 48, 23);
		this.CapeRightFront2.setRotationPoint(2.5F, 20.0F, -2.299999952316284F);
		this.CapeRightFront2.addBox(-5.0F, 0.0F, -1.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(CapeRightFront2, -0.19198621771937624F, 0.0F, 0.0F);
		this.capeBack1 = new ModelRenderer(this, 1, 1);
		this.capeBack1.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.capeBack1.addBox(-4.0F, 0.0F, -1.0F, 8, 20, 1, 0.0F);
		this.setRotateAngle(capeBack1, 0.08726646259971647F, 0.0F, 0.0F);
		this.hoodBack = new ModelRenderer(this, 12, 57);
		this.hoodBack.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.hoodBack.addBox(-4.0F, 0.0F, 3.5F, 8, 4, 1, 0.0F);
		this.setRotateAngle(hoodBack, 0.15707963267948966F, 0.0F, 0.0F);
		this.CapeRightDown1 = new ModelRenderer(this, 48, 23);
		this.CapeRightDown1.setRotationPoint(0.0F, 20.0F, 1.0F);
		this.CapeRightDown1.addBox(-5.0F, 0.0F, -1.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(CapeRightDown1, -0.19198621771937624F, 0.0F, 0.0F);
		this.armRight = new ModelRenderer(this, 40, 16);
		this.armRight.mirror = true;
		this.armRight.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.armRight.addBox(-1.0F, -2.0F, -2.0F, 0, 0, 0, 0.0F);
		this.CapeLeftFront2 = new ModelRenderer(this, 48, 23);
		this.CapeLeftFront2.setRotationPoint(2.5F, 20.0F, 2.299999952316284F);
		this.CapeLeftFront2.addBox(-5.0F, 0.0F, 0.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(CapeLeftFront2, 0.19198621771937624F, 0.0F, 0.0F);
		this.capeLeft1 = new ModelRenderer(this, 48, 1);
		this.capeLeft1.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.capeLeft1.addBox(-5.0F, 0.0F, -1.0F, 5, 20, 1, 0.0F);
		this.setRotateAngle(capeLeft1, -0.06981317007977318F, 0.0F, 0.0F);
		this.CapeBack2 = new ModelRenderer(this, 1, 23);
		this.CapeBack2.setRotationPoint(0.0F, 20.0F, -1.0F);
		this.CapeBack2.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 1, 0.0F);
		this.setRotateAngle(CapeBack2, 0.17453292519943295F, 0.0F, 0.0F);
		this.capeRightFront1 = new ModelRenderer(this, 48, 1);
		this.capeRightFront1.setRotationPoint(-3.5F, -0.009999999776482582F, 1.4900000095367432F);
		this.capeRightFront1.addBox(-2.5F, 0.0F, -3.299999952316284F, 5, 20, 1, 0.0F);
		this.setRotateAngle(capeRightFront1, 0.0F, 1.5707963267948966F, 0.0F);
		this.hoodRight2 = new ModelRenderer(this, 22, 43);
		this.hoodRight2.mirror = true;
		this.hoodRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hoodRight2.addBox(0.0F, 0.0F, -4.0F, 1, 4, 9, 0.0F);
		this.setRotateAngle(hoodRight2, 0.0F, 0.0F, -0.12217304763960307F);
		this.hoodRight1 = new ModelRenderer(this, 0, 43);
		this.hoodRight1.mirror = true;
		this.hoodRight1.setRotationPoint(3.5F, -4.0F, -0.5F);
		this.hoodRight1.addBox(0.0F, -4.0F, -4.0F, 1, 4, 9, 0.0F);
		this.setRotateAngle(hoodRight1, 0.12217304763960307F, 0.0F, 0.0F);
		this.hood = new ModelRenderer(this, 12, 42);
		this.hood.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hood.addBox(-4.0F, -8.1F, 3.51F, 8, 4, 1, 0.0F);
		this.CapeLeftDown1 = new ModelRenderer(this, 48, 23);
		this.CapeLeftDown1.setRotationPoint(0.0F, 20.0F, -1.0F);
		this.CapeLeftDown1.addBox(-5.0F, 0.0F, 0.0F, 5, 4, 1, 0.0F);
		this.setRotateAngle(CapeLeftDown1, 0.19198621771937624F, 0.0F, 0.0F);
		this.shoulderLeft = new ModelRenderer(this, 1, 29);
		this.shoulderLeft.mirror = true;
		this.shoulderLeft.setRotationPoint(1.0F, -3.0F, 0.1F);
		this.shoulderLeft.addBox(0.0F, -1.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(shoulderLeft, 3.141592653589793F, 0.0F, 3.01941960595019F);
		this.capeRight2 = new ModelRenderer(this, 33, 1);
		this.capeRight2.setRotationPoint(-5.0F, 0.0F, 1.0F);
		this.capeRight2.addBox(-6.0F, 0.0F, -1.0F, 6, 20, 1, 0.0F);
		this.setRotateAngle(capeRight2, 0.03490658503988659F, 1.5707963267948966F, 0.06108652381F);
		this.hoodTop2 = new ModelRenderer(this, 27, 31);
		this.hoodTop2.setRotationPoint(-3.5F, -4.5F, 5.0F);
		this.hoodTop2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 9, 0.0F);
		this.setRotateAngle(hoodTop2, 0.0F, 3.141592653589793F, -0.12217304763960307F);
		this.hoodLeft1 = new ModelRenderer(this, 0, 43);
		this.hoodLeft1.mirror = true;
		this.hoodLeft1.setRotationPoint(-8.0F, 0.0F, -4.01F);
		this.hoodLeft1.addBox(-1.0F, -4.0F, -9.0F, 1, 4, 9, 0.0F);
		this.setRotateAngle(hoodLeft1, 0.0F, 3.141592653589793F, 0.0F);
		this.capeLeft2 = new ModelRenderer(this, 33, 1);
		this.capeLeft2.setRotationPoint(-5.0F, 0.0F, -1.0F);
		this.capeLeft2.addBox(-6.0F, 0.0F, 0.0F, 6, 20, 1, 0.0F);
		this.setRotateAngle(capeLeft2, -0.03490658503988659F, -1.5707963267948966F, 0.06108652381F);
		this.capeLeftFront1 = new ModelRenderer(this, 48, 1);
		this.capeLeftFront1.setRotationPoint(-3.5F, -0.009999999776482582F, -1.5F);
		this.capeLeftFront1.addBox(-2.5F, 0.0F, 2.299999952316284F, 5, 20, 1, 0.0F);
		this.setRotateAngle(capeLeftFront1, 0.0F, -1.5707963267948966F, 0.0F);
		this.neck = new ModelRenderer(this, 36, 42);
		this.neck.setRotationPoint(0.0F, -1.0F, 4.5F);
		this.neck.addBox(-4.0F, -6.0F, -3.0F, 8, 6, 3, 0.0F);
		this.setRotateAngle(neck, 1.3089969389957472F, 0.0F, 0.0F);
		this.hoodRight1.addChild(this.hoodLeft2);
		this.capeRight2.addChild(this.CapeRightDown2);
		this.armRight.addChild(this.shoulderRight);
		this.capeLeft2.addChild(this.capeLeftDown2);
		this.hoodRight1.addChild(this.hoodTop1);
		this.capeBack1.addChild(this.capeRight1);
		this.capeRightFront1.addChild(this.CapeRightFront2);
		this.cape.addChild(this.capeBack1);
		this.hood.addChild(this.hoodBack);
		this.capeRight1.addChild(this.CapeRightDown1);
		this.capeLeftFront1.addChild(this.CapeLeftFront2);
		this.capeBack1.addChild(this.capeLeft1);
		this.capeBack1.addChild(this.CapeBack2);
		this.capeRight2.addChild(this.capeRightFront1);
		this.hoodRight1.addChild(this.hoodRight2);
		this.hood.addChild(this.hoodRight1);
		this.capeLeft1.addChild(this.CapeLeftDown1);
		this.armLeft.addChild(this.shoulderLeft);
		this.capeRight1.addChild(this.capeRight2);
		this.hoodRight1.addChild(this.hoodTop2);
		this.hoodRight1.addChild(this.hoodLeft1);
		this.capeLeft1.addChild(this.capeLeft2);
		this.capeLeft2.addChild(this.capeLeftFront1);

	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		if (limbSwingAmount > 0.05) {
			cape.rotateAngleX = getAndUpdateRotation((EntityPlayer) entityIn, limbSwingAmount);
			capeRightFront1.rotateAngleY = 1.57079632679F - limbSwingAmount * 1.5F;
			capeLeftFront1.rotateAngleY = -capeRightFront1.rotateAngleY;
			armRight.rotateAngleX = -0.52359877559F / 0.333333F * limbSwingAmount;
			armLeft.rotateAngleX = armRight.rotateAngleX;
			armRight.offsetY = -limbSwingAmount;
			armLeft.offsetY = -limbSwingAmount;
		} else {
			armRight.rotateAngleX = 0;
			armLeft.rotateAngleX = 0;
		}

		this.hood.rotateAngleX = bipedHead.rotateAngleX;
		this.hood.rotateAngleY = playerModel.bipedHead.rotateAngleY;

		if (entityIn.isSneaking()) {
			capeLeftFront1.rotateAngleY = 0;
			capeRightFront1.rotateAngleY = capeLeftFront1.rotateAngleY;
			this.hood.rotateAngleX = bipedHead.rotateAngleX + 0.52359877559F;
		}

		this.cape.render(1);
		this.hood.render(1);
		this.neck.render(1);
		this.armLeft.render(1);
		this.armRight.render(1);
	}

	private float getAndUpdateRotation(EntityPlayer entity, float limbSwingAmount) {
		String key = entity.getUniqueID().toString();
		if (!valuesMap.containsKey(key)) {
			valuesMap.put(key, 0f);
		}
		float currentRotation = valuesMap.get(key);
		if (entity.moveForward > 0) {
			currentRotation = limbSwingAmount;
		} else {
			currentRotation = limbSwingAmount / 1.65F;
		}
		valuesMap.put(key, currentRotation);
		return currentRotation;
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
