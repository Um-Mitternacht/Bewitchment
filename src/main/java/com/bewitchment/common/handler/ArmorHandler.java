package com.bewitchment.common.handler;

import com.bewitchment.api.BewitchmentAPI;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class ArmorHandler {
	@SubscribeEvent
	public void reducePotionDamage(LivingDamageEvent event) {
		if (!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getSource().getDamageType().equals("indirectMagic") && BewitchmentAPI.hasAlchemistGear(player)) {
				event.setAmount(event.getAmount() * 0.5f);
			}
		}
	}
	
	@SubscribeEvent
	public void reduceNegativeEffectLevel(TickEvent.PlayerTickEvent event) {
		if (!event.player.world.isRemote && event.player.world.getTotalWorldTime() % 40 == 0) {
			EntityPlayer player = event.player;
			if (BewitchmentAPI.hasAlchemistGear(player) && (player.isPotionActive(MobEffects.WITHER) || player.isPotionActive(MobEffects.POISON))) {
				player.removePotionEffect(MobEffects.WITHER);
				player.removePotionEffect(MobEffects.POISON);
			}
		}
	}
	
	@SubscribeEvent
	public void greenWitchRegen(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if (!player.world.isRemote && player.world.getTotalWorldTime() % 40 == 0 && BewitchmentAPI.hasGreenWitchGear(player)) {
			if (BiomeDictionary.hasType(player.world.getBiome(player.getPosition()), BiomeDictionary.Type.FOREST) && player.world.isDaytime() && player.world.getBlockState(player.getPosition().down()).getBlock() == Blocks.GRASS) {
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 0));
			}
		}
	}
	
	@SubscribeEvent
	public void greenWitchHarvest(BlockEvent.HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player != null && !player.world.isRemote && BewitchmentAPI.hasGreenWitchGear(player) && event.getState().getBlock() instanceof BlockCrops && ((BlockCrops) event.getState().getBlock()).isMaxAge(event.getState()) && player.getRNG().nextBoolean()) {
			List<ItemStack> result = new ArrayList<>(event.getDrops());
			event.getDrops().clear();
			for (ItemStack stack : result) stack.shrink(-player.getRNG().nextInt(3));
			event.getDrops().addAll(result);
		}
	}
}
