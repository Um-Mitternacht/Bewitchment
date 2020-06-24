package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionDesertification extends ModPotion {
	public PotionDesertification() {
		super("desertification", false, 0xffc400);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (living instanceof EntityWaterMob || living instanceof EntitySlime)
			living.attackEntityFrom(DamageSource.MAGIC, 6 * (amplifier + 1));
	}

	@Override
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		boolean flag = false;
		int radius = 2 * (amplifier + 1);
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
			if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
				Block block = world.getBlockState(pos0).getBlock();
				if (world.rand.nextInt(3) == 0) {
					if (block instanceof BlockStone || block instanceof BlockStoneBrick) {
						world.setBlockState(pos0, Blocks.SANDSTONE.getDefaultState());
						flag = true;
					} else if (block instanceof BlockDirt || block instanceof BlockGrass) {
						world.setBlockState(pos0, Blocks.DIRT.getDefaultState());
						flag = true;
					} else if (block instanceof BlockDoubleStoneSlab && world.getBlockState(pos0).getValue(BlockDoubleStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE) {
						world.setBlockState(pos0, Blocks.DOUBLE_STONE_SLAB.getDefaultState().withProperty(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND));
						flag = true;
					} else if (block instanceof BlockStoneSlab && world.getBlockState(pos0).getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE) {
						world.setBlockState(pos0, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, world.getBlockState(pos0).getValue(BlockSlab.HALF)));
						flag = true;
					} else if (block == Blocks.STONE_BRICK_STAIRS) {
						world.setBlockState(pos0, Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, world.getBlockState(pos0).getValue(BlockStairs.HALF)).withProperty(BlockStairs.FACING, world.getBlockState(pos0).getValue(BlockStairs.FACING)).withProperty(BlockStairs.SHAPE, world.getBlockState(pos0).getValue(BlockStairs.SHAPE)));
						flag = true;
					} else if (block instanceof BlockBush) {
						world.setBlockState(pos0, Blocks.DEADBUSH.getDefaultState());
						flag = true;
					}
				}
				if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
					world.setBlockToAir(pos0);
					flag = true;
				}
			}
		}
		return flag;
	}
}