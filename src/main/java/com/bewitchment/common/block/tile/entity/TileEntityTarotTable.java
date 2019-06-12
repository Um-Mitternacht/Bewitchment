package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Tarot;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import com.bewitchment.common.item.ItemTarotCards;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityTarotTable extends TileEntityAltarStorage {
	@Override
	public boolean activate(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (!world.isRemote) {
			if (!player.isSneaking() && player.getHeldItem(hand).getItem() instanceof ItemTarotCards) {
				if (altarPos != null && MagicPower.attemptDrain(world.getTileEntity(altarPos), player, 1000)) {
					List<Tarot> valid = GameRegistry.findRegistry(Tarot.class).getValuesCollection().stream().filter(f -> f.isValid(player)).collect(Collectors.toList());
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
				else player.sendStatusMessage(new TextComponentTranslation("altar.no_power", player.getDisplayName()), true);
			}
		}
		return true;
	}
}