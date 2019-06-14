package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class TileEntityCrystalBall extends TileEntityAltarStorage {
	@Override
	public boolean activate(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (!world.isRemote) {
			if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, 750)) {
				if (ExtendedPlayer.getFortune(player) == null) {
					List<Fortune> valid = BewitchmentAPI.REGISTRY_FORTUNE.getValuesCollection().stream().filter(f -> f.isValid(player)).collect(Collectors.toList());
					if (!valid.isEmpty()) {
						ExtendedPlayer.setFortune(player, valid.get(world.rand.nextInt(valid.size())));
						player.sendStatusMessage(new TextComponentTranslation("fortune." + ExtendedPlayer.getFortune(player).getRegistryName().toString().replace(":", ".")), true);
					}
					else player.sendStatusMessage(new TextComponentTranslation("fortune.no_fortune"), true);
				}
				else player.sendStatusMessage(new TextComponentTranslation("fortune.has_fortune", player.getDisplayName()), true);
			}
			else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
		}
		return true;
	}
}