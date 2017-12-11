package com.bewitchment.api.brew;

import com.bewitchment.client.ResourceLocations;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface IBrew {

	/**
	 * Apply the effects
	 */
	default void onStart(World world, BlockPos pos, EntityLivingBase entity, int amplifier) {
		//NO-OP
	}

	void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick);

	default void onFinish(World world, BlockPos pos, EntityLivingBase entity, int amplifier) {
		//NO-OP
	}

	boolean isBad();

	boolean isInstant();

	int getColor();

	String getName();

	default boolean shouldRender() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	void renderHUD(int x, int y, net.minecraft.client.Minecraft mc, int amplifier);

	@SideOnly(Side.CLIENT)
	default void render(int x, int y, net.minecraft.client.Minecraft mc, int index) {
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

		int textureX = index % 14 * 18;
		int textureY = index / 14 * 18;
		x += 1;
		y += 1;

		buf.begin(7, DefaultVertexFormats.POSITION_TEX);
		buf.pos(x, y + 18, 0).tex(textureX * f, (textureY + 18) * f).endVertex();
		buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * f, (textureY + 18) * f).endVertex();
		buf.pos(x + 18, y, 0).tex((textureX + 18) * f, textureY * f).endVertex();
		buf.pos(x, y, 0).tex(textureX * f, textureY * f).endVertex();
		tessellator.draw();
	}
}
