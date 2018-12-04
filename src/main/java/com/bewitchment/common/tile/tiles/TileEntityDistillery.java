package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.crafting.DistilleryRecipe;
import com.bewitchment.common.crafting.ModDistilleryRecipes;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.IOInventory;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
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
	
	private ItemStackHandler container = new ItemStackHandler(2) {
		@Override
		protected void onContentsChanged(int slot) {
			contentsChanged();
		};
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (slot == 1 && !stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		};
		
		@Override
		public int getSlotLimit(int slot) {
			if (slot == 0) {
				return 1;
			}
			return super.getSlotLimit(slot);
		};
	};
	private int progress = 0;
	private String currentRecipe = "";
	
	private int startingProgress = -1;

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
					startingProgress = -1;
				} else {
					for (int i = 0; i < inventory.getInputSlotsCount(); i++) {
						if (!inventory.getStackInSlot(i).isEmpty()) {
							inventory.getStackInSlot(i).shrink(1);
						}
					}
					
					if (drain(container.getStackInSlot(0))) {
						ItemStack split = container.getStackInSlot(0).splitStack(1);
						container.insertItem(1, containerItem(split), false);
					}
					
					for (ItemStack is:recipe.getOutputs()) {
						inventory.insertInOutputs(is, false);
					}
					progress = startingProgress;
					checkRecipe();
				}
				markDirty();
			}
		}
	}

	private ItemStack containerItem(ItemStack split) {
		if (split.getItem().hasContainerItem(split)) {
			return split.getItem().getContainerItem(split);
		}
		return split;
	}

	private boolean drain(ItemStack stack) {
		IFluidHandlerItem fh = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (fh == null) {
			return false;
		}
		if (fh.drain(Fluid.BUCKET_VOLUME, false) != null && fh.drain(Fluid.BUCKET_VOLUME, false).amount == Fluid.BUCKET_VOLUME) {
			fh.drain(Fluid.BUCKET_VOLUME, true);
		} else {
			return false;
		}
		return fh.drain(1, false) == null || fh.drain(1, false).amount == 0;
	}

	private boolean isHeated() {
		IBlockState fire = world.getBlockState(getPos().down());
		return fire.getBlock() == Blocks.FIRE || fire.getBlock() == ModBlocks.witchfire;
	}

	protected void contentsChanged() {
		if (!world.isRemote) {
			checkRecipe();
			this.markDirty();
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(Bewitchment.instance, LibGui.DISTILLERY.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	private void checkRecipe() {
		DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValuesCollection().parallelStream()
			.filter(dr -> dr.matches(inventory.getInputStacks(), inventory.getStackInSlot(0)))
			.findFirst().orElse(null);
		if (recipe == null) {
			currentRecipe = "";
			progress = 0;
			startingProgress = -1;
		} else if (!currentRecipe.equals(recipe.getRegistryName().toString()) && canOutputFit(recipe)) {
			currentRecipe = recipe.getRegistryName().toString();
			Log.i("nr: "+currentRecipe);
			progress = recipe.getTime();
			startingProgress = recipe.getTime();
		}
	}

	private boolean canOutputFit(DistilleryRecipe recipe) {
		IOInventory simulated = new IOInventory(0, 6);
		for (int i = 0; i < inventory.getOutputStacks().size(); i++) {
			simulated.setStackInSlot(i, inventory.getOutputStacks().get(i).copy());
		}
		for (ItemStack is:recipe.getOutputs()) {
			if (!simulated.insertInOutputs(is, false).isEmpty()) {
				return false;
			}
		}
		
		ItemStack copy = container.getStackInSlot(0).copy();
		if (copy.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			IFluidHandler fh = copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (fh.drain(1000, false) != null && fh.drain(1000, true).amount == 1000) {
				if ((fh.drain(1, false) == null || fh.drain(1, false).amount == 0) && !container.insertItem(1, copy.splitStack(1), true).isEmpty()) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public ItemStackHandler getInputInventory() {
		return inventory.getInputHandler();
	}
	
	public ItemStackHandler getOutputInventory() {
		return inventory.getOutputHandler();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
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
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == world.getBlockState(pos).getValue(BlockHorizontal.FACING) || facing == null) {
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
		mp.readFromNbt(tag.getCompoundTag("mp"));
		progress = tag.getInteger("progress");
		currentRecipe = tag.getString("recipe");
		startingProgress = tag.getInteger("recipeTime");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("inv", inventory.serializeNBT());
		tag.setTag("cont", container.serializeNBT());
		tag.setTag("mp", mp.writeToNbt());
		tag.setInteger("progress", progress);
		tag.setString("recipe", currentRecipe);
		tag.setInteger("recipeTime", startingProgress);
	}
	
	public int getProgress() {
		return progress;
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
	}

	public int getStartingTime() {
		return startingProgress;
	}

}
