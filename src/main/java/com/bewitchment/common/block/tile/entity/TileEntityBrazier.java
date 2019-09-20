package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.Incense;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBrazier extends ModTileEntity {
	private ItemStackHandler handler;
	private Incense incense;
	
	public TileEntityBrazier() {
		this.handler = new ItemStackHandler(9);
		this.incense = null;
	}

	public void getIncense() {
		this.incense = GameRegistry.findRegistry(Incense.class).getValuesCollection().stream().filter(p -> p.matches(handler)).findFirst().orElse(null);
	}

	public boolean interact(EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			ItemStack itemStack = player.getHeldItem(hand);
			int slot = getFirstEmptySlot(handler);
			if (slot != -1) {
				handler.setStackInSlot(slot, new ItemStack(itemStack.getItem(), 1));
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
				markDirty();
				IBlockState state = world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 3);
				return true;
			}
		} else {
			int slot = getLastNonEmptySlot(handler);
			if (slot != -1) {
				ItemHandlerHelper.giveItemToPlayer(player, handler.getStackInSlot(slot));
				handler.setStackInSlot(slot, ItemStack.EMPTY);
				markDirty();
				IBlockState state = world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 3);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		this.writeUpdateTag(tag);
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.readUpdateTag(tag);
		super.readFromNBT(tag);
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
	
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		NBTTagCompound tag = packet.getNbtCompound();
		readUpdateTag(tag);
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
	
	private void writeUpdateTag(NBTTagCompound tag) {
		tag.setString("incense", incense == null ? "" : incense.getRegistryName().toString());
		tag.setTag("ItemStackHandler", this.handler.serializeNBT());
	}
	
	private void readUpdateTag(NBTTagCompound tag) {
		this.incense = tag.getString("incense").isEmpty() ? null : GameRegistry.findRegistry(Incense.class).getValue(new ResourceLocation(tag.getString("incense")));
		this.handler.deserializeNBT(tag.getCompoundTag("ItemStackHandler"));
	}
	
	
}
