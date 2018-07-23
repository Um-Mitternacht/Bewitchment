package com.bewitchment.api.mp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IMagicPowerConsumer {
	
	@CapabilityInject(IMagicPowerConsumer.class)
	public static final Capability<IMagicPowerConsumer> CAPABILITY = null;
	
	public boolean drain(EntityPlayer caster, World world, BlockPos position, int radius, int amount);
	
	public NBTTagCompound writeToNbt();
	
	public void readFromNbt(NBTTagCompound tag);
	
}
