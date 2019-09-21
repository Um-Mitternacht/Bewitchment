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
	
	public NBTTagList uniqueDefeatedBosses = new NBTTagList(), exploredChunks = new NBTTagList();
	public Fortune fortune;
	public Map<String, Integer> curses = new HashMap<>(); //curse id-days left
	public int fortuneTime, ritualsCast, mobsKilled;
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("uniqueDefeatedBosses", instance.uniqueDefeatedBosses);
		tag.setTag("exploredChunks", instance.exploredChunks);
		tag.setString("fortune", instance.fortune == null ? "" : instance.fortune.getRegistryName().toString());

		NBTTagList cursesList = new NBTTagList();
		tag.setTag("curses", cursesList);
		instance.curses.entrySet().stream().forEach(entry -> this.addNewCouple(entry, cursesList));

		tag.setInteger("fortuneTime", fortuneTime);
		tag.setInteger("ritualsCast", instance.ritualsCast);
		tag.setInteger("mobsKilled", instance.mobsKilled);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.uniqueDefeatedBosses = tag.getTagList("uniqueDefeatedBosses", Constants.NBT.TAG_STRING);
		instance.exploredChunks = tag.getTagList("exploredChunks", Constants.NBT.TAG_LONG);
		instance.fortune = tag.getString("fortune").isEmpty() ? null : GameRegistry.findRegistry(Fortune.class).getValue(new ResourceLocation(tag.getString("fortune")));

		instance.curses.clear();
		tag.getTagList("curses", Constants.NBT.TAG_COMPOUND).forEach(s -> this.loadCouple(instance, s));

		instance.fortuneTime = tag.getInteger("fortuneTime");
		instance.ritualsCast = tag.getInteger("ritualsCast");
		instance.mobsKilled = tag.getInteger("mobsKilled");
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

	public List<Curse> getCurses(){
		List<Curse> curseList = new ArrayList<>();
		curses.keySet().forEach(s -> curseList.add(GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(s))));
		return curseList;
	}

	public void addCurse(Curse curse, int days){
		curses.putIfAbsent(curse.getRegistryName().toString(), days);
	}

	public void updateCurses(){
		for (String curs : curses.keySet()){
			if (curses.get(curs) <= 0){
				curses.remove(curs);
			}
		}
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