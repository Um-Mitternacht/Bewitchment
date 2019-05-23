package com.bewitchment.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * witches_attire_4 - Ingoleth, cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class ModelWitchesArmor extends ModelBiped {
	private final EntityEquipmentSlot slot;
	
	public ModelRenderer legLeft;
	public ModelRenderer legRight;
	public ModelRenderer hat1;
	public ModelRenderer Body;
	public ModelRenderer hood01;
	public ModelRenderer tunicLeftBack;
	public ModelRenderer tunicLeftFront;
	public ModelRenderer tunicLeft;
	public ModelRenderer tunicLeftBack_1;
	public ModelRenderer tunicFront;
	public ModelRenderer tunicLeft_1;
	public ModelRenderer tunicRightFront;
	public ModelRenderer tunicRightBack;
	public ModelRenderer tunicRight;
	public ModelRenderer tunicRightFront_1;
	public ModelRenderer tunicRightBack_1;
	public ModelRenderer tunicRight_1;
	public ModelRenderer hat2;
	public ModelRenderer hatWing;
	public ModelRenderer hat3;
	public ModelRenderer armLeft;
	public ModelRenderer armRight;
	public ModelRenderer shoulderLeft;
	public ModelRenderer sleeveLeft;
	public ModelRenderer shoulderRight;
	public ModelRenderer sleeveRight;
	public ModelRenderer hoodFringe01;
	public ModelRenderer hoodFringeL01;
	public ModelRenderer hoodFringeR01;
	public ModelRenderer hoodFringeL02;
	public ModelRenderer hoodFringeR02;
	public ModelRenderer hoodFringeL03;
	public ModelRenderer hoodFringeR03;
	public ModelRenderer hoodShade;
	public ModelRenderer hoodFringeL04;
	public ModelRenderer hoodFringeR04;
	public ModelRenderer hoodPipe01;
	public ModelRenderer hoodPipe02;
	
	public ModelWitchesArmor(EntityEquipmentSlot slot) {
		this.slot = slot;
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.sleeveRight = new ModelRenderer(this, 49, 98);
		this.sleeveRight.setRotationPoint(0.99F, 10.1F, 2);
		this.sleeveRight.addBox(-2, -4, 0, 4, 4, 2, 0);
		this.setRotateAngle(sleeveRight, 0.5235987755982988F, 0, 0);
		this.hoodShade = new ModelRenderer(this, 105, 0);
		this.hoodShade.setRotationPoint(0, -7.3F, -5.2F);
		this.hoodShade.addBox(-4, 0, 0, 8, 8, 0, 0);
		this.setRotateAngle(hoodShade, 0.05235987755982988F, 0, 0);
		this.hat2 = new ModelRenderer(this, 87, 114);
		this.hat2.setRotationPoint(0, -10.5F, -3);
		this.hat2.addBox(-3, -4, 0, 6, 4, 6, 0);
		this.setRotateAngle(hat2, -0.2617993877991494F, 0, 0);
		this.shoulderRight = new ModelRenderer(this, 0, 68);
		this.shoulderRight.setRotationPoint(0, 0, 0);
		this.shoulderRight.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5, 0);
		this.setRotateAngle(shoulderRight, 0, 0, 0.08726646259971647F);
		this.tunicRight_1 = new ModelRenderer(this, 23, 77);
		this.tunicRight_1.setRotationPoint(-0.01F, 10, 1);
		this.tunicRight_1.addBox(0, 0, -1, 4, 2, 1, 0);
		this.setRotateAngle(tunicRight_1, -0.2617993877991494F, 0, 0);
		this.tunicLeftBack_1 = new ModelRenderer(this, 53, 77);
		this.tunicLeftBack_1.setRotationPoint(-0.01F, 10, 1);
		this.tunicLeftBack_1.addBox(0, 0, -1, 4, 2, 1, 0);
		this.setRotateAngle(tunicLeftBack_1, -0.2617993877991494F, 0, 0);
		this.armLeft = new ModelRenderer(this, 47, 81);
		this.armLeft.setRotationPoint(-5, 2, 0);
		this.armLeft.addBox(-3, -1.9F, -2, 4, 12, 4, 0);
		this.hatWing = new ModelRenderer(this, 0, 112);
		this.hatWing.setRotationPoint(0, -6, 0);
		this.hatWing.addBox(-6, 0, -6, 12, 1, 12, 0);
		this.setRotateAngle(hatWing, 0.03490658503988659F, 0, 0.06981317007977318F);
		this.hoodFringeL02 = new ModelRenderer(this, 97, 19);
		this.hoodFringeL02.setRotationPoint(4.85F, -1.2F, 0.1F);
		this.hoodFringeL02.addBox(0, -0.5F, -5.38F, 1, 2, 10, 0);
		this.setRotateAngle(hoodFringeL02, 0, 0, 0.20943951023931953F);
		this.hoodPipe02 = new ModelRenderer(this, 65, 54);
		this.hoodPipe02.setRotationPoint(0, -0.3F, 3.1F);
		this.hoodPipe02.addBox(-2, -2, 0, 4, 4, 4, 0);
		this.setRotateAngle(hoodPipe02, -0.45378560551852565F, 0, 0);
		this.Body = new ModelRenderer(this, 3, 81);
		this.Body.setRotationPoint(0, 0, 0);
		this.Body.addBox(-4, -0.01F, -2, 8, 12, 4, 0);
		this.hoodFringeR04 = new ModelRenderer(this, 57, 20);
		this.hoodFringeR04.mirror = true;
		this.hoodFringeR04.setRotationPoint(-2.7F, -8.4F, 0.1F);
		this.hoodFringeR04.addBox(-0.9F, -1, -5.38F, 4, 2, 11, 0);
		this.setRotateAngle(hoodFringeR04, 0, 0, -0.3141592653589793F);
		this.tunicLeftFront = new ModelRenderer(this, 53, 65);
		this.tunicLeftFront.setRotationPoint(2.01F, 0, -2.1F);
		this.tunicLeftFront.addBox(0, 0, -1, 4, 10, 1, 0);
		this.setRotateAngle(tunicLeftFront, -3.01941960595019F, 0, 3.141592653589793F);
		this.hat3 = new ModelRenderer(this, 110, 112);
		this.hat3.setRotationPoint(1.4F, -4, 1.5F);
		this.hat3.addBox(-3, -4, 0, 3, 4, 3, 0);
		this.setRotateAngle(hat3, -0.3490658503988659F, 0, 0);
		this.sleeveLeft = new ModelRenderer(this, 49, 98);
		this.sleeveLeft.setRotationPoint(1.01F, 10.1F, 2);
		this.sleeveLeft.addBox(-4, -4, 0, 4, 4, 2, 0);
		this.setRotateAngle(sleeveLeft, 0.5235987755982988F, 0, 0);
		this.hood01 = new ModelRenderer(this, 70, 0);
		this.hood01.setRotationPoint(0, 0, 0);
		this.hood01.addBox(-4.5F, -9, -4.7F, 9, 9, 10, 0);
		this.tunicRightFront_1 = new ModelRenderer(this, 53, 77);
		this.tunicRightFront_1.setRotationPoint(-0.01F, 10, 1);
		this.tunicRightFront_1.addBox(0, 0, -1, 4, 2, 1, 0);
		this.setRotateAngle(tunicRightFront_1, -0.2617993877991494F, 0, 0);
		this.shoulderLeft = new ModelRenderer(this, 0, 68);
		this.shoulderLeft.setRotationPoint(0, 0, 0);
		this.shoulderLeft.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5, 0);
		this.setRotateAngle(shoulderLeft, 0, 3.141592653589793F, -0.08726646259971647F);
		this.armRight = new ModelRenderer(this, 47, 81);
		this.armRight.mirror = true;
		this.armRight.setRotationPoint(5, 2, 0);
		this.armRight.addBox(-1, -1.9F, -2, 4, 12, 4, 0);
		this.tunicFront = new ModelRenderer(this, 53, 77);
		this.tunicFront.setRotationPoint(-0.01F, 10, -1);
		this.tunicFront.addBox(0, 0, 0, 4, 2, 1, 0);
		this.setRotateAngle(tunicFront, 0.2617993877991494F, 0, 0);
		this.hoodFringeL01 = new ModelRenderer(this, 77, 24);
		this.hoodFringeL01.setRotationPoint(1.9F, -8.2F, 0.1F);
		this.hoodFringeL01.addBox(0, -0.3F, -5.39F, 2, 8, 10, 0);
		this.setRotateAngle(hoodFringeL01, 0, 0, -0.2792526803190927F);
		this.tunicRightFront = new ModelRenderer(this, 53, 65);
		this.tunicRightFront.setRotationPoint(-1.99F, 0, -2.1F);
		this.tunicRightFront.addBox(0, 0, 0, 4, 10, 1, 0);
		this.setRotateAngle(tunicRightFront, -0.12217304763960307F, 0, 0);
		this.hoodFringe01 = new ModelRenderer(this, 66, 0);
		this.hoodFringe01.setRotationPoint(0, 0, 0.1F);
		this.hoodFringe01.addBox(-3, -8.9F, -5.4F, 6, 2, 1, 0);
		this.tunicLeft = new ModelRenderer(this, 23, 65);
		this.tunicLeft.setRotationPoint(-2.01F, 0, -2.1F);
		this.tunicLeft.addBox(0, 0, -1, 4, 10, 1, 0);
		this.setRotateAngle(tunicLeft, 0.12217304763960307F, -1.5707963267948966F, 0);
		this.tunicRightBack = new ModelRenderer(this, 53, 65);
		this.tunicRightBack.setRotationPoint(-1.99F, 0, 2.1F);
		this.tunicRightBack.addBox(0, 0, -1, 4, 10, 1, 0);
		this.setRotateAngle(tunicRightBack, 0.12217304763960307F, 0, 0);
		this.hoodPipe01 = new ModelRenderer(this, 65, 43);
		this.hoodPipe01.setRotationPoint(0, -7, 4.2F);
		this.hoodPipe01.addBox(-3, -2.5F, 0, 6, 6, 4, 0);
		this.setRotateAngle(hoodPipe01, -0.40142572795869574F, 0, 0);
		this.hoodFringeR02 = new ModelRenderer(this, 97, 19);
		this.hoodFringeR02.mirror = true;
		this.hoodFringeR02.setRotationPoint(-4.85F, -1.2F, 0.1F);
		this.hoodFringeR02.addBox(-1, -0.5F, -5.38F, 1, 2, 10, 0);
		this.setRotateAngle(hoodFringeR02, 0, 0, -0.20943951023931953F);
		this.hoodFringeR01 = new ModelRenderer(this, 77, 24);
		this.hoodFringeR01.mirror = true;
		this.hoodFringeR01.setRotationPoint(-1.9F, -8.2F, 0.1F);
		this.hoodFringeR01.addBox(-2, -0.3F, -5.39F, 2, 8, 10, 0);
		this.setRotateAngle(hoodFringeR01, 0, 0, 0.2792526803190927F);
		this.tunicRightBack_1 = new ModelRenderer(this, 53, 77);
		this.tunicRightBack_1.setRotationPoint(-0.01F, 10, -1);
		this.tunicRightBack_1.addBox(0, 0, 0, 4, 2, 1, 0);
		this.setRotateAngle(tunicRightBack_1, 0.2617993877991494F, 0, 0);
		this.legRight = new ModelRenderer(this, 0, 16);
		this.legRight.mirror = true;
		this.legRight.setRotationPoint(2, 12, 0);
		this.legRight.addBox(-2, 0, -2, 0, 0, 0, 0);
		this.hoodFringeL03 = new ModelRenderer(this, 97, 33);
		this.hoodFringeL03.setRotationPoint(5.15F, -0.7F, 0.1F);
		this.hoodFringeL03.addBox(-5, -0.9F, -5.37F, 5, 2, 10, 0);
		this.setRotateAngle(hoodFringeL03, 0, 0, -0.3141592653589793F);
		this.hoodFringeR03 = new ModelRenderer(this, 97, 33);
		this.hoodFringeR03.mirror = true;
		this.hoodFringeR03.setRotationPoint(-5.15F, -0.7F, 0.1F);
		this.hoodFringeR03.addBox(0, -0.9F, -5.37F, 5, 2, 10, 0);
		this.setRotateAngle(hoodFringeR03, 0, 0, 0.3141592653589793F);
		this.tunicLeftBack = new ModelRenderer(this, 53, 65);
		this.tunicLeftBack.setRotationPoint(2.01F, 0, 2.1F);
		this.tunicLeftBack.addBox(0, 0, 0, 4, 10, 1, 0);
		this.setRotateAngle(tunicLeftBack, 3.01941960595019F, 0, 3.141592653589793F);
		this.tunicLeft_1 = new ModelRenderer(this, 23, 77);
		this.tunicLeft_1.setRotationPoint(-0.01F, 10, -1);
		this.tunicLeft_1.addBox(0, 0, 0, 4, 2, 1, 0);
		this.setRotateAngle(tunicLeft_1, 0.2617993877991494F, 0, 0);
		this.hoodFringeL04 = new ModelRenderer(this, 57, 20);
		this.hoodFringeL04.setRotationPoint(2.7F, -8.4F, 0.1F);
		this.hoodFringeL04.addBox(-3.2F, -1, -5.38F, 4, 2, 11, 0);
		this.setRotateAngle(hoodFringeL04, 0, 0, 0.3141592653589793F);
		this.hat1 = new ModelRenderer(this, 49, 111);
		this.hat1.setRotationPoint(0, 0, 0);
		this.hat1.addBox(-4.5F, -10.5F, -4.5F, 9, 5, 9, 0);
		this.legLeft = new ModelRenderer(this, 3, 32);
		this.legLeft.setRotationPoint(-2, 12, 0);
		this.legLeft.addBox(-2, 0, -2, 0, 0, 0, 0);
		this.tunicRight = new ModelRenderer(this, 23, 65);
		this.tunicRight.setRotationPoint(2.01F, 0, -2.1F);
		this.tunicRight.addBox(0, 0, 0, 4, 10, 1, 0);
		this.setRotateAngle(tunicRight, -0.12217304763960307F, -1.5707963267948966F, 0);
		this.armRight.addChild(this.sleeveRight);
		this.hood01.addChild(this.hoodShade);
		this.hat1.addChild(this.hat2);
		this.armRight.addChild(this.shoulderRight);
		this.tunicRight.addChild(this.tunicRight_1);
		this.tunicLeftBack.addChild(this.tunicLeftBack_1);
		this.Body.addChild(this.armLeft);
		this.hat1.addChild(this.hatWing);
		this.hood01.addChild(this.hoodFringeL02);
		this.hoodPipe01.addChild(this.hoodPipe02);
		this.hood01.addChild(this.hoodFringeR04);
		this.legLeft.addChild(this.tunicLeftFront);
		this.hat2.addChild(this.hat3);
		this.armLeft.addChild(this.sleeveLeft);
		this.tunicRightFront.addChild(this.tunicRightFront_1);
		this.armLeft.addChild(this.shoulderLeft);
		this.Body.addChild(this.armRight);
		this.tunicLeftFront.addChild(this.tunicFront);
		this.hood01.addChild(this.hoodFringeL01);
		this.legRight.addChild(this.tunicRightFront);
		this.hood01.addChild(this.hoodFringe01);
		this.legLeft.addChild(this.tunicLeft);
		this.legRight.addChild(this.tunicRightBack);
		this.hood01.addChild(this.hoodPipe01);
		this.hood01.addChild(this.hoodFringeR02);
		this.hood01.addChild(this.hoodFringeR01);
		this.tunicRightBack.addChild(this.tunicRightBack_1);
		this.hood01.addChild(this.hoodFringeL03);
		this.hood01.addChild(this.hoodFringeR03);
		this.legLeft.addChild(this.tunicLeftBack);
		this.tunicLeft.addChild(this.tunicLeft_1);
		this.hood01.addChild(this.hoodFringeL04);
		this.legRight.addChild(this.tunicRight);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.bipedHead.showModel = slot == EntityEquipmentSlot.HEAD;
		this.bipedHeadwear.showModel = slot == EntityEquipmentSlot.HEAD;
		
		this.bipedBody.showModel = slot == EntityEquipmentSlot.CHEST;
		this.bipedRightArm.showModel = slot == EntityEquipmentSlot.CHEST;
		this.bipedLeftArm.showModel = slot == EntityEquipmentSlot.CHEST;
		
		this.bipedRightLeg.showModel = slot == EntityEquipmentSlot.LEGS;
		this.bipedLeftLeg.showModel = slot == EntityEquipmentSlot.LEGS;
		super.render(entity, f, f1, f2, f3, f4, f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		if (entity instanceof EntityArmorStand) {
			EntityArmorStand stand = (EntityArmorStand) entity;
			this.bipedHead.rotateAngleX = 0.017453292F * stand.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = 0.017453292F * stand.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = 0.017453292F * stand.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0, 1, 0);
			
			this.bipedBody.rotateAngleX = 0.017453292F * stand.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = 0.017453292F * stand.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = 0.017453292F * stand.getBodyRotation().getZ();
			
			this.bipedLeftArm.rotateAngleX = 0.017453292F * stand.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = 0.017453292F * stand.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = 0.017453292F * stand.getLeftArmRotation().getZ();
			
			this.bipedRightArm.rotateAngleX = 0.017453292F * stand.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = 0.017453292F * stand.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = 0.017453292F * stand.getRightArmRotation().getZ();
			
			this.bipedLeftLeg.rotateAngleX = 0.017453292F * stand.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = 0.017453292F * stand.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = 0.017453292F * stand.getLeftLegRotation().getZ();
			this.bipedLeftLeg.setRotationPoint(1.9F, 11, 0);
			
			this.bipedRightLeg.rotateAngleX = 0.017453292F * stand.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = 0.017453292F * stand.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = 0.017453292F * stand.getRightLegRotation().getZ();
			this.bipedRightLeg.setRotationPoint(-1.9F, 11, 0);
			
			copyModelAngles(this.bipedHead, this.bipedHeadwear);
		}
		else super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
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