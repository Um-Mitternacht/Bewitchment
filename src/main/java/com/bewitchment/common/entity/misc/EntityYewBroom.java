package com.bewitchment.common.entity.misc;

import com.bewitchment.Util;
import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityYewBroom extends EntityBroom {
	private BlockPos originalPos;
	private int originalDimension;
	
	public EntityYewBroom(World world) {
		super(world);
	}
	
	@Override
	protected float getSpeed() {
		return 1.5f;
	}
	
	@Override
	protected float getMaxSpeed() {
		return 1.1f;
	}
	
	@Override
	protected float getThrust() {
		return 0.125f;
	}
	
	@Override
	protected int getMagicCost() {
		return 1;
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		originalPos = getPosition();
		originalDimension = dimension;
		return super.processInitialInteract(player, hand);
	}
	
	@Override
	protected void dismount() {
		if (!world.isRemote && dimension == originalDimension && originalPos != null && getControllingPassenger() instanceof EntityPlayer) {
			double x = originalPos.getX() + 0.5;
			double y = originalPos.getY() + 0.5;
			double z = originalPos.getZ() + 0.5;
			setPositionAndUpdate(x, y, z);
			Util.teleportPlayer((EntityPlayer) getControllingPassenger(), x, y, z);
		}
		super.dismount();
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setLong("originalPos", originalPos.toLong());
		tag.setInteger("originalDimension", originalDimension);
		super.writeEntityToNBT(tag);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		originalPos = BlockPos.fromLong(tag.getLong("originalPos"));
		originalDimension = tag.getInteger("originalDimension");
		super.readEntityFromNBT(tag);
	}
}