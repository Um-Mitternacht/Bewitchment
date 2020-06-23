package com.bewitchment.common.potion;

import com.bewitchment.Util;
import com.bewitchment.common.entity.living.EntitySnake;
import com.bewitchment.common.entity.spirit.demon.EntityFeuerwurm;
import com.bewitchment.common.entity.spirit.demon.EntityHellhound;
import com.bewitchment.common.potion.util.ModPotion;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings({"unused"})
public class PotionHellworld extends ModPotion {
	public PotionHellworld() {
		super("hellworld", true, 0xd20202);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (living instanceof EntityWolf) Util.convertEntity((EntityLiving) living, new EntityHellhound(living.world));
		if (living instanceof EntitySnake) Util.convertEntity((EntityLiving) living, new EntityFeuerwurm(living.world));
		living.setFire(15);
	}
	
	@Override
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		boolean flag = false;
		int radius = 3 * (amplifier + 1);
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
			if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
				if (world.rand.nextInt(3) == 0) {
					Block block = world.getBlockState(pos0).getBlock();
					if (block instanceof BlockStone || block == ModObjects.perpetual_ice) {
						world.setBlockState(pos0, Blocks.NETHERRACK.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockGrass || block instanceof BlockDirt || block instanceof BlockSand) {
						world.setBlockState(pos0, Blocks.SOUL_SAND.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.BRICK_BLOCK) {
						world.setBlockState(pos0, ModObjects.scorned_bricks[0].getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockStoneBrick) {
						world.setBlockState(pos0, Blocks.NETHER_BRICK.getDefaultState());
					}
					else if (block == Blocks.WOOL) {
						world.setBlockState(pos0, Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
						flag = true;
					}
					else if (block == ModObjects.embittered_bricks) {
						world.setBlockState(pos0, ModObjects.scorned_bricks[0].getDefaultState());
						flag = true;
					}
					else if (block == Blocks.GRASS_PATH) {
						world.setBlockState(pos0, Blocks.RED_NETHER_BRICK.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockLog) {
						world.setBlockState(pos0, Blocks.BONE_BLOCK.getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.despair_bricks) {
						world.setBlockState(pos0, ModObjects.ochre_despair_bricks.getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.despair_brick_stairs) {
						world.setBlockState(pos0, ModObjects.ochre_despair_brick_stairs.getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.mossy_scorned_bricks) {
						world.setBlockState(pos0, ModObjects.scorned_bricks[0].getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.crying_scorned_bricks) {
						world.setBlockState(pos0, ModObjects.scorned_bricks[0].getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.despair_brick_wall) {
						world.setBlockState(pos0, ModObjects.ochre_despair_brick_wall.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.COBBLESTONE_WALL) {
						world.setBlockState(pos0, ModObjects.scorned_brick_wall.getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.embittered_brick_fence) {
						world.setBlockState(pos0, ModObjects.scorned_brick_fence.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockLeaves) {
						world.setBlockState(pos0, Blocks.NETHER_WART_BLOCK.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.BRICK_STAIRS) {
						world.setBlockState(pos0, Blocks.NETHER_BRICK_STAIRS.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockFence) {
						world.setBlockState(pos0, Blocks.NETHER_BRICK_FENCE.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.DOUBLE_STONE_SLAB || block == Blocks.DOUBLE_STONE_SLAB2 || block == ModObjects.embittered_bricks_slab.double_slab) {
						world.setBlockState(pos0, Blocks.DOUBLE_STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK));
						flag = true;
					}
					else if (block == Blocks.STONE_SLAB || block == Blocks.STONE_SLAB2 || block == ModObjects.embittered_bricks_slab) {
						world.setBlockState(pos0, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK).withProperty(BlockSlab.HALF, world.getBlockState(pos0).getValue(BlockSlab.HALF)));
						flag = true;
					}
					else if (block == Blocks.STONE_BRICK_STAIRS || block == ModObjects.embittered_brick_stairs) {
						world.setBlockState(pos0, Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, world.getBlockState(pos0).getValue(BlockStairs.HALF)).withProperty(BlockStairs.FACING, world.getBlockState(pos0).getValue(BlockStairs.FACING)).withProperty(BlockStairs.SHAPE, world.getBlockState(pos0).getValue(BlockStairs.SHAPE)));
						flag = true;
					}
					else if (!world.isAirBlock(pos0)) {
						ItemStack stack = new ItemStack(block);
						if (!stack.isEmpty()) {
							for (int i : OreDictionary.getOreIDs(stack)) {
								if (OreDictionary.getOreName(i).startsWith("ore")) {
									world.setBlockState(pos0, Blocks.QUARTZ_ORE.getDefaultState());
									flag = true;
									break;
								}
							}
						}
					}
				}
				if (world.rand.nextInt(5) == 0 && world.isAirBlock(pos0.up())) {
					world.setBlockState(pos0.up(), Blocks.FIRE.getDefaultState());
					flag = true;
				}
			}
		}
		return flag;
	}
}