package com.bewitchment.common.tile;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.TarotMessage;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class TileEntityTarotsTable extends ModTileEntity { // No ticking

	private static final int READ_COST = 2000;

	private TileEntityWitchAltar te = null;

	public TileEntityTarotsTable() {
	}

	@Override
	void readAllModDataNBT(NBTTagCompound cmp) {
	}

	@Override
	void writeAllModDataNBT(NBTTagCompound cmp) {
	}

	public void read(@Nonnull ItemStack tarotDeck, @Nonnull EntityPlayer reader) {
		if (!reader.world.isRemote) {
			if (checkDeck(tarotDeck) && consumePower(READ_COST, false)) {
				reader.openGui(Bewitchment.instance, LibGui.TAROT.ordinal(), reader.world, pos.getX(), pos.getY(), pos.getZ());
				NetworkHandler.HANDLER.sendTo(new TarotMessage(reader), (EntityPlayerMP) reader);
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

	@Override
	void writeModSyncDataNBT(NBTTagCompound tag) {
	}

	@Override
	void readModSyncDataNBT(NBTTagCompound tag) {
	}

}
