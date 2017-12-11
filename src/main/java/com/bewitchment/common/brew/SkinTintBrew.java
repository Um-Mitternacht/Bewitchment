package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.brew.IBrewRenderLiving;
import com.bewitchment.client.ResourceLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 13/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class SkinTintBrew implements IBrew, IBrewRenderLiving {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO-OP
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public String getName() {
		return "skin_tint";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		mc.renderEngine.bindTexture(ResourceLocations.BREW_TEXTURES);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder buf = tessellator.getBuffer();
		GlStateManager.color(1F, 1F, 1F, 1F);
		final float f = 0.00390625F;

		buf.begin(7, DefaultVertexFormats.POSITION_TEX);
		buf.pos(x, y + 20, 0).tex(236 * f, (236 + 20) * f).endVertex();
		buf.pos(x + 20, y + 20, 0).tex((236 + 20) * f, (236 + 20) * f).endVertex();
		buf.pos(x + 20, y, 0).tex((236 + 20) * f, 236 * f).endVertex();
		buf.pos(x, y, 0).tex(236 * f, 236 * f).endVertex();
		tessellator.draw();

		int textureX = 9 % 14 * 18;
		int textureY = 0;
		x += 1;
		y += 1;

		GlStateManager.pushMatrix();

		EnumDyeColor dye = EnumDyeColor.byDyeDamage(Math.min(amplifier, EnumDyeColor.values().length - 1));
		int rgb = dye.getColorValue();

		float r = (rgb >>> 16 & 0xFF) / 256.0F;
		float g = (rgb >>> 8 & 0xFF) / 256.0F;
		float b = (rgb & 0xFF) / 256.0F;
		GlStateManager.color(r, g, b);

		buf.begin(7, DefaultVertexFormats.POSITION_TEX);
		buf.pos(x, y + 18, 0).tex(textureX * f, (textureY + 18) * f).endVertex();
		buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * f, (textureY + 18) * f).endVertex();
		buf.pos(x + 18, y, 0).tex((textureX + 18) * f, textureY * f).endVertex();
		buf.pos(x, y, 0).tex(textureX * f, textureY * f).endVertex();
		tessellator.draw();

		GlStateManager.popMatrix();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onRenderLiving(RenderLivingEvent.Pre event, RenderLivingBase renderer, int amplifier) {
		GlStateManager.pushMatrix();

		EnumDyeColor dye = EnumDyeColor.byDyeDamage(Math.min(amplifier, EnumDyeColor.values().length - 1));
		int rgb = dye.getColorValue();

		float r = (rgb >>> 16 & 0xFF) / 256.0F;
		float g = (rgb >>> 8 & 0xFF) / 256.0F;
		float b = (rgb & 0xFF) / 256.0F;
		GlStateManager.color(r, g, b);

		GlStateManager.popMatrix();
	}
}
