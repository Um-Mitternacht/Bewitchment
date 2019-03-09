package com.bewitchment.common.item.equipment;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemColdIronArmor extends ItemArmor implements IModelRegister {
	public ItemColdIronArmor(String id, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setRegistryName(id);
		setTranslationKey(LibMod.MOD_ID + "." + id);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private static boolean isSpirit(Entity e) {
		if (e instanceof EntityLivingBase) {
			if (((EntityLivingBase) e).getCreatureAttribute() == BewitchmentAPI.getAPI().SPIRIT) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.bewitchment." + getArmorMaterial().name() + "_armor" + "_description.name"));
	}

	//Todo: Reduce the chance at which thorns fire, and make it resist demon damage a bit as well
	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event) {
		if (getArmorPieces(event.getEntityLiving()) > 0) {
			DamageSource source = event.getSource();
			Entity attacker = source.getTrueSource();
			if (attacker instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) attacker;
				EntityLivingBase attacked = event.getEntityLiving();
				if (living.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON) {
					if (!source.isProjectile())
						living.attackEntityFrom(DamageSource.causeThornsDamage(attacked), EnchantmentThorns.getDamage(getArmorPieces(attacked), attacked.getRNG()));
					event.setAmount(event.getAmount() * (1 - .05f * getArmorPieces(attacked)));
				}
				if (isSpirit(event.getSource().getTrueSource())) {
					event.setAmount(event.getAmount() * 0.80F);
				}
				if (event.getSource().isFireDamage()) {
					event.setAmount(event.getAmount() * 0.95F);
				}
			}
		}
	}

	private byte getArmorPieces(EntityLivingBase entity) {
		byte fin = 0;
		for (ItemStack stack : entity.getArmorInventoryList()) if (stack.getItem() instanceof ItemColdIronArmor) fin++;
		return fin;
	}
}
