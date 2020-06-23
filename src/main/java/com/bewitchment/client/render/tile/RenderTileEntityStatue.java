package com.bewitchment.client.render.tile;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.misc.Statues;
import com.bewitchment.common.block.BlockStatue;
import com.bewitchment.common.block.tile.entity.TileEntityStatue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileEntityStatue extends TileEntitySpecialRenderer<TileEntityStatue> {
	@Override
	public void render(TileEntityStatue tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		Statues.Statue statue = Statues.statues.get(new ResourceLocation(Bewitchment.MODID, tile.name));
		if (statue == null) return;
		ResourceLocation loc = statue.getLoc();
		ModelBase model = statue.getModel();
		if (loc == null || model == null) return;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180, 0, 0, 1);
		bindTexture(loc);
		if (!tile.item && tile.getWorld().getBlockState(tile.getPos()).getPropertyKeys().contains(BlockHorizontal.FACING)) {
			EnumFacing face = tile.getWorld().getBlockState(tile.getPos()).getValue(BlockHorizontal.FACING);
			GlStateManager.rotate(face == EnumFacing.WEST ? 270 : face == EnumFacing.EAST ? 90 : face == EnumFacing.SOUTH ? 180 : 0, 0, 1, 0);
		}
		model.render(null, 0, 0, 0, 0, 0, 0.0625f);
		GlStateManager.popMatrix();
	}
	
	public static class ForwardingTEISR extends TileEntityItemStackRenderer {
		private final TileEntityItemStackRenderer compose;
		private final TileEntityStatue statueRender = new TileEntityStatue();
		
		public ForwardingTEISR(TileEntityItemStackRenderer compose) {
			this.compose = compose;
		}
		
		@Override
		public void renderByItem(ItemStack itemStack) {
			Block block = Block.getBlockFromItem(itemStack.getItem());
			if (block instanceof BlockStatue) {
				statueRender.name = ((BlockStatue) block).statue.getName();
				statueRender.item = true;
				TileEntityRendererDispatcher.instance.render(this.statueRender, 0.0D, 0.0D, 0.0D, 0.0F, 0, 0);
			}
			else {
				compose.renderByItem(itemStack);
			}
		}
	}
}