package com.bewitchment.common.item.magic.brew;

import com.bewitchment.api.BrewRegistry;
import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.BrewUtils;
import com.bewitchment.common.core.WitchcraftCreativeTabs;
import com.bewitchment.common.core.capability.brew.BrewStorageHandler;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.bewitchment.api.BrewRegistry.Brew.DRINK;

/**
 * This class was created by BerciTheBeast on 27.3.2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ItemBrewDrink extends ItemBrew {

	public ItemBrewDrink() {
		super(LibItemName.BREW_PHIAL_DRINK);
		setCreativeTab(WitchcraftCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
		EntityPlayer entityplayer = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;
		if (!world.isRemote) {
			for (BrewEffect effect : BrewUtils.getBrewsFromStack(stack)) {
				BrewStorageHandler.addEntityBrewEffect(entity, effect);
			}

			for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
				entity.addPotionEffect(new PotionEffect(potioneffect));
			}
		}

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			stack.shrink(1);
			if (stack.getCount() <= 0) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (entityplayer != null) {
				entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return stack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 16;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			BrewRegistry.getDefaults().get(DRINK).forEach((brew, brewEffect) ->
					items.add(BrewUtils.createBrew(DRINK, brew))
			);
		}
	}
}
