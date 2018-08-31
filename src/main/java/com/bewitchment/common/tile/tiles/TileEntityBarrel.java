package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.crafting.BarrelRecipe;
import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.api.state.enums.EnumWoodType;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.helper.ItemHandlerHelper;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NullableProblems", "unchecked"})
public class TileEntityBarrel extends ModTileEntity implements ITickable {
	private ItemStackHandler handler;
	private String recipeName = null;
	private BarrelRecipe cachedRecipe = null;
	private int brewingTime = 0;
	private int barrelType = 0;
	private int powerAbsorbed = 0;
	private int powerRequired = 0;
	private int timeRequired = 0;
	private IMagicPowerConsumer altarTracker = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();

	private FluidTank internalTank = new FluidTank(Fluid.BUCKET_VOLUME) {
		@Override
		protected void onContentsChanged() {
			if (this.getFluidAmount() == 0 || this.getFluidAmount() == Fluid.BUCKET_VOLUME) {
				TileEntityBarrel.this.markDirty();
			}
			checkRecipe();
		}
	};

	public TileEntityBarrel() {
		handler = new ItemStackHandler(7);
		internalTank.setTileEntity(this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}
		if (worldIn.isRemote) {
			return true;
		}

		worldIn.notifyBlockUpdate(pos, state, state, 3);
		ItemStack stack = playerIn.getHeldItem(hand);
		if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			IFluidHandlerItem itemHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			IFluidHandler barrelHandler = this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			FluidStack fluidInItem = itemHandler.drain(Fluid.BUCKET_VOLUME, false);
			FluidStack fluidInBarrel = barrelHandler.drain(Fluid.BUCKET_VOLUME, false);
			if ((fluidInBarrel != null && fluidInBarrel.amount > 0) && (fluidInItem == null || fluidInItem.amount == 0 || (fluidInItem.isFluidEqual(fluidInBarrel) && fluidInItem.amount < Fluid.BUCKET_VOLUME))) {
				itemHandler.fill(barrelHandler.drain(Fluid.BUCKET_VOLUME, true), true);
				playerIn.setHeldItem(hand, itemHandler.getContainer());
			} else if (fluidInItem != null && fluidInItem.amount > 0 && fluidInItem.getFluid() != null && (fluidInBarrel == null || fluidInBarrel.amount == 0 || (fluidInBarrel.amount < Fluid.BUCKET_VOLUME && fluidInBarrel.isFluidEqual(fluidInItem)))) {
				FluidStack fsin = itemHandler.drain(Fluid.BUCKET_VOLUME, true);
				if (fsin != null && fsin.amount > 0 && fsin.getFluid() != null) {
					barrelHandler.fill(fsin, true);
					playerIn.setHeldItem(hand, itemHandler.getContainer());
					playerIn.inventory.markDirty();
				}
			}
			return true;
		}
		playerIn.openGui(Bewitchment.instance, LibGui.BARREL.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void onBlockHarvested(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (worldIn.isRemote || player.isCreative()) {
			return;
		}
		ItemHandlerHelper.dropItems(handler, world, pos);
		final EntityItem block = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(ModBlocks.barrel, 1, barrelType));
		world.spawnEntity(block);
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		if (hasRecipe()) {
			BarrelRecipe currentRecipe = getRecipe();
			if (powerAbsorbed < currentRecipe.getPower()) {
				if (altarTracker.drain(null, getPos(), world.provider.getDimension(), 1)) {
					powerAbsorbed++;
					markDirty();
					return;
				}
			} else {
				if (brewingTime < currentRecipe.getRequiredTime()) {
					brewingTime++;
					markDirty();
					return;
				}

				List<ItemStack> stacks = new ArrayList<>();
				for (int i = 0; i < handler.getSlots(); i++) {
					if (i != 1) {
						stacks.add(handler.getStackInSlot(i));
					}
				}
				currentRecipe.onFinish(world, stacks, pos, internalTank.drain(1000, true));
				ItemStack output = handler.getStackInSlot(0);
				if (output.isEmpty()) {
					handler.setStackInSlot(0, currentRecipe.getResult());
				} else {
					output.grow(currentRecipe.getResult().getCount());
				}
				brewingTime = 0;
				powerAbsorbed = 0;
				recipeName = null;
				cachedRecipe = null;
				markDirty();
				checkRecipe();
			}
		}
	}

