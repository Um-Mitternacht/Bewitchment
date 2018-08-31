package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifierList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BrewModifierListImpl implements IBrewModifierList, INBTSerializable<NBTTagList> {

	private HashMap<ResourceLocation, Integer> map = new HashMap<>();

	public BrewModifierListImpl() {
	}

	public BrewModifierListImpl(IBrewModifierList from) {
		from.getModifiers().forEach(bm -> addModifier(bm, from.getLevel(bm).get()));
	}

	@Override
	public Optional<Integer> getLevel(IBrewModifier modifier) {
		if (map.containsKey(modifier.getRegistryName())) {
			return Optional.of(map.get(modifier.getRegistryName()));
		}
		return Optional.empty();
	}

	@Override
	public Set<IBrewModifier> getModifiers() {
		return map.keySet().stream().map(rl -> CauldronRegistry.BREW_MODIFIERS.getValue(rl)).collect(Collectors.toSet());
	}

	public void addModifier(IBrewModifier mod, int level) {
		map.put(mod.getRegistryName(), level);
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
		map.clear();
		for (NBTBase b : list) {
			NBTTagCompound singleModifier = (NBTTagCompound) b;
			if (singleModifier.hasKey("level")) {
				int lvl = singleModifier.getInteger("level");
				IBrewModifier mod = CauldronRegistry.BREW_MODIFIERS.getValue(new ResourceLocation(singleModifier.getString("name")));
				map.put(mod.getRegistryName(), lvl);
			}
		}
	}

}
