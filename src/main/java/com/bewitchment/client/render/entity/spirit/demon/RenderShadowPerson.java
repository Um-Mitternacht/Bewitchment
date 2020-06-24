package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelShadowPersonClassic;
import com.bewitchment.common.entity.spirit.demon.EntityShadowPerson;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("WeakerAccess")
@SideOnly(Side.CLIENT)
public class RenderShadowPerson extends RenderLiving<EntityShadowPerson> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson_hatdemon.png")};

	public RenderShadowPerson(RenderManager manager) {
		this(manager, new ModelShadowPersonClassic());
		this.addLayer(new LayerEyes(this));
	}

	protected RenderShadowPerson(RenderManager manager, ModelBase model) {
		super(manager, model, 0.3f);
		this.addLayer(new LayerEyes(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShadowPerson entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN) % 2];
	}

	@Override
	protected void preRenderCallback(EntityShadowPerson entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.05, 1.05, 1.05);
	}

	private static class LayerEyes implements LayerRenderer<EntityShadowPerson> {
		private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson_eyes.png");
		private static final ResourceLocation TEX1 = new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson_hatdemon_eyes.png");

		private final RenderShadowPerson renderer;

		private LayerEyes(RenderShadowPerson renderer) {
			this.renderer = renderer;
		}

		@Override
		public void doRenderLayer(EntityShadowPerson entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if (!entitylivingbaseIn.isInvisible() && !entitylivingbaseIn.isChild()) {
				if (entitylivingbaseIn.getDataManager().get(ModEntityMob.SKIN) % 2 == 0) {
					this.renderer.bindTexture(TEX);
				} else {
					this.renderer.bindTexture(TEX1);
				}

				GlStateManager.enableBlend();
				GlStateManager.disableAlpha();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

				int i = 61680;
				int j = i % 65536;
				int k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
				this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
				i = entitylivingbaseIn.getBrightnessForRender();
				j = i % 65536;
				k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
				this.renderer.setLightmap(entitylivingbaseIn);
				GlStateManager.disableBlend();
				GlStateManager.enableAlpha();
			}
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}
}