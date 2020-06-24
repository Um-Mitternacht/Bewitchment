package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.api.event.CurseEvent;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.api.registry.Incense;
import com.bewitchment.common.block.BlockBrazier;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.ItemTaglock;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBrazier extends ModTileEntity implements ITickable {
	public ItemStackHandler handler;
	public Incense incense;
	private int litTime;

	public TileEntityBrazier() {
		this.handler = new ItemStackHandler(9);
		this.incense = null;
		this.litTime = 0;
	}

	private void getIncense() {
		Incense incense = GameRegistry.findRegistry(Incense.class).getValuesCollection().stream().filter(p -> p.matches(handler)).findFirst().orElse(null);
		if (incense != null) {
			this.incense = incense;
			this.litTime = 0;
		}
		markDirty();
	}

	private void curse(EntityPlayer caster) {
		Curse curse = GameRegistry.findRegistry(Curse.class).getValuesCollection().stream().filter(p -> p.matches(handler)).findFirst().orElse(null);
		if (curse != null) {
			EntityPlayer target = null;
			for (int i = 0; i < handler.getSlots(); i++) {
				target = ItemTaglock.getPlayerFromTaglock(handler.getStackInSlot(i));
				if (target != null) break;
			}
			if (target != null) {
				int days = 7;
				CurseEvent.PlayerCursedEvent event = new CurseEvent.PlayerCursedEvent(target, caster, curse, days);
				MinecraftForge.EVENT_BUS.post(event);
				if (!event.isCanceled()) event.getCurse().apply(event.getTarget(), event.getCurseDuration());
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.readUpdateTag(tag);
		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		this.writeUpdateTag(tag);
		return super.writeToNBT(tag);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeUpdateTag(tag);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeUpdateTag(tag);
		return tag;
	}

	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		NBTTagCompound tag = packet.getNbtCompound();
		readUpdateTag(tag);
	}

	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		IBlockState state = world.getBlockState(pos);
		if (!state.getValue(BlockBrazier.LIT)) {
			if (!player.isSneaking()) {
				if (player.getHeldItem(hand).getItem() instanceof ItemFlintAndSteel) {
					if (!isEmpty(handler)) {
						world.setBlockState(pos, state.withProperty(BlockBrazier.LIT, true));
						if (player.world.isRemote)
							player.world.playSound(player, pos, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						getIncense();
						if (incense == null) curse(player);
						clearBrazier();
						markDirty();
						return true;
					}
					return false;
				} else {
					int slot = getFirstEmptySlot(handler);
					if (slot != -1) {
						Item heldItem = player.getHeldItem(hand).getItem();
						if (heldItem.getContainerItem() != null)
							Util.giveItem(player, new ItemStack(heldItem.getContainerItem()));
						else if (heldItem instanceof ItemTaglock)
							Util.giveItem(player, new ItemStack(ModObjects.taglock));
						player.inventory.setItemStack(handler.insertItem(slot, player.inventory.decrStackSize(player.inventory.currentItem, 1), false));
						markDirty();
						world.notifyBlockUpdate(pos, state, state, 3);
						return true;
					}
				}
			} else return false;
		} else if (player.getHeldItem(hand).getItem() instanceof ItemSpade) {
			stopBurning();
			return true;
		}
		return super.activate(world, pos, player, hand, face);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
		return super.getCapability(capability, facing);
	}

	private void writeUpdateTag(NBTTagCompound tag) {
		tag.setString("incense", incense == null ? "" : incense.getRegistryName().toString());
		tag.setInteger("time", this.litTime);
		tag.setTag("handler", this.handler.serializeNBT());
	}

	private void readUpdateTag(NBTTagCompound tag) {
		this.incense = tag.getString("incense").isEmpty() ? null : GameRegistry.findRegistry(Incense.class).getValue(new ResourceLocation(tag.getString("incense")));
		this.litTime = tag.getInteger("time");
		this.handler.deserializeNBT(tag.getCompoundTag("handler"));
	}

	@Override
	public void update() {
		if (this.incense != null) {
			if (world.getTotalWorldTime() % 20 == 0) {
				this.litTime++;
				if (this.litTime > incense.time) stopBurning();
			}
		}
		if (this.litTime > 168000) stopBurning();
	}

	private void stopBurning() {
		this.litTime = 0;
		this.incense = null;
		world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockBrazier.LIT, false));
		markDirty();
	}

	private void clearBrazier() {
		for (int i = 0; i < handler.getSlots(); i++) {
			handler.setStackInSlot(i, ItemStack.EMPTY);
		}
		markDirty();
	}
}
