package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.capability.IEnergy;
import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import net.minecraft.entity.player.EntityPlayerMP;

public class DummyIEnergy implements IEnergy {

	public static final IEnergy INSTANCE = new DummyIEnergy();

	private DummyIEnergy() {
	}

	@Override
	public boolean set(int amount) {
		return false;
	}

	@Override
	public int get() {
		return 0;
	}

	@Override
	public int getMax() {
		return 1;
	}

	@Override
	public void setMax(int max) {
	}

	@Override
	public int getRegenTime() {
		return 0;
	}

	@Override
	public int getRegenBurst() {
		return 0;
	}

	@Override
	public void setRegen(int rateInSeconds, int burst) {
	}

	@Override
	public int getUses() {
		return 0;
	}

	@Override
	public void setUses(int uses) {
	}

	@Override
	public int tick() {
		return 0;
	}

	@Override
	public int tickProgress() {
		return 0;
	}

	@Override
	public void tickReset() {
	}

	@Override
	public void syncTo(EntityPlayerMP target) {
	}

	@Override
	public IInfusion getType() {
		return DefaultInfusions.NONE;
	}

	@Override
	public void setType(IInfusion type) {
	}

}
