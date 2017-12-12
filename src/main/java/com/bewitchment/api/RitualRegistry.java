package com.bewitchment.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.bewitchment.api.ritual.IRitual;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

/**
 * This class was created by Arekkuusu on 06/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("rawtypes")
public class RitualRegistry {

	private static final BiMap<ResourceLocation, IRitual> LOCATION_RITUALS = HashBiMap.create(256);
	private static final IntIdentityHashBiMap<IRitual> RITUALS = new IntIdentityHashBiMap<>(256);

	public static <T extends TileEntity, R extends IRitual<T>> IRitual<T> register(ResourceLocation location, R ritual) {
		if (LOCATION_RITUALS.containsKey(location))
			throw new IllegalArgumentException("This Ritual has been registered twice!: " + location);
		LOCATION_RITUALS.put(location, ritual);
		RITUALS.add(ritual);
		return ritual;
	}

	public static int getRitualId(IRitual brew) {
		return RITUALS.getId(brew);
	}

	public static IRitual getRitual(int id) {
		return RITUALS.get(id);
	}

	public static ResourceLocation getRitualResource(IRitual brew) {
		return LOCATION_RITUALS.inverse().get(brew);
	}

	public static IRitual getRegisteredRitual(String location) {
		return getRegisteredRitual(new ResourceLocation(location));
	}

	public static IRitual getRegisteredRitual(ResourceLocation location) {
		return LOCATION_RITUALS.get(location);
	}

	public static Set<IRitual> getRituals() {
		return LOCATION_RITUALS.values();
	}
}
