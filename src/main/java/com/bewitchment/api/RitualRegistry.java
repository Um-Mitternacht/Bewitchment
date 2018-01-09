package com.bewitchment.api;

import com.bewitchment.api.cauldron_ritual.ICauldronRitual;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
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

	private static final BiMap<ResourceLocation, ICauldronRitual> LOCATION_RITUALS = HashBiMap.create(256);
	private static final IntIdentityHashBiMap<ICauldronRitual> RITUALS = new IntIdentityHashBiMap<>(256);

	public static <T extends TileEntity, R extends ICauldronRitual<T>> ICauldronRitual<T> register(ResourceLocation location, R ritual) {
		if (LOCATION_RITUALS.containsKey(location))
			throw new IllegalArgumentException("This Ritual has been registered twice!: " + location);
		LOCATION_RITUALS.put(location, ritual);
		RITUALS.add(ritual);
		return ritual;
	}

	public static int getRitualId(ICauldronRitual brew) {
		return RITUALS.getId(brew);
	}

	public static ICauldronRitual getRitual(int id) {
		return RITUALS.get(id);
	}

	public static ResourceLocation getRitualResource(ICauldronRitual brew) {
		return LOCATION_RITUALS.inverse().get(brew);
	}

	public static ICauldronRitual getRegisteredRitual(String location) {
		return getRegisteredRitual(new ResourceLocation(location));
	}

	public static ICauldronRitual getRegisteredRitual(ResourceLocation location) {
		return LOCATION_RITUALS.get(location);
	}

	public static Set<ICauldronRitual> getRituals() {
		return LOCATION_RITUALS.values();
	}
}
