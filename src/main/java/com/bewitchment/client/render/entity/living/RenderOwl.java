package com.bewitchment.client.render.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.living.ModelOwl;
import com.bewitchment.common.entity.living.EntityOwl;
import com.bewitchment.common.entity.util.ModEntityTameable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOwl extends RenderLiving<EntityOwl> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/owl_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/owl_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/owl_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/owl_3.png")};
	
	public RenderOwl(RenderManager manager) {
		super(manager, new ModelOwl(), 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityOwl entity) {
		return TEX[entity.getDataManager().get(ModEntityTameable.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityOwl entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		if (entity.isChild()) GlStateManager.scale(0.4, 0.4, 0.4);
		else GlStateManager.scale(0.6, 0.6, 0.6);
	}
}