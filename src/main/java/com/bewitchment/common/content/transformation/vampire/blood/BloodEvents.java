package com.bewitchment.common.content.transformation.vampire.blood;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.potion.potions.PotionBloodDrained;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BloodEvents {

	@SubscribeEvent
	public static void onJoin(EntityJoinWorldEvent evt) {
		Entity e = evt.getEntity();
		if (!e.world.isRemote && e instanceof EntityLivingBase) {
			IBloodReserve br = e.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			if (br.getMaxBlood() == 0) {
				int maxBlood = 100;
				if (e instanceof EntityPlayer) {
					maxBlood = 480;
				} else if (e instanceof EntityVillager) {
					maxBlood = 240;
				} else if (e instanceof EntityCow || e instanceof EntityHorse || e instanceof EntityPolarBear) {
					maxBlood = 150;
				} else if (e instanceof EntityDonkey || e instanceof EntityLlama) {
					maxBlood = 130;
				} else if (e instanceof EntitySheep) {
					maxBlood = 110;
				} else if (e instanceof EntityWolf || e instanceof EntityOcelot) {
					maxBlood = 80;
				} else if (e instanceof EntityChicken || e instanceof EntityParrot) {
					maxBlood = 50;
				} else {
					maxBlood = -1;
				}
				br.setMaxBlood(maxBlood);
				br.setBlood(maxBlood);
				EntityLivingBase ent = (EntityLivingBase) e;
				NetworkHandler.HANDLER.sendToAllTracking(new EntityInternalBloodChanged(ent), ent);
			} else {
				EntityLivingBase ent = (EntityLivingBase) e;
				NetworkHandler.HANDLER.sendToAllTracking(new EntityInternalBloodChanged(ent), ent);
			}
		}
	}

	@SubscribeEvent
	public static void entityTrackingEvent(StartTracking evt) {
		if (evt.getTarget() instanceof EntityLivingBase) {
			EntityLivingBase ent = (EntityLivingBase) evt.getTarget();
			NetworkHandler.HANDLER.sendTo(new EntityInternalBloodChanged(ent), (EntityPlayerMP) evt.getEntityPlayer());
		}
	}

	@SubscribeEvent
	public static void fillBloodOverTime(LivingUpdateEvent evt) {
		if (!evt.getEntityLiving().world.isRemote) {
			EntityLivingBase ent = evt.getEntityLiving();
			boolean ignore = false;
			IBloodReserve br = ent.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			if (br.getMaxBlood() > br.getBlood() && ent.ticksExisted % 80 == 0) {
				int baseIncrease = 20 / (int) ent.world.getEntitiesWithinAABB(EntityLivingBase.class, ent.getEntityBoundingBox().grow(10)).parallelStream().count();
				if (ent instanceof EntityPlayer) {
					CapabilityTransformation data = ent.getCapability(CapabilityTransformation.CAPABILITY, null);

					if (data.getType() == DefaultTransformations.VAMPIRE || data.getType() == DefaultTransformations.SPECTRE) {
						ignore = true;
					} else {
						ent.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 60, 1));
						br.setBlood(br.getBlood() + baseIncrease);
					}
				} else {
					br.setBlood(br.getBlood() + baseIncrease);
				}

				float stored = br.getPercentFilled();
				if (!ignore && stored < PotionBloodDrained.TRESHOLD) {
					ent.addPotionEffect(new PotionEffect(ModPotions.bloodDrained, 200, 0));
				}

				NetworkHandler.HANDLER.sendToAllTracking(new EntityInternalBloodChanged(ent), ent);
			}
		}
	}
}
