package com.bewitchment.common.core.event;

import com.bewitchment.api.capability.transformations.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.core.capability.transformation.blood.BloodReserveProvider;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.potion.PotionBloodDrained;
import com.bewitchment.common.transformation.ModTransformations;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

@Mod.EventBusSubscriber
public class BloodEvents {

	public static final ResourceLocation BLOOD_DATA = new ResourceLocation(LibMod.MOD_ID, "blood_pool");

	@SubscribeEvent
	public static void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> evt) {
		if (evt.getObject() instanceof EntityLivingBase) {
			evt.addCapability(BLOOD_DATA, new BloodReserveProvider());
		}
	}

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
				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(ent), new TargetPoint(e.dimension, ent.posX, ent.posY, ent.posZ, 32));
			} else {
				EntityLivingBase ent = (EntityLivingBase) e;
				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(ent), new TargetPoint(e.dimension, ent.posX, ent.posY, ent.posZ, 32));
			}
		}
	}

	@SubscribeEvent
	public static void fillBloodOverTime(LivingUpdateEvent evt) {
		EntityLivingBase ent = evt.getEntityLiving();
		if (!ent.world.isRemote) {
			boolean ignore = false;
			IBloodReserve br = ent.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			if (br.getMaxBlood() > br.getBlood() && ent.ticksExisted % 80 == 0) {

				int baseIncrease = getBloodRegen(br);

				if (ent instanceof EntityPlayer) {
					ITransformationData data = ent.getCapability(CapabilityTransformationData.CAPABILITY, null);
					if (data.getType() != ModTransformations.VAMPIRE) {
						ent.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 60, 1));
					}
					if (data.getType() == ModTransformations.VAMPIRE || data.getType() == ModTransformations.SPECTRE)
						ignore = true;
					br.setBlood(br.getBlood() + baseIncrease);
				} else if (ent instanceof EntityVillager) {
					// TODO check for villagers nearby. Regen rate should be nerfed when many are in the same place
					br.setBlood(br.getBlood() + baseIncrease);
				} else {
					br.setBlood(br.getBlood() + baseIncrease);
				}

				float stored = br.getPercentFilled();
				if (!ignore && stored < PotionBloodDrained.TRESHOLD) {
					ent.addPotionEffect(new PotionEffect(ModPotions.bloodDrained, 200, 0));
				}

				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(ent), new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, 32));
			}
		}
	}

	private static int getBloodRegen(IBloodReserve br) {
		if (br.getPercentFilled() < PotionBloodDrained.TRESHOLD)
			return 20;
		return 10;
	}

}
