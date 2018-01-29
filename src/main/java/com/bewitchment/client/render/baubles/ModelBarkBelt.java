
package com.bewitchment.client.render.baubles;

import com.bewitchment.common.item.baubles.ItemBarkBelt;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelBarkBelt extends ModelBase {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/models/bark_belt.png");
	
    public ModelRenderer armfakeleft;
    public ModelRenderer armfakeright;
    public ModelRenderer Rootright1;
	public ModelRenderer Rootleft1;
	public ModelRenderer Belt2;
	public ModelRenderer Belt;
	public ModelRenderer Belt3;
	public ModelRenderer Belt4;
	public ModelRenderer barkfront3a;
	public ModelRenderer barkfront2a;
	public ModelRenderer Barkfront1a;
	public ModelRenderer barkback3a;
	public ModelRenderer barkback2a;
	public ModelRenderer barkback1a;
	public ModelRenderer Pauldronlefttop1;
	public ModelRenderer pauldronlefttop2;
	public ModelRenderer pauldronleftfront;
	public ModelRenderer pauldronleftback;
	public ModelRenderer pauldronrighttop1;
	public ModelRenderer Pauldronrighttop2;
	public ModelRenderer pauldronrightback;
	public ModelRenderer pauldronrightfront;
    public ModelRenderer Rootright2;
    public ModelRenderer Rootleft2;
    public ModelRenderer barkfront3b;
    public ModelRenderer barkfront3c;
    public ModelRenderer barkfront3d;
	public ModelRenderer barkfront2b;
	public ModelRenderer barkfront2c;
	public ModelRenderer barkfront1b;
	public ModelRenderer barkfront1c;
    public ModelRenderer barkback3b;
    public ModelRenderer barkback3c;
    public ModelRenderer barkback3d;
	public ModelRenderer barkback2b;
	public ModelRenderer barkback2c;
	public ModelRenderer barkback1b;
	public ModelRenderer barkback1c;

	public ModelBarkBelt() {
		this.textureWidth = 32;
        this.textureHeight = 32;
		this.barkback1a = new ModelRenderer(this, 1, 27);
		this.barkback1a.setRotationPoint(-1.5F, 10.04F, 3.0F);
		this.barkback1a.addBox(0.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkback1a, -3.0878365126283676F, 0.17383479349863523F, -3.126233756172243F);
		this.pauldronleftback = new ModelRenderer(this, 1, 8);
		this.pauldronleftback.setRotationPoint(-1.5F, 1.0F, -2.990000009536743F);
		this.pauldronleftback.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
		this.Belt = new ModelRenderer(this, 10, 2);
		this.Belt.setRotationPoint(-4.01F, 10.0F, -2.0F);
		this.Belt.addBox(0.0F, 0.0F, 0.0F, 0, 2, 4, 0.0F);
		this.barkfront2b = new ModelRenderer(this, 8, 20);
		this.barkfront2b.setRotationPoint(-0.23636749386787415F, -1.0835572481155396F, 0.9001429677009583F);
		this.barkfront2b.addBox(-2.5F, -4.0F, -1.0F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkfront2b, 0.17746230252997924F, 0.26099494670237733F, 0.020280113181386308F);
		this.barkfront2c = new ModelRenderer(this, 25, 14);
		this.barkfront2c.setRotationPoint(-2.850585699081421F, 0.9393904209136963F, 0.8476096391677856F);
		this.barkfront2c.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkfront2c, -0.026075345208696685F, 0.43477582849948554F, 0.037526528018900884F);
		this.barkback3c = new ModelRenderer(this, 1, 13);
		this.barkback3c.setRotationPoint(-0.3364242613315582F, -3.142953634262085F, 0.07752387970685959F);
		this.barkback3c.addBox(-3.0F, -2.0F, 0.0F, 5, 3, 1, 0.0F);
		this.setRotateAngle(barkback3c, -0.15854242384248612F, 0.33488954431075146F, 0.32152516945997495F);
		this.Pauldronlefttop1 = new ModelRenderer(this, 11, 4);
		this.Pauldronlefttop1.setRotationPoint(-0.5F, -3.0F, 0.0F);
		this.Pauldronlefttop1.addBox(-1.5F, 0.0F, -3.5F, 3, 1, 7, 0.0F);
		this.setRotateAngle(Pauldronlefttop1, -3.1066858022337973F, 0.0F, 2.96705972839036F);
		this.pauldronrightback = new ModelRenderer(this, 1, 8);
		this.pauldronrightback.setRotationPoint(-1.5F, 1.0F, 1.9900000095367432F);
		this.pauldronrightback.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
		this.Rootright2 = new ModelRenderer(this, 25, 1);
		this.Rootright2.setRotationPoint(0.5F, -0.5F, 3.0F);
		this.Rootright2.addBox(0.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(Rootright2, 0.2617993877991494F, 0.3490658503988659F, 0.0F);
		this.Pauldronrighttop2 = new ModelRenderer(this, 13, 13);
		this.Pauldronrighttop2.setRotationPoint(1.5F, 0.0F, 0.0F);
		this.Pauldronrighttop2.addBox(0.0F, 0.0F, -3.0F, 3, 1, 6, 0.0F);
		this.setRotateAngle(Pauldronrighttop2, 0.0F, 0.0F, 0.43633234628809603F);
		this.pauldronlefttop2 = new ModelRenderer(this, 13, 13);
		this.pauldronlefttop2.setRotationPoint(1.5F, 0.0F, 0.0F);
		this.pauldronlefttop2.addBox(0.0F, 0.0F, -3.0F, 3, 1, 6, 0.0F);
		this.setRotateAngle(pauldronlefttop2, 0.0F, 0.0F, 0.43633234628809603F);
        this.armfakeright = new ModelRenderer(this, 62, 47);
		this.armfakeright.mirror = true;
        this.armfakeright.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.armfakeright.addBox(0.0F, -2.0F, -2.0F, 0, 0, 0, 0.0F);
        this.Rootright1 = new ModelRenderer(this, 21, 26);
        this.Rootright1.setRotationPoint(0.5F, 5.0F, 2.5F);
		this.Rootright1.addBox(0.0F, -2.0F, 0.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(Rootright1, 0.6108652381980153F, 0.2617993877991494F, 0.0F);
		this.barkfront3b = new ModelRenderer(this, 16, 21);
		this.barkfront3b.setRotationPoint(-2.2953593730926514F, 0.1996787041425705F, 0.800812304019928F);
		this.barkfront3b.addBox(-2.0F, -2.0F, -1.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(barkfront3b, -0.15734130490134676F, 0.4286702320948563F, -0.08382216307240337F);
		this.barkback1b = new ModelRenderer(this, 8, 26);
		this.barkback1b.setRotationPoint(-0.4055797755718231F, -0.020214997231960297F, 0.5788751840591431F);
		this.barkback1b.addBox(-2.5F, -4.0F, -0.5F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkback1b, 0.035444551902461F, 0.1744255332284755F, 0.0061536955906296405F);
		this.barkback2a = new ModelRenderer(this, 1, 23);
		this.barkback2a.setRotationPoint(-1.48F, 7.05F, 3.16F);
		this.barkback2a.addBox(-0.2F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkback2a, -3.1391491926370017F, 0.26092672317315224F, -3.121346834266659F);
		this.Belt2 = new ModelRenderer(this, 10, 2);
		this.Belt2.setRotationPoint(4.01F, 10.0F, -2.0F);
		this.Belt2.addBox(0.0F, 0.0F, 0.0F, 0, 2, 4, 0.0F);
		this.barkback2c = new ModelRenderer(this, 25, 14);
		this.barkback2c.setRotationPoint(-2.859649181365967F, 0.939050555229187F, 0.8518217206001282F);
		this.barkback2c.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkback2c, -0.026075313999777656F, 0.43477582849948554F, 0.037526698627658214F);
		this.Barkfront1a = new ModelRenderer(this, 1, 27);
		this.Barkfront1a.setRotationPoint(1.5F, 10.04F, -3.0F);
		this.Barkfront1a.addBox(0.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(Barkfront1a, 0.05375614096142535F, -0.17383479349863523F, -0.0153588974175501F);
        this.Rootleft1 = new ModelRenderer(this, 21, 26);
        this.Rootleft1.setRotationPoint(-0.5F, 5.0F, 2.5F);
		this.Rootleft1.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 3, 0.0F);
		this.setRotateAngle(Rootleft1, 0.6108652381980153F, -0.2617993877991494F, 0.0F);
		this.Belt3 = new ModelRenderer(this, 1, 1);
		this.Belt3.setRotationPoint(-4.0F, 10.0F, -2.01F);
		this.Belt3.addBox(0.0F, 0.0F, 0.0F, 8, 2, 0, 0.0F);
		this.barkfront3d = new ModelRenderer(this, 25, 21);
		this.barkfront3d.setRotationPoint(-2.2878177165985107F, -2.080759048461914F, 0.3301289975643158F);
		this.barkfront3d.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkfront3d, -0.42063677337711863F, 0.2867452177299217F, -0.334097154017912F);
        this.barkfront1c = new ModelRenderer(this, 17, 25);
		this.barkfront1c.setRotationPoint(-2.954423189163208F, -0.01818070188164711F, 0.5206272006034851F);
		this.barkfront1c.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkfront1c, -0.015215062708624117F, 0.3488441755276304F, 0.012701695067186129F);
		this.Belt4 = new ModelRenderer(this, 1, 1);
		this.Belt4.setRotationPoint(-4.0F, 10.0F, 2.01F);
		this.Belt4.addBox(0.0F, 0.0F, 0.0F, 8, 2, 0, 0.0F);
		this.pauldronrighttop1 = new ModelRenderer(this, 11, 4);
		this.pauldronrighttop1.setRotationPoint(0.5F, -3.0F, 0.0F);
		this.pauldronrighttop1.addBox(-1.5F, 0.0F, -3.5F, 3, 1, 7, 0.0F);
		this.setRotateAngle(pauldronrighttop1, 0.0F, 0.0F, 0.17453292519943295F);
		this.armfakeleft = new ModelRenderer(this, 58, 39);
		this.armfakeleft.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.armfakeleft.addBox(-3.0F, -2.0F, -2.0F, 0, 0, 0, 0.0F);
		this.pauldronleftfront = new ModelRenderer(this, 1, 8);
		this.pauldronleftfront.setRotationPoint(-1.5F, 1.0F, 1.9900000095367432F);
		this.pauldronleftfront.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
		this.Rootleft2 = new ModelRenderer(this, 24, 1);
		this.Rootleft2.setRotationPoint(-0.5F, -0.5F, 3.0F);
		this.Rootleft2.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(Rootleft2, 0.2617993877991494F, -0.3490658503988659F, 0.0F);
        this.barkback3b = new ModelRenderer(this, 16, 21);
		this.barkback3b.setRotationPoint(-2.2953593730926514F, 0.199678435921669F, 0.8008123636245728F);
		this.barkback3b.addBox(-2.0F, -2.0F, -1.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(barkback3b, -0.15734173766502388F, 0.42867029867388357F, -0.08382197165770001F);
		this.barkfront1b = new ModelRenderer(this, 8, 26);
		this.barkfront1b.setRotationPoint(-0.4055797755718231F, -0.02021503821015358F, 0.5788751840591431F);
		this.barkfront1b.addBox(-2.5F, -4.0F, -0.5F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkfront1b, 0.035444630965055866F, 0.1744255332284755F, 0.006153645656359201F);
		this.barkback1c = new ModelRenderer(this, 17, 25);
		this.barkback1c.setRotationPoint(-2.954423189163208F, -0.01818085089325905F, 0.5206272006034851F);
		this.barkback1c.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkback1c, -0.01521546530367953F, 0.3488441755276304F, 0.012701799096916213F);
		this.barkback3d = new ModelRenderer(this, 25, 21);
		this.barkback3d.setRotationPoint(-2.2878174781799316F, -2.080759286880493F, 0.33012905716896057F);
		this.barkback3d.addBox(-2.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkback3d, -0.1668814470057967F, 0.3618904690757106F, -0.3709979806610772F);
		this.barkfront2a = new ModelRenderer(this, 1, 23);
		this.barkfront2a.setRotationPoint(1.48F, 7.05F, -3.16F);
		this.barkfront2a.addBox(-0.2F, -2.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(barkfront2a, 0.0024434609527920616F, -0.26092672317315224F, -0.02024581932313422F);
		this.barkback3a = new ModelRenderer(this, 1, 18);
		this.barkback3a.setRotationPoint(-1.19F, 5.05F, 2.19F);
		this.barkback3a.addBox(-0.2F, -3.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkback3a, -2.877349804837852F, 0.26092672317315224F, -3.121346834266659F);
		this.barkfront3c = new ModelRenderer(this, 1, 18);
		this.barkfront3c.setRotationPoint(-0.33642464876174927F, -3.142953634262085F, 0.07752383500337601F);
		this.barkfront3c.addBox(0.0F, -3.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkfront3c, 0.0035554643965062705F, 0.13856031006470126F, 0.5847116647128922F);
		this.barkfront3a = new ModelRenderer(this, 1, 18);
		this.barkfront3a.setRotationPoint(1.19F, 5.05F, -2.19F);
		this.barkfront3a.addBox(-0.2F, -3.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(barkfront3a, 0.2642428487519415F, -0.26092672317315224F, -0.02024581932313422F);
		this.pauldronrightfront = new ModelRenderer(this, 1, 8);
		this.pauldronrightfront.setRotationPoint(-1.5F, 1.0F, -2.990000009536743F);
		this.pauldronrightfront.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
		this.barkback2b = new ModelRenderer(this, 8, 20);
		this.barkback2b.setRotationPoint(-0.2363673448562622F, -1.0835572481155396F, 0.900143027305603F);
		this.barkback2b.addBox(-2.5F, -4.0F, -1.0F, 3, 4, 1, 0.0F);
		this.setRotateAngle(barkback2b, 0.17746230252997924F, 0.26099494670237733F, 0.02028021929171099F);
		this.Pauldronlefttop1.addChild(this.pauldronleftback);
		this.barkfront2a.addChild(this.barkfront2b);
		this.barkfront2a.addChild(this.barkfront2c);
		this.barkback3a.addChild(this.barkback3c);
		this.armfakeleft.addChild(this.Pauldronlefttop1);
		this.pauldronrighttop1.addChild(this.pauldronrightback);
		this.Rootright1.addChild(this.Rootright2);
		this.pauldronrighttop1.addChild(this.Pauldronrighttop2);
		this.Pauldronlefttop1.addChild(this.pauldronlefttop2);
		this.barkfront3a.addChild(this.barkfront3b);
		this.barkback1a.addChild(this.barkback1b);
		this.barkback2a.addChild(this.barkback2c);
		this.barkfront3a.addChild(this.barkfront3d);
		this.Barkfront1a.addChild(this.barkfront1c);
		this.armfakeright.addChild(this.pauldronrighttop1);
		this.Pauldronlefttop1.addChild(this.pauldronleftfront);
		this.Rootleft1.addChild(this.Rootleft2);
		this.barkback3a.addChild(this.barkback3b);
		this.Barkfront1a.addChild(this.barkfront1b);
		this.barkback1a.addChild(this.barkback1c);
		this.barkback3a.addChild(this.barkback3d);
		this.barkfront3a.addChild(this.barkfront3c);
		this.pauldronrighttop1.addChild(this.pauldronrightfront);
		this.barkback2a.addChild(this.barkback2b);
    }

    @Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float f5) {
		if (!(entity instanceof EntityPlayer))
			return;
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		
		int barkPieces = ItemBarkBelt.getBarkPiecesForRendering((EntityPlayer) entity);
		
		if (barkPieces > 0) {
			this.barkback1a.render(f5);
			this.Barkfront1a.render(f5);
		}
		
		if (barkPieces > 1) {
			this.barkback2a.render(f5);
			this.barkfront2a.render(f5);
		}
		
		if (barkPieces > 2) {
			this.barkback3a.render(f5);
			this.barkfront3a.render(f5);
		}
		
		if (barkPieces > 3) {
			float f = 1.0f;
			f = (float) (entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
			float armrot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount / f;
			this.armfakeleft.rotateAngleX = (float) (armrot * Math.PI / 360) * 1.1f;
			this.armfakeright.rotateAngleX = -this.armfakeleft.rotateAngleX;
			this.armfakeleft.render(f5);
			this.armfakeright.render(f5);
		}
		
		if (barkPieces > 4) {
			this.Rootleft1.render(f5);
			this.Rootright1.render(f5);
		}
		

		this.Belt.render(f5);
		this.Belt2.render(f5);
		this.Belt3.render(f5);
		this.Belt4.render(f5);

    }

	protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
