package com.witchcraft.common.brew;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ExtinguishFiresBrew extends BlockHitBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity.isBurning() && canExtinguish(world, pos, amplifier)) {
			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);

			entity.extinguish();
		}
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		//NO-OP
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		if (side != null) pos = pos.offset(side);
		if (canExtinguish(world, pos, amplifier)) {
			int box = 1 + (int) ((float) amplifier / 2F);

			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);

			BlockPos posI = pos.add(box, box, box);
			BlockPos posF = pos.add(-box, -box, -box);

			Iterable<BlockPos> poses = BlockPos.getAllInBox(posI, posF);
			poses.forEach(
					in -> {
						Material material = world.getBlockState(in).getMaterial();
						if (material == Material.FIRE) {
							world.setBlockToAir(in);
						}
					}
			);
		}
	}

	private boolean canExtinguish(World world, BlockPos pos, int amplifier) {
		int dimId = world.provider.getDimension();
		return dimId == DimensionType.OVERWORLD.getId() || (dimId == DimensionType.NETHER.getId() && amplifier > 2);
	}

	@Override
	public int getColor() {
		return 0x3EB489;
	}

	@Override
	public String getName() {
		return "extinguish_fires";
	}
}
