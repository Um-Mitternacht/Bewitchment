package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import net.minecraft.item.ItemFood;

public class ItemGarlic extends ItemFood {
	public ItemGarlic() {
		super(2, 0.2f, false);
		Util.registerItem(this, "garlic", "cropGarlic", "listAllherb");
	}

	//	@Override
	//	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
	//		super.onFoodEaten(stack, world, player);
	//		if (player.getCapability(ExtendedPlayer.CAPABILITY, null).getTransformation() == TransformationType.VAMPIRE)
	//			player.setFire(10);
	//	}
}