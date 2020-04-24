package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemJuniperTea extends ItemFood {
	public ItemJuniperTea() {
		super(6, 0.55f, false);
		Util.registerItem(this, "juniper_tea");
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		this.setPotionEffect(new PotionEffect(ModPotions.absence, 1, 0), 0.2F);
		return new ItemStack(Items.GLASS_BOTTLE);
	}
}