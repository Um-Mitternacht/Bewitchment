package com.bewitchment.common.block.plants.util;

import com.bewitchment.Util;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class BlockBushSpreading extends BlockBush {
	public static final PropertyInteger TIMES_SPREAD = PropertyInteger.create("times_spread", 0, 3);
	
	protected BlockBushSpreading(String name, String... oreDictionaryName) {
		super();
		Util.registerBlock(this, name, Material.PLANTS, SoundType.PLANT, 0, 0, "shears", 0, oreDictionaryName);
		setDefaultState(getBlockState().getBaseState().withProperty(TIMES_SPREAD, 0));
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if (rand.nextInt(35) == 0 && state.getValue(TIMES_SPREAD) < TIMES_SPREAD.getAllowedValues().size() - 1) {
			BlockPos pos0 = pos.add(rand.nextInt(9) - 4, rand.nextInt(3) - 2, rand.nextInt(9) - 4);
			if (checkSurrounding(world, pos) && canSustainBush(world.getBlockState(pos0.down())) && world.isAirBlock(pos0)) world.setBlockState(pos0, rand.nextInt(10) == 0 ? world.getBlockState(pos) : world.getBlockState(pos).cycleProperty(TIMES_SPREAD));
		}
	}
	
	private boolean checkSurrounding(World world, BlockPos pos) {
		int count = 0;
		for (BlockPos current : BlockPos.getAllInBoxMutable(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
			if (world.getBlockState(current).getBlock() == this) {
				count++;
			}
		}
		return count < 6;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIMES_SPREAD, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIMES_SPREAD);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TIMES_SPREAD);
	}
}