package com.bewitchment.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * This class was created by Arekkuusu on 20/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class RayTraceHelper {

	private RayTraceHelper() {
	}

	public static RayTraceResult rayTraceResult(Entity source, Vec3d vec3, boolean includeEntities, boolean excludeSource) {
		double d0 = source.posX;
		double d1 = source.posY;
		double d2 = source.posZ;
		World world = source.world;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		Vec3d forward = vec3d.add(vec3);
		RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, forward, false, true, false);

		if (includeEntities) {
			if (raytraceresult != null) {
				forward = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}

			Entity entity = null;
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(source, source.getEntityBoundingBox().expand(vec3.x, vec3.y, vec3.z).grow(1.0D));
			double d6 = 0.0D;

			for (Entity ent : list) {

				if (ent.canBeCollidedWith() && (excludeSource || !ent.isEntityEqual(source)) && !ent.noClip) {
					AxisAlignedBB axisalignedbb = ent.getEntityBoundingBox().grow(0.30000001192092896D);
					RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, forward);

					if (raytraceresult1 != null) {
						double d7 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

						if (d7 < d6 || d6 == 0.0D) {
							entity = ent;
							d6 = d7;
						}
					}
				}
			}

			if (entity != null) {
				raytraceresult = new RayTraceResult(entity);
			}
		}

		return raytraceresult;
	}

	public static Vec3d fromMotion(Entity entity) {
		return new Vec3d(entity.motionX, entity.motionY, entity.motionZ);
	}

	public static Vec3d fromVecRange(Vec3d look, double range) {
		return new Vec3d(look.x * range, look.y * range, look.z * range);
	}

	public static Vec3d fromLookVec(Entity entity, double range) {
		Vec3d look = entity.getLookVec();
		return new Vec3d(look.x * range, entity.getEyeHeight() + look.y * range, look.z * range);
	}
}
