package com.bewitchment.common.item.food;

import com.bewitchment.Util;
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
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
	}
}