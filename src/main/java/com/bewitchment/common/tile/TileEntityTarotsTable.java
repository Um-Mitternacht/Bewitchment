package com.bewitchment.common.tile;

import javax.annotation.Nonnull;

import com.bewitchment.common.item.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class TileEntityTarotsTable extends TileMod { // No ticking
	
	private static final int READ_COST = 2000;
	
	private TileEntityWitchAltar te = null;
	
	public TileEntityTarotsTable() {
	}
	
	@Override
	void readDataNBT(NBTTagCompound cmp) {
	}
	
	@Override
	void writeDataNBT(NBTTagCompound cmp) {
	}
	
	public void read(@Nonnull ItemStack tarotDeck, @Nonnull EntityPlayer reader) {
		if (!reader.world.isRemote) {
			if (checkDeck(tarotDeck) && consumePower(READ_COST, false)) {
				// show gui
				reader.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + "TEMP MESSAGE"), true);
			} else {
				reader.sendStatusMessage(new TextComponentTranslation("item.tarots.error_reading"), true);
			}
		}
	}
	
	private boolean checkDeck(ItemStack tarotDeck) {
		return (tarotDeck.getItem() == ModItems.tarots && tarotDeck.hasTagCompound() && tarotDeck.getTagCompound().hasKey("read_id") && tarotDeck.getTagCompound().hasKey("read_name"));
	}
	
	private boolean consumePower(int power, boolean simulate) {
		if (power == 0)
			return true;
		if (te == null || te.isInvalid())
			te = TileEntityWitchAltar.getClosest(pos, world);
		if (te == null)
			return false;
		return te.consumePower(power, simulate);
	}
	
}
