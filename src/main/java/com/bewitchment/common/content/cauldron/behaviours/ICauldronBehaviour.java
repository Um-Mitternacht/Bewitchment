package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public interface ICauldronBehaviour {

	public void setCauldron(TileEntityCauldron tile);

	public void handleParticles(boolean isActiveBehaviour);

	/**
	 * If <i>any</i> decorator returns true, the cauldron won't accept any more items
	 */
	default boolean shouldInputsBeBlocked() {
		return false;
	}

	default boolean canAccept(EntityItem itemEntity) {
		return canAccept(itemEntity.getItem().copy().splitStack(1)) && !itemEntity.getTags().contains("cauldron_drop");
	}

	public boolean canAccept(ItemStack stack);

	public void statusChanged(boolean isActiveBehaviour);

	public void update(boolean isActiveBehaviour);

	public int getColor();

	public void saveToNBT(NBTTagCompound tag);

	public void loadFromNBT(NBTTagCompound tag);

	public void saveToSyncNBT(NBTTagCompound tag);

	public void loadFromSyncNBT(NBTTagCompound tag);

	default void playerInteract(EntityPlayer player, EnumHand hand) {
	}

	public String getID();

	public void onDeactivation();
}
