package com.bewitchment.common.content.transformation.vampire;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class AIWatchClosestWrapper extends EntityAIBase {

	private static final Field entityClosest = ReflectionHelper.findField(EntityAIWatchClosest.class, "closestEntity", "field_75334_a", "b");
	private static final Field entitySubject = ReflectionHelper.findField(EntityAIWatchClosest.class, "entity", "field_75332_b", "a");
	private EntityAIWatchClosest wrapped;

	public AIWatchClosestWrapper(EntityAIWatchClosest action) {
		wrapped = action;
		this.setMutexBits(action.getMutexBits());
	}

	@Override
	public boolean shouldExecute() {
		try {
			return wrapped.shouldExecute() && canLookAt((EntityLiving) entitySubject.get(wrapped), (Entity) entityClosest.get(wrapped));
		} catch (Exception e) {
			throw new IllegalStateException("Reflection failed", e);
		}
	}

	private boolean canLookAt(EntityLiving subject, Entity target) {
		return !(target.isSneaking() && (Math.abs(target.getRotationYawHead() - subject.rotationYawHead) < 70 || target.world.getLightFor(EnumSkyBlock.BLOCK, target.getPosition()) < 3));
	}

	@Override
	public boolean shouldContinueExecuting() {
		return wrapped.shouldContinueExecuting();
	}

	@Override
	public boolean isInterruptible() {
		return wrapped.isInterruptible();
	}

	@Override
	public void resetTask() {
		wrapped.resetTask();
	}

	@Override
	public void updateTask() {
		wrapped.updateTask();
	}

	@Override
	public void startExecuting() {
		wrapped.startExecuting();
	}
}
