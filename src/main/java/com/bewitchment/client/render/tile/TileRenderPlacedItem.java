package com.bewitchment.client.render.tile;

import com.bewitchment.common.tile.tiles.TileEntityPlacedItem;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class TileRenderPlacedItem extends TileEntitySpecialRenderer<TileEntityPlacedItem> {
	@Override
	public void render(TileEntityPlacedItem te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (!te.getItem().isEmpty()) {
			ItemStack stack = te.getItem();
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.1625, z + 0.5);
			float rot = te.getWorld().getBlockState(te.getPos()).getValue(BlockHorizontal.FACING).getHorizontalAngle();
			if (rot == 0 || rot == 180) {
				rot += 180;
			}
			GlStateManager.rotate(rot, 0, 1, 0);
			GlStateManager.translate(0, -0.125, 0);
			GlStateManager.rotate(-90, 1, 0, 0);
			GlStateManager.translate(0, -0.125, 0);

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
