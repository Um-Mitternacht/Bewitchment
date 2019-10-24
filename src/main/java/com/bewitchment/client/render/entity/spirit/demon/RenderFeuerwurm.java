package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelFeuerwurm;
import com.bewitchment.common.entity.spirit.demon.EntityFeuerwurm;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFeuerwurm extends RenderLiving<EntityFeuerwurm> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_4.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_5.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_6.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_7.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/feuerwurm_8.png")};
	
	public RenderFeuerwurm(RenderManager manager) {
		super(manager, new ModelFeuerwurm(), 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFeuerwurm entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
}