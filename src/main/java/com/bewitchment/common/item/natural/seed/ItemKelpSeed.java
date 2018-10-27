package com.bewitchment.common.item.natural.seed;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.statics.ModCrops;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 02/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemKelpSeed extends ItemSeed {

	public ItemKelpSeed() {
		super(LibItemName.SEED_KELP, ModBlocks.crop_kelp, ModCrops.KELP.getSoil());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		final RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
		ItemStack stack = playerIn.getHeldItem(hand);
		if (raytraceresult == null) {
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		}
		if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
			final BlockPos blockpos = raytraceresult.getBlockPos();

			if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, stack)) {
				return ActionResult.newResult(EnumActionResult.FAIL, stack);
			}

			final BlockPos up = blockpos.up();
			final IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if (iblockstate.getMaterial().isSolid()) {
				// special case for handling block placement with water lilies
				BlockPos I = up.add(-1, 0, -1);
				BlockPos F = up.add(1, 1, 1);
				for (BlockPos blockPos : BlockPos.getAllInBox(I, F)) {
					Block block = worldIn.getBlockState(blockPos).getBlock();
					if (block != ModBlocks.crop_kelp && block != Blocks.WATER) {
						return ActionResult.newResult(EnumActionResult.FAIL, stack);
					}
				}
				final net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(worldIn, up);
				if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(playerIn, blocksnapshot, net.minecraft.util.EnumFacing.UP, hand).isCanceled()) {
					blocksnapshot.restore(true, false);
					return ActionResult.newResult(EnumActionResult.FAIL, stack);
				}

				worldIn.setBlockState(up, this.crop.getDefaultState(), 11);

				if (!playerIn.capabilities.isCreativeMode) {
					stack.shrink(1);
				}

				worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
		}

		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.FAIL;
	}
}
