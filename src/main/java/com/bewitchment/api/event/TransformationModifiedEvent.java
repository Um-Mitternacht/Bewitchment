package com.bewitchment.api.event;

import com.bewitchment.api.transformation.ITransformation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class TransformationModifiedEvent extends PlayerEvent {

	public int level;
	public ITransformation type;

	public TransformationModifiedEvent(EntityPlayer player, ITransformation type, int level) {
		super(player);
		this.level = level;
		this.type = type;
	}

}
