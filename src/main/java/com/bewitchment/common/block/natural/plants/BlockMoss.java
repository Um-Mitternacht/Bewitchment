package com.bewitchment.common.block.natural.plants;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

// FIXME placement (try and place it under a dangling piece while not connected laterally to any block)
public class BlockMoss extends BlockVine implements IModelRegister {

	boolean terminalPiece;

	public BlockMoss(boolean terminal) {
		String name = LibBlockName.SPANISH_MOSS + (terminal ? "_end" : "");
		this.setRegistryName(LibMod.MOD_ID, name);
		this.setTranslationKey(LibBlockName.SPANISH_MOSS);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(!terminalPiece);
		if (terminal) {
			this.setCreativeTab(null);
		} else {
			this.setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		}
		this.terminalPiece = terminal;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && !terminalPiece && worldIn.isAirBlock(pos.down())) {
			IBlockState newState = (rand.nextInt(3) == 0 ? ModBlocks.spanish_moss_end : ModBlocks.spanish_moss).getDefaultState();
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
		if (!worldIn.isRemote && stack.getItem() == ModItems.boline && !terminalPiece) {
			player.addStat(StatList.getBlockStats(this));
			spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.spanish_moss, 1, 0));
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
				if (canAttachTo(worldIn, pos.offset(f), f.getOpposite()) || (upper.getBlock() == ModBlocks.spanish_moss && upper.getValue(visualSide) && state.getValue(visualSide))) {
					foundValidAttachment = true;
					if (!newState.getValue(visualSide)) {
						newState = newState.withProperty(visualSide, true);
						changed = true;
					}
				} else {
					if (newState.getValue(visualSide)) {
						newState = newState.withProperty(visualSide, false);
						changed = true;
					}
				}
			}
			if (canAttachTo(worldIn, pos.up(), EnumFacing.DOWN)) {
				foundValidAttachment = true;
			} else {
				if (newState.getValue(UP)) {
					newState = newState.withProperty(UP, false);
					changed = true;
				}
			}
			if (!foundValidAttachment) {
				worldIn.setBlockToAir(pos);
			} else if (changed) {
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
			return worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.spanish_moss;
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
		IBlockState state = ModBlocks.spanish_moss.getDefaultState();
		if (worldIn.getBlockState(pos.up()).getBlockFaceShape(worldIn, pos.up(), EnumFacing.DOWN) == BlockFaceShape.SOLID) {
			state = state.withProperty(UP, true);
		}
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			PropertyBool side = getPropertyFor(f);
			if (canAttachTo(worldIn, pos.offset(f), f.getOpposite()) || (worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.spanish_moss && worldIn.getBlockState(pos.up()).getValue(side))) {
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
