package com.bewitchment.api.capability.extendedworld;

import com.bewitchment.Bewitchment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "SameReturnValue", "NullableProblems", "WeakerAccess"})
public class ExtendedWorld extends WorldSavedData {
	private static final String TAG = Bewitchment.MODID + ".world_data";
	
	public final List<NBTTagCompound> storedCauldrons = new ArrayList<>();
	
	public ExtendedWorld(String name) {
		super(name);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = new NBTTagList();
		for (NBTTagCompound cauldron : this.storedCauldrons) storedCauldrons.appendTag(cauldron);
		nbt.setTag("storedCauldrons", storedCauldrons);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = nbt.getTagList("storedCauldrons", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < storedCauldrons.tagCount(); i++) this.storedCauldrons.add(storedCauldrons.getCompoundTagAt(i));
	}
	
	public static ExtendedWorld get(World world) {
		ExtendedWorld data = (ExtendedWorld) world.getMapStorage().getOrLoadData(ExtendedWorld.class, TAG);
		if (data == null) {
			data = new ExtendedWorld(TAG);
			world.getMapStorage().setData(TAG, data);
		}
		return data;
	}
}