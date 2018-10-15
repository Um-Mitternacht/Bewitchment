package com.bewitchment.common.content.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModEnchantments {

	public static Enchantment mpExpansion = new EnchantmentExtraMP();
	public static Enchantment spiritProtection = new EnchantmentSpiritProtection();
	public static Enchantment demonProtection = new EnchantmentDemonProtection();
	public static Enchantment potentWard = new EnchantmentPotentWard();
	public static Enchantment desperateWard = new EnchantmentDesperateWard();

	//Placeholders
	public static Enchantment hexProtection = new EnchantmentPlaceholder("hex_protection", Rarity.UNCOMMON, 3);
	public static Enchantment occultation = new EnchantmentPlaceholder("occultation", Rarity.UNCOMMON, 3);
	public static Enchantment taglockProtection = new EnchantmentPlaceholder("taglock_protection", Rarity.RARE, 3);

	@SubscribeEvent
	public static void enchantmentRegistration(RegistryEvent.Register<Enchantment> evt) {
		evt.getRegistry().registerAll(
				mpExpansion, spiritProtection, demonProtection, potentWard, desperateWard,

				//Placeholders
				hexProtection, occultation, taglockProtection
		);
	}

}
