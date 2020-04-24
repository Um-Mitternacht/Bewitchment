package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemJuniperTea extends ItemFood {
	public ItemJuniperTea() {
		super(5, 0.8f, false);
		Util.registerItem(this, "juniper_tea");
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		this.setPotionEffect(new PotionEffect(ModPotions.absence, 1, 0), 0.2F);
		return new ItemStack(Items.GLASS_BOTTLE);
	}
}