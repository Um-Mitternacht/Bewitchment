package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesOven;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.common.handler.GuiHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockWitchesOven extends ModBlockContainer {
    public static final PropertyBool LIT = PropertyBool.create("lit");

    private static final AxisAlignedBB BOX = new AxisAlignedBB(1 / 16d, 0, 1 / 16d, 15 / 16d, 1, 15 / 16d);

    public BlockWitchesOven() {
        super(Bewitchment.instance, "witches_oven", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", GuiHandler.ModGui.OVEN.ordinal());
        setDefaultState(blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(LIT, false));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWitchesOven();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta & 7]).withProperty(LIT, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex() | (state.getValue(LIT) ? 1 : 0) << 3;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOX;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT)) {
            EnumFacing facing = state.getValue(BlockHorizontal.FACING);
            double d0 = pos.getX() + 0.425d;
            double d1 = pos.getY() + 0.3 + 0.75 * (rand.nextDouble() * 8 / 16d);
            double d2 = pos.getZ() + 0.425d;
            double d4 = rand.nextDouble() * 0.6d - 0.3d;
            if (rand.nextDouble() < 0.1)
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1, 1, false);
            switch (facing) {
                case WEST:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52d, d1, d2 + d4, 0, 0, 0d);
                    world.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52d, d1, d2 + d4, 0, 0, 0d);
                    break;
                case EAST:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52d, d1, d2 + d4, 0, 0, 0d);
                    world.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52d, d1, d2 + d4, 0, 0, 0d);
                    break;
                case NORTH:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52d, 0, 0, 0d);
                    world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52d, 0, 0, 0d);
                    break;
                case SOUTH:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52d, 0, 0, 0d);
                    world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52d, 0, 0, 0d);
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockHorizontal.FACING, LIT);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 13 : 0;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
    }
}