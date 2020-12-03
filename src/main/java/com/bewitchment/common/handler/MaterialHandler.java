package com.bewitchment.common.handler;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.misc.Weakness;
import com.bewitchment.registry.ModEnchantments;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class MaterialHandler {
	public static final Set<Item> SILVER_TOOLS = new HashSet<>(), SILVER_ARMOR = new HashSet<>(), COLD_IRON_TOOLS = new HashSet<>(), COLD_IRON_ARMOR = new HashSet<>();

	public static float getDamage(float initialDamage, Weakness weakness, EntityLivingBase target, EntityLivingBase attacker, Set<Item> tools, Set<Item> armor) {
		float amount = weakness.get(target);

		if (amount > 1.0F && tools.contains(attacker.getHeldItemMainhand().getItem()))
			return initialDamage * amount;

		amount = weakness.get(attacker);

		if (amount > 1.0F) {
			int a = 0;

			for (ItemStack stack : target.getArmorInventoryList()) if (armor.contains(stack.getItem())) a++;

			if (a > 0) {
				attacker.attackEntityFrom(DamageSource.causeThornsDamage(target), a);
				return initialDamage * (1 - (0.06F * a));
			}
		}

		return initialDamage;
	}

	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!entity.world.isRemote && entity.ticksExisted % 20 == 0) {
			if (BewitchmentAPI.SILVER_WEAKNESS.contains(entity)) {
				int armor = 0;
				for (ItemStack stack : entity.getArmorInventoryList())
					if (SILVER_ARMOR.contains(stack.getItem())) armor++;
				if (armor > 0) entity.attackEntityFrom(DamageSource.MAGIC, armor);
			}
			if (BewitchmentAPI.COLD_IRON_WEAKNESS.contains(entity)) {
				int armor = 0;
				for (ItemStack stack : entity.getArmorInventoryList())
					if (COLD_IRON_ARMOR.contains(stack.getItem())) armor++;
				if (armor > 0) entity.attackEntityFrom(DamageSource.MAGIC, armor);
			}
		}
	}

	@SubscribeEvent
	public void livingHurt(@NotNull LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		Entity entity = event.getSource().getImmediateSource();

		if (!target.world.isRemote) {
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) entity;

				{ //silver
					float damage = getDamage(event.getAmount(), BewitchmentAPI.SILVER_WEAKNESS, target, attacker, SILVER_TOOLS, SILVER_ARMOR);
					event.setAmount(damage);
				}

				{ //cold iron
					float damage = getDamage(event.getAmount(), BewitchmentAPI.COLD_IRON_WEAKNESS, target, attacker, COLD_IRON_TOOLS, COLD_IRON_ARMOR);
					event.setAmount(damage);
				}

				ModEnchantments.magic_protection.applyEnchantment(event, Util.getArmorPieces(target, ModObjects.ARMOR_WITCHES));
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void materialToolTip(ItemTooltipEvent event) {
		if (SILVER_TOOLS.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.GOLD + I18n.format("tooltip.bewitchment.silver_tool_description.name"));
		if (SILVER_ARMOR.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.GOLD + I18n.format("tooltip.bewitchment.silver_armor_description.name"));
		if (COLD_IRON_TOOLS.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.GOLD + I18n.format("tooltip.bewitchment.cold_iron_tool_description.name"));
		if (COLD_IRON_ARMOR.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.GOLD + I18n.format("tooltip.bewitchment.cold_iron_armor_description.name"));
	}
}