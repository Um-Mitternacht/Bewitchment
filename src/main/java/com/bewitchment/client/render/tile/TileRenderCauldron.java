package com.bewitchment.client.render.tile;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.util.Optional;

/**
 * This class was created by Arekkuusu on 09/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class TileRenderCauldron extends TileEntitySpecialRenderer<TileEntityCauldron> {

	@SuppressWarnings("ConstantConditions")
	@Override
	public void render(TileEntityCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		Optional<FluidStack> optional = te.getFluid();
		if (optional.isPresent() && optional.get().amount > 0) {
			FluidStack fluidStack = optional.get();
			Fluid fluid = fluidStack.getFluid();
			ResourceLocation location = fluid.getStill();
			double level = fluidStack.amount / (Fluid.BUCKET_VOLUME * 2D);

			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.translate(x, y + 0.1 + level, z);
			if (fluid == FluidRegistry.WATER) {
				int color = te.getColorRGB();
				float r = (color >>> 16 & 0xFF) / 256.0F;
				float g = (color >>> 8 & 0xFF) / 256.0F;
				float b = (color & 0xFF) / 256.0F;
				GlStateManager.color(r, g, b, 0.8f);
				location = ResourceLocations.GRAY_WATER;
			}

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableAlpha();

			final float w = 0.125F;
			GlStateManager.translate(w, 0, w);
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			final float s = 0.0460425F;
			GlStateManager.scale(s, s, s);

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			renderWater(location);

			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}

		if (te.getName() != null && Minecraft.getMinecraft().objectMouseOver != null && te.getPos().equals(Minecraft.getMinecraft().objectMouseOver.getBlockPos())) {
			drawNameplate(te, te.getName(), x, y, z, 5);
		}
	}

	private void renderWater(ResourceLocation loc) {
		final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(loc.toString());
		final Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		tessellator.getBuffer().pos(0, 16, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		tessellator.getBuffer().pos(16, 16, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
		tessellator.getBuffer().pos(16, 0, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		tessellator.getBuffer().pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		tessellator.draw();
	}
}
