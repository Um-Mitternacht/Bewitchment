package com.bewitchment.common.potion;

import com.bewitchment.common.potion.potions.PotionBloodDrained;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModPotions {

	public static Potion bloodDrained;

	private ModPotions() {
	}

	public static void init() {
		bloodDrained = new PotionBloodDrained();
		ForgeRegistries.POTIONS.register(bloodDrained);
	}

}
