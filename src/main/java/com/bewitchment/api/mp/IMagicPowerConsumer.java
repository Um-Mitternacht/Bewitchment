package com.bewitchment.api.mp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * I highly suggest not to implement this interface on a class, but to have a field
 * on your consumer and initializing it to the default implementation, then using
 * the provided methods to save and load the field to/from nbt
 *
 * @author zabi94
 */
public interface IMagicPowerConsumer {

	@CapabilityInject(IMagicPowerConsumer.class)
	public static final Capability<IMagicPowerConsumer> CAPABILITY = null;

	public boolean drainAltarFirst(@Nullable EntityPlayer caster, @Nonnull BlockPos pos, int dimension, int amount);

	public NBTTagCompound writeToNbt();

	public void readFromNbt(NBTTagCompound tag);

	default boolean drainPlayerFirst(@Nullable EntityPlayer caster, @Nonnull BlockPos pos, int dimension, int amount) {
		if (caster != null && caster.getCapability(IMagicPowerContainer.CAPABILITY, null).drain(amount)) {
			return true;
		}
		return drainAltarFirst(null, pos, dimension, amount);
	}

}