	public void checkRecipe() {
		if (this.recipeName != null && this.recipeName.length() > 0) {
			return;
		}
		List<ItemStack> stacks = new ArrayList<>();
		for (int i = 0; i < handler.getSlots(); i++) {
			if (i != 1) {
				stacks.add(handler.getStackInSlot(i));
			}
		}
		refreshRecipeStatus(BarrelRecipe.getRecipe(world, stacks, pos, internalTank.drainInternal(1000, false)));
	}

	public void refreshRecipeStatus(BarrelRecipe incomingRecipe) {
		if (incomingRecipe != null) {
			ItemStack recipeOutput = handler.getStackInSlot(0);
			if (recipeOutput.isEmpty() || recipeOutput.getMaxStackSize() >= recipeOutput.getCount() + incomingRecipe.getResult().getCount()) {
				internalTank.drain(Fluid.BUCKET_VOLUME, true);
				this.cachedRecipe = incomingRecipe;
				this.recipeName = incomingRecipe.getRegistryName().toString();
				powerRequired = cachedRecipe.getPower();
				timeRequired = cachedRecipe.getRequiredTime();
				markDirty();
			}
		}
	}

	public void setType(EnumWoodType type) {
		this.setType(type.ordinal());
	}

	public EnumWoodType getType() {
		return EnumWoodType.values()[barrelType];
	}

	public void setType(int type) {
		barrelType = type;
		markDirty();
		syncToClient();
	}

	public boolean hasRecipe() {
		return recipeName != null && recipeName.length() > 0;
	}

	public int getBrewingTime() {
		return brewingTime;
	}

	public int getPowerAbsorbed() {
		return powerAbsorbed;
	}

	public String getRecipeName() {
		return recipeName;
	}

	@Nullable
	public BarrelRecipe getRecipe() {
		if (cachedRecipe == null) {
			if (recipeName == null || recipeName.length() == 0) return null;
			cachedRecipe = BarrelRecipe.REGISTRY.getValue(new ResourceLocation(recipeName));
			if (cachedRecipe != null) {
				powerRequired = cachedRecipe.getPower();
				timeRequired = cachedRecipe.getRequiredTime();
			}
			markDirty();
		}
		return cachedRecipe;
	}

	public int getPowerRequired() {
		return powerRequired;
	}

	public int getTimeRequired() {
		return timeRequired;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		} else if (capability == IMagicPowerConsumer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(internalTank);
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
		} else if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(altarTracker);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setInteger("time", brewingTime);
		tag.setInteger("type", barrelType);
		tag.setInteger("powerAbsorbed", powerAbsorbed);
		tag.setTag("handler", handler.serializeNBT());
		tag.setTag("altar_cache", altarTracker.writeToNbt());
		NBTTagCompound fluid = new NBTTagCompound();
		internalTank.writeToNBT(fluid);
		tag.setTag("fluid", fluid);
		if (recipeName != null) tag.setString("crafting", recipeName);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		brewingTime = tag.getInteger("time");
		barrelType = tag.getInteger("type");
		powerAbsorbed = tag.getInteger("powerAbsorbed");
		handler.deserializeNBT((NBTTagCompound) tag.getTag("handler"));
		internalTank = internalTank.readFromNBT(tag.getCompoundTag("fluid"));
		altarTracker.readFromNbt(tag.getCompoundTag("altar_cache"));
		if (tag.hasKey("crafting")) {
			recipeName = tag.getString("crafting");
			getRecipe();//Refresh cache
		} else {
			recipeName = null;
		}
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setInteger("type", barrelType);
		if (recipeName != null) {
			tag.setString("recipeName", recipeName);
		}
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		barrelType = tag.getInteger("type");
		if (tag.hasKey("recipeName")) {
			recipeName = tag.getString("recipeName");
		} else {
			recipeName = null;
		}
	}

}
