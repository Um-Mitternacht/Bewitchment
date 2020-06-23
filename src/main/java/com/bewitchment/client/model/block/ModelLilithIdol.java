package com.bewitchment.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * idol_lilith - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelLilithIdol extends ModelBase {
	public ModelRenderer plith;
	public ModelRenderer bottom;
	public ModelRenderer lLeg01;
	public ModelRenderer rLeg01;
	public ModelRenderer body;
	public ModelRenderer loincloth;
	public ModelRenderer bottom2;
	public ModelRenderer chest;
	public ModelRenderer head;
	public ModelRenderer lWing01;
	public ModelRenderer rWing01;
	public ModelRenderer lArm01;
	public ModelRenderer rArm01;
	public ModelRenderer boobs;
	public ModelRenderer lArm02;
	public ModelRenderer rArm02;
	public ModelRenderer hair;
	public ModelRenderer lHorn01;
	public ModelRenderer rHorn01;
	public ModelRenderer lHorn02;
	public ModelRenderer lHorn03;
	public ModelRenderer rHorn02;
	public ModelRenderer rHorn03;
	public ModelRenderer lWing02;
	public ModelRenderer lWing03;
	public ModelRenderer lWing04;
	public ModelRenderer lWing05;
	public ModelRenderer rWing02;
	public ModelRenderer rWing03;
	public ModelRenderer rWing04;
	public ModelRenderer rWing05;
	
	public ModelLilithIdol() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.rArm02 = new ModelRenderer(this, 0, 0);
		this.rArm02.setRotationPoint(-0.1F, 4.4F, 0.0F);
		this.rArm02.addBox(-1.0F, -0.7F, -0.9F, 2, 5, 2, 0.0F);
		this.setRotateAngle(rArm02, 0.0F, 0.0F, 0.2792526803190927F);
		this.lWing01 = new ModelRenderer(this, 37, 9);
		this.lWing01.setRotationPoint(0.3F, -4.1F, 0.0F);
		this.lWing01.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 8, 0.0F);
		this.setRotateAngle(lWing01, 0.7853981633974483F, -0.7853981633974483F, 0.0F);
		this.rLeg01 = new ModelRenderer(this, 0, 20);
		this.rLeg01.mirror = true;
		this.rLeg01.setRotationPoint(-1.3F, 2.7F, 0.5F);
		this.rLeg01.addBox(-1.0F, 0.2F, -1.5F, 2, 8, 3, 0.0F);
		this.head = new ModelRenderer(this, 40, 0);
		this.head.setRotationPoint(0.0F, -6.3F, 0.5F);
		this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
		this.lHorn02 = new ModelRenderer(this, 57, 0);
		this.lHorn02.setRotationPoint(0.0F, -1.5F, 0.0F);
		this.lHorn02.addBox(-0.5F, -1.9F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn02, 0.0F, 0.0F, -0.3490658503988659F);
		this.lWing05 = new ModelRenderer(this, 37, 23);
		this.lWing05.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lWing05.addBox(-0.5F, -1.5F, -1.1F, 1, 6, 1, 0.0F);
		this.lHorn01 = new ModelRenderer(this, 57, 0);
		this.lHorn01.setRotationPoint(1.6F, -4.0F, 0.0F);
		this.lHorn01.addBox(-0.5F, -1.6F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn01, 0.0F, 0.0F, 0.5235987755982988F);
		this.lWing02 = new ModelRenderer(this, 37, 23);
		this.lWing02.setRotationPoint(0.1F, 3.0F, 7.7F);
		this.lWing02.addBox(-0.5F, -1.0F, 1.9F, 1, 7, 1, 0.0F);
		this.setRotateAngle(lWing02, 1.2217304763960306F, 0.0F, 0.0F);
		this.hair = new ModelRenderer(this, 45, 24);
		this.hair.setRotationPoint(0.0F, -3.3F, 1.6F);
		this.hair.addBox(-2.0F, -0.5F, -0.2F, 4, 1, 5, 0.0F);
		this.setRotateAngle(hair, -1.2217304763960306F, 0.0F, 0.0F);
		this.lArm02 = new ModelRenderer(this, 0, 0);
		this.lArm02.mirror = true;
		this.lArm02.setRotationPoint(0.1F, 4.4F, 0.0F);
		this.lArm02.addBox(-1.0F, -0.7F, -0.9F, 2, 5, 2, 0.0F);
		this.setRotateAngle(lArm02, 0.0F, 0.0F, -0.2792526803190927F);
		this.rHorn03 = new ModelRenderer(this, 57, 0);
		this.rHorn03.mirror = true;
		this.rHorn03.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.rHorn03.addBox(-0.5F, -1.8F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn03, 0.0F, 0.0F, 0.2617993877991494F);
		this.chest = new ModelRenderer(this, 19, 14);
		this.chest.setRotationPoint(0.0F, -3.5F, 0.5F);
		this.chest.addBox(-2.5F, -2.8F, -1.5F, 5, 3, 3, 0.0F);
		this.bottom = new ModelRenderer(this, 0, 13);
		this.bottom.setRotationPoint(0.0F, 9.8F, -0.7F);
		this.bottom.addBox(-2.3F, 0.0F, -1.0F, 4, 3, 3, 0.0F);
		this.lLeg01 = new ModelRenderer(this, 0, 20);
		this.lLeg01.setRotationPoint(1.3F, 2.7F, 0.5F);
		this.lLeg01.addBox(-1.0F, 0.2F, -1.5F, 2, 8, 3, 0.0F);
		this.lArm01 = new ModelRenderer(this, 0, 0);
		this.lArm01.mirror = true;
		this.lArm01.setRotationPoint(2.5F, -2.1F, 0.0F);
		this.lArm01.addBox(-1.0F, -0.7F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(lArm01, 0.0F, 0.0F, -0.3490658503988659F);
		this.body = new ModelRenderer(this, 20, 20);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-2.0F, -3.9F, -0.9F, 4, 4, 3, 0.0F);
		this.loincloth = new ModelRenderer(this, 34, 8);
		this.loincloth.setRotationPoint(0.1F, 1.0F, -0.6F);
		this.loincloth.addBox(-2.0F, 0.0F, -0.5F, 4, 8, 1, 0.0F);
		this.setRotateAngle(loincloth, -0.03490658503988659F, 0.0F, 0.0F);
		this.lHorn03 = new ModelRenderer(this, 57, 0);
		this.lHorn03.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.lHorn03.addBox(-0.5F, -1.8F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn03, 0.0F, 0.0F, -0.2617993877991494F);
		this.rWing01 = new ModelRenderer(this, 37, 9);
		this.rWing01.mirror = true;
		this.rWing01.setRotationPoint(-0.3F, -4.1F, 0.0F);
		this.rWing01.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 8, 0.0F);
		this.setRotateAngle(rWing01, 0.7853981633974483F, 0.7853981633974483F, 0.0F);
		this.rWing03 = new ModelRenderer(this, 37, 23);
		this.rWing03.mirror = true;
		this.rWing03.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rWing03.addBox(-0.5F, -0.5F, 0.9F, 1, 6, 1, 0.0F);
		this.plith = new ModelRenderer(this, 0, 0);
		this.plith.setRotationPoint(0.0F, 24.1F, 0.0F);
		this.plith.addBox(-4.0F, -4.1F, -4.0F, 8, 4, 8, 0.0F);
		this.rHorn02 = new ModelRenderer(this, 57, 0);
		this.rHorn02.mirror = true;
		this.rHorn02.setRotationPoint(0.0F, -1.5F, 0.0F);
		this.rHorn02.addBox(-0.5F, -1.9F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn02, 0.0F, 0.0F, 0.3490658503988659F);
		this.rHorn01 = new ModelRenderer(this, 57, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-1.6F, -4.0F, 0.0F);
		this.rHorn01.addBox(-0.5F, -1.6F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn01, 0.0F, 0.0F, -0.5235987755982988F);
		this.rWing04 = new ModelRenderer(this, 37, 23);
		this.rWing04.mirror = true;
		this.rWing04.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rWing04.addBox(-0.5F, -1.0F, -0.1F, 1, 6, 1, 0.0F);
		this.rWing05 = new ModelRenderer(this, 37, 23);
		this.rWing05.mirror = true;
		this.rWing05.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rWing05.addBox(-0.5F, -1.5F, -1.1F, 1, 6, 1, 0.0F);
		this.lWing03 = new ModelRenderer(this, 37, 23);
		this.lWing03.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lWing03.addBox(-0.5F, -0.5F, 0.9F, 1, 6, 1, 0.0F);
		this.bottom2 = new ModelRenderer(this, 11, 20);
		this.bottom2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom2.addBox(1.3F, 0.0F, -1.0F, 1, 3, 3, 0.0F);
		this.rArm01 = new ModelRenderer(this, 0, 0);
		this.rArm01.setRotationPoint(-2.5F, -2.1F, 0.0F);
		this.rArm01.addBox(-1.0F, -0.7F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(rArm01, 0.0F, 0.0F, 0.3490658503988659F);
		this.lWing04 = new ModelRenderer(this, 37, 23);
		this.lWing04.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lWing04.addBox(-0.5F, -1.0F, -0.1F, 1, 6, 1, 0.0F);
		this.rWing02 = new ModelRenderer(this, 37, 23);
		this.rWing02.mirror = true;
		this.rWing02.setRotationPoint(-0.1F, 3.0F, 7.7F);
		this.rWing02.addBox(-0.5F, -1.0F, 1.9F, 1, 7, 1, 0.0F);
		this.setRotateAngle(rWing02, 1.2217304763960306F, 0.0F, 0.0F);
		this.boobs = new ModelRenderer(this, 20, 16);
		this.boobs.setRotationPoint(0.0F, -2.3F, -0.9F);
		this.boobs.addBox(-2.0F, 0.0F, -0.7F, 4, 2, 2, 0.0F);
		this.setRotateAngle(boobs, -0.6981317007977318F, 0.0F, 0.0F);
		this.rArm01.addChild(this.rArm02);
		this.body.addChild(this.lWing01);
		this.bottom.addChild(this.rLeg01);
		this.body.addChild(this.head);
		this.lHorn01.addChild(this.lHorn02);
		this.lWing02.addChild(this.lWing05);
		this.head.addChild(this.lHorn01);
		this.lWing01.addChild(this.lWing02);
		this.head.addChild(this.hair);
		this.lArm01.addChild(this.lArm02);
		this.rHorn02.addChild(this.rHorn03);
		this.body.addChild(this.chest);
		this.bottom.addChild(this.lLeg01);
		this.chest.addChild(this.lArm01);
		this.bottom.addChild(this.body);
		this.bottom.addChild(this.loincloth);
		this.lHorn02.addChild(this.lHorn03);
		this.body.addChild(this.rWing01);
		this.rWing02.addChild(this.rWing03);
		this.rHorn01.addChild(this.rHorn02);
		this.head.addChild(this.rHorn01);
		this.rWing02.addChild(this.rWing04);
		this.rWing02.addChild(this.rWing05);
		this.lWing02.addChild(this.lWing03);
		this.bottom.addChild(this.bottom2);
		this.chest.addChild(this.rArm01);
		this.lWing02.addChild(this.lWing04);
		this.rWing01.addChild(this.rWing02);
		this.chest.addChild(this.boobs);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bottom.render(f5);
		this.plith.render(f5);
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