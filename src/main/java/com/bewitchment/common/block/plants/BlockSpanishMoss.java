package com.bewitchment.common.block.plants;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"NullableProblems", "WeakerAccess"})
public class BlockSpanishMoss extends BlockVine {

	boolean terminalPiece;

	public BlockSpanishMoss(boolean terminal) {
		Util.registerBlock(this, "spanish_moss" + (terminal ? "_end" : ""), Blocks.VINE);
		this.setTickRandomly(!terminalPiece);
		this.terminalPiece = terminal;
	}

	public boolean isTerminalPiece() {
		return terminalPiece;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && !terminalPiece && worldIn.isAirBlock(pos.down())) {
			IBlockState newState = (rand.nextInt(3) == 0 ? ModObjects.spanish_moss_end : ModObjects.spanish_moss).getDefaultState();
			newState = newState.withProperty(UP, state.getValue(UP));
			newState = newState.withProperty(NORTH, state.getValue(NORTH));
			newState = newState.withProperty(SOUTH, state.getValue(SOUTH));
			newState = newState.withProperty(EAST, state.getValue(EAST));
			newState = newState.withProperty(WEST, state.getValue(WEST));
			worldIn.setBlockState(pos.down(), newState, 3);
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		if (!worldIn.isRemote && stack.getItem() == ModObjects.boline && !terminalPiece) {
			player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
			spawnAsEntity(worldIn, pos, new ItemStack(ModObjects.spanish_moss, 1, 0));
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {

			IBlockState newState = state;
			boolean foundValidAttachment = false, changed = false;
			IBlockState upper = worldIn.getBlockState(pos.up());
			for (EnumFacing f : EnumFacing.HORIZONTALS) {
				PropertyBool visualSide = getPropertyFor(f);
				if (canAttachTo(worldIn, pos.offset(f), f.getOpposite()) || (upper.getBlock() == ModObjects.spanish_moss && upper.getValue(visualSide) && state.getValue(visualSide))) {
					foundValidAttachment = true;
					if (!newState.getValue(visualSide)) {
						newState = newState.withProperty(visualSide, true);
						changed = true;
					}
				}
				else {
					if (newState.getValue(visualSide)) {
						newState = newState.withProperty(visualSide, false);
						changed = true;
					}
				}
			}
			if (canAttachTo(worldIn, pos.up(), EnumFacing.DOWN)) {
				foundValidAttachment = true;
			}
			else {
				if (newState.getValue(UP)) {
					newState = newState.withProperty(UP, false);
					changed = true;
				}
			}
			if (!foundValidAttachment) {
				worldIn.setBlockToAir(pos);
			}
			else if (changed) {
				worldIn.setBlockState(pos, newState, 3);
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {

		if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
			for (EnumFacing f : EnumFacing.HORIZONTALS) {
				if (canAttachTo(worldIn, pos.offset(f), f.getOpposite())) {
					return true;
				}
			}
		}
		if (side == EnumFacing.DOWN) {
			return worldIn.getBlockState(pos.up()).getBlock() == ModObjects.spanish_moss;
		}

		return this.canAttachTo(worldIn, pos.offset(side.getOpposite()), side);
	}

	@Override
	public boolean canAttachTo(World world, BlockPos blockToAttachTo, EnumFacing comingFrom) {
		if (world.getBlockState(blockToAttachTo).getBlockFaceShape(world, blockToAttachTo, comingFrom) != BlockFaceShape.SOLID) {
			return false;
		}
		return !isExceptBlockForAttaching(world.getBlockState(blockToAttachTo).getBlock());
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = ModObjects.spanish_moss.getDefaultState();
		if (worldIn.getBlockState(pos.up()).getBlockFaceShape(worldIn, pos.up(), EnumFacing.DOWN) == BlockFaceShape.SOLID) {
			state = state.withProperty(UP, true);
		}
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			PropertyBool side = getPropertyFor(f);
			if (canAttachTo(worldIn, pos.offset(f), f.getOpposite()) || (worldIn.getBlockState(pos.up()).getBlock() == ModObjects.spanish_moss && worldIn.getBlockState(pos.up()).getValue(side))) {
				state = state.withProperty(side, true);
			}
		}
		return state;
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
		return false;
	}
}