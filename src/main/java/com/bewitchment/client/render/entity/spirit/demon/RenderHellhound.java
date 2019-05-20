package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelHellhound;
import com.bewitchment.common.entity.spirit.demon.EntityHellhound;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHellhound extends RenderLiving<EntityHellhound> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/hellhound_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/hellhound_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/hellhound_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/hellhound_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/hellhound_4.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/hellhound_5.png")};
	
	public RenderHellhound(RenderManager manager) {
		super(manager, new ModelHellhound(), 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityHellhound entity) {
		return TEX[entity.getDataManager().get(EntityHellhound.SKIN)];
	}
}