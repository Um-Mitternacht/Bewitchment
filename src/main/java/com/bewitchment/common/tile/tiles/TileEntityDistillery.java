package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.IOInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityDistillery extends ModTileEntity implements ITickable {

	private IMagicPowerConsumer mp = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private IOInventory inventory = new IOInventory(7, 6);
	private FluidTank tank = new FluidTank(1000);
	private int progress = 0;

	@Override
	public void update() {

	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(mp);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		inventory.deserializeNBT(tag.getCompoundTag("inv"));
		tank.readFromNBT(tag.getCompoundTag("tank"));
		mp.readFromNbt(tag.getCompoundTag("mp"));
		progress = tag.getInteger("progress");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("inv", inventory.serializeNBT());
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("mp", mp.writeToNbt());
		tag.setInteger("progress", progress);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {

	}

}
