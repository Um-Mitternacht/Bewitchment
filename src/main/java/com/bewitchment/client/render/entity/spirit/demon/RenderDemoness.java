package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelDemoness;
import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDemoness extends RenderDemon {
    private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/demoness_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/demoness_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/demoness_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/demoness_3.png")};

    public RenderDemoness(RenderManager manager) {
        super(manager, new ModelDemoness());
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDemon entity) {
        return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
    }
}