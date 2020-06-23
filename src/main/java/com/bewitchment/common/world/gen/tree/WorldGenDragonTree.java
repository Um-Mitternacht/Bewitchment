package com.bewitchment.common.world.gen.tree;

import com.bewitchment.common.world.gen.tree.util.WorldGenModTree;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.bewitchment.common.block.BlockDragonsBloodLog.SLASHED;
import static net.minecraft.block.BlockLog.LOG_AXIS;

@SuppressWarnings({"NullableProblems", "SuspiciousNameCombination"})
public class WorldGenDragonTree extends WorldGenModTree {
	public WorldGenDragonTree(boolean notify) {
		super(notify);
	}
	
	@Override
	public boolean canSaplingGrow(World world, BlockPos pos) {
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++)
				for (int y = 0; y < 8; y++) {
					BlockPos current = pos.up(1).add(x, y, z);
					if (!world.getBlockState(current).getBlock().canBeReplacedByLeaves(world.getBlockState(current), world, current)) return false;
				}
		}
		return true;
	}
	
	@Override
	public boolean generate(World world, Random random, BlockPos blockPos) {
		int h = generateTrunk(world, ModObjects.dragons_blood_wood.getDefaultState(), blockPos, random, 3, 4);
		if (random.nextDouble() < 0.7) generateBottom(world, blockPos);
		int branch = generateCrown(world, random, blockPos, h);
		generateLeaves(world, blockPos, h, branch);
		return true;
	}
	
	private void generateBottom(World world, BlockPos pos) {
		for (int i = 0; i < 4; i++) {
			BlockPos current = pos.offset(EnumFacing.HORIZONTALS[i]);
			setBlocks(world, current, ModObjects.dragons_blood_wood.getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(SLASHED, false));
		}
	}
	
	private int generateCrown(World world, Random random, BlockPos pos, int height) {
		BlockPos treetop = pos.up(height);
		int ymax = random.nextInt(2) + 3;
		for (int y = 0; y < ymax; y++) {
			for (int i = 0; i < 4; i++) {
				BlockPos current = treetop.offset(EnumFacing.HORIZONTALS[i], y + 1).up(y);
				setBlocks(world, current, ModObjects.dragons_blood_wood.getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(SLASHED, false));
			}
			BlockPos trunk = treetop.up(y);
			setBlocks(world, trunk, ModObjects.dragons_blood_wood.getDefaultState());
			BlockPos diagonal = treetop.add(y, y, y);
			setBlocks(world, diagonal, ModObjects.dragons_blood_wood.getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(SLASHED, false));
			diagonal = treetop.add(y, y, -y);
			setBlocks(world, diagonal, ModObjects.dragons_blood_wood.getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(SLASHED, false));
			diagonal = treetop.add(-y, y, y);
			setBlocks(world, diagonal, ModObjects.dragons_blood_wood.getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(SLASHED, false));
			diagonal = treetop.add(-y, y, -y);
			setBlocks(world, diagonal, ModObjects.dragons_blood_wood.getBlockState().getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(SLASHED, false));
		}
		return ymax;
	}
	
	private void generateLeaves(World world, BlockPos pos, int height, int branch) {
		BlockPos treetop = pos.up(height);
		for (int h = 1; h < branch + 1; h++) {
			BlockPos newtop = new BlockPos(treetop.getX() - h, treetop.getY() + h, treetop.getZ() - h);
			for (int x = 0; x < 2 * h + 1; x++) {
				for (int z = 0; z < 2 * h + 1; z++) {
					BlockPos current = newtop.add(x, 0, z);
					setBlocks(world, current, ModObjects.dragons_blood_leaves.getDefaultState());
				}
			}
		}
		BlockPos toplevel = new BlockPos(treetop.getX() - branch + 1, treetop.getY() + branch + 1, treetop.getZ() - branch + 1);
		for (int x = 0; x < 2 * branch - 1; x++) {
			for (int z = 0; z < 2 * branch - 1; z++) {
				BlockPos current = toplevel.add(x, 0, z);
				setBlocks(world, current, ModObjects.dragons_blood_leaves.getDefaultState());
			}
		}
	}
	
	private void setBlocks(World world, BlockPos pos, IBlockState blockState) {
		if (world.getBlockState(pos).getBlock().canBeReplacedByLeaves(world.getBlockState(pos), world, pos)) {
			world.setBlockState(pos, blockState);
		}
	}
}
