package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.ServerProxy;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import com.bewitchment.common.item.ItemTarotCards;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.UUID;

public class TileEntityTarotTable extends TileEntityAltarStorage {
	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote && !player.isSneaking()) {
			if (player.getHeldItem(hand).getItem() instanceof ItemTarotCards) {
				if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, 1000)) {
					NBTTagCompound tag = player.getHeldItem(hand).getTagCompound();
					if (tag != null && tag.hasKey("readId")) {
						if (Util.findPlayer(UUID.fromString(tag.getString("readId"))) != null) player.openGui(Bewitchment.instance, ServerProxy.ModGui.TAROT_TABLE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
						else player.sendStatusMessage(new TextComponentTranslation("tarot.player_offline", tag.getString("readName")), true);
					}
					else player.sendStatusMessage(new TextComponentTranslation("tarot.no_player"), true);
				}
				else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
			}
			else player.sendStatusMessage(new TextComponentTranslation("tarot.no_cards"), true);
		}
		return true;
	}
}