package com.bewitchment.common.core.event;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class LootTableEventHandler {

    // Adds mod saplings to dungeon chest loot tables - temporary method of obtaining - requested by zabi94 in Github issue #54
    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {

        if (evt.getName().toString().equals("minecraft:chests/simple_dungeon")) {

            evt.getTable().addPool(
                new LootPool(
                    new LootEntry[] {
                        new LootEntryTable(
                            new ResourceLocation(LibMod.MOD_ID + ":chests/saplings"),
                            5,
                            0,
                            new LootCondition[0],
                            "bewitchment_saplings_entry"
                        )
                    },
                    new LootCondition[0],
                    new RandomValueRange( 3 ),
                    new RandomValueRange( 0 , 1 ),
                    "bewitchment_saplings_pool"
                )
            );

        }
    }

}
