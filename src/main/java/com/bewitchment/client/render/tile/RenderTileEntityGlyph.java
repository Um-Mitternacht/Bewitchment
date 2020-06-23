package com.bewitchment.client.render.tile;

import com.bewitchment.client.handler.ClientHandler;
import com.bewitchment.common.block.tile.entity.TileEntityGlyph;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

/**
 * A large portion of this code comes from Botania, created by Vazkii. This code was edited to the needs of this mod, but is largely Vazkii's code.
 * Botania's license: https://botaniamod.net/license.php
 */
@SideOnly(Side.CLIENT)
public class RenderTileEntityGlyph extends TileEntitySpecialRenderer<TileEntityGlyph> {
	@Override
	public void render(TileEntityGlyph tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		ItemStackHandler inventory = tile.getInventories()[0];
		int items = 0;
		for (int i = 0; i < inventory.getSlots(); i++) {
			if (inventory.getStackInSlot(i).isEmpty()) break;
			else items++;
		}
		float[] angles = new float[inventory.getSlots()];
		float anglePer = 360f / items;
		float totalAngle = 0;
		for (int i = 0; i < angles.length; i++) angles[i] = totalAngle += anglePer;
		float time = ClientHandler.ticksInGame + partialTicks;
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		for (int i = 0; i < inventory.getSlots(); i++) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5, 1 / 4f, 0.5);
			GlStateManager.rotate(angles[i] + time, 0, 1, 0);
			GlStateManager.translate(2 / 3f, 0, 0.25f);
			GlStateManager.translate(0, 0.075 * Math.sin((time + i * 10) / 5f), 0);
			ItemStack stack = inventory.getStackInSlot(i);
			Minecraft mc = Minecraft.getMinecraft();
			if (!stack.isEmpty()) mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			GlStateManager.popMatrix();
		}
		GlStateManager.translate(0, 0.2f, 0);
		GlStateManager.popMatrix();
	}
}