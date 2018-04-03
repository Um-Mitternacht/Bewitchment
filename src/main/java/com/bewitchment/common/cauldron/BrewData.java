package com.bewitchment.common.cauldron;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.api.cauldron.IBrewData;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BrewData implements INBTSerializable<NBTTagList>, IBrewData {
	
	private ArrayList<IBrewEntry> effects = new ArrayList<>();
	
	public static BrewData fromStack(ItemStack stack) {
		BrewData data = new BrewData();
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("brew")) {
				data.deserializeNBT(stack.getTagCompound().getTagList("brew", NBT.TAG_COMPOUND));
			}
		}
		return data;
	}
	
	public void addEntry(BrewEntry entry) {
		effects.add(entry);
	}
	
	public void saveToStack(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		tag.setTag("brew", this.serializeNBT());
	}
	
	@Override
	public List<IBrewEntry> getEffects() {
		return ImmutableList.copyOf(effects);
	}
	
	@Override
	public NBTTagList serializeNBT() {
		NBTTagList list = new NBTTagList();
		effects.forEach(ef -> list.appendTag(((BrewEntry) ef).serializeNBT()));
		return list;
	}
	
	@Override
	public void deserializeNBT(NBTTagList nbt) {
		effects.clear();
		nbt.forEach(nbtb -> effects.add(new BrewEntry((NBTTagCompound) nbtb)));
	}
	
	public static class BrewEntry implements INBTSerializable<NBTTagCompound>, IBrewEntry {
		
		private Potion pot;
		private BrewModifierListImpl mods;
		
		protected BrewEntry(Potion potion, BrewModifierListImpl modifiers) {
			this.pot = potion;
			this.mods = modifiers;
		}
		
		protected BrewEntry(NBTTagCompound tag) {
			deserializeNBT(tag);
		}
		
		@Override
		public Potion getPotion() {
			return pot;
		}
		
		@Override
		public IBrewModifierList getModifiers() {
			return mods;
		}
		
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("potion", pot.getRegistryName().toString());
			tag.setTag("modifiers", mods.serializeNBT());
			return tag;
		}
		
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			pot = ForgeRegistries.POTIONS.getValue(new ResourceLocation(nbt.getString("potion")));
			mods = new BrewModifierListImpl();
			mods.deserializeNBT(nbt.getTagList("modifiers", NBT.TAG_COMPOUND));
		}
		
	}
}
