package com.bewitchment.api.entity;

import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

public abstract class EntityFamiliar extends EntityTameable {

	private static final float REDIRECTED_DAMAGE = 0.1f;
	private static final DamageSource FAMILIAR_LINK = new DamageSource("familiar_link").setMagicDamage();

	private static final DataParameter<Boolean> FAMILIAR_STATUS = EntityDataManager.createKey(EntityFamiliar.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> FAMILIAR_TYPE = EntityDataManager.createKey(EntityFamiliar.class, DataSerializers.VARINT);

	public EntityFamiliar(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(FAMILIAR_STATUS, false);
		this.dataManager.register(FAMILIAR_TYPE, 0);
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
	}

	/**
	 * @return The integer representing the skin type
	 */
	public int getFamiliarSkin() {
		return dataManager.get(FAMILIAR_TYPE);
	}

	public void setFamiliarSkin(int type) {
		if (type >= getTotalVariants()) {
			throw new IllegalArgumentException(String.format("Skin of index %d doesn't exist for %s", type, this.getClass().getName()));
		}
		dataManager.set(FAMILIAR_TYPE, type);
	}

	public abstract int getTotalVariants();

	public abstract String[] getRandomNames();

	@Nullable
	public EntityPlayer getOwnerAcrossWorlds() {
		if (getOwnerId() == null || !isTamed()) {
			return null;
		}
		for (WorldServer ws : world.getMinecraftServer().worlds) {
			EntityPlayer p = ws.getPlayerEntityByUUID(getOwnerId());
			if (p != null) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("familiar", isFamiliar());
		compound.setInteger("fam_type", getFamiliarSkin());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setFamiliar(compound.getBoolean("familiar"));
		setFamiliarSkin(compound.getInteger("fam_type"));
	}
}
