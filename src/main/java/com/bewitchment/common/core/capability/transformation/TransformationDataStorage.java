package com.bewitchment.common.core.capability.transformation;

import java.util.ArrayList;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.api.event.HotbarAction;

import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class TransformationDataStorage implements IStorage<ITransformationData> {

	@Override
	public NBTBase writeNBT(Capability<ITransformationData> capability, ITransformationData instance, EnumFacing side) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("level", instance.getLevel());
		data.setInteger("type", instance.getType().ordinal());
		if (instance.getType() == EnumTransformationType.VAMPIRE) {
			data.setInteger("blood", instance.getBlood());
		}
		data.setTag("misc", instance.getMiscDataTag());
		NBTTagList actions = new NBTTagList();
		for (HotbarAction act : instance.getAvailableHotbarActions()) {
			actions.appendTag(new NBTTagString(act.getName().toString()));
		}
		data.setTag("actions", actions);
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
		instance.loadMiscDataTag(data.getCompoundTag("misc"));
		NBTTagList listTag = data.getTagList("actions", NBT.TAG_LIST);
		ArrayList<HotbarAction> list = new ArrayList<>(listTag.tagCount());
		listTag.forEach(nbtb -> list.add(HotbarAction.getFromRegistryName(nbtb.toString())));
		instance.loadAvailableHotbarActions(list);
	}

}
