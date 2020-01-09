package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelLeonard;
import com.bewitchment.common.entity.spirit.demon.EntityLeonard;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderLeonard extends RenderLiving<EntityLeonard> {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/leonard.png");
	
	public RenderLeonard(RenderManager manager) {
		super(manager, new ModelLeonard(), 0.3f);
		this.addLayer(new LayerHeldItem(this));
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityLeonard entityLeonard) {
		return TEX;
	}
	
	@Override
	protected void preRenderCallback(EntityLeonard entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.5, 1.5, 1.5);
	}
}
