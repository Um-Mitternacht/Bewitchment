package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.capability.IEnergy;
import com.bewitchment.common.core.capability.energy.CapabilityEnergy;
import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.infusion.ModInfusions;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Optional;
import java.util.UUID;

/**
 * This class was created by Arekkuusu on 04/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class EnergyMessage implements IMessage {

	private IEnergy energy;
	private UUID target;

	public EnergyMessage() {
		energy = new CapabilityEnergy.DefaultEnergy();
	}

	public EnergyMessage(IEnergy energy, EntityLivingBase target) {
		this.energy = energy;
		this.target = target.getUniqueID();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		energy.set(buf.readInt());
		energy.setMax(buf.readInt());
		energy.setRegen(buf.readInt(), buf.readInt());
		energy.setUses(buf.readInt());
		energy.setType(ModInfusions.REGISTRY.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(buf))));

		target = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(energy.get());
		buf.writeInt(energy.getMax());
		buf.writeInt(energy.getRegenTime());
		buf.writeInt(energy.getRegenBurst());
		buf.writeInt(energy.getUses());
		ByteBufUtils.writeUTF8String(buf, energy.getType().getRegistryName().toString());

		buf.writeLong(target.getMostSignificantBits());
		buf.writeLong(target.getLeastSignificantBits());
	}

	public static class EnergyMessageHandler implements IMessageHandler<EnergyMessage, IMessage> {

		@Override
		public IMessage onMessage(EnergyMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				final EntityPlayer entityTarget = Minecraft.getMinecraft().world.getPlayerEntityByUUID(message.target);

				if (entityTarget != null) {
					final Optional<IEnergy> optData = EnergyHandler.getEnergy(entityTarget);
					if (optData.isPresent()) {
						final IEnergy data = optData.get();
						data.set(message.energy.get());
						data.setMax(message.energy.getMax());
						data.setRegen(message.energy.getRegenTime(), message.energy.getRegenBurst());
						data.setUses(message.energy.getUses());
						data.setType(message.energy.getType());
					}
				}
			});
			return null;
		}
	}
}
