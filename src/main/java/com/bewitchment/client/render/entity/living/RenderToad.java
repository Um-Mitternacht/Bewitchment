package com.bewitchment.client.render.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.living.ModelToad;
import com.bewitchment.common.entity.living.EntityToad;
import com.bewitchment.common.entity.util.ModEntityTameable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderToad extends RenderLiving<EntityToad> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/toad_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/toad_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/toad_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/toad_3.png")};

	public RenderToad(RenderManager manager) {
		super(manager, new ModelToad(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityToad entity) {
		return TEX[entity.getDataManager().get(ModEntityTameable.SKIN)];
	}

	@Override
	protected void preRenderCallback(EntityToad entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		if (entity.isChild()) GlStateManager.scale(0.2, 0.2, 0.2);
		else GlStateManager.scale(0.4, 0.4, 0.4);
	}
}