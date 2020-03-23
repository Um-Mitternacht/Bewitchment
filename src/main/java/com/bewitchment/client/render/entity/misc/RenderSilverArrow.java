package com.bewitchment.client.render.entity.misc;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.misc.EntitySilverArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class RenderSilverArrow extends RenderArrow<EntitySilverArrow> {
	private static final ResourceLocation SILVER_ARROW_TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/silver_arrow.png");
	private static final ResourceLocation COLD_IRON_ARROW_TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/cold_iron_arrow.png");
	
	public RenderSilverArrow(RenderManager manager) {
		super(manager);
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntitySilverArrow entity) {
		return entity.isColdIron() ? COLD_IRON_ARROW_TEX : SILVER_ARROW_TEX;
	}
}