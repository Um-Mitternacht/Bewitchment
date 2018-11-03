package com.bewitchment.common.tile.tiles;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.BrewData.ApplicationType;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemTaglock;
import com.bewitchment.common.lib.LibDamageSources;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.AutomatableInventory;
import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

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
		public int getSlotLimit(int slot) {return 1;};
	};

	@Override
	public void update() {
		Item modifier = modifiers_inventory.getStackInSlot(0).getItem();
		int time = 200;
		if (modifier == Items.BLAZE_POWDER) {
			time = 100;
		}
		if (!world.isRemote && world.getTotalWorldTime() % time == 0) {
			ApiaryInventory ai = new ApiaryInventory();
			List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(5));
			EntityLivingBase stingedEntity = null;
			if (!list.isEmpty()) {
				stingedEntity = list.get(world.rand.nextInt(list.size()));
			}
			boolean shouldTaglock = modifier == ModItems.wool_of_bat;
			if (modifier == ModItems.brew_phial_linger && stingedEntity != null) {
				BrewData.fromStack(modifiers_inventory.getStackInSlot(0)).applyToEntity(stingedEntity, null, null, ApplicationType.LINGERING);
			}
			boolean hasHives = false;
			for (int i = 0; i < COLUMNS*ROWS; i++) {
				Item found = hives_inventory.getStackInSlot(i).getItem();
				if (found == ModItems.honeycomb || found == ModItems.empty_honeycomb) {
					hasHives = true;
					ArrayList<Integer> neighbors = getNeighbors(i);
					for (int s:neighbors) {
						if (hives_inventory.getStackInSlot(s).isEmpty()) {
							growSlot(s, ai, null);
						}
					}
					growSlot(i, ai, shouldTaglock?stingedEntity:null);
				}
			}
			
			if (hasHives) {
				for (int k = 0; k < ai.getSlots(); k++) {
					if (!ai.getStackInSlot(k).isEmpty()) {
						hives_inventory.setStackInSlot(k, ai.getStackInSlot(k));
					}
				}
				if (stingedEntity != null) {
					stingedEntity.attackEntityFrom(LibDamageSources.BEES, modifier == Items.BLAZE_POWDER ? 4f : 1f);
				}
			}
			hasBees = hasHives;
			syncToClient();
			markDirty();
		}
	}

	private void growSlot(int s, ApiaryInventory ai, EntityLivingBase taglocked) {
		if (mp_controller.drainAltarFirst(null, pos, world.provider.getDimension(), world.rand.nextInt(1000))) {
			Item found = hives_inventory.getStackInSlot(s).getItem();
			if (found == Items.PAPER || found == Item.getItemFromBlock(Blocks.CARPET)) {
				if (world.rand.nextInt(40)==0) {
					ai.setStackInSlot(s, new ItemStack(ModItems.empty_honeycomb));
				}
			} else if (found == Items.AIR) {
				if (world.rand.nextInt(20)==0) {
					ai.setStackInSlot(s, new ItemStack(ModItems.empty_honeycomb));
				}
			} else if (found == ModItems.empty_honeycomb) {
				if (world.rand.nextInt(20)==0) {
					if (taglocked == null) {
						ai.setStackInSlot(s, new ItemStack(ModItems.honeycomb));
					} else {
						ItemStack tl = new ItemStack(ModItems.taglock);
						ItemTaglock.setVictim(tl, taglocked);
						ai.setStackInSlot(s, tl);
					}
				}
			}
		}
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
			super(COLUMNS*ROWS);
		}

		@Override
		public boolean canInsertItemInSlot(int slot, ItemStack stack) {
			Item i = stack.getItem();
			return i == Items.PAPER || i == Item.getItemFromBlock(Blocks.CARPET) || i == ModItems.empty_honeycomb;
		}

		@Override
		public int getSlotLimit(int slot) {return 1;};

	}
}
