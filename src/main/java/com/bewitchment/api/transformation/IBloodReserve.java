package com.bewitchment.api.transformation;

import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IBloodReserve {
	int getMaxBlood();

	int getBlood();

	@Nullable
	String getLastDrinker(World world);

	UUID getDrinkerUUID();

	void setBlood(int integer);

	void setMaxBlood(int integer);

	void setDrinker(UUID uuid);

	default float getPercentFilled() {
		return (float) getBlood() / (float) getMaxBlood();
	}
}
