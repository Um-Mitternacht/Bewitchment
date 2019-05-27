package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "NullableProblems"})
public class TileEntityWitchesAltar extends ModTileEntity implements ITickable {
	public MagicPower magicPower = MagicPower.CAPABILITY.getDefaultInstance();
	
	public int color;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		color = tag.getInteger("color");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		tag.setInteger("color", color);
		return super.writeToNBT(tag);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY || super.hasCapability(capability, face);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY ? MagicPower.CAPABILITY.cast(magicPower) : super.getCapability(capability, face);
	}
	
	@Override
	public void update() {
	}
}