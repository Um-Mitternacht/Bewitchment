package com.bewitchment.common.block.tile.entity.util;

import com.bewitchment.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings({"NullableProblems", "WeakerAccess"})
public abstract class ModTileEntity extends TileEntity {
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		for (int i = 0; i < getInventories().length; i++) getInventories()[i].deserializeNBT(tag.getCompoundTag("inventory_" + i));
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		for (int i = 0; i < getInventories().length; i++)
			tag.setTag("inventory_" + i, getInventories()[i].serializeNBT());
		markDirty();
		return super.writeToNBT(tag);
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 11);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 11);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{};
	}
	
	public boolean activate(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		return false;
	}
	
	public static boolean isEmpty(ItemStackHandler handler) {
		for (int i = 0; i < handler.getSlots(); i++)
			if (!handler.getStackInSlot(i).isEmpty()) return false;
		return true;
	}
	
	public static int getFirstEmptySlot(ItemStackHandler handler) {
		return getFirstValidSlot(handler, ItemStack.EMPTY);
	}
	
	public static int getFirstValidSlot(ItemStackHandler handler, ItemStack stack) {
		boolean hasEmpty = false;
		for (int i = 0; i < handler.getSlots(); i++) {
			if (Util.canMerge(handler.getStackInSlot(i), stack)) return i;
			if (handler.getStackInSlot(i).isEmpty()) hasEmpty = true;
		}
		return hasEmpty ? getFirstEmptySlot(handler) : -1;
	}
	
	public static void clear(ItemStackHandler handler) {
		for (int i = 0; i < handler.getSlots(); i++)
			handler.setStackInSlot(i, ItemStack.EMPTY);
	}
}