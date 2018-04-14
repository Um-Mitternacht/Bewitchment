package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.api.cauldron.modifiers.BewitchmentModifiers;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.potion.BrewMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		stateMap.put(Blocks.PACKED_ICE, Blocks.OBSIDIAN.getDefaultState());
		stateMap.put(Blocks.ICE, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.SNOW, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.WOOL, Blocks.WOOL.getStateFromMeta(14));
		stateMap.put(Blocks.FARMLAND, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE.getStateFromMeta(14));
		stateMap.put(Blocks.STAINED_GLASS_PANE, Blocks.STAINED_GLASS_PANE.getStateFromMeta(14));
		stateMap.put(Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(14));
		stateMap.put(Blocks.STAINED_HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(14));
		stateMap.put(Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.LIME_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.PINK_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.SILVER_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
		stateMap.put(Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState());
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(BewitchmentModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			if (spot.distanceSq(pos) < 2 + box * box / 2) {
				IBlockState state = world.getBlockState(spot);
				if (world.rand.nextInt(4) <= modifiers.getLevel(BewitchmentModifiers.POWER).orElse(0) / 2) {
					if (BlockStairs.isBlockStairs(state)) {
						IBlockState newState = Blocks.NETHER_BRICK_STAIRS.getDefaultState()
								.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING))
								.withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF));
						world.setBlockState(spot, newState);
					} else if (state.getBlock() == Blocks.LOG) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.LOG2) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
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
					} else if (state.getBlock() == ModBlocks.log_cypress) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_elder) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_yew) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_juniper) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					}
				}
			}
		}
	}
}