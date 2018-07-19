package com.bewitchment.common.tile;

import com.bewitchment.api.crafting.SpinningThreadRecipe;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.capability.energy.storage.CapabilityMagicPoints;
import com.bewitchment.common.core.capability.energy.user.CapabilityMagicPointsUser;
import com.bewitchment.common.core.helper.ItemHandlerHelper;
import com.bewitchment.common.lib.LibGui;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityThreadSpinner extends ModTileEntity implements ITickable, IWorldNameable {
	public static final int MAX_TICKS = 200;
	public static final int POWER_PER_TICK = 6;
	private static final String CUSTOM_NAME_TAG = "customName";
	private static final String HANDLER_TAG = "handler";
	private int tickProcessed = 0;
	private String loadedRecipe = null;
	private String customName = null;
	private CapabilityMagicPointsUser magicPointsUser;
	private ItemStackHandler handler;

	public TileEntityThreadSpinner() {
		handler = new ItemStackHandler(5);
		magicPointsUser = new CapabilityMagicPointsUser();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}
		if (worldIn.isRemote) {
			return true;
		}

		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty() && heldItem.getItem() == Items.NAME_TAG) {
			this.customName = heldItem.getDisplayName();
			this.markDirty();
			this.syncToClient();
		} else {
			playerIn.openGui(Bewitchment.instance, LibGui.THREAD_SPINNER.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) {
			return;
		}
		ItemHandlerHelper.dropItems(handler, world, pos);
		final EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(ModBlocks.thread_spinner));
		world.spawnEntity(item);
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		//TODO: this needs to be rewritten.
		checkRecipe();
		if (loadedRecipe != null && canStackResults()) {
			if (world.getTotalWorldTime() % 20 == 0) {
				if (!magicPointsUser.hasValidAltar(world)) {
					if (!magicPointsUser.findClosestAltar(this.pos, this.world)) {
						return;
					}
					this.markDirty();
				}
			}

			if (magicPointsUser.hasValidAltar(world)) {
				CapabilityMagicPoints magicPoints = magicPointsUser.getAltar(this.world);
				if (magicPoints.subtract(POWER_PER_TICK)) {
					tickProcessed++;
					if (tickProcessed >= MAX_TICKS) {
						ItemStack result = SpinningThreadRecipe.REGISTRY.getValue(new ResourceLocation(loadedRecipe)).getResult();
						if (handler.getStackInSlot(0).isEmpty()) handler.setStackInSlot(0, result);
						else {
							handler.getStackInSlot(0).grow(result.getCount());
						}
						for (int i = 1; i < 5; i++) handler.getStackInSlot(i).shrink(1);
						tickProcessed = 0;
						loadedRecipe = null;
					}
					this.syncToClient();
					markDirty();
				}
			}
		} else if (tickProcessed != 0) {
			tickProcessed = 0;
			this.syncToClient();
			markDirty();
		}
	}

	private boolean canStackResults() {
		if (handler.getStackInSlot(0).isEmpty()) return true;
		ItemStack recipeResult = SpinningThreadRecipe.REGISTRY.getValue(new ResourceLocation(loadedRecipe)).getResult();
		if (ItemStack.areItemsEqual(handler.getStackInSlot(0), recipeResult) && ItemStack.areItemStackTagsEqual(handler.getStackInSlot(0), recipeResult)) {
			int sum = handler.getStackInSlot(0).getCount() + recipeResult.getCount();
			return sum <= recipeResult.getMaxStackSize();
		}
		return false;
	}

	private void checkRecipe() {
		SpinningThreadRecipe recipe = SpinningThreadRecipe.getRecipe(NonNullList.from(ItemStack.EMPTY, new ItemStack[]{
				handler.getStackInSlot(1), handler.getStackInSlot(2), handler.getStackInSlot(3), handler.getStackInSlot(4)
		}));
		if (recipe != null) {
			loadedRecipe = recipe.getRegistryName().toString();
		} else {
			loadedRecipe = null;
		}
	}

	public int getTickProgress() {
		return tickProcessed;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : new TextComponentTranslation("container.thread_spinner").getFormattedText();
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		//TODO: <rustylocks79> update to new magic points system.
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		//TODO: <rustylocks79> update to new magic points system.
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
		tag.setTag(HANDLER_TAG, handler.serializeNBT());
		if (loadedRecipe != null) tag.setString("crafting", loadedRecipe);
		tag.setInteger("ticks", tickProcessed);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
		handler.deserializeNBT(tag.getCompoundTag(HANDLER_TAG));
		if (tag.hasKey("crafting")) loadedRecipe = tag.getString("crafting");
		else loadedRecipe = null;
		tickProcessed = tag.getInteger("ticks");
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
		tag.setInteger("tick", tickProcessed);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
		tickProcessed = tag.getInteger("tick");
	}
}
