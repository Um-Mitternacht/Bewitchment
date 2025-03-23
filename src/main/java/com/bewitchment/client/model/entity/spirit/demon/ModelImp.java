package com.bewitchment.client.model.entity.spirit.demon;

import com.bewitchment.common.entity.spirit.demon.EntityImp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * imp2 - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class ModelImp extends ModelBiped {
    public final ModelRenderer bipedBody;
    public final ModelRenderer tail00;
    public final ModelRenderer leftWing00;
    public final ModelRenderer rightWing00;
    public final ModelRenderer bipedLeftArm;
    public final ModelRenderer bipedRightArm;
    public final ModelRenderer bipedLeftLeg;
    public final ModelRenderer bipedRightLeg;
    public final ModelRenderer bipedHead;
    public final ModelRenderer tail01;
    public final ModelRenderer tail02;
    public final ModelRenderer tail03;
    public final ModelRenderer tail04;
    public final ModelRenderer MiddleTailfur00;
    public final ModelRenderer leftTailfur;
    public final ModelRenderer rightTailfur;
    public final ModelRenderer MiddleTailfur01;
    public final ModelRenderer leftWing01;
    public final ModelRenderer leftWing02;
    public final ModelRenderer leftWingMemebrane;
    public final ModelRenderer leftWing03;
    public final ModelRenderer rightWing01;
    public final ModelRenderer rightWing02;
    public final ModelRenderer rightWingMembrane;
    public final ModelRenderer rightWing03;
    public final ModelRenderer leftLeg01;
    public final ModelRenderer leftLeg02;
    public final ModelRenderer lLegfur;
    public final ModelRenderer leftHoof;
    public final ModelRenderer rightLeg01;
    public final ModelRenderer rightLeg02;
    public final ModelRenderer rLegfur;
    public final ModelRenderer rightHoof;
    public final ModelRenderer leftEar00;
    public final ModelRenderer leftHorn00a;
    public final ModelRenderer upperJaw00;
    public final ModelRenderer lowerJaw;
    public final ModelRenderer snout;
    public final ModelRenderer rightHorn00a;
    public final ModelRenderer rightEar00;
    public final ModelRenderer upperJaw01;
    public final ModelRenderer leftEar01;
    public final ModelRenderer lEar03;
    public final ModelRenderer leftHorn00b;
    public final ModelRenderer leftHorn00c;
    public final ModelRenderer leftHorn00d;
    public final ModelRenderer leftHorn01;
    public final ModelRenderer rightHorn00b;
    public final ModelRenderer rightHorn00c;
    public final ModelRenderer rightHorn00d;
    public final ModelRenderer rightHorn01;
    public final ModelRenderer rightEar01;
    public final ModelRenderer rEar03;

    public ModelImp() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leftHorn00c = new ModelRenderer(this, 35, 5);
        this.leftHorn00c.setRotationPoint(0, 0, 0);
        this.leftHorn00c.addBox(-0.8f, -2.5f, -0.2f, 1, 3, 1, 0);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.setRotationPoint(1.5f, 9, 0.2f);
        this.bipedLeftLeg.addBox(-1.5f, 0, -2, 3, 6, 4, 0);
        this.setRotateAngle(bipedLeftLeg, -0.26f, 0, -0.1f);
        this.rLegfur = new ModelRenderer(this, 51, 53);
        this.rLegfur.mirror = true;
        this.rLegfur.setRotationPoint(0, 3.8f, 0);
        this.rLegfur.addBox(0, -0.7f, 0, 0, 6, 3, 0);
        this.setRotateAngle(rLegfur, -0.1f, 0, 0);
        this.tail04 = new ModelRenderer(this, 13, 43);
        this.tail04.setRotationPoint(0, 0, 3.9f);
        this.tail04.addBox(-0.5f, -0.5f, 0, 1, 1, 4, 0);
        this.setRotateAngle(tail04, 0.28f, 0, 0);
        this.rightHoof = new ModelRenderer(this, 0, 44);
        this.rightHoof.mirror = true;
        this.rightHoof.setRotationPoint(0, 4.7f, 0);
        this.rightHoof.addBox(-1.5f, 0, -2, 3, 1, 3, 0);
        this.rightWingMembrane = new ModelRenderer(this, 42, 33);
        this.rightWingMembrane.mirror = true;
        this.rightWingMembrane.setRotationPoint(0, -0.3f, 4.6f);
        this.rightWingMembrane.addBox(0, 0, -7.1f, 0, 10, 10, 0);
        this.setRotateAngle(rightWingMembrane, -0.16f, 0, 0);
        this.rightHorn00b = new ModelRenderer(this, 35, 5);
        this.rightHorn00b.mirror = true;
        this.rightHorn00b.setRotationPoint(0, 0, 0);
        this.rightHorn00b.addBox(-0.2f, -2.5f, -0.8f, 1, 3, 1, 0);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0, 0, 0.4f);
        this.bipedHead.addBox(-3, -6, -3.5f, 6, 6, 6, 0);
        this.upperJaw01 = new ModelRenderer(this, 53, 6);
        this.upperJaw01.setRotationPoint(0, -2, -3.3f);
        this.upperJaw01.addBox(-1.7f, -1, -1.7f, 1, 2, 2, 0);
        this.leftLeg01 = new ModelRenderer(this, 0, 27);
        this.leftLeg01.setRotationPoint(-0.1f, 5, -0.7f);
        this.leftLeg01.addBox(-1.5f, 0, -1.4f, 3, 5, 3, 0);
        this.setRotateAngle(leftLeg01, 0.61f, 0, 0.1f);
        this.leftHorn00d = new ModelRenderer(this, 35, 5);
        this.leftHorn00d.setRotationPoint(0, 0, 0);
        this.leftHorn00d.addBox(-0.2f, -2.5f, -0.2f, 1, 3, 1, 0);
        this.rightHorn00d = new ModelRenderer(this, 35, 5);
        this.rightHorn00d.mirror = true;
        this.rightHorn00d.setRotationPoint(0, 0, 0);
        this.rightHorn00d.addBox(-0.2f, -2.5f, -0.2f, 1, 3, 1, 0);
        this.leftWing02 = new ModelRenderer(this, 25, 56);
        this.leftWing02.setRotationPoint(0.1f, -0.5f, 4.6f);
        this.leftWing02.addBox(-0.5f, 0, -0.5f, 1, 5, 1, 0);
        this.setRotateAngle(leftWing02, 0.63f, 0, 0);
        this.rightTailfur = new ModelRenderer(this, 11, 51);
        this.rightTailfur.setRotationPoint(0, 0, 0);
        this.rightTailfur.addBox(-0.2f, -0.3f, 0, 0, 3, 5, 0);
        this.setRotateAngle(rightTailfur, 0, -0.12f, 0);
        this.tail00 = new ModelRenderer(this, 13, 37);
        this.tail00.setRotationPoint(0, 8.5f, 1.1f);
        this.tail00.addBox(-1, -1, 0, 2, 2, 3, 0);
        this.setRotateAngle(tail00, -1.05f, 0, 0);
        this.MiddleTailfur00 = new ModelRenderer(this, 0, 48);
        this.MiddleTailfur00.setRotationPoint(0, 0.3f, 0.7f);
        this.MiddleTailfur00.addBox(0, -0.4f, 0, 0, 2, 4, 0);
        this.setRotateAngle(MiddleTailfur00, -0.14f, 0, 0);
        this.leftHorn01 = new ModelRenderer(this, 35, 10);
        this.leftHorn01.setRotationPoint(0, -1.7f, 0);
        this.leftHorn01.addBox(-0.5f, -3, -0.5f, 1, 3, 1, 0);
        this.setRotateAngle(leftHorn01, -0.14f, 0, -0.1f);
        this.lowerJaw = new ModelRenderer(this, 41, 11);
        this.lowerJaw.setRotationPoint(0, -0.7f, -3.3f);
        this.lowerJaw.addBox(-1.5f, -0.5f, -1.6f, 3, 1, 2, 0);
        this.rEar03 = new ModelRenderer(this, 49, 0);
        this.rEar03.mirror = true;
        this.rEar03.setRotationPoint(0.1f, -0.2f, 0.1f);
        this.rEar03.addBox(-0.5f, -2.9f, -0.5f, 1, 3, 1, 0);
        this.setRotateAngle(rEar03, 0, 0, -0.17f);
        this.snout = new ModelRenderer(this, 22, 0);
        this.snout.setRotationPoint(0, -3.1f, -3.5f);
        this.snout.addBox(-1.5f, -1, -1.9f, 3, 2, 3, 0);
        this.setRotateAngle(snout, 0.42f, 0, 0);
        this.rightWing03 = new ModelRenderer(this, 30, 55);
        this.rightWing03.mirror = true;
        this.rightWing03.setRotationPoint(0, 4.6f, 0.1f);
        this.rightWing03.addBox(-0.5f, -0.5f, -7.1f, 1, 1, 7, 0);
        this.setRotateAngle(rightWing03, 0.79f, 0, 0);
        this.rightWing01 = new ModelRenderer(this, 25, 48);
        this.rightWing01.mirror = true;
        this.rightWing01.setRotationPoint(-0.1f, 0, 2.5f);
        this.rightWing01.addBox(-0.5f, -1, 0, 1, 2, 5, 0);
        this.setRotateAngle(rightWing01, 0.52f, 0, 0);
        this.leftHorn00a = new ModelRenderer(this, 35, 5);
        this.leftHorn00a.setRotationPoint(2.1f, -5.7f, -1.8f);
        this.leftHorn00a.addBox(-0.8f, -2.5f, -0.8f, 1, 3, 1, 0);
        this.setRotateAngle(leftHorn00a, 0, 0, 0.23f);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.setRotationPoint(4, 2, -0.1f);
        this.bipedLeftArm.addBox(-1, -2, -1.5f, 3, 11, 3, 0);
        this.setRotateAngle(bipedLeftArm, 0, 0, -0.1f);
        this.tail02 = new ModelRenderer(this, 13, 43);
        this.tail02.setRotationPoint(0, 0, 3.9f);
        this.tail02.addBox(-0.5f, -0.5f, 0, 1, 1, 4, 0);
        this.setRotateAngle(tail02, 0.23f, 0, 0);
        this.rightWing00 = new ModelRenderer(this, 25, 42);
        this.rightWing00.mirror = true;
        this.rightWing00.setRotationPoint(-2.5f, 2.4f, 1.8f);
        this.rightWing00.addBox(-0.5f, -1, 0, 1, 2, 3, 0);
        this.setRotateAngle(rightWing00, 0.4f, -0.42f, 0);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.mirror = true;
        this.bipedRightArm.setRotationPoint(-4, 2, -0.1f);
        this.bipedRightArm.addBox(-2, -2, -1.5f, 3, 11, 3, 0);
        this.setRotateAngle(bipedRightArm, 0, 0, 0.1f);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.mirror = true;
        this.bipedRightLeg.setRotationPoint(-1.5f, 9, 0.2f);
        this.bipedRightLeg.addBox(-1.5f, 0, -2, 3, 6, 4, 0);
        this.setRotateAngle(bipedRightLeg, -0.26f, 0, 0.1f);
        this.rightHorn00a = new ModelRenderer(this, 35, 5);
        this.rightHorn00a.mirror = true;
        this.rightHorn00a.setRotationPoint(-2.1f, -5.7f, -1.8f);
        this.rightHorn00a.addBox(-0.8f, -2.5f, -0.8f, 1, 3, 1, 0);
        this.setRotateAngle(rightHorn00a, 0, 0, -0.23f);
        this.lEar03 = new ModelRenderer(this, 49, 0);
        this.lEar03.setRotationPoint(-0.1f, -0.2f, 0.1f);
        this.lEar03.addBox(-0.5f, -2.9f, -0.5f, 1, 3, 1, 0);
        this.setRotateAngle(lEar03, 0, 0, 0.17f);
        this.upperJaw00 = new ModelRenderer(this, 41, 6);
        this.upperJaw00.setRotationPoint(0, -2, -3.3f);
        this.upperJaw00.addBox(-1.3f, -1, -1.7f, 3, 2, 2, 0);
        this.lLegfur = new ModelRenderer(this, 51, 53);
        this.lLegfur.setRotationPoint(0, 3.8f, 0);
        this.lLegfur.addBox(0, -0.7f, 0, 0, 6, 3, 0);
        this.setRotateAngle(lLegfur, -0.1f, 0, 0);
        this.rightEar00 = new ModelRenderer(this, 42, 0);
        this.rightEar00.mirror = true;
        this.rightEar00.setRotationPoint(-2.1f, -4, 0.2f);
        this.rightEar00.addBox(-1, -3, -0.9f, 1, 3, 2, 0);
        this.setRotateAngle(rightEar00, -0.44f, -0.4f, -0.44f);
        this.rightHorn01 = new ModelRenderer(this, 35, 10);
        this.rightHorn01.mirror = true;
        this.rightHorn01.setRotationPoint(0, -1.7f, 0);
        this.rightHorn01.addBox(-0.5f, -3, -0.5f, 1, 3, 1, 0);
        this.setRotateAngle(rightHorn01, -0.14f, 0, 0.1f);
        this.leftHorn00b = new ModelRenderer(this, 35, 5);
        this.leftHorn00b.setRotationPoint(0, 0, 0);
        this.leftHorn00b.addBox(-0.2f, -2.5f, -0.8f, 1, 3, 1, 0);
        this.leftHoof = new ModelRenderer(this, 0, 44);
        this.leftHoof.setRotationPoint(0, 4.7f, 0);
        this.leftHoof.addBox(-1.5f, 0, -2, 3, 1, 3, 0);
        this.rightHorn00c = new ModelRenderer(this, 35, 5);
        this.rightHorn00c.mirror = true;
        this.rightHorn00c.setRotationPoint(0, 0, 0);
        this.rightHorn00c.addBox(-0.8f, -2.5f, -0.2f, 1, 3, 1, 0);
        this.MiddleTailfur01 = new ModelRenderer(this, 0, 53);
        this.MiddleTailfur01.setRotationPoint(0, 0.3f, 0);
        this.MiddleTailfur01.addBox(0, 0.1f, 0, 0, 3, 7, 0);
        this.leftEar01 = new ModelRenderer(this, 49, 0);
        this.leftEar01.setRotationPoint(0.4f, -2.6f, 0.1f);
        this.leftEar01.addBox(-0.5f, -1.9f, -0.5f, 1, 2, 1, 0);
        this.setRotateAngle(leftEar01, 0.14f, 0, 0);
        this.leftEar00 = new ModelRenderer(this, 42, 0);
        this.leftEar00.setRotationPoint(2.1f, -4, 0.2f);
        this.leftEar00.addBox(0, -3, -0.9f, 1, 3, 2, 0);
        this.setRotateAngle(leftEar00, -0.44f, 0.4f, 0.44f);
        this.leftWingMemebrane = new ModelRenderer(this, 42, 33);
        this.leftWingMemebrane.setRotationPoint(0, -0.3f, 4.6f);
        this.leftWingMemebrane.addBox(0, 0, -7.1f, 0, 10, 10, 0);
        this.setRotateAngle(leftWingMemebrane, -0.16f, 0, 0);
        this.tail01 = new ModelRenderer(this, 13, 43);
        this.tail01.setRotationPoint(0, 0, 1.9f);
        this.tail01.addBox(-0.5f, -0.5f, 0, 1, 1, 4, 0);
        this.leftTailfur = new ModelRenderer(this, 0, 51);
        this.leftTailfur.setRotationPoint(0, 0, 0);
        this.leftTailfur.addBox(0.2f, -0.3f, 0, 0, 3, 5, 0);
        this.setRotateAngle(leftTailfur, 0, 0.12f, 0);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0, 1.5f, 0);
        this.bipedBody.addBox(-3, 0, -2, 6, 10, 4, 0);
        this.rightEar01 = new ModelRenderer(this, 49, 0);
        this.rightEar01.mirror = true;
        this.rightEar01.setRotationPoint(-0.4f, -2.6f, 0.1f);
        this.rightEar01.addBox(-0.5f, -1.9f, -0.5f, 1, 2, 1, 0);
        this.setRotateAngle(rightEar01, 0.14f, 0, 0);
        this.rightWing02 = new ModelRenderer(this, 25, 56);
        this.rightWing02.mirror = true;
        this.rightWing02.setRotationPoint(-0.1f, -0.5f, 4.6f);
        this.rightWing02.addBox(-0.5f, 0, -0.5f, 1, 5, 1, 0);
        this.setRotateAngle(rightWing02, 0.63f, 0, 0);
        this.leftWing03 = new ModelRenderer(this, 30, 55);
        this.leftWing03.setRotationPoint(0, 4.6f, 0.1f);
        this.leftWing03.addBox(-0.5f, -0.5f, -7.1f, 1, 1, 7, 0);
        this.setRotateAngle(leftWing03, 0.79f, 0, 0);
        this.rightLeg02 = new ModelRenderer(this, 0, 36);
        this.rightLeg02.mirror = true;
        this.rightLeg02.setRotationPoint(0, 3.5f, 0.4f);
        this.rightLeg02.addBox(-1, 0, -1, 2, 5, 2, 0);
        this.setRotateAngle(rightLeg02, -0.35f, 0, 0);
        this.leftWing00 = new ModelRenderer(this, 25, 42);
        this.leftWing00.setRotationPoint(2.5f, 2.4f, 1.8f);
        this.leftWing00.addBox(-0.5f, -1, 0, 1, 2, 3, 0);
        this.setRotateAngle(leftWing00, 0.4f, 0.42f, 0);
        this.leftLeg02 = new ModelRenderer(this, 0, 36);
        this.leftLeg02.setRotationPoint(0, 3.5f, 0.4f);
        this.leftLeg02.addBox(-1, 0, -1, 2, 5, 2, 0);
        this.setRotateAngle(leftLeg02, -0.35f, 0, 0);
        this.tail03 = new ModelRenderer(this, 13, 43);
        this.tail03.setRotationPoint(0, 0, 3.9f);
        this.tail03.addBox(-0.5f, -0.5f, 0, 1, 1, 4, 0);
        this.setRotateAngle(tail03, 0.28f, 0, 0);
        this.leftWing01 = new ModelRenderer(this, 25, 48);
        this.leftWing01.setRotationPoint(0.1f, 0, 2.5f);
        this.leftWing01.addBox(-0.5f, -1, 0, 1, 2, 5, 0);
        this.setRotateAngle(leftWing01, 0.52f, 0, 0);
        this.rightLeg01 = new ModelRenderer(this, 0, 27);
        this.rightLeg01.mirror = true;
        this.rightLeg01.setRotationPoint(0.1f, 5, -0.7f);
        this.rightLeg01.addBox(-1.5f, 0, -1.4f, 3, 5, 3, 0);
        this.setRotateAngle(rightLeg01, 0.61f, 0, -0.1f);
        this.leftHorn00a.addChild(this.leftHorn00c);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.rightLeg01.addChild(this.rLegfur);
        this.tail03.addChild(this.tail04);
        this.rightLeg02.addChild(this.rightHoof);
        this.rightWing01.addChild(this.rightWingMembrane);
        this.rightHorn00a.addChild(this.rightHorn00b);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedHead.addChild(this.upperJaw01);
        this.bipedLeftLeg.addChild(this.leftLeg01);
        this.leftHorn00a.addChild(this.leftHorn00d);
        this.rightHorn00a.addChild(this.rightHorn00d);
        this.leftWing01.addChild(this.leftWing02);
        this.tail04.addChild(this.rightTailfur);
        this.bipedBody.addChild(this.tail00);
        this.tail03.addChild(this.MiddleTailfur00);
        this.leftHorn00a.addChild(this.leftHorn01);
        this.bipedHead.addChild(this.lowerJaw);
        this.rightEar00.addChild(this.rEar03);
        this.bipedHead.addChild(this.snout);
        this.rightWing02.addChild(this.rightWing03);
        this.rightWing00.addChild(this.rightWing01);
        this.bipedHead.addChild(this.leftHorn00a);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.tail01.addChild(this.tail02);
        this.bipedBody.addChild(this.rightWing00);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.bipedHead.addChild(this.rightHorn00a);
        this.leftEar00.addChild(this.lEar03);
        this.bipedHead.addChild(this.upperJaw00);
        this.leftLeg01.addChild(this.lLegfur);
        this.bipedHead.addChild(this.rightEar00);
        this.rightHorn00a.addChild(this.rightHorn01);
        this.leftHorn00a.addChild(this.leftHorn00b);
        this.leftLeg02.addChild(this.leftHoof);
        this.rightHorn00a.addChild(this.rightHorn00c);
        this.tail04.addChild(this.MiddleTailfur01);
        this.leftEar00.addChild(this.leftEar01);
        this.bipedHead.addChild(this.leftEar00);
        this.leftWing01.addChild(this.leftWingMemebrane);
        this.tail00.addChild(this.tail01);
        this.tail04.addChild(this.leftTailfur);
        this.rightEar00.addChild(this.rightEar01);
        this.rightWing01.addChild(this.rightWing02);
        this.leftWing02.addChild(this.leftWing03);
        this.rightLeg01.addChild(this.rightLeg02);
        this.bipedBody.addChild(this.leftWing00);
        this.leftLeg01.addChild(this.leftLeg02);
        this.tail02.addChild(this.tail03);
        this.leftWing00.addChild(this.leftWing01);
        this.bipedRightLeg.addChild(this.rightLeg01);
    }

    private static float triangleWave(float x, float y) {
        return (Math.abs(x % y - y * 0.5f) - y * 0.25f) / (y * 0.25f);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.bipedBody.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
        this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
        float add = entity.getUniqueID().hashCode() * 0.003F;
        float mul = 0.1F;
        float div = 40F;

        if (flag) {
            this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
        } else {
            this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
        }
        float swingMod = 0.3F;
        this.bipedLeftLeg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount - 0.26F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount - 0.26F;

        this.bipedRightArm.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount;

        tail00.rotateAngleY = MathHelper.sin(limbSwing * 0.25f) * 0.65F * limbSwingAmount + 0f;
        this.tail01.rotateAngleZ = (float) Math.cos(ageInTicks * (mul + 0.06F) + add) / div + 0F;
        this.leftWing00.rotateAngleZ = (float) Math.cos(ageInTicks * (mul + 0.06F) + add) / div + 0F;
        this.rightWing00.rotateAngleZ = -(float) Math.cos(ageInTicks * (mul + 0.06F) + add) / div + 0F;
        setLivingAnimations((EntityLivingBase) entity, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
    }

    public void setLivingAnimations(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTickTime) {
        EntityImp demon = (EntityImp) living;
        int i = demon.attackTimer;
        if (i > 0) {
            bipedRightArm.rotateAngleX = -2 + 1.5f * triangleWave((float) i - partialTickTime, 10);
            bipedLeftArm.rotateAngleX = -2 + 1.5f * triangleWave((float) i - partialTickTime, 10);
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }
}