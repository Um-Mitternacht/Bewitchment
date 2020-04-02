package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 4/2/2020.
 */

//Todo: Everything.
public class PotionCorrosion extends ModPotion {
	protected PotionCorrosion(String name, boolean isNegative, int color) {
		super("corrosion", true, 0x0BDA51);
	}
	
	@SubscribeEvent
	public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this)) {
			event.getEntityLiving().getTotalArmorValue();
		}
	}
}
