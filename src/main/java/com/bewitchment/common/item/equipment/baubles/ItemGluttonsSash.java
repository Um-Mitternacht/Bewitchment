package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


public class ItemGluttonsSash extends ModItemBauble {
	public ItemGluttonsSash() {
		super("gluttons_sash", BaubleType.BELT);
		maxStackSize = 1;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (!player.world.isRemote && player instanceof EntityPlayer && ((EntityPlayer) player).getFoodStats().needFood() && player.world.getTotalWorldTime() % 20 == 0 && MagicPower.attemptDrain(null, (EntityPlayer) player, 5)) {
			((EntityPlayer) player).getFoodStats().addStats(1, 1);
		}
	}
}
