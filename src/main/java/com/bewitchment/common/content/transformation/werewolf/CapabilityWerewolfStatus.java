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

	public int currentWWForm = 0; // 0 = none, 1 = wolf, 2 = wolfman
	public int texture = 0;
	public boolean nightVision = false;

	public void setNightVision(boolean b) {
		boolean changed = false;
		if (nightVision != b) {
			changed = true;
		}
		nightVision = b;
		if (changed) {
			markDirty((byte) 1);
		}
	}

	public void changeForm(boolean backwards) {
		if (backwards) {
			if (currentWWForm == 0) {
				currentWWForm = 2;
			} else {
				currentWWForm = (currentWWForm - 1);
			}
		} else {
			currentWWForm = (currentWWForm + 1) % 3;
		}
		markDirty((byte) 2);
	}

	@Override
	public boolean isRelevantFor(Entity object) {
		return object instanceof EntityPlayer;
	}

	@Override
	public SimpleCapability getNewInstance() {
		return new CapabilityWerewolfStatus();
	}

}
