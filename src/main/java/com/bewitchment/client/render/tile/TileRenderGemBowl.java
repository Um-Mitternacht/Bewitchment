package com.bewitchment.client.render.tile;

import com.bewitchment.common.tile.tiles.TileEntityGemBowl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class TileRenderGemBowl extends TileEntitySpecialRenderer<TileEntityGemBowl> {
	@Override
	public void render(TileEntityGemBowl te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te.hasGem()) {
			ItemStack stack = te.getGem();
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.27, z + 0.5);
			if (te.getDirection() == EnumFacing.EAST || te.getDirection() == EnumFacing.WEST) {
				GlStateManager.rotate(90, 0, 1, 0);
			}
			GlStateManager.translate(0, -0.125, 0);
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.translate(0, -0.100, 0);
			GlStateManager.scale(0.8, 0.8, 0.8);

			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

			GlStateManager.popMatrix();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
	}
}
