package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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
public class ItemWaystone extends Item {
	public ItemWaystone() {
		super();
		setMaxStackSize(1);
		setMaxDamage(3);
		Util.registerItem(this, "waystone", Collections.singletonList(s -> s.hasTagCompound() && s.getTagCompound().hasKey("location")));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("location")) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setLong("location", pos.offset(face).toLong());
			stack.getTagCompound().setInteger("dimension", player.dimension);
			stack.getTagCompound().setString("dimensionName", world.provider.getDimensionType().getName());
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) {
			BlockPos pos = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
			tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), pos.getX(), pos.getY(), pos.getZ(), stack.getTagCompound().getString("dimensionName")));
		} else tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5) + ".unbound"));
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return (stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) || super.hasEffect(stack);
	}
}