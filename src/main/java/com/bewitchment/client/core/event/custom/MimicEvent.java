package com.bewitchment.client.core.event.custom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.UUID;

public class MimicEvent extends PlayerEvent {
	private UUID victimID;
	private String victimName;
	private boolean reverting;

	public MimicEvent(EntityPlayer player, UUID victimID, String victimName, boolean reverting) {
		super(player);
		this.victimID = victimID;
		this.victimName = victimName;
		this.reverting = reverting;
	}

	public UUID getVictimID() {
		return victimID;
	}

	public String getVictimName() {
		return victimName;
	}

	public boolean isReverting() {
		return reverting;
	}
}
