package com.bewitchment.api.capability.extendedworld;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "SameReturnValue"})
public class ExtendedWorld implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<ExtendedWorld> {
	@CapabilityInject(ExtendedWorld.class)
	public static final Capability<ExtendedWorld> CAPABILITY = null;
	
	public final List<Long> storedCauldrons = new ArrayList<>();
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ExtendedWorld> capability, ExtendedWorld instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList cauldrons = new NBTTagList();
		for (long l : instance.storedCauldrons) cauldrons.appendTag(new NBTTagLong(l));
		tag.setTag("cauldrons", cauldrons);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<ExtendedWorld> capability, ExtendedWorld instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		NBTTagList list = tag.getTagList("cauldrons", Constants.NBT.TAG_LONG);
		for (int i = 0; i < list.tagCount(); i++) instance.storedCauldrons.add(((NBTTagLong) list.get(i)).getLong());
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing face) {
		return capability == CAPABILITY ? CAPABILITY.cast(this) : null;
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing face) {
		return getCapability(capability, null) != null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) CAPABILITY.getStorage().writeNBT(CAPABILITY, this, null);
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		CAPABILITY.getStorage().readNBT(CAPABILITY, this, null, tag);
	}
}