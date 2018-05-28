/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.item.block;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.magic.BlockWitchFire;
import com.bewitchment.common.block.magic.BlockWitchFire.EnumFireType;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockRevealingLantern extends ItemBlock {

	private boolean lit;

	public ItemBlockRevealingLantern(Block block, boolean lit) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.lit = lit;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() < 16 && stack.getMetadata() >= 0) {
			return super.getUnlocalizedName(stack) + "." + EnumDyeColor.values()[stack.getMetadata()].getDyeColorName();
		}
		return super.getUnlocalizedName(stack);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return lit;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() == ModBlocks.witchfire && worldIn.getBlockState(pos).getValue(BlockWitchFire.TYPE) == EnumFireType.SIGHTFIRE && player.getHeldItem(hand).getItem().equals(Item.getItemFromBlock(ModBlocks.lantern))) {
			player.setHeldItem(hand, new ItemStack(ModBlocks.revealing_lantern, 1, player.getHeldItem(hand).getMetadata()));
			worldIn.setBlockToAir(pos);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
