package com.bewitchment.client.model.entity.spirit.demon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * hellhound - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelHellhound extends ModelBase {
    public final ModelRenderer leftforeleg;
    public final ModelRenderer body;
    public final ModelRenderer leftHindleg;
    public final ModelRenderer rightHindleg;
    public final ModelRenderer rightforeleg;
    public final ModelRenderer mane00;
    public final ModelRenderer neck;
    public final ModelRenderer tail00;
    public final ModelRenderer mane02;
    public final ModelRenderer mane01;
    public final ModelRenderer head;
    public final ModelRenderer leftEar;
    public final ModelRenderer rightEar;
    public final ModelRenderer muzzle;
    public final ModelRenderer lowerJaw;
    public final ModelRenderer leftHorn00;
    public final ModelRenderer rightHorn00;
    public final ModelRenderer leftHorn01;
    public final ModelRenderer leftHorn02a;
    public final ModelRenderer leftHorn02c;
    public final ModelRenderer leftHorn03a;
    public final ModelRenderer leftHorn03c;
    public final ModelRenderer leftHorn04a;
    public final ModelRenderer leftHorn04b;
    public final ModelRenderer leftHorn05;
    public final ModelRenderer rightHorn01;
    public final ModelRenderer rightHorn02a;
    public final ModelRenderer rightHorn02c;
    public final ModelRenderer rightHorn03a;
    public final ModelRenderer rightHorn03c;
    public final ModelRenderer rightHorn04a;
    public final ModelRenderer rightHorn04b;
    public final ModelRenderer rightHorn05;
    public final ModelRenderer tail01;
    public final ModelRenderer tail02;
    public final ModelRenderer tail03;
    public final ModelRenderer tail04;

    public ModelHellhound() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.tail01 = new ModelRenderer(this, 26, 33);
        this.tail01.setRotationPoint(0, 3.3f, 0);
        this.tail01.addBox(-0.5f, -0.1f, -0.5f, 1, 5, 1, 0);
        this.setRotateAngle(tail01, 0.27f, 0, 0);
        this.leftHindleg = new ModelRenderer(this, 0, 18);
        this.leftHindleg.setRotationPoint(1.5f, 16, 6);
        this.leftHindleg.addBox(-1, 0, -1, 2, 8, 2, 0);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0, 0, -2.9f);
        this.head.addBox(-3, -3, -4, 6, 6, 4, 0);
        this.mane01 = new ModelRenderer(this, 0, 48);
        this.mane01.setRotationPoint(0, -1.8f, -3);
        this.mane01.addBox(-3, -1, 0, 6, 2, 7, 0);
        this.setRotateAngle(mane01, 0.44f, 0, 0);
        this.lowerJaw = new ModelRenderer(this, 0, 43);
        this.lowerJaw.setRotationPoint(0, 2, -3.8f);
        this.lowerJaw.addBox(-1.5f, -0.4f, -3, 3, 1, 3, 0);
        this.leftHorn03c = new ModelRenderer(this, 57, 6);
        this.leftHorn03c.setRotationPoint(0, 0, 0);
        this.leftHorn03c.addBox(-0.3f, -3, -0.3f, 1, 3, 1, 0);
        this.leftEar = new ModelRenderer(this, 16, 14);
        this.leftEar.setRotationPoint(2, -3, -2);
        this.leftEar.addBox(-1, -2, -0.5f, 2, 2, 1, 0);
        this.leftHorn03a = new ModelRenderer(this, 57, 6);
        this.leftHorn03a.setRotationPoint(0, -1.5f, -0.1f);
        this.leftHorn03a.addBox(-0.3f, -3, -0.7f, 1, 3, 1, 0);
        this.setRotateAngle(leftHorn03a, -0.7f, 0, 0);
        this.leftHorn02a = new ModelRenderer(this, 52, 7);
        this.leftHorn02a.setRotationPoint(0, -1.5f, -0.1f);
        this.leftHorn02a.addBox(-0.2f, -2, -0.8f, 1, 2, 1, 0);
        this.setRotateAngle(leftHorn02a, -0.7f, 0.21f, 0);
        this.rightHorn03a = new ModelRenderer(this, 57, 6);
        this.rightHorn03a.mirror = true;
        this.rightHorn03a.setRotationPoint(0, -1.5f, -0.1f);
        this.rightHorn03a.addBox(-0.7f, -3, -0.7f, 1, 3, 1, 0);
        this.setRotateAngle(rightHorn03a, -0.7f, 0, 0);
        this.leftHorn04a = new ModelRenderer(this, 52, 12);
        this.leftHorn04a.setRotationPoint(0, -2.5f, -0.1f);
        this.leftHorn04a.addBox(-0.5f, -3, -0.7f, 1, 3, 1, 0);
        this.setRotateAngle(leftHorn04a, -0.7f, 0.35f, 0);
        this.rightforeleg = new ModelRenderer(this, 0, 18);
        this.rightforeleg.mirror = true;
        this.rightforeleg.setRotationPoint(-1.5f, 16, -4);
        this.rightforeleg.addBox(-1, 0, -1, 2, 8, 2, 0);
        this.tail02 = new ModelRenderer(this, 32, 33);
        this.tail02.setRotationPoint(0, 4.4f, 0);
        this.tail02.addBox(-0.5f, -0.1f, -0.5f, 1, 5, 1, 0);
        this.setRotateAngle(tail02, 0.21f, 0, 0);
        this.leftHorn01 = new ModelRenderer(this, 55, 0);
        this.leftHorn01.setRotationPoint(0, -2.5f, -0.1f);
        this.leftHorn01.addBox(-1, -2, -1, 2, 2, 2, 0);
        this.setRotateAngle(leftHorn01, -0.52f, 0.17f, 0);
        this.tail03 = new ModelRenderer(this, 37, 33);
        this.tail03.setRotationPoint(0, 4.3f, 0);
        this.tail03.addBox(-1, -0.1f, -0.5f, 2, 2, 1, 0);
        this.setRotateAngle(tail03, 0.21f, 0, 0);
        this.rightEar = new ModelRenderer(this, 16, 14);
        this.rightEar.mirror = true;
        this.rightEar.setRotationPoint(-2, -3, -2);
        this.rightEar.addBox(-1, -2, -0.5f, 2, 2, 1, 0);
        this.rightHorn00 = new ModelRenderer(this, 46, 0);
        this.rightHorn00.mirror = true;
        this.rightHorn00.setRotationPoint(-1.3f, -2.1f, -1.4f);
        this.rightHorn00.addBox(-1, -3, -1, 2, 3, 2, 0);
        this.setRotateAngle(rightHorn00, -1, -0.45f, 0);
        this.leftHorn04b = new ModelRenderer(this, 52, 12);
        this.leftHorn04b.setRotationPoint(0, 0, 0);
        this.leftHorn04b.addBox(-0.5f, -3, -0.3f, 1, 3, 1, 0);
        this.rightHindleg = new ModelRenderer(this, 0, 18);
        this.rightHindleg.mirror = true;
        this.rightHindleg.setRotationPoint(-1.5f, 16, 6);
        this.rightHindleg.addBox(-1, 0, -1, 2, 8, 2, 0);
        this.rightHorn03c = new ModelRenderer(this, 57, 6);
        this.rightHorn03c.mirror = true;
        this.rightHorn03c.setRotationPoint(0, 0, 0);
        this.rightHorn03c.addBox(-0.7f, -3, -0.3f, 1, 3, 1, 0);
        this.rightHorn04a = new ModelRenderer(this, 52, 12);
        this.rightHorn04a.mirror = true;
        this.rightHorn04a.setRotationPoint(0, -2.5f, -0.1f);
        this.rightHorn04a.addBox(-0.5f, -3, -0.7f, 1, 3, 1, 0);
        this.setRotateAngle(rightHorn04a, -0.7f, -0.35f, 0);
        this.neck = new ModelRenderer(this, 0, 32);
        this.neck.setRotationPoint(0, -5.4f, 0.5f);
        this.neck.addBox(-2.5f, -2.5f, -4, 5, 5, 4, 0);
        this.setRotateAngle(neck, -1.57f, 0, 0);
        this.tail00 = new ModelRenderer(this, 20, 33);
        this.tail00.setRotationPoint(0, 6.8f, 2.2f);
        this.tail00.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1, 0);
        this.setRotateAngle(tail00, -1, 0, 0);
        this.rightHorn05 = new ModelRenderer(this, 58, 13);
        this.rightHorn05.mirror = true;
        this.rightHorn05.setRotationPoint(0, -2.7f, 0);
        this.rightHorn05.addBox(-0.5f, -3, -0.5f, 1, 3, 1, 0);
        this.setRotateAngle(rightHorn05, -0.63f, 0, 0);
        this.rightHorn02c = new ModelRenderer(this, 52, 7);
        this.rightHorn02c.mirror = true;
        this.rightHorn02c.setRotationPoint(0, 0, 0);
        this.rightHorn02c.addBox(-0.8f, -2, -0.2f, 1, 2, 1, 0);
        this.mane02 = new ModelRenderer(this, 28, 48);
        this.mane02.setRotationPoint(0, -1, 2.7f);
        this.mane02.addBox(-3.5f, -1, 0, 7, 2, 7, 0);
        this.setRotateAngle(mane02, -1.3f, 0, 0);
        this.leftHorn00 = new ModelRenderer(this, 46, 0);
        this.leftHorn00.setRotationPoint(1.3f, -2.1f, -1.4f);
        this.leftHorn00.addBox(-1, -3, -1, 2, 3, 2, 0);
        this.setRotateAngle(leftHorn00, -1, 0.45f, 0);
        this.muzzle = new ModelRenderer(this, 0, 10);
        this.muzzle.setRotationPoint(0, 0.7f, -3.9f);
        this.muzzle.addBox(-1.5f, -1, -3, 3, 2, 3, 0);
        this.mane00 = new ModelRenderer(this, 21, 0);
        this.mane00.setRotationPoint(0, -4, 0);
        this.mane00.addBox(-4, -3.5f, -3.01f, 8, 7, 7, 0);
        this.leftforeleg = new ModelRenderer(this, 0, 18);
        this.leftforeleg.setRotationPoint(1.5f, 16, -4);
        this.leftforeleg.addBox(-1, 0, -1, 2, 8, 2, 0);
        this.body = new ModelRenderer(this, 18, 14);
        this.body.setRotationPoint(0, 14, 1);
        this.body.addBox(-3, -2, -3, 6, 9, 6, 0);
        this.setRotateAngle(body, 1.57f, 0, 0);
        this.rightHorn04b = new ModelRenderer(this, 52, 12);
        this.rightHorn04b.mirror = true;
        this.rightHorn04b.setRotationPoint(0, 0, 0);
        this.rightHorn04b.addBox(-0.5f, -3, -0.3f, 1, 3, 1, 0);
        this.rightHorn02a = new ModelRenderer(this, 52, 7);
        this.rightHorn02a.mirror = true;
        this.rightHorn02a.setRotationPoint(0, -1.5f, -0.1f);
        this.rightHorn02a.addBox(-0.8f, -2, -0.8f, 1, 2, 1, 0);
        this.setRotateAngle(rightHorn02a, -0.7f, -0.21f, 0);
        this.leftHorn02c = new ModelRenderer(this, 52, 7);
        this.leftHorn02c.setRotationPoint(0, 0, 0);
        this.leftHorn02c.addBox(-0.2f, -2, -0.2f, 1, 2, 1, 0);
        this.leftHorn05 = new ModelRenderer(this, 58, 13);
        this.leftHorn05.setRotationPoint(0, -2.7f, 0);
        this.leftHorn05.addBox(-0.5f, -3, -0.5f, 1, 3, 1, 0);
        this.setRotateAngle(leftHorn05, -0.63f, 0, 0);
        this.rightHorn01 = new ModelRenderer(this, 55, 0);
        this.rightHorn01.mirror = true;
        this.rightHorn01.setRotationPoint(0, -2.5f, -0.1f);
        this.rightHorn01.addBox(-1, -2, -1, 2, 2, 2, 0);
        this.setRotateAngle(rightHorn01, -0.52f, -0.17f, 0);
        this.tail04 = new ModelRenderer(this, 44, 33);
        this.tail04.setRotationPoint(0, 1.3f, 0.1f);
        this.tail04.addBox(-0.5f, -0.5f, -0.59f, 2, 2, 1, 0);
        this.setRotateAngle(tail04, 0, 0, 0.79f);
        this.tail00.addChild(this.tail01);
        this.neck.addChild(this.head);
        this.neck.addChild(this.mane01);
        this.head.addChild(this.lowerJaw);
        this.leftHorn03a.addChild(this.leftHorn03c);
        this.head.addChild(this.leftEar);
        this.leftHorn02a.addChild(this.leftHorn03a);
        this.leftHorn01.addChild(this.leftHorn02a);
        this.rightHorn02a.addChild(this.rightHorn03a);
        this.leftHorn03a.addChild(this.leftHorn04a);
        this.tail01.addChild(this.tail02);
        this.leftHorn00.addChild(this.leftHorn01);
        this.tail02.addChild(this.tail03);
        this.head.addChild(this.rightEar);
        this.head.addChild(this.rightHorn00);
        this.leftHorn04a.addChild(this.leftHorn04b);
        this.rightHorn03a.addChild(this.rightHorn03c);
        this.rightHorn03a.addChild(this.rightHorn04a);
        this.body.addChild(this.neck);
        this.body.addChild(this.tail00);
        this.rightHorn04a.addChild(this.rightHorn05);
        this.rightHorn02a.addChild(this.rightHorn02c);
        this.mane00.addChild(this.mane02);
        this.head.addChild(this.leftHorn00);
        this.head.addChild(this.muzzle);
        this.body.addChild(this.mane00);
        this.rightHorn04a.addChild(this.rightHorn04b);
        this.rightHorn01.addChild(this.rightHorn02a);
        this.leftHorn02a.addChild(this.leftHorn02c);
        this.leftHorn04a.addChild(this.leftHorn05);
        this.rightHorn00.addChild(this.rightHorn01);
        this.tail03.addChild(this.tail04);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.leftHindleg.render(scale);
        this.rightforeleg.render(scale);
        this.rightHindleg.render(scale);
        this.leftforeleg.render(scale);
        this.body.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        float swingModifier = 0.9f;
        if (entity instanceof EntityLivingBase) {
            this.leftHindleg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingModifier * limbSwingAmount - 0f;
            this.rightHindleg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingModifier * limbSwingAmount - 0f;
            this.leftforeleg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F) * swingModifier * limbSwingAmount + 0f;
            this.rightforeleg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F + (float) Math.PI) * swingModifier * limbSwingAmount + 0f;

            tail00.rotateAngleY = MathHelper.sin(limbSwing * 0.25f) * 0.65F * limbSwingAmount + 0f;
            boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
            this.head.rotateAngleY = netHeadYaw * 0.017453292F;

            if (flag) {
                this.head.rotateAngleX = -((float) Math.PI / 4F);
            } else {
                this.head.rotateAngleX = headPitch * 0.017453292F;
            }
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