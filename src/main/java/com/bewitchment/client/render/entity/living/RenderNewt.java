package com.bewitchment.client.render.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.living.ModelNewt;
import com.bewitchment.common.entity.living.EntityNewt;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNewt extends RenderLiving<EntityNewt> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/newt_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/newt_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/newt_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/newt_3.png")};
	
	public RenderNewt(RenderManager manager) {
		super(manager, new ModelNewt(), 0.1f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNewt entity) {
		return TEX[entity.getDataManager().get(EntityNewt.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityNewt entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		if (entity.isChild()) GlStateManager.scale(0.4, 0.4, 0.4);
		else GlStateManager.scale(0.6, 0.6, 0.6);
	}
}