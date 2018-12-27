package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelWerewolfHead - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelWerewolfHead extends ModelBase {
	public ModelRenderer wolfHead;
	public ModelRenderer jawUpper01;
	public ModelRenderer jawLower;
	public ModelRenderer lEarFeral01;
	public ModelRenderer rEarFeral01;
	public ModelRenderer lCheekFur;
	public ModelRenderer rCheekFur;
	public ModelRenderer lEarClassic;
	public ModelRenderer rEarClassic;
	public ModelRenderer snout;
	public ModelRenderer jawUpper02;
	public ModelRenderer upperTeeth01;
	public ModelRenderer upperTeeth02;
	public ModelRenderer upperTeeth03;
	public ModelRenderer lowerTeeth01;
	public ModelRenderer lowerTeeth02;
	public ModelRenderer lEarFeral02;
	public ModelRenderer lEarFeral03;
	public ModelRenderer rEarFeral02;
	public ModelRenderer lEarFeral03_1;

	public ModelWerewolfHead() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.lowerTeeth01 = new ModelRenderer(this, 68, 41);
		this.lowerTeeth01.setRotationPoint(0.0F, -3.8F, 0.1F);
		this.lowerTeeth01.addBox(-0.6F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
		this.upperTeeth01 = new ModelRenderer(this, 56, 37);
		this.upperTeeth01.setRotationPoint(0.0F, -3.2F, -1.0F);
		this.upperTeeth01.addBox(-0.3F, -0.7F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(upperTeeth01, 0.0F, 0.0F, 0.136659280431156F);
		this.upperTeeth03 = new ModelRenderer(this, 63, 38);
		this.upperTeeth03.setRotationPoint(0.0F, -3.2F, -1.0F);
		this.upperTeeth03.addBox(-2.27F, -0.7F, -0.8F, 3, 0, 1, 0.0F);
		this.setRotateAngle(upperTeeth03, 0.0F, 0.0F, 0.136659280431156F);
		this.rEarFeral01 = new ModelRenderer(this, 70, 0);
		this.rEarFeral01.mirror = true;
		this.rEarFeral01.setRotationPoint(-2.7F, -2.2F, 1.0F);
		this.rEarFeral01.addBox(-1.0F, 0.0F, -0.9F, 1, 3, 2, 0.0F);
		this.setRotateAngle(rEarFeral01, 0.5462880558742251F, 0.0F, 0.5462880558742251F);
		this.jawUpper01 = new ModelRenderer(this, 20, 36);
		this.jawUpper01.setRotationPoint(1.2F, -4.9F, -1.1F);
		this.jawUpper01.addBox(-1.2F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(jawUpper01, 0.0F, 0.0F, -0.13962634015954636F);
		this.lEarFeral03 = new ModelRenderer(this, 77, 0);
		this.lEarFeral03.setRotationPoint(-0.3F, 0.0F, 0.0F);
		this.lEarFeral03.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lEarFeral03, 0.091106186954104F, 0.0F, -0.22759093446006054F);
		this.rEarFeral02 = new ModelRenderer(this, 77, 0);
		this.rEarFeral02.mirror = true;
		this.rEarFeral02.setRotationPoint(0.0F, 2.8F, 0.1F);
		this.rEarFeral02.addBox(-1.0F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rEarFeral02, 0.36425021489121656F, 0.0F, 0.0F);
		this.rEarClassic = new ModelRenderer(this, 73, 8);
		this.rEarClassic.mirror = true;
		this.rEarClassic.setRotationPoint(-2.2F, -2.0F, 3.0F);
		this.rEarClassic.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(rEarClassic, -0.18203784098300857F, -0.091106186954104F, 0.0F);
		this.jawLower = new ModelRenderer(this, 39, 37);
		this.jawLower.setRotationPoint(0.0F, -4.8F, -2.5F);
		this.jawLower.addBox(-1.5F, -4.0F, -0.5F, 3, 4, 1, 0.0F);
		this.setRotateAngle(jawLower, 0.136659280431156F, 0.0F, 0.0F);
		this.jawUpper02 = new ModelRenderer(this, 20, 36);
		this.jawUpper02.mirror = true;
		this.jawUpper02.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.jawUpper02.addBox(-3.2F, -3.67F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(jawUpper02, 0.0F, 0.0F, 0.2792526803190927F);
		this.lEarFeral01 = new ModelRenderer(this, 70, 0);
		this.lEarFeral01.setRotationPoint(2.7F, -2.2F, 1.0F);
		this.lEarFeral01.addBox(0.0F, 0.0F, -0.9F, 1, 3, 2, 0.0F);
		this.setRotateAngle(lEarFeral01, 0.5462880558742251F, 0.0F, -0.5462880558742251F);
		this.rCheekFur = new ModelRenderer(this, 38, 4);
		this.rCheekFur.mirror = true;
		this.rCheekFur.setRotationPoint(-3.5F, -2.5F, 1.1F);
		this.rCheekFur.addBox(0.0F, -0.5F, -7.5F, 0, 4, 8, 0.0F);
		this.setRotateAngle(rCheekFur, -0.18203784098300857F, 0.091106186954104F, 0.36425021489121656F);
		this.wolfHead = new ModelRenderer(this, 44, 0);
		this.wolfHead.setRotationPoint(0.0F, 21.0F, 4.0F);
		this.wolfHead.addBox(-3.5F, -5.0F, -3.0F, 7, 5, 6, 0.0F);
		this.setRotateAngle(wolfHead, 1.5707963267948966F, 0.0F, 0.0F);
		this.lEarFeral02 = new ModelRenderer(this, 77, 0);
		this.lEarFeral02.setRotationPoint(0.0F, 2.8F, 0.1F);
		this.lEarFeral02.addBox(0.0F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lEarFeral02, 0.36425021489121656F, 0.0F, 0.0F);
		this.lEarFeral03_1 = new ModelRenderer(this, 77, 0);
		this.lEarFeral03_1.mirror = true;
		this.lEarFeral03_1.setRotationPoint(0.3F, 0.0F, 0.0F);
		this.lEarFeral03_1.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lEarFeral03_1, 0.091106186954104F, 0.0F, 0.22759093446006054F);
		this.lCheekFur = new ModelRenderer(this, 38, 4);
		this.lCheekFur.setRotationPoint(3.5F, -2.5F, 1.1F);
		this.lCheekFur.addBox(0.0F, -0.5F, -7.5F, 0, 4, 8, 0.0F);
		this.setRotateAngle(lCheekFur, -0.18203784098300857F, -0.091106186954104F, -0.36425021489121656F);
		this.lEarClassic = new ModelRenderer(this, 73, 8);
		this.lEarClassic.setRotationPoint(2.2F, -2.0F, 3.0F);
		this.lEarClassic.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(lEarClassic, -0.18203784098300857F, 0.091106186954104F, 0.0F);
		this.snout = new ModelRenderer(this, 29, 35);
		this.snout.setRotationPoint(0.0F, -4.6F, 0.1F);
		this.snout.addBox(-1.0F, -4.5F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(snout, 0.18203784098300857F, 0.0F, 0.0F);
		this.lowerTeeth02 = new ModelRenderer(this, 63, 41);
		this.lowerTeeth02.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lowerTeeth02.addBox(-1.3F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.upperTeeth02 = new ModelRenderer(this, 50, 37);
		this.upperTeeth02.setRotationPoint(0.0F, -3.2F, -1.0F);
		this.upperTeeth02.addBox(-2.2F, -0.7F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(upperTeeth02, 0.0F, 0.0F, 0.136659280431156F);
		this.jawLower.addChild(this.lowerTeeth01);
		this.jawUpper01.addChild(this.upperTeeth01);
		this.jawUpper01.addChild(this.upperTeeth03);
		this.wolfHead.addChild(this.rEarFeral01);
		this.wolfHead.addChild(this.jawUpper01);
		this.lEarFeral01.addChild(this.lEarFeral03);
		this.rEarFeral01.addChild(this.rEarFeral02);
		this.wolfHead.addChild(this.rEarClassic);
		this.wolfHead.addChild(this.jawLower);
		this.jawUpper01.addChild(this.jawUpper02);
		this.wolfHead.addChild(this.lEarFeral01);
		this.wolfHead.addChild(this.rCheekFur);
		this.lEarFeral01.addChild(this.lEarFeral02);
		this.rEarFeral01.addChild(this.lEarFeral03_1);
		this.wolfHead.addChild(this.lCheekFur);
		this.wolfHead.addChild(this.lEarClassic);
		this.wolfHead.addChild(this.snout);
		this.lowerTeeth01.addChild(this.lowerTeeth02);
		this.jawUpper01.addChild(this.upperTeeth02);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.wolfHead.render(f5);
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
