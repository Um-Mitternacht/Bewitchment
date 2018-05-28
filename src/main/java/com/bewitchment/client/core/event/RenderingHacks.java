package com.bewitchment.client.core.event;

import com.bewitchment.common.item.block.ItemBlockRevealingLantern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.UUID;

public class RenderingHacks {

	HashMap<UUID, Tuple<Float, Float>> map = new HashMap<>();

	@SubscribeEvent
	public void raisePlayerHandWhenHoldingLantern(RenderPlayerEvent.Pre evt) {
		if (evt.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemBlockRevealingLantern) {
			saveAndRaise(evt.getEntityPlayer());
		}
	}

	@SubscribeEvent
	public void lowerPlayerHandWhenHoldingLantern(RenderPlayerEvent.Post evt) {
		if (evt.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemBlockRevealingLantern) {
			restore(evt.getEntityPlayer());
		}
	}

	private void saveAndRaise(EntityPlayer p) {
		map.put(p.getUniqueID(), new Tuple<Float, Float>(p.swingProgress, p.prevSwingProgress));
		p.swingProgress = 0.18f;
		p.prevSwingProgress = 0.18f;
	}

	private void restore(EntityPlayer p) {
		if (map.containsKey(p.getUniqueID())) {
			Tuple<Float, Float> t = map.get(p.getUniqueID());
			p.swingProgress = t.getFirst();
			p.prevSwingProgress = t.getSecond();
		}
	}

}
