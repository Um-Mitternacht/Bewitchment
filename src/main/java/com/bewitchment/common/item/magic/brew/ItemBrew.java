package com.bewitchment.common.item.magic.brew;

import com.bewitchment.common.brew.BrewUtils;
import com.bewitchment.common.core.ModCreativeTabs;
import com.bewitchment.common.core.helper.NBTHelper;
import com.bewitchment.common.item.ItemMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This class was created by Arekkuusu on 22/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemBrew extends ItemMod {

	public ItemBrew(String id) {
		super(id);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		if (NBTHelper.hasTag(stack, BrewUtils.BREW_DESC)) {
			tooltip.add(TextFormatting.ITALIC + I18n.format(NBTHelper.getString(stack, BrewUtils.BREW_DESC)));
		}
		if (GuiScreen.isShiftKeyDown()) {
			tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + I18n.format("tooltip.brew.data"));
			BrewUtils.addBrewTooltip(stack, tooltip);

			tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + I18n.format("tooltip.potion.data"));
			BrewUtils.addPotionTooltip(stack, tooltip);
		} else {
			tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + I18n.format("tooltip.shift_for_info"));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (NBTHelper.hasTag(stack, BrewUtils.BREW_NAME)) {
			TextComponentTranslation text = new TextComponentTranslation(NBTHelper.getString(stack, BrewUtils.BREW_NAME));
			return text.getFormattedText();
		}
		return super.getItemStackDisplayName(stack);
	}
}
