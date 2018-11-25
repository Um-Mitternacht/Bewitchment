package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.crafting.DistilleryRecipe;
import com.bewitchment.common.crafting.ModDistilleryRecipes;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.IOInventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityDistillery extends ModTileEntity implements ITickable {

	private IMagicPowerConsumer mp = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private IOInventory inventory = new IOInventory(7, 6) {
		@Override
		protected void inventoryChanged() {
			contentsChanged();
		}
	};
	private FluidTank tank = new FluidTank(1000) {
		@Override
		protected void onContentsChanged() {
			contentsChanged();
		};
	};
	private int progress = 0;
	private String currentRecipe = null;

	@Override
	public void update() {
		if (currentRecipe != null) {
			if (progress > 0) {
				if (mp.drainAltarFirst(world.getClosestPlayer(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 5, false), getPos(), this.world.provider.getDimension(), 2)) {
					progress--;
				}
			} else {
				DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValue(new ResourceLocation(currentRecipe));
				if (recipe == null) {
					currentRecipe = null;
					progress = 0;
				} else {
					tank.setFluid(recipe.getRemainingFluidStack(tank.getFluid()));
					for (int i = 1; i < inventory.getInputSlotsCount(); i++) {
						if (!inventory.getStackInSlot(i).isEmpty()) {
							inventory.getStackInSlot(i).shrink(1);
						}
					}
				}
			}
		}
	}

	protected void contentsChanged() {
		checkRecipe();
		this.markDirty();
	}

	private void checkRecipe() {
		DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValuesCollection().parallelStream()
			.filter(dr -> dr.matches(inventory.getInputs(), inventory.getStackInSlot(0), tank.getFluid()))
			.findFirst().orElse(null);
		if (currentRecipe != recipe.getRegistryName().toString() && canOutputFit(recipe)) {
			currentRecipe = recipe.getRegistryName().toString();
			progress = recipe.getTime();
		}
	}

	private boolean canOutputFit(DistilleryRecipe recipe) {
		// FIXME this will report an incorrect data when the first slot is empty 
		// and everything else is full, and the recipe has at least two outputs.
		// We need to instanciate a new copy-inventory, actually modify it and see
		// with real stacks, not simulating.
		for (ItemStack is:recipe.getOutputs()) {
			if (!inventory.insertInOutputs(is, true).isEmpty()) {
				return false;
			}
		}
		return true;
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
		currentRecipe = tag.getString("recipe");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("inv", inventory.serializeNBT());
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("mp", mp.writeToNbt());
		tag.setInteger("progress", progress);
		tag.setString("recipe", currentRecipe);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {

	}

}
