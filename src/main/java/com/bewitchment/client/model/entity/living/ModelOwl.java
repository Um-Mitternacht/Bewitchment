package com.bewitchment.client.model.entity.living;

import com.bewitchment.common.entity.living.EntityOwl;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

/**
 * owl - Ingoleth, Cybercat5555
 * Created using Tabula 5.1.0
 */
@SuppressWarnings("WeakerAccess")
public class ModelOwl extends ModelBase {
	public final ModelRenderer body;
	public final ModelRenderer middleTail;
	public final ModelRenderer rightTail;
	public final ModelRenderer leftTail;
	public final ModelRenderer head;
	public final ModelRenderer rightfoot;
	public final ModelRenderer leftfoot;
	public final ModelRenderer fluff;
	public final ModelRenderer leftWing00;
	public final ModelRenderer rightWing00;
	public final ModelRenderer beak;
	public final ModelRenderer rightEar;
	public final ModelRenderer leftEar;
	public final ModelRenderer rightTalon00;
	public final ModelRenderer rightTalon01;
	public final ModelRenderer rightTalon02;
	public final ModelRenderer rightTalon03;
	public final ModelRenderer leftTalon00;
	public final ModelRenderer leftTalon01;
	public final ModelRenderer leftTalon02;
	public final ModelRenderer leftTalon03;
	public final ModelRenderer leftWing00b;
	public final ModelRenderer leftWing01;
	public final ModelRenderer leftWing00c;
	public final ModelRenderer leftWing01b;
	public final ModelRenderer leftWing02;
	public final ModelRenderer leftWing01c;
	public final ModelRenderer leftWing02b;
	public final ModelRenderer leftWing02c;
	public final ModelRenderer rightWing00b;
	public final ModelRenderer rightWing01;
	public final ModelRenderer rightWing00c;
	public final ModelRenderer rightWing01b;
	public final ModelRenderer rightWing02;
	public final ModelRenderer rightWing01c;
	public final ModelRenderer rightWing02b;
	public final ModelRenderer rightWing02c;
	
