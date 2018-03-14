package com.bewitchment.common.item.magic.brew;

import com.bewitchment.api.brew.BrewUtils;
import com.bewitchment.common.core.ModCreativeTabs;
import com.bewitchment.common.entity.EntityBrew;
import com.bewitchment.common.internalApi.BrewRegistry;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.bewitchment.common.internalApi.BrewRegistry.Brew.SPLASH;

import javax.annotation.Nonnull;

/**
 * This class was created by BerciTheBeast on 27.3.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemBrewSplash extends ItemBrew {

	public ItemBrewSplash() {
		super(LibItemName.BREW_PHIAL_SPLASH);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		ItemStack copy = playerIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);
		playerIn.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityBrew brew = new EntityBrew(worldIn, playerIn, copy, EntityBrew.BrewDispersion.SPLASH);

			brew.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
			worldIn.spawnEntity(brew);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			BrewRegistry.getDefaults().get(SPLASH).forEach((brew, brewEffect) ->
					items.add(BrewUtils.createBrew(SPLASH, brew))
			);
		}
	}
}
