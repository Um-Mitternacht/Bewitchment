package com.bewitchment.common.world;

import com.bewitchment.common.entity.living.familiar.EntityOwl;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import java.util.Map;

/**
 * Created by Joseph on 8/28/2018.
 */
//Todo: Remove this class. It's probably not needed.
public class EntityPlacementHelper {
	private static final Map<Class<?>, EntityLiving.SpawnPlacementType> ENTITY_PLACEMENTS = Maps.<Class<?>, EntityLiving.SpawnPlacementType>newHashMap();

	static {
		ENTITY_PLACEMENTS.put(EntityOwl.class, EntityLiving.SpawnPlacementType.ON_GROUND);
	}

	public static EntityLiving.SpawnPlacementType getPlacementForEntity(Class<?> entityClass) {
		return ENTITY_PLACEMENTS.getOrDefault(entityClass, EntityLiving.SpawnPlacementType.ON_GROUND);
	}

	public static void setPlacementType(Class<? extends Entity> entityClass, EntityLiving.SpawnPlacementType placementType) {
		ENTITY_PLACEMENTS.putIfAbsent(entityClass, placementType);
	}
}
