package com.bewitchment.api.entity;

import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

public abstract class EntityFamiliar extends EntityTameable {

	private static final float REDIRECTED_DAMAGE = 0.1f;
	private static final DamageSource FAMILIAR_LINK = new DamageSource("familiar_link").setMagicDamage();

	public EntityFamiliar(World worldIn) {
		super(worldIn);
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

	private boolean isFamiliar() {
		return false;
	}

	@Override
	public boolean isTamed() {
		return super.isTamed() || isFamiliar();
	}

	@Nullable
	public EntityPlayer getOwnerAcrossWorlds() {
		if (getOwnerId() == null) {
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
}
