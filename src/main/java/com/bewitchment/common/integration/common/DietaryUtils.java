package com.bewitchment.common.integration.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityRabbit;

/**
 * Created by Joseph on 8/25/2018. Credit to Alexthe666 for some of the code.
 */
public class DietaryUtils {

	public static boolean isVillager(Entity entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof INpc || className.contains("VillagerMCA") || className.contains("MillVillager") || className.contains("Citizen");
	}

	public static boolean isAnimaniaMob(Entity entity) {
		String className = entity.getClass().getCanonicalName().toLowerCase();
		return className.contains("animania");
	}

	public static boolean isAlive(EntityLivingBase entity) {
		if (entity instanceof IDeadMob && ((IDeadMob) entity).isMobDead()) {
			return false;
		}
		return true;
	}

	public static boolean isOwlFodder(Entity entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof EntityRabbit || entity instanceof EntityBat || className.contains("Rat") || className.contains("Hedgehog") || className.contains("Hamster") || className.contains("Squirrel") || className.contains("Hare") || className.contains("Fox") || className.contains("Weasel") || className.contains("Pigeon") || className.contains("Turkey") || className.contains("Mouse");
	}
}
