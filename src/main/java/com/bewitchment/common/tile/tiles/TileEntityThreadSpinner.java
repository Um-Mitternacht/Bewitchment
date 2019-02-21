package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.crafting.SpinningThreadRecipe;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TileEntityThreadSpinner extends ModTileEntity implements ITickable, IWorldNameable {
	public static final int TOTAL_WORK = 200;
	public static final int POWER_PER_TICK = 6;
	private static final String CUSTOM_NAME_TAG = "customName";
	private static final String WORK_TAG = "work";
	private static final String HANDLER_TAG = "handler";
	private ItemStackHandler handler;
	private SpinningThreadRecipe loadedRecipe;
	private String customName = null;
	private int work = 0;
	private IMagicPowerConsumer altarTracker = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();

	public TileEntityThreadSpinner() {
		this.handler = new ItemStackHandler(5);
		this.loadedRecipe = null;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}


		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty() && (heldItem.getItem() == Items.NAME_TAG)) {
			if (!worldIn.isRemote) {
				this.customName = heldItem.getDisplayName();
				this.markDirty();
				this.syncToClient();
			}
		} else {
			playerIn.openGui(Bewitchment.instance, LibGui.THREAD_SPINNER.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
		this.dropInventory(this.handler);
	}

	@Override
	public void update() {
		if (this.world.isRemote) {
			return;
		}
		if (this.canProgress()) {
			this.work++;
			if (this.work >= TOTAL_WORK) {
				this.onFinished();
				this.work = 0;
			}
			this.syncToClient();
			this.markDirty();
		} else if (this.work != 0) {
			this.work = 0;
			this.syncToClient();
			this.markDirty();
		}
	}

	private boolean canProgress() {
		NonNullList<ItemStack> list = NonNullList.from(ItemStack.EMPTY, this.handler.getStackInSlot(1), this.handler.getStackInSlot(2), this.handler.getStackInSlot(3), this.handler.getStackInSlot(4));
		if ((this.loadedRecipe == null) || !this.loadedRecipe.matches(list)) {
			this.loadedRecipe = SpinningThreadRecipe.getRecipe(list);
		}
		return (this.loadedRecipe != null) && this.handler.insertItem(0, this.loadedRecipe.getOutput(), true).isEmpty() && this.altarTracker.drainAltarFirst(null, this.pos, this.world.provider.getDimension(), POWER_PER_TICK);
	}

	@SuppressWarnings("ConstantConditions")
	private void onFinished() {
		if (this.handler.getStackInSlot(0).isEmpty()) {
			this.handler.setStackInSlot(0, this.loadedRecipe.getOutput());
		} else {
			this.handler.getStackInSlot(0).grow(this.loadedRecipe.getOutput().getCount());
		}
		for (int i = 1; i < 5; i++) {
			this.handler.getStackInSlot(i).shrink(1);
		}
		this.loadedRecipe = null;
	}

	public int getWork() {
		return this.work;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : new TextComponentTranslation("container.thread_spinner").getFormattedText();
	}

	@Override
	public boolean hasCustomName() {
		return (this.customName != null) && !this.customName.isEmpty();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == IMagicPowerConsumer.CAPABILITY) || (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(this.altarTracker);
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.handler);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
		tag.setTag(HANDLER_TAG, this.handler.serializeNBT());
		tag.setInteger(WORK_TAG, this.work);
		tag.setTag("altar", this.altarTracker.writeToNbt());
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
		this.altarTracker.readFromNbt(tag.getCompoundTag("altar"));
		this.handler.deserializeNBT(tag.getCompoundTag(HANDLER_TAG));
		this.work = tag.getInteger(WORK_TAG);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		if (this.hasCustomName()) {
			tag.setString(CUSTOM_NAME_TAG, this.customName);
		}
		tag.setInteger(WORK_TAG, this.work);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		if (tag.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = tag.getString(CUSTOM_NAME_TAG);
		}
		this.work = tag.getInteger(WORK_TAG);
	}
}