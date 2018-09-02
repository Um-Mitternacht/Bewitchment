package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEnderInhibition extends BrewMod {

	public PotionEnderInhibition() {
		super("ender_inhibition", true, 0x86608E, false, 900);
		MinecraftForge.EVENT_BUS.register(this);
		this.setIconIndex(5, 0);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		int redo = 5 - amplifier;
		if ((redo <= 1 || e.world.rand.nextInt(redo) == 0) && e.getDistanceSq(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ) > 1) {
			e.posX = e.lastTickPosX;
			e.posY = e.lastTickPosY;
			e.posZ = e.lastTickPosZ;
			e.setPositionAndUpdate(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ);
		}
	}

	@SubscribeEvent
	public void onTeleport(EnderTeleportEvent event) {
		PotionEffect pe = event.getEntityLiving().getActivePotionEffect(this);
		if (pe != null) {
			int redo = 5 - pe.getAmplifier();
			if (redo <= 1 || event.getEntityLiving().world.rand.nextInt(redo) == 0) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onTeleportDimensional(EntityTravelToDimensionEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			PotionEffect pe = ((EntityLivingBase) event.getEntity()).getActivePotionEffect(this);
			if (pe != null) {
				int redo = 5 - pe.getAmplifier();
				if (redo <= 1 || event.getEntity().world.rand.nextInt(redo) == 0) {
					event.setCanceled(true);
				}
			}
		}
	}

}
