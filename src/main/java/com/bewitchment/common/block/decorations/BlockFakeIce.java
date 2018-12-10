package com.bewitchment.common.block.decorations;

import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("deprecation")
public class BlockFakeIce extends BlockMod {

	public BlockFakeIce() {
		super(LibBlockName.FAKE_ICE, Material.ICE);
		setSoundType(SoundType.STONE);
		setResistance(2F);
		setHardness(2F);
		setDefaultSlipperiness(0.98F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		IBlockState sideState = world.getBlockState(pos.offset(side));
		Block block = sideState.getBlock();
		return block != this && super.shouldSideBeRendered(state, world, pos, side);
	}
}
