package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemGarlic extends ItemFood {
	public ItemGarlic() {
		super(2, 0.2f, false);
		Util.registerItem(this, "garlic", "cropGarlic", "listAllherb");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		if (BewitchmentAPI.isVampire(player)) player.setFire(10);
	}
}