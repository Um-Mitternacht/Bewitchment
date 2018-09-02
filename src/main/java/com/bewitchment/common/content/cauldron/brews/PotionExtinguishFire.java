package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class PotionExtinguishFire extends BrewMod {

	public PotionExtinguishFire() {
		super("extinguish_fire", false, 0x3EB489, true, 0);
	}

	private static boolean canExtinguish(World world, int amplifier) {
		int dimId = world.provider.getDimension();
		return dimId != DimensionType.NETHER.getId() || amplifier > 2;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		if (entity.isBurning() && canExtinguish(entity.world, amplifier)) {
			entity.world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);
			entity.extinguish();
		}
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);
		int radius = modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		if (canExtinguish(world, amplifier)) {
			if (side != null) {
				pos = pos.offset(side);
			}
			int box = 1 + radius;
			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);
			BlockPos posI = pos.add(box, Math.min(box, 3), box);
			BlockPos posF = pos.add(-box, Math.max(-box, -3), -box);
			BlockPos.getAllInBox(posI, posF).forEach(in -> {
				if (world.getBlockState(in).getBlock() == Blocks.FIRE) {
					world.setBlockToAir(in);
				} else {
					if (amplifier > 0) {
						if (world.getBlockState(in).getMaterial() == Material.FIRE) {
							world.setBlockToAir(in);
						}
					}
				}
			});
		}
	}

}
