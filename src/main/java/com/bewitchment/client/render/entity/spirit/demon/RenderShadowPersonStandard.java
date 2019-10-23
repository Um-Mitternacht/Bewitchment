package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelShadowPersonClassic;
import com.bewitchment.common.entity.spirit.demon.EntityShadowPersonStandard;
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
public class RenderShadowPersonStandard extends RenderLiving<EntityShadowPersonStandard> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson"), new ResourceLocation("textures/entity/shadowperson_hatdemon")};
	
	public RenderShadowPersonStandard(RenderManager manager) {
		this(manager, new ModelShadowPersonClassic());
	}
	
	protected RenderShadowPersonStandard(RenderManager manager, ModelBase model) {
		super(manager, model, 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityShadowPersonStandard entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityShadowPersonStandard entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.6, 1.6, 1.6);
	}
}