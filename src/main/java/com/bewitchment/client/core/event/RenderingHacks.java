package com.bewitchment.client.core.event;

import com.bewitchment.common.item.block.ItemBlockRevealingLantern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.UUID;

public class RenderingHacks {

	HashMap<UUID, Triple<Float, Float, EnumHand>> map = new HashMap<>();

	@SubscribeEvent
	public void raisePlayerHandWhenHoldingLantern(RenderPlayerEvent.Pre evt) {
		if (evt.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemBlockRevealingLantern
				|| evt.getEntityPlayer().getHeldItemOffhand().getItem() instanceof ItemBlockRevealingLantern) {
			EnumHand raised = (evt.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemBlockRevealingLantern)
					? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
			saveAndRaise(evt.getEntityPlayer(), raised);
		}
	}

	@SubscribeEvent
	public void lowerPlayerHandWhenHoldingLantern(RenderPlayerEvent.Post evt) {
		if (evt.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemBlockRevealingLantern) {
			restore(evt.getEntityPlayer());
		}
	}

	private void saveAndRaise(EntityPlayer p, EnumHand raised) {
		map.put(p.getUniqueID(), new ImmutableTriple<>(p.swingProgress, p.prevSwingProgress, p.swingingHand));
		p.swingProgress = 0.18f;
		p.prevSwingProgress = 0.18f;
		p.swingingHand = raised;
	}

	private void restore(EntityPlayer p) {
		if (map.containsKey(p.getUniqueID())) {
			Triple<Float, Float, EnumHand> t = map.get(p.getUniqueID());
			p.swingProgress = t.getLeft();
			p.prevSwingProgress = t.getMiddle();
			p.swingingHand = t.getRight();
		}
	}

}
