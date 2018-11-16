package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.AutomatableInventory;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;

public class TileEntityApiary extends ModTileEntity implements ITickable {

	public static final int ROWS = 3, COLUMNS = 6;

	private boolean hasBees = false;
	private IMagicPowerConsumer mp_controller = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private ApiaryInventory hives_inventory = new ApiaryInventory();
	private AutomatableInventory modifiers_inventory = new AutomatableInventory(1) {
		@Override
		public boolean canInsertItemInSlot(int slot, ItemStack stack) {
			Item i = stack.getItem();
			return i == Items.SPIDER_EYE || i == Items.BLAZE_POWDER || i == ModItems.wool_of_bat || i == ModItems.brew_phial_linger;
		}

		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}

		;
	};

	@Override
	public void update() {
		Item modifier = modifiers_inventory.getStackInSlot(0).getItem();
		int chance = 150;
		if (modifier == Items.BLAZE_POWDER) {
			chance = 100;
		}
		if (!world.isRemote && world.getTotalWorldTime() % 20 == 0) {
			boolean hasHives = false;
			for (int i = 0; i < COLUMNS * ROWS; i++) {
				if (rng.nextInt(chance) == 0) {
					hives_inventory.setStackInSlot(i, growItem(i));
				}
			}
			hasBees = hasHives;
			syncToClient();
			markDirty();
		}
	}


	private ItemStack growItem(int i) {
		ItemStack is = hives_inventory.getStackInSlot(i);
		Item item = is.getItem();
		if (item == Items.AIR && rng.nextInt(3) == 0) {
			if (getNeighbors(i).stream().anyMatch(n -> (hives_inventory.getStackInSlot(n).getItem() != Items.AIR))) {
				return new ItemStack(ModItems.empty_honeycomb);
			}
		}

		if (item == ModItems.empty_honeycomb) {
			return new ItemStack(ModItems.honeycomb);
		}

		if (item == Items.PAPER || item == Item.getItemFromBlock(Blocks.CARPET)) {
			return new ItemStack(ModItems.empty_honeycomb);
		}

		return is;
	}


	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == IMagicPowerConsumer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(mp_controller);
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(hives_inventory);
		}
		return null;
	}

	private ArrayList<Integer> getNeighbors(int slot) {
		int i = slot % COLUMNS;
		int j = slot / COLUMNS;
		ArrayList<Integer> res = Lists.newArrayList();
		if (i > 0) res.add(slot - 1);
		if (i < COLUMNS - 1) res.add(slot + 1);
		if (j > 0) res.add(slot - COLUMNS);
		if (j < ROWS - 1) res.add(slot + COLUMNS);
		return res;
	}

	public AutomatableInventory getModifiersInventory() {
		return modifiers_inventory;
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		hives_inventory.deserializeNBT(tag.getCompoundTag("hives"));
		modifiers_inventory.deserializeNBT(tag.getCompoundTag("modifiers"));
		mp_controller.readFromNbt(tag.getCompoundTag("mp"));
		readModSyncDataNBT(tag);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("hives", hives_inventory.serializeNBT());
		tag.setTag("modifiers", modifiers_inventory.serializeNBT());
		tag.setTag("mp", mp_controller.writeToNbt());
		writeModSyncDataNBT(tag);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setBoolean("hasbees", hasBees);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		hasBees = tag.getBoolean("hasbees");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(Bewitchment.instance, LibGui.APIARY.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	public boolean hasBees() {
		return hasBees;
	}

	static class ApiaryInventory extends AutomatableInventory {

		public ApiaryInventory() {
			super(COLUMNS * ROWS);
		}

		@Override
		public boolean canInsertItemInSlot(int slot, ItemStack stack) {
			Item i = stack.getItem();
			return i == Items.PAPER || i == Item.getItemFromBlock(Blocks.CARPET) || i == ModItems.empty_honeycomb;
		}

		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}

		;

	}
}
