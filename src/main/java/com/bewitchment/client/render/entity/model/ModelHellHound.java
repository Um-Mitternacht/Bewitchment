package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.entity.spirits.demons.EntityHellhound;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;

/**
 * hellhound - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelHellHound extends AdvancedModelBase {
	public AdvancedModelRenderer lArm01;
	public AdvancedModelRenderer body;
	public AdvancedModelRenderer lHindLeg01;
	public AdvancedModelRenderer rHindLeg01;
	public AdvancedModelRenderer rArm01;
	public AdvancedModelRenderer chest;
	public AdvancedModelRenderer neck;
	public AdvancedModelRenderer tail01;
	public AdvancedModelRenderer mane02;
	public AdvancedModelRenderer mane01;
	public AdvancedModelRenderer head;
	public AdvancedModelRenderer lEar;
	public AdvancedModelRenderer rEar;
	public AdvancedModelRenderer muzzle;
	public AdvancedModelRenderer lowerJaw;
	public AdvancedModelRenderer lHorn01;
	public AdvancedModelRenderer rHorn01;
	public AdvancedModelRenderer lHorn02;
	public AdvancedModelRenderer lHorn03a;
	public AdvancedModelRenderer lHorn03b;
	public AdvancedModelRenderer lHorn03c;
	public AdvancedModelRenderer lHorn03d;
	public AdvancedModelRenderer lHorn04a;
	public AdvancedModelRenderer lHorn04b;
	public AdvancedModelRenderer lHorn04c;
	public AdvancedModelRenderer lHorn04d;
	public AdvancedModelRenderer lHorn05a;
	public AdvancedModelRenderer lHorn05b;
	public AdvancedModelRenderer lHorn06;
	public AdvancedModelRenderer rHorn02;
	public AdvancedModelRenderer rHorn03a;
	public AdvancedModelRenderer rHorn03b;
	public AdvancedModelRenderer rHorn03c;
	public AdvancedModelRenderer rHorn03d;
	public AdvancedModelRenderer rHorn04a;
	public AdvancedModelRenderer rHorn04b;
	public AdvancedModelRenderer rHorn04c;
	public AdvancedModelRenderer rHorn04d;
	public AdvancedModelRenderer rHorn05a;
	public AdvancedModelRenderer rHorn05b;
	public AdvancedModelRenderer rHorn06;
	public AdvancedModelRenderer tail02;
	public AdvancedModelRenderer tail03;
	public AdvancedModelRenderer tail04;
	public AdvancedModelRenderer tail05;

	private ModelAnimator animator;

	public ModelHellHound() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.lHorn03a = new AdvancedModelRenderer(this, 52, 7);
		this.lHorn03a.setRotationPoint(0.0F, -1.5F, -0.1F);
		this.lHorn03a.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(lHorn03a, -0.6981317007977318F, 0.20943951023931953F, 0.0F);
		this.lHorn06 = new AdvancedModelRenderer(this, 58, 13);
		this.lHorn06.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.lHorn06.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn06, -0.6283185307179586F, 0.0F, 0.0F);
		this.tail04 = new AdvancedModelRenderer(this, 37, 33);
		this.tail04.setRotationPoint(0.0F, 4.3F, 0.0F);
		this.tail04.addBox(-1.0F, -0.1F, -0.5F, 2, 2, 1, 0.0F);
		this.setRotateAngle(tail04, 0.20943951023931953F, 0.0F, 0.0F);
		this.rHorn03d = new AdvancedModelRenderer(this, 52, 7);
		this.rHorn03d.mirror = true;
		this.rHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03d.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.rHorn03c = new AdvancedModelRenderer(this, 52, 7);
		this.rHorn03c.mirror = true;
		this.rHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03c.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.muzzle = new AdvancedModelRenderer(this, 0, 10);
		this.muzzle.setRotationPoint(0.0F, 0.7F, -3.9F);
		this.muzzle.addBox(-1.5F, -1.0F, -3.0F, 3, 2, 3, 0.0F);
		this.rHindLeg01 = new AdvancedModelRenderer(this, 0, 18);
		this.rHindLeg01.mirror = true;
		this.rHindLeg01.setRotationPoint(-1.5F, 16.0F, 6.0F);
		this.rHindLeg01.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.lHorn03c = new AdvancedModelRenderer(this, 52, 7);
		this.lHorn03c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03c.addBox(-0.2F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.lHorn03d = new AdvancedModelRenderer(this, 52, 7);
		this.lHorn03d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03d.addBox(-0.8F, -2.0F, -0.2F, 1, 2, 1, 0.0F);
		this.tail03 = new AdvancedModelRenderer(this, 32, 33);
		this.tail03.setRotationPoint(0.0F, 4.4F, 0.0F);
		this.tail03.addBox(-0.5F, -0.1F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(tail03, 0.20943951023931953F, 0.0F, 0.0F);
		this.lHindLeg01 = new AdvancedModelRenderer(this, 0, 18);
		this.lHindLeg01.setRotationPoint(1.5F, 16.0F, 6.0F);
		this.lHindLeg01.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.rHorn03b = new AdvancedModelRenderer(this, 52, 7);
		this.rHorn03b.mirror = true;
		this.rHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn03b.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.tail02 = new AdvancedModelRenderer(this, 26, 33);
		this.tail02.setRotationPoint(0.0F, 3.3F, 0.0F);
		this.tail02.addBox(-0.5F, -0.1F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(tail02, 0.27314402793711257F, 0.0F, 0.0F);
		this.mane01 = new AdvancedModelRenderer(this, 0, 48);
		this.mane01.setRotationPoint(0.0F, -1.8F, -3.0F);
		this.mane01.addBox(-3.0F, -1.0F, 0.0F, 6, 2, 7, 0.0F);
		this.setRotateAngle(mane01, 0.4363323129985824F, 0.0F, 0.0F);
		this.lHorn05a = new AdvancedModelRenderer(this, 52, 12);
		this.lHorn05a.setRotationPoint(0.0F, -2.5F, -0.1F);
		this.lHorn05a.addBox(-0.5F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn05a, -0.6981317007977318F, 0.3490658503988659F, 0.0F);
		this.rEar = new AdvancedModelRenderer(this, 16, 14);
		this.rEar.mirror = true;
		this.rEar.setRotationPoint(-2.0F, -3.0F, -2.0F);
		this.rEar.addBox(-1.0F, -2.0F, -0.5F, 2, 2, 1, 0.0F);
		this.lHorn04b = new AdvancedModelRenderer(this, 57, 6);
		this.lHorn04b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn04b.addBox(-0.7F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
		this.mane02 = new AdvancedModelRenderer(this, 28, 48);
		this.mane02.setRotationPoint(0.0F, -1.0F, 2.7F);
		this.mane02.addBox(-3.5F, -1.0F, 0.0F, 7, 2, 7, 0.0F);
		this.setRotateAngle(mane02, -1.2915436464758039F, 0.0F, 0.0F);
		this.rHorn05a = new AdvancedModelRenderer(this, 52, 12);
		this.rHorn05a.mirror = true;
		this.rHorn05a.setRotationPoint(0.0F, -2.5F, -0.1F);
		this.rHorn05a.addBox(-0.5F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn05a, -0.6981317007977318F, -0.3490658503988659F, 0.0F);
		this.rHorn05b = new AdvancedModelRenderer(this, 52, 12);
		this.rHorn05b.mirror = true;
		this.rHorn05b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn05b.addBox(-0.5F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
		this.rHorn02 = new AdvancedModelRenderer(this, 55, 0);
		this.rHorn02.mirror = true;
		this.rHorn02.setRotationPoint(0.0F, -2.5F, -0.1F);
		this.rHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(rHorn02, -0.5235987755982988F, -0.17453292519943295F, 0.0F);
		this.tail05 = new AdvancedModelRenderer(this, 44, 33);
		this.tail05.setRotationPoint(0.0F, 1.3F, 0.1F);
		this.tail05.addBox(-0.5F, -0.5F, -0.59F, 2, 2, 1, 0.0F);
		this.setRotateAngle(tail05, 0.0F, 0.0F, 0.7853981633974483F);
		this.rHorn06 = new AdvancedModelRenderer(this, 58, 13);
		this.rHorn06.mirror = true;
		this.rHorn06.setRotationPoint(0.0F, -2.7F, 0.0F);
		this.rHorn06.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn06, -0.6283185307179586F, 0.0F, 0.0F);
		this.rArm01 = new AdvancedModelRenderer(this, 0, 18);
		this.rArm01.mirror = true;
		this.rArm01.setRotationPoint(-1.5F, 16.0F, -4.0F);
		this.rArm01.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.lowerJaw = new AdvancedModelRenderer(this, 0, 43);
		this.lowerJaw.setRotationPoint(0.0F, 2.0F, -3.8F);
		this.lowerJaw.addBox(-1.5F, -0.4F, -3.0F, 3, 1, 3, 0.0F);
		this.lHorn05b = new AdvancedModelRenderer(this, 52, 12);
		this.lHorn05b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn05b.addBox(-0.5F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
		this.rHorn03a = new AdvancedModelRenderer(this, 52, 7);
		this.rHorn03a.mirror = true;
		this.rHorn03a.setRotationPoint(0.0F, -1.5F, -0.1F);
		this.rHorn03a.addBox(-0.2F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.setRotateAngle(rHorn03a, -0.6981317007977318F, -0.20943951023931953F, 0.0F);
		this.rHorn04a = new AdvancedModelRenderer(this, 57, 6);
		this.rHorn04a.mirror = true;
		this.rHorn04a.setRotationPoint(0.0F, -1.5F, -0.1F);
		this.rHorn04a.addBox(-0.3F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(rHorn04a, -0.6981317007977318F, 0.0F, 0.0F);
		this.body = new AdvancedModelRenderer(this, 18, 14);
		this.body.setRotationPoint(0.0F, 14.0F, 1.0F);
		this.body.addBox(-3.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F);
		this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
		this.rHorn04c = new AdvancedModelRenderer(this, 57, 6);
		this.rHorn04c.mirror = true;
		this.rHorn04c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn04c.addBox(-0.3F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
		this.rHorn04b = new AdvancedModelRenderer(this, 57, 6);
		this.rHorn04b.mirror = true;
		this.rHorn04b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn04b.addBox(-0.7F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
		this.chest = new AdvancedModelRenderer(this, 21, 0);
		this.chest.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.chest.addBox(-4.0F, -3.5F, -3.01F, 8, 7, 7, 0.0F);
		this.tail01 = new AdvancedModelRenderer(this, 20, 33);
		this.tail01.setRotationPoint(0.0F, 6.8F, 2.2F);
		this.tail01.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tail01, -1.0471975511965976F, 0.0F, 0.0F);
		this.lEar = new AdvancedModelRenderer(this, 16, 14);
		this.lEar.setRotationPoint(2.0F, -3.0F, -2.0F);
		this.lEar.addBox(-1.0F, -2.0F, -0.5F, 2, 2, 1, 0.0F);
		this.lHorn04c = new AdvancedModelRenderer(this, 57, 6);
		this.lHorn04c.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn04c.addBox(-0.3F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
		this.lArm01 = new AdvancedModelRenderer(this, 0, 18);
		this.lArm01.setRotationPoint(1.5F, 16.0F, -4.0F);
		this.lArm01.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.head = new AdvancedModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, -2.9F);
		this.head.addBox(-3.0F, -3.0F, -4.0F, 6, 6, 4, 0.0F);
		this.lHorn04d = new AdvancedModelRenderer(this, 57, 6);
		this.lHorn04d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn04d.addBox(-0.7F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
		this.lHorn03b = new AdvancedModelRenderer(this, 52, 7);
		this.lHorn03b.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lHorn03b.addBox(-0.8F, -2.0F, -0.8F, 1, 2, 1, 0.0F);
		this.rHorn04d = new AdvancedModelRenderer(this, 57, 6);
		this.rHorn04d.mirror = true;
		this.rHorn04d.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rHorn04d.addBox(-0.7F, -3.0F, -0.3F, 1, 3, 1, 0.0F);
		this.lHorn02 = new AdvancedModelRenderer(this, 55, 0);
		this.lHorn02.setRotationPoint(0.0F, -2.5F, -0.1F);
		this.lHorn02.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(lHorn02, -0.5235987755982988F, 0.17453292519943295F, 0.0F);
		this.lHorn04a = new AdvancedModelRenderer(this, 57, 6);
		this.lHorn04a.setRotationPoint(0.0F, -1.5F, -0.1F);
		this.lHorn04a.addBox(-0.3F, -3.0F, -0.7F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lHorn04a, -0.6981317007977318F, 0.0F, 0.0F);
		this.neck = new AdvancedModelRenderer(this, 0, 32);
		this.neck.setRotationPoint(0.0F, -5.4F, 0.5F);
		this.neck.addBox(-2.5F, -2.5F, -4.0F, 5, 5, 4, 0.0F);
		this.setRotateAngle(neck, -1.5707963267948966F, 0.0F, 0.0F);
		this.rHorn01 = new AdvancedModelRenderer(this, 46, 0);
		this.rHorn01.mirror = true;
		this.rHorn01.setRotationPoint(-1.3F, -2.1F, -1.4F);
		this.rHorn01.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(rHorn01, -1.0016444577195458F, -0.4553564018453205F, 0.0F);
		this.lHorn01 = new AdvancedModelRenderer(this, 46, 0);
		this.lHorn01.setRotationPoint(1.3F, -2.1F, -1.4F);
		this.lHorn01.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(lHorn01, -1.0016444577195458F, 0.4553564018453205F, 0.0F);
		this.lHorn02.addChild(this.lHorn03a);
		this.lHorn05a.addChild(this.lHorn06);
		this.tail03.addChild(this.tail04);
		this.rHorn03a.addChild(this.rHorn03d);
		this.rHorn03a.addChild(this.rHorn03c);
		this.head.addChild(this.muzzle);
		this.lHorn03a.addChild(this.lHorn03c);
		this.lHorn03a.addChild(this.lHorn03d);
		this.tail02.addChild(this.tail03);
		this.rHorn03a.addChild(this.rHorn03b);
		this.tail01.addChild(this.tail02);
		this.neck.addChild(this.mane01);
		this.lHorn04a.addChild(this.lHorn05a);
		this.head.addChild(this.rEar);
		this.lHorn04a.addChild(this.lHorn04b);
		this.chest.addChild(this.mane02);
		this.rHorn04a.addChild(this.rHorn05a);
		this.rHorn05a.addChild(this.rHorn05b);
		this.rHorn01.addChild(this.rHorn02);
		this.tail04.addChild(this.tail05);
		this.rHorn05a.addChild(this.rHorn06);
		this.head.addChild(this.lowerJaw);
		this.lHorn05a.addChild(this.lHorn05b);
		this.rHorn02.addChild(this.rHorn03a);
		this.rHorn03a.addChild(this.rHorn04a);
		this.rHorn04a.addChild(this.rHorn04c);
		this.rHorn04a.addChild(this.rHorn04b);
		this.body.addChild(this.chest);
		this.body.addChild(this.tail01);
		this.head.addChild(this.lEar);
		this.lHorn04a.addChild(this.lHorn04c);
		this.neck.addChild(this.head);
		this.lHorn04a.addChild(this.lHorn04d);
		this.lHorn03a.addChild(this.lHorn03b);
		this.rHorn04a.addChild(this.rHorn04d);
		this.lHorn01.addChild(this.lHorn02);
		this.lHorn03a.addChild(this.lHorn04a);
		this.body.addChild(this.neck);
		this.head.addChild(this.rHorn01);
		this.head.addChild(this.lHorn01);
		this.updateDefaultPose();
		animator = ModelAnimator.create();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
		this.rHindLeg01.render(f5);
		this.lHindLeg01.render(f5);
		this.rArm01.render(f5);
		this.body.render(f5);
		this.lArm01.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(AdvancedModelRenderer AdvancedModelRenderer, float x, float y, float z) {
		AdvancedModelRenderer.rotateAngleX = x;
		AdvancedModelRenderer.rotateAngleY = y;
		AdvancedModelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.resetToDefaultPose();

		//f = entity.ticksExisted;
		//f1 = 0.5f;

		float globalSpeed = 1;
		float globalHeight = 1;
		float globalDegree = 1;
		//float frame = hellhound.frame + LLibrary.PROXY.getPartialTicks();

		bob(body, 0.5f * globalSpeed, 0.9f * globalHeight, false, f, f1);
		walk(rArm01, 0.6f, 0.5f, false, 0, 0.2f, f, f1);
		walk(lArm01, 0.6f, 0.5f, true, 0, 0.2f, f, f1);
		walk(rHindLeg01, 0.6f, 0.5f, true, 0, 0.2f, f, f1);
		walk(lHindLeg01, 0.6f, 0.5f, false, 0, 0.2f, f, f1);
	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.resetToDefaultPose();
		this.setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
		animator.update(entity);
		animator.setAnimation(EntityHellhound.ANIMATION_BITE);
		animator.startKeyframe(20);
		animator.rotate(muzzle, -0.30f, 0.0f, 0.0f);
		animator.rotate(lowerJaw, 0.30f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(10);


		//float frame = hellhound.frame + LLibrary.PROXY.getPartialTicks();
	}
}
