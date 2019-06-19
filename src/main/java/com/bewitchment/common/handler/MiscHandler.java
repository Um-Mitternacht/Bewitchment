package com.bewitchment.common.handler;

import com.bewitchment.Util;
import com.bewitchment.api.registry.entity.EntityBroom;
import com.bewitchment.common.entity.misc.EntityYewBroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class MiscHandler {
	@SubscribeEvent
	public void onEntityMount(EntityMountEvent event) {
		if (!event.getWorldObj().isRemote && event.getEntityBeingMounted() instanceof EntityBroom && event.isDismounting() && !event.getEntityMounting().isDead) {
			if (event.getEntityBeingMounted() instanceof EntityYewBroom) {
				EntityYewBroom broom = (EntityYewBroom) event.getEntityBeingMounted();
				if (event.getEntityBeingMounted().dimension == broom.originalDimension) {
					double x = broom.originalPos.getX() + 0.5;
					double y = broom.originalPos.getY() + 0.5;
					double z = broom.originalPos.getZ() + 0.5;
					broom.setPositionAndUpdate(x, y, z);
					if (event.getEntityMounting() instanceof EntityPlayer) Util.teleportPlayer((EntityPlayer) event.getEntityMounting(), x, y, z);
				}
			}
			event.getEntityBeingMounted().setDead();
		}
	}
}