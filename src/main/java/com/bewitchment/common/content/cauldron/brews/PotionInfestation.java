package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class PotionInfestation extends BrewMod {

	private static final DamageSource[] source = new DamageSource[]{DamageSource.WITHER, DamageSource.DROWN, DamageSource.ON_FIRE, DamageSource.MAGIC};

	public PotionInfestation() {
		super("infestation", true, 0xFF80DC, false, 3 * 60 * 20);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {

		if (entity instanceof EntityCow) {
			EntityMooshroom entitymooshroom = new EntityMooshroom(entity.world);
			entitymooshroom.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			entitymooshroom.setHealth(entity.getHealth());
			entitymooshroom.renderYawOffset = entity.renderYawOffset;
			if (entity.hasCustomName()) {
				entitymooshroom.setCustomNameTag(entity.getCustomNameTag());
			}
			entity.setDead();
			entity.world.spawnEntity(entitymooshroom);
			return;
		}

		entity.attackEntityFrom(source[entity.getRNG().nextInt(source.length)], 3 + entity.getRNG().nextFloat() * amplifier);
		if (entity.getRNG().nextBoolean() && amplifier > 1) {
			entity.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(amplifier)).forEach(e -> {
				if (e.getActivePotionEffect(this) == null) {
					e.addPotionEffect(new PotionEffect(this, this.getDefaultDuration(), amplifier - 1));
				}
			});
		}
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 2 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<MutableBlockPos> spots = BlockPos.getAllInBoxMutable(posI, posF);
		for (BlockPos spot : spots) {
			if (spot.distanceSq(pos) < 2 + box * box / 2) {
				IBlockState state = world.getBlockState(spot);
				Block mushroom = (state.getBlock().getRegistryName().toString().length() + state.getBlock().getMetaFromState(state)) % 2 == 0 ? Blocks.RED_MUSHROOM_BLOCK : Blocks.BROWN_MUSHROOM_BLOCK;
				Block mushroomSmall = world.rand.nextBoolean() ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM;
				if (world.rand.nextInt(4) <= modifiers.getLevel(DefaultModifiers.POWER).orElse(0) / 2) {
					if (state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS_PATH) {
						world.setBlockState(spot, Blocks.MYCELIUM.getDefaultState(), 3);
						if (world.isAirBlock(spot.up()) && world.rand.nextInt(10) == 0) {
							world.setBlockState(spot.up(), mushroomSmall.getDefaultState(), 3);
						}
					} else if (state.getBlock() == Blocks.COBBLESTONE) {
						world.setBlockState(spot, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3);
					} else if (state.getBlock() instanceof IPlantable) {
						world.setBlockState(spot, mushroomSmall.getDefaultState(), 2);
					} else if (state.getBlock() instanceof BlockLog) {
						world.setBlockState(spot, mushroom.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM), 3);
					} else if (state.getBlock() instanceof BlockLeaves) {
						world.setBlockState(spot, mushroom.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.ALL_OUTSIDE), 3);
					} else if (state.getBlock() == Blocks.STONEBRICK) {
						world.setBlockState(spot, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY), 3);
					}

					// TODO add wheat to infested wheat
				}
			}
		}
	}
}
