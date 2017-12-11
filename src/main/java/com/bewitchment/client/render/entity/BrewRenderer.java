package com.bewitchment.client.render.entity;

import com.bewitchment.common.entity.EntityBrew;
import com.bewitchment.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;

/**
 * This class was created by Arekkuusu on 07/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BrewRenderer extends RenderSnowball<EntityBrew> {

	public BrewRenderer(RenderManager renderManagerIn) {
		super(renderManagerIn, ModItems.brew_phial_splash, Minecraft.getMinecraft().getRenderItem());
	}

	@Override
	public ItemStack getStackToRender(EntityBrew brew) {
		return brew.getBrew();
	}
}
