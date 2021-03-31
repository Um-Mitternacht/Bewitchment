package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.entity.spirit.demon.EntityBaphomet;
import com.bewitchment.common.entity.spirit.demon.EntityLeonard;
import com.bewitchment.common.entity.util.IPledgeable;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

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
		player.addItemStackToInventory(new ItemStack(Items.BOWL));
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.POISON, 750, 3));
		List<Entity> entities = world.getEntitiesWithinAABB(ModEntityMob.class, new AxisAlignedBB(player.posX - 8, player.posY - 5, player.posZ - 8, player.posX + 8, player.posY + 5, player.posZ + 8), e -> e instanceof IPledgeable);
		if (!entities.isEmpty() && (BewitchmentAPI.hasAlchemistGear(player) || BewitchmentAPI.hasBesmirchedGear(player) || BewitchmentAPI.hasGreenWitchGear(player) || BewitchmentAPI.hasWitchesGear(player))) {
			IPledgeable boss = (IPledgeable) entities.get(0);
			if (boss instanceof EntityBaphomet && player.getHeldItem(EnumHand.OFF_HAND).getItem() == ModObjects.pentacle) {
				ExtendedWorld.pledgePlayerToDemon(world, player, boss);
				world.playSound(player, 0.5, 0.5, 0.5, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1, 1);
			} else if (boss instanceof EntityLeonard && player.getHeldItem(EnumHand.OFF_HAND).getItem() == Item.getItemFromBlock(ModObjects.green_candle)) {
				ExtendedWorld.pledgePlayerToDemon(world, player, boss);
				world.playSound(player, 0.5, 0.5, 0.5, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1, 1);
			}
		}
	}
}