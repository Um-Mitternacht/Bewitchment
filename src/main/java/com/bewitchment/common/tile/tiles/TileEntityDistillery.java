package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.crafting.DistilleryRecipe;
import com.bewitchment.common.crafting.ModDistilleryRecipes;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.JointInventoryWrapper;
import com.bewitchment.common.tile.util.JointInventoryWrapper.Mode;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class TileEntityDistillery extends ModTileEntity implements ITickable {

	public static final int BURN_TIME = 1200;

	private IMagicPowerConsumer mp = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private int progress = 0;
	private int heat = 0;
	private String currentRecipe = "";
	private int startingProgress = -1;
	private ItemStackHandler fuelInventory = new ItemStackHandler(1) {

		@Override
		protected void onContentsChanged(int slot) {
			TileEntityDistillery.this.markDirty();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack.getItem() != Items.BLAZE_POWDER) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
	};
	private ItemStackHandler inventoryOutput = new ItemStackHandler(6) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityDistillery.this.contentsChanged();
		}
	};
	private ItemStackHandler inventoryInput = new ItemStackHandler(6) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityDistillery.this.contentsChanged();
		}
	};
	private JointInventoryWrapper ioSideWrapper = new JointInventoryWrapper();
	private JointInventoryWrapper ioGuiWrapper = new JointInventoryWrapper();
	private JointInventoryWrapper ioBackWrapper = new JointInventoryWrapper();

	public TileEntityDistillery() {
		for (int i = 0; i < 6; i++) {
			this.ioSideWrapper.bind(() -> this.inventoryInput, i, Mode.INSERT);
			this.ioSideWrapper.bind(() -> this.inventoryOutput, i, Mode.EXTRACT);
			this.ioGuiWrapper.bind(() -> this.inventoryInput, i, Mode.BOTH);
			this.ioGuiWrapper.bind(() -> this.inventoryOutput, i, Mode.EXTRACT);
		}
		this.ioGuiWrapper.bind(() -> this.fuelInventory, 0, Mode.BOTH);
		this.ioBackWrapper.bind(() -> this.fuelInventory, 0, Mode.INSERT);
	}

	@Override
	public void update() {
		if (this.heat > 0) {
			this.heat--;
			this.markDirty();
		}
		if (this.currentRecipe.length() > 0) {
			if (this.heat == 0) {
				this.burnFuel();
			}
			if (this.heat > 0) {
				if (this.progress > 0) {
					if (this.mp.drainAltarFirst(this.world.getClosestPlayer(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 5, false), this.getPos(), this.world.provider.getDimension(), 2)) {
						this.progress--;
						this.markDirty();
					}
				} else {
					DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValue(new ResourceLocation(this.currentRecipe));
					if (recipe == null) {
						this.currentRecipe = "";
						this.progress = 0;
						this.startingProgress = -1;
					} else {
						for (int i = 0; i < this.inventoryInput.getSlots(); i++) {
							if (!this.inventoryInput.getStackInSlot(i).isEmpty()) {
								this.inventoryInput.extractItem(i, 1, false);
							}
						}

						for (ItemStack is : recipe.getOutputs()) {
							ItemStack remaining = is.copy();
							for (int i = 0; (i < this.inventoryOutput.getSlots()) && !remaining.isEmpty(); i++) {
								remaining = this.inventoryOutput.insertItem(i, remaining, false);
							}
						}
						this.progress = this.startingProgress;
						this.checkRecipe();
					}
				}
				this.markDirty();
			}
		}
	}

	private void burnFuel() {
		if (!this.fuelInventory.extractItem(0, 1, false).isEmpty()) {
			this.heat = BURN_TIME;
		}
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
		this.dropInventory(this.fuelInventory);
		this.dropInventory(this.inventoryInput);
		this.dropInventory(this.inventoryOutput);
	}

	protected void contentsChanged() {
		if (!this.world.isRemote) {
			this.checkRecipe();
			this.markDirty();
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(Bewitchment.instance, LibGui.DISTILLERY.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	private void checkRecipe() {
		DistilleryRecipe recipe = ModDistilleryRecipes.REGISTRY.getValuesCollection().parallelStream().filter(dr -> dr.matches(this.asList(this.inventoryInput))).findFirst().orElse(null);
		if (recipe == null) {
			this.currentRecipe = "";
			this.progress = 0;
			this.startingProgress = -1;
		} else if (!this.currentRecipe.equals(recipe.getRegistryName().toString()) && this.canOutputFit(recipe)) {
			this.currentRecipe = recipe.getRegistryName().toString();
			this.progress = recipe.getTime();
			this.startingProgress = recipe.getTime();
		}
	}

	private List<ItemStack> asList(ItemStackHandler inv) {
		ArrayList<ItemStack> list = Lists.newArrayList();
		for (int i = 0; i < inv.getSlots(); i++) {
			list.add(inv.getStackInSlot(i));
		}
		return list;
	}

	private boolean canOutputFit(DistilleryRecipe recipe) {
		ItemStackHandler simulated = new ItemStackHandler(6);
		for (int i = 0; i < this.inventoryOutput.getSlots(); i++) {
			simulated.setStackInSlot(i, this.inventoryOutput.getStackInSlot(i).copy());
		}
		for (ItemStack is : recipe.getOutputs()) {
			ItemStack remaining = is.copy();
			for (int i = 0; (i < simulated.getSlots()) && !remaining.isEmpty(); i++) {
				remaining = simulated.insertItem(i, remaining, false);
			}
			if (!remaining.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStackHandler getInputInventory() {
		return this.inventoryInput;
	}

	public ItemStackHandler getOutputInventory() {
		return this.inventoryOutput;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
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
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == this.world.getBlockState(this.pos).getValue(BlockHorizontal.FACING)) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.ioBackWrapper);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.ioSideWrapper);
		}
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(this.mp);
		}
		return super.getCapability(capability, facing);
	}

	public IItemHandler getGuiHandler() {
		return this.ioGuiWrapper;
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		this.inventoryInput.deserializeNBT(tag.getCompoundTag("inv_in"));
		this.inventoryOutput.deserializeNBT(tag.getCompoundTag("inv_out"));
		this.fuelInventory.deserializeNBT(tag.getCompoundTag("fuel"));
		this.mp.readFromNbt(tag.getCompoundTag("mp"));
		this.progress = tag.getInteger("progress");
		this.currentRecipe = tag.getString("recipe");
		this.startingProgress = tag.getInteger("recipeTime");
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("inv_in", this.inventoryInput.serializeNBT());
		tag.setTag("inv_out", this.inventoryOutput.serializeNBT());
		tag.setTag("fuel", this.fuelInventory.serializeNBT());
		tag.setTag("mp", this.mp.writeToNbt());
		tag.setInteger("progress", this.progress);
		tag.setString("recipe", this.currentRecipe);
		tag.setInteger("recipeTime", this.startingProgress);
	}

	public int getProgress() {
		return this.progress;
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		// NO-OP
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		// NO-OP
	}

	public int getStartingTime() {
		return this.startingProgress;
	}

	public int getHeat() {
		return this.heat;
	}

}
