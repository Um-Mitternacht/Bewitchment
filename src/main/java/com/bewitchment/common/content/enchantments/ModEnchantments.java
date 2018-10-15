package com.bewitchment.common.content.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModEnchantments {
	
	public static Enchantment mpExpansion = new EnchantmentExtraMP();
	public static Enchantment spiritProtection = new EnchantmentSpiritProtection();
	public static Enchantment demonProtection = new EnchantmentDemonProtection();
	public static Enchantment higherWard = new EnchantmentHigherWard();
	public static Enchantment lesserWard = new EnchantmentLesserWard();
	
	@SubscribeEvent
	public static void enchantmentRegistration(RegistryEvent.Register<Enchantment> evt) {
		evt.getRegistry().registerAll(
				mpExpansion, spiritProtection, demonProtection, higherWard, lesserWard
			);
	}
	
}
