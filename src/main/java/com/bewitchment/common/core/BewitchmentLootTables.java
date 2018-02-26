package com.bewitchment.common.core;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * Created by Joseph on 2/26/2018.
 */
public class BewitchmentLootTables {

	//Fortunes and chests
	public static final ResourceLocation JEWELS = new ResourceLocation(LibMod.MOD_ID + ":" + "chests/jewels");

	public static void preInit() {
		registerLootTables();
	}

	public static void registerLootTables() {
		LootTableList.register(JEWELS);
	}
}
