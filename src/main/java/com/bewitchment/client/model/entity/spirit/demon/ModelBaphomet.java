package com.bewitchment.client.model.entity.spirit.demon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * baphomet - cybercat5555
 * Created using Tabula 7.0.1
 */
public class ModelBaphomet extends ModelBiped {
    public ModelRenderer boobLeft;
    public ModelRenderer boobRight;
    public ModelRenderer stomach;
    public ModelRenderer boobs;
    public ModelRenderer head;
    public ModelRenderer leftWing00;
    public ModelRenderer rightWing00;
    public ModelRenderer hips;
    public ModelRenderer backLoincloth00;
    public ModelRenderer frontLoincloth00;
    public ModelRenderer backLoincloth01;
    public ModelRenderer frontLoincloth01;
    public ModelRenderer lHorn01;
    public ModelRenderer rHorn01;
    public ModelRenderer snout;
    public ModelRenderer jawUpper00;
    public ModelRenderer jawLower;
    public ModelRenderer leftEar;
    public ModelRenderer rightEar;
    public ModelRenderer torch00;
    public ModelRenderer lCheekFur;
    public ModelRenderer rCheekFur;
    public ModelRenderer lHorn02a;
    public ModelRenderer lHorn02b;
    public ModelRenderer lHorn02c;
    public ModelRenderer lHorn03d;
    public ModelRenderer lHorn03a;
    public ModelRenderer lHorn03b;
    public ModelRenderer lHorn03c;
    public ModelRenderer lHorn03d_1;
    public ModelRenderer lHorn04;
    public ModelRenderer lHorn05;
    public ModelRenderer rHorn02a;
    public ModelRenderer rHorn02b;
    public ModelRenderer rHorn02c;
    public ModelRenderer rHorn03d;
    public ModelRenderer rHorn03a;
    public ModelRenderer rHorn03b;
    public ModelRenderer rHorn03c;
    public ModelRenderer rHorn03d_1;
    public ModelRenderer rHorn04;
    public ModelRenderer rHorn05;
    public ModelRenderer jawUpper01;
    public ModelRenderer beard;
    public ModelRenderer torch01a;
    public ModelRenderer torch01b;
    public ModelRenderer torch01c;
    public ModelRenderer torch01d;
    public ModelRenderer torch02a;
    public ModelRenderer torch03a;
    public ModelRenderer torch03b;
    public ModelRenderer torch03d;
    public ModelRenderer torch03c;
    public ModelRenderer leftArm01;
    public ModelRenderer rightArm01;
    public ModelRenderer leftLeg01;
    public ModelRenderer leftLeg02;
    public ModelRenderer leftHoof;
    public ModelRenderer rightLeg01;
    public ModelRenderer rightLeg02;
    public ModelRenderer rHoof;
    public ModelRenderer leftWing01;
    public ModelRenderer leftWing02;
    public ModelRenderer leftFeathers00;
    public ModelRenderer leftWing03;
    public ModelRenderer leftFeathers01;
    public ModelRenderer rightWing01;
    public ModelRenderer rightWing02;
    public ModelRenderer rightFeathers00;
    public ModelRenderer rightWing03;
    public ModelRenderer rightFeathers01;

    public ModelBaphomet() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.lHorn03d_1 = new ModelRenderer(this, 0, 4);
        this.lHorn03d_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lHorn03d_1.addBox(-0.3F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
        this.leftLeg01 = new ModelRenderer(this, 0, 32);
        this.leftLeg01.setRotationPoint(0.5F, 7.4F, -1.7F);
        this.leftLeg01.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(leftLeg01, 0.9075712110370513F, -0.13962634015954636F, 0.0F);
        this.lCheekFur = new ModelRenderer(this, 61, 54);
        this.lCheekFur.setRotationPoint(2.4F, -3.0F, -0.3F);
        this.lCheekFur.addBox(0.0F, -2.5F, 0.0F, 5, 5, 0, 0.0F);
        this.setRotateAngle(lCheekFur, -0.6981317007977318F, -0.3490658503988659F, 0.4363323129985824F);
        this.rHorn02c = new ModelRenderer(this, 0, 0);
        this.rHorn02c.mirror = true;
        this.rHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rHorn02c.addBox(-0.8F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
        this.torch03d = new ModelRenderer(this, 122, 10);
        this.torch03d.setRotationPoint(0.6F, -1.5F, 0.6F);
        this.torch03d.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(torch03d, -0.24434609527920614F, -0.20943951023931953F, 0.2792526803190927F);
        this.leftHoof = new ModelRenderer(this, 0, 55);
        this.leftHoof.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.leftHoof.addBox(-1.5F, 0.0F, -2.8F, 3, 2, 4, 0.0F);
        this.boobRight = new ModelRenderer(this, 18, 52);
        this.boobRight.mirror = true;
        this.boobRight.setRotationPoint(-1.6F, 1.9F, -0.8F);
        this.boobRight.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(boobRight, -0.6981317007977318F, 0.17453292519943295F, 0.0F);
        this.head = new ModelRenderer(this, 1, 2);
        this.head.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.head.addBox(-3.5F, -7.0F, -3.5F, 7, 7, 7, 0.0F);
        this.rightFeathers00 = new ModelRenderer(this, 78, 29);
        this.rightFeathers00.setRotationPoint(-0.2F, 0.0F, 0.0F);
        this.rightFeathers00.addBox(0.0F, -0.6F, -11.1F, 0, 10, 22, 0.0F);
        this.setRotateAngle(rightFeathers00, -0.17453292519943295F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 48, 0);
        this.rightEar.mirror = true;
        this.rightEar.setRotationPoint(-2.6F, -5.4F, 0.1F);
        this.rightEar.addBox(-4.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(rightEar, -0.3490658503988659F, 0.0F, -0.3141592653589793F);
        this.torch03b = new ModelRenderer(this, 122, 6);
        this.torch03b.mirror = true;
        this.torch03b.setRotationPoint(-0.6F, -1.5F, -0.6F);
        this.torch03b.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(torch03b, 0.24434609527920614F, -0.20943951023931953F, -0.2792526803190927F);
        this.rightArm01 = new ModelRenderer(this, 61, 19);
        this.rightArm01.mirror = true;
        this.rightArm01.setRotationPoint(-0.5F, 6.0F, -0.0F);
        this.rightArm01.addBox(-1.5F, -0.3F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(rightArm01, -0.17453292519943295F, 0.0F, 0.0F);
        this.torch03c = new ModelRenderer(this, 122, 10);
        this.torch03c.mirror = true;
        this.torch03c.setRotationPoint(-0.6F, -1.5F, 0.6F);
        this.torch03c.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(torch03c, -0.24434609527920614F, 0.13962634015954636F, -0.2792526803190927F);
        this.torch00 = new ModelRenderer(this, 117, 0);
        this.torch00.setRotationPoint(0.0F, -6.6F, -1.2F);
        this.torch00.addBox(-1.0F, -2.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(torch00, 0.0F, 0.7853981633974483F, 0.0F);
        this.rHorn03a = new ModelRenderer(this, 0, 4);
        this.rHorn03a.mirror = true;
        this.rHorn03a.setRotationPoint(0.0F, -2.7F, 0.0F);
        this.rHorn03a.addBox(-0.7F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rHorn03a, -0.10471975511965977F, 0.0F, -0.17453292519943295F);
        this.rHorn04 = new ModelRenderer(this, 4, 0);
        this.rHorn04.mirror = true;
        this.rHorn04.setRotationPoint(0.0F, -2.8F, 0.0F);
        this.rHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rHorn04, 0.05235987755982988F, 0.0F, -0.13962634015954636F);
        this.rightLeg01 = new ModelRenderer(this, 0, 32);
        this.rightLeg01.mirror = true;
        this.rightLeg01.setRotationPoint(-0.5F, 7.4F, -1.7F);
        this.rightLeg01.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(rightLeg01, 0.9075712110370513F, 0.13962634015954636F, 0.0F);
        this.rHorn02b = new ModelRenderer(this, 0, 0);
        this.rHorn02b.mirror = true;
        this.rHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rHorn02b.addBox(-0.2F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.mirror = true;
        this.bipedRightLeg.setRotationPoint(-2.4F, 13.7F, 0.3F);
        this.bipedRightLeg.addBox(-2.0F, -1.6F, -2.9F, 4, 11, 5, 0.0F);
        this.setRotateAngle(bipedRightLeg, -0.2792526803190927F, 0.0F, 0.13962634015954636F);
        this.lHorn03a = new ModelRenderer(this, 0, 4);
        this.lHorn03a.setRotationPoint(0.0F, -2.7F, 0.0F);
        this.lHorn03a.addBox(-0.7F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
        this.setRotateAngle(lHorn03a, -0.10471975511965977F, 0.0F, 0.17453292519943295F);
        this.leftFeathers01 = new ModelRenderer(this, 78, 2);
        this.leftFeathers01.mirror = true;
        this.leftFeathers01.setRotationPoint(-0.1F, 2.3F, 0.0F);
        this.leftFeathers01.addBox(0.0F, -0.6F, -14.9F, 0, 30, 18, 0.0F);
        this.setRotateAngle(leftFeathers01, -0.4363323129985824F, 0.0F, 0.0F);
        this.snout = new ModelRenderer(this, 29, 2);
        this.snout.setRotationPoint(0.0F, -4.6F, -2.5F);
        this.snout.addBox(-2.0F, -1.9F, -5.1F, 4, 3, 5, 0.0F);
        this.setRotateAngle(snout, 0.5235987755982988F, 0.0F, 0.0F);
        this.jawUpper00 = new ModelRenderer(this, 43, 11);
        this.jawUpper00.setRotationPoint(0.0F, -2.5F, -2.2F);
        this.jawUpper00.addBox(-1.6F, -1.0F, -5.0F, 4, 2, 5, 0.0F);
        this.setRotateAngle(jawUpper00, 0.06981317007977318F, 0.0F, 0.0F);
        this.rHorn03d = new ModelRenderer(this, 0, 0);
        this.rHorn03d.mirror = true;
        this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rHorn03d.addBox(-0.2F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
        this.rightWing00 = new ModelRenderer(this, 43, 32);
        this.rightWing00.mirror = true;
        this.rightWing00.setRotationPoint(-1.8F, 2.8F, 1.4F);
        this.rightWing00.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 6, 0.0F);
        this.setRotateAngle(rightWing00, 0.3490658503988659F, -0.6457718232379019F, 0.0F);
        this.leftWing01 = new ModelRenderer(this, 43, 41);
        this.leftWing01.setRotationPoint(0.1F, 0.2F, 5.6F);
        this.leftWing01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(leftWing01, 0.5235987755982988F, 0.13962634015954636F, 0.0F);
        this.frontLoincloth00 = new ModelRenderer(this, 65, 0);
        this.frontLoincloth00.setRotationPoint(0.0F, 0.5F, -1.7F);
        this.frontLoincloth00.addBox(-5.0F, 0.0F, -0.5F, 10, 9, 3, 0.0F);
        this.setRotateAngle(frontLoincloth00, -0.3490658503988659F, 0.0F, 0.0F);
        this.leftWing03 = new ModelRenderer(this, 51, 52);
        this.leftWing03.setRotationPoint(0.0F, 8.6F, 0.2F);
        this.leftWing03.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
        this.setRotateAngle(leftWing03, -0.47123889803846897F, 0.0F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 19, 19);
        this.bipedBody.setRotationPoint(0.0F, -10.9F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.5F, 8, 6, 5, 0.0F);
        this.rHorn03b = new ModelRenderer(this, 0, 4);
        this.rHorn03b.mirror = true;
        this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rHorn03b.addBox(-0.3F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
        this.frontLoincloth01 = new ModelRenderer(this, 65, 13);
        this.frontLoincloth01.setRotationPoint(0.0F, 8.8F, 0.0F);
        this.frontLoincloth01.addBox(-4.5F, -0.05F, -0.53F, 9, 4, 1, 0.0F);
        this.setRotateAngle(frontLoincloth01, 0.40142572795869574F, 0.0F, 0.0F);
        this.torch01a = new ModelRenderer(this, 117, 6);
        this.torch01a.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.torch01a.addBox(-0.2F, -3.8F, -0.8F, 1, 4, 1, 0.0F);
        this.torch03a = new ModelRenderer(this, 122, 6);
        this.torch03a.setRotationPoint(0.6F, -1.5F, -0.6F);
        this.torch03a.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(torch03a, 0.24434609527920614F, 0.13962634015954636F, 0.2792526803190927F);
        this.lHorn05 = new ModelRenderer(this, 4, 4);
        this.lHorn05.setRotationPoint(0.0F, -2.7F, 0.0F);
        this.lHorn05.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(lHorn05, 0.05235987755982988F, 0.0F, -0.13962634015954636F);
        this.lHorn03d = new ModelRenderer(this, 0, 0);
        this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lHorn03d.addBox(-0.2F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
        this.torch01c = new ModelRenderer(this, 117, 6);
        this.torch01c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.torch01c.addBox(-0.2F, -3.8F, -0.2F, 1, 4, 1, 0.0F);
        this.rightLeg02 = new ModelRenderer(this, 0, 44);
        this.rightLeg02.mirror = true;
        this.rightLeg02.setRotationPoint(0.0F, 6.0F, 0.2F);
        this.rightLeg02.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.setRotateAngle(rightLeg02, -0.6108652381980153F, 0.0F, -0.10471975511965977F);
        this.leftWing00 = new ModelRenderer(this, 43, 32);
        this.leftWing00.setRotationPoint(1.8F, 2.8F, 1.4F);
        this.leftWing00.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 6, 0.0F);
        this.setRotateAngle(leftWing00, 0.3490658503988659F, 0.6457718232379019F, 0.0F);
        this.rHoof = new ModelRenderer(this, 0, 55);
        this.rHoof.mirror = true;
        this.rHoof.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.rHoof.addBox(-1.5F, 0.0F, -2.8F, 3, 2, 4, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 46, 19);
        this.bipedRightArm.mirror = true;
        this.bipedRightArm.setRotationPoint(-5.0F, 1.9F, 0.0F);
        this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.08726646259971647F, 0.0F, 0.10000736613927509F);
        this.boobLeft = new ModelRenderer(this, 18, 52);
        this.boobLeft.setRotationPoint(1.6F, 1.9F, -0.8F);
        this.boobLeft.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(boobLeft, -0.6981317007977318F, -0.17453292519943295F, 0.0F);
        this.beard = new ModelRenderer(this, 18, 59);
        this.beard.setRotationPoint(0.0F, 0.3F, -2.4F);
        this.beard.addBox(-1.5F, 0.0F, -1.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(beard, -0.03490658503988659F, 0.0F, 0.0F);
        this.rHorn01 = new ModelRenderer(this, 23, 0);
        this.rHorn01.mirror = true;
        this.rHorn01.setRotationPoint(-2.9F, -7.2F, -0.2F);
        this.rHorn01.addBox(-1.0F, -2.7F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(rHorn01, -0.17453292519943295F, -0.13962634015954636F, -0.40142572795869574F);
        this.jawUpper01 = new ModelRenderer(this, 30, 11);
        this.jawUpper01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jawUpper01.addBox(-2.3F, -1.0F, -5.0F, 1, 2, 5, 0.0F);
        this.rightFeathers01 = new ModelRenderer(this, 78, 2);
        this.rightFeathers01.setRotationPoint(0.1F, 2.3F, 0.0F);
        this.rightFeathers01.addBox(0.0F, -0.6F, -14.9F, 0, 30, 18, 0.0F);
        this.setRotateAngle(rightFeathers01, -0.4363323129985824F, 0.0F, 0.0F);
        this.lHorn03c = new ModelRenderer(this, 0, 4);
        this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lHorn03c.addBox(-0.7F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
        this.rightWing01 = new ModelRenderer(this, 43, 41);
        this.rightWing01.mirror = true;
        this.rightWing01.setRotationPoint(-0.1F, 0.2F, 5.6F);
        this.rightWing01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(rightWing01, 0.5235987755982988F, -0.13962634015954636F, 0.0F);
        this.backLoincloth01 = new ModelRenderer(this, 89, 12);
        this.backLoincloth01.setRotationPoint(0.0F, 7.8F, 0.7F);
        this.backLoincloth01.addBox(-4.0F, 0.05F, -1.0F, 8, 4, 2, 0.0F);
        this.setRotateAngle(backLoincloth01, -0.08726646259971647F, 0.0F, 0.0F);
        this.stomach = new ModelRenderer(this, 19, 31);
        this.stomach.setRotationPoint(0.0F, 5.6F, 0.0F);
        this.stomach.addBox(-3.5F, 0.0F, -2.0F, 7, 7, 4, 0.0F);
        this.leftEar = new ModelRenderer(this, 48, 0);
        this.leftEar.setRotationPoint(2.6F, -5.4F, 0.1F);
        this.leftEar.addBox(0.0F, -0.5F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(leftEar, -0.3490658503988659F, 0.0F, 0.3141592653589793F);
        this.rHorn02a = new ModelRenderer(this, 0, 0);
        this.rHorn02a.mirror = true;
        this.rHorn02a.setRotationPoint(0.0F, -2.2F, 0.0F);
        this.rHorn02a.addBox(-0.8F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rHorn02a, -0.17453292519943295F, 0.0F, -0.2617993877991494F);
        this.lHorn01 = new ModelRenderer(this, 23, 0);
        this.lHorn01.setRotationPoint(2.9F, -7.2F, -0.2F);
        this.lHorn01.addBox(-1.0F, -2.7F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(lHorn01, -0.17453292519943295F, 0.13962634015954636F, 0.40142572795869574F);
        this.rHorn03c = new ModelRenderer(this, 0, 4);
        this.rHorn03c.mirror = true;
        this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rHorn03c.addBox(-0.7F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
        this.lHorn03b = new ModelRenderer(this, 0, 4);
        this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lHorn03b.addBox(-0.3F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
        this.lHorn02b = new ModelRenderer(this, 0, 0);
        this.lHorn02b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lHorn02b.addBox(-0.2F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
        this.rCheekFur = new ModelRenderer(this, 61, 54);
        this.rCheekFur.mirror = true;
        this.rCheekFur.setRotationPoint(-2.4F, -3.0F, -0.3F);
        this.rCheekFur.addBox(-5.0F, -2.5F, 0.0F, 5, 5, 0, 0.0F);
        this.setRotateAngle(rCheekFur, -0.6981317007977318F, 0.3490658503988659F, -0.4363323129985824F);
        this.rHorn05 = new ModelRenderer(this, 4, 4);
        this.rHorn05.mirror = true;
        this.rHorn05.setRotationPoint(0.0F, -2.7F, 0.0F);
        this.rHorn05.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(rHorn05, 0.05235987755982988F, 0.0F, 0.13962634015954636F);
        this.torch01d = new ModelRenderer(this, 117, 6);
        this.torch01d.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.torch01d.addBox(-0.8F, -3.8F, -0.2F, 1, 4, 1, 0.0F);
        this.torch01b = new ModelRenderer(this, 117, 6);
        this.torch01b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.torch01b.addBox(-0.8F, -3.8F, -0.8F, 1, 4, 1, 0.0F);
        this.jawLower = new ModelRenderer(this, 48, 5);
        this.jawLower.setRotationPoint(0.0F, -1.0F, -3.0F);
        this.jawLower.addBox(-2.0F, -0.5F, -4.0F, 4, 1, 4, 0.0F);
        this.setRotateAngle(jawLower, -0.03490658503988659F, 0.0F, 0.0F);
        this.lHorn04 = new ModelRenderer(this, 4, 0);
        this.lHorn04.setRotationPoint(0.0F, -2.8F, 0.0F);
        this.lHorn04.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(lHorn04, 0.05235987755982988F, 0.0F, 0.13962634015954636F);
        this.rightWing03 = new ModelRenderer(this, 51, 52);
        this.rightWing03.mirror = true;
        this.rightWing03.setRotationPoint(0.0F, 8.6F, 0.2F);
        this.rightWing03.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
        this.setRotateAngle(rightWing03, -0.47123889803846897F, 0.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.setRotationPoint(2.4F, 13.7F, 0.3F);
        this.bipedLeftLeg.addBox(-2.0F, -1.6F, -2.9F, 4, 11, 5, 0.0F);
        this.setRotateAngle(bipedLeftLeg, -0.2792526803190927F, 0.0F, -0.13962634015954636F);
        this.lHorn02a = new ModelRenderer(this, 0, 0);
        this.lHorn02a.setRotationPoint(0.0F, -2.2F, 0.0F);
        this.lHorn02a.addBox(-0.8F, -3.0F, -0.8F, 1, 3, 1, 0.0F);
        this.setRotateAngle(lHorn02a, -0.17453292519943295F, 0.0F, 0.2617993877991494F);
        this.rightWing02 = new ModelRenderer(this, 42, 53);
        this.rightWing02.mirror = true;
        this.rightWing02.setRotationPoint(-0.1F, 0.4F, 8.0F);
        this.rightWing02.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(rightWing02, 0.13962634015954636F, 0.0F, 0.0F);
        this.leftArm01 = new ModelRenderer(this, 61, 19);
        this.leftArm01.setRotationPoint(0.5F, 6.0F, -0.0F);
        this.leftArm01.addBox(-1.5F, -0.3F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(leftArm01, -0.17453292519943295F, 0.0F, 0.0F);
        this.backLoincloth00 = new ModelRenderer(this, 92, 0);
        this.backLoincloth00.setRotationPoint(0.0F, 0.1F, 0.9F);
        this.backLoincloth00.addBox(-4.5F, 0.0F, -1.2F, 9, 8, 3, 0.0F);
        this.setRotateAngle(backLoincloth00, 0.08726646259971647F, 0.0F, 0.0F);
        this.torch02a = new ModelRenderer(this, 115, 12);
        this.torch02a.setRotationPoint(0.0F, -3.6F, 0.0F);
        this.torch02a.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
        this.hips = new ModelRenderer(this, 16, 43);
        this.hips.setRotationPoint(0.0F, 5.4F, 0.0F);
        this.hips.addBox(-4.0F, 0.0F, -2.3F, 8, 3, 5, 0.0F);
        this.lHorn02c = new ModelRenderer(this, 0, 0);
        this.lHorn02c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lHorn02c.addBox(-0.8F, -3.0F, -0.2F, 1, 3, 1, 0.0F);
        this.rHorn03d_1 = new ModelRenderer(this, 0, 4);
        this.rHorn03d_1.mirror = true;
        this.rHorn03d_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rHorn03d_1.addBox(-0.3F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
        this.leftFeathers00 = new ModelRenderer(this, 78, 29);
        this.leftFeathers00.mirror = true;
        this.leftFeathers00.setRotationPoint(0.2F, 0.0F, 0.0F);
        this.leftFeathers00.addBox(0.0F, -0.6F, -11.1F, 0, 10, 22, 0.0F);
        this.setRotateAngle(leftFeathers00, -0.17453292519943295F, 0.0F, 0.0F);
        this.boobs = new ModelRenderer(this, 57, 42);
        this.boobs.setRotationPoint(0.0F, 1.9F, -0.9F);
        this.boobs.addBox(-3.5F, 0.0F, -2.0F, 7, 3, 3, 0.0F);
        this.setRotateAngle(boobs, -0.6108652381980153F, 0.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 46, 19);
        this.bipedLeftArm.setRotationPoint(5.0F, 1.9F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(bipedLeftArm, 0.08726646259971647F, 0.0F, -0.10000736613927509F);
        this.leftLeg02 = new ModelRenderer(this, 0, 44);
        this.leftLeg02.setRotationPoint(0.0F, 6.0F, 0.2F);
        this.leftLeg02.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.setRotateAngle(leftLeg02, -0.6108652381980153F, 0.0F, 0.10471975511965977F);
        this.leftWing02 = new ModelRenderer(this, 42, 53);
        this.leftWing02.setRotationPoint(0.1F, 0.4F, 8.0F);
        this.leftWing02.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(leftWing02, 0.13962634015954636F, 0.0F, 0.0F);
        this.lHorn03a.addChild(this.lHorn03d_1);
        this.bipedLeftLeg.addChild(this.leftLeg01);
        this.head.addChild(this.lCheekFur);
        this.rHorn02a.addChild(this.rHorn02c);
        this.torch02a.addChild(this.torch03d);
        this.leftLeg02.addChild(this.leftHoof);
        this.bipedBody.addChild(this.boobRight);
        this.bipedBody.addChild(this.head);
        this.rightWing01.addChild(this.rightFeathers00);
        this.head.addChild(this.rightEar);
        this.torch02a.addChild(this.torch03b);
        this.bipedRightArm.addChild(this.rightArm01);
        this.torch02a.addChild(this.torch03c);
        this.head.addChild(this.torch00);
        this.rHorn02a.addChild(this.rHorn03a);
        this.rHorn03a.addChild(this.rHorn04);
        this.bipedRightLeg.addChild(this.rightLeg01);
        this.rHorn02a.addChild(this.rHorn02b);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.lHorn02a.addChild(this.lHorn03a);
        this.leftWing02.addChild(this.leftFeathers01);
        this.head.addChild(this.snout);
        this.head.addChild(this.jawUpper00);
        this.rHorn02a.addChild(this.rHorn03d);
        this.bipedBody.addChild(this.rightWing00);
        this.leftWing00.addChild(this.leftWing01);
        this.hips.addChild(this.frontLoincloth00);
        this.leftWing02.addChild(this.leftWing03);
        this.rHorn03a.addChild(this.rHorn03b);
        this.frontLoincloth00.addChild(this.frontLoincloth01);
        this.torch00.addChild(this.torch01a);
        this.torch02a.addChild(this.torch03a);
        this.lHorn04.addChild(this.lHorn05);
        this.lHorn02a.addChild(this.lHorn03d);
        this.torch01a.addChild(this.torch01c);
        this.rightLeg01.addChild(this.rightLeg02);
        this.bipedBody.addChild(this.leftWing00);
        this.rightLeg02.addChild(this.rHoof);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.boobLeft);
        this.jawLower.addChild(this.beard);
        this.head.addChild(this.rHorn01);
        this.jawUpper00.addChild(this.jawUpper01);
        this.rightWing02.addChild(this.rightFeathers01);
        this.lHorn03a.addChild(this.lHorn03c);
        this.rightWing00.addChild(this.rightWing01);
        this.backLoincloth00.addChild(this.backLoincloth01);
        this.bipedBody.addChild(this.stomach);
        this.head.addChild(this.leftEar);
        this.rHorn01.addChild(this.rHorn02a);
        this.head.addChild(this.lHorn01);
        this.rHorn03a.addChild(this.rHorn03c);
        this.lHorn03a.addChild(this.lHorn03b);
        this.lHorn02a.addChild(this.lHorn02b);
        this.head.addChild(this.rCheekFur);
        this.rHorn04.addChild(this.rHorn05);
        this.torch01a.addChild(this.torch01d);
        this.torch01a.addChild(this.torch01b);
        this.head.addChild(this.jawLower);
        this.lHorn03a.addChild(this.lHorn04);
        this.rightWing02.addChild(this.rightWing03);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.lHorn01.addChild(this.lHorn02a);
        this.rightWing01.addChild(this.rightWing02);
        this.bipedLeftArm.addChild(this.leftArm01);
        this.hips.addChild(this.backLoincloth00);
        this.torch01a.addChild(this.torch02a);
        this.stomach.addChild(this.hips);
        this.lHorn02a.addChild(this.lHorn02c);
        this.rHorn03a.addChild(this.rHorn03d_1);
        this.leftWing01.addChild(this.leftFeathers00);
        this.bipedBody.addChild(this.boobs);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.leftLeg01.addChild(this.leftLeg02);
        this.leftWing01.addChild(this.leftWing02);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bipedBody.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
        this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

        if (flag) {
            this.bipedHead.rotateAngleX = -((float) Math.PI / 4F);
        } else {
            this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
        }

        this.bipedBody.rotateAngleY = 0.0F;
        float f = 1.0F;

        if (flag) {
            f = (float) (entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        float swingMod = 0.3F;
        this.bipedLeftLeg.rotateAngleX = MathHelper.sin(limbSwing * 0.8665F + (float) Math.PI) * swingMod * limbSwingAmount - 0.26F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.8665F) * swingMod * limbSwingAmount - 0.26F;


        this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.bipedRightArm.rotateAngleZ = 0.10000736613927509F;
        this.bipedLeftArm.rotateAngleZ = -0.10000736613927509F;

        if (this.isRiding) {
            this.bipedRightArm.rotateAngleX += -((float) Math.PI / 5F);
            this.bipedLeftArm.rotateAngleX += -((float) Math.PI / 5F);
            this.bipedRightLeg.rotateAngleX = -1.4137167F;
            this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 10F);
            this.bipedRightLeg.rotateAngleZ = 0.07853982F;
            this.bipedLeftLeg.rotateAngleX = -1.4137167F;
            this.bipedLeftLeg.rotateAngleY = -((float) Math.PI / 10F);
            this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
        }

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedRightArm.rotateAngleZ = 0.0F;

        switch (this.leftArmPose) {
            case EMPTY:
                this.bipedLeftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedLeftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                this.bipedLeftArm.rotateAngleY = 0.0F;
        }

        switch (this.rightArmPose) {
            case EMPTY:
                this.bipedRightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedRightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                this.bipedRightArm.rotateAngleY = 0.0F;
        }

        if (this.swingProgress > 0.0F) {
            EnumHandSide enumhandside = this.getMainHand(entity);
            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
            float f1 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

            if (enumhandside == EnumHandSide.LEFT) {
                this.bipedBody.rotateAngleY *= -1.0F;
            }

            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }


        this.bipedBody.rotateAngleX = 0.0F;
        this.bipedHead.rotationPointY = 0.0F;

        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
            this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
            this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
        } else if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
            this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
            this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
            this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
        }

        copyModelAngles(this.bipedHead, this.bipedHeadwear);

        setLivingAnimations((EntityLivingBase) entity, limbSwing, limbSwingAmount, Minecraft.getMinecraft().getRenderPartialTicks());
    }

    @Override
    public void postRenderArm(float scale, EnumHandSide side) {

        GlStateManager.translate(0, -0.38, 0);
        super.postRenderArm(scale, side);
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
