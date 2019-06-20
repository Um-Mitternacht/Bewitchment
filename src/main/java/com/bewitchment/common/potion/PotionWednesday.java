package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.SpawnParticle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@SuppressWarnings({"ConstantConditions", "unused"})
public class PotionWednesday extends Potion {
	public PotionWednesday() {
		super(false, 0x00ff00);
		setRegistryName(new ResourceLocation(Bewitchment.MODID, "wednesday"));
		setPotionName(getRegistryName().toString().replace(":", "."));
		if (Bewitchment.config.wednesday) {
			ForgeRegistries.POTIONS.register(this);
			MinecraftForge.EVENT_BUS.register(this);
		}
	}
	
	@SubscribeEvent
	public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getEntityLiving().getActivePotionEffect(this).getDuration() == 1) {
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.EXPLOSION_HUGE, event.getEntityLiving().getPosition()), event.getEntityLiving().dimension);
			event.getEntityLiving().world.playSound(null, event.getEntityLiving().getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1, 1);
			if (event.getEntityLiving() instanceof EntityPlayer) event.getEntityLiving().attackEntityFrom(DamageSource.OUT_OF_WORLD, Integer.MAX_VALUE);
			else event.getEntityLiving().setDead();
		}
	}
}