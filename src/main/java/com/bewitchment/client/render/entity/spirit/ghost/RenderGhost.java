package com.bewitchment.client.render.entity.spirit.ghost;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.ghost.ModelGhost;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
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
public class RenderGhost extends RenderLiving<EntityGhost> {
    private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/ghost_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/ghost_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/ghost_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/ghost_4.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/ghost_5.png")};

    public RenderGhost(RenderManager manager) {
        this(manager, new ModelGhost());
    }

    protected RenderGhost(RenderManager manager, ModelBase model) {
        super(manager, model, 0.3f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGhost entity) {
        return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
    }

    @Override
    public void doRender(EntityGhost entity, double x, double y, double z, float entityYaw, float partialTicks) {

        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.25F);
        GlStateManager.depthMask(false);

        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //GlStateManager.alphaFunc(256, 0.003921569F);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);

        //GlStateManager.enableLighting();
        //GlStateManager.alphaFunc(256, 0.1F);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }

    @Override
    protected void preRenderCallback(EntityGhost entity, float partialTickTime) {
        super.preRenderCallback(entity, partialTickTime);
        //GlStateManager.scale(1.6, 1.6, 1.6);
    }
}