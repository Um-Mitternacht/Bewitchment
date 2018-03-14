package com.bewitchment.api.event;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.api.hotbar.IHotbarAction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class HotbarActionCollectionEvent extends Event {

	public EntityPlayer player;
	public World world;
	private List<IHotbarAction> list = new ArrayList<IHotbarAction>();

	public HotbarActionCollectionEvent(EntityPlayer player, World world) {
		this.player = player;
		this.world = world;
	}

	public List<IHotbarAction> getList() {
		return list;
	}

}
