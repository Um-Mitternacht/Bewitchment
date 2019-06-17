package com.bewitchment.api.capability.extendedplayer;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.message.SyncExtendedPlayer;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ExtendedPlayer implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<ExtendedPlayer> {
	@CapabilityInject(ExtendedPlayer.class)
	public static final Capability<ExtendedPlayer> CAPABILITY = null;
	
	private NBTTagList uniqueDefeatedBosses = new NBTTagList();
	private Fortune fortune;
	private int ritualsCast;
	
	public static NBTTagList getUniqueDefeatedBosses(EntityPlayer player) {
		return player.getCapability(CAPABILITY, null).uniqueDefeatedBosses;
	}
	
	public static Fortune getFortune(EntityPlayer player) {
		return player.getCapability(CAPABILITY, null).fortune;
	}
	
	public static void setFortune(EntityPlayer player, Fortune fortune) {
		player.getCapability(CAPABILITY, null).fortune = fortune;
		syncToClient(player);
	}
	
	public static int getRitualsCast(EntityPlayer player) {
		return player.getCapability(CAPABILITY, null).ritualsCast;
	}
	
	public static void setRitualsCast(EntityPlayer player, int ritualsCast) {
		player.getCapability(CAPABILITY, null).ritualsCast = ritualsCast;
		syncToClient(player);
	}
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("uniqueDefeatedBosses", instance.uniqueDefeatedBosses);
		tag.setString("fortune", instance.fortune == null ? "" : instance.fortune.getRegistryName().toString());
		tag.setInteger("ritualsCast", instance.ritualsCast);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.uniqueDefeatedBosses = tag.getTagList("uniqueDefeatedBosses", Constants.NBT.TAG_STRING);
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
	
	public static void syncToClient(EntityPlayer player) {
		if (!player.world.isRemote) Bewitchment.network.sendTo(new SyncExtendedPlayer(player.getCapability(CAPABILITY, null).serializeNBT()), ((EntityPlayerMP) player));
	}
}