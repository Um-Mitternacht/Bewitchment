package com.bewitchment.common.content.transformation.vampire;

import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.capability.simple.SimpleCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityVampire extends SimpleCapability {

	@CapabilityInject(CapabilityVampire.class)
	public static final Capability<CapabilityVampire> CAPABILITY = null;
	public static final CapabilityVampire DEFAULT_INSTANCE = new CapabilityVampire();

	public int blood = 0;
	public boolean nightVision = false;

	@Override
	public boolean isRelevantFor(Entity object) {
		return object instanceof EntityPlayer;
	}

	@Override
	public SimpleCapability getNewInstance() {
		return new CapabilityVampire();
	}

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

	public boolean addVampireBlood(int amount, EntityPlayer p) {
		if (getBlood() >= getMaxBlood(p) && amount > 0)
			return false;
		if (amount + getBlood() < 0)
			return false;
		blood += amount;
		markDirty((byte) 2);
		return true;
	}


	public int getBlood() {
		return blood;
	}

	public int getMaxBlood(EntityPlayer p) {
		int max = 50 + 155 * p.getCapability(CapabilityTransformation.CAPABILITY, null).getLevel();
		if (getBlood() > max) {
			setBlood(max);
		}
		return max; // lvl 0: 50, lvl 5: 825, lvl 10: 1600
	}

	public void setBlood(int blood) {
		this.blood = blood;
		markDirty((byte) 2);
	}

}
