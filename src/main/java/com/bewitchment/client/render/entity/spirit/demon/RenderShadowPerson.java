package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelShadowPersonClassic;
import com.bewitchment.client.model.entity.spirit.demon.ModelShadowPersonSlim;
import com.bewitchment.common.entity.spirit.demon.EntityShadowPerson;
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
public class RenderShadowPerson extends RenderLiving<EntityShadowPerson> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/shadowperson_hatdemon.png")};
	private static final ModelBase slimModel = new ModelShadowPersonSlim();
	private static final ModelBase regModel = new ModelShadowPersonClassic();
	
	public RenderShadowPerson(RenderManager manager) {
		this(manager, new ModelShadowPersonClassic());
	}
	
	protected RenderShadowPerson(RenderManager manager, ModelBase model) {
		super(manager, model, 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityShadowPerson entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN) % 2];
	}
	
	@Override
	protected void preRenderCallback(EntityShadowPerson entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.05, 1.05, 1.05);
	}
	
	@Override
	public void doRender(EntityShadowPerson entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (entity.getDataManager().get(ModEntityMob.SKIN) >= 2) {
			this.mainModel = slimModel;
		}
		else this.mainModel = regModel;
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}