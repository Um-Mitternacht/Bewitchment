package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.SpinningWheelRecipe;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
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
public class TileEntitySpinningWheel extends TileEntityAltarStorage implements ITickable {
	private final ItemStackHandler inventory_down = new ItemStackHandler(2) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return false;
		}
	};
	public int progress;
	private SpinningWheelRecipe recipe;
	private final ItemStackHandler inventory_up = new ItemStackHandler(4) {
		@Override
		protected void onContentsChanged(int index) {
			recipe = GameRegistry.findRegistry(SpinningWheelRecipe.class).getValuesCollection().stream().filter(p -> p.matches(this)).findFirst().orElse(null);
		}
	};
	private boolean hasPower;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		recipe = tag.getString("recipe").isEmpty() ? null : GameRegistry.findRegistry(SpinningWheelRecipe.class).getValue(new ResourceLocation(tag.getString("recipe")));
		hasPower = tag.getBoolean("hasPower");
		progress = tag.getInteger("progress");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("recipe", recipe == null ? "" : recipe.getRegistryName().toString());
		tag.setBoolean("hasPower", hasPower);
		tag.setInteger("progress", progress);
		return super.writeToNBT(tag);
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
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory_up, inventory_down};
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (recipe == null || !recipe.isValid(inventory_down)) progress = 0;
			else {
				if (world.getTotalWorldTime() % 20 == 0) hasPower = MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 16, false), 20);
				if (hasPower) progress++;
				if (progress >= 200) {
					progress = 0;
					recipe.giveOutput(inventory_up, inventory_down);
				}
			}
		}
	}
}