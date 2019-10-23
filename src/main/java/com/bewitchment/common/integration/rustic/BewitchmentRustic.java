package com.bewitchment.common.integration.rustic;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Brew;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rustic.common.items.ModItems;
import rustic.common.potions.PotionsRustic;

public class BewitchmentRustic {
	@SubscribeEvent
	public void registerBrew(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blazing_trail"), Util.get(ModItems.CHILI_PEPPER), new PotionEffect(PotionsRustic.BLAZING_TRAIL_POTION, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "iron_skin"), Util.get(ModItems.IRONBERRIES), new PotionEffect(PotionsRustic.IRON_SKIN_POTION, (20 * 30))));
	}
}
