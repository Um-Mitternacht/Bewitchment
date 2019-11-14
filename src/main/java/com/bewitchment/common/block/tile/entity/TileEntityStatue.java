package com.bewitchment.common.block.tile.entity;

import com.bewitchment.client.model.block.*;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ResourceBundle;

public class TileEntityStatue extends ModTileEntity {
	public String name;
	public boolean item = false;

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.name = tag.getString("name");
		this.item = tag.getBoolean("item");
		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("name", name);
		tag.setBoolean("item", item);
		return super.writeToNBT(tag);
	}
}