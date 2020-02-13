package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelCleaver;
import com.bewitchment.common.entity.spirit.demon.EntityCleaver;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCleaver extends RenderLiving<EntityCleaver> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/cleaver.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/cleaver_2.png")};
	
	public RenderCleaver(RenderManager manager) {
		super(manager, new ModelCleaver(), 0.3f);
		this.addLayer(new LayerHeldItem(this));
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCleaver entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityCleaver entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.0, 1.0, 1.0);
	}
}