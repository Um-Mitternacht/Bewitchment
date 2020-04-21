package com.bewitchment.api.capability.extendedplayer;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.SyncExtendedPlayer;
import com.bewitchment.api.registry.Curse;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"ConstantConditions"})
public class ExtendedPlayer implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<ExtendedPlayer> {
	@CapabilityInject(ExtendedPlayer.class)
	public static final Capability<ExtendedPlayer> CAPABILITY = null;
	
	public NBTTagList uniqueDefeatedBosses = new NBTTagList();
	public Fortune fortune;
	public Map<String, Integer> curses = new HashMap<>(); //curse id-days left
	public boolean canRitual = true;
	public int ritualDisabledTime, fortuneTime, ritualsCast, mobsKilled, pets;
	
	public static void syncToClient(EntityPlayer player) {
		if (!player.world.isRemote) Bewitchment.network.sendTo(new SyncExtendedPlayer(player.getCapability(CAPABILITY, null).serializeNBT()), ((EntityPlayerMP) player));
	}
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("uniqueDefeatedBosses", instance.uniqueDefeatedBosses);
		tag.setString("fortune", instance.fortune == null ? "" : instance.fortune.getRegistryName().toString());
		
		tag.setBoolean("canRitual", instance.canRitual);
		
		NBTTagList cursesList = new NBTTagList();
		tag.setTag("curses", cursesList);
		instance.curses.entrySet().stream().forEach(entry -> this.addNewCouple(entry, cursesList));
		
		tag.setInteger("fortuneTime", fortuneTime);
		tag.setInteger("ritualsCast", instance.ritualsCast);
		tag.setInteger("mobsKilled", instance.mobsKilled);
		tag.setInteger("ritualDisabledTime", instance.ritualDisabledTime);
		tag.setInteger("pets", instance.pets);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.uniqueDefeatedBosses = tag.getTagList("uniqueDefeatedBosses", Constants.NBT.TAG_STRING);
		instance.fortune = tag.getString("fortune").isEmpty() ? null : GameRegistry.findRegistry(Fortune.class).getValue(new ResourceLocation(tag.getString("fortune")));
		
		instance.canRitual = tag.getBoolean("canRitual");
		
		instance.curses.clear();
		tag.getTagList("curses", Constants.NBT.TAG_COMPOUND).forEach(s -> this.loadCouple(instance, s));
		
		instance.fortuneTime = tag.getInteger("fortuneTime");
		instance.ritualsCast = tag.getInteger("ritualsCast");
		instance.mobsKilled = tag.getInteger("mobsKilled");
		instance.ritualDisabledTime = tag.getInteger("ritualDisabledTime");
		instance.pets = tag.getInteger("pets");
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing face) {
		return getCapability(capability, null) != null;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing face) {
		return capability == CAPABILITY ? CAPABILITY.cast(this) : null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) CAPABILITY.getStorage().writeNBT(CAPABILITY, this, null);
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		CAPABILITY.getStorage().readNBT(CAPABILITY, this, null, tag);
	}
	
	public List<Curse> getCurses() {
		List<Curse> curseList = new ArrayList<>();
		curses.keySet().forEach(s -> {
			Curse curse = GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(s));
			if (curse != null) curseList.add(curse);
		});
		return curseList;
	}
	
	public void addCurse(Curse curse, int days) {
		curses.putIfAbsent(curse.getRegistryName().toString(), days * 24000);
	}
	
	public boolean hasCurse(Curse curse) {
		return getCurses().contains(curse);
	}
	
	public boolean removeCurse(Curse curse) {
		if (getCurses().contains(curse)) {
			curses.remove(curse.getRegistryName().toString());
			return true;
		}
		return false;
	}
	
	//called every second
	public void updateCurses() {
		List<String> toRemove = new ArrayList<>();
		for (String curs : curses.keySet()) {
			curses.replace(curs, curses.get(curs), curses.get(curs) - 20);
			if (curses.get(curs) <= 0) {
				toRemove.add(curs);
			}
		}
		toRemove.forEach(s -> curses.remove(s));
	}
	
	private void addNewCouple(Map.Entry<String, Integer> entry, NBTTagList list) {
		NBTTagCompound couple = new NBTTagCompound();
		couple.setString("curseId", entry.getKey());
		couple.setInteger("daysLeft", entry.getValue());
		list.appendTag(couple);
	}
	
	private void loadCouple(ExtendedPlayer instance, NBTBase s) {
		NBTTagCompound tag = (NBTTagCompound) s;
		instance.curses.put(tag.getString("curseId"), tag.getInteger("daysLeft"));
	}
}