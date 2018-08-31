package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.helper.ItemHandlerHelper;
import com.bewitchment.common.crafting.OvenSmeltingRecipe;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.AutomatableInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
	public static final int TOTAL_WORK = 400;
	private static final String CUSTOM_NAME_TAG = "customName";
	private static final String WORK_TIME_TAG = "work";
	private static final String IS_BURNING_TAG = "isBurning";
	private static final String BURN_TIME_TAG = "burnTime";
	private static final String ITEM_BURN_TIME_TAG = "itemBurnTime";
	private static final String HANDLER_UP_TAG = "handlerUp";
	private static final String HANDLER_SIDE_TAG = "handlerSide";
	private static final String HANDLER_DOWN_TAG = "handlerDown";
	private int work;
	private boolean isBurning = false;
	private int burnTime;
	private int itemBurnTime;
	private Random random;
	private String customName;

	private ItemStackHandler handlerUp;
	private ItemStackHandler handlerSide;
	private ItemStackHandler handlerDown;

	@SuppressWarnings("ConstantConditions")
	public TileEntityOven() {
		this.random = new Random(100);
		this.handlerUp = new AutomatableInventory(1) {
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack stack) {
				return slot == 0 && OvenSmeltingRecipe.isSmeltable(stack);
			}
		};
		this.handlerDown = new AutomatableInventory(2) {
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack stack) {
				return slot == 0 || slot == 1;

			}
		};
		this.handlerSide = new AutomatableInventory(2) {
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack stack) {
				if (slot == 0) {
					return TileEntityFurnace.isItemFuel(stack);
				} else if (slot == 1) {
					return ItemStack.areItemsEqual(stack, new ItemStack(ModItems.fume, 1, ItemFumes.Type.empty_jar.ordinal()));
				} else {
					return false;
				}
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
			this.markDirty();
			this.syncToClient();
		} else {
			playerIn.openGui(Bewitchment.instance, LibGui.OVEN.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockHarvested(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (world.isRemote || player.isCreative()) {
			return;
		}
		ItemHandlerHelper.dropItems(handlerUp, world, pos);
		ItemHandlerHelper.dropItems(handlerSide, world, pos);
		ItemHandlerHelper.dropItems(handlerDown, world, pos);
		final EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(ModBlocks.oven));
		world.spawnEntity(item);
	}

	@Override
	public void update() {
		//TODO: <rustylocks79> attempt to rewrite this method in such a way that it doesn't update every tick.
		if (world.isRemote) {
			return;
		}

		if (!isBurning) {
			if (TileEntityFurnace.isItemFuel(handlerSide.getStackInSlot(0))) {
				if (canSmelt()) {
					itemBurnTime = TileEntityFurnace.getItemBurnTime(handlerSide.getStackInSlot(0));
					handlerSide.getStackInSlot(0).shrink(1);
					isBurning = true;
					this.markDirty();
					this.syncToClient();
				}
			}
		} else {
			this.burnTime++;
			if (burnTime >= itemBurnTime) {
				this.burnTime = 0;
				this.isBurning = false;
			}
			if (canSmelt()) {
				this.work++;
				if (this.work >= TOTAL_WORK) {
					this.work = 0;
					this.smelt();
				}
			} else if (this.work > 0) {
				this.work = 0;
			}
			if (this.burnTime >= this.itemBurnTime) {
				this.isBurning = false;
				this.burnTime = 0;
			}
			this.markDirty();
			this.syncToClient();
		}
	}

	// Returns true if the input is not empty,
	private boolean canSmelt() {
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
		handlerUp.getStackInSlot(0).shrink(1);
		handlerDown.insertItem(0, outputStack, false);
		if (fumesStack != null && !handlerSide.getStackInSlot(1).isEmpty() && random.nextInt(100) <= recipe.getFumeChance()) { // If there are jars
			if (handlerDown.insertItem(1, fumesStack, false).isEmpty()) {// If the fumes output is full fumes will be lost
				handlerSide.getStackInSlot(1).shrink(1);
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
			if (facing == EnumFacing.UP) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerUp);
			} else if (facing == EnumFacing.DOWN) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerDown);
			} else if (facing == EnumFacing.NORTH || facing == EnumFacing.EAST || facing == EnumFacing.SOUTH || facing == EnumFacing.WEST) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerSide);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
		tag.setInteger(WORK_TIME_TAG, this.work);
		tag.setInteger(BURN_TIME_TAG, this.burnTime);
		tag.setInteger(ITEM_BURN_TIME_TAG, this.itemBurnTime);
		tag.setBoolean(IS_BURNING_TAG, this.isBurning);
		tag.setTag(HANDLER_UP_TAG, this.handlerUp.serializeNBT());
		tag.setTag(HANDLER_SIDE_TAG, this.handlerSide.serializeNBT());
		tag.setTag(HANDLER_DOWN_TAG, this.handlerDown.serializeNBT());
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
		this.work = tag.getInteger(WORK_TIME_TAG);
		this.burnTime = tag.getInteger(BURN_TIME_TAG);
		this.itemBurnTime = tag.getInteger(ITEM_BURN_TIME_TAG);
		this.isBurning = tag.getBoolean(IS_BURNING_TAG);
		this.handlerUp.deserializeNBT((NBTTagCompound) tag.getTag(HANDLER_UP_TAG));
		this.handlerSide.deserializeNBT((NBTTagCompound) tag.getTag(HANDLER_SIDE_TAG));
		this.handlerDown.deserializeNBT((NBTTagCompound) tag.getTag(HANDLER_DOWN_TAG));
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
		tag.setInteger(WORK_TIME_TAG, this.work);
		tag.setInteger(BURN_TIME_TAG, this.burnTime);
		tag.setInteger(ITEM_BURN_TIME_TAG, this.itemBurnTime);
		tag.setBoolean(IS_BURNING_TAG, this.isBurning);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
		this.work = tag.getInteger(WORK_TIME_TAG);
		this.burnTime = tag.getInteger(BURN_TIME_TAG);
		this.itemBurnTime = tag.getInteger(ITEM_BURN_TIME_TAG);
		this.isBurning = tag.getBoolean(IS_BURNING_TAG);
	}
}
