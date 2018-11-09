package com.bewitchment.common.entity;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityBatSwarm extends Entity {

	public EntityBatSwarm(World worldIn) {
		super(worldIn);
		this.setSize(0.01f, 0.01f);
		this.setEntityInvulnerable(true);
		this.setNoGravity(true);
		this.setSilent(true);
	}

	@Override
	protected void entityInit() {
		this.setEntityBoundingBox(null);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (this.rand.nextInt(7) == 0) {
			this.world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_BAT_LOOP, SoundCategory.PLAYERS, 0.8f, 0.8f + 0.4f * rand.nextFloat());
		}
		if (this.getPassengers().size() == 0) {
			this.setDead();
		} else {
			EntityPlayer rider = (EntityPlayer) this.getPassengers().get(0);
			if (rider.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
				if (this.rand.nextInt(7) == 0) {
					this.world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_BAT_LOOP, SoundCategory.PLAYERS, 0.8f, 0.8f + 0.4f * rand.nextFloat());
				}
				if (rider.isCreative() || BewitchmentAPI.getAPI().addVampireBlood(rider, -1)) {
					this.move(MoverType.SELF, this.getLookVec().x, this.getLookVec().y, this.getLookVec().z);
				} else {
					rider.dismountRidingEntity();
					this.setDead();
					return;
				}
				if (this.collided || this.ticksExisted > 60) {
					rider.dismountRidingEntity();
					this.setDead();
					return;
				}
			} else {
				rider.dismountRidingEntity();
				this.setDead();
			}
		}
	}

	@Override
	public void setDead() {
		if (world.isRemote) {
			for (int i = 0; i < 30; i++) {
				Bewitchment.proxy.spawnParticle(ParticleF.BAT, this.posX + rand.nextGaussian(), posY + rand.nextGaussian(), posZ + rand.nextGaussian(), 0, 0, 0, 1);
			}
		}
		this.world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_BAT_AMBIENT, SoundCategory.PLAYERS, 1f, 0.8f + 0.4f * rand.nextFloat());
		this.world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.PLAYERS, 1f, 0.8f + 0.4f * rand.nextFloat());
		super.setDead();
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return super.canBeRidden(entityIn) && (entityIn instanceof EntityPlayer);
	}

	@Override
	public double getMountedYOffset() {
		return -1.2;
	}
}
