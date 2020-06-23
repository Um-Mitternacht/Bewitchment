package com.bewitchment.client.model.entity.living;

import com.bewitchment.common.entity.living.EntityToad;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * toad - cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelToad extends ModelBase {
    public final ModelRenderer body;
    public final ModelRenderer neck;
    public final ModelRenderer leftArm00;
    public final ModelRenderer rightArm00;
    public final ModelRenderer leftLeg00;
    public final ModelRenderer rightLeg00;
    public final ModelRenderer head;
    public final ModelRenderer jaw;
    public final ModelRenderer leftEye;
    public final ModelRenderer rightEye;
    public final ModelRenderer leftArm01;
    public final ModelRenderer leftHand;
    public final ModelRenderer rightArm01;
    public final ModelRenderer rightHand;
    public final ModelRenderer leftLeg01;
    public final ModelRenderer leftfoot;
    public final ModelRenderer rightLeg01;
    public final ModelRenderer rightfoot;

    public ModelToad() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0, 17.7f, -1.9f);
        this.body.addBox(-4, -2.5f, 0, 8, 6, 10, 0);
        this.setRotateAngle(body, -0.36f, 0, 0);
        this.leftArm00 = new ModelRenderer(this, 16, 25);
        this.leftArm00.setRotationPoint(3, 0.6f, 1.1f);
        this.leftArm00.addBox(0, -1, -1, 2, 2, 5, 0);
        this.setRotateAngle(leftArm00, -0.82f, 0, -0.28f);
        this.leftLeg01 = new ModelRenderer(this, 39, 19);
        this.leftLeg01.setRotationPoint(0.9f, 3.6f, -0.2f);
        this.leftLeg01.addBox(-1.2f, -1, 0, 2, 2, 5, 0);
        this.setRotateAngle(leftLeg01, 0.45f, 0.14f, 0.17f);
        this.rightArm00 = new ModelRenderer(this, 16, 25);
        this.rightArm00.mirror = true;
        this.rightArm00.setRotationPoint(-3, 0.6f, 1.1f);
        this.rightArm00.addBox(-2, -1, -1, 2, 2, 5, 0);
        this.setRotateAngle(rightArm00, -0.82f, 0, 0.28f);
        this.rightHand = new ModelRenderer(this, 3, 27);
        this.rightHand.mirror = true;
        this.rightHand.setRotationPoint(-0.1f, 4.5f, -0.3f);
        this.rightHand.addBox(-1.5f, 0, -2.7f, 3, 0, 4, 0);
        this.setRotateAngle(rightHand, 0.72f, -0.09f, -0.45f);
        this.jaw = new ModelRenderer(this, 37, 10);
        this.jaw.setRotationPoint(0, 0.4f, -0.6f);
        this.jaw.addBox(-3, 0, -5, 6, 2, 6, 0);
        this.neck = new ModelRenderer(this, 0, 17);
        this.neck.setRotationPoint(0, -0.9f, 1.3f);
        this.neck.addBox(-2.5f, -1.5f, -3, 5, 5, 4, 0);
        this.setRotateAngle(neck, -0.46f, 0, 0);
        this.leftLeg00 = new ModelRenderer(this, 22, 16);
        this.leftLeg00.setRotationPoint(3.3f, -0.8f, 7.5f);
        this.leftLeg00.addBox(0, -1, -1.5f, 2, 5, 3, 0);
        this.setRotateAngle(leftLeg00, -0.49f, 0, -0.17f);
        this.leftHand = new ModelRenderer(this, 3, 27);
        this.leftHand.setRotationPoint(0.1f, 4.5f, -0.3f);
        this.leftHand.addBox(-1.5f, 0, -2.7f, 3, 0, 4, 0);
        this.setRotateAngle(leftHand, 0.72f, 0.09f, 0.45f);
        this.rightfoot = new ModelRenderer(this, 49, 25);
        this.rightfoot.mirror = true;
        this.rightfoot.setRotationPoint(0.1f, 0.6f, 4.3f);
        this.rightfoot.addBox(-1.2f, -0.5f, -5, 2, 1, 5, 0);
        this.setRotateAngle(rightfoot, 0.4f, 0.31f, 0);
        this.head = new ModelRenderer(this, 37, 0);
        this.head.setRotationPoint(0, -0.2f, -2.2f);
        this.head.addBox(-3, -1.5f, -5.7f, 6, 2, 6, 0);
        this.setRotateAngle(head, 0.87f, 0, 0);
        this.rightArm01 = new ModelRenderer(this, 32, 25);
        this.rightArm01.mirror = true;
        this.rightArm01.setRotationPoint(-1, -0.1f, 2.8f);
        this.rightArm01.addBox(-1.01f, 0, -1, 2, 5, 2, 0);
        this.setRotateAngle(rightArm01, 0.45f, 0, 0);
        this.leftArm01 = new ModelRenderer(this, 32, 25);
        this.leftArm01.setRotationPoint(1, -0.1f, 2.8f);
        this.leftArm01.addBox(-0.99f, 0, -1, 2, 5, 2, 0);
        this.setRotateAngle(leftArm01, 0.45f, 0, 0);
        this.leftfoot = new ModelRenderer(this, 49, 25);
        this.leftfoot.setRotationPoint(-0.1f, 0.6f, 4.3f);
        this.leftfoot.addBox(-1.2f, -0.5f, -5, 2, 1, 5, 0);
        this.setRotateAngle(leftfoot, 0.4f, -0.31f, 0);
        this.rightLeg01 = new ModelRenderer(this, 39, 19);
        this.rightLeg01.mirror = true;
        this.rightLeg01.setRotationPoint(-0.9f, 3.6f, -0.2f);
        this.rightLeg01.addBox(-1.2f, -1, 0, 2, 2, 5, 0);
        this.setRotateAngle(rightLeg01, 0.45f, -0.14f, -0.17f);
        this.rightLeg00 = new ModelRenderer(this, 22, 16);
        this.rightLeg00.mirror = true;
        this.rightLeg00.setRotationPoint(-3.3f, -0.8f, 7.5f);
        this.rightLeg00.addBox(-2, -1, -1.5f, 2, 5, 3, 0);
        this.setRotateAngle(rightLeg00, -0.49f, 0, 0.17f);
        this.leftEye = new ModelRenderer(this, 27, 0);
        this.leftEye.setRotationPoint(1.3f, -0.5f, -2.3f);
        this.leftEye.addBox(0, -2.1f, -1.5f, 2, 2, 3, 0);
        this.setRotateAngle(leftEye, 0, 0.18f, 0.14f);
        this.rightEye = new ModelRenderer(this, 27, 0);
        this.rightEye.mirror = true;
        this.rightEye.setRotationPoint(-1.3f, -0.5f, -2.3f);
        this.rightEye.addBox(-2, -2.1f, -1.5f, 2, 2, 3, 0);
        this.setRotateAngle(rightEye, 0, -0.18f, -0.14f);
        this.body.addChild(this.leftArm00);
        this.leftLeg00.addChild(this.leftLeg01);
        this.body.addChild(this.rightArm00);
        this.rightArm01.addChild(this.rightHand);
        this.head.addChild(this.jaw);
        this.body.addChild(this.neck);
        this.body.addChild(this.leftLeg00);
        this.leftArm01.addChild(this.leftHand);
        this.rightLeg01.addChild(this.rightfoot);
        this.neck.addChild(this.head);
        this.rightArm00.addChild(this.rightArm01);
        this.leftArm00.addChild(this.leftArm01);
        this.leftLeg01.addChild(this.leftfoot);
        this.rightLeg00.addChild(this.rightLeg01);
        this.body.addChild(this.rightLeg00);
        this.head.addChild(this.leftEye);
        this.head.addChild(this.rightEye);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.body.render(scale);
        this.body.rotateAngleX = -0.36651914291F;
        this.leftLeg00.rotateAngleX = -0.51225413546F;
        this.leftLeg01.rotateAngleX = 0.45378560551F;
        this.rightLeg00.rotateAngleX = leftLeg00.rotateAngleX;
        this.rightLeg01.rotateAngleX = leftLeg01.rotateAngleX;
        this.rightfoot.rotateAngleX = leftfoot.rotateAngleX;
        this.leftArm00.rotateAngleX = -0.8159414253F;
        this.leftArm01.rotateAngleX = 0.45378560551F;
        this.rightArm00.rotateAngleX = leftArm00.rotateAngleX;
        this.rightArm01.rotateAngleX = leftArm01.rotateAngleX;
        this.leftfoot.rotateAngleX = this.leftfoot.rotateAngleX + (0.41887902047F - this.leftfoot.rotateAngleX);
        this.rightfoot.rotateAngleX = leftfoot.rotateAngleX;
    }

    //Fixme: Barely noticable gliding
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {

        float time = entity.ticksExisted * 0.1F;
        this.jaw.rotationPointY = (float) (0.5F + 0.02 * MathHelper.sin(time));

        if (entity instanceof EntityToad) {
            EntityToad toad = (EntityToad) entity;
            if (toad.isSitting()) {
                leftfoot.rotateAngleY = 1.0471975512F;
                rightfoot.rotateAngleY = -1.0471975512F;
            } else {
                leftfoot.rotateAngleY = -0.6000000238418579F;
                rightfoot.rotateAngleY = 0.6000000238418579F;
            }
            float timer = toad.getAnimationTime();
            if (limbSwingAmount > 0.1 || timer != 0) {
                timer = toad.postIncAnimation();
                if (timer < 25) {
                    this.body.offsetY = toad.getAnimationHeight() + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    toad.setAnimationHeight(this.body.offsetY);
                    this.body.rotateAngleX = this.body.rotateAngleX + (0 - this.body.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    this.leftLeg00.rotateAngleX = this.leftLeg00.rotateAngleX + (1.3962634016F - this.leftLeg00.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    this.leftLeg01.rotateAngleX = this.leftLeg01.rotateAngleX + (-1.3962634016F - this.leftLeg01.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    this.leftfoot.rotateAngleX = this.leftfoot.rotateAngleX + (2.35619449019F - this.leftfoot.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    this.rightLeg00.rotateAngleX = leftLeg00.rotateAngleX;
                    this.rightLeg01.rotateAngleX = leftLeg01.rotateAngleX;
                    this.rightfoot.rotateAngleX = leftfoot.rotateAngleX;
                    this.leftArm00.rotateAngleX = this.leftArm00.rotateAngleX + (-2.35619449019F - this.leftArm00.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    this.leftArm01.rotateAngleX = this.leftArm01.rotateAngleX + (1.3962634016F - this.leftArm01.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * (timer / 75F);
                    this.rightArm00.rotateAngleX = leftArm00.rotateAngleX;
                    this.rightArm01.rotateAngleX = leftArm01.rotateAngleX;

                } else if (timer < 50) {
                    this.body.offsetY = toad.getAnimationHeight() + (0F - toad.getAnimationHeight()) * ((timer - 25) / 75);
                    toad.setAnimationHeight(this.body.offsetY);
                    this.body.rotateAngleX = this.body.rotateAngleX + (0.36651914291F - this.body.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 24F) / 75F);
                    this.leftLeg00.rotateAngleX = this.leftLeg00.rotateAngleX + (-0.51225413546F - this.leftLeg00.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 24F) / 75F);
                    this.leftLeg01.rotateAngleX = this.leftLeg01.rotateAngleX + (0.45378560551F - this.leftLeg01.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 24F) / 75F);
                    this.leftfoot.rotateAngleX = this.leftfoot.rotateAngleX + (0.41887902047F - this.leftfoot.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 24F) / 75F);
                    this.rightLeg00.rotateAngleX = leftLeg00.rotateAngleX;
                    this.rightLeg01.rotateAngleX = leftLeg01.rotateAngleX;
                    this.rightfoot.rotateAngleX = leftfoot.rotateAngleX;
                    this.leftArm00.rotateAngleX = this.leftArm00.rotateAngleX + (-0.8159414253F - this.leftArm00.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 24F) / 75F);
                    this.leftArm01.rotateAngleX = this.leftArm01.rotateAngleX + (0.45378560551F - this.leftArm01.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 24F) / 75F);
                    this.rightArm00.rotateAngleX = leftArm00.rotateAngleX;
                    this.rightArm01.rotateAngleX = leftArm01.rotateAngleX;

                } else if (timer < 75) {
                    this.body.rotateAngleX = this.body.rotateAngleX + (-0.36651914291F - this.body.rotateAngleX) + (-1.5F - toad.getAnimationHeight()) * ((timer - 49F) / 75F);
                } else {
                    this.body.rotateAngleX = -0.36651914291F;
                    this.leftLeg00.rotateAngleX = -0.51225413546F;
                    this.leftLeg01.rotateAngleX = 0.45378560551F;
                    this.rightLeg00.rotateAngleX = leftLeg00.rotateAngleX;
                    this.rightLeg01.rotateAngleX = leftLeg01.rotateAngleX;
                    this.rightfoot.rotateAngleX = leftfoot.rotateAngleX;
                    this.leftArm00.rotateAngleX = -0.8159414253F;
                    this.leftArm01.rotateAngleX = 0.45378560551F;
                    this.rightArm00.rotateAngleX = leftArm00.rotateAngleX;
                    this.rightArm01.rotateAngleX = leftArm01.rotateAngleX;
                    toad.resetAnimationTime();
                    this.body.offsetY = 0;
                    toad.resetAnimationHeight();
                }

            } else {
                toad.resetAnimationTime();
                this.body.offsetY = 0;
                toad.resetAnimationHeight();
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