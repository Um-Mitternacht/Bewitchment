package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class ItemSalt extends Item {
	public ItemSalt() {
		super();
		Util.registerItem(this, "salt", "salt", "itemSalt", "dustSalt", "foodSalt", "listAllSalt", "ingredientSalt", "pinchSalt", "portionSalt", "lumpSalt", "materialSalt");
	}

	@Override
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
		ItemStack stack = player.getHeldItem(hand);
		if (player.canPlayerEdit(pos0, face, stack) && world.mayPlace(world.getBlockState(pos0).getBlock(), pos0, false, face, player) && ModObjects.salt_barrier.canPlaceBlockAt(world, pos0)) {
			stack.shrink(1);
			world.setBlockState(pos0, ModObjects.salt_barrier.getDefaultState());
			world.playSound(null, pos, ModObjects.salt_barrier.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
}