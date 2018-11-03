package com.bewitchment.common.item.food;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

//Todo: Make this expand ME only once, and make sure it's ME extension is working
public class ItemMagicSalve extends ItemMod implements IMagicPowerExpander {

	public ItemMagicSalve() {
		super(LibItemName.MAGIC_SALVE);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 60;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		player.setActiveHand(hand);
		BewitchmentAPI.getAPI().expandPlayerMP(this, player);
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ResourceLocation getID() {
		return this.getRegistryName();
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase player) {
		player.addPotionEffect(new PotionEffect(MobEffects.POISON, 1000, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 1000, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1000, 1));
		return new ItemStack(Items.GLASS_BOTTLE);
	}

	@Override
	public int getExtraAmount(EntityPlayer p) {
		return 75;
	}

}
