package com.bewitchment.common.item.natural.crop;

import com.bewitchment.common.core.helper.NBTHelper;
import com.bewitchment.common.item.food.ItemModFood;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class was created by Arekkuusu on 01/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
public class ItemCropFood extends ItemModFood {

	private static final String DRY = "dry";
	private List<Potion> potions;

	public ItemCropFood(String id, int amount, float saturation, boolean isWolfFood) {
		super(id, amount, saturation, isWolfFood);
	}


	protected void addPotion(Potion... potionsIn) {
		this.potions = new ArrayList<>();
		Collections.addAll(this.potions, potionsIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.ITALIC + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}

	public String getNameInefficiently(ItemStack stack) {
		return getTranslationKey().substring(5);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		if (!worldIn.isRemote && potions != null) {
			final int modifier = isDry(stack) ? 160 : 80;
			for (Potion effect : potions) {
				player.addPotionEffect(new PotionEffect(effect, modifier, modifier / 80));
			}
		}

	}

	public boolean isDry(ItemStack stack) {
		return NBTHelper.getBoolean(stack, DRY);
	}

	public void setDry(ItemStack stack, boolean isDry) {
		NBTHelper.setBoolean(stack, DRY, isDry);
	}
}
