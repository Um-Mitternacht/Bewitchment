package com.bewitchment.common.core.capability.brew;

import com.bewitchment.api.BrewRegistry;
import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.BrewUtils;
import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.capability.IBrewStorage;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.BrewMessage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.*;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class CapabilityBrewStorage {

	private CapabilityBrewStorage() {
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(IBrewStorage.class, new Capability.IStorage<IBrewStorage>() {

			@Override
			public NBTBase writeNBT(Capability<IBrewStorage> capability, IBrewStorage instance, EnumFacing side) {
				NBTTagList tagList = new NBTTagList();
				for (Map.Entry<IBrew, BrewEffect> entry : instance.getBrewMap().entrySet()) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString(BrewUtils.BREW_ID, BrewRegistry.getBrewResource(entry.getKey()).toString());
					tag.setInteger(BrewUtils.BREW_DURATION, entry.getValue().getDuration());
					tag.setInteger(BrewUtils.BREW_AMPLIFIER, entry.getValue().getAmplifier());
					tagList.appendTag(tag);
				}
				NBTTagCompound compound = new NBTTagCompound();
				compound.setTag(BrewUtils.BREW_DATA, tagList);
				return compound;
			}

			@Override
			public void readNBT(Capability<IBrewStorage> capability, IBrewStorage instance, EnumFacing side, NBTBase nbt) {
				Map<IBrew, BrewEffect> effects = new HashMap<>();
				NBTTagList tagList = (NBTTagList) ((NBTTagCompound) nbt).getTag(BrewUtils.BREW_DATA);
				for (int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.get(i);
					IBrew brew = BrewRegistry.getRegisteredBrew(tag.getString(BrewUtils.BREW_ID));
					int duration = tag.getInteger(BrewUtils.BREW_DURATION);
					int amplifier = tag.getInteger(BrewUtils.BREW_AMPLIFIER);
					effects.put(brew, new BrewEffect(brew, duration, amplifier));
				}
				instance.setBrewMap(effects);
			}

		}, DefaultBrewStorage::new);
	}

	public static class DefaultBrewStorage implements IBrewStorage {

		private Map<IBrew, BrewEffect> effects = new LinkedHashMap<>(1);

		@Override
		public Map<IBrew, BrewEffect> getBrewMap() {
			return effects;
		}

		@Override
		public void setBrewMap(Map<IBrew, BrewEffect> effects) {
			this.effects = effects;
		}

		@Override
		public void syncToNear(EntityLivingBase target) {
			NetworkHandler.sendNear(target, this);
		}

		@Override
		public Collection<BrewEffect> getBrewEffects() {
			return effects.values();
		}

		@Override
		public Set<IBrew> getBrews() {
			return effects.keySet();
		}

		@Override
		public void syncTo(EntityPlayerMP target) {
			NetworkHandler.HANDLER.sendTo(new BrewMessage(this, target), target);
		}
	}
}
