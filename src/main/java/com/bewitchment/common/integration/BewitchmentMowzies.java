package com.bewitchment.common.integration;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Brew;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.potion.PotionHandler;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BewitchmentMowzies {
	@SubscribeEvent
	public void registerBrew(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "poison_resistance"), Util.get(ItemHandler.NAGA_FANG), new PotionEffect(PotionHandler.POISON_RESIST, (600))));
	}
}
