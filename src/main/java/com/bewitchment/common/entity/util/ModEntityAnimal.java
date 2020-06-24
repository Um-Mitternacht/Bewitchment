package com.bewitchment.common.entity.util;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;

@SuppressWarnings({"NullableProblems", "ConstantConditions", "EntityConstructor"})
public abstract class ModEntityAnimal extends EntityAnimal {
	public static final DataParameter<Integer> SKIN = EntityDataManager.createKey(ModEntityAnimal.class, DataSerializers.VARINT);

	private final ResourceLocation lootTableLocation;

	protected ModEntityAnimal(World world, ResourceLocation lootTableLocation) {
		super(world);
		this.lootTableLocation = lootTableLocation;
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
	public EntityAgeable createChild(EntityAgeable other) {
		EntityAgeable entity = (EntityAgeable) EntityRegistry.getEntry(getClass()).newInstance(world);
		entity.getDataManager().set(SKIN, rand.nextBoolean() ? dataManager.get(SKIN) : other.getDataManager().get(SKIN));
		return entity;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SKIN, 0);
	}

	protected int getSkinTypes() {
		return 1;
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
	public boolean canMateWith(EntityAnimal other) {
		if (other == this || !(other.getClass().getName().equals(getClass().getName()))) return false;
		return isInLove() && other.isInLove();
	}
}