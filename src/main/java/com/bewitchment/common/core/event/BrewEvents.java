package com.bewitchment.common.core.event;

import com.bewitchment.api.capability.IBrewStorage;
import com.bewitchment.api.cauldron.brew.*;
import com.bewitchment.api.cauldron.brew.special.*;
import com.bewitchment.common.brew.BrewEffect;
import com.bewitchment.common.core.capability.brew.BrewStorageHandler;
import com.bewitchment.common.core.capability.brew.BrewStorageProvider;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BrewEvents {

	@SubscribeEvent
	public void attachPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityLivingBase) {
			event.addCapability(new ResourceLocation(LibMod.MOD_ID, "BrewData"), new BrewStorageProvider());
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		BrewStorageHandler.getBrewStorage(event.player).ifPresent(data -> {
			if (event.player instanceof EntityPlayerMP) {
				data.syncToNear(event.player);
			}
		});
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		BrewStorageHandler.getBrewStorage(event.player).ifPresent(data -> {
			if (event.player instanceof EntityPlayerMP) {
				data.syncToNear(event.player);
			}
		});
	}

	@SubscribeEvent
	public void onPlayerClone(Clone event) {
		if (!event.isWasDeath()) {
			final EntityPlayer oldPlayer = event.getOriginal();
			final EntityPlayer newPlayer = event.getEntityPlayer();

			Optional<IBrewStorage> optional = BrewStorageHandler.getBrewStorage(oldPlayer);
			optional.ifPresent(oldStorage -> BrewStorageHandler.getBrewStorage(newPlayer).ifPresent(newStorage -> {
				newStorage.setBrewMap(oldStorage.getBrewMap());
				newStorage.syncToNear(newPlayer);
			}));
		}
	}

	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		Optional<IBrewStorage> optional = BrewStorageHandler.getBrewStorage(entity);
		if (optional.isPresent()) {
			IBrewStorage storage = optional.get();
			Map<IBrew, BrewEffect> brews = storage.getBrewMap();
			if (brews.isEmpty()) return;
			boolean update = false;

			if (!entity.world.isRemote && BrewStorageHandler.BREW_REMOVAL.containsKey(entity)) {
				for (IBrew brew : BrewStorageHandler.BREW_REMOVAL.get(entity)) {
					brews.get(brew).end(entity.world, entity.getPosition(), entity);
					brews.remove(brew);
				}
				BrewStorageHandler.BREW_REMOVAL.remove(entity);
				update = true;
			}

			Map<IBrew, BrewEffect> updated = new HashMap<>();
			for (BrewEffect effect : brews.values()) {
				if (effect.getDuration() <= 0) {
					effect.end(entity.world, entity.getPosition(), entity);
					update = true;
				} else {
					effect.update(entity.world, entity.getPosition(), entity);
					updated.put(effect.getBrew(), effect);
				}
			}

			if (!entity.world.isRemote) {
				storage.setBrewMap(updated);
				if (update) {
					storage.syncToNear(entity);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityStartTracking(StartTracking event) {
		Entity entity = event.getTarget();
		EntityPlayer player = event.getEntityPlayer();
		if (!entity.world.isRemote && entity != player && entity instanceof EntityLivingBase && player instanceof EntityPlayerMP) {
			Optional<IBrewStorage> optional = BrewStorageHandler.getBrewStorage((EntityLivingBase) entity);
			if (optional.isPresent()) {
				IBrewStorage storage = optional.get();
				storage.syncTo((EntityPlayerMP) event.getEntityPlayer());
			}
		}
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntityLiving());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewHurt).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewHurt) effect.getBrew()).onHurt(event, event.getSource(), event.getEntityLiving(), effect.getAmplifier());
		});
	}

	@SubscribeEvent
	public void onHeal(LivingHealEvent event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntityLiving());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewHeal).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewHeal) effect.getBrew()).onHeal(event, event.getEntityLiving(), effect.getAmplifier());
		});
	}

	@SubscribeEvent
	public void onAttack(LivingAttackEvent event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntityLiving());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewAttack).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewAttack) effect.getBrew()).onAttack(event, event.getSource(), event.getEntityLiving(), effect.getAmplifier());
		});
	}

	@SubscribeEvent
	public void onBlockDestroy(LivingDestroyBlockEvent event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntityLiving());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewBlockDestroy).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewBlockDestroy) effect.getBrew()).onBlockDestroy(event, event.getEntityLiving(), effect.getAmplifier());
		});
	}

	@SubscribeEvent
	public void onTeleport(EnderTeleportEvent event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntityLiving());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewEnderTeleport).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewEnderTeleport) effect.getBrew()).onTeleport(event, event.getEntityLiving()
					, event.getTargetX(), event.getTargetY(), event.getTargetZ(), effect.getAmplifier());
		});
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		Collection<BrewEffect> effects = BrewStorageHandler.getBrewEffects(event.getEntityLiving());
		effects.stream().filter(effect -> effect.getBrew() instanceof IBrewDeath).forEach(effect -> {
			if (event.isCanceled()) return;
			((IBrewDeath) effect.getBrew()).onDeath(event, event.getSource(), event.getEntityLiving(), effect.getAmplifier());
		});
	}
}
