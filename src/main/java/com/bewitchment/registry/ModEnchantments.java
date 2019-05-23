package com.bewitchment.registry;

import com.bewitchment.common.enchantment.EnchantmentMagicProtection;
import com.bewitchment.common.enchantment.EnchantmentSpiritProtection;

@SuppressWarnings("unused")
public class ModEnchantments {
	public static final EnchantmentMagicProtection magic_protection = new EnchantmentMagicProtection();
	public static final EnchantmentSpiritProtection spirit_protection = new EnchantmentSpiritProtection();
	
	public static void preInit() {
	}
}