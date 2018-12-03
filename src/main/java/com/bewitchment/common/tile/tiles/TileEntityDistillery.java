package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.crafting.DistilleryRecipe;
import com.bewitchment.common.crafting.ModDistilleryRecipes;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.IOInventory;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDistillery extends ModTileEntity implements ITickable {

	private IMagicPowerConsumer mp = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private IOInventory inventory = new IOInventory(6, 6) {
		@Override
		protected void inventoryChanged() {
			contentsChanged();
		}
	};
	
	private ItemStackHandler container = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			contentsChanged();
		};
	};
	private FluidTank tank = new FluidTank(1000) {
		@Override
		protected void onContentsChanged() {
			contentsChanged();
		};
	};
	private int progress = 0;
	private String currentRecipe = "";
	
	private int startingProgress = -1;
	private Fluid fluidType = null;

	@Override
	public void update() {
		if (currentRecipe.length() > 0) {
			if (progress > 0) {
				if (isHeated()) {
					if (mp.drainAltarFirst(world.getClosestPlayer(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 5, false), getPos(), this.world.provider.getDimension(), 2)) {
						progress--;
						markDirty();
					}
				}
			} else {
				DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValue(new ResourceLocation(currentRecipe));
				if (recipe == null) {
					currentRecipe = "";
					progress = 0;
					startingProgress = -1;
				} else {
					tank.setFluid(recipe.getRemainingFluidStack(tank.getFluid()));
					for (int i = 0; i < inventory.getInputSlotsCount(); i++) {
						if (!inventory.getStackInSlot(i).isEmpty()) {
							inventory.getStackInSlot(i).shrink(1);
						}
					}
					for (ItemStack is:recipe.getOutputs()) {
						inventory.insertInOutputs(is, false);
					}
					checkRecipe();
				}
				markDirty();
			}
		}
	}

	private boolean isHeated() {
		IBlockState fire = world.getBlockState(getPos().down());
		return fire.getBlock() == Blocks.FIRE || fire.getBlock() == ModBlocks.witchfire;
	}

	protected void contentsChanged() {
		if ((fluidType != null && isTankFluidNull(tank)) || (tank.getFluid()!=null && tank.getFluid().getFluid() != fluidType)) {
			if (isTankFluidNull(tank)) {
				fluidType = null;
			} else {
				fluidType = tank.getFluid().getFluid();
			}
			syncToClient();
		}
		checkRecipe();
		this.markDirty();
	}
	
	private static boolean isTankFluidNull(FluidTank tank) {
		return (tank == null || tank.getFluid() == null || tank.getFluid().getFluid() == null || tank.getFluidAmount() == 0);
	}
	
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(Bewitchment.instance, LibGui.DISTILLERY.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	private void checkRecipe() {
		DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValuesCollection().parallelStream()
			.filter(dr -> dr.matches(inventory.getInputStacks(), inventory.getStackInSlot(0), tank.getFluid()))
			.findFirst().orElse(null);
		if (recipe == null) {
			currentRecipe = "";
			progress = 0;
			startingProgress = -1;
		} else if (currentRecipe != recipe.getRegistryName().toString() && canOutputFit(recipe)) {
			currentRecipe = recipe.getRegistryName().toString();
			progress = recipe.getTime();
			startingProgress = recipe.getTime();
		}
	}

	private boolean canOutputFit(DistilleryRecipe recipe) {
		IOInventory simulated = new IOInventory(0, 6);
		for (int i = 0; i < inventory.getOutputStacks().size(); i++) {
			simulated.setStackInSlot(i, inventory.getOutputStacks().get(i).copy());
		}
		for (ItemStack is:recipe.getOutputs()) {
			if (!simulated.insertInOutputs(is, false).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public ItemStackHandler getInputInventory() {
		return inventory.getInputHandler();
	}
	
	public ItemStackHandler getOutputInventory() {
		return inventory.getOutputHandler();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == world.getBlockState(pos).getValue(BlockHorizontal.FACING) || facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(container);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(mp);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		inventory.deserializeNBT(tag.getCompoundTag("inv"));
		container.deserializeNBT(tag.getCompoundTag("cont"));
		tank.readFromNBT(tag.getCompoundTag("tank"));
		mp.readFromNbt(tag.getCompoundTag("mp"));
		progress = tag.getInteger("progress");
		currentRecipe = tag.getString("recipe");
		startingProgress = tag.getInteger("recipeTime");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("inv", inventory.serializeNBT());
		tag.setTag("cont", container.serializeNBT());
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("mp", mp.writeToNbt());
		tag.setInteger("progress", progress);
		tag.setString("recipe", currentRecipe);
		tag.setInteger("recipeTime", startingProgress);
	}
	
	public int getProgress() {
		return progress;
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		String fname = fluidType==null?"null":FluidRegistry.getFluidName(fluidType);
		tag.setString("fluid", fname);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		String fname = tag.getString("fluid");
		if (fname.equals("null")) {
			fluidType = null;
		} else {
			fluidType = FluidRegistry.getFluid(fname);
		}
	}

	public int getFluidLevel() {
		return tank.getFluidAmount();
	}
	
	@SideOnly(Side.CLIENT)
	public Fluid getCurrentFluid() {
		return getCurrentFluid();
	}

	public int getStartingTime() {
		return startingProgress;
	}

}
