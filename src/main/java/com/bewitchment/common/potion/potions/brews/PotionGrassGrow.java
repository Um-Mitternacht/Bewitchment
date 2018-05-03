package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.potion.BrewMod;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class PotionGrassGrow extends BrewMod {
	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	public PotionGrassGrow() {
		super("grass_grow", false, 0xD2691E, true, 0);
		stateMap.put(Blocks.MYCELIUM, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.RED_MUSHROOM, Blocks.TALLGRASS.getDefaultState());
		stateMap.put(Blocks.DEADBUSH, Blocks.YELLOW_FLOWER.getDefaultState());
		stateMap.put(Blocks.SAND, Blocks.DIRT.getDefaultState());
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);
		int box = 2 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			}
		}
	}
}