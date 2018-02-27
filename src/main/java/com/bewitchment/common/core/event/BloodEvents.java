package com.bewitchment.common.core.event;

import com.bewitchment.api.capability.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.blood.BloodReserveProvider;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityVillager;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
				} else if (e instanceof EntitySkeleton || e instanceof EntitySkeletonHorse || e instanceof EntityShulker || !e.isNonBoss()) {
					maxBlood = -1;
				}
				br.setMaxBlood(maxBlood);
				br.setBlood(maxBlood);
			}
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public static void fillBloodOverTime(LivingUpdateEvent evt) {
		EntityLivingBase ent = evt.getEntityLiving();
		IBloodReserve br = ent.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		if (br.getMaxBlood() > br.getBlood() && ent.ticksExisted % 60 == 0) {
			if (ent instanceof EntityPlayer) {
				ent.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 60, 1));
				br.setBlood(br.getBlood() + 40);
			} else if (ent instanceof EntityVillager) {
				// TODO check for villagers nearby. Regen rate should be nerfed when many are in the same place
				br.setBlood(br.getBlood() + 40);
			} else {
				br.setBlood(br.getBlood() + 40);
			}
			NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(ent), new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, 32));
		}
	}

}