	public ModelOwl() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.rightWing02 = new ModelRenderer(this, 0, 49);
		this.rightWing02.mirror = true;
		this.rightWing02.setRotationPoint(-6, 0, 0);
		this.rightWing02.addBox(-6, 0, -1, 6, 2, 2, 0);
		this.setRotateAngle(rightWing02, 0, 0, -0.35f);
		this.leftWing02b = new ModelRenderer(this, 49, 60);
		this.leftWing02b.setRotationPoint(3, 1.8f, 0);
		this.leftWing02b.addBox(-3, 0, -0.5f, 6, 2, 1, 0);
		this.leftWing00b = new ModelRenderer(this, 49, 54);
		this.leftWing00b.setRotationPoint(3, 1.8f, 0);
		this.leftWing00b.addBox(-3, 0, -0.5f, 6, 2, 1, 0);
		this.leftWing01c = new ModelRenderer(this, 17, 42);
		this.leftWing01c.setRotationPoint(1.1f, 1.3f, 0);
		this.leftWing01c.addBox(-4.1f, 0, 0, 6, 8, 0, 0);
		this.rightWing02b = new ModelRenderer(this, 49, 60);
		this.rightWing02b.mirror = true;
		this.rightWing02b.setRotationPoint(-3, 1.8f, 0);
		this.rightWing02b.addBox(-3, 0, -0.5f, 6, 2, 1, 0);
		this.rightWing01c = new ModelRenderer(this, 17, 42);
		this.rightWing01c.mirror = true;
		this.rightWing01c.setRotationPoint(1.1f, 1.3f, 0);
		this.rightWing01c.addBox(-4.1f, 0, 0, 6, 8, 0, 0);
		this.fluff = new ModelRenderer(this, 0, 0);
		this.fluff.setRotationPoint(0, -6.4f, -3.1f);
		this.fluff.addBox(-3.5f, 0, -1, 7, 5, 2, 0);
		this.setRotateAngle(fluff, -0.37f, 0, 0);
		this.leftWing00 = new ModelRenderer(this, 0, 41);
		this.leftWing00.setRotationPoint(6, -7, 0);
		this.leftWing00.addBox(0, 0, -1, 6, 2, 2, 0);
		this.setRotateAngle(leftWing00, 0.09f, -0.61f, 1.22f);
		this.rightWing01b = new ModelRenderer(this, 49, 57);
		this.rightWing01b.mirror = true;
		this.rightWing01b.setRotationPoint(-3, 1.8f, 0);
		this.rightWing01b.addBox(-3, 0, -0.5f, 6, 2, 1, 0);
		this.leftEar = new ModelRenderer(this, 27, 1);
		this.leftEar.setRotationPoint(4, -3.4f, 0);
		this.leftEar.addBox(-1, -4, -1, 2, 4, 2, 0);
		this.setRotateAngle(leftEar, -0.35f, 0, 0.26f);
		this.body = new ModelRenderer(this, 24, 16);
		this.body.setRotationPoint(0, 16, 0);
		this.body.addBox(-6, -8, -4, 12, 14, 8, 0);
		this.setRotateAngle(body, 0.09f, 0, 0);
		this.rightTail = new ModelRenderer(this, 42, 40);
		this.rightTail.mirror = true;
		this.rightTail.setRotationPoint(-2, 2.5f, 4);
		this.rightTail.addBox(-4, 0, 0, 4, 2, 6, 0);
		this.setRotateAngle(rightTail, -0.6f, -0.47f, -0.4f);
		this.leftWing02 = new ModelRenderer(this, 0, 49);
		this.leftWing02.setRotationPoint(6, 0, 0);
		this.leftWing02.addBox(0, 0, -1, 6, 2, 2, 0);
		this.setRotateAngle(leftWing02, 0, 0, 0.35f);
		this.leftTalon02 = new ModelRenderer(this, 56, 0);
		this.leftTalon02.setRotationPoint(0.6f, 1.2f, 0.5f);
		this.leftTalon02.addBox(-0.5f, -1, 0, 1, 2, 3, 0);
		this.setRotateAngle(leftTalon02, -0.17f, 0.07f, 0);
		this.leftTalon03 = new ModelRenderer(this, 56, 0);
		this.leftTalon03.setRotationPoint(-0.7f, 1.2f, 0.5f);
		this.leftTalon03.addBox(-0.5f, -1, 0, 1, 2, 3, 0);
		this.setRotateAngle(leftTalon03, -0.17f, -0.07f, 0);
		this.rightWing00b = new ModelRenderer(this, 49, 54);
		this.rightWing00b.mirror = true;
		this.rightWing00b.setRotationPoint(-3, 1.8f, 0);
		this.rightWing00b.addBox(-3, 0, -0.5f, 6, 2, 1, 0);
		this.leftWing01 = new ModelRenderer(this, 0, 45);
		this.leftWing01.setRotationPoint(6, 0, 0);
		this.leftWing01.addBox(0, 0, -1, 6, 2, 2, 0);
		this.setRotateAngle(leftWing01, 0, 0, 0.44f);
		this.head = new ModelRenderer(this, 28, 0);
		this.head.setRotationPoint(0, -12, 0);
		this.head.addBox(-5, -4, -4, 10, 8, 8, 0);
		this.setRotateAngle(head, -0.07f, 0, 0);
		this.rightTalon03 = new ModelRenderer(this, 56, 0);
		this.rightTalon03.mirror = true;
		this.rightTalon03.setRotationPoint(0.7f, 1.2f, 0.5f);
		this.rightTalon03.addBox(-0.5f, -1, 0, 1, 2, 3, 0);
		this.setRotateAngle(rightTalon03, -0.17f, 0.07f, 0);
		this.leftWing00c = new ModelRenderer(this, 16, 16);
		this.leftWing00c.setRotationPoint(0.6f, 1.8f, 0);
		this.leftWing00c.addBox(-3.6f, 0, 0, 6, 8, 0, 0);
		this.rightTalon02 = new ModelRenderer(this, 56, 0);
		this.rightTalon02.mirror = true;
		this.rightTalon02.setRotationPoint(-0.7f, 1.2f, 0.5f);
		this.rightTalon02.addBox(-0.5f, -1, 0, 1, 2, 3, 0);
		this.setRotateAngle(rightTalon02, -0.17f, -0.07f, 0);
		this.leftWing02c = new ModelRenderer(this, 0, 53);
		this.leftWing02c.setRotationPoint(0.6f, 0.3f, 0);
		this.leftWing02c.addBox(-3.6f, -2.9f, 0, 12, 11, 0, 0);
		this.leftTalon01 = new ModelRenderer(this, 19, 8);
		this.leftTalon01.setRotationPoint(-0.7f, 1, -3.6f);
		this.leftTalon01.addBox(-0.5f, -1, -3, 1, 2, 3, 0);
		this.setRotateAngle(leftTalon01, -0.17f, 0.1f, 0);
		this.leftfoot = new ModelRenderer(this, 28, 56);
		this.leftfoot.setRotationPoint(3, 6, -1.5f);
		this.leftfoot.addBox(-1.5f, 0, -5, 3, 2, 6, 0);
		this.setRotateAngle(leftfoot, 0.09f, -0.09f, 0);
		this.beak = new ModelRenderer(this, 19, 1);
		this.beak.setRotationPoint(-1, 0, -5);
		this.beak.addBox(0, 0, 0.4f, 2, 4, 1, 0);
		this.setRotateAngle(beak, -0.09f, 0, 0);
		this.leftTail = new ModelRenderer(this, 42, 40);
		this.leftTail.setRotationPoint(2, 2.5f, 4);
		this.leftTail.addBox(0, 0, 0, 4, 2, 6, 0);
		this.setRotateAngle(leftTail, -0.6f, 0.47f, 0.4f);
		this.rightEar = new ModelRenderer(this, 27, 1);
		this.rightEar.mirror = true;
		this.rightEar.setRotationPoint(-4, -3.4f, 0);
		this.rightEar.addBox(-1, -4, -1, 2, 4, 2, 0);
		this.setRotateAngle(rightEar, -0.35f, 0, -0.26f);
		this.rightWing00 = new ModelRenderer(this, 0, 41);
		this.rightWing00.mirror = true;
		this.rightWing00.setRotationPoint(-6, -7, 0);
		this.rightWing00.addBox(-6, 0, -1, 6, 2, 2, 0);
		this.setRotateAngle(rightWing00, 0.09f, 0.61f, -1.22f);
		this.leftTalon00 = new ModelRenderer(this, 19, 8);
		this.leftTalon00.setRotationPoint(0.7f, 1, -3.6f);
		this.leftTalon00.addBox(-0.5f, -1, -3, 1, 2, 3, 0);
		this.setRotateAngle(leftTalon00, -0.17f, -0.1f, 0);
		this.rightWing02c = new ModelRenderer(this, 0, 53);
		this.rightWing02c.mirror = true;
		this.rightWing02c.setRotationPoint(-0.6f, 0.3f, 0);
		this.rightWing02c.addBox(-8.4f, -2.9f, 0, 12, 11, 0, 0);
		this.rightTalon00 = new ModelRenderer(this, 19, 8);
		this.rightTalon00.mirror = true;
		this.rightTalon00.setRotationPoint(-0.7f, 1, -3.6f);
		this.rightTalon00.addBox(-0.5f, -1, -3, 1, 2, 3, 0);
		this.setRotateAngle(rightTalon00, -0.17f, 0.1f, 0);
		this.leftWing01b = new ModelRenderer(this, 49, 57);
		this.leftWing01b.setRotationPoint(3, 1.8f, 0);
		this.leftWing01b.addBox(-3, 0, -0.5f, 6, 2, 1, 0);
		this.rightWing01 = new ModelRenderer(this, 0, 45);
		this.rightWing01.mirror = true;
		this.rightWing01.setRotationPoint(-6, 0, 0);
		this.rightWing01.addBox(-6, 0, -1, 6, 2, 2, 0);
		this.setRotateAngle(rightWing01, 0, 0, -0.44f);
		this.rightfoot = new ModelRenderer(this, 28, 56);
		this.rightfoot.setRotationPoint(-3, 6, -1.5f);
		this.rightfoot.addBox(-1.5f, 0, -5, 3, 2, 6, 0);
		this.setRotateAngle(rightfoot, 0.09f, 0.09f, 0);
		this.middleTail = new ModelRenderer(this, 25, 45);
		this.middleTail.setRotationPoint(-2, 2.5f, 4);
		this.middleTail.addBox(0, 0, 0, 4, 2, 8, 0);
		this.setRotateAngle(middleTail, -0.7f, 0, 0);
		this.rightTalon01 = new ModelRenderer(this, 19, 8);
		this.rightTalon01.mirror = true;
		this.rightTalon01.setRotationPoint(0.7f, 1, -3.6f);
		this.rightTalon01.addBox(-0.5f, -1, -3, 1, 2, 3, 0);
		this.setRotateAngle(rightTalon01, -0.17f, -0.1f, 0);
		this.rightWing00c = new ModelRenderer(this, 16, 16);
		this.rightWing00c.mirror = true;
		this.rightWing00c.setRotationPoint(0.6f, 1.8f, 0);
		this.rightWing00c.addBox(-3.6f, 0, 0, 6, 8, 0, 0);
		this.rightWing01.addChild(this.rightWing02);
		this.leftWing02.addChild(this.leftWing02b);
		this.leftWing00.addChild(this.leftWing00b);
		this.leftWing01b.addChild(this.leftWing01c);
		this.rightWing02.addChild(this.rightWing02b);
		this.rightWing01b.addChild(this.rightWing01c);
		this.body.addChild(this.fluff);
		this.body.addChild(this.leftWing00);
		this.rightWing01.addChild(this.rightWing01b);
		this.head.addChild(this.leftEar);
		this.body.addChild(this.rightTail);
		this.leftWing01.addChild(this.leftWing02);
		this.leftfoot.addChild(this.leftTalon02);
		this.leftfoot.addChild(this.leftTalon03);
		this.rightWing00.addChild(this.rightWing00b);
		this.leftWing00.addChild(this.leftWing01);
		this.body.addChild(this.head);
		this.rightfoot.addChild(this.rightTalon03);
		this.leftWing00b.addChild(this.leftWing00c);
		this.rightfoot.addChild(this.rightTalon02);
		this.leftWing02b.addChild(this.leftWing02c);
		this.leftfoot.addChild(this.leftTalon01);
		this.body.addChild(this.leftfoot);
		this.head.addChild(this.beak);
		this.body.addChild(this.leftTail);
		this.head.addChild(this.rightEar);
		this.body.addChild(this.rightWing00);
		this.leftfoot.addChild(this.leftTalon00);
		this.rightWing02b.addChild(this.rightWing02c);
		this.rightfoot.addChild(this.rightTalon00);
		this.leftWing01.addChild(this.leftWing01b);
		this.rightWing00.addChild(this.rightWing01);
		this.body.addChild(this.rightfoot);
		this.body.addChild(this.middleTail);
		this.rightfoot.addChild(this.rightTalon01);
		this.rightWing00b.addChild(this.rightWing00c);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		this.body.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		boolean show;
		if (entity instanceof EntityOwl) {
			EntityOwl bird = (EntityOwl) entity;
			if (bird.world != null && bird.world.isBlockLoaded(bird.getPosition().down()) && bird.world.getBlockState(bird.getPosition().down()).getBlockFaceShape(bird.world, bird.getPosition().down(), EnumFacing.UP) == BlockFaceShape.UNDEFINED && !bird.collidedVertically) {
				this.rightWing00.rotateAngleY = 0F;
				this.leftWing00.rotateAngleY = 0F;
				this.rightWing01.rotateAngleY = 0F;
				this.leftWing01.rotateAngleY = 0F;
				this.rightWing00.rotateAngleX = 0F;
				this.leftWing00.rotateAngleX = 0F;
				this.rightWing01.rotateAngleX = 0F;
				this.leftWing00.rotateAngleX = 0F;
				//this.rightLeg00.rotateAngleX = 0.31f;
				//this.leftLeg00.rotateAngleX = 0.31f;
				this.rightWing00.rotateAngleY = MathHelper.cos(ageInTicks / 2F) * (float) Math.PI / 6F;
				if ((Math.abs(bird.motionY) > 0 && (Math.abs(bird.motionX) > 0.05 || Math.abs(bird.motionZ) > 0.05)) || Math.abs(bird.motionY) > 0.25) {
					float rotX = -((float) Math.atan(bird.motionY / Math.sqrt(Math.pow(bird.motionX, 2) + Math.pow(bird.motionZ, 2))) / 1.5F);
					if (rotX < 0) {
						rotX /= 3;
					}
					this.body.rotateAngleX = rotX + (float) Math.PI / 2F - 0.1F;
				}
				else {
					this.body.rotateAngleX = (float) Math.PI / 2F - 0.1F;
				}
				this.leftWing00.rotateAngleY = -this.rightWing00.rotateAngleY;
				this.rightWing01.rotateAngleY = this.rightWing00.rotateAngleY * 0.5F;
				this.leftWing01.rotateAngleY = -this.rightWing00.rotateAngleY * 0.5F;
				this.rightWing00.rotateAngleZ = 0F;
				this.leftWing00.rotateAngleZ = 0F;
				this.head.rotateAngleX = -(float) Math.toRadians(55F);
				if (bird.isSitting()) {
					this.head.rotateAngleX = -(float) Math.toRadians(25F);
				}
				show = false;
			}
			else {
				this.setRotateAngle(head, -0.07f, 0, 0);
				this.setRotateAngle(rightWing00, 0.09f, 0.61f, -1.22f);
				this.setRotateAngle(leftWing00, 0.09f, -0.61f, 1.22f);
				this.setRotateAngle(rightWing01, 0, 0, -0.44f);
				this.setRotateAngle(leftWing01, 0, 0, 0.44f);
				this.body.rotateAngleX = 0F;
				if (bird.isSitting()) this.head.rotateAngleX = (float) Math.toRadians(10F);
				show = true;
			}
			this.leftfoot.showModel = show;
			this.rightfoot.showModel = show;
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