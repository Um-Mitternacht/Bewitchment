package com.bewitchment.common.item.equipment;

import java.util.List;

import javax.annotation.Nullable;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;

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

public class ItemColdIronArmor extends ItemArmor implements IModelRegister
{
	public ItemColdIronArmor(String id, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setRegistryName(id);
		setTranslationKey(id);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel()
	{
		ModelHandler.registerModel(this, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(TextFormatting.GRAY + I18n.format("witch.tooltip." + getTranslationKey().substring(5) + "_description.name"));
	}
	
	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event)
	{
		DamageSource source = event.getSource();
		Entity attacker = source.getTrueSource();
		if (attacker instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) attacker;
			EntityLivingBase attacked = event.getEntityLiving();
			if (living.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON)
			{
				if (!source.isProjectile()) living.attackEntityFrom(DamageSource.causeThornsDamage(attacked), EnchantmentThorns.getDamage(getArmorPieces(attacked), attacked.getRNG()));
				event.setAmount(event.getAmount()*(1-.05f*getArmorPieces(attacked)));
			}
		}
	}
	
	private byte getArmorPieces(EntityLivingBase entity)
	{
		byte fin = 0;
		for (ItemStack stack : entity.getArmorInventoryList()) if (stack.getItem() instanceof ItemColdIronArmor) fin++;
		return fin;
	}
}
