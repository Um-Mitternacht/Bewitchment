package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.core.capability.cauldronTeleports.CapabilityCauldronTeleport;
import com.bewitchment.common.core.net.SimpleMessage;

import net.minecraft.entity.player.EntityPlayer;
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
		if (dest != null) {
			sender.world.setBlockToAir(sender.getPosition());
			sender.setPositionAndUpdate(dest.getX() + 0.5d, dest.getY() + 0.5, dest.getZ() + 0.5d);
		}
		return null;
	}
	
}
