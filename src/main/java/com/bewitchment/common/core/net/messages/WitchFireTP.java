package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.misc.BlockWitchFire;
import com.bewitchment.common.content.cauldron.teleportCapability.CapabilityCauldronTeleport;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WitchFireTP extends SimpleMessage<WitchFireTP> {

	public String destination = null;

	public WitchFireTP(String to) {
		destination = to;
	}

	public WitchFireTP() {
	}


	@Override
	public IMessage handleMessage(MessageContext context) {
		EntityPlayer sender = context.getServerHandler().player;
		CapabilityCauldronTeleport ctp = sender.world.getCapability(CapabilityCauldronTeleport.CAPABILITY, null);
		BlockPos dest = ctp.get(destination, sender.world);
		if (dest != null && sender.world.getBlockState(sender.getPosition()).getBlock() == ModBlocks.witchfire) {
			int color = sender.world.getBlockState(sender.getPosition()).getValue(BlockWitchFire.TYPE).getColor();
			NetworkHandler.HANDLER.sendToAllTracking(new WitchfireFlame(sender.getPosition(), dest, color), sender);
			NetworkHandler.HANDLER.sendTo(new WitchfireFlame(sender.getPosition(), dest, color), (EntityPlayerMP) sender);
			sender.world.setBlockState(sender.getPosition(), ModBlocks.witchfire.getDefaultState(), 3);
			sender.setPositionAndUpdate(dest.getX() + 0.5d, dest.getY() + 0.5, dest.getZ() + 0.5d);
		}
		return null;
	}

}
