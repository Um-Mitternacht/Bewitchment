package com.bewitchment.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;

public class HotbarActionCollectionEvent extends Event {

	public EntityPlayer player;
	public World world;
	private List<HotbarAction> list = new ArrayList<HotbarAction>();

	public HotbarActionCollectionEvent(EntityPlayer player, World world) {
		this.player = player;
		this.world = world;
	}

	public List<HotbarAction> getList() {
		return list;
	}

}
