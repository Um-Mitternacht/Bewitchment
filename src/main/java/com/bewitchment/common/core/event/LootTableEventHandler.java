package com.bewitchment.common.core.event;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTableEventHandler {

	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent evt) {
		if (evt.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
			evt.getTable().addPool(
					new LootPool(new LootEntry[]{
							new LootEntryTable(
									new ResourceLocation(LibMod.MOD_ID, "chests/saplings"), 5, 0, new LootCondition[0], "bewitchment_saplings_entry"
							)
					}, new LootCondition[0], new RandomValueRange(3), new RandomValueRange(0, 1), "bewitchment_saplings_pool")
			);
		}

		if (evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)) {
			//Here in my garage, just bought this new Lamborghini here. It’s fun to drive up here in the Hollywood hills.
			// But you know what I like more than materialistic things? Knowledge.
			evt.getTable().addPool(new LootPool(new LootEntry[]{
					new LootEntryTable(
							new ResourceLocation(LibMod.MOD_ID, "chests/knowledge"), 5, 0, new LootCondition[0], "bewitchment_knowledge_entry"
					)
			}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_knowledge_pool"));
		}

		if (evt.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON) ||
				evt.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT) ||
				evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) ||
				evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) ||
				evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE) ||
				evt.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION)
				) { //Not sure if this is messy or better looking than the alternative.
			evt.getTable().addPool(new LootPool(new LootEntry[]{
					new LootEntryTable(
							new ResourceLocation(LibMod.MOD_ID, "chests/jewels"), 5, 0, new LootCondition[0], "bewitchment_jewels_entry"
					)
			}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_jewels_pool"));
		}
	}
}
