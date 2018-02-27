package com.bewitchment.common.core.event;

import com.bewitchment.api.capability.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.blood.BloodReserveProvider;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.potion.PotionBloodDrained;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySkeleton;
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

	private BloodEvents() {
	}

	@SubscribeEvent
	public static void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> evt) {
		if (evt.getObject() instanceof EntityLivingBase) {
			evt.addCapability(BLOOD_DATA, new BloodReserveProvider());
		}
	}

	@SubscribeEvent
	public static void onJoin(EntityJoinWorldEvent evt) {
		Entity e = evt.getEntity();
		if (e instanceof EntityLivingBase) {
			IBloodReserve br = e.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			if (br.getMaxBlood() == 0) {
				int maxBlood = 120;
				if (e instanceof EntityPlayer) {
					maxBlood = 480;
				} else if (e instanceof EntityVillager) {
					maxBlood = 320;
				} else if (e instanceof EntityCow) {
					maxBlood = 275;
				} else if (e instanceof EntityHorse) {
					maxBlood = 260;
				} else if (e instanceof EntityDonkey) {
					maxBlood = 235;
				} else if (e instanceof EntityLlama) {
					maxBlood = 200;
				} else if (e instanceof EntitySheep) {
					maxBlood = 200;
				} else if (e instanceof EntityChicken) {
					maxBlood = 120;
				} else if (e instanceof EntitySkeleton || e instanceof EntitySkeletonHorse || e instanceof EntityShulker || !e.isNonBoss()) {
					maxBlood = -1;
				}
				br.setMaxBlood(maxBlood);
				br.setBlood(maxBlood);
			}
		}
	}

	@SubscribeEvent
	public static void fillBloodOverTime(LivingUpdateEvent evt) {
		EntityLivingBase ent = evt.getEntityLiving();
		if (!ent.world.isRemote) {
			IBloodReserve br = ent.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			if (br.getMaxBlood() > br.getBlood() && ent.ticksExisted % 80 == 0) {
				if (ent instanceof EntityPlayer) {
					ent.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 60, 1));
					br.setBlood(br.getBlood() + 10);
				} else if (ent instanceof EntityVillager) {
					// TODO check for villagers nearby. Regen rate should be nerfed when many are in the same place
					br.setBlood(br.getBlood() + 10);
				} else {
					br.setBlood(br.getBlood() + 10);
				}

				float stored = br.getPercentFilled();
				if (stored < PotionBloodDrained.TRESHOLD) {
					ent.addPotionEffect(new PotionEffect(ModPotions.bloodDrained, 200, 0));
				}

				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(ent), new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, 32));
			}
		}
	}

}
