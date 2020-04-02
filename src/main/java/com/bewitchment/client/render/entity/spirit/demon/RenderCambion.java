package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelCambion;
import com.bewitchment.client.model.entity.spirit.demon.ModelCambionSlim;
import com.bewitchment.common.entity.spirit.demon.EntityCambion;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 3/30/2020.
 */
public class RenderCambion extends RenderLiving<EntityCambion> {
	
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_3.png")};
	private static final ModelBase slimModel = new ModelCambionSlim();
	private static final ModelBase regModel = new ModelCambion();
	
	public RenderCambion(RenderManager manager) {
		super(manager, new ModelCambion(), 0.3f);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerBipedArmor(this));
	}
	
	@Override
	public void doRender(EntityCambion entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (entity.getDataManager().get(ModEntityMob.SKIN) >= 4) {
			this.mainModel = slimModel;
		}
		else this.mainModel = regModel;
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCambion entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityCambion entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.0, 1.0, 1.0);
	}
}
