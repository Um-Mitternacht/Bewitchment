package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class TileEntityCrystalBall extends TileEntityAltarStorage {
	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (!world.isRemote) {
			if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, 750)) {
				ExtendedPlayer cap = player.getCapability(ExtendedPlayer.CAPABILITY, null);
				if (cap.fortune == null) {
					List<Fortune> valid = GameRegistry.findRegistry(Fortune.class).getValuesCollection().stream().filter(f -> f.isValid(player)).collect(Collectors.toList());
					if (!valid.isEmpty()) {
						cap.fortune = valid.get(player.getRNG().nextInt(valid.size()));
						cap.fortuneTime = (player.getRNG().nextInt(cap.fortune.maxTime - cap.fortune.minTime) + cap.fortune.minTime);
						ExtendedPlayer.syncToClient(player);
						player.sendStatusMessage(new TextComponentTranslation("fortune." + cap.fortune.getRegistryName().toString().replace(":", ".")), true); //maybe replace that true with false so that the message can be re-read?
					}
					else player.sendStatusMessage(new TextComponentTranslation("fortune.no_fortune"), true);
				}
				else player.sendStatusMessage(new TextComponentTranslation("fortune.has_fortune", player.getDisplayName(), new TextComponentTranslation("fortune." + cap.fortune.getRegistryName().toString().replace(":", ".")).getFormattedText()), true);
			}
			else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
		}
		return true;
	}
}