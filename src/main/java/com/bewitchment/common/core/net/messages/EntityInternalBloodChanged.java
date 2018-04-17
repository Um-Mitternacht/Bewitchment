package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class EntityInternalBloodChanged extends SimpleMessage<EntityInternalBloodChanged> {

	public UUID id_drainer;
	public int amount, max, entity_id;

	public EntityInternalBloodChanged() {
	}

	public EntityInternalBloodChanged(EntityLivingBase entity) {
		entity_id = entity.getEntityId();
		IBloodReserve data = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		amount = data.getBlood();
		max = data.getMaxBlood();
		id_drainer = data.getDrinkerUUID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().world != null) {
				EntityLivingBase ent = (EntityLivingBase) Minecraft.getMinecraft().world.getEntityByID(entity_id);
				if (ent != null) {
					IBloodReserve br = ent.getCapability(CapabilityBloodReserve.CAPABILITY, null);
					br.setBlood(amount);
					br.setMaxBlood(max);
					br.setDrinker(id_drainer);
				}
			}
		});
		return null;
	}

}
