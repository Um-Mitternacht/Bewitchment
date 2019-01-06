package com.bewitchment.api.familiars;

import com.bewitchment.common.core.helper.PlayerHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 12/14/2018. Original code by Zabi94.
 */

public abstract class EntityFamiliar extends EntityTameable {

	private static final float REDIRECTED_DAMAGE = 0.1f;
	private static final DamageSource FAMILIAR_LINK = new DamageSource("familiar_link").setMagicDamage();
	private static final DataParameter<Boolean> FAMILIAR_STATUS = EntityDataManager.createKey(EntityFamiliar.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> FAMILIAR_TYPE = EntityDataManager.createKey(EntityFamiliar.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SKIN = EntityDataManager.createKey(EntityFamiliar.class, DataSerializers.VARINT);

	public EntityFamiliar(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(FAMILIAR_STATUS, false);
		this.dataManager.register(FAMILIAR_TYPE, 0);
		this.dataManager.register(SKIN, 0);
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		if (getOwner() != null && this.isFamiliar() && getOwner().getDistanceSq(this) < 256 && damageSrc.getImmediateSource() != this) {
			getOwner().attackEntityFrom(DamageSource.causeIndirectMagicDamage(damageSrc.getTrueSource(), this), REDIRECTED_DAMAGE * damageAmount);
			damageAmount *= 1 - REDIRECTED_DAMAGE;
		}
		super.damageEntity(damageSrc, damageAmount);
		if (isFamiliar() && this.getHealth() == 0 && !world.isRemote) {
			this.setHealth(1);
			EntityPlayer p = getOwnerAcrossWorlds();
			if (p != null) {
				if (p.getEntityWorld().provider.getDimension() != world.provider.getDimension()) {
					this.changeDimension(p.getEntityWorld().provider.getDimension());
				}
				BlockPos dest = p.getBedLocation(p.getEntityWorld().provider.getDimension());
				if (dest == null) {
					dest = p.getPosition().up();
				}
				this.setPositionAndUpdate(dest.getX(), dest.getY(), dest.getZ());
				p.attackEntityFrom(FAMILIAR_LINK, Integer.MAX_VALUE);
			} else {
				if (world.provider.getDimension() != 0) {
					this.changeDimension(0);
				}
				this.setPositionAndUpdate(world.getSpawnPoint().getX(), world.getSpawnPoint().getY() + 1, world.getSpawnPoint().getZ());
			}
		}
	}

	public boolean isFamiliar() {
		return dataManager.get(FAMILIAR_STATUS);
	}

	public void setFamiliar(boolean flag) {
		dataManager.set(FAMILIAR_STATUS, flag);
		setFamiliarAttributes(flag);
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

	protected abstract void setFamiliarAttributes(boolean isFamiliar);

	/**
	 * @return The integer representing the skin type
	 */
	public int getFamiliarSkin() {
		return dataManager.get(FAMILIAR_TYPE);
	}

	public void setEntitySkin(int type) {
		if (type >= getSkinTypes()) {
			throw new IllegalArgumentException(String.format("Skin of index %d doesn't exist for %s", type, this.getClass().getName()));
		}
		dataManager.set(FAMILIAR_TYPE, type);
	}

	public abstract String[] getRandomNames();

	@Nullable
	public EntityPlayer getOwnerAcrossWorlds() {
		if (getOwnerId() == null || !isTamed()) {
			return null;
		}
		return PlayerHelper.getPlayerAcrossDimensions(getOwnerId());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("familiar", isFamiliar());
		compound.setInteger("fam_type", getFamiliarSkin());
		compound.setInteger("skin", dataManager.get(SKIN));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setFamiliar(compound.getBoolean("familiar"));
		setEntitySkin(compound.getInteger("fam_type"));
		dataManager.set(SKIN, compound.getInteger("skin"));
		dataManager.setDirty(SKIN);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setEntitySkinIndex(rand.nextInt(getSkinTypes()));
		return super.onInitialSpawn(difficulty, livingdata);
	}

}