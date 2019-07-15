package com.bewitchment.client.render.tile;

import com.bewitchment.common.block.tile.entity.TileEntityIdol;
import com.bewitchment.proxy.ClientProxy;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileEntityIdol extends TileEntitySpecialRenderer<TileEntityIdol> {
	@Override
	public void render(TileEntityIdol tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180, 0, 0, 1);
		ResourceLocation loc = ClientProxy.IDOL_TEXTURES.get(tile.stack.getItem());
		if (loc == null) {
			GlStateManager.popMatrix();
			return;
		}
		bindTexture(loc);
		EnumFacing face = tile.getWorld().getBlockState(tile.getPos()).getValue(BlockHorizontal.FACING);
		GlStateManager.rotate(face == EnumFacing.WEST ? 270 : face == EnumFacing.EAST ? 90 : face == EnumFacing.SOUTH ? 180 : 0, 0, 1, 0);
		ModelBase model = ClientProxy.IDOL_MODELS.get(tile.stack.getItem());
		if (model == null) {
			GlStateManager.popMatrix();
			return;
		}
		model.render(null, 0, 0, 0, 0, 0, 0.0625f);
		GlStateManager.popMatrix();
	}
}