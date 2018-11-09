package com.bewitchment.common.core.event;

import com.bewitchment.common.content.crystalBall.capability.FortuneCapabilityProvider;
import com.bewitchment.common.content.infusion.capability.InfusionProvider;
import com.bewitchment.common.content.transformation.vampire.blood.BloodReserveProvider;
import com.bewitchment.common.core.capability.energy.player.PlayerMPContainerProvider;
import com.bewitchment.common.core.capability.energy.player.expansion.MagicPowerExpansionProvider;
import com.bewitchment.common.core.capability.mimic.MimicDataProvider;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityAttacher {

	public static final ResourceLocation MP_PLAYER_TAG = new ResourceLocation(LibMod.MOD_ID, "mp");
	public static final ResourceLocation DIVINATION_TAG = new ResourceLocation(LibMod.MOD_ID, "divination");
	public static final ResourceLocation BLOOD_TAG = new ResourceLocation(LibMod.MOD_ID, "blood_pool");
	public static final ResourceLocation MIMIC_TAG = new ResourceLocation(LibMod.MOD_ID, "mimic_data");
	public static final ResourceLocation TRANSFORMATION_TAG = new ResourceLocation(LibMod.MOD_ID, "transformations");
	public static final ResourceLocation INFUSION_TAG = new ResourceLocation(LibMod.MOD_ID, "infusion");
	public static final ResourceLocation MP_EXPANSION_TAG = new ResourceLocation(LibMod.MOD_ID, "mp_expansion");

	@SubscribeEvent
	public static void attach(AttachCapabilitiesEvent<Entity> evt) {

		if (evt.getObject() instanceof EntityLivingBase) {
			evt.addCapability(BLOOD_TAG, new BloodReserveProvider());
		}

		if (evt.getObject() instanceof EntityPlayer) {
			evt.addCapability(DIVINATION_TAG, new FortuneCapabilityProvider());
			evt.addCapability(MP_PLAYER_TAG, new PlayerMPContainerProvider());
			evt.addCapability(MIMIC_TAG, new MimicDataProvider());
			evt.addCapability(INFUSION_TAG, new InfusionProvider());
			evt.addCapability(MP_EXPANSION_TAG, new MagicPowerExpansionProvider());
		}
	}

}
