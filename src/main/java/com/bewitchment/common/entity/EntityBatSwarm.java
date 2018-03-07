package com.bewitchment.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBatSwarm extends Entity {
	
	public EntityBatSwarm(World worldIn) {
		super(worldIn);
		this.setSize(0.2f, 0.2f);
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
		this.move(MoverType.SELF, this.getLookVec().x, this.getLookVec().y, this.getLookVec().z);
		if (this.collided || this.ticksExisted > 60) {
			this.getPassengers().forEach(e -> e.dismountRidingEntity());
			this.setDead();
		}
		if (this.getPassengers().isEmpty()) {
			this.setDead();
		}
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
