package com.bewitchment.api.capability.extendedplayer;

import com.bewitchment.Bewitchment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ExtendedPlayerHandler {
	private static final ResourceLocation LOC = new ResourceLocation(Bewitchment.MODID, "extended_player");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) event.addCapability(LOC, new ExtendedPlayer());
	}
	
	@SubscribeEvent
	public void clonePlayer(PlayerEvent.Clone event) {
		event.getEntityPlayer().getCapability(ExtendedPlayer.CAPABILITY, null).deserializeNBT(event.getOriginal().getCapability(ExtendedPlayer.CAPABILITY, null).serializeNBT());
	}
	
	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		if (!event.player.world.isRemote && event.phase == TickEvent.Phase.END) {
			ExtendedPlayer cap = event.player.getCapability(ExtendedPlayer.CAPABILITY, null);
			if (cap.fortune != null && cap.fortune.apply(event.player)) {
				cap.fortune = null;
				ExtendedPlayer.syncToClient(event.player);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (!event.getEntityLiving().isNonBoss()) {
				NBTTagList list = player.getCapability(ExtendedPlayer.CAPABILITY, null).uniqueDefeatedBosses;
				String name = EntityRegistry.getEntry(event.getEntityLiving().getClass()).getName();
				boolean found = false;
				for (int i = 0; i < list.tagCount(); i++) {
					if (list.getStringTagAt(i).equals(name)) {
						found = true;
						break;
					}
				}
				if (!found) {
					list.appendTag(new NBTTagString(name));
					ExtendedPlayer.syncToClient(player);
				}
			}
			if (event.getEntityLiving() instanceof EntityMob) {
				player.getCapability(ExtendedPlayer.CAPABILITY, null).mobsKilled++;
				ExtendedPlayer.syncToClient(player);
			}
		}
	}
}