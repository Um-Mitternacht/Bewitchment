package com.bewitchment.common.item.tool;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.magicpower.MagicPower;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "ConstantConditions", "NullableProblems"})
public class ItemGrimoireMagia extends Item {
	public ItemGrimoireMagia() {
		super();
		setMaxStackSize(1);
		Util.registerItem(this, "grimoire_magia", Collections.singletonList(s -> s.getDisplayName().equalsIgnoreCase("The Grimoire of Alice") || s.getDisplayName().equalsIgnoreCase("Grimoire of Alice")));
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound tag) {
		MagicPower power = new MagicPower();
		power.maxAmount = Bewitchment.config.maxGrimoirePower;
		return power;
	}
	
	@Nullable
	@Override
	public NBTTagCompound getNBTShareTag(ItemStack stack) {
		return stack.getCapability(MagicPower.CAPABILITY, null).serializeNBT();
	}
	
	@Override
	public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
		if (nbt != null) stack.getCapability(MagicPower.CAPABILITY, null).deserializeNBT(nbt);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		MagicPower cap = stack.getCapability(MagicPower.CAPABILITY, null);
		return cap.amount < cap.maxAmount;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		MagicPower cap = stack.getCapability(MagicPower.CAPABILITY, null);
		return 1 - (double) cap.amount / cap.maxAmount;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		MagicPower cap = stack.getCapability(MagicPower.CAPABILITY, null);
		tooltip.add(I18n.format("tooltip.bewitchment.grimoire_magia", cap.amount, cap.maxAmount));
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			list.add(new ItemStack(this));
			ItemStack full = new ItemStack(this);
			full.getCapability(MagicPower.CAPABILITY, null).amount = Bewitchment.config.maxGrimoirePower;
			list.add(full);
		}
	}
}