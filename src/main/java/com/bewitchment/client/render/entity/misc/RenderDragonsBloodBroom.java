package com.bewitchment.client.render.entity.misc;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.entity.EntityBroom;
import com.bewitchment.api.registry.entity.client.RenderBroom;
import com.bewitchment.common.entity.misc.EntityDragonsBloodBroom;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderDragonsBloodBroom extends RenderBroom {
    private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/dragons_blood_broom.png");
    private static final ResourceLocation TEX_SIGIl = new ResourceLocation(Bewitchment.MODID, "textures/entity/dragons_blood_broom_marked.png");

    public RenderDragonsBloodBroom(RenderManager manager) {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBroom entityBroom) {
        if (((EntityDragonsBloodBroom) entityBroom).sigil != null) return TEX_SIGIl;
        return TEX;
    }
}
