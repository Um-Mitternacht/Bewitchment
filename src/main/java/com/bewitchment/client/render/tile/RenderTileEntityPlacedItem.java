package com.bewitchment.client.render.tile;

import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileEntityPlacedItem extends TileEntitySpecialRenderer<TileEntityPlacedItem> {
	@Override
	public void render(TileEntityPlacedItem tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (tile.getWorld().getBlockState(tile.getPos()).getBlock() == ModObjects.placed_item) {
			ItemStack stack = tile.getInventories()[0].getStackInSlot(0);
			if (!stack.isEmpty()) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5, y + 0.0375, z + 0.5);
				GlStateManager.rotate(90 * (2 - tile.getWorld().getBlockState(tile.getPos()).getValue(BlockHorizontal.FACING).getOpposite().getHorizontalIndex()), 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(0.5, 0.5, 0.5);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
				GlStateManager.popMatrix();
			}
		}
	}
}