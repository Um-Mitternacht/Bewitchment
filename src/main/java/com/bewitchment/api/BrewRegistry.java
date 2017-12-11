package com.bewitchment.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.IBrew;
import net.minecraft.item.Item;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Set;

/**
 * This class was created by Arekkuusu on 22/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class BrewRegistry {

	private static final BiMap<ResourceLocation, IBrew> LOCATION_BREWS = HashBiMap.create(256);
	private static final IntIdentityHashBiMap<IBrew> BREWS = new IntIdentityHashBiMap<>(256);
	private static final Map<Brew, Map<IBrew, BrewEffect>> DEFAULTS = Maps.newHashMap();

	static {
		DEFAULTS.put(Brew.DRINK, Maps.newLinkedHashMap());
		DEFAULTS.put(Brew.SPLASH, Maps.newLinkedHashMap());
		DEFAULTS.put(Brew.LINGER, Maps.newLinkedHashMap());
	}

	private BrewRegistry() {
	}

	public static IBrew register(ResourceLocation location, IBrew brew) {
		if (LOCATION_BREWS.containsKey(location))
			throw new IllegalArgumentException("This Brew has been registered twice!: " + location);
		LOCATION_BREWS.put(location, brew);
		BREWS.add(brew);
		return brew;
	}

	public static void setDefault(Brew brew, BrewEffect effect) {
		DEFAULTS.get(brew).put(effect.getBrew(), effect);
	}

	public static boolean hasDefault(IBrew brew) {
		for (Brew enu : Brew.values()) {
			if (DEFAULTS.get(enu).containsKey(brew)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasDefault(Brew enu, IBrew brew) {
		return DEFAULTS.get(enu).containsKey(brew);
	}

	public static BrewEffect getDefault(IBrew brew) {
		for (Brew enu : Brew.values()) {
			if (DEFAULTS.get(enu).containsKey(brew)) {
				return DEFAULTS.get(enu).get(brew);
			}
		}
		return new BrewEffect(brew, 0, 0);
	}

	public static BrewEffect getDefault(Brew enu, IBrew brew) {
		return DEFAULTS.get(enu).get(brew);
	}

	public static int getBrewId(IBrew brew) {
		return BREWS.getId(brew);
	}

	public static IBrew getBrew(int id) {
		return BREWS.get(id);
	}

	public static ResourceLocation getBrewResource(IBrew brew) {
		return LOCATION_BREWS.inverse().get(brew);
	}

	public static IBrew getRegisteredBrew(String location) {
		return getRegisteredBrew(new ResourceLocation(location));
	}

	public static IBrew getRegisteredBrew(ResourceLocation location) {
		return LOCATION_BREWS.get(location);
	}

	public static Set<IBrew> getBrews() {
		return LOCATION_BREWS.values();
	}

	public static Map<Brew, Map<IBrew, BrewEffect>> getDefaults() {
		return DEFAULTS;
	}

	public enum Brew {
		DRINK("brew_phial_drink"),
		SPLASH("brew_phial_splash"),
		LINGER("brew_phial_linger");

		private final String id;
		private Item item;

		Brew(String id) {
			this.id = id;
		}

		public static Brew byOrdinal(int ordinal) {
			return values()[ordinal];
		}

		public Item getItem() {
			return item != null ? item : (item = Item.REGISTRY.getObject(new ResourceLocation("bewitchment", id)));
		}
	}
}
