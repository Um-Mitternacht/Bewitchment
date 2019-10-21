package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Joseph on 10/21/2019.
 */
public class EntityShadowPersonStandard extends ModEntityMob {
	
	private int livingTimer = 0;
	
	
	public EntityShadowPersonStandard(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/shadow_person"));
		setSize(1, 0.85f);
		isImmuneToFire = true;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		livingTimer = 600;
		return super.onInitialSpawn(difficulty, data);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		livingTimer--;
		if (livingTimer <= 0) setDead();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("livingTimer", livingTimer);
		super.writeEntityToNBT(tag);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		if (tag.hasKey("livingTimer")) livingTimer = tag.getInteger("livingTimer");
		super.readEntityFromNBT(tag);
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return false;
	}
}
