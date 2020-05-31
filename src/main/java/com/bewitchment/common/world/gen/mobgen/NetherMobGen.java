package com.bewitchment.common.world.gen.mobgen;

import com.bewitchment.common.entity.spirit.demon.EntityBafometyr;
import com.bewitchment.common.entity.spirit.demon.EntityCleaver;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 5/31/2020.
 */

//Credit to this thread on the minecraft forge forums on getting it right: https://www.minecraftforge.net/forum/topic/83725-solved1152-updating-the-spawn-list-in-the-nether-fortress/
public class NetherMobGen {
	
	Biome.SpawnListEntry cleaverSpawn = new Biome.SpawnListEntry(EntityCleaver.class, 8, 0, 3);
	Biome.SpawnListEntry bafometyrSpawn = new Biome.SpawnListEntry(EntityBafometyr.class, 8, 0, 3);
	
	@SubscribeEvent
	public void registerNetherWorldSpawn(WorldEvent.PotentialSpawns event) {
		if(event.getType() == EnumCreatureType.MONSTER) {
			if (InitMapGenEvent.EventType.NETHER_BRIDGE.isPositionInsideStructure(event.getWorld(), event.getPos())) {
				event.getList().add(cleaverSpawn);
				event.getList().add(bafometyrSpawn);
			}
			
			if (InitMapGenEvent.EventType.NETHER_BRIDGE.isPositionInStructure(event.getWorld(), event.getPos()) && event.getWorld().getBlockState(event.getPos().down()).getBlock() == Blocks.NETHER_BRICK) {
				event.getList().add(cleaverSpawn);
				event.getList().add(bafometyrSpawn);
			}
		}
	}
}
