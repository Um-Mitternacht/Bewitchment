package com.bewitchment.client.render.tile;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTileEntityWitchesCauldron extends TileEntitySpecialRenderer<TileEntityWitchesCauldron> {
    public static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "blocks/fluid/gray_fluid");

    @Override
    public void render(TileEntityWitchesCauldron tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (tile.hasCustomName()) drawNameplate(tile, tile.getName(), x, y - 0.25, z, 3);
        FluidTank tank = tile.tank;
        if (tank.getFluid() != null) {
            FluidStack stack = tank.getFluid();
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.translate(x + 0.125, y + tile.getLiquidHeight(), z + 0.125);
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.scale(0.0460425, 0.0460425, 0.0460425);
            boolean water = stack.getFluid() == FluidRegistry.WATER;
            if (water) GlStateManager.color(tile.color[0] / 255f, tile.color[1] / 255f, tile.color[2] / 255f);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(water ? TEX.toString() : stack.getFluid().getStill().toString());
            Tessellator tessellator = Tessellator.getInstance();
            tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            tessellator.getBuffer().pos(0, 16, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
            tessellator.getBuffer().pos(16, 16, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
            tessellator.getBuffer().pos(16, 0, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
            tessellator.getBuffer().pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
            tessellator.draw();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}