package com.bewitchment.client.render.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.living.ModelRaven;
import com.bewitchment.common.entity.living.EntityRaven;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("NullableProblems")
@SideOnly(Side.CLIENT)
public class RenderRaven extends RenderLiving<EntityRaven> {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/raven.png");

	public RenderRaven(RenderManager manager) {
		super(manager, new ModelRaven(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRaven entity) {
		return TEX;
	}

	@Override
	protected void preRenderCallback(EntityRaven entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		if (entity.isChild()) GlStateManager.scale(0.4, 0.4, 0.4);
		else GlStateManager.scale(0.6, 0.6, 0.6);
	}
}