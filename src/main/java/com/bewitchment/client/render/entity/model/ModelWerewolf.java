package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

/**
 * werewolf5 - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelWerewolf extends ModelBase {
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedHead;
	public ModelRenderer bipedBody;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer rArm02;
	public ModelRenderer rClaw01;
	public ModelRenderer rArmFur;
	public ModelRenderer rClaw02;
	public ModelRenderer rClaw03;
	public ModelRenderer rClaw04;
	public ModelRenderer rLeg02;
	public ModelRenderer rLeg03;
	public ModelRenderer rFoot;
	public ModelRenderer wolfHead;
	public ModelRenderer fur01;
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
	public ModelRenderer chest;
	public ModelRenderer fur05;
	public ModelRenderer fur06;
	public ModelRenderer fur02;
	public ModelRenderer fur03;
	public ModelRenderer fur04;
	public ModelRenderer tail01Floof;
	public ModelRenderer tail02Floof;
	public ModelRenderer tail03Floof;
	public ModelRenderer lArm02;
	public ModelRenderer lClaw01;
	public ModelRenderer lArmFur;
	public ModelRenderer lClaw02;
	public ModelRenderer lClaw03;
	public ModelRenderer lClaw04;
	public ModelRenderer lLeg02;
	public ModelRenderer lLeg03;
	public ModelRenderer lFoot;
	int timer = 0;
	int phase = 0;
	boolean rotation = true;

	public ModelWerewolf() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.fur04 = new ModelRenderer(this, 90, 35);
		this.fur04.setRotationPoint(0.0F, -3.4F, 2.9F);
		this.fur04.addBox(-2.5F, -1.0F, 0.0F, 5, 5, 2, 0.0F);
		this.setRotateAngle(fur04, 0.5235987755982988F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 32, 48);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.setRotationPoint(-2.8F, -4.9F, 0.0F);
		this.bipedRightArm.addBox(-3.5F, -1.0F, -2.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(bipedRightArm, -0.3490658503988659F, 0.17453292519943295F, 0.3490658503988659F);
		this.lClaw04 = new ModelRenderer(this, 36, 0);
		this.lClaw04.setRotationPoint(-0.9F, 0.0F, 0.0F);
		this.lClaw04.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(lClaw04, 0.0F, 0.0F, 0.091106186954104F);
		this.fur05 = new ModelRenderer(this, 90, 43);
		this.fur05.setRotationPoint(0.0F, -5.2F, 1.7F);
		this.fur05.addBox(-2.0F, -1.0F, 0.0F, 4, 5, 2, 0.0F);
		this.setRotateAngle(fur05, 0.4363323129985824F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, -1.25F, 6.0F);
		this.bipedBody.addBox(-4.0F, -7.5F, -3.0F, 8, 11, 6, 0.0F);
		this.setRotateAngle(bipedBody, 0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 13, 48);
		this.bipedLeftLeg.setRotationPoint(1.1F, 2.5F, -0.3F);
		this.bipedLeftLeg.addBox(0.0F, -1.0F, -2.5F, 4, 10, 5, 0.0F);
		this.setRotateAngle(bipedLeftLeg, -0.5235987755982988F, 0.0F, -0.08726646259971647F);
		this.jawUpper01 = new ModelRenderer(this, 20, 36);
		this.jawUpper01.setRotationPoint(1.2F, -4.9F, -1.1F);
		this.jawUpper01.addBox(-1.2F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(jawUpper01, 0.0F, 0.0F, -0.13962634015954636F);
		this.wolfHead = new ModelRenderer(this, 44, 0);
		this.wolfHead.setRotationPoint(0.0F, -4.2F, -0.2F);
		this.wolfHead.addBox(-3.5F, -5.0F, -3.0F, 7, 5, 6, 0.0F);
		this.setRotateAngle(wolfHead, 0.5235987755982988F, 0.0F, 0.0F);
		this.lClaw03 = new ModelRenderer(this, 27, 0);
		this.lClaw03.setRotationPoint(-0.9F, 0.0F, 0.0F);
		this.lClaw03.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(lClaw03, 0.0F, 0.0F, 0.091106186954104F);
		this.upperTeeth01 = new ModelRenderer(this, 56, 37);
		this.upperTeeth01.setRotationPoint(0.0F, -3.2F, -1.0F);
		this.upperTeeth01.addBox(-0.3F, -0.7F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(upperTeeth01, 0.0F, 0.0F, 0.136659280431156F);
		this.fur06 = new ModelRenderer(this, 90, 52);
		this.fur06.setRotationPoint(0.01F, -2.3F, 1.5F);
		this.fur06.addBox(-2.0F, -1.0F, 0.0F, 4, 5, 2, 0.0F);
		this.setRotateAngle(fur06, 0.3490658503988659F, 0.0F, 0.0F);
		this.lArm02 = new ModelRenderer(this, 49, 46);
		this.lArm02.setRotationPoint(1.6F, 9.4F, 0.0F);
		this.lArm02.addBox(-1.5F, -1.0F, -1.5F, 3, 13, 3, 0.0F);
		this.setRotateAngle(lArm02, -0.5235987755982988F, 0.0F, 0.2617993877991494F);
		this.chest = new ModelRenderer(this, 46, 15);
		this.chest.setRotationPoint(0.0F, -7.5F, 0.3F);
		this.chest.addBox(-4.5F, -9.0F, -4.0F, 9, 10, 8, 0.0F);
		this.setRotateAngle(chest, 0.5235987755982988F, 0.0F, 0.0F);
		this.upperTeeth02 = new ModelRenderer(this, 50, 37);
		this.upperTeeth02.setRotationPoint(0.0F, -3.2F, -1.0F);
		this.upperTeeth02.addBox(-2.2F, -0.7F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(upperTeeth02, 0.0F, 0.0F, 0.136659280431156F);
		this.lEarFeral03_1 = new ModelRenderer(this, 77, 0);
		this.lEarFeral03_1.setRotationPoint(0.3F, 0.0F, 0.0F);
		this.lEarFeral03_1.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lEarFeral03_1, 0.091106186954104F, 0.0F, 0.22759093446006054F);
		this.lClaw02 = new ModelRenderer(this, 27, 0);
		this.lClaw02.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.lClaw02.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(lClaw02, 0.0F, 0.0F, 0.091106186954104F);
		this.rClaw01 = new ModelRenderer(this, 27, 0);
		this.rClaw01.mirror = true;
		this.rClaw01.setRotationPoint(-1.1F, 10.6F, -1.2F);
		this.rClaw01.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(rClaw01, 0.8196066167365371F, 0.0F, 0.0F);
		this.fur01 = new ModelRenderer(this, 90, 0);
		this.fur01.setRotationPoint(-0.01F, -2.8F, 1.1F);
		this.fur01.addBox(-3.0F, -1.0F, 0.0F, 6, 7, 2, 0.0F);
		this.setRotateAngle(fur01, 0.4553564018453205F, 0.0F, 0.0F);
		this.rClaw03 = new ModelRenderer(this, 27, 0);
		this.rClaw03.mirror = true;
		this.rClaw03.setRotationPoint(0.9F, 0.0F, 0.0F);
		this.rClaw03.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(rClaw03, 0.0F, 0.0F, -0.091106186954104F);
		this.lowerTeeth01 = new ModelRenderer(this, 68, 41);
		this.lowerTeeth01.setRotationPoint(0.0F, -3.8F, 0.1F);
		this.lowerTeeth01.addBox(-0.6F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
		this.lEarClassic = new ModelRenderer(this, 73, 8);
		this.lEarClassic.setRotationPoint(2.2F, -2.0F, 3.0F);
		this.lEarClassic.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(lEarClassic, -0.18203784098300857F, 0.091106186954104F, 0.0F);
		this.fur02 = new ModelRenderer(this, 90, 11);
		this.fur02.setRotationPoint(0.0F, -8.1F, 2.0F);
		this.fur02.addBox(-4.0F, -1.0F, 0.0F, 8, 10, 2, 0.0F);
		this.setRotateAngle(fur02, 0.9599310885968813F, 0.0F, 0.0F);
		this.jawLower = new ModelRenderer(this, 39, 37);
		this.jawLower.setRotationPoint(0.0F, -4.8F, -2.5F);
		this.jawLower.addBox(-1.5F, -4.0F, -0.5F, 3, 4, 1, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, -7.6F, 0.7F);
		this.bipedHead.addBox(-3.0F, -5.0F, -2.5F, 6, 5, 5, 0.0F);
		this.setRotateAngle(bipedHead, 0.7853981633974483F, 0.0F, 0.0F);
		this.lArmFur = new ModelRenderer(this, 62, 50);
		this.lArmFur.setRotationPoint(0.6F, 1.6F, 0.0F);
		this.lArmFur.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4, 0.0F);
		this.setRotateAngle(lArmFur, 0.0F, 0.091106186954104F, 0.091106186954104F);
		this.rLeg03 = new ModelRenderer(this, 0, 23);
		this.rLeg03.mirror = true;
		this.rLeg03.setRotationPoint(0.0F, 10.0F, 3.0F);
		this.rLeg03.addBox(-1.5F, 0.0F, -3.0F, 3, 7, 3, 0.0F);
		this.setRotateAngle(rLeg03, -1.0471975511965976F, 0.0F, -0.08726646259971647F);
		this.rClaw04 = new ModelRenderer(this, 36, 0);
		this.rClaw04.mirror = true;
		this.rClaw04.setRotationPoint(0.9F, 0.0F, 0.0F);
		this.rClaw04.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(rClaw04, 0.0F, 0.0F, -0.091106186954104F);
		this.fur03 = new ModelRenderer(this, 90, 24);
		this.fur03.setRotationPoint(0.0F, -5.9F, 2.5F);
		this.fur03.addBox(-3.0F, -1.0F, 0.0F, 6, 8, 2, 0.0F);
		this.setRotateAngle(fur03, 0.7853981633974483F, 0.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 13, 48);
		this.bipedRightLeg.mirror = true;
		this.bipedRightLeg.setRotationPoint(-1.1F, 2.5F, -0.3F);
		this.bipedRightLeg.addBox(-4.0F, -1.0F, -2.5F, 4, 10, 5, 0.0F);
		this.setRotateAngle(bipedRightLeg, -0.5235987755982988F, 0.0F, 0.08726646259971647F);
		this.lEarFeral03 = new ModelRenderer(this, 77, 0);
		this.lEarFeral03.setRotationPoint(-0.3F, 0.0F, 0.0F);
		this.lEarFeral03.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lEarFeral03, 0.091106186954104F, 0.0F, -0.22759093446006054F);
		this.rEarFeral01 = new ModelRenderer(this, 70, 0);
		this.rEarFeral01.mirror = true;
		this.rEarFeral01.setRotationPoint(-2.7F, -2.2F, 1.0F);
		this.rEarFeral01.addBox(-1.0F, 0.0F, -0.9F, 1, 3, 2, 0.0F);
		this.setRotateAngle(rEarFeral01, 0.5462880558742251F, 0.0F, 0.5462880558742251F);
		this.tail02Floof = new ModelRenderer(this, 112, 28);
		this.tail02Floof.setRotationPoint(0.0F, 5.0F, 1.5F);
		this.tail02Floof.addBox(-2.0F, 0.0F, -3.5F, 4, 5, 4, 0.0F);
		this.setRotateAngle(tail02Floof, -0.3490658503988659F, 0.0F, 0.0F);
		this.tail03Floof = new ModelRenderer(this, 112, 44);
		this.tail03Floof.setRotationPoint(0.0F, 5.0F, 0.5F);
		this.tail03Floof.addBox(-2.0F, 0.0F, -4.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(tail03Floof, -0.17453292519943295F, 0.0F, 0.0F);
		this.lFoot = new ModelRenderer(this, 0, 14);
		this.lFoot.setRotationPoint(0.0F, 5.8F, -1.5F);
		this.lFoot.addBox(-2.0F, 0.0F, -4.3F, 4, 2, 6, 0.0F);
		this.setRotateAngle(lFoot, 0.296705972839036F, 0.0F, 0.0F);
		this.rClaw02 = new ModelRenderer(this, 27, 0);
		this.rClaw02.mirror = true;
		this.rClaw02.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.rClaw02.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(rClaw02, 0.0F, 0.0F, -0.091106186954104F);
		this.snout = new ModelRenderer(this, 29, 35);
		this.snout.setRotationPoint(0.0F, -4.6F, 0.1F);
		this.snout.addBox(-1.0F, -4.5F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(snout, 0.18203784098300857F, 0.0F, 0.0F);
		this.upperTeeth03 = new ModelRenderer(this, 63, 38);
		this.upperTeeth03.setRotationPoint(0.0F, -3.2F, -1.0F);
		this.upperTeeth03.addBox(-2.27F, -0.7F, -0.8F, 3, 0, 1, 0.0F);
		this.setRotateAngle(upperTeeth03, 0.0F, 0.0F, 0.136659280431156F);
		this.rEarClassic = new ModelRenderer(this, 73, 8);
		this.rEarClassic.mirror = true;
		this.rEarClassic.setRotationPoint(-2.2F, -2.0F, 3.0F);
		this.rEarClassic.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F);
		this.setRotateAngle(rEarClassic, -0.18203784098300857F, -0.091106186954104F, 0.0F);
		this.lLeg02 = new ModelRenderer(this, 0, 34);
		this.lLeg02.setRotationPoint(2.0F, 9.0F, -1.2F);
		this.lLeg02.addBox(-2.01F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(lLeg02, 1.2217304763960306F, 0.0F, 0.0F);
		this.rLeg02 = new ModelRenderer(this, 0, 34);
		this.rLeg02.mirror = true;
		this.rLeg02.setRotationPoint(-2.0F, 9.0F, -1.2F);
		this.rLeg02.addBox(-1.99F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(rLeg02, 1.2217304764F, 0.0F, 0.0F);
		this.jawUpper02 = new ModelRenderer(this, 20, 36);
		this.jawUpper02.mirror = true;
		this.jawUpper02.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.jawUpper02.addBox(-3.2F, -3.67F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(jawUpper02, 0.0F, 0.0F, 0.2792526803190927F);
		this.rCheekFur = new ModelRenderer(this, 38, 4);
		this.rCheekFur.mirror = true;
		this.rCheekFur.setRotationPoint(-3.5F, -2.5F, 1.1F);
		this.rCheekFur.addBox(0.0F, -0.5F, -7.5F, 0, 4, 8, 0.0F);
		this.setRotateAngle(rCheekFur, -0.18203784098300857F, 0.091106186954104F, 0.36425021489121656F);
		this.rEarFeral02 = new ModelRenderer(this, 77, 0);
		this.rEarFeral02.mirror = true;
		this.rEarFeral02.setRotationPoint(0.0F, 2.8F, 0.1F);
		this.rEarFeral02.addBox(-1.0F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rEarFeral02, 0.36425021489121656F, 0.0F, 0.0F);
		this.lowerTeeth02 = new ModelRenderer(this, 63, 41);
		this.lowerTeeth02.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lowerTeeth02.addBox(-1.3F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.lEarFeral02 = new ModelRenderer(this, 77, 0);
		this.lEarFeral02.setRotationPoint(0.0F, 2.8F, 0.1F);
		this.lEarFeral02.addBox(0.0F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lEarFeral02, 0.36425021489121656F, 0.0F, 0.0F);
		this.lEarFeral01 = new ModelRenderer(this, 70, 0);
		this.lEarFeral01.setRotationPoint(2.7F, -2.2F, 1.0F);
		this.lEarFeral01.addBox(0.0F, 0.0F, -0.9F, 1, 3, 2, 0.0F);
		this.setRotateAngle(lEarFeral01, 0.5462880558742251F, 0.0F, -0.5462880558742251F);
		this.lCheekFur = new ModelRenderer(this, 38, 4);
		this.lCheekFur.setRotationPoint(3.5F, -2.5F, 1.1F);
		this.lCheekFur.addBox(0.0F, -0.5F, -7.5F, 0, 4, 8, 0.0F);
		this.setRotateAngle(lCheekFur, -0.18203784098300857F, -0.091106186954104F, -0.36425021489121656F);
		this.tail01Floof = new ModelRenderer(this, 112, 18);
		this.tail01Floof.setRotationPoint(0.0F, 2.5F, 1.0F);
		this.tail01Floof.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
		this.setRotateAngle(tail01Floof, 0.6108652381980153F, 0.0F, 0.0F);
		this.lLeg03 = new ModelRenderer(this, 0, 23);
		this.lLeg03.setRotationPoint(0.0F, 10.0F, 3.0F);
		this.lLeg03.addBox(-1.5F, 0.0F, -3.0F, 3, 7, 3, 0.0F);
		this.setRotateAngle(lLeg03, -1.0471975511965976F, 0.0F, 0.08726646259971647F);
		this.lClaw01 = new ModelRenderer(this, 27, 0);
		this.lClaw01.setRotationPoint(1.1F, 10.6F, -1.2F);
		this.lClaw01.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 2, 0.0F);
		this.setRotateAngle(lClaw01, 0.8196066167365371F, 0.0F, 0.0F);
		this.rArmFur = new ModelRenderer(this, 62, 50);
		this.rArmFur.mirror = true;
		this.rArmFur.setRotationPoint(-1.6F, 1.6F, 0.0F);
		this.rArmFur.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4, 0.0F);
		this.setRotateAngle(rArmFur, 0.0F, -0.091106186954104F, -0.091106186954104F);
		this.bipedLeftArm = new ModelRenderer(this, 32, 48);
		this.bipedLeftArm.setRotationPoint(2.8F, -4.9F, 0.0F);
		this.bipedLeftArm.addBox(-0.5F, -1.0F, -2.0F, 4, 10, 4, 0.0F);
		this.setRotateAngle(bipedLeftArm, -0.3490658503988659F, -0.17453292519943295F, -0.3490658503988659F);
		this.rArm02 = new ModelRenderer(this, 49, 46);
		this.rArm02.mirror = true;
		this.rArm02.setRotationPoint(-1.6F, 9.4F, 0.0F);
		this.rArm02.addBox(-1.5F, -1.0F, -1.5F, 3, 13, 3, 0.0F);
		this.setRotateAngle(rArm02, -0.5235987755982988F, 0.0F, -0.2617993877991494F);
		this.rFoot = new ModelRenderer(this, 0, 14);
		this.rFoot.mirror = true;
		this.rFoot.setRotationPoint(0.0F, 5.8F, -1.5F);
		this.rFoot.addBox(-2.0F, 0.0F, -4.3F, 4, 2, 6, 0.0F);
		this.setRotateAngle(rFoot, 0.296705972839036F, 0.0F, 0.0F);

		/**
		 * Childing
		 */

		this.bipedBody.addChild(this.chest);
		this.bipedBody.addChild(this.tail01Floof);
		this.bipedBody.addChild(this.fur05);
		this.bipedBody.addChild(this.fur06);
		this.bipedBody.addChild(this.bipedLeftLeg);
		this.bipedBody.addChild(this.bipedRightLeg);

		this.chest.addChild(this.bipedLeftArm);
		this.chest.addChild(this.bipedRightArm);
		this.chest.addChild(this.fur02);
		this.chest.addChild(this.fur03);
		this.chest.addChild(this.fur04);
		this.chest.addChild(this.bipedHead);
		this.bipedHead.addChild(this.wolfHead);
		this.bipedHead.addChild(this.fur01);

		this.bipedRightArm.addChild(this.rArm02);
		this.rArm02.addChild(this.rClaw01);
		this.rArm02.addChild(this.rArmFur);
		this.rClaw01.addChild(this.rClaw02);
		this.rClaw02.addChild(this.rClaw03);
		this.rClaw03.addChild(this.rClaw04);

		this.bipedLeftArm.addChild(this.lArm02);
		this.lArm02.addChild(this.lArmFur);
		this.lArm02.addChild(this.lClaw01);
		this.lClaw01.addChild(this.lClaw02);
		this.lClaw02.addChild(this.lClaw03);
		this.lClaw03.addChild(this.lClaw04);

		this.bipedRightLeg.addChild(this.rLeg02);
		this.rLeg02.addChild(this.rLeg03);
		this.lLeg03.addChild(this.lFoot);

		this.bipedLeftLeg.addChild(this.lLeg02);
		this.lLeg02.addChild(this.lLeg03);
		this.rLeg03.addChild(this.rFoot);

		this.tail01Floof.addChild(this.tail02Floof);
		this.tail02Floof.addChild(this.tail03Floof);

		this.wolfHead.addChild(this.jawUpper01);
		this.jawUpper01.addChild(this.jawUpper02);
		this.jawUpper01.addChild(this.upperTeeth01);
		this.jawUpper01.addChild(this.upperTeeth02);
		this.jawUpper01.addChild(this.upperTeeth03);

		this.wolfHead.addChild(this.jawLower);
		this.jawLower.addChild(this.lowerTeeth01);
		this.lowerTeeth01.addChild(this.lowerTeeth02);

		this.wolfHead.addChild(this.lEarClassic);
		this.wolfHead.addChild(this.lEarFeral01);
		this.lEarFeral01.addChild(this.lEarFeral02);
		this.lEarFeral01.addChild(this.lEarFeral03);
		this.rEarFeral01.addChild(this.lEarFeral03_1);

		this.wolfHead.addChild(this.rEarClassic);
		this.wolfHead.addChild(this.rEarFeral01);
		this.rEarFeral01.addChild(this.rEarFeral02);

		this.wolfHead.addChild(this.snout);
		this.wolfHead.addChild(this.rCheekFur);
		this.wolfHead.addChild(this.lCheekFur);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float ptick) {

		EntityPlayer p = (EntityPlayer) entity;
/*
		float rotArm = MathHelper.cos(limbSwing * 0.5f) * limbSwingAmount * 2;
		bipedRightArm.rotateAngleX = rotArm;
		bipedLeftArm.rotateAngleX = -rotArm;

		bipedRightLeg.rotateAngleX = -rotArm;
		bipedLeftLeg.rotateAngleX = rotArm;
	
		bipedHead.rotateAngleY = netHeadYaw * 0.017453292F; 
		bipedHead.rotateAngleX = MathHelper.clamp((float) (Math.PI * headPitch / 180f), -1.20f, 0.4f) + 0.785398163395f; //TODO change limits so it covers all the movement range
/*		
		float sp = p.swingProgress;
		float psp = p.prevSwingProgress;
/*
		if (sp != 0) {
			float interpolatedSwingProgress = (psp + (sp + 0.16f - psp) * ptick); // TODO fix
			if ((p.swingingHand == EnumHand.MAIN_HAND) == (p.getPrimaryHand() == EnumHandSide.RIGHT)) {
				this.bipedRightArm.rotateAngleX += interpolatedSwingProgress;
			} else {
				this.bipedLeftArm.rotateAngleX += interpolatedSwingProgress;
			}
		}

*/
		float time = (p.ticksExisted + ptick) * 0.10471975512F;
		float time_running = (p.ticksExisted + ptick) * 0.314159265F;


		this.bipedBody.render(1);

		tail01Floof.rotateAngleZ = 0.03490658503F * MathHelper.sin((time));
		tail01Floof.rotateAngleX = 0.6108652381980153F + 0.00872664626F * MathHelper.cos((time));
		tail02Floof.rotateAngleZ = 0.03490658503F * MathHelper.cos((time));
		tail03Floof.rotateAngleZ = tail02Floof.rotateAngleZ;

		if (limbSwingAmount > 0.05 || timer > 0) {//Walking animation
			if (limbSwingAmount < 0.3333333 && timer == 0) { // Walking animation
				bipedRightArm.rotateAngleX = -0.3490658503988659F + limbSwingAmount * MathHelper.sin(time * 5);
				bipedLeftArm.rotateAngleX = -0.698131701F - bipedRightArm.rotateAngleX;
				bipedLeftLeg.rotateAngleX = bipedRightArm.rotateAngleX;
				bipedRightLeg.rotateAngleX = bipedLeftArm.rotateAngleX;
				chest.rotateAngleY = bipedRightArm.rotateAngleX / 10 + 0.03490658503988659F;
			} else {
				if (timer < 13) { // Transition to running animation
					if ((MathHelper.sin(time_running) > -0.1 && MathHelper.sin(time_running) < 0.1) || phase == 1) {
						bipedBody.rotateAngleX = bipedBody.rotateAngleX + (1.4835298642F - bipedBody.rotateAngleX) * (timer + 1) / 40;
						chest.rotateAngleX = chest.rotateAngleX - (chest.rotateAngleX - 0.17453292519F) * (timer + 1) / 40;
						bipedHead.rotateAngleX = bipedHead.rotateAngleX + (-bipedHead.rotateAngleX) * (timer + 1) / 40;
						bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX + (-1.74532925199F - bipedLeftArm.rotateAngleX) * (timer + 1) / 40;
						bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX + (-1.74532925199F - bipedRightArm.rotateAngleX) * (timer + 1) / 40;
						bipedRightArm.rotateAngleZ = bipedRightArm.rotateAngleZ - bipedRightArm.rotateAngleZ * (timer + 1) / 40;
						bipedLeftArm.rotateAngleZ = -bipedRightArm.rotateAngleZ;
						bipedLeftLeg.rotateAngleX = bipedLeftLeg.rotateAngleX + (-1.2217304764F - bipedLeftLeg.rotateAngleX) * (timer + 1) / 40;
						bipedRightLeg.rotateAngleX = bipedRightLeg.rotateAngleX + (-1.2217304764F - bipedRightLeg.rotateAngleX) * (timer + 1) / 40;
						lLeg02.rotateAngleX = lLeg02.rotateAngleX + (1.0471975512F - lLeg02.rotateAngleX) * (timer + 1) / 40;
						rLeg02.rotateAngleX = lLeg02.rotateAngleX;
						lFoot.rotateAngleX = lFoot.rotateAngleX + (0.3839724354387525f - lFoot.rotateAngleX) * (timer + 1) / 40;
						rFoot.rotateAngleX = lFoot.rotateAngleX;
						fur02.rotateAngleX = fur02.rotateAngleX + (0.69813170079F - fur02.rotateAngleX) * (timer + 1) / 40;
						fur03.rotateAngleX = fur02.rotateAngleX + (0.52359877559F - fur03.rotateAngleX) * (timer + 1) / 40;
						fur04.rotateAngleX = fur03.rotateAngleX + (0.26179938779F - fur04.rotateAngleX) * (timer + 1) / 40;

						timer++;
						phase = 1;
					} else {
						bipedRightArm.rotateAngleX = -0.3490658503988659F + limbSwingAmount * MathHelper.sin(time * 5);
						bipedLeftArm.rotateAngleX = -0.698131701F - bipedRightArm.rotateAngleX;
						bipedLeftLeg.rotateAngleX = bipedRightArm.rotateAngleX;
						bipedRightLeg.rotateAngleX = bipedLeftArm.rotateAngleX;
						chest.rotateAngleY = bipedRightArm.rotateAngleX / 10 + 0.03490658503988659F;
					}
				} else {
					if (limbSwingAmount > 0.3333333) { //Running animation goes here; Sin phase = 6; timer unit = 0.033 (aprox);

						bipedBody.rotateAngleX = 1.4835298642F + 0.17453292519F * MathHelper.sin(time_running);
						chest.rotateAngleX = 0.34906585038F + 0.08726646259F * MathHelper.sin(time_running);
						bipedHead.rotateAngleX = -0.52359877559F + chest.rotateAngleX - 0.34906585038F;
						bipedLeftArm.rotateAngleX = -1.74532925199F + 0.52359877559F * MathHelper.sin((float) (time_running + 4.5));
						bipedRightArm.rotateAngleX = bipedLeftArm.rotateAngleX;
						rArm02.rotateAngleX = -1.3962634016F + 0.0872664626F * MathHelper.sin(time_running);
						lArm02.rotateAngleX = rArm02.rotateAngleX;
						bipedLeftLeg.rotateAngleX = -1.2217304764F + 0.52359877559F * MathHelper.cos(time_running);
						bipedRightLeg.rotateAngleX = bipedLeftLeg.rotateAngleX;
						lLeg02.rotateAngleX = 1.0471975512F + 0.52359877559F * MathHelper.sin((float) (time_running + 4.5));
						rLeg02.rotateAngleX = lLeg02.rotateAngleX;
						lFoot.rotateAngleX = 0.3839724354387525f + 0.43633231299F * MathHelper.cos(time_running);
						rFoot.rotateAngleX = lFoot.rotateAngleX;
						bipedBody.setRotationPoint(0.0F, (float) (-1.5F + 5 * MathHelper.sin((float) (time_running + 4.5))), 6.0F);
						tail01Floof.rotateAngleX = 0.34906585039F + 0.26179938779F * MathHelper.cos(time_running);
						tail02Floof.rotateAngleX = -0.3490658503988659F + 0.26179938779F * MathHelper.sin(time_running);
						tail03Floof.rotateAngleX = -0.17453292519943295F + 0.08726646259F * MathHelper.sin(time_running);
						fur02.rotateAngleX = 0.69813170079F + 0.0872664625997164F * MathHelper.sin(time_running);
						fur03.rotateAngleX = 0.52359877559F + fur02.rotateAngleX - 0.69813170079F;
						fur04.rotateAngleX = 0.26179938779F + fur02.rotateAngleX - 0.69813170079F;
						//Fix fur rotation...
					} else {
						if (timer < 53) { //Transition to idle
							bipedBody.rotateAngleX = bipedBody.rotateAngleX - bipedBody.rotateAngleX * (timer - 12) / 40;
							bipedBody.setRotationPoint(0.0F, -1.25F, 6.0F);
							chest.rotateAngleX = chest.rotateAngleX + (0.5235987755982988F - chest.rotateAngleX) * (timer - 12) / 40;
							bipedHead.rotateAngleX = bipedHead.rotateAngleX + (0.7853981633974483F - bipedHead.rotateAngleX) * (timer - 12) / 40;
							;
							bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX + (-0.17683882565f - bipedLeftArm.rotateAngleX) * (timer - 12) / 40;
							bipedRightArm.rotateAngleX = bipedLeftArm.rotateAngleX;
							bipedLeftArm.rotateAngleZ = bipedLeftArm.rotateAngleZ + (-0.3490658503988659F - bipedLeftArm.rotateAngleZ) * (timer - 12) / 40;
							bipedRightArm.rotateAngleZ = -bipedLeftArm.rotateAngleZ;
							rArm02.rotateAngleX = rArm02.rotateAngleX + (-0.5235987755982988F - rArm02.rotateAngleX) * (timer - 12) / 40;
							lArm02.rotateAngleX = rArm02.rotateAngleX;
							bipedLeftLeg.rotateAngleX = bipedLeftLeg.rotateAngleX + (-0.5235987755982988F - bipedLeftLeg.rotateAngleX) * (timer - 12) / 40;
							bipedRightLeg.rotateAngleX = bipedLeftLeg.rotateAngleX;
							lLeg02.rotateAngleX = lLeg02.rotateAngleX + (1.2217304763960306F - lLeg02.rotateAngleX) * (timer - 12) / 40;
							rLeg02.rotateAngleX = lLeg02.rotateAngleX;
							lFoot.rotateAngleX = lFoot.rotateAngleX + (0.296705972839036F - lFoot.rotateAngleX) * (timer - 12) / 40;
							rFoot.rotateAngleX = lFoot.rotateAngleX;
							tail01Floof.rotateAngleX = tail01Floof.rotateAngleX - (0.6108652381980153F - tail01Floof.rotateAngleX) * (timer - 12) / 40;
							tail02Floof.rotateAngleX = tail02Floof.rotateAngleX + (-0.3490658503988659F - tail02Floof.rotateAngleX) * (timer - 12) / 40;
							tail03Floof.rotateAngleX = tail03Floof.rotateAngleX + (-0.17453292519943295F - tail03Floof.rotateAngleX) * (timer - 12) / 40;
							fur02.rotateAngleX = fur02.rotateAngleX + (0.9599310885968813F - fur02.rotateAngleX) * (timer - 12) / 40;
							fur03.rotateAngleX = fur02.rotateAngleX + (0.7853981633974483F - fur03.rotateAngleX) * (timer - 12) / 40;
							fur04.rotateAngleX = fur03.rotateAngleX + (0.5235987755982988F - fur04.rotateAngleX) * (timer - 12) / 40;

							timer++;
						} else {//Resets the values to the originals, as to avoid possible aberrations...
							bipedBody.rotateAngleX = 0;
							bipedBody.setRotationPoint(0.0F, -1.25F, 6.0F);
							chest.rotateAngleX = 0.5235987755982988F;
							bipedHead.rotateAngleX = 0.7853981633974483F;
							bipedLeftArm.rotateAngleX = -0.17683882565F;
							bipedRightArm.rotateAngleX = bipedLeftArm.rotateAngleX;
							bipedLeftArm.rotateAngleZ = -0.3490658503988659F;
							bipedRightArm.rotateAngleZ = -bipedLeftArm.rotateAngleZ;
							rArm02.rotateAngleX = -0.5235987755982988F;
							bipedLeftLeg.rotateAngleX = -0.5235987755982988F;
							bipedRightLeg.rotateAngleX = bipedLeftLeg.rotateAngleX;
							lLeg02.rotateAngleX = 1.2217304763960306F;
							rLeg02.rotateAngleX = lLeg02.rotateAngleX;
							lFoot.rotateAngleX = 0.296705972839036F;
							rFoot.rotateAngleX = lFoot.rotateAngleX;
							tail01Floof.rotateAngleX = 0.6108652381980153F;
							tail02Floof.rotateAngleX = -0.3490658503988659F;
							tail03Floof.rotateAngleX = -0.17453292519943295F;
							fur02.rotateAngleX = 0.9599310885968813F;
							fur03.rotateAngleX = 0.7853981633974483F;
							fur04.rotateAngleX = 0.5235987755982988F;

							timer = 0;
							phase = 0;
						}
					}
				}
			}
		} else {

			chest.rotateAngleX = 0.5235987755982988F + 0.0174532925199432F * MathHelper.cos((time));
			bipedRightArm.rotateAngleZ = 0.3490658503988659F - 0.0174532925199432F * MathHelper.cos((time));
			bipedLeftArm.rotateAngleZ = -bipedRightArm.rotateAngleZ;
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
