package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.*;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joseph on 4/14/2018.
 */
public class PotionFireWorld extends BrewMod {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	@SuppressWarnings("deprecation")
	public PotionFireWorld() {
		super("fireworld", true, 0xED2939, true, 0);
		stateMap.put(Blocks.GRASS_PATH, Blocks.RED_NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.GRAVEL, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.COBBLESTONE, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.PLANKS, Blocks.NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.OAK_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.SPRUCE_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.ACACIA_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.JUNGLE_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.BIRCH_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.DARK_OAK_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.PACKED_ICE, Blocks.MAGMA.getDefaultState());
		stateMap.put(Blocks.ICE, Blocks.MAGMA.getDefaultState());
		stateMap.put(ModBlocks.fake_ice, Blocks.MAGMA.getDefaultState());
		stateMap.put(Blocks.WOOL, Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
		stateMap.put(Blocks.FARMLAND, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.GRASS, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.IRON_BLOCK, ModBlocks.nethersteel.getDefaultState());
		stateMap.put(Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.RED));
		stateMap.put(Blocks.STAINED_GLASS_PANE, Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.RED));
		stateMap.put(Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
		stateMap.put(Blocks.STAINED_HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
		stateMap.put(Blocks.WATER, Blocks.LAVA.getDefaultState());
		stateMap.put(Blocks.GLASS, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED));
		stateMap.put(Blocks.STAINED_GLASS, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED));
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		entity.setFire((1 + amplifier) * 2);
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
				if (world.rand.nextInt(4) <= modifiers.getLevel(DefaultModifiers.POWER).orElse(0) / 2) {
					if (BlockStairs.isBlockStairs(state)) {
						IBlockState newState = Blocks.NETHER_BRICK_STAIRS.getDefaultState()
								.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING))
								.withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF));
						world.setBlockState(spot, newState);
					} else if (state.getBlock() == Blocks.BRICK_BLOCK) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.STONEBRICK) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.END_BRICKS) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.embittered_bricks) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.fake_ice_fence) {
						world.setBlockState(spot, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 3);
					} else if (state.getBlock() instanceof BlockLog) {
						EnumFacing.Axis bone_axis = EnumFacing.Axis.Y;
						BlockLog.EnumAxis log_axis = state.getValue(BlockLog.LOG_AXIS);
						if (log_axis == EnumAxis.X) {
							bone_axis = Axis.X;
						} else if (log_axis == EnumAxis.Z) {
							bone_axis = Axis.Z;
						}
						world.setBlockState(spot, Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockBone.AXIS, bone_axis), 3);
					} else if (stateMap.containsKey(state.getBlock())) {
						world.setBlockState(spot, stateMap.get(state.getBlock()), 3);
					} else if (state.getMaterial() == Material.SNOW) {
						world.setBlockToAir(spot);
					} else if (state.getBlock() == Blocks.WATER) {
						world.setBlockState(spot, Blocks.LAVA.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.FLOWING_WATER) {
						world.setBlockState(spot, Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, state.getValue(BlockDynamicLiquid.LEVEL)), 2);
					} else if (state.getBlock() instanceof BlockLeaves) {
						world.setBlockState(spot, Blocks.NETHER_WART_BLOCK.getDefaultState(), 3);
					} else if (state.getBlock() instanceof BlockGlazedTerracotta) {
						world.setBlockState(spot, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState().withProperty(BlockGlazedTerracotta.FACING, state.getValue(BlockGlazedTerracotta.FACING)), 3);
					}

					if (state.getBlock() != Blocks.NETHERRACK && world.getBlockState(spot).getBlock() == Blocks.NETHERRACK && world.isAirBlock(spot.up()) && world.rand.nextInt(10) == 0) {
						world.setBlockState(spot.up(), Blocks.FIRE.getDefaultState(), 3);
					}

				}
			}
		}
	}
}