package com.bewitchment.common.tile;

import com.bewitchment.common.crafting.oven.OvenCrafting;
import com.bewitchment.common.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 7/17/2017.
 */
public class TileOven extends TileEntity implements ITickable {
	public int workTime;
	public int totalWorkTime;
	public boolean isBurning = false;
	public int burnTime;
	public int itemBurnTime = 0;
	public ItemStackHandler inventory = new ItemStackHandler(5) {
		@Override
		protected void onContentsChanged(int slot) {
			TileOven.this.markDirty();
		}
	};
	//come back to for input with hopper/other ducts
	//private static final int[] SLOT_TOP = new int[]{3, 4};
	//private static final int[] SLOT_BOTTOM = new int[]{0, 1, 2};
	private String customName;

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
			world.notifyBlockUpdate(pos, state, state, 3);
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
		return 512;
	}

	public boolean canSmelt() {
		if (!inventory.getStackInSlot(0).isEmpty()) {
			if (OvenCrafting.instance().getSmeltResult(inventory.getStackInSlot(0)) != ItemStack.EMPTY) {
				if (!inventory.getStackInSlot(2).isEmpty()) {
					if (inventory.getStackInSlot(2).getItem() == ModItems.glass_jar || inventory.getStackInSlot(2).getItem() == Items.GLASS_BOTTLE) {

						ItemStack stack = inventory.getStackInSlot(0);
						if (inventory.getStackInSlot(3).isEmpty() && inventory.getStackInSlot(4).isEmpty()) {
							return true;
						} else if (!inventory.getStackInSlot(3).isEmpty() && inventory.getStackInSlot(4).isEmpty()) {
							ItemStack stackFume = inventory.getStackInSlot(3);
							if (inventory.getStackInSlot(3).getCount() < 64) {
								if (stackFume == OvenCrafting.instance().getFumesResult(stack)) {
									return true;
								}
							}
						} else if (!inventory.getStackInSlot(4).isEmpty() && inventory.getStackInSlot(3).isEmpty()) {
							if (inventory.getStackInSlot(4).getCount() < 64) {
								ItemStack stackOutput = inventory.getStackInSlot(4);
								if (stackOutput == OvenCrafting.instance().getSmeltResult(stack)) {
									return true;
								}
							}
						} else if (!inventory.getStackInSlot(3).isEmpty() && !inventory.getStackInSlot(4).isEmpty()) {
							ItemStack stackFume = inventory.getStackInSlot(3);
							ItemStack stackOutput = inventory.getStackInSlot(4);
							if (inventory.getStackInSlot(3).getCount() < 64 && inventory.getStackInSlot(4).getCount() < 64) {
								if (stackFume == OvenCrafting.instance().getFumesResult(stack) && stackOutput == OvenCrafting.instance().getSmeltResult(stack)) {
									return true;
								}
							}
						} else {
							return false;
						}
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public void smelt() {
		if (canSmelt()) {
			ItemStack stack = inventory.getStackInSlot(0);
			ItemStack outputStack = OvenCrafting.instance().getSmeltResult(stack);
			ItemStack fumesStack = OvenCrafting.instance().getFumesResult(stack);

			if (inventory.getStackInSlot(3).isEmpty()) {
				inventory.setStackInSlot(3, fumesStack);
			} else {
				if (inventory.getStackInSlot(3).getCount() < 64) {
					inventory.getStackInSlot(3).grow(1);
				}
			}

			/**
			 * This is where you could add random checks for random chances
			 */
			if (inventory.getStackInSlot(4).isEmpty()) {
				inventory.setStackInSlot(4, outputStack);
			} else {
				if (inventory.getStackInSlot(4).getCount() < 64) {
					inventory.getStackInSlot(4).grow(1);
				}
			}

			inventory.getStackInSlot(0).shrink(1);
			inventory.getStackInSlot(2).shrink(1);
		}
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public String getName() {
		return this.customName;
	}

	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			inventory.deserializeNBT((NBTTagCompound) compound.getTag("items"));
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
		compound.setTag("items", inventory.serializeNBT());

		if (this.hasCustomName()) {
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
