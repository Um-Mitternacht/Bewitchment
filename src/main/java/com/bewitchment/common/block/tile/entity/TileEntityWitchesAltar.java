package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class TileEntityWitchesAltar extends ModTileEntity implements ITickable {
	public MagicPower magicPower = MagicPower.CAPABILITY.getDefaultInstance();
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		return super.writeToNBT(tag);
	}
	
	@Override
	public void update() {
	}
}