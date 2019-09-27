package com.bewitchment.common.entity.util;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("EntityConstructor")
public abstract class ModEntityMob extends EntityMob {
	public static final DataParameter<Integer> SKIN = EntityDataManager.createKey(ModEntityMob.class, DataSerializers.VARINT);
	
	private final ResourceLocation lootTableLocation;
	
	protected ModEntityMob(World world, ResourceLocation lootTableLocation) {
		super(world);
		this.lootTableLocation = lootTableLocation;
	}
	
	protected int getSkinTypes() {
		return 1;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SKIN, 0);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("skin", dataManager.get(SKIN));
		dataManager.setDirty(SKIN);
		super.writeEntityToNBT(tag);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		dataManager.set(SKIN, tag.getInteger("skin"));
		super.readEntityFromNBT(tag);
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return lootTableLocation;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		dataManager.set(SKIN, rand.nextInt(getSkinTypes()));
		return super.onInitialSpawn(difficulty, data);
	}
	
	@Override
	protected abstract boolean isValidLightLevel();
}