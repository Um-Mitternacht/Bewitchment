package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Tarot;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import com.bewitchment.common.item.ItemTarotCards;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TileEntityTarotTable extends TileEntityAltarStorage {
	@Override
	public boolean activate(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (!player.isSneaking()) {
			if (player.getHeldItem(hand).getItem() instanceof ItemTarotCards) {
				if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, 1000)) {
					NBTTagCompound tag = player.getHeldItem(hand).getTagCompound();
					if (tag != null && tag.hasKey("readId")) {
						EntityPlayer toFind = Util.findPlayer(UUID.fromString(tag.getString("readId")));
						if (toFind != null) {
							List<Tarot> valid = BewitchmentAPI.REGISTRY_TAROT.getValuesCollection().stream().filter(f -> f.isValid(toFind)).collect(Collectors.toList());
							if (!valid.isEmpty()) {
								List<Tarot> toShow = new ArrayList<>();
								while (!valid.isEmpty() && toShow.size() < 5) {
									int i = world.rand.nextInt(valid.size());
									toShow.add(valid.get(i));
									valid.remove(i);
								}
								for (Tarot tarot : toShow) System.out.println(tarot.getRegistryName());
							}
						}
						else if (!world.isRemote) player.sendStatusMessage(new TextComponentTranslation("tarot.player_offline", tag.getString("readName")), true);
					}
					else if (!world.isRemote) player.sendStatusMessage(new TextComponentTranslation("tarot.no_player"), true);
				}
				else if (!world.isRemote) player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
			}
			else if (!world.isRemote) player.sendStatusMessage(new TextComponentTranslation("tarot.no_cards"), true);
		}
		return true;
	}
}