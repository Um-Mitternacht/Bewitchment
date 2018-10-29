package com.bewitchment.common.content.transformation.werewolf;

import com.bewitchment.common.core.capability.simple.SimpleCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityWerewolfStatus extends SimpleCapability {

	@CapabilityInject(CapabilityWerewolfStatus.class)
	public static final Capability<CapabilityWerewolfStatus> CAPABILITY = null;
	public static final CapabilityWerewolfStatus DEFAULT_INSTANCE = new CapabilityWerewolfStatus();

	public byte currentForm = 0; // 0 = none, 1 = wolf, 2 = wolfman
	public int texture = 0;

	@Override
	public boolean isRelevantFor(Entity object) {
		return object instanceof EntityPlayer;
	}

	@Override
	public SimpleCapability getNewInstance() {
		return new CapabilityWerewolfStatus();
	}

}
