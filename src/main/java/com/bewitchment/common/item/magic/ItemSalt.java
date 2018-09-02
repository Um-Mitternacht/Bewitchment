package com.bewitchment.common.item.magic;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by BerciTheBeast on 27.3.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemSalt extends ItemMod {

	public ItemSalt() {
		super(LibItemName.SALT);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		final boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
		final BlockPos blockpos = flag ? pos : pos.offset(facing);

		ItemStack stack = playerIn.getHeldItem(hand);
		if (playerIn.canPlayerEdit(blockpos, facing, stack) && worldIn.mayPlace(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, playerIn) && ModBlocks.salt_barrier.canPlaceBlockAt(worldIn, blockpos)) {
			stack.shrink(1);
			worldIn.setBlockState(blockpos, ModBlocks.salt_barrier.getDefaultState());
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
}
