package com.bewitchment.client.render.tile;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntityDBChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileEntityDBChest extends TileEntitySpecialRenderer<TileEntityDBChest> {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/blocks/dragons_blood_chest.png");
	private final ModelChest model = new ModelChest();

	@Override
	public void render(TileEntityDBChest tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		int i = 0;
		if (tile.hasWorld()) i = tile.getBlockMetadata();
		if (destroyStage >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4, 4, 1);
			GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
			GlStateManager.matrixMode(5888);
		} else this.bindTexture(TEX);
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1, 1, 1, alpha);
		GlStateManager.translate(x, y + 1, z + 1);
		GlStateManager.scale(1, -1, -1);
		GlStateManager.translate(0.5f, 0.5f, 0.5f);
		int j = 0;
		if (i == 1) j = 90;
		if (i == 2) j = 180;
		if (i == 3) j = -90;
		GlStateManager.rotate(j, 0, 1, 0);
		GlStateManager.translate(-0.5f, -0.5f, -0.5f);
		float f = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialTicks;
		f = 1 - f;
		f = 1 - f * f * f;
		this.model.chestLid.rotateAngleX = -(f * 1.5707964f);
		this.model.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1, 1, 1, 1);
		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}
