package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ItemTarotCards extends Item {
	public ItemTarotCards() {
		super();
		setMaxStackSize(1);
		Util.registerItem(this, "tarot_cards");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("readName")) {
			String readName = stack.getTagCompound().getString("readName");
			tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), readName));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			setTags(stack, player);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		return !player.isSneaking() && setTags(stack, target);
	}
	
	private boolean setTags(ItemStack stack, EntityLivingBase target) {
		if (target instanceof EntityPlayer) {
			if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("readId", ((EntityPlayer) target).getGameProfile().getId().toString());
			stack.getTagCompound().setString("readName", ((EntityPlayer) target).getGameProfile().getName());
			return true;
		}
		return false;
	}
}