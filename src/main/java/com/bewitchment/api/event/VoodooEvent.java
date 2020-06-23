package com.bewitchment.api.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class VoodooEvent extends Event {
	private final EntityLivingBase target;
	private final EntityPlayer caster;
	
	public VoodooEvent(EntityPlayer caster, EntityLivingBase target) {
		this.caster = caster;
		this.target = target;
	}
	
	public EntityLivingBase getTarget() {
		return target;
	}
	
	public EntityPlayer getCaster() {
		return caster;
	}
}
