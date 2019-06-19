package com.bewitchment.common.entity.misc;

import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityYewBroom extends EntityBroom {
	public BlockPos originalPos;
	public int originalDimension;
	
	public EntityYewBroom(World world) {
		super(world);
	}
	
	@Override
	protected float getSpeed() {
		return 1;
	}
	
	@Override
	protected float getMaxSpeed() {
		return 1;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setLong("origialPos", originalPos.toLong());
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