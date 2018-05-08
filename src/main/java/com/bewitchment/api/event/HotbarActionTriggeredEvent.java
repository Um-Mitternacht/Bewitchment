package com.bewitchment.api.event;

import javax.annotation.Nullable;

import com.bewitchment.api.hotbar.IHotbarAction;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class HotbarActionTriggeredEvent extends Event {

	public EntityPlayer player;
	public World world;
	public IHotbarAction action;
	public Entity focusedEntity;

	public HotbarActionTriggeredEvent(IHotbarAction action, EntityPlayer player, World world, @Nullable Entity entity) {
		this.player = player;
		this.world = world;
		this.action = action;
		this.focusedEntity = entity;
	}

}
