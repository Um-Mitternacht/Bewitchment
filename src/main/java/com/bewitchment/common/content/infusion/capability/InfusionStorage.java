package com.bewitchment.common.content.infusion.capability;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.api.infusion.IInfusionCapability;
import com.bewitchment.common.content.infusion.ModInfusions;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class InfusionStorage implements IStorage<IInfusionCapability> {

	@Override
	public NBTBase writeNBT(Capability<IInfusionCapability> capability, IInfusionCapability instance, EnumFacing side) {
		return new NBTTagString(instance.getType().getRegistryName().toString());
	}

	@Override
	public void readNBT(Capability<IInfusionCapability> capability, IInfusionCapability instance, EnumFacing side, NBTBase nbt) {
		IInfusion inf = ModInfusions.REGISTRY.getValue(new ResourceLocation(((NBTTagString) nbt).getString()));
		if (inf == null) {
			inf = DefaultInfusions.NONE;
		}
		instance.setType(inf);
	}

}
