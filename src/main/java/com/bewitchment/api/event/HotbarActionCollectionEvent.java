package com.bewitchment.api.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class HotbarActionCollectionEvent extends Event {
	
	private List<HotbarAction> list = new ArrayList<HotbarAction>();
	
	public EntityPlayer player;
	public World world;
	
	public HotbarActionCollectionEvent(EntityPlayer player, World world) {
		this.player = player;
		this.world = world;
	}
	
	public List<HotbarAction> getList() {
		return list;
	}
	
}
