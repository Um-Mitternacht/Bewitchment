package com.bewitchment.common.handler;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class PoppetHandler {
	@SubscribeEvent
	public void deathProtection(LivingDeathEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_deathprotection)) {
				event.setCanceled(true);
				player.setHealth(4f);
				player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1000, 1));
				player.playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 2, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void binding(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLiving) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_binding)) {
				EntityLiving source = (EntityLiving) event.getSource().getTrueSource();
				source.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 2));
				source.playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 5, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void clumsy(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLiving) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			EntityLiving source = (EntityLiving) event.getSource().getTrueSource();
			ItemStack held = source.getHeldItemMainhand();
			if (!held.isEmpty() && Util.attemptDamagePoppet(player, ModObjects.poppet_clumsy)) {
				InventoryHelper.spawnItemStack(source.getEntityWorld(), source.posX, source.posY, source.posZ, held);
				source.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
				source.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 2, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void wasting(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_wasting)) {
				EntityLiving source = (EntityLiving) event.getSource().getTrueSource();
				source.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 200, 1));
				source.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 2, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void earthProtection(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource() == DamageSource.FALL) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_earthprotection)) {
				event.setAmount(event.getAmount() / 4);
			}
		}
	}
	
	@SubscribeEvent
	public void fireProtection(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE)) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_flameprotection)) {
				event.setAmount(event.getAmount() * 0.6f);
			}
		}
	}
	
	@SubscribeEvent
	public void hungerProtection(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource() == DamageSource.STARVE) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_hungerprotection)) {
				event.setAmount(0);
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 100, 1));
			}
		}
	}
	
	@SubscribeEvent
	public void waterProtection(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource() == DamageSource.DROWN) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_waterprotection)) {
				event.setAmount(0);
				event.setCanceled(true);
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 200, 0));
			}
		}
	}
	
	@SubscribeEvent
	public void spiritProtection(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLiving) {
			EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (Util.attemptDamagePoppet(player, ModObjects.poppet_spiritbane) && BewitchmentAPI.isSpirit(attacker)) {
				event.setAmount(event.getAmount() * 0.6f);
				event.getEntity().playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 2, 1);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void vampiric(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack.getItem() == ModObjects.poppet_vampiric && stack.hasTagCompound() && stack.getTagCompound().hasKey("boundId")) {
					String uuid = stack.getTagCompound().getString("boundId");
					Entity entity = getEntity(player.world, uuid);
					if (entity instanceof EntityLivingBase) {
						if (entity.attackEntityFrom(event.getSource(), event.getAmount())) {
							event.getEntity().playSound(SoundEvents.ENTITY_WITCH_DRINK, 2, 1);
							event.setAmount(0);
							stack.damageItem(1, player);
							if (stack.getItemDamage() == stack.getItem().getMaxDamage()) stack.damageItem(1, player);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void toolProtection(PlayerDestroyItemEvent event) {
		if (!event.getEntityPlayer().world.isRemote && event.getOriginal().getItem() != ModObjects.poppet_tool && Util.attemptDamagePoppet(event.getEntityPlayer(), ModObjects.poppet_tool)) {
			event.getEntityPlayer().setHeldItem(event.getHand(), event.getOriginal());
			event.getEntityPlayer().playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 5, 1);
		}
	}
	
	@Nullable
	private Entity getEntity(World world, String uuid) {
		return world.getEntities(Entity.class, e -> e != null && e.getPersistentID().toString().equals(uuid)).stream().findFirst().orElse(null);
	}
}
