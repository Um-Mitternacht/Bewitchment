package com.bewitchment.common.tile;

import com.bewitchment.common.crafting.oven.OvenCrafting;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.tile.util.AutomatableInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 7/17/2017.
 */
public class TileEntityOven extends TileEntity implements ITickable {
	public int workTime;
	public int totalWorkTime;
	public boolean isBurning = false;
	public int burnTime;
	public int itemBurnTime = 0;
	protected String customName;
	public AutomatableInventory inventory = new AutomatableInventory(5) {

		@Override
		public String getName() {
			return customName;
		}

		@Override
		public boolean hasCustomName() {
			return customName != null;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TextComponentString(getName());
		}

		@Override
		public boolean canMachineInsert(int slot, ItemStack stack) {
			if (slot == 1 && TileEntityFurnace.isItemFuel(stack))
				return true;
			if (slot == 2 && stack.getItem() == ModItems.fume && stack.getMetadata() == ItemFumes.Type.empty_jar.ordinal())
				return true;
			if (slot == 0)
				return true;
			return false;
		}

		@Override
		public boolean canMachineExtract(int slot, ItemStack stack) {
			return slot > 2;
		}

		@Override
		public void onMarkDirty() {
			TileEntityOven.this.markDirty();
		}
	};

	public void dropItems() {
		for (int i = 0; i < 5; ++i) {
			final ItemStack stack = inventory.getStackInSlot(i);
			if (!world.isRemote && !stack.isEmpty()) {
				final EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
				world.spawnEntity(item);
			}
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3); // <zabi> FIXME This causes a chunk re-draw at every tick, it's very bad!
		}
		this.totalWorkTime = getWorkTime();
		if (!isBurning) {
			if (!inventory.getStackInSlot(1).isEmpty()) {
				if (TileEntityFurnace.isItemFuel(inventory.getStackInSlot(1))) {
					if (canSmelt()) {
						itemBurnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(1));
						inventory.getStackInSlot(1).shrink(1);
						isBurning = true;
					}
				}
			}
		} else if (isBurning) {
			burnTime++;
			if (burnTime >= itemBurnTime) {
				burnTime = 0;
				isBurning = false;
			}
			if (canSmelt()) {
				workTime++;
				if (workTime >= totalWorkTime) {
					workTime = 0;
					smelt();
				}
			} else if (workTime > 0) {
				workTime = 0;
			}
		}

		if (burnTime >= itemBurnTime) {
			isBurning = false;
			burnTime = 0;
		}
	}

	//Change to speed up smelting, lower = faster
	public int getWorkTime() {
		return 400;
	}

	// Returns true if the input is not empty,
	public boolean canSmelt() {
		if (!inventory.getStackInSlot(0).isEmpty()) {
			if (!OvenCrafting.instance().getSmeltResult(inventory.getStackInSlot(0)).isEmpty()) {
				ItemStack stackToBeSmelted = inventory.getStackInSlot(0);
				ItemStack stackNewOutput = OvenCrafting.instance().getSmeltResult(stackToBeSmelted);
				if (inventory.insertItemUnchecked(4, stackNewOutput, true).isEmpty()) { // empty stack returned = I can fit a whole new batch of results
					return true;
				}
			}
		}
		return false;
	}

	public void smelt() {
		ItemStack stack = inventory.getStackInSlot(0);
		ItemStack outputStack = OvenCrafting.instance().getSmeltResult(stack);
		ItemStack fumesStack = OvenCrafting.instance().getFumesResult(stack);
		inventory.decrStackSize(0, 1);
		inventory.insertItemUnchecked(4, outputStack, false);
		if (!inventory.getStackInSlot(2).isEmpty() && shouldYieldFumes()) { // If there are jars
			if (inventory.insertItemUnchecked(3, fumesStack, false).isEmpty()) // If the fumes output is full fumes will be lost
				inventory.decrStackSize(2, 1);
		}
	}

	private boolean shouldYieldFumes() {
		return true; // TODO add drop chance
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			inventory.loadFromNBT((NBTTagCompound) compound.getTag("items"));
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
		this.workTime = compound.getInteger("WorkTime");
		this.totalWorkTime = compound.getInteger("totalWorkTime");
		this.burnTime = compound.getInteger("BurnTime");
		this.itemBurnTime = compound.getInteger("itemBurnTime");
		this.isBurning = compound.getBoolean("isBurning");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", inventory.saveToNbt());

		if (inventory.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		compound.setInteger("WorkTime", (short) this.workTime);
		compound.setInteger("totalWorkTime", (short) this.totalWorkTime);
		compound.setInteger("BurnTime", (short) this.burnTime);
		compound.setInteger("itemBurnTime", (short) this.itemBurnTime);
		compound.setBoolean("isBurning", this.isBurning);
		return compound;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? true : super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}
}
