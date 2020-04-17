package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelDruden;
import com.bewitchment.common.entity.spirit.demon.EntityDruden;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDruden extends RenderLiving<EntityDruden> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_3.png")};
	
	public RenderDruden(RenderManager manager) {
		super(manager, new ModelDruden(), 0.3f);
		this.addLayer(new LayerHeldItem(this));
		addLayer(new LayerEyes(this));
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDruden entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityDruden entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.0, 1.0, 1.0);
	}
	
	private static class LayerEyes implements LayerRenderer<EntityDruden> {
		private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_eyes.png");
		
		private final RenderDruden renderer;
		
		private LayerEyes(RenderDruden renderer) {
			this.renderer = renderer;
		}
		
		@Override
		public void doRenderLayer(EntityDruden entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if (!entitylivingbaseIn.isInvisible() && !entitylivingbaseIn.isChild()) {
				this.renderer.bindTexture(TEX);
				
				GlStateManager.enableBlend();
				GlStateManager.disableAlpha();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
				
				if (entitylivingbaseIn.isInvisible()) {
					GlStateManager.depthMask(false);
				}
				else {
					GlStateManager.depthMask(true);
				}
				
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