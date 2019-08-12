package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemStewOfTheGrotesque extends ItemFood {
	public ItemStewOfTheGrotesque() {
		super(8, 1.25f, false);
		Util.registerItem(this, "stew_of_the_grotesque");
		setAlwaysEdible();
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.POISON, 750, 3));
	}
}