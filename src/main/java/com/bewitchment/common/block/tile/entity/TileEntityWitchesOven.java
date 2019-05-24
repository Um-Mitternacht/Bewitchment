package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.common.block.BlockWitchesOven;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class TileEntityWitchesOven extends ModTileEntity implements ITickable {
	private final ItemStackHandler inventory_up = new ItemStackHandler(3) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return index == 0 ? TileEntityFurnace.isItemFuel(stack) : index != 1 || stack.getItem() == ModObjects.empty_jar;
		}
		
		@Override
		protected void onContentsChanged(int index) {
			recipe = GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection().stream().filter(p -> p.matches(getStackInSlot(2))).findFirst().orElse(null);
		}
	};
	private final ItemStackHandler inventory_down = new ItemStackHandler(2) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return false;
		}
	};
	public int burnTime, fuelBurnTime, progress;
	private OvenRecipe recipe;
	private boolean burning = false;
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (burning && burnTime < 0) burning = world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWitchesOven.LIT, false));
			if (burnTime > -1) burnTime--;
			else {
				if (progress > 0) {
					progress -= 2;
					if (progress < 0) progress = 0;
				}
			}
			if (recipe == null || !recipe.isValid(inventory_up, inventory_down)) progress = 0;
			else {
				if (burnTime < 0) {
					int time = TileEntityFurnace.getItemBurnTime(inventory_up.getStackInSlot(0));
					if (time > 0) {
						burnTime = time;
						fuelBurnTime = burnTime;
						inventory_up.extractItem(0, 1, false);
						if (!burning) burning = world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWitchesOven.LIT, true));
					}
				}
				else {
					progress++;
					if (progress >= 200) {
						progress = 0;
						recipe.giveOutput(world.rand, inventory_up, inventory_down);
					}
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
		recipe = tag.getString("recipe").isEmpty() ? null : GameRegistry.findRegistry(OvenRecipe.class).getValue(new ResourceLocation(tag.getString("recipe")));
		burning = tag.getBoolean("burning");
		burnTime = tag.getInteger("burnTime");
		fuelBurnTime = tag.getInteger("fuelBurnTime");
		progress = tag.getInteger("progress");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("recipe", recipe == null ? "" : recipe.getRegistryName().toString());
		tag.setBoolean("burning", burning);
		tag.setInteger("burnTime", burnTime);
		tag.setInteger("fuelBurnTime", fuelBurnTime);
		tag.setInteger("progress", progress);
		return super.writeToNBT(tag);
	}
	
	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory_up, inventory_down};
	}
}