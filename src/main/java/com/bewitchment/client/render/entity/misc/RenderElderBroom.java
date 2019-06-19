package com.bewitchment.client.render.entity.misc;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.entity.EntityBroom;
import com.bewitchment.api.registry.entity.client.RenderBroom;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("NullableProblems")
public class RenderElderBroom extends RenderBroom {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/elder_broom.png");
	
	public RenderElderBroom(RenderManager manager) {
		super(manager);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBroom entityBroom) {
		return TEX;
	}
}