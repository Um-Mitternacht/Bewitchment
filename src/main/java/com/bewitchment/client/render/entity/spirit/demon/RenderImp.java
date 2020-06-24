package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelImp;
import com.bewitchment.common.entity.spirit.demon.EntityImp;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderImp extends RenderLiving<EntityImp> {
	//imp_6 exists solely to spite some thief
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_4.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_5.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/imp_6.png")};

	public RenderImp(RenderManager manager) {
		super(manager, new ModelImp(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityImp entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}

	@Override
	protected void preRenderCallback(EntityImp entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(0.9, 0.9, 0.9);
	}
}