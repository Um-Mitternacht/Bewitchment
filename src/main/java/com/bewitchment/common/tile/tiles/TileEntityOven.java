package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.helper.ItemHandlerHelper;
import com.bewitchment.common.crafting.OvenSmeltingRecipe;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Joseph on 7/17/2017.
 */
@SuppressWarnings("NullableProblems")
public class TileEntityOven extends ModTileEntity implements ITickable, IWorldNameable {
	//Change to speed up smelting, lower = faster
	public static final int TOTAL_WORK = 100;
	private static final String CUSTOM_NAME_TAG = "customName";
	private static final String WORK_TIME_TAG = "work";
	private static final String IS_BURNING_TAG = "isBurning";
	private static final String IS_WORKING_TAG = "isWorking";
	private static final String BURN_TIME_TAG = "burnTime";
	private static final String ITEM_BURN_TIME_TAG = "itemBurnTime";
	private static final String HANDLER_UP_TAG = "handlerUp";
	private static final String HANDLER_DOWN_TAG = "handlerDown";
	private int work;
	private boolean isBurning = false;
	private boolean isWorking = false;
	private int burnTime;
	private int itemBurnTime;
	private Random random;
	private String customName;

	private ItemStackHandler handlerUp;
	private ItemStackHandler handlerDown;

	@SuppressWarnings("ConstantConditions")
	public TileEntityOven() {
		this.random = new Random();
		this.handlerDown = new ItemStackHandler(2);
		this.handlerUp = new ItemStackHandler(3) {
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if ((slot == 0 && !OvenSmeltingRecipe.isSmeltable(stack)) || (slot == 1 && !TileEntityFurnace.isItemFuel(stack))) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}

			@Override
			protected void onContentsChanged(int slot) {
				if (slot != 2) {
					checkRecipe();
				}
				;
			}
		};
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}

		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty() && heldItem.getItem() == Items.NAME_TAG) {
			this.customName = heldItem.getDisplayName();
			if (!playerIn.isCreative()) {
				heldItem.shrink(1);
			}
			this.markDirty();
			syncToClient();
		} else {
			playerIn.openGui(Bewitchment.instance, LibGui.OVEN.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.getGameRules().getBoolean("doTileDrops")) {
			ItemHandlerHelper.dropItems(handlerUp, world, pos);
			ItemHandlerHelper.dropItems(handlerDown, world, pos);
		}
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			if (isBurning) {
				this.burnTime++;
				if (isWorking) {
					this.work++;
					if (this.work >= TOTAL_WORK) {
						this.work = 0;
						isWorking = false;
						this.smelt();
						checkRecipe();
					}
				}
				if (burnTime >= itemBurnTime) {
					this.burnTime = 0;
					this.itemBurnTime = 0;
					this.isBurning = false;
					itemBurnTime = consumeFuel();
					if (itemBurnTime > 0) {
						isBurning = true;
					}
				}
				this.markDirty();
			}
		}
	}


	protected void checkRecipe() {
		if (hasWorkToDo()) {
			if (!isBurning) {
				itemBurnTime = consumeFuel();
				if (itemBurnTime > 0) {
					isBurning = true;
				}
			}
			if (isBurning) {
				isWorking = true;
			} else {
				isWorking = false;
			}
		} else {
			isWorking = false;
		}
		markDirty();
	}


	private int consumeFuel() {
		ItemStack fuel = handlerUp.getStackInSlot(1);
		if (TileEntityFurnace.isItemFuel(fuel)) {
			int time = TileEntityFurnace.getItemBurnTime(fuel);
			handlerUp.extractItem(1, 1, false);
			return time;
		}
		return 0;
	}

	private boolean hasWorkToDo() {
		ItemStack stackToBeSmelted = handlerUp.getStackInSlot(0);
		if (!stackToBeSmelted.isEmpty()) {
			OvenSmeltingRecipe recipe = OvenSmeltingRecipe.getRecipe(stackToBeSmelted);
			if (recipe != null) {
				ItemStack stackNewOutput = recipe.getOutput();
				return handlerDown.insertItem(0, stackNewOutput, true).isEmpty();
			}
		}
		return false;
	}

	@SuppressWarnings("ConstantConditions")
	private void smelt() {
		ItemStack stack = handlerUp.getStackInSlot(0);
		OvenSmeltingRecipe recipe = OvenSmeltingRecipe.getRecipe(stack);
		final ItemStack outputStack = recipe.getOutput();
		final ItemStack fumesStack = recipe.getFumes();
		handlerUp.extractItem(0, 1, false);
		handlerDown.insertItem(0, outputStack, false);
		if (fumesStack != null && !handlerUp.getStackInSlot(2).isEmpty() && random.nextFloat() <= recipe.getFumeChance()) { // If there are jars
			if (handlerDown.insertItem(1, fumesStack, false).isEmpty()) {// If the fumes output is full fumes will be lost
				handlerUp.extractItem(2, 1, false);
			}
		}
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : new TextComponentTranslation("container.oven").getFormattedText();
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	public int getWork() {
		return work;
	}

	public boolean isBurning() {
		return isBurning;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public int getItemBurnTime() {
		return itemBurnTime;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == EnumFacing.DOWN) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerDown);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerUp);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		writeModSyncDataNBT(tag);
		tag.setInteger(WORK_TIME_TAG, this.work);
		tag.setInteger(BURN_TIME_TAG, this.burnTime);
		tag.setInteger(ITEM_BURN_TIME_TAG, this.itemBurnTime);
		tag.setBoolean(IS_BURNING_TAG, this.isBurning);
		tag.setBoolean(IS_WORKING_TAG, isWorking);
		tag.setTag(HANDLER_UP_TAG, this.handlerUp.serializeNBT());
		tag.setTag(HANDLER_DOWN_TAG, this.handlerDown.serializeNBT());
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		readModSyncDataNBT(tag);
		this.work = tag.getInteger(WORK_TIME_TAG);
		this.burnTime = tag.getInteger(BURN_TIME_TAG);
		this.itemBurnTime = tag.getInteger(ITEM_BURN_TIME_TAG);
		this.isBurning = tag.getBoolean(IS_BURNING_TAG);
		this.isWorking = tag.getBoolean(IS_WORKING_TAG);
		this.handlerUp.deserializeNBT((NBTTagCompound) tag.getTag(HANDLER_UP_TAG));
		this.handlerDown.deserializeNBT((NBTTagCompound) tag.getTag(HANDLER_DOWN_TAG));
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
	}
}
