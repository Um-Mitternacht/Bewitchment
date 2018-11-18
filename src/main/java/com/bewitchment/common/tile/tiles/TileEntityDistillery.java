package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.AutomatableInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityDistillery extends ModTileEntity implements ITickable {

	private AutomatableInventory inputs = new AutomatableInventory(2) {

		@Override
		public boolean canInsertItemInSlot(int slot, ItemStack stack) {
			return false;
		}
	};

	private AutomatableInventory outputs = new AutomatableInventory(4) {

		@Override
		public boolean canInsertItemInSlot(int slot, ItemStack stack) {
			return false;
		}
	};

	private int progress = 0;

	@Override
	public void update() {

	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		inputs.deserializeNBT(tag.getCompoundTag("inputs"));
		outputs.deserializeNBT(tag.getCompoundTag("outputs"));
		progress = tag.getInteger("progress");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("outputs", outputs.serializeNBT());
		tag.setTag("inputs", inputs.serializeNBT());
		tag.setInteger("progress", progress);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {

	}

}
