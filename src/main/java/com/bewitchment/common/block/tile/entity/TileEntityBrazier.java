package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.IncenseRecipe;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBrazier extends ModTileEntity {
	private ItemStackHandler handler;
	private String incense;
	private IncenseRecipe recipe;
	
	public TileEntityBrazier() {
		this.handler = new ItemStackHandler(9) {
			@Override
			protected void onContentsChanged(int index) {
				recipe = GameRegistry.findRegistry(IncenseRecipe.class).getValuesCollection().stream().filter(p -> p.matches(this)).findFirst().orElse(null);
			}
		};
		this.incense = "none";
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
		tag.setString("recipe", recipe == null ? "" : recipe.getRegistryName().toString());
		tag.setString("incense", this.incense);
		tag.setTag("ItemStackHandler", this.handler.serializeNBT());
	}
	
	private void readUpdateTag(NBTTagCompound tag) {
		this.incense = tag.getString("incense");
		this.handler.deserializeNBT(tag.getCompoundTag("ItemStackHandler"));
	}
	
	
}
