package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelDemon;
import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("WeakerAccess")
@SideOnly(Side.CLIENT)
public class RenderDemon extends RenderLiving<EntityDemon> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/demon_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/demon_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/demon_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/demon_3.png")};

	public RenderDemon(RenderManager manager) {
		this(manager, new ModelDemon());
	}

	protected RenderDemon(RenderManager manager, ModelBase model) {
		super(manager, model, 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDemon entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}

	@Override
	protected void preRenderCallback(EntityDemon entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.4, 1.4, 1.4);
	}
}