package com.bewitchment.common.block.magic;

import com.bewitchment.api.helper.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.WitchcraftCreativeTabs;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockFakeIceStairs extends BlockStairs implements IModelRegister {

	@SuppressWarnings("deprecation")
	public BlockFakeIceStairs(String unlocalizedName, IBlockState state, Material material) {
		super(state);
		setUnlocalizedName(LibBlockName.FAKE_ICE_STAIRS);
		setRegistryName(new ResourceLocation(LibMod.MOD_ID, unlocalizedName));
		setCreativeTab(WitchcraftCreativeTabs.BLOCKS_CREATIVE_TAB);
		useNeighborBrightness = true;
		setResistance(2F);
		setHardness(2F);
		slipperiness = 0.98F;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		IBlockState sideState = world.getBlockState(pos.offset(side));
		Block block = sideState.getBlock();
		return block != this && super.shouldSideBeRendered(state, world, pos, side);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
