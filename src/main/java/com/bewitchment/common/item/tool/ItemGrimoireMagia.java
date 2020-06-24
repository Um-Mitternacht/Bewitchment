package com.bewitchment.common.item.tool;

import com.bewitchment.ModConfig;
import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "ConstantConditions", "NullableProblems"})
public class ItemGrimoireMagia extends Item {
	public ItemGrimoireMagia() {
		super();
		setMaxStackSize(1);
		Util.registerItem(this, "grimoire_magia", Collections.singletonList(s -> s.getDisplayName().equalsIgnoreCase("The Grimoire of Alice") || s.getDisplayName().equalsIgnoreCase("Grimoire of Alice")));
	}

	public static ItemStack create(int amount) {
		ItemStack stack = new ItemStack(ModObjects.grimoire_magia);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("maxAmount", ModConfig.misc.maxGrimoirePower);
		stack.getTagCompound().setInteger("amount", amount);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		int amount = 0, maxAmount = 0;
		if (stack.hasTagCompound()) {
			amount = stack.getTagCompound().getInteger("amount");
			maxAmount = stack.getTagCompound().getInteger("maxAmount");
		}
		tooltip.add(I18n.format("tooltip.bewitchment.grimoire_magia", amount, maxAmount));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			list.add(create(0));
			list.add(create(ModConfig.misc.maxGrimoirePower));
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			return tag.getInteger("amount") < tag.getInteger("maxAmount");
		}
		return super.showDurabilityBar(stack);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			return 1 - (double) tag.getInteger("amount") / tag.getInteger("maxAmount");
		}
		return super.getDurabilityForDisplay(stack);
	}
}