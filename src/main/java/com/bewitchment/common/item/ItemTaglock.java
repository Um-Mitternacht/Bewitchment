package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockBed;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ItemTaglock extends Item {
	public ItemTaglock() {
		super();
		Util.registerItem(this, "taglock", Collections.singletonList(s -> s.hasTagCompound() && s.getTagCompound().hasKey("boundName")));
	}
	
	public static EntityPlayer getPlayerFromTaglock(ItemStack stack) {
		if (stack.getItem() instanceof ItemTaglock && stack.hasTagCompound() && stack.getTagCompound().hasKey("boundId")) {
			return Util.findPlayer(UUID.fromString(stack.getTagCompound().getString("boundId")));
		}
		return null;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		if (!player.world.isRemote && target != null && target.isNonBoss()) {
			setTags(player, hand, target);
			return true;
		}
		return false;
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
		//todo get the bed also from the bottom block
		if (player.isSneaking() && world.getBlockState(pos).getBlock() instanceof BlockBed) {
			for (EntityPlayer player0 : world.playerEntities) {
				if (player0.getBedLocation() != null && player0.getBedLocation().equals(pos)) {
					setTags(player, hand, player0);
					if (world.isRemote) world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	private void setTags(EntityPlayer player, EnumHand hand, EntityLivingBase target) {
		ItemStack playerHeldItem = player.getHeldItem(hand);
		if (!playerHeldItem.hasTagCompound()) {
			ItemStack stack = new ItemStack(ModObjects.taglock, 1);
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("boundId", target.getPersistentID().toString());
			stack.getTagCompound().setString("boundName", target.getName());
			Util.giveAndConsumeItem(player, hand, stack);
		}
	}
}