package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@SuppressWarnings({"unused", "ConstantConditions"})
public class PotionEnderference extends ModPotion {
	public PotionEnderference() {
		super("enderference", true, 0x7fc07f);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (EntityRegistry.getEntry(living.getClass()).getRegistryName().toString().toLowerCase().contains("ender")) living.attackEntityFrom(DamageSource.MAGIC, 8 * (amplifier + 1));
	}
	
	@SubscribeEvent
	public void onAttacked(EnderTeleportEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this)) event.setCanceled(true);
	}
}