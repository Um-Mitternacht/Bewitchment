package com.bewitchment.common.tile;

import com.bewitchment.common.spinning.SpinningThreadRecipe;
import com.bewitchment.common.tile.util.AutomatableInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityThreadSpinner extends TileMod implements ITickable {

	public static final int MAX_TICKS = 200;
	public static final int POWER_PER_TICK = 6;

	private int tickProcessed = 0;
	private String loadedRecipe = null;
	private TileEntityWitchAltar te = null;
	private AutomatableInventory inv = new AutomatableInventory(5) {

		@Override
		public void onMarkDirty() {
			checkRecipe();
			TileEntityThreadSpinner.this.markDirty();
		}

		@Override
		public boolean canMachineInsert(int slot, ItemStack stack) {
			return slot != 0;
		}

		@Override
		public boolean canMachineExtract(int slot, ItemStack stack) {
			return slot == 0;
		}

		@Override
		public String getName() {
			return null;// TODO
		}

		@Override
		public boolean hasCustomName() {
			// TODO
			return false;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TextComponentString(getName());
		}
	};

	@Override
	protected void readDataNBT(NBTTagCompound tag) {
		inv.loadFromNBT(tag.getCompoundTag("inv"));
		if (tag.hasKey("recipe")) loadedRecipe = tag.getString("recipe");
		else loadedRecipe = null;
		tickProcessed = tag.getInteger("ticks");
	}

	@Override
	protected void writeDataNBT(NBTTagCompound tag) {
		tag.setTag("inv", inv.saveToNbt());
		if (loadedRecipe != null) tag.setString("recipe", loadedRecipe);
		tag.setInteger("ticks", tickProcessed);
	}

	@Override
	public void update() {
		if (world.isRemote) return;
		if (loadedRecipe != null && canStackResults()) {
			if ((te == null || te.isInvalid()) && world.getTotalWorldTime() % 20 == 0)
				te = TileEntityWitchAltar.getClosest(getPos(), getWorld());
			if (te == null || te.isInvalid()) {
				return;
			}
			if (te.consumePower(POWER_PER_TICK, false)) {
				tickProcessed++;
				if (tickProcessed >= MAX_TICKS) {
					ItemStack result = SpinningThreadRecipe.REGISTRY.getValue(new ResourceLocation(loadedRecipe)).getResult();
					if (inv.getStackInSlot(0).isEmpty()) inv.setInventorySlotContents(0, result);
					else {
						inv.getStackInSlot(0).grow(result.getCount());
					}
					for (int i = 1; i < 5; i++) inv.decrStackSize(i, 1);
					tickProcessed = 0;
				}

				inv.markDirty();
				markDirty();
			}
		} else {
			tickProcessed = 0;
			markDirty();
		}
	}

	private boolean canStackResults() {
		if (inv.getStackInSlot(0).isEmpty()) return true;
		ItemStack recipeResult = SpinningThreadRecipe.REGISTRY.getValue(new ResourceLocation(loadedRecipe)).getResult();
		if (ItemStack.areItemsEqual(inv.getStackInSlot(0), recipeResult) && ItemStack.areItemStackTagsEqual(inv.getStackInSlot(0), recipeResult)) {
			int sum = inv.getStackInSlot(0).getCount() + recipeResult.getCount();
			return sum <= recipeResult.getMaxStackSize();
		}
		return false;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) inv;
		return super.getCapability(capability, facing);
	}

	private void checkRecipe() {
		SpinningThreadRecipe recipe = SpinningThreadRecipe.getRecipe(NonNullList.from(ItemStack.EMPTY, new ItemStack[]{
				inv.getStackInSlot(1), inv.getStackInSlot(2), inv.getStackInSlot(3), inv.getStackInSlot(4)
		}));
		if (recipe != null) {
			loadedRecipe = recipe.getRegistryName().toString();
		} else {
			loadedRecipe = null;
		}
	}


	public AutomatableInventory getInventory() {
		return inv;
	}

	public int getTickProgress() {
		return tickProcessed;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		tickProcessed = tag.getInteger("tick");
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setInteger("tick", tickProcessed);
		return tag;
	}


}
