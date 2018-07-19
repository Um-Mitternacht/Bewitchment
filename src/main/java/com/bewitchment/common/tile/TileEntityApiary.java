package com.bewitchment.common.tile;

import javax.annotation.Nullable;

import com.bewitchment.api.helper.ItemStackHelper;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.handler.ModSounds;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.util.AutomatableInventory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class TileEntityApiary extends ModTileEntity implements ITickable, IWorldNameable {
	private static final String HANDLER_TOP_TAG = "handlerUp";
	private static final String HANDLER_BOTTOM_TAG = "handlerDown";
	private static final String CUSTOM_NAME_TAG = "customName";

	private static final int GEN = 200;
	private String customName;
	private int flowerCount;
	private int tick;

	private ItemStackHandler handlerUp, handlerDown;

	public TileEntityApiary() {
		handlerUp = new AutomatableInventory(1) {
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack stack) {
				return this.getStackInSlot(slot).isEmpty() && stack.getItem() == ModItems.bee;
			}
		};
		handlerDown = new AutomatableInventory(18) {
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack stack) {
				return this.getStackInSlot(slot).isEmpty() && (stack.getItem() == ModItems.honeycomb || stack.getItem() == ModItems.empty_honeycomb || stack.getItem() == ModItems.honey);
			}
		};
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
			return true;
		}

		playerIn.openGui(Bewitchment.instance, LibGui.APIARY.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) {
			return;
		}

		ItemStackHelper.dropItems(handlerUp, worldIn, pos);
		ItemStackHelper.dropItems(handlerDown, worldIn, pos);
		final EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, handlerUp.getStackInSlot(0));
		world.spawnEntity(item);
		final EntityItem block = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(ModBlocks.apiary));
		world.spawnEntity(block);
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		++tick;
		final ItemStack bee = handlerUp.getStackInSlot(0);
		if (!bee.isEmpty() && bee.getItemDamage() < 35) {
			lookForFlowers();
			if (tick % 60 == 0 && world.rand.nextBoolean()) {
				world.playSound(null, pos, ModSounds.BUZZ, SoundCategory.BLOCKS, 0.1F, 1F);
			}
			if (flowerCount > 0) {
				if (world.rand.nextInt(3) == 0 && tick % (GEN - flowerCount * 3) == 0) {
					for (int i = 0; i < handlerDown.getSlots(); i++) {
						if (!(handlerDown.getStackInSlot(i).getItem() == ModItems.honeycomb)) {
							bee.attemptDamageItem(1, world.rand, null);
							bonemealArea();
							handlerDown.setStackInSlot(i, randomItem());
							break;
						}
					}
				}
				flowerCount = 0;
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
	public String getName() {
		return this.hasCustomName() ? this.customName : new TextComponentTranslation("container.apiary").getFormattedText();
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == EnumFacing.UP) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerUp);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerDown);
		}
		return null;
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound cmp) {
		if (cmp.hasKey(CUSTOM_NAME_TAG, 8)) {
			this.customName = cmp.getString(CUSTOM_NAME_TAG);
		}
		handlerUp.deserializeNBT((NBTTagCompound) cmp.getTag(HANDLER_TOP_TAG));
		handlerDown.deserializeNBT((NBTTagCompound) cmp.getTag(HANDLER_BOTTOM_TAG));
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound cmp) {
		if (this.hasCustomName()) {
			cmp.setString(CUSTOM_NAME_TAG, this.customName);
		}
		cmp.setTag(HANDLER_TOP_TAG, handlerUp.serializeNBT());
		cmp.setTag(HANDLER_BOTTOM_TAG, handlerDown.serializeNBT());
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
