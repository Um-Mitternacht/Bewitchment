package com.bewitchment.common.cauldron;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.crafting.cauldron.CauldronRegistry;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class BrewModifierListImpl implements IBrewModifierList, INBTSerializable<NBTTagList> {
	
	private HashMap<IBrewModifier, Integer> mods = new HashMap<>();
	
	@Override
	public Optional<Integer> getLevel(IBrewModifier modifier) {
		if (mods.containsKey(modifier)) {
			return Optional.of(mods.get(modifier));
		}
		return Optional.empty();
	}
	
	@Override
	public Set<IBrewModifier> getModifiers() {
		return mods.keySet();
	}
	
	public void addModifier(IBrewModifier mod, int level) {
		mods.put(mod, level);
	}
	
	@Override
	public NBTTagList serializeNBT() {
		NBTTagList modf_list = new NBTTagList();
		for (IBrewModifier mod : getModifiers()) {
			Optional<Integer> lvl = getLevel(mod);
			if (lvl.isPresent()) {
				NBTTagCompound singleModifier = new NBTTagCompound();
				singleModifier.setString("name", mod.getRegistryName().toString());
				singleModifier.setInteger("level", lvl.get());
				modf_list.appendTag(singleModifier);
			}
		}
		return modf_list;
	}
	
	@Override
	public void deserializeNBT(NBTTagList list) {
		mods.clear();
		for (NBTBase b : list) {
			NBTTagCompound singleModifier = (NBTTagCompound) b;
			if (singleModifier.hasKey("level")) {
				int lvl = singleModifier.getInteger("level");
				IBrewModifier mod = CauldronRegistry.BREW_MODIFIERS.getValue(new ResourceLocation(singleModifier.getString("name")));
				mods.put(mod, lvl);
			}
		}
	}
	
}
