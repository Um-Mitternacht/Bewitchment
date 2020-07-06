package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class FortuneDropItem extends Fortune {
	public FortuneDropItem() {
		super(new ResourceLocation(Bewitchment.MODID, "drop_item"), true, (30), (60 * 15));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
		return player.dropItem(stack.splitStack(stack.getCount()), false) != null;
	}
}