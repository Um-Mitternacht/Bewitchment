package com.bewitchment.client.render.entity.spirit.ghost;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.ghost.ModelBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlackDog extends RenderLiving<EntityBlackDog> {
    private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_4.png")};

    public RenderBlackDog(RenderManager manager) {
        super(manager, new ModelBlackDog(), 0.3f);
        addLayer(new LayerEyes(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBlackDog entity) {
        return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
    }

    @Override
    protected void preRenderCallback(EntityBlackDog entity, float partialTickTime) {
        super.preRenderCallback(entity, partialTickTime);
        GlStateManager.scale(1.8, 1.8, 1.8);
    }

    private static class LayerEyes implements LayerRenderer<EntityBlackDog> {
        private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_eyes_0.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_eyes_1.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_eyes_2.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_eyes_3.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/black_dog_eyes_4.png")};

        private final RenderBlackDog renderer;

        private LayerEyes(RenderBlackDog renderer) {
            this.renderer = renderer;
        }

        @Override
        public void doRenderLayer(EntityBlackDog entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float rotationYaw, float rotationPitch, float scale) {
            renderer.bindTexture(TEX[entity.getDataManager().get(ModEntityMob.SKIN)]);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, age, rotationYaw, rotationPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.disableBlend();
        }

        @Override
        public boolean shouldCombineTextures() {
            return false;
        }
    }
}