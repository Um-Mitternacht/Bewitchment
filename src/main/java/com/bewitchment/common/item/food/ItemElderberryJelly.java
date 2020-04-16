package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemElderberryJelly extends ItemFood {
	public ItemElderberryJelly() {
		super(5, 0.8f, false);
		Util.registerItem(this, "elderberry_jelly");
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return new ItemStack(Items.GLASS_BOTTLE);
	}
}