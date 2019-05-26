package com.bewitchment.common.block.tile.entity.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityAltarStorage extends ModTileEntity {
	public BlockPos altarPos;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("altarPos")) altarPos = BlockPos.fromLong(tag.getLong("altarPos"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		if (altarPos != null) tag.setLong("altarPos", altarPos.toLong());
		return super.writeToNBT(tag);
	}
}