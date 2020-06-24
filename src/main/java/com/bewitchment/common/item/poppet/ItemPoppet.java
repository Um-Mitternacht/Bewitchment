package com.bewitchment.common.item.poppet;

import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"NullableProblems"})
public class ItemPoppet extends Item {
	public ItemPoppet(boolean oneTimeUse) {
		super();
		setMaxStackSize(1);
		setMaxDamage(oneTimeUse ? 1 : 50);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		EnumHand otherHand = hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
		if (!player.getHeldItem(hand).hasTagCompound() && player.getHeldItem(otherHand).getItem() instanceof ItemTaglock && player.getHeldItem(otherHand).hasTagCompound() && player.getHeldItem(otherHand).getTagCompound().hasKey("boundId") && player.getHeldItem(otherHand).getTagCompound().hasKey("boundName")) {
			ItemStack result = player.getHeldItem(hand);
			ItemStack taglock = player.getHeldItem(otherHand);
			result.setTagCompound(new NBTTagCompound());
			result.getTagCompound().setString("boundId", taglock.getTagCompound().getString("boundId"));
			result.getTagCompound().setString("boundName", taglock.getTagCompound().getString("boundName"));
			player.setHeldItem(hand, result);
			player.getHeldItem(otherHand).shrink(1);
			if (world.isRemote)
				world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 10.0F, 1.0F);
			return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("boundName")) {
			String boundName = stack.getTagCompound().getString("boundName");
			tooltip.add(I18n.format("tooltip.bewitchment.poppet.bound", boundName));
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return stack.hasTagCompound();
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
}
