package com.witchcraft.client.core.event;

import com.witchcraft.api.brew.BrewEffect;
import com.witchcraft.api.brew.IBrewRenderLiving;
import com.witchcraft.common.core.capability.brew.BrewStorageHandler;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

/**
 * This class was created by Arekkuusu on 13/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
@SideOnly(Side.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public void onRender(RenderLivingEvent.Pre event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntity());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewRenderLiving).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewRenderLiving) effect.getBrew()).onRenderLiving(event, event.getRenderer(), effect.getAmplifier());
		});
	}
}
