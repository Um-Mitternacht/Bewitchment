package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ModBlockPillar extends BlockRotatedPillar {
    public ModBlockPillar(String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level, String... oreDictionaryNames) {
        super(mat);
        Util.registerBlock(this, name, mat, sound, hardness, resistance, tool, level, oreDictionaryNames);
        setDefaultState(getBlockState().getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
    }

    public ModBlockPillar(String name, Block base, String... oreDictionaryNames) {
        super(base.getDefaultState().getMaterial());
        Util.registerBlock(this, name, base, oreDictionaryNames);
        setDefaultState(getBlockState().getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return !Util.isTransparent(state) && super.isFullCube(state);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return (!Util.isTransparent(state) || world.getBlockState(pos.offset(face)).getBlock() != this) && super.shouldSideBeRendered(state, world, pos, face);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !Util.isTransparent(state) && super.isOpaqueCube(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getMaterial() == Material.WOOD;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial() == Material.WOOD;
    }
}