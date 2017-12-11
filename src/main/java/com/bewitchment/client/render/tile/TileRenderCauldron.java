package com.bewitchment.client.render.tile;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.tile.TileCauldron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.util.Optional;

/**
 * This class was created by Arekkuusu on 09/03/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class TileRenderCauldron extends TileEntitySpecialRenderer<TileCauldron> {

	@SuppressWarnings("ConstantConditions")
	@Override
	public void render(TileCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		Optional<FluidStack> optional = te.getFluid();
		if (optional.isPresent() && optional.get().amount > 0) {
			FluidStack fluidStack = optional.get();
			Fluid fluid = fluidStack.getFluid();
			ResourceLocation location = fluid.getStill();
			double level = (double) fluidStack.amount / (Fluid.BUCKET_VOLUME * 2D);

			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.translate(x, y + 0.1 + level, z);
			if (fluid == FluidRegistry.WATER || te.hasIngredients()) {
				float r = (te.getColorRGB() >>> 16 & 0xFF) / 256.0F;
				float g = (te.getColorRGB() >>> 8 & 0xFF) / 256.0F;
				float b = (te.getColorRGB() & 0xFF) / 256.0F;
				GlStateManager.color(r, g, b);
				if (fluid == FluidRegistry.WATER)
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
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.2D, z + 0.35);
		GlStateManager.rotate(90F, 1F, 0, 0);

		ItemStack stack = te.getContainer();
		if (!stack.isEmpty()) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
		}
		GlStateManager.popMatrix();
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
