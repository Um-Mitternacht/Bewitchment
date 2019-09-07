package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelDruden;
import com.bewitchment.common.entity.spirit.demon.EntityDruden;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDruden extends RenderLiving<EntityDruden> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_4.png")};
	
	public RenderDruden(RenderManager manager) {
		super(manager, new ModelDruden(), 0.3f);
		addLayer(new LayerEyes(this));
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDruden entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityDruden entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.2, 1.2, 1.2);
	}
	
	private static class LayerEyes implements LayerRenderer<EntityDruden> {
		private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/drude_eyes.png")};
		
		private final RenderDruden renderer;
		
		private LayerEyes(RenderDruden renderer) {
			this.renderer = renderer;
		}
		
		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
		
		@Override
		public void doRenderLayer(EntityDruden entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float rotationYaw, float rotationPitch, float scale) {
			renderer.bindTexture(TEX[entity.getDataManager().get(ModEntityMob.SKIN)]);
			GlStateManager.color(0.5f, 0.5f, 0.5f);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, age, rotationYaw, rotationPitch, scale);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
			GlStateManager.disableBlend();
		}
	}
}