package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.sigils.ItemSigil;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileEntityDragonsBlood extends ModTileEntity {
	public ItemStackHandler handler = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			return stack.getItem() instanceof ItemSigil;
		}
	};
	
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
		if (world.getBlockState(pos).getBlock() == ModObjects.dragons_blood_door.door) {
			TileEntityDragonsBlood door1 = (TileEntityDragonsBlood) world.getTileEntity(pos);
			TileEntityDragonsBlood door2 = world.getTileEntity(pos.down()) instanceof TileEntityDragonsBlood ? (TileEntityDragonsBlood) world.getTileEntity(pos.down()) : (TileEntityDragonsBlood) world.getTileEntity(pos.up());
			if (isEmpty(door1.handler) && isEmpty(door2.handler)) {
				if (player.getHeldItem(hand).getItem() instanceof ItemSigil) {
					handler.insertItem(0, player.inventory.decrStackSize(player.inventory.currentItem, 1), false);
					return true;
				}
			}
			else {
				ItemSigil sigil = door1.handler.getStackInSlot(0).isEmpty() ? (ItemSigil) door2.handler.getStackInSlot(0).getItem() : (ItemSigil) door1.handler.getStackInSlot(0).getItem();
				sigil.applyEffects(player);
				return true;
			}
		}
		else {
			if (isEmpty(handler)) {
				if (player.getHeldItem(hand).getItem() instanceof ItemSigil) {
					handler.insertItem(0, player.inventory.decrStackSize(player.inventory.currentItem, 1), false);
					return true;
				}
			}
			else {
				if (handler.getStackInSlot(0).getItem() instanceof ItemSigil) {
					((ItemSigil) handler.getStackInSlot(0).getItem()).applyEffects(player);
					return true;
				}
			}
		}
		return super.activate(world, pos, player, hand, face);
	}
	
	private void writeUpdateTag(NBTTagCompound tag) {
		tag.setTag("handler", this.handler.serializeNBT());
	}
	
	private void readUpdateTag(NBTTagCompound tag) {
		this.handler.deserializeNBT(tag.getCompoundTag("handler"));
	}
}
