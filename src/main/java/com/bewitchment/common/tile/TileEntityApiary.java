package com.bewitchment.common.tile;

import com.bewitchment.client.gui.container.ContainerApiary;
import com.bewitchment.client.sound.ModSounds;
import com.bewitchment.common.core.helper.ItemNullHelper;
import com.bewitchment.common.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class TileEntityApiary extends TileEntityLockable implements ITickable, ISidedInventory {

	private static final int[] SLOT_TOP = new int[]{0};
	private static final int[] SLOT_BOTTOM = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
	private static final int GEN = 1000;
	private List<ItemStack> itemStacks = ItemNullHelper.asList(19);
	private String customName;
	private int flowerCount;
	private int tick;
	private EntityPlayerMP player;

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.UP ? SLOT_TOP : SLOT_BOTTOM;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return direction == EnumFacing.UP && isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return direction == EnumFacing.DOWN && index > 0;
	}

	@Override
	public int getSizeInventory() {
		return 19;
	}

	@Override
	public boolean isEmpty() {
		return ItemNullHelper.isEmpty(itemStacks);
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return itemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(itemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(itemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		final boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStacks.get(index)) && ItemStack.areItemStackTagsEqual(stack, itemStacks.get(index));
		itemStacks.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 ? stack.getItem() == ModItems.bee
				: stack.getItem() == ModItems.honeycomb
				|| stack.getItem() == ModItems.empty_honeycomb
				|| stack.getItem() == ModItems.bee;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < 16; ++i) {
			this.itemStacks.set(i, ItemStack.EMPTY);
		}
	}

	public void dropItems() {
		for (int i = 0; i < 16; ++i) {
			final ItemStack stack = itemStacks.get(i);
			if (!world.isRemote && !stack.isEmpty()) {
				final EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
				world.spawnEntity(item);
			}
			itemStacks.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			++tick;
			final ItemStack bee = getStackInSlot(0);
			if (!bee.isEmpty() && bee.getItemDamage() < 35) {
				lookForFlowers();
				if (tick % 60 == 0 && world.rand.nextBoolean()) {
					world.playSound(null, pos, ModSounds.BUZZ, SoundCategory.BLOCKS, 0.1F, 1F);
				}
				if (flowerCount > 0) {
					if (world.rand.nextInt(3) == 0 && tick % (GEN - flowerCount * 3) == 0) {
						for (int i = 1; i < 16; i++) {
							if (!getStackInSlot(i).isEmpty()) {
								bee.attemptDamageItem(1, world.rand, player);
								bonemealArea();

								itemStacks.set(i, randomItem());
								break;
							}
						}
					}
					flowerCount = 0;
				}
			}
		}
	}

	private void lookForFlowers() {
		getArea().forEach(
				pos -> {
					final Block block = world.getBlockState(pos).getBlock();
					if ((block instanceof BlockCrops) || block == Blocks.RED_FLOWER || block == Blocks.YELLOW_FLOWER) {
						if (++flowerCount > 200) flowerCount = 200;
					}
				}
		);
	}

	private void bonemealArea() {
		getArea().forEach(
				pos -> {
					final IBlockState state = world.getBlockState(pos);
					if (state.getBlock() instanceof BlockCrops && world.rand.nextInt(400 - flowerCount) == 0) {
						if (((BlockCrops) state.getBlock()).canGrow(world, pos, state, false)) {
							((BlockCrops) state.getBlock()).grow(world, world.rand, pos, state);
						}
					}
				}
		);
	}

	private ItemStack randomItem() {
		final Item item = world.rand.nextBoolean()
				? (world.rand.nextInt(8) == 0 ? ModItems.bee : ModItems.honeycomb)
				: ModItems.empty_honeycomb;
		return new ItemStack(item);
	}

	private Iterable<BlockPos> getArea() {
		final BlockPos posI = getPos().add(-5, -5, -5);
		final BlockPos posF = getPos().add(5, 5, 5);
		return BlockPos.getAllInBox(posI, posF);
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerApiary(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return "bewitchment:apiary";
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : new TextComponentTranslation("container.apiary").getFormattedText();
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	public void setCustomInventoryName(String name) {
		this.customName = name;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		final NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.itemStacks = ItemNullHelper.asList(19);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			final NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			final int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < 19) {
				this.itemStacks.set(j, new ItemStack(nbttagcompound));
			}
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		final NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < 19; ++i) {
			if (!itemStacks.get(i).isEmpty()) {
				final NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				itemStacks.get(i).writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}
}
