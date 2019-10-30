package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemBoxSealedEvil extends Item {
	public ItemBoxSealedEvil() {
		super();
		Util.registerItem(this,"box_of_sealed_evil");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	private ItemStack getRandomItem(Random random) {
		switch (random.nextInt(10)) {
			case 0:

		}
		return ItemStack.EMPTY;
	}
}
