package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TileEntityApiary extends ModTileEntity implements ITickable {

	public static final int ROWS = 3, COLUMNS = 6;

	private IMagicPowerConsumer mp_controller = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();
	private ApiaryInventory hives_inventory = new ApiaryInventory(this);
	private boolean hasBees = false;

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
		this.dropInventory(this.hives_inventory);
	}

	@Override
	public void update() {
		if (!world.isRemote && world.getTotalWorldTime() % 20 == 0 && hasBees) {
			List<BlockPos> crops = Lists.newArrayList();
			List<BlockPos> flowers = Lists.newArrayList();
			BlockPos.getAllInBox(getPos().add(-2, -2, -2), getPos().add(2, 2, 2)).forEach(pos -> {
				IBlockState state = world.getBlockState(pos);
				if (state.getBlock() instanceof BlockCrops) {
					crops.add(pos);
				}
				if (state.getBlock() instanceof BlockFlower) {
					flowers.add(pos);
				}
			});
			if (flowers.isEmpty()) return;
			IMagicPowerConsumer consumer = getCapability(IMagicPowerConsumer.CAPABILITY, null);
			assert consumer != null;
			for (BlockPos cropPos : crops) {
				if (rng.nextInt(5) == 0) {
					IBlockState state = world.getBlockState(cropPos);
					BlockCrops cropBlock = (BlockCrops) state.getBlock();
					if (cropBlock.canUseBonemeal(world, rng, cropPos, state) && consumer.drainAltarFirst(null, getPos(), world.provider.getDimension(), 50)) {
						cropBlock.grow(world, rng, cropPos, state);
					}
				}
			}
			for (BlockPos flowerPos : flowers) {
				if (rng.nextInt(100) == 0) {
					IBlockState state = world.getBlockState(flowerPos);
					BlockFlower cropFlower = (BlockFlower) state.getBlock();
					EnumFacing facingOffset = EnumFacing.random(rng);
					BlockPos flowerPosOffset = flowerPos.offset(facingOffset);
					if (cropFlower.canPlaceBlockAt(world, flowerPosOffset) && consumer.drainAltarFirst(null, getPos(), world.provider.getDimension(), 30)) {
						world.setBlockState(flowerPosOffset, state);
					}
				}
			}
			boolean update = false;
			for (int i = 0; i < COLUMNS * ROWS; i++) {
				if (rng.nextInt(100) == 0) {
					ItemStack oldStack = hives_inventory.getStackInSlot(i);
					ItemStack newStack = growItem(i);
					if (oldStack != newStack && consumer.drainAltarFirst(null, getPos(), world.provider.getDimension(), 40)) {
						hives_inventory.setStackInSlot(i, newStack);
						update = true;
					}
				}
			}
			if (update) {
				syncToClient();
				markDirty();
			}
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
		if (item == Items.ITEM_FRAME) {
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

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		hives_inventory.deserializeNBT(tag.getCompoundTag("hives"));
		mp_controller.readFromNbt(tag.getCompoundTag("mp"));
		readModSyncDataNBT(tag);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("hives", hives_inventory.serializeNBT());
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

	static class ApiaryInventory extends ItemStackHandler {

		private final TileEntityApiary tile;

		public ApiaryInventory(TileEntityApiary tile) {
			super(COLUMNS * ROWS);
			this.tile = tile;
		}

		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			Item item = stack.getItem();
			return item == ModItems.empty_honeycomb || item == ModItems.honeycomb || item == Items.ITEM_FRAME;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			Item i = stack.getItem();
			if (i == Items.ITEM_FRAME || i == ModItems.empty_honeycomb) {
				return super.insertItem(slot, stack, simulate);
			}
			return stack;
		}

		@Override
		protected void onContentsChanged(int slot) {
			ItemStack stack = getStackInSlot(slot);
			tile.hasBees = !stack.isEmpty();
			tile.syncToClient();
			tile.markDirty();
		}

		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}
	}
}
