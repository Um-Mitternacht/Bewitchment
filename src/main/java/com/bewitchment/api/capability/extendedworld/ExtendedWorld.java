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
	public final List<NBTTagCompound> storedPoppetShelves = new ArrayList<>();
	
	public ExtendedWorld(String name) {
		super(name);
	}
	
	public static ExtendedWorld get(World world) {
		ExtendedWorld data = (ExtendedWorld) world.getMapStorage().getOrLoadData(ExtendedWorld.class, TAG);
		if (data == null) {
			data = new ExtendedWorld(TAG);
			world.getMapStorage().setData(TAG, data);
		}
		return data;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = nbt.getTagList("storedCauldrons", Constants.NBT.TAG_COMPOUND);
		NBTTagList storedPoppetShelves = nbt.getTagList("storedPoppetShelves", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < storedCauldrons.tagCount(); i++) this.storedCauldrons.add(storedCauldrons.getCompoundTagAt(i));
		for (int i = 0; i < storedPoppetShelves.tagCount(); i++) this.storedPoppetShelves.add(storedPoppetShelves.getCompoundTagAt(i));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = new NBTTagList();
		NBTTagList storedPoppetShelves = new NBTTagList();
		for (NBTTagCompound cauldron : this.storedCauldrons) storedCauldrons.appendTag(cauldron);
		for (NBTTagCompound poppet : this.storedPoppetShelves) storedPoppetShelves.appendTag(poppet);
		nbt.setTag("storedCauldrons", storedCauldrons);
		nbt.setTag("storedPoppetShelves", storedPoppetShelves);
		return nbt;
	}
}