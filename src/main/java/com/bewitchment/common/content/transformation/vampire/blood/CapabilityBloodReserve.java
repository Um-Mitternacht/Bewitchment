package com.bewitchment.common.content.transformation.vampire.blood;

import com.bewitchment.api.transformation.IBloodReserve;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.UUID;

public class CapabilityBloodReserve implements IBloodReserve {

	@CapabilityInject(IBloodReserve.class)
	public static final Capability<IBloodReserve> CAPABILITY = null;

	int max_blood, blood;
	@Nullable
	UUID lastDrinker;

	public CapabilityBloodReserve() {
		max_blood = 0;
		blood = 0;
		lastDrinker = null;
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(IBloodReserve.class, new BloodReserveStorage(), CapabilityBloodReserve::new);
	}

	/**
	 * Gets the maximum amount of blood for that entity
	 *
	 * @return The maximum amount of blood for the entity, if the entity is something
	 * that shouldn't have blood the max should be set to a negative value
	 */
	@Override
	public int getMaxBlood() {
		return max_blood;
	}

	@Override
	public int getBlood() {
		if (getMaxBlood() >= 0) {
			return blood;
		}
		return 0;
	}

	@Override
	@Nullable
	public String getLastDrinker(World world) {
		if (lastDrinker != null) {
			try {
				return world.getMinecraftServer().getPlayerProfileCache().getProfileByUUID(lastDrinker).getName();
			} catch (NullPointerException e) {
				// There are like a billion things that could be null in that line. I believe
				// this is actually more efficient than checking 4 times for null
			}
		}
		return null;
	}

	@Override
	public UUID getDrinkerUUID() {
		return lastDrinker;
	}

	@Override
	public void setBlood(int amount) {
		blood = amount;
		if (blood > getMaxBlood())
			blood = getMaxBlood();
		if (blood < 0)
			blood = 0;
	}

	@Override
	public void setMaxBlood(int amount) {
		max_blood = amount;
		if (amount <= 0) {
			setBlood(0);
		}
	}

	@Override
	public void setDrinker(UUID uuid) {
		lastDrinker = uuid;
	}
}
