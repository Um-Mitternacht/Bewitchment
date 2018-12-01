package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.crafting.DistilleryRecipe;
import com.bewitchment.common.crafting.ModDistilleryRecipes;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.IOInventory;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDistillery extends ModTileEntity implements ITickable {

	private IMagicPowerConsumer mp = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private IOInventory inventory = new IOInventory(6, 6) {
		@Override
		protected void inventoryChanged() {
			contentsChanged();
		}
	};
	private ItemStackHandler container = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			contentsChanged();
		};
	};
	private FluidTank tank = new FluidTank(1000) {
		@Override
		protected void onContentsChanged() {
			contentsChanged();
		};
	};
	private int progress = 0;
	private String currentRecipe = "";

	@Override
	public void update() {
		if (currentRecipe.length() > 0) {
			if (progress > 0) {
				if (isHeated()) {
					if (mp.drainAltarFirst(world.getClosestPlayer(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 5, false), getPos(), this.world.provider.getDimension(), 2)) {
						progress--;
						markDirty();
					}
				}
			} else {
				DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValue(new ResourceLocation(currentRecipe));
				if (recipe == null) {
					currentRecipe = "";
					progress = 0;
				} else {
					tank.setFluid(recipe.getRemainingFluidStack(tank.getFluid()));
					for (int i = 0; i < inventory.getInputSlotsCount(); i++) {
						if (!inventory.getStackInSlot(i).isEmpty()) {
							inventory.getStackInSlot(i).shrink(1);
						}
					}
					for (ItemStack is:recipe.getOutputs()) {
						inventory.insertInOutputs(is, false);
					}
					checkRecipe();
				}
				markDirty();
			}
		}
	}

	private boolean isHeated() {
		IBlockState fire = world.getBlockState(getPos().down());
		return fire.getBlock() == Blocks.FIRE || fire.getBlock() == ModBlocks.witchfire;
	}

	protected void contentsChanged() {
		checkRecipe();
		this.markDirty();
	}

	private void checkRecipe() {
		DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValuesCollection().parallelStream()
			.filter(dr -> dr.matches(inventory.getInputs(), inventory.getStackInSlot(0), tank.getFluid()))
			.findFirst().orElse(null);
		if (recipe == null) {
			currentRecipe = "";
			progress = 0;
		} else if (currentRecipe != recipe.getRegistryName().toString() && canOutputFit(recipe)) {
			currentRecipe = recipe.getRegistryName().toString();
			progress = recipe.getTime();
		}
	}

	private boolean canOutputFit(DistilleryRecipe recipe) {
		IOInventory simulated = new IOInventory(0, 6);
		for (int i = 0; i < inventory.getOutputs().size(); i++) {
			simulated.setStackInSlot(i, inventory.getOutputs().get(i).copy());
		}
		for (ItemStack is:recipe.getOutputs()) {
			if (!simulated.insertInOutputs(is, false).isEmpty()) {
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
			if (facing == world.getBlockState(pos).getValue(BlockHorizontal.FACING)) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(container);
			}
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
		container.deserializeNBT(tag.getCompoundTag("cont"));
		tank.readFromNBT(tag.getCompoundTag("tank"));
		mp.readFromNbt(tag.getCompoundTag("mp"));
		progress = tag.getInteger("progress");
		currentRecipe = tag.getString("recipe");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("inv", inventory.serializeNBT());
		tag.setTag("cont", container.serializeNBT());
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
