package com.bewitchment.common.block;

import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

@SuppressWarnings({"deprecation", "NullableProblems", "WeakerAccess"})
@Optional.Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliserExt", modid = "thaumcraft")
public abstract class BlockCandleBase extends ModBlock implements IInfusionStabiliserExt {
    public static final PropertyBool LIT = PropertyBool.create("lit");

    protected BlockCandleBase(String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level) {
        super(name, mat, sound, hardness, resistance, tool, level);
        setLightOpacity(0);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public abstract int getLightValue(IBlockState state);

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LIT, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LIT) ? 1 : 0;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Optional.Method(modid = "thaumcraft")
    public boolean canStabaliseInfusion(World world, BlockPos pos) {
        return true;
    }

    @Override
    @Optional.Method(modid = "thaumcraft")
    public float getStabilizationAmount(World world, BlockPos pos) {
        return 0.5f;
    }
}