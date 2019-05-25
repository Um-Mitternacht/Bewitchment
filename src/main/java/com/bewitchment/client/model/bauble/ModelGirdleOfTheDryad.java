package com.bewitchment.client.model.bauble;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.item.equipment.baubles.ItemGirdleOfTheDryads;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("WeakerAccess")
public class ModelGirdleOfTheDryad extends ModelBase {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Bewitchment.MODID, "textures/models/bauble/girdle_of_the_dryad.png");
	
	public ModelRenderer rootRight1;
	public ModelRenderer rootLeft1;
	public ModelRenderer belt2;
	public ModelRenderer belt1;
	public ModelRenderer belt3;
	public ModelRenderer belt4;
	public ModelRenderer barkfront3a;
	public ModelRenderer barkfront2a;
	public ModelRenderer barkfront1a;
	public ModelRenderer barkBack3a;
	public ModelRenderer barkBack2a;
	public ModelRenderer barkBack1a;
	public ModelRenderer rootLeft3;
	public ModelRenderer rootRight3;
	public ModelRenderer rootRight2;
	public ModelRenderer rootLeft2;
	public ModelRenderer barkfront3b;
	public ModelRenderer barkfront3c;
	public ModelRenderer barkfront3d;
	public ModelRenderer barkfront2b;
	public ModelRenderer barkfront2c;
	public ModelRenderer barkfront1b;
	public ModelRenderer barkfront1c;
	public ModelRenderer barkBack3b;
	public ModelRenderer barkBack3c;
	public ModelRenderer barkBack3d;
	public ModelRenderer barkBack2b;
	public ModelRenderer barkBack2c;
	public ModelRenderer barkBack1b;
	public ModelRenderer barkBack1c;
	
	public ModelGirdleOfTheDryad() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.barkfront2c = new ModelRenderer(this, 25, 14);
		this.barkfront2c.setRotationPoint(-2.850585699081421f, 0.9393904209136963f, 0.8476096391677856f);
		this.barkfront2c.addBox(-2, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkfront2c, -0.026075345208696685f, 0.43477582849948554f, 0.037526528018900884f);
		this.barkBack2a = new ModelRenderer(this, 1, 23);
		this.barkBack2a.setRotationPoint(-1.4800000190734863f, 7.050000190734863f, 3.1600000858306885f);
		this.barkBack2a.addBox(-0.20000000298023224f, -2, 0, 2, 2, 1, 0);
		this.setRotateAngle(barkBack2a, -3.1391492032896458f, 0.2609267198442009f, -3.1213467703507924f);
		this.barkfront1b = new ModelRenderer(this, 8, 26);
		this.barkfront1b.setRotationPoint(-0.4055797755718231f, -0.02021503821015358f, 0.5788751840591431f);
		this.barkfront1b.addBox(-2.5f, -4, -0.5f, 3, 4, 1, 0);
		this.setRotateAngle(barkfront1b, 0.035444630965055866f, 0.1744255332284755f, 0.006153645656359201f);
		this.belt3 = new ModelRenderer(this, 1, 1);
		this.belt3.setRotationPoint(-4.199999809265137f, 10, -2.200000047683716f);
		this.belt3.addBox(0, 0, 0, 8, 2, 0, 0);
		this.barkBack1b = new ModelRenderer(this, 8, 26);
		this.barkBack1b.setRotationPoint(-0.4055797755718231f, -0.020214997231960297f, 0.5788751840591431f);
		this.barkBack1b.addBox(-2.5f, -4, -0.5f, 3, 4, 1, 0);
		this.setRotateAngle(barkBack1b, 0.035444551902461f, 0.1744255332284755f, 0.0061536955906296405f);
		this.belt2 = new ModelRenderer(this, 10, 2);
		this.belt2.setRotationPoint(4.199999809265137f, 10, -2.200000047683716f);
		this.belt2.addBox(0, 0, 0, 0, 2, 4, 0);
		this.belt4 = new ModelRenderer(this, 1, 1);
		this.belt4.setRotationPoint(-4.199999809265137f, 10, 2.200000047683716f);
		this.belt4.addBox(0, 0, 0, 8, 2, 0, 0);
		this.rootLeft2 = new ModelRenderer(this, 24, 1);
		this.rootLeft2.setRotationPoint(-0.5f, -0.5f, 3);
		this.rootLeft2.addBox(-1, -1, 0, 1, 1, 2, 0);
		this.setRotateAngle(rootLeft2, 0.2617993877991494f, -0.3490658503988659f, 0);
		this.barkBack3b = new ModelRenderer(this, 16, 21);
		this.barkBack3b.setRotationPoint(-2.2953593730926514f, 0.199678435921669f, 0.8008123636245728f);
		this.barkBack3b.addBox(-2, -2, -1, 3, 2, 1, 0);
		this.setRotateAngle(barkBack3b, -0.15734173766502388f, 0.42867029867388357f, -0.08382197165770001f);
		this.barkfront2a = new ModelRenderer(this, 1, 23);
		this.barkfront2a.setRotationPoint(1.4800000190734863f, 7.050000190734863f, -3.1600000858306885f);
		this.barkfront2a.addBox(-0.20000000298023224f, -2, 0, 2, 2, 1, 0);
		this.setRotateAngle(barkfront2a, 0.0024434609631950343f, -0.2609267198442009f, -0.020245818740567734f);
		this.barkfront3d = new ModelRenderer(this, 25, 21);
		this.barkfront3d.setRotationPoint(-2.2878177165985107f, -2.080759048461914f, 0.3301289975643158f);
		this.barkfront3d.addBox(-2, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkfront3d, -0.42063677337711863f, 0.2867452177299217f, -0.334097154017912f);
		this.barkfront2b = new ModelRenderer(this, 8, 20);
		this.barkfront2b.setRotationPoint(-0.23636749386787415f, -1.0835572481155396f, 0.9001429677009583f);
		this.barkfront2b.addBox(-2.5f, -4, -1, 3, 4, 1, 0);
		this.setRotateAngle(barkfront2b, 0.17746230252997924f, 0.26099494670237733f, 0.020280113181386308f);
		this.barkfront3c = new ModelRenderer(this, 1, 18);
		this.barkfront3c.setRotationPoint(-0.33642464876174927f, -3.142953634262085f, 0.07752383500337601f);
		this.barkfront3c.addBox(0, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkfront3c, 0.0035554643965062705f, 0.13856031006470126f, 0.5847116647128922f);
		this.barkfront1c = new ModelRenderer(this, 17, 25);
		this.barkfront1c.setRotationPoint(-2.954423189163208f, -0.01818070188164711f, 0.5206272006034851f);
		this.barkfront1c.addBox(-2, -2, 0, 2, 2, 1, 0);
		this.setRotateAngle(barkfront1c, -0.015215062708624117f, 0.3488441755276304f, 0.012701695067186129f);
		this.rootRight1 = new ModelRenderer(this, 21, 26);
		this.rootRight1.setRotationPoint(0.5f, 5, 3.5f);
		this.rootRight1.addBox(0, -2, 0, 2, 2, 3, 0);
		this.setRotateAngle(rootRight1, 0.6108652381980153f, 0.2617993877991494f, 0);
		this.rootRight2 = new ModelRenderer(this, 25, 1);
		this.rootRight2.setRotationPoint(0.5f, -0.5f, 3);
		this.rootRight2.addBox(0, -1, 0, 1, 1, 2, 0);
		this.setRotateAngle(rootRight2, 0.2617993877991494f, 0.3490658503988659f, 0);
		this.barkBack1a = new ModelRenderer(this, 1, 27);
		this.barkBack1a.setRotationPoint(-1.5f, 10.039999961853027f, 3);
		this.barkBack1a.addBox(0, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkBack1a, -3.087836480670435f, 0.1738347941644255f, -3.126233670951088f);
		this.barkfront3a = new ModelRenderer(this, 1, 18);
		this.barkfront3a.setRotationPoint(1.190000057220459f, 5.050000190734863f, -2.190000057220459f);
		this.barkfront3a.addBox(-0.20000000298023224f, -3, -1, 2, 3, 1, 0);
		this.setRotateAngle(barkfront3a, 0.26424285474405396f, -0.2609267198442009f, -0.020245818740567734f);
		this.rootRight3 = new ModelRenderer(this, 25, 1);
		this.rootRight3.setRotationPoint(0.5f, 8, 3.5f);
		this.rootRight3.addBox(0, -1, 0, 1, 1, 2, 0);
		this.setRotateAngle(rootRight3, 0.5235987755982988f, 0.3490658503988659f, 0);
		this.barkBack3a = new ModelRenderer(this, 1, 18);
		this.barkBack3a.setRotationPoint(-1.190000057220459f, 5.050000190734863f, 2.190000057220459f);
		this.barkBack3a.addBox(-0.20000000298023224f, -3, -1, 2, 3, 1, 0);
		this.setRotateAngle(barkBack3a, -2.877349815490496f, 0.2609267198442009f, -3.1213467703507924f);
		this.barkBack2c = new ModelRenderer(this, 25, 14);
		this.barkBack2c.setRotationPoint(-2.859649181365967f, 0.939050555229187f, 0.8518217206001282f);
		this.barkBack2c.addBox(-2, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkBack2c, -0.026075313999777656f, 0.43477582849948554f, 0.037526698627658214f);
		this.barkBack1c = new ModelRenderer(this, 17, 25);
		this.barkBack1c.setRotationPoint(-2.954423189163208f, -0.01818085089325905f, 0.5206272006034851f);
		this.barkBack1c.addBox(-2, -2, 0, 2, 2, 1, 0);
		this.setRotateAngle(barkBack1c, -0.01521546530367953f, 0.3488441755276304f, 0.012701799096916213f);
		this.barkfront1a = new ModelRenderer(this, 1, 27);
		this.barkfront1a.setRotationPoint(1.5f, 10.039999961853027f, -3);
		this.barkfront1a.addBox(0, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkfront1a, 0.0537561396298448f, -0.1738347941644255f, -0.015358897334326315f);
		this.rootLeft1 = new ModelRenderer(this, 21, 26);
		this.rootLeft1.setRotationPoint(-0.5f, 5, 3.5f);
		this.rootLeft1.addBox(-2, -2, 0, 2, 2, 3, 0);
		this.setRotateAngle(rootLeft1, 0.6108652381980153f, -0.2617993877991494f, 0);
		this.barkBack2b = new ModelRenderer(this, 8, 20);
		this.barkBack2b.setRotationPoint(-0.2363673448562622f, -1.0835572481155396f, 0.900143027305603f);
		this.barkBack2b.addBox(-2.5f, -4, -1, 3, 4, 1, 0);
		this.setRotateAngle(barkBack2b, 0.17746230252997924f, 0.26099494670237733f, 0.02028021929171099f);
		this.barkBack3d = new ModelRenderer(this, 25, 21);
		this.barkBack3d.setRotationPoint(-2.2878174781799316f, -2.080759286880493f, 0.33012905716896057f);
		this.barkBack3d.addBox(-2, -3, 0, 2, 3, 1, 0);
		this.setRotateAngle(barkBack3d, -0.1668814470057967f, 0.3618904690757106f, -0.3709979806610772f);
		this.belt1 = new ModelRenderer(this, 10, 2);
		this.belt1.setRotationPoint(-4.199999809265137f, 10, -2.200000047683716f);
		this.belt1.addBox(0, 0, 0, 0, 2, 4, 0);
		this.barkBack3c = new ModelRenderer(this, 1, 13);
		this.barkBack3c.setRotationPoint(-0.3364242613315582f, -3.142953634262085f, 0.07752387970685959f);
		this.barkBack3c.addBox(-3, -2, 0, 5, 3, 1, 0);
		this.setRotateAngle(barkBack3c, -0.15854242384248612f, 0.33488954431075146f, 0.32152516945997495f);
		this.barkfront3b = new ModelRenderer(this, 16, 21);
		this.barkfront3b.setRotationPoint(-2.2953593730926514f, 0.1996787041425705f, 0.800812304019928f);
		this.barkfront3b.addBox(-2, -2, -1, 3, 2, 1, 0);
		this.setRotateAngle(barkfront3b, -0.15734130490134676f, 0.4286702320948563f, -0.08382216307240337f);
		this.rootLeft3 = new ModelRenderer(this, 24, 1);
		this.rootLeft3.setRotationPoint(-0.5f, 8, 3.5f);
		this.rootLeft3.addBox(-1, -1, 0, 1, 1, 2, 0);
		this.setRotateAngle(rootLeft3, 0.5235987755982988f, -0.3490658503988659f, 0);
		this.barkfront2a.addChild(this.barkfront2c);
		this.barkfront2a.addChild(this.barkfront2b);
		this.barkBack3a.addChild(this.barkBack3b);
		this.barkBack3a.addChild(this.barkBack3d);
		this.barkBack3a.addChild(this.barkBack3c);
		this.barkfront3a.addChild(this.barkfront3d);
		this.barkfront3a.addChild(this.barkfront3c);
		this.barkfront3a.addChild(this.barkfront3b);
		this.barkBack1a.addChild(this.barkBack1b);
		this.barkBack1a.addChild(this.barkBack1c);
		this.barkfront1a.addChild(this.barkfront1b);
		this.barkfront1a.addChild(this.barkfront1c);
		this.rootLeft1.addChild(this.rootLeft2);
		this.rootRight1.addChild(this.rootRight2);
		this.barkBack2a.addChild(this.barkBack2c);
		this.barkBack2a.addChild(this.barkBack2b);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float rotationYaw, float rotationPitch, float scale) {
		if (entity instanceof EntityPlayer) {
			EntityLivingBase living = (EntityLivingBase) entity;
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			this.belt1.render(scale);
			this.belt2.render(scale);
			this.belt3.render(scale);
			this.belt4.render(scale);
			int bark = ItemGirdleOfTheDryads.getBark(living);
			if (bark > 0) {
				barkBack1a.render(scale);
				barkfront1a.render(scale);
				if (bark > 1) {
					barkfront2a.render(scale);
					barkBack2a.render(scale);
					if (bark > 2) {
						barkfront3a.render(scale);
						barkBack3a.render(scale);
						if (bark > 3) {
							rootRight1.render(scale);
							rootLeft1.render(scale);
							rootRight3.render(scale);
							rootLeft3.render(scale);
						}
					}
				}
			}
		}
	}
	
	protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}