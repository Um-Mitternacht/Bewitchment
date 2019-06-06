package com.bewitchment.common.block.tile.entity;

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
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class TileEntityCrystalBall extends TileEntityAltarStorage {
	@Override
	public boolean activate(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (!world.isRemote) {
			if (altarPos != null && MagicPower.attemptDrain(world.getTileEntity(altarPos), null, 1000)) {
				ExtendedPlayer cap = player.getCapability(ExtendedPlayer.CAPABILITY, null);
				if (cap.fortune == null) {
					List<Fortune> valid = GameRegistry.findRegistry(Fortune.class).getValuesCollection().stream().filter(f -> f.isValid(player)).collect(Collectors.toList());
					if (!valid.isEmpty()) {
						cap.fortune = valid.get(world.rand.nextInt(valid.size()));
						player.sendStatusMessage(new TextComponentTranslation(cap.fortune.getRegistryName().toString().replace(":", ".")), true);
					}
					else player.sendStatusMessage(new TextComponentTranslation("fortune.no_fortune"), true);
				}
				else player.sendStatusMessage(new TextComponentTranslation("fortune.has_fortune", player.getDisplayName()), true);
			}
			else player.sendStatusMessage(new TextComponentTranslation("altar.no_power", player.getDisplayName()), true);
		}
		return true;
	}
}