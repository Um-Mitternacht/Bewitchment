package com.bewitchment.api.capability;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.world.World;

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
