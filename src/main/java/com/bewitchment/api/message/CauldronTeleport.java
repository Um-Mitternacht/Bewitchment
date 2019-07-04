package com.bewitchment.api.message;

import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused"})
public class CauldronTeleport implements IMessage {
	public String message;
	
	public CauldronTeleport() {
	}
	
	public CauldronTeleport(String message) {
		this.message = message;
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		message = ByteBufUtils.readUTF8String(byteBuf);
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		ByteBufUtils.writeUTF8String(byteBuf, message);
	}
	
	@SuppressWarnings("ConstantConditions")
	public static class Handler implements IMessageHandler<CauldronTeleport, IMessage> {
		@Override
		public IMessage onMessage(CauldronTeleport message, MessageContext ctx) {
			if (ctx.side.isServer()) {
				EntityPlayer player = ctx.getServerHandler().player;
				ExtendedWorld cap = player.world.getCapability(ExtendedWorld.CAPABILITY, null);
				for (long l : cap.storedCauldrons) {
					BlockPos pos = BlockPos.fromLong(l);
					if (player.world.getTileEntity(pos) instanceof TileEntityWitchesCauldron && ((TileEntityWitchesCauldron) player.world.getTileEntity(pos)).getName().equals(message.message)) {
						player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
						Util.teleportPlayer(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
						player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
						break;
					}
				}
			}
			return null;
		}
	}
}