package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.demon.EntityCambion;
import com.bewitchment.common.entity.spirit.demon.EntityImp;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/30/2020.
 */
public class RenderCambion extends RenderLiving<EntityCambion> {
	
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson_hatdemon.png")};
	private static final ModelBase slimModel = new ModelCambionSlim();
	private static final ModelBase regModel = new ModelCambion();
	
	public RenderCambion(RenderManager manager) {
		super(manager, new ModelCambion(), 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCambion entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityCambion entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(0.9, 0.9, 0.9);
	}
}
