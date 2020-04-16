package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemElderberryJelly extends ItemFood {
	public ItemElderberryJelly() {
		super(5, 0.8f, false);
		Util.registerItem(this, "garlic_grilled");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		if (BewitchmentAPI.isVampire(player)) player.setFire(25);
	}
}