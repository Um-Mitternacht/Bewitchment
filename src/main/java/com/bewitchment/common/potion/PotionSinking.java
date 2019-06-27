package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class PotionSinking extends ModPotion {
	public PotionSinking() {
		super("sinking", true, 0x4000c0);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getEntityLiving().world.rand.nextInt(3) <= event.getEntityLiving().getActivePotionEffect(this).getAmplifier()) {
			event.getEntityLiving().motionY = 0;
			if (event.getEntityLiving() instanceof EntityPlayer) ((EntityPlayerMP) event.getEntityLiving()).connection.sendPacket(new SPacketEntityVelocity(event.getEntityLiving()));
		}
	}
}