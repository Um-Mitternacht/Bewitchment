package com.bewitchment.api.event;

import com.bewitchment.api.capability.transformations.EnumTransformationType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class TransformationModifiedEvent extends PlayerEvent {

	public int level;
	public EnumTransformationType type;

	public TransformationModifiedEvent(EntityPlayer player, EnumTransformationType type, int level) {
		super(player);
		this.level = level;
		this.type = type;
	}

}
