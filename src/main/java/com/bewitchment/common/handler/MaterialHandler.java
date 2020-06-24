package com.bewitchment.common.handler;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
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

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class MaterialHandler {
	public static final Set<Item> SILVER_TOOLS = new HashSet<>(), SILVER_ARMOR = new HashSet<>(), COLD_IRON_TOOLS = new HashSet<>(), COLD_IRON_ARMOR = new HashSet<>();

	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!entity.world.isRemote) {
			if (BewitchmentAPI.getSilverWeakness(entity) > 1) {
				int armor = 0;
				for (ItemStack stack : entity.getArmorInventoryList())
					if (SILVER_ARMOR.contains(stack.getItem())) armor++;
				if (armor > 0 && entity.ticksExisted % 20 == 0) entity.attackEntityFrom(DamageSource.MAGIC, armor);
			}
			if (BewitchmentAPI.getColdIronWeakness(entity) > 1) {
				int armor = 0;
				for (ItemStack stack : entity.getArmorInventoryList())
					if (COLD_IRON_ARMOR.contains(stack.getItem())) armor++;
				if (armor > 0 && entity.ticksExisted % 20 == 0) entity.attackEntityFrom(DamageSource.MAGIC, armor);
			}
		}
	}

	@SubscribeEvent
	public void livingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!entity.world.isRemote) {
			Entity source = event.getSource().getImmediateSource();
			if (source instanceof EntityLivingBase) {
				{ //silver
					float weakness = BewitchmentAPI.getSilverWeakness(entity);
					if (weakness > 1 && SILVER_TOOLS.contains(((EntityLivingBase) source).getHeldItemMainhand().getItem()))
						event.setAmount(event.getAmount() * weakness);
					weakness = BewitchmentAPI.getSilverWeakness((EntityLivingBase) source);
					if (weakness > 1) {
						int armor = 0;
						for (ItemStack stack : entity.getArmorInventoryList())
							if (SILVER_ARMOR.contains(stack.getItem())) armor++;
						if (armor > 0) {
							event.setAmount(event.getAmount() * (1 - (0.06f * armor)));
							source.attackEntityFrom(DamageSource.causeThornsDamage(entity), armor);
						}
					}
				}
				{ //cold iron
					float weakness = BewitchmentAPI.getColdIronWeakness(entity);
					if (weakness > 1 && COLD_IRON_TOOLS.contains(((EntityLivingBase) source).getHeldItemMainhand().getItem()))
						event.setAmount(event.getAmount() * weakness);
					weakness = BewitchmentAPI.getColdIronWeakness((EntityLivingBase) source);
					if (weakness > 1) {
						int armor = 0;
						for (ItemStack stack : entity.getArmorInventoryList())
							if (COLD_IRON_ARMOR.contains(stack.getItem())) armor++;
						if (armor > 0) {
							event.setAmount(event.getAmount() * (1 - (0.06f * armor)));
							source.attackEntityFrom(DamageSource.causeThornsDamage(entity), armor);
						}
					}
				}
				//witches
				ModEnchantments.magic_protection.applyEnchantment(event, Util.getArmorPieces(event.getEntityLiving(), ModObjects.ARMOR_WITCHES));
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void materialToolTip(ItemTooltipEvent event) {
		if (SILVER_TOOLS.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.BLUE + I18n.format("tooltip.bewitchment.silver_tool_description.name"));
		if (SILVER_ARMOR.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.BLUE + I18n.format("tooltip.bewitchment.silver_armor_description.name"));
		if (COLD_IRON_TOOLS.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.BLUE + I18n.format("tooltip.bewitchment.cold_iron_tool_description.name"));
		if (COLD_IRON_ARMOR.contains(event.getItemStack().getItem()))
			event.getToolTip().add(TextFormatting.BLUE + I18n.format("tooltip.bewitchment.cold_iron_armor_description.name"));
	}
}