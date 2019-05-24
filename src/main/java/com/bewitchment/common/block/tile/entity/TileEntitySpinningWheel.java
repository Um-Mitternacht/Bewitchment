package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.SpinningWheelRecipe;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
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
public class TileEntitySpinningWheel extends ModTileEntity implements ITickable {
	private final ItemStackHandler inventory_up = new ItemStackHandler(4) {
		@Override
		protected void onContentsChanged(int index) {
			recipe = GameRegistry.findRegistry(SpinningWheelRecipe.class).getValuesCollection().stream().filter(p -> p.matches(this)).findFirst().orElse(null);
		}
	};
	private final ItemStackHandler inventory_down = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return false;
		}
	};
	public int progress;
	private SpinningWheelRecipe recipe;
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (recipe == null || !recipe.isValid(inventory_up, inventory_down)) progress = 0;
			else {
				progress++;
				if (progress >= 200) {
					progress = 0;
					recipe.giveOutput(inventory_up, inventory_down);
				}
			}
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, face);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(face == EnumFacing.DOWN ? inventory_down : inventory_up) : super.getCapability(capability, face);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		recipe = tag.getString("recipe").isEmpty() ? null : GameRegistry.findRegistry(SpinningWheelRecipe.class).getValue(new ResourceLocation(tag.getString("recipe")));
		progress = tag.getInteger("progress");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("recipe", recipe == null ? "" : recipe.getRegistryName().toString());
		tag.setInteger("progress", progress);
		return super.writeToNBT(tag);
	}
	
	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory_up, inventory_down};
	}
}