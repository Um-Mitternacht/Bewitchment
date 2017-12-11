package com.bewitchment.common.brew;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This class was created by Arekkuusu on 12/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class AutoPlantBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0x003506;
	}

	@Override
	public String getName() {
		return "auto_plant";
	}

	@Override
	void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		AxisAlignedBB space = new AxisAlignedBB(pos).grow(2 + MathHelper.clamp(amplifier, 0, 5));
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, space, input -> input != null && isSeed(input.getItem()));
		if (items.isEmpty()) return;
		plantAll(items, world, pos, amplifier);
	}

	private boolean isSeed(ItemStack stack) {
		return stack.getItem() instanceof IPlantable;
	}

	private void plantAll(List<EntityItem> items, World world, BlockPos pos, int amplifier) {
		int box = 1 + (int) ((float) amplifier / 2F);

		BlockPos posI = pos.add(box, 1, box);
		BlockPos posF = pos.add(-box, -1, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);

		for (EntityItem item : items) {
			ItemStack stack = item.getItem();
			for (BlockPos spot : spots) {
				if (stack.isEmpty()) {
					item.setDead();
					break;
				}
				IBlockState state = world.getBlockState(spot);
				IPlantable seed = (IPlantable) stack.getItem();
				if (world.isAirBlock(spot.up()) && state.getBlock().canSustainPlant(state, world, spot, EnumFacing.UP, seed)) {
					world.setBlockState(spot.up(), seed.getPlant(world, spot.up()));
					stack.shrink(1);
				}
			}
		}
	}
}
