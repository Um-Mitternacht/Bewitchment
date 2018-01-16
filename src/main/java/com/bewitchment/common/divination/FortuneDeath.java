package com.bewitchment.common.divination;

import com.bewitchment.api.divination.Fortune;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Created by Joseph on 1/16/2018.
 */
public class FortuneDeath extends Fortune {

	public FortuneDeath(int weight, String name, String modid) {
		super(weight, name, modid);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean canBeUsedFor(@Nonnull EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(@Nonnull EntityPlayer player) {
		return player.getRNG().nextDouble() < 0.0005d;
	}

	@Override
	public boolean apply(@Nonnull EntityPlayer player) {
		player.getCapability(CapabilityDivination.CAPABILITY, null).setActive();
		return false;
	}

	@SubscribeEvent
	public void onArrowNock(ArrowNockEvent evt) {
		if (evt.getEntityPlayer() != null && !evt.getEntityPlayer().isCreative() && evt.getHand() == EnumHand.MAIN_HAND) { // Needs to check for mainhand due to how the event works
			CapabilityDivination cap = evt.getEntityPlayer().getCapability(CapabilityDivination.CAPABILITY, null);
			if (cap.getFortune() == this && cap.isActive()) {
				if (evt.getEntityPlayer().dropItem(true) != null) {
					cap.setRemovable();
				}
			}
		}
	}
}
