package com.bewitchment.common.item.tool;

import com.bewitchment.common.block.BlockJuniperChest;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Joseph on 3/17/2020.
 */
public class ItemKeyring extends ItemJuniperKey {
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (player.isCreative() && (world.getBlockState(pos).getBlock() instanceof BlockJuniperChest || world.getBlockState(pos).getBlock() == ModObjects.juniper_door.door)) {
			setTags(world, pos, stack);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
	}
}
