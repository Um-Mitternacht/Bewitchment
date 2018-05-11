package toad;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * toad - cybercat5555
 * Created using Tabula 5.1.0
 */
public class toad extends ModelBase {
	public ModelRenderer stomach;
	public ModelRenderer neck;
	public ModelRenderer lArm01;
	public ModelRenderer rArm01;
	public ModelRenderer lLeg01;
	public ModelRenderer rLeg01;
	public ModelRenderer head;
	public ModelRenderer jaw;
	public ModelRenderer lEye;
	public ModelRenderer rEye;
	public ModelRenderer lArm02;
	public ModelRenderer lHand;
	public ModelRenderer rArm02;
	public ModelRenderer rHand;
	public ModelRenderer lLeg02;
	public ModelRenderer lLeg03;
	public ModelRenderer rLeg02;
	public ModelRenderer rLeg03;

	public toad() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.rArm01 = new ModelRenderer(this, 16, 25);
		this.rArm01.mirror = true;
		this.rArm01.setRotationPoint(-3.0F, 0.6F, 1.1F);
		this.rArm01.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(rArm01, -0.8196066167365371F, 0.0F, 0.27314402793711257F);
		this.lArm02 = new ModelRenderer(this, 32, 25);
		this.lArm02.setRotationPoint(1.0F, -0.1F, 2.8F);
		this.lArm02.addBox(-0.99F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(lArm02, 0.4553564018453205F, 0.0F, 0.0F);
		this.rLeg03 = new ModelRenderer(this, 49, 25);
		this.rLeg03.mirror = true;
		this.rLeg03.setRotationPoint(0.1F, 0.6F, 4.3F);
		this.rLeg03.addBox(-1.2F, -0.5F, -5.0F, 2, 1, 5, 0.0F);
		this.setRotateAngle(rLeg03, 0.40980330836826856F, 0.31869712141416456F, 0.0F);
		this.rLeg01 = new ModelRenderer(this, 22, 16);
		this.rLeg01.mirror = true;
		this.rLeg01.setRotationPoint(-3.3F, -0.8F, 7.5F);
		this.rLeg01.addBox(-2.0F, -1.0F, -1.5F, 2, 5, 3, 0.0F);
		this.setRotateAngle(rLeg01, -0.5009094953223726F, 0.0F, 0.18203784098300857F);
		this.lLeg01 = new ModelRenderer(this, 22, 16);
		this.lLeg01.setRotationPoint(3.3F, -0.8F, 7.5F);
		this.lLeg01.addBox(0.0F, -1.0F, -1.5F, 2, 5, 3, 0.0F);
		this.setRotateAngle(lLeg01, -0.5009094953223726F, 0.0F, -0.18203784098300857F);
		this.jaw = new ModelRenderer(this, 37, 10);
		this.jaw.setRotationPoint(0.0F, 0.4F, -0.6F);
		this.jaw.addBox(-3.0F, 0.0F, -5.0F, 6, 2, 6, 0.0F);
		this.rLeg02 = new ModelRenderer(this, 39, 19);
		this.rLeg02.mirror = true;
		this.rLeg02.setRotationPoint(-0.9F, 3.6F, -0.2F);
		this.rLeg02.addBox(-1.2F, -1.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(rLeg02, 0.4553564018453205F, -0.136659280431156F, -0.18203784098300857F);
		this.lArm01 = new ModelRenderer(this, 16, 25);
		this.lArm01.setRotationPoint(3.0F, 0.6F, 1.1F);
		this.lArm01.addBox(0.0F, -1.0F, -1.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(lArm01, -0.8196066167365371F, 0.0F, -0.27314402793711257F);
		this.rArm02 = new ModelRenderer(this, 32, 25);
		this.rArm02.mirror = true;
		this.rArm02.setRotationPoint(-1.0F, -0.1F, 2.8F);
		this.rArm02.addBox(-1.01F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(rArm02, 0.4553564018453205F, 0.0F, 0.0F);
		this.lEye = new ModelRenderer(this, 27, 0);
		this.lEye.setRotationPoint(1.3F, -0.5F, -2.3F);
		this.lEye.addBox(0.0F, -2.1F, -1.5F, 2, 2, 3, 0.0F);
		this.setRotateAngle(lEye, 0.0F, 0.18203784098300857F, 0.136659280431156F);
		this.rEye = new ModelRenderer(this, 27, 0);
		this.rEye.mirror = true;
		this.rEye.setRotationPoint(-1.3F, -0.5F, -2.3F);
		this.rEye.addBox(-2.0F, -2.1F, -1.5F, 2, 2, 3, 0.0F);
		this.setRotateAngle(rEye, 0.0F, -0.18203784098300857F, -0.136659280431156F);
		this.rHand = new ModelRenderer(this, 3, 27);
		this.rHand.mirror = true;
		this.rHand.setRotationPoint(-0.1F, 4.5F, -0.3F);
		this.rHand.addBox(-1.5F, 0.0F, -2.7F, 3, 0, 4, 0.0F);
		this.setRotateAngle(rHand, 0.7285004297824331F, -0.091106186954104F, -0.4553564018453205F);
		this.lLeg02 = new ModelRenderer(this, 39, 19);
		this.lLeg02.setRotationPoint(0.9F, 3.6F, -0.2F);
		this.lLeg02.addBox(-1.2F, -1.0F, 0.0F, 2, 2, 5, 0.0F);
		this.setRotateAngle(lLeg02, 0.4553564018453205F, 0.136659280431156F, 0.18203784098300857F);
		this.stomach = new ModelRenderer(this, 0, 0);
		this.stomach.setRotationPoint(0.0F, 17.7F, -1.9F);
		this.stomach.addBox(-4.0F, -2.5F, 0.0F, 8, 6, 10, 0.0F);
		this.setRotateAngle(stomach, -0.36425021489121656F, 0.0F, 0.0F);
		this.lLeg03 = new ModelRenderer(this, 49, 25);
		this.lLeg03.setRotationPoint(-0.1F, 0.6F, 4.3F);
		this.lLeg03.addBox(-1.2F, -0.5F, -5.0F, 2, 1, 5, 0.0F);
		this.setRotateAngle(lLeg03, 0.40980330836826856F, -0.31869712141416456F, 0.0F);
		this.head = new ModelRenderer(this, 37, 0);
		this.head.setRotationPoint(0.0F, -0.2F, -2.2F);
		this.head.addBox(-3.0F, -1.5F, -5.7F, 6, 2, 6, 0.0F);
		this.setRotateAngle(head, 0.8651597102135892F, 0.0F, 0.0F);
		this.neck = new ModelRenderer(this, 0, 17);
		this.neck.setRotationPoint(0.0F, -0.9F, 1.3F);
		this.neck.addBox(-2.5F, -1.5F, -3.0F, 5, 5, 4, 0.0F);
		this.setRotateAngle(neck, -0.4553564018453205F, 0.0F, 0.0F);
		this.lHand = new ModelRenderer(this, 3, 27);
		this.lHand.setRotationPoint(0.1F, 4.5F, -0.3F);
		this.lHand.addBox(-1.5F, 0.0F, -2.7F, 3, 0, 4, 0.0F);
		this.setRotateAngle(lHand, 0.7285004297824331F, 0.091106186954104F, 0.4553564018453205F);
		this.stomach.addChild(this.rArm01);
		this.lArm01.addChild(this.lArm02);
		this.rLeg02.addChild(this.rLeg03);
		this.stomach.addChild(this.rLeg01);
		this.stomach.addChild(this.lLeg01);
		this.head.addChild(this.jaw);
		this.rLeg01.addChild(this.rLeg02);
		this.stomach.addChild(this.lArm01);
		this.rArm01.addChild(this.rArm02);
		this.head.addChild(this.lEye);
		this.head.addChild(this.rEye);
		this.rArm02.addChild(this.rHand);
		this.lLeg01.addChild(this.lLeg02);
		this.lLeg02.addChild(this.lLeg03);
		this.neck.addChild(this.head);
		this.stomach.addChild(this.neck);
		this.lArm02.addChild(this.lHand);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.stomach.render(f5);
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
