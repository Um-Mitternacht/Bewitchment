package com.bewitchment.api.capability.magicpower;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesAltar;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({"ConstantConditions"})
public class MagicPower implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<MagicPower> {
	@CapabilityInject(MagicPower.class)
	public static final Capability<MagicPower> CAPABILITY = null;
	
	public int amount, maxAmount;
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<MagicPower> capability, MagicPower instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("amount", instance.amount);
		tag.setInteger("maxAmount", instance.maxAmount);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<MagicPower> capability, MagicPower instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.amount = tag.getInteger("amount");
		instance.maxAmount = tag.getInteger("maxAmount");
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
	
	public static boolean attemptDrain(TileEntity tile, EntityPlayer player, int amount) {
		if (amount == 0) return true;
		if (tile instanceof TileEntityWitchesAltar && tile.getCapability(CAPABILITY, null).drain(amount)) return true;
		if (player != null) {
			for (ItemStack stack : Util.getEntireInventory(player)) {
				if (stack.getItem() == ModObjects.grimoire_magia && stack.getCapability(CAPABILITY, null).drain(amount)) return true;
			}
		}
		return false;
	}
	
	public boolean drain(int amount) {
		if (this.amount - amount >= 0) {
			this.amount = Math.max(0, this.amount - amount);
			return true;
		}
		return false;
	}
	
	public boolean fill(int amount) {
		if (this.amount < this.maxAmount) {
			this.amount = Math.min(this.amount + amount, this.maxAmount);
			return true;
		}
		return true;
	}
}