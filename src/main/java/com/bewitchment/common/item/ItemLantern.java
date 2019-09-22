package com.bewitchment.common.item;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ItemLantern extends ItemBlock {
	public ItemLantern(Block block) {
		super(block);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (player.isSneaking()) return super.onItemUseFirst(player, world, pos, face, hitX, hitY, hitZ, hand);
		if (player.getHeldItem(hand).hasTagCompound() && player.getHeldItem(hand).getTagCompound().getBoolean("lit") && world.getBlockState(pos.offset(face)).getBlock().isReplaceable(world, pos.offset(face)) && MagicPower.attemptDrain(null, player, 50)) {
			world.setBlockState(pos.offset(face), ModObjects.witches_light.getDefaultState());
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUseFirst(player, world, pos, face, hitX, hitY, hitZ, hand);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return (stack.hasTagCompound() && stack.getTagCompound().getBoolean("lit")) || super.hasEffect(stack);
	}
}