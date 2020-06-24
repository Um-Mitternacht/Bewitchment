package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.potion.Potion.getPotionById;

@SuppressWarnings({"NullableProblems", "unchecked"})
public class ItemBloodBottle extends Item {
	public ItemBloodBottle() {
		this.maxStackSize = 64;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase living) {
		EntityPlayer entityplayer = living instanceof EntityPlayer ? (EntityPlayer) living : null;

		if (entityplayer instanceof EntityPlayerMP) {
			CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
		}

		if (entityplayer != null) {
			if (!entityplayer.world.isRemote) {
				Random rand = entityplayer.getRNG();
				if (rand.nextDouble() < 0.8)
					entityplayer.addPotionEffect(new PotionEffect(Objects.requireNonNull(getPotionById(17)), 200, 1));
				entityplayer.addPotionEffect(new PotionEffect(Objects.requireNonNull(getPotionById(5)), 400, 0));
				entityplayer.getFoodStats().addStats(3, 0);
			}

			if (!entityplayer.isCreative()) {
				Util.giveItem(entityplayer, new ItemStack(Items.GLASS_BOTTLE));
				stack.shrink(1);
			}
		}

		return stack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
}
