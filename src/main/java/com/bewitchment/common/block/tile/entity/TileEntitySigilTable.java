package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.SigilRecipe;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileEntitySigilTable extends ModTileEntity {
	public final ItemStackHandler output = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			return false;
		}
	};
	public SigilRecipe recipe;
	public final ItemStackHandler matrix = new ItemStackHandler(25) {
		@Override
		protected void onContentsChanged(int slot) {
			recipe = GameRegistry.findRegistry(SigilRecipe.class).getValuesCollection().stream().filter(p -> p.matches(matrix)).findAny().orElse(null);
			if (recipe != null) output.setStackInSlot(0, new ItemStack(recipe.getOutput().getItem(), 1));
			markDirty();
		}
	};

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		readUpdateTag(tag);
		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		writeUpdateTag(tag);
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

	public void decreaseStackAmount(int amount) {
		for (int i = 0; i < matrix.getSlots(); i++) {
			matrix.extractItem(i, amount, false);
		}
	}

	private void writeUpdateTag(NBTTagCompound tag) {
		tag.setString("recipe", recipe == null ? "" : recipe.getRegistryName().toString());
		tag.setTag("matrix", this.matrix.serializeNBT());
	}

	private void readUpdateTag(NBTTagCompound tag) {
		recipe = tag.getString("recipe").isEmpty() ? null : GameRegistry.findRegistry(SigilRecipe.class).getValue(new ResourceLocation(tag.getString("recipe")));
		this.matrix.deserializeNBT(tag.getCompoundTag("matrix"));
	}
}
