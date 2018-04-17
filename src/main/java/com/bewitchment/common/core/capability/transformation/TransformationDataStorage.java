package com.bewitchment.common.core.capability.transformation;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.transformation.ModTransformations;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TransformationDataStorage implements IStorage<ITransformationData> {

	@Override
	public NBTBase writeNBT(Capability<ITransformationData> capability, ITransformationData instance, EnumFacing side) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("level", instance.getLevel());
		data.setString("type", instance.getType().getRegistryName().toString());
		if (instance.getType() == DefaultTransformations.VAMPIRE) {
			data.setInteger("blood", instance.getBlood());
		}
		data.setBoolean("nightvision", instance.isNightVisionActive());
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void readNBT(Capability<ITransformationData> capability, ITransformationData instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound data = (NBTTagCompound) nbt;
		instance.setLevel(data.getInteger("level"));
		ITransformation transf = ModTransformations.REGISTRY.getValue(new ResourceLocation(data.getString("type")));
		instance.setType(transf == null ? DefaultTransformations.NONE : transf);
		if (transf == DefaultTransformations.VAMPIRE) {
			instance.setBlood(data.getInteger("blood"));
		}
		instance.setNightVision(data.getBoolean("nightvision"));
	}

}
