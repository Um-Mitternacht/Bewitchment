package com.bewitchment.common.core.capability.transformation;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TransformationDataStorage implements IStorage<ITransformationData> {

	@Override
	public NBTBase writeNBT(Capability<ITransformationData> capability, ITransformationData instance, EnumFacing side) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("level", instance.getLevel());
		data.setInteger("type", instance.getType().ordinal());
		if (instance.getType() == EnumTransformationType.VAMPIRE) {
			data.setInteger("blood", instance.getBlood());
		}
		data.setBoolean("nightvision", instance.isNightVisionActive());
		return data;
	}

	@Override
	public void readNBT(Capability<ITransformationData> capability, ITransformationData instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound data = (NBTTagCompound) nbt;
		instance.setLevel(data.getInteger("level"));
		instance.setType(EnumTransformationType.values()[data.getInteger("type")]);
		if (instance.getType() == EnumTransformationType.VAMPIRE) {
			instance.setBlood(data.getInteger("blood"));
		}
		instance.setNightVision(data.getBoolean("nightvision"));
	}

}
