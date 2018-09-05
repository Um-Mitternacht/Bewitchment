package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

/**
 * werewolf5 - cybercat5555
 * Created using Tabula 5.1.0
 */
public class ModelGhost extends ModelBase {


	public ModelRenderer body;
	public ModelRenderer lArm;
	public ModelRenderer lLeg;
	public ModelRenderer head;
	public ModelRenderer rLeg;
	public ModelRenderer rArm;
	private int transition = 0;
	private int timer = 0;
	private int timer2 = 0;

	public ModelGhost() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.lArm = new ModelRenderer(this, 40, 16);
		this.lArm.mirror = true;
		this.lArm.setRotationPoint(5.0F, 2.0F, -0.0F);
		this.lArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.body = new ModelRenderer(this, 16, 16);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		this.setRotateAngle(body, 0.6108652381980153F, 0.0F, 0.0F);
		this.rLeg = new ModelRenderer(this, 0, 16);
		this.rLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
		this.rLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.rArm = new ModelRenderer(this, 40, 16);
		this.rArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.lLeg = new ModelRenderer(this, 0, 16);
		this.lLeg.mirror = true;
		this.lLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
		this.lLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);

		/**
		 * Childing
		 */


		this.body.addChild(this.lArm);
		this.body.addChild(this.rLeg);
		this.body.addChild(this.head);
		this.body.addChild(this.rArm);
		this.body.addChild(this.lLeg);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float ptick) {

		EntityPlayer p = (EntityPlayer) entity;

		float time = (p.ticksExisted + ptick) * 0.10471975512F;
		this.body.render(1);

		if (entity.isSneaking() && transition == 0) {
			float anglechange = 2.4712389F - body.rotateAngleX;
			body.rotateAngleX = body.rotateAngleX + anglechange * (timer + 1) / 40;
			head.rotateAngleX = -(body.rotateAngleX - 0.43633231299F);

			timer++;
			if (timer >= 40) {
				transition = 1;
			}
			//Sets the sneak position
		} else if (entity.isSneaking() && transition == 1) {
			body.rotateAngleZ = 0F;
			body.rotationPointY = 2F + 1.5F * MathHelper.sin(time);
			body.rotateAngleX = 0.43633231299F + 6F * limbSwingAmount + 0.03490658503F;
			lLeg.rotateAngleZ = -0.03490658503F + 0.03490658503F * MathHelper.sin(time);
			rLeg.rotateAngleZ = -lLeg.rotateAngleZ;
			lArm.rotateAngleZ = lLeg.rotateAngleZ;
			rArm.rotateAngleZ = -lLeg.rotateAngleZ;
			head.rotateAngleX = -(body.rotateAngleX - 0.43633231299F);
			timer = 1;
			//After the sneak position is taken, if shift remains pressed, it does the actual shift animation
		} else {
			body.rotationPointY = -2F + 1.5F * MathHelper.sin(time);
			body.rotateAngleX = 0.43633231299F + 3F * limbSwingAmount + 0.03490658503F;
			lLeg.rotateAngleZ = -0.03490658503F + 0.03490658503F * MathHelper.sin(time);
			rLeg.rotateAngleZ = -lLeg.rotateAngleZ;
			lArm.rotateAngleZ = lLeg.rotateAngleZ;
			rArm.rotateAngleZ = -lLeg.rotateAngleZ;
			head.rotateAngleX = -(body.rotateAngleX - 0.43633231299F);
			transition = 0;
			//Idle animation. Notice that this is the first chunk of code that runs, so the idle animation will be present throughout the rest of the animation...


		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || timer2 != 0) {
			//the or timer2 check makes it so the animation is run fully even if you just press the key once, and keeps the animation going until the timer reaches 40;
			body.rotateAngleZ = 6.28318531F * (timer2 + 1) / 40;
			timer2++;
			if (timer2 == 40) {
				timer2 = 0;
				//resets the timer, thus turning the second condition to false. If the first condition remains fulfilled, the animation should start from the beginning.
				//Also, a sine could in theory be used so long as it remains in phase with the timer.
			}

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
