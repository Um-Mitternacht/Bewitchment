package com.bewitchment.api.event;

import com.bewitchment.api.hotbar.IHotbarAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class HotbarActionTriggeredEvent extends Event {

	public EntityPlayer player;
	public World world;
	public IHotbarAction action;

	public HotbarActionTriggeredEvent(IHotbarAction action, EntityPlayer player, World world) {
		this.player = player;
		this.world = world;
		this.action = action;
	}

}
