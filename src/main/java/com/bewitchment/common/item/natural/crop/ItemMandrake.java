package com.bewitchment.common.item.natural.crop;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 03/07/2017, and modified by Sunconure11 on 03/17/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemMandrake extends ItemCropFood implements IMagicPowerExpander {

	public ItemMandrake() {
		super(LibItemName.MANDRAKE, 1, 2F, false);
		setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 35, 1));
		IMagicPowerContainer playerMP = player.getCapability(IMagicPowerContainer.CAPABILITY, null);
		playerMP.fill(25);
	}

	@Override
	public ResourceLocation getID() {
		return this.getRegistryName();
	}

	@Override
	public int getExtraAmount(EntityPlayer p) {
		return 0;
	}
}
