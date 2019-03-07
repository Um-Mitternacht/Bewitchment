package com.bewitchment.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;

public class LargeEntityAIAttackMelee extends EntityAIAttackMelee {

	public LargeEntityAIAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
		super(creature, speedIn, useLongMemory);
	}

	@Override
	protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
		double d0 = this.getAttackReachSqr(enemy);

		if (this.attackTick <= 0 && distToEnemySqr <= d0) {

			// call block raytrace from attacker's eyes, confirm hit if not looking at block
			double distance = Math.sqrt(distToEnemySqr);
			Vec3d vec3d = this.attacker.getPositionEyes(1.0f);
			Vec3d vec3d1 = this.attacker.getLook(1.0f);
			Vec3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
			RayTraceResult trace = this.attacker.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);

			if (trace != null
					&& trace.typeOfHit == Type.MISS) {
				this.attackTick = 20;
				this.attacker.swingArm(EnumHand.MAIN_HAND);
				this.attacker.attackEntityAsMob(enemy);
			}
		}
	}
}
