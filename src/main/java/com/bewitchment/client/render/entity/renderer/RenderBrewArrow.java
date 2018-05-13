package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.common.entity.EntityBrewArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBrewArrow extends RenderArrow<EntityBrewArrow> {
	public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");
	public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

	public RenderBrewArrow(RenderManager manager) {
		super(manager);
	}

	@Override
	public void doRender(EntityBrewArrow entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBrewArrow entity) {
		return RES_TIPPED_ARROW;
	}
}