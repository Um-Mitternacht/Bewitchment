package com.bewitchment.common.entity.living;

/**
 * Created by Joseph on 4/28/2019.
 */

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityMultiSkinMonster extends EntityMob {
	private static final DataParameter<Integer> SKIN = EntityDataManager.createKey(EntityMultiSkinMonster.class, DataSerializers.VARINT);

	public EntityMultiSkinMonster(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SKIN, 0);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setEntitySkinIndex(rand.nextInt(getSkinTypes()));
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@SideOnly(Side.CLIENT)
	public int getSkinIndex() {
		return dataManager.get(SKIN);
	}

	public void setEntitySkinIndex(int type) {
		if (type >= getSkinTypes()) {
			throw new IllegalArgumentException(String.format("Skin of index %d doesn't exist for %s", type, this.getClass().getName()));
		}
		dataManager.set(SKIN, type);
		dataManager.setDirty(SKIN);
	}

	public abstract int getSkinTypes();

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("skin", dataManager.get(SKIN));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		dataManager.set(SKIN, compound.getInteger("skin"));
		dataManager.setDirty(SKIN);
	}

}