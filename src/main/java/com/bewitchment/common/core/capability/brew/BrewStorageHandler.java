package com.bewitchment.common.core.capability.brew;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.capability.IBrewStorage;
import net.minecraft.entity.EntityLivingBase;

import java.util.*;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class BrewStorageHandler {

	public static final Map<EntityLivingBase, Set<IBrew>> BREW_REMOVAL = Maps.newHashMap();

	private BrewStorageHandler() {
	}

	@SuppressWarnings("ConstantConditions")
	public static Map<IBrew, BrewEffect> getBrewMap(EntityLivingBase entity) {
		if (entity.hasCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null)) {
			IBrewStorage storage = entity.getCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null);
			return storage.getBrewMap();
		}
		return Collections.emptyMap();
	}

	@SuppressWarnings("ConstantConditions")
	public static Collection<BrewEffect> getBrewEffects(EntityLivingBase entity) {
		if (entity.hasCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null)) {
			IBrewStorage storage = entity.getCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null);
			return storage.getBrewEffects();
		}
		return Collections.emptyList();
	}

	public static void removeActiveBrew(EntityLivingBase entity, IBrew brew) {
		if (isBrewActive(entity, brew)) {
			if (BREW_REMOVAL.containsKey(entity)) {
				BREW_REMOVAL.get(entity).add(brew);
			} else {
				BREW_REMOVAL.put(entity, Sets.newHashSet(brew));
			}
		}
	}

	/**
	 * Checks if a Brew is active.
	 *
	 * @param entity The entity
	 * @param brew   The brew
	 * @return If it is active
	 */
	@SuppressWarnings("ConstantConditions")
	public static boolean isBrewActive(EntityLivingBase entity, IBrew brew) {
		if (entity.hasCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null)) {
			IBrewStorage storage = entity.getCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null);
			return storage.getBrews().contains(brew);
		}
		return false;
	}

	/**
	 * Add a BrewEffect to the entity.
	 *
	 * @param entity The entity
	 * @param effect The effect
	 */
	public static void addEntityBrewEffect(EntityLivingBase entity, BrewEffect effect) {
		Optional<IBrewStorage> optional = BrewStorageHandler.getBrewStorage(entity);
		if (optional.isPresent()) {
			IBrewStorage storage = optional.get();

			BrewEffect active = storage.getBrew(effect.getBrew());
			if (active == null || effect.getDuration() > active.getDuration()) {
				storage.addBrew(entity, effect);
			}
		}
	}

	/**
	 * Returns the {@link IBrewStorage} interface of the brew storage.
	 *
	 * @param entity The entity
	 * @return An {@link Optional <IBrewStorage>} for correctness
	 */
	@SuppressWarnings("ConstantConditions")
	public static Optional<IBrewStorage> getBrewStorage(EntityLivingBase entity) {
		if (entity.hasCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null)) {
			return Optional.of(entity.getCapability(BrewStorageProvider.BREW_STORAGE_CAPABILITY, null));
		}
		return Optional.empty();
	}
}
