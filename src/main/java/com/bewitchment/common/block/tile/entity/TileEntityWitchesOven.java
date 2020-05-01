package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.common.block.BlockWitchesOven;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.registry.ModObjects;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import vazkii.botania.api.item.IExoflameHeatable;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
@Optional.Interface(iface = "vazkii.botania.api.item.IExoflameHeatable", modid = "botania")
public class TileEntityWitchesOven extends ModTileEntity implements ITickable, IExoflameHeatable {
	private final ItemStackHandler inventory_down = new ItemStackHandler(2) {
		@Override
		public boolean isItemValid(int index, ItemStack stack) {
			return false;
		}
	};
	public int burnTime, fuelBurnTime, progress;
	private OvenRecipe recipe;
	private final ItemStackHandler inventory_up = new ItemStackHandler(3) {
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return this.isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return ((slot == 0 ? TileEntityFurnace.isItemFuel(stack) : slot == 2) || (slot == 1 ? stack.getItem() == ModObjects.empty_jar : slot == 2)) && (getStackInSlot(slot).isEmpty() || Util.canMerge(stack, getStackInSlot(slot)));
		}
		
		@Override
		protected void onContentsChanged(int index) {
			recipe = GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection().stream().filter(p -> p.matches(getStackInSlot(2))).findFirst().orElse(null);
		}
	};
	private boolean burning;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		recipe = tag.getString("recipe").isEmpty() ? null : GameRegistry.findRegistry(OvenRecipe.class).getValue(new ResourceLocation(tag.getString("recipe")));
		burning = tag.getBoolean("burning");
		burnTime = tag.getInteger("burnTime");
		fuelBurnTime = tag.getInteger("fuelBurnTime");
		progress = tag.getInteger("progress");
		super.readFromNBT(tag);
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, face);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(face == EnumFacing.DOWN ? inventory_down : inventory_up) : super.getCapability(capability, face);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (burning && burnTime < 1) burning = world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWitchesOven.LIT, false));
			if (burnTime > 0) burnTime--;
			else {
				if (progress > 0) {
					progress -= 2;
					if (progress < 0) progress = 0;
				}
			}
			if ((recipe == null || !recipe.isValid(inventory_up, inventory_down)) && !isFurnaceRecipe()) progress = 0;
			else {
				if (burnTime < 1) {
					int time = TileEntityFurnace.getItemBurnTime(inventory_up.getStackInSlot(0));
					if (time > 0) burnFuel(time, true);
				}
				else {
					progress++;
					if (progress >= 200) {
						progress = 0;
						if (recipe == null) outputFurnace();
						else recipe.giveOutput(world.rand, inventory_up, inventory_down);
					}
				}
			}
		}
	}
	
	private void burnFuel(int time, boolean consume) {
		burnTime = time + 1;
		fuelBurnTime = burnTime;
		if (consume) {
			ItemStack stack = inventory_up.extractItem(0, 1, false);
			if (stack.getItem() == Items.LAVA_BUCKET) inventory_up.insertItem(0, new ItemStack(Items.BUCKET), false);
		}
		if (!burning) burning = world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWitchesOven.LIT, true));
	}
	
	@Override
	@Optional.Method(modid = "botania")
	public boolean canSmelt() {
		return (recipe != null && recipe.isValid(inventory_up, inventory_down)) || isFurnaceRecipe();
	}
	
	@Override
	@Optional.Method(modid = "botania")
	public int getBurnTime() {
		return burnTime;
	}
	
	@Override
	@Optional.Method(modid = "botania")
	public void boostBurnTime() {
		burnFuel(200, false);
	}
	
	@Override
	@Optional.Method(modid = "botania")
	public void boostCookTime() {
		progress++;
	}
	
	private boolean isFurnaceRecipe() {
		ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(inventory_up.getStackInSlot(2));
		return !stack.isEmpty() && Util.canMerge(inventory_down.getStackInSlot(0), stack);
	}
	
	private void outputFurnace() {
		inventory_down.insertItem(0, FurnaceRecipes.instance().getSmeltingResult(inventory_up.getStackInSlot(2)).copy(), false);
		inventory_up.extractItem(2, 1, false);
	}
}