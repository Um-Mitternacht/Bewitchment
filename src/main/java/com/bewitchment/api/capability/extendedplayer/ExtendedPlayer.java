package com.bewitchment.api.capability.extendedplayer;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class ExtendedPlayer implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<ExtendedPlayer> {
	@CapabilityInject(ExtendedPlayer.class)
	public static final Capability<ExtendedPlayer> CAPABILITY = null;
	
	public List<String> uniqueDefeatedBosses = new ArrayList<>();
	public Fortune fortune;
	public int ritualsCast;
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList bosses = new NBTTagList();
		for (String boss : instance.uniqueDefeatedBosses) bosses.appendTag(new NBTTagString(boss));
		tag.setString("fortune", instance.fortune == null ? "" : instance.fortune.getRegistryName().toString());
		tag.setInteger("ritualsCast", ritualsCast);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		NBTTagList bosses = tag.getTagList("pets", Constants.NBT.TAG_STRING);
		for (int i = 0; i < bosses.tagCount(); i++) instance.uniqueDefeatedBosses.add(bosses.getStringTagAt(i));
		instance.fortune = tag.getString("fortune").isEmpty() ? null : BewitchmentAPI.REGISTRY_FORTUNE.getValue(new ResourceLocation(tag.getString("fortune")));
		instance.ritualsCast = tag.getInteger("ritualsCast");
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