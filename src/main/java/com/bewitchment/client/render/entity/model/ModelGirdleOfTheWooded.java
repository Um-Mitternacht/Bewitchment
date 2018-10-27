package com.bewitchment.client.render.entity.model;

import com.bewitchment.common.item.equipment.baubles.ItemGirdleOfTheWooded;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelGirdleOfTheWooded extends ModelBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/models/girdle_of_the_wooded.png");

	public ModelRenderer rootRight1;
	public ModelRenderer rootLeft1;
	public ModelRenderer belt2;
	public ModelRenderer belt1;
	public ModelRenderer belt3;
	public ModelRenderer belt4;
	public ModelRenderer barkFront3a;
	public ModelRenderer barkFront2a;
	public ModelRenderer barkFront1a;
	public ModelRenderer barkBack3a;
	public ModelRenderer barkBack2a;
	public ModelRenderer barkBack1a;
	public ModelRenderer rootLeft3;
	public ModelRenderer rootRight3;
	public ModelRenderer rootRight2;
	public ModelRenderer rootLeft2;
	public ModelRenderer barkFront3b;
	public ModelRenderer barkFront3c;
	public ModelRenderer barkFront3d;
	public ModelRenderer barkFront2b;
	public ModelRenderer barkFront2c;
	public ModelRenderer barkFront1b;
	public ModelRenderer barkFront1c;
	public ModelRenderer barkBack3b;
	public ModelRenderer barkBack3c;
	public ModelRenderer barkBack3d;
	public ModelRenderer barkBack2b;
	public ModelRenderer barkBack2c;
	public ModelRenderer barkBack1b;
	public ModelRenderer barkBack1c;

	public ModelGirdleOfTheWooded() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.barkFront2c = new ModelRenderer(this, 25, 14);
		this.barkFront2c.setRotationPoint(-2.850585699081421F, 0.9393904209136963F, 0.8476096391677856F);
		this.barkFront2c.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkFront2c, -0.026075345208696685F, 0.43477582849948554F, 0.037526528018900884F);
		this.barkBack2a = new ModelRenderer(this, 1, 23);
		this.barkBack2a.setRotationPoint(-1.4800000190734863F, 7.050000190734863F, 3.1600000858306885F);
		this.barkBack2a.addBox(-0.20000000298023224F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkBack2a, -3.1391492032896458F, 0.2609267198442009F, -3.1213467703507924F);
		this.barkFront1b = new ModelRenderer(this, 8, 26);
		this.barkFront1b.setRotationPoint(-0.4055797755718231F, -0.02021503821015358F, 0.5788751840591431F);
		this.barkFront1b.addBox(-2.5F, -4.0F, -0.5F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkFront1b, 0.035444630965055866F, 0.1744255332284755F, 0.006153645656359201F);
		this.belt3 = new ModelRenderer(this, 1, 1);
		this.belt3.setRotationPoint(-4.199999809265137F, 10.0F, -2.200000047683716F);
		this.belt3.addBox(0.0F, 0.0F, 0.0F, 8, 2, 0, 0.0F);
		this.barkBack1b = new ModelRenderer(this, 8, 26);
		this.barkBack1b.setRotationPoint(-0.4055797755718231F, -0.020214997231960297F, 0.5788751840591431F);
		this.barkBack1b.addBox(-2.5F, -4.0F, -0.5F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkBack1b, 0.035444551902461F, 0.1744255332284755F, 0.0061536955906296405F);
		this.belt2 = new ModelRenderer(this, 10, 2);
		this.belt2.setRotationPoint(4.199999809265137F, 10.0F, -2.200000047683716F);
		this.belt2.addBox(0.0F, 0.0F, 0.0F, 0, 2, 4, 0.0F);
		this.belt4 = new ModelRenderer(this, 1, 1);
		this.belt4.setRotationPoint(-4.199999809265137F, 10.0F, 2.200000047683716F);
		this.belt4.addBox(0.0F, 0.0F, 0.0F, 8, 2, 0, 0.0F);
		this.rootLeft2 = new ModelRenderer(this, 24, 1);
		this.rootLeft2.setRotationPoint(-0.5F, -0.5F, 3.0F);
		this.rootLeft2.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(rootLeft2, 0.2617993877991494F, -0.3490658503988659F, 0.0F);
		this.barkBack3b = new ModelRenderer(this, 16, 21);
		this.barkBack3b.setRotationPoint(-2.2953593730926514F, 0.199678435921669F, 0.8008123636245728F);
		this.barkBack3b.addBox(-2.0F, -2.0F, -1.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(barkBack3b, -0.15734173766502388F, 0.42867029867388357F, -0.08382197165770001F);
		this.barkFront2a = new ModelRenderer(this, 1, 23);
		this.barkFront2a.setRotationPoint(1.4800000190734863F, 7.050000190734863F, -3.1600000858306885F);
		this.barkFront2a.addBox(-0.20000000298023224F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkFront2a, 0.0024434609631950343F, -0.2609267198442009F, -0.020245818740567734F);
		this.barkFront3d = new ModelRenderer(this, 25, 21);
		this.barkFront3d.setRotationPoint(-2.2878177165985107F, -2.080759048461914F, 0.3301289975643158F);
		this.barkFront3d.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkFront3d, -0.42063677337711863F, 0.2867452177299217F, -0.334097154017912F);
		this.barkFront2b = new ModelRenderer(this, 8, 20);
		this.barkFront2b.setRotationPoint(-0.23636749386787415F, -1.0835572481155396F, 0.9001429677009583F);
		this.barkFront2b.addBox(-2.5F, -4.0F, -1.0F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkFront2b, 0.17746230252997924F, 0.26099494670237733F, 0.020280113181386308F);
		this.barkFront3c = new ModelRenderer(this, 1, 18);
		this.barkFront3c.setRotationPoint(-0.33642464876174927F, -3.142953634262085F, 0.07752383500337601F);
		this.barkFront3c.addBox(0.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkFront3c, 0.0035554643965062705F, 0.13856031006470126F, 0.5847116647128922F);
		this.barkFront1c = new ModelRenderer(this, 17, 25);
		this.barkFront1c.setRotationPoint(-2.954423189163208F, -0.01818070188164711F, 0.5206272006034851F);
		this.barkFront1c.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkFront1c, -0.015215062708624117F, 0.3488441755276304F, 0.012701695067186129F);
		this.rootRight1 = new ModelRenderer(this, 21, 26);
		this.rootRight1.setRotationPoint(0.5F, 5.0F, 3.5F);
		this.rootRight1.addBox(0.0F, -2.0F, 0.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(rootRight1, 0.6108652381980153F, 0.2617993877991494F, 0.0F);
		this.rootRight2 = new ModelRenderer(this, 25, 1);
		this.rootRight2.setRotationPoint(0.5F, -0.5F, 3.0F);
		this.rootRight2.addBox(0.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(rootRight2, 0.2617993877991494F, 0.3490658503988659F, 0.0F);
		this.barkBack1a = new ModelRenderer(this, 1, 27);
		this.barkBack1a.setRotationPoint(-1.5F, 10.039999961853027F, 3.0F);
		this.barkBack1a.addBox(0.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkBack1a, -3.087836480670435F, 0.1738347941644255F, -3.126233670951088F);
		this.barkFront3a = new ModelRenderer(this, 1, 18);
		this.barkFront3a.setRotationPoint(1.190000057220459F, 5.050000190734863F, -2.190000057220459F);
		this.barkFront3a.addBox(-0.20000000298023224F, -3.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkFront3a, 0.26424285474405396F, -0.2609267198442009F, -0.020245818740567734F);
		this.rootRight3 = new ModelRenderer(this, 25, 1);
		this.rootRight3.setRotationPoint(0.5F, 8.0F, 3.5F);
		this.rootRight3.addBox(0.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(rootRight3, 0.5235987755982988F, 0.3490658503988659F, 0.0F);
		this.barkBack3a = new ModelRenderer(this, 1, 18);
		this.barkBack3a.setRotationPoint(-1.190000057220459F, 5.050000190734863F, 2.190000057220459F);
		this.barkBack3a.addBox(-0.20000000298023224F, -3.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkBack3a, -2.877349815490496F, 0.2609267198442009F, -3.1213467703507924F);
		this.barkBack2c = new ModelRenderer(this, 25, 14);
		this.barkBack2c.setRotationPoint(-2.859649181365967F, 0.939050555229187F, 0.8518217206001282F);
		this.barkBack2c.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkBack2c, -0.026075313999777656F, 0.43477582849948554F, 0.037526698627658214F);
		this.barkBack1c = new ModelRenderer(this, 17, 25);
		this.barkBack1c.setRotationPoint(-2.954423189163208F, -0.01818085089325905F, 0.5206272006034851F);
		this.barkBack1c.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkBack1c, -0.01521546530367953F, 0.3488441755276304F, 0.012701799096916213F);
		this.barkFront1a = new ModelRenderer(this, 1, 27);
		this.barkFront1a.setRotationPoint(1.5F, 10.039999961853027F, -3.0F);
		this.barkFront1a.addBox(0.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkFront1a, 0.0537561396298448F, -0.1738347941644255F, -0.015358897334326315F);
		this.rootLeft1 = new ModelRenderer(this, 21, 26);
		this.rootLeft1.setRotationPoint(-0.5F, 5.0F, 3.5F);
		this.rootLeft1.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(rootLeft1, 0.6108652381980153F, -0.2617993877991494F, 0.0F);
		this.barkBack2b = new ModelRenderer(this, 8, 20);
		this.barkBack2b.setRotationPoint(-0.2363673448562622F, -1.0835572481155396F, 0.900143027305603F);
		this.barkBack2b.addBox(-2.5F, -4.0F, -1.0F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkBack2b, 0.17746230252997924F, 0.26099494670237733F, 0.02028021929171099F);
		this.barkBack3d = new ModelRenderer(this, 25, 21);
		this.barkBack3d.setRotationPoint(-2.2878174781799316F, -2.080759286880493F, 0.33012905716896057F);
		this.barkBack3d.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkBack3d, -0.1668814470057967F, 0.3618904690757106F, -0.3709979806610772F);
		this.belt1 = new ModelRenderer(this, 10, 2);
		this.belt1.setRotationPoint(-4.199999809265137F, 10.0F, -2.200000047683716F);
		this.belt1.addBox(0.0F, 0.0F, 0.0F, 0, 2, 4, 0.0F);
		this.barkBack3c = new ModelRenderer(this, 1, 13);
		this.barkBack3c.setRotationPoint(-0.3364242613315582F, -3.142953634262085F, 0.07752387970685959F);
		this.barkBack3c.addBox(-3.0F, -2.0F, 0.0F, 5, 3, 1, 0.0F);
		this.setRotateAngle(barkBack3c, -0.15854242384248612F, 0.33488954431075146F, 0.32152516945997495F);
		this.barkFront3b = new ModelRenderer(this, 16, 21);
		this.barkFront3b.setRotationPoint(-2.2953593730926514F, 0.1996787041425705F, 0.800812304019928F);
		this.barkFront3b.addBox(-2.0F, -2.0F, -1.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(barkFront3b, -0.15734130490134676F, 0.4286702320948563F, -0.08382216307240337F);
		this.rootLeft3 = new ModelRenderer(this, 24, 1);
		this.rootLeft3.setRotationPoint(-0.5F, 8.0F, 3.5F);
		this.rootLeft3.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(rootLeft3, 0.5235987755982988F, -0.3490658503988659F, 0.0F);

		this.barkFront2a.addChild(this.barkFront2c);
		this.barkFront2a.addChild(this.barkFront2b);

		this.barkBack3a.addChild(this.barkBack3b);
		this.barkBack3a.addChild(this.barkBack3d);
		this.barkBack3a.addChild(this.barkBack3c);

		this.barkFront3a.addChild(this.barkFront3d);
		this.barkFront3a.addChild(this.barkFront3c);
		this.barkFront3a.addChild(this.barkFront3b);

		this.barkBack1a.addChild(this.barkBack1b);
		this.barkBack1a.addChild(this.barkBack1c);
		this.barkFront1a.addChild(this.barkFront1b);
		this.barkFront1a.addChild(this.barkFront1c);

		this.rootLeft1.addChild(this.rootLeft2);
		this.rootRight1.addChild(this.rootRight2);

		this.barkBack2a.addChild(this.barkBack2c);
		this.barkBack2a.addChild(this.barkBack2b);

	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float f5) {
		if (!(entity instanceof EntityPlayer))
			return;
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		int barkPieces = ItemGirdleOfTheWooded.getBarkPieces((EntityPlayer) entity);

		// FIXME belt is still rendering weirdly
		this.belt1.render(f5);
		this.belt2.render(f5);
		this.belt3.render(f5);
		this.belt4.render(f5);

		if (barkPieces > 0) {
			barkBack1a.render(1);
			barkFront1a.render(1);
		}

		if (barkPieces > 1) {
			barkFront2a.render(1);
			barkBack2a.render(1);
		}

		if (barkPieces > 2) {
			barkFront3a.render(1);
			barkBack3a.render(1);
		}

		if (barkPieces > 3) {
			rootRight1.render(1);
			rootLeft1.render(1);
			rootRight3.render(1);
			rootLeft3.render(1);
		}
	}

	protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}