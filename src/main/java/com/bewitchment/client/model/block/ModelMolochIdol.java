package com.bewitchment.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * idol_moloch - sunconure11
 * Created using Tabula 7.1.0
 */
public class ModelMolochIdol extends ModelBase {
	public ModelRenderer plinth;
	public ModelRenderer pedestal;
	public ModelRenderer lLeg0;
	public ModelRenderer rLeg0;
	public ModelRenderer ass;
	public ModelRenderer lArm0;
	public ModelRenderer lArm1;
	public ModelRenderer belly;
	public ModelRenderer head;
	public ModelRenderer snout1;
	public ModelRenderer snout2;
	public ModelRenderer torso;
	public ModelRenderer rArm0;
	public ModelRenderer rArm1;
	public ModelRenderer rHorn0;
	public ModelRenderer lHorn0;
	public ModelRenderer lHorn1;
	public ModelRenderer rHorn1;
	
	public ModelMolochIdol() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.snout1 = new ModelRenderer(this, 34, 43);
		this.snout1.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.snout1.addBox(-0.6F, -12.45F, 0.3F, 2, 1, 3, 0.0F);
		this.setRotateAngle(snout1, 0.3490658503988659F, 0.0F, 0.0F);
		this.rArm0 = new ModelRenderer(this, 40, 11);
		this.rArm0.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.rArm0.addBox(2.3F, -9.6F, -2.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rArm0, -0.20245819323134223F, 0.0F, 0.0F);
		this.rHorn1 = new ModelRenderer(this, 13, 22);
		this.rHorn1.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.rHorn1.addBox(3.6F, -10.5F, 7.6F, 2, 1, 1, 0.0F);
		this.setRotateAngle(rHorn1, 0.0F, 1.0471975511965976F, -0.5235987755982988F);
		this.plinth = new ModelRenderer(this, 0, 43);
		this.plinth.setRotationPoint(0.0F, 24.1F, 0.0F);
		this.plinth.addBox(-3.8F, -4.0F, -4.1F, 8, 4, 8, 0.0F);
		this.pedestal = new ModelRenderer(this, 4, 31);
		this.pedestal.setRotationPoint(0.0F, 21.1F, 0.0F);
		this.pedestal.addBox(-2.7F, -6.0F, -3.2F, 6, 5, 6, 0.0F);
		this.lLeg0 = new ModelRenderer(this, 0, 20);
		this.lLeg0.mirror = true;
		this.lLeg0.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.lLeg0.addBox(-1.6F, -4.7F, -4.3F, 1, 4, 1, 0.0F);
		this.rLeg0 = new ModelRenderer(this, 0, 20);
		this.rLeg0.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.rLeg0.addBox(1.4F, -4.8F, -4.3F, 1, 4, 1, 0.0F);
		this.head = new ModelRenderer(this, 34, 34);
		this.head.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.head.addBox(-1.1F, -12.8F, -1.3F, 3, 3, 3, 0.0F);
		this.lHorn1 = new ModelRenderer(this, 13, 22);
		this.lHorn1.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.lHorn1.addBox(-5.3F, -10.7F, 7.1F, 2, 1, 1, 0.0F);
		this.setRotateAngle(lHorn1, 0.0F, -1.0471975511965976F, 0.5235987755982988F);
		this.lArm1 = new ModelRenderer(this, 40, 20);
		this.lArm1.mirror = true;
		this.lArm1.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.lArm1.addBox(-2.7F, -5.1F, -5.3F, 1, 4, 1, 0.0F);
		this.setRotateAngle(lArm1, -0.7438593271999832F, 0.0F, 0.0F);
		this.rHorn0 = new ModelRenderer(this, 13, 22);
		this.rHorn0.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.rHorn0.addBox(6.6F, -10.5F, 2.5F, 2, 1, 1, 0.0F);
		this.setRotateAngle(rHorn0, 0.0F, 0.2617993877991494F, -0.5235987755982988F);
		this.lHorn0 = new ModelRenderer(this, 13, 22);
		this.lHorn0.mirror = true;
		this.lHorn0.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.lHorn0.addBox(-8.1F, -10.7F, 2.35F, 2, 1, 1, 0.0F);
		this.setRotateAngle(lHorn0, 0.0F, -0.2617993877991494F, 0.5235987755982988F);
		this.snout2 = new ModelRenderer(this, 34, 50);
		this.snout2.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.snout2.addBox(-0.6F, -11.95F, -3.55F, 2, 2, 3, 0.0F);
		this.lArm0 = new ModelRenderer(this, 40, 11);
		this.lArm0.mirror = true;
		this.lArm0.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.lArm0.addBox(-2.7F, -9.6F, -2.0F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lArm0, -0.20245819323134223F, 0.0F, 0.0F);
		this.rArm1 = new ModelRenderer(this, 40, 20);
		this.rArm1.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.rArm1.addBox(2.3F, -5.1F, -5.3F, 1, 4, 1, 0.0F);
		this.setRotateAngle(rArm1, -0.7438593271999832F, 0.0F, 0.0F);
		this.torso = new ModelRenderer(this, 0, 0);
		this.torso.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.torso.addBox(-2.2F, -9.9F, -1.3F, 5, 5, 3, 0.0F);
		this.belly = new ModelRenderer(this, 27, 9);
		this.belly.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.belly.addBox(-1.6F, -7.5F, -2.3F, 4, 2, 1, 0.0F);
		this.ass = new ModelRenderer(this, 0, 11);
		this.ass.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.ass.addBox(-1.6F, -5.7F, -4.3F, 4, 1, 6, 0.0F);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.snout1.render(f5);
		this.rArm0.render(f5);
		this.rHorn1.render(f5);
		this.plinth.render(f5);
		this.pedestal.render(f5);
		this.lLeg0.render(f5);
		this.rLeg0.render(f5);
		this.head.render(f5);
		this.lHorn1.render(f5);
		this.lArm1.render(f5);
		this.rHorn0.render(f5);
		this.lHorn0.render(f5);
		this.snout2.render(f5);
		this.lArm0.render(f5);
		this.rArm1.render(f5);
		this.torso.render(f5);
		this.belly.render(f5);
		this.ass.render(f5);
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
