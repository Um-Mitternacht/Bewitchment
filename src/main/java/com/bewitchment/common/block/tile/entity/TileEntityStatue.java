package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.nbt.NBTTagCompound;

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