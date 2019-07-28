package com.bewitchment.common.events;

import com.bewitchment.Bewitchment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 7/28/2019.
 */
public class LootTableEventHandler {
	
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent evt) {
		
		if (evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)) {
			evt.getTable().addPool(new LootPool(new LootEntry[]{
					new LootEntryTable(
							new ResourceLocation(Bewitchment.MODID, "chests/books"), 5, 0, new LootCondition[0], "bewitchment_books_entry"
					)
			}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_books_pool"));
		}
		
		if (evt.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON) ||
				evt.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT) ||
				evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) ||
				evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) ||
				evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE) ||
				evt.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH) ||
				evt.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE) ||
				evt.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID) ||
				evt.getName().equals(LootTableList.CHESTS_IGLOO_CHEST) ||
				evt.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE) ||
				evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY) ||
				evt.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION)
				) { //Not sure if this is messy or better looking than the alternative.
			evt.getTable().addPool(new LootPool(new LootEntry[]{
					new LootEntryTable(
							new ResourceLocation(Bewitchment.MODID, "chests/materials"), 5, 0, new LootCondition[0], "bewitchment_materials_entry"
					)
			}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_materials_pool"));
		}
	}
}
