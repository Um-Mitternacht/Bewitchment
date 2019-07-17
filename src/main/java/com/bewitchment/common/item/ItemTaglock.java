package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.block.BlockBed;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ItemTaglock extends Item {
	public ItemTaglock() {
		super();
		Util.registerItem(this, "taglock", Collections.singletonList(s -> s.hasTagCompound() && s.getTagCompound().hasKey("boundName")));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("boundName")) {
			String boundName = stack.getTagCompound().getString("boundName");
			tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), boundName));
		}
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (player.isSneaking() && world.getBlockState(pos).getBlock() instanceof BlockBed) {
			for (EntityPlayer player0 : world.playerEntities) {
				if (player0.getBedLocation() != null && player0.getBedLocation().equals(pos)) {
					setTags(player, hand, player0);
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		if (target != null) {
			setTags(player, hand, target);
			return true;
		}
		return false;
	}
	
	private void setTags(EntityPlayer player, EnumHand hand, EntityLivingBase target) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("boundName")) {
			ItemStack copy = stack.copy();
			copy.setCount(1);
			copy.getTagCompound().setString("boundId", target.getPersistentID().toString());
			copy.getTagCompound().setString("boundName", target.getName());
			Util.giveAndConsumeItem(player, hand, copy);
		}
	}
}