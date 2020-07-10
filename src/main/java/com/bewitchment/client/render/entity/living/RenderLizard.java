package com.bewitchment.client.render.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.living.ModelLizard;
import com.bewitchment.common.entity.living.EntityLizard;
import com.bewitchment.common.entity.util.ModEntityAnimal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLizard extends RenderLiving<EntityLizard> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/lizard_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/lizard_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/lizard_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/lizard_3.png")};

	public RenderLizard(RenderManager manager) {
		super(manager, new ModelLizard(), 0.1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLizard entity) {
		return TEX[entity.getDataManager().get(ModEntityAnimal.SKIN)];
	}

	@Override
	protected void preRenderCallback(EntityLizard entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		if (entity.isChild()) GlStateManager.scale(0.1, 0.1, 0.1);
		else GlStateManager.scale(0.2, 0.2, 0.2);
	}
}