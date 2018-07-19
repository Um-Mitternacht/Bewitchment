package com.bewitchment.common.core.capability.mimic;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MimicDataStorage implements Capability.IStorage<IMimicData> {

	private static final String
			MIMICKING = "mimicking",
			MIMICKING_ID = "mimickingID",
			MIMICKING_NAME = "mimickingName";

	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IMimicData> capability, IMimicData iMimicData, EnumFacing enumFacing) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean(MIMICKING, iMimicData.isMimicking());
		compound.setUniqueId(MIMICKING_ID, iMimicData.getMimickedPlayerID());
		compound.setString(MIMICKING_NAME, iMimicData.getMimickedPlayerName());
		return compound;
	}

	@Override
	public void readNBT(Capability<IMimicData> capability, IMimicData iMimicData, EnumFacing enumFacing, NBTBase nbtBase) {
		NBTTagCompound compound = (NBTTagCompound) nbtBase;
		iMimicData.setMimickingDirect(compound.getBoolean(MIMICKING));
		iMimicData.setMimickedPlayerID(compound.getUniqueId(MIMICKING_ID));
		iMimicData.setMimickedPlayerName(compound.getString(MIMICKING_NAME));
	}
}
