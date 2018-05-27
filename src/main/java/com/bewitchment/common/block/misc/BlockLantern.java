package com.bewitchment.common.block.misc;

import java.util.Random;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLantern extends BlockMod {
	
	private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.2, 0, 0.2, 0.8, 0.9375, 0.8);
	
	private boolean lit;
	
	public BlockLantern(boolean lit) {
		super(lit ? LibBlockName.LANTERN : LibBlockName.REVEALING_LANTERN, Material.IRON);
		this.setHarvestLevel("pickaxe", 0);
		this.setLightOpacity(0);
		this.setLightLevel(lit ? 15 : 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(Bewitchment.COLOR, EnumDyeColor.WHITE));
		this.lit = lit;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return bounding_box;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (lit) {
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, 0, 0, 0);
		}
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, Bewitchment.COLOR);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(Bewitchment.COLOR, EnumDyeColor.values()[meta]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(Bewitchment.COLOR).ordinal();
	}
	
	@Override
	public void registerModel() {
		if (!lit) {
			super.registerModel();
		}
	}
}
