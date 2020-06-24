package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ModBlockPressurePlate extends BlockPressurePlate {
	public ModBlockPressurePlate(String name, Block base, String... oreDictionaryNames) {
		super(base.getDefaultState().getMaterial(), Sensitivity.EVERYTHING);
		Util.registerBlock(this, name, base, oreDictionaryNames);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return (!Util.isTransparent(state) || world.getBlockState(pos.offset(face)).getBlock() != this) && super.shouldSideBeRendered(state, world, pos, face);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
	}
}