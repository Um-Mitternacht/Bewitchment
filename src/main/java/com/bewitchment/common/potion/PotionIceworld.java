package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionIceworld extends ModPotion {
	public PotionIceworld() {
		super("iceworld", true, 0x10dcdc);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (!(living instanceof EntityPolarBear)) {
			living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (20 * 15), 2));
			living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (20 * 15), 2));
		}
		living.extinguish();
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
					if (block instanceof BlockStone || block instanceof BlockNetherrack) {
						world.setBlockState(pos0, ModObjects.perpetual_ice.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.WATER) {
						world.setBlockState(pos0, Blocks.ICE.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockIce || block instanceof BlockLog) {
						world.setBlockState(pos0, Blocks.PACKED_ICE.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockStoneBrick || block instanceof BlockNetherBrick || block == ModObjects.scorned_bricks[0]) {
						world.setBlockState(pos0, ModObjects.embittered_bricks.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.WOOL) {
						world.setBlockState(pos0, Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.LIGHT_BLUE));
						flag = true;
					}
					else if (block instanceof BlockFence) {
						world.setBlockState(pos0, ModObjects.perpetual_ice_fence.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockWall) {
						world.setBlockState(pos0, ModObjects.embittered_brick_wall.getDefaultState());
						flag = true;
					}
					else if (block == ModObjects.mossy_scorned_bricks) {
						world.setBlockState(pos0, ModObjects.crying_scorned_bricks.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockLeaves) {
						world.setBlockState(pos0, ModObjects.perpetual_ice.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.DOUBLE_STONE_SLAB || block == Blocks.DOUBLE_STONE_SLAB2 || block == ModObjects.scorned_bricks_slab.double_slab) {
						world.setBlockState(pos0, ModObjects.embittered_bricks_slab.double_slab.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.STONE_SLAB || block == Blocks.STONE_SLAB2 || block == ModObjects.scorned_bricks_slab) {
						world.setBlockState(pos0, ModObjects.embittered_bricks_slab.getDefaultState().withProperty(BlockSlab.HALF, world.getBlockState(pos0).getValue(BlockSlab.HALF)));
						flag = true;
					}
					else if (block == Blocks.STONE_BRICK_STAIRS || block == ModObjects.scorned_brick_stairs) {
						world.setBlockState(pos0, ModObjects.embittered_brick_stairs.getDefaultState().withProperty(BlockStairs.HALF, world.getBlockState(pos0).getValue(BlockStairs.HALF)).withProperty(BlockStairs.FACING, world.getBlockState(pos0).getValue(BlockStairs.FACING)).withProperty(BlockStairs.SHAPE, world.getBlockState(pos0).getValue(BlockStairs.SHAPE)));
						flag = true;
					}
				}
				if (world.getBlockState(pos0).getBlock() instanceof BlockFire) {
					world.setBlockToAir(pos0);
					flag = true;
				}
				if (world.rand.nextInt(5) == 0 && !world.isAirBlock(pos0) && world.getBlockState(pos0).isFullBlock() && world.isAirBlock(pos0.up())) {
					world.setBlockState(pos0.up(), Blocks.SNOW_LAYER.getDefaultState());
					flag = true;
				}
			}
		}
		return flag;
	}
}