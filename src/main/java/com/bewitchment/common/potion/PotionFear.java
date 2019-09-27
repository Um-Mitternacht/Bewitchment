package com.bewitchment.common.potion;

import com.bewitchment.common.handler.PotionEffectHandler;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PotionFear extends ModPotion {
	public PotionFear() {
		super("fear", true, 0x10c440);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
	}

	@SubscribeEvent
	public void movePlayer(TickEvent.PlayerTickEvent event) {
		if (event.player.isPotionActive(this) && event.phase == TickEvent.Phase.END) {
			event.player.motionX += (event.player.getRNG().nextDouble() / 3d) - (event.player.getRNG().nextDouble() / 3d);
			event.player.motionZ += (event.player.getRNG().nextDouble() / 3d) - (event.player.getRNG().nextDouble() / 3d);
			if (!event.player.world.isRemote) ((EntityPlayerMP) event.player).connection.sendPacket(new SPacketEntityVelocity(event.player));
		}
	}
}
