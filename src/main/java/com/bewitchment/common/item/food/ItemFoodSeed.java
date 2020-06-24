package com.bewitchment.common.item.food;

import com.bewitchment.common.integration.dynamictrees.DynamicTreesCompat;
import com.ferreusveritas.dynamictrees.blocks.BlockBonsaiPot;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

public class ItemFoodSeed extends ItemFood {
	public ItemFoodSeed(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
	}

	@Optional.Method(modid = "dynamictrees")
	public EnumActionResult onItemUseFlowerPot(EntityPlayer player, World world, BlockPos pos, EnumHand hand, ItemStack seedStack, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState emptyPotState = world.getBlockState(pos);
		if (emptyPotState.getBlock() instanceof BlockFlowerPot && emptyPotState == emptyPotState.getBlock().getDefaultState()) {
			Species species = getSpecies();
			if (species != null) {
				BlockBonsaiPot bonzaiPot = species.getBonzaiPot();
				world.setBlockState(pos, bonzaiPot.getDefaultState());
				if (bonzaiPot.setSpecies(world, species, pos) && bonzaiPot.setPotState(world, emptyPotState, pos)) {
					seedStack.shrink(1);
					return EnumActionResult.SUCCESS;
				}
			}
		}

		return EnumActionResult.PASS;
	}

	@Optional.Method(modid = "dynamictrees")
	public EnumActionResult onItemUsePlantSeed(EntityPlayer player, World world, BlockPos pos, EnumHand hand, ItemStack seedStack, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		if (block.isReplaceable(world, pos)) {
			pos = pos.down();
			facing = EnumFacing.UP;
		}

		if (facing == EnumFacing.UP && player.canPlayerEdit(pos, facing, seedStack) && player.canPlayerEdit(pos.up(), facing, seedStack) && this.doPlanting(world, pos.up())) {
			seedStack.shrink(1);
			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.PASS;
		}
	}

	@Optional.Method(modid = "dynamictrees")
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack seedStack = player.getHeldItem(hand);
		if (this.onItemUseFlowerPot(player, world, pos, hand, seedStack, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS) {
			return EnumActionResult.SUCCESS;
		} else {
			return this.onItemUsePlantSeed(player, world, pos, hand, seedStack, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
		}
	}

	private boolean doPlanting(World world, BlockPos pos) {
		Species species = getSpecies();
		if (species != null) return species.plantSapling(world, pos);
		return false;
	}

	private Species getSpecies() {
		if (this.getRegistryName().getPath().contains("juniper"))
			return DynamicTreesCompat.juniperTree.getCommonSpecies();
		if (this.getRegistryName().getPath().contains("elder")) return DynamicTreesCompat.elderTree.getCommonSpecies();
		return null;
	}
}
