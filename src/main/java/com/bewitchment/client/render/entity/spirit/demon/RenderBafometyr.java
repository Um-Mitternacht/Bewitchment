package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelBafometyr;
import com.bewitchment.common.entity.spirit.demon.EntityBafometyr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 4/23/2020.
 */
public class RenderBafometyr extends RenderLiving<EntityBafometyr> {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/bafometyr.png");
	private static final ResourceLocation[] FLAME = {new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_2"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_3"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_4"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_5"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_6"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_7"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_8")};
	
	public RenderBafometyr(RenderManager manager) {
		this(manager, new ModelBafometyr());
		addLayer(new RenderBafometyr.LayerFlames(this));
	}
	
	protected RenderBafometyr(RenderManager manager, ModelBase model) {
		super(manager, model, 0.3f);
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityBafometyr entity) {
		return TEX;
	}
	
	//Todo: Animate this when I'm not dealing with ungodly brain fog
	private static class LayerFlames implements LayerRenderer<EntityBafometyr> {
		private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_4.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_5.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_6.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/layer/bafometyr_fire_7.png")};
		
		private final RenderBafometyr renderer;
		
		private LayerFlames(RenderBafometyr renderer) {
			this.renderer = renderer;
		}
		
		@Override
		public void doRenderLayer(EntityBafometyr entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float rotationYaw, float rotationPitch, float scale) {
			renderer.bindTexture(TEX[(entity.getFlameTimer())]);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, age, rotationYaw, rotationPitch, scale);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
			GlStateManager.disableBlend();
		}
		
		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}
}
