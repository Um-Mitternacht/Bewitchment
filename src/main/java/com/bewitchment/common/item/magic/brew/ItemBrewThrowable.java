package com.bewitchment.common.item.magic.brew;

import com.bewitchment.common.entity.EntityBrew;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBrewThrowable extends ItemBrew {

	public ItemBrewThrowable(String id) {
		super(id);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityBrew brew = new EntityBrew(worldIn, playerIn, playerIn.getHeldItem(handIn).copy());
			brew.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
			worldIn.spawnEntity(brew);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.isCreative() ? playerIn.getHeldItem(handIn) : ItemStack.EMPTY);
	}

}
