package com.bewitchment.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Joseph on 3/6/2019. Credit to AlexThe666 for bits of the code.
 */
public class DemonAITradePlayer extends EntityAIBase {

	private final EntityDemonBase demon;

	public DemonAITradePlayer(EntityDemonBase demon) {
		this.demon = demon;
		this.setMutexBits(5);
	}

	@Override
	public boolean shouldExecute() {
		if (!this.demon.isEntityAlive()) {
			return false;
		} else if (this.demon.isInWater()) {
			return false;
		} else if (!this.demon.onGround) {
			return false;
		} else if (this.demon.velocityChanged) {
			return false;
		} else {
			EntityPlayer entityplayer = this.demon.getCustomer();

			if (entityplayer == null) {
				return false;
			} else if (this.demon.getDistanceSq(entityplayer) > 16.0D) {
				return false;
			} else {
				return entityplayer.openContainer != null;
			}
		}
	}

	public void updateTask() {
		this.demon.getNavigator().clearPath();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		this.demon.setCustomer((EntityPlayer) null);
	}
}
