package com.bewitchment.client.core.event;

import java.util.Collection;
import java.util.Optional;

import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.capability.IBrewStorage;
import com.bewitchment.common.core.capability.brew.BrewStorageHandler;
import com.bewitchment.common.core.handler.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SideOnly(Side.CLIENT)
public class BrewHUD {

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (!ConfigHandler.CLIENT.BREW_HUD.hide && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			Optional<IBrewStorage> optional = BrewStorageHandler.getBrewStorage(Minecraft.getMinecraft().player);
			if (optional.isPresent()) {
				ScaledResolution res = event.getResolution();

				int xOffset = res.getScaledWidth() - ConfigHandler.CLIENT.BREW_HUD.x;
				int yOffset = ConfigHandler.CLIENT.BREW_HUD.y;
				Minecraft mc = Minecraft.getMinecraft();
				Collection<BrewEffect> effects = optional.get().getBrewEffects();
				if (effects.isEmpty()) return;

				for (BrewEffect effect : effects) {
					IBrew brew = effect.getBrew();
					if (brew.shouldRender()) {
						GlStateManager.pushMatrix();
						GlStateManager.enableBlend();

						brew.renderHUD(xOffset, yOffset, mc, effect.getAmplifier());
						if (ConfigHandler.CLIENT.BREW_HUD.orientation) {
							yOffset += 22;
						} else {
							xOffset += 22;
						}

						GlStateManager.disableBlend();
						GlStateManager.popMatrix();
					}
				}
			}
		}
	}
}
