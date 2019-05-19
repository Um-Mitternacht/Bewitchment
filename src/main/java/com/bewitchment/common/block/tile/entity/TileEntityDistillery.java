package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.DistilleryRecipe;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class TileEntityDistillery extends ModTileEntity implements ITickable {
	private final ItemStackHandler inventory_side = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return stack.getItem() == Items.BLAZE_POWDER;
		}
	};
	private final ItemStackHandler inventory_down = new ItemStackHandler(6) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return false;
		}
	};
	private final ItemStackHandler inventory_up = new ItemStackHandler(6) {
		@Override
		protected void onContentsChanged(int index) {
			recipe = GameRegistry.findRegistry(DistilleryRecipe.class).getValuesCollection().stream().filter(p -> p.matches(this)).findFirst().orElse(null);
		}
	};

	public int burnTime, progress;
	private DistilleryRecipe recipe;

	@Override
	public void update() {
		if (!world.isRemote) {
			if (burnTime > -1) burnTime--;
			else if (progress > 0) {
				progress -= 2;
				if (progress < 0) progress = 0;
			}
			if (recipe == null || !recipe.isValid(inventory_up, inventory_down)) progress = 0;
			else {
				if (burnTime > -1) {
					progress++;
					if (progress >= 199) {
						progress = 0;
						recipe.giveOutput(inventory_up, inventory_down);
					}
				}
				else {
					if (!ModTileEntity.isEmpty(inventory_side)) {
						burnTime = 1200;
						inventory_side.extractItem(0, 1, false);
					}
				}
			}
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(face == EnumFacing.DOWN ? inventory_down : face == EnumFacing.UP ? inventory_up : inventory_side) : super.getCapability(capability, face);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, face);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("recipe", recipe == null ? "" : recipe.getRegistryName().toString());
		tag.setInteger("burnTime", burnTime);
		tag.setInteger("progress", progress);
		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		recipe = tag.getString("recipe").isEmpty() ? null : GameRegistry.findRegistry(DistilleryRecipe.class).getValue(new ResourceLocation(tag.getString("recipe")));
		burnTime = tag.getInteger("burnTime");
		progress = tag.getInteger("progress");
	}

	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory_side, inventory_up, inventory_down};
	}
}