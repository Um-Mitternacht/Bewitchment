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

    private boolean terminalPiece;

    public BlockSpanishMoss(boolean terminal) {
        this.setTickRandomly(!terminalPiece);
        this.terminalPiece = terminal;
        Util.registerBlock(this, "spanish_moss" + (terminal ? "_end" : ""), Blocks.VINE);
    }

    public boolean isTerminalPiece() {
        return terminalPiece;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote && worldIn.rand.nextInt(4) == 0 && worldIn.isAreaLoaded(pos, 4)) {
            int j = 5;
            boolean flag = false;
            for (int k = -4; k <= 4; ++k) {
                for (int l = -4; l <= 4; ++l) {
                    for (int i1 = -1; i1 <= 1; ++i1) {
                        if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this) {
                            --j;
                            if (j <= 0) {
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            }

            EnumFacing enumfacing1 = EnumFacing.random(rand);
            BlockPos blockpos2 = pos.up();
            if (enumfacing1 == EnumFacing.UP && pos.getY() < 255 && worldIn.isAirBlock(blockpos2)) {
                IBlockState iblockstate2 = state;
                for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                    if (rand.nextBoolean() && this.canAttachTo(worldIn, blockpos2, facing.getOpposite())) {
                        iblockstate2 = iblockstate2.withProperty(getPropertyFor(facing), true);
                    } else {
                        iblockstate2 = iblockstate2.withProperty(getPropertyFor(facing), false);
                    }
                }
                if (iblockstate2.getValue(NORTH) || iblockstate2.getValue(EAST) || iblockstate2.getValue(SOUTH) || iblockstate2.getValue(WEST)) {
                    worldIn.setBlockState(blockpos2, iblockstate2, 2);
                }
            } else {
                IBlockState iblockstate;
                Block block;
                BlockPos blockpos3;
                if (enumfacing1.getAxis().isHorizontal() && !state.getValue(getPropertyFor(enumfacing1))) {
                    if (!flag && !terminalPiece) {
                        blockpos3 = pos.offset(enumfacing1);
                        iblockstate = worldIn.getBlockState(blockpos3);
                        block = iblockstate.getBlock();
                        if (block.isAir(iblockstate, worldIn, blockpos3)) {
                            EnumFacing enumfacing3 = enumfacing1.rotateY();
                            EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
                            boolean flag1 = state.getValue(getPropertyFor(enumfacing3));
                            boolean flag2 = state.getValue(getPropertyFor(enumfacing4));
                            BlockPos blockpos = blockpos3.offset(enumfacing3);
                            BlockPos blockpos1 = blockpos3.offset(enumfacing4);
                            if (flag1 && this.canAttachTo(worldIn, blockpos.offset(enumfacing3), enumfacing3)) {
                                worldIn.setBlockState(blockpos3, this.getDefaultState().withProperty(getPropertyFor(enumfacing3), true), 2);
                            } else if (flag2 && this.canAttachTo(worldIn, blockpos1.offset(enumfacing4), enumfacing4)) {
                                worldIn.setBlockState(blockpos3, this.getDefaultState().withProperty(getPropertyFor(enumfacing4), true), 2);
                            } else if (flag1 && worldIn.isAirBlock(blockpos) && this.canAttachTo(worldIn, blockpos, enumfacing1)) {
                                worldIn.setBlockState(blockpos, this.getDefaultState().withProperty(getPropertyFor(enumfacing1.getOpposite()), true), 2);
                            } else if (flag2 && worldIn.isAirBlock(blockpos1) && this.canAttachTo(worldIn, blockpos1, enumfacing1)) {
                                worldIn.setBlockState(blockpos1, this.getDefaultState().withProperty(getPropertyFor(enumfacing1.getOpposite()), true), 2);
                            }
                        } else if (iblockstate.getBlockFaceShape(worldIn, blockpos3, enumfacing1) == BlockFaceShape.SOLID) {
                            worldIn.setBlockState(pos, state.withProperty(getPropertyFor(enumfacing1), true), 2);
                        }
                    }
                } else if (pos.getY() > 1) {
                    blockpos3 = pos.down();
                    iblockstate = worldIn.getBlockState(blockpos3);
                    block = iblockstate.getBlock();
                    IBlockState iblockstate4;
                    if (block.isAir(iblockstate, worldIn, blockpos3) && !terminalPiece) {
                        iblockstate4 = state;
                        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                            if (rand.nextBoolean()) {
                                iblockstate4 = iblockstate4.withProperty(getPropertyFor(facing), false);
                            }
                        }
                        if (iblockstate4.getValue(NORTH) || iblockstate4.getValue(EAST) || iblockstate4.getValue(SOUTH) || iblockstate4.getValue(WEST)) {
                            if (rand.nextDouble() < 0.25)
                                iblockstate4 = ModObjects.spanish_moss_end.getDefaultState().withProperty(NORTH, iblockstate4.getValue(NORTH)).withProperty(SOUTH, iblockstate4.getValue(SOUTH)).withProperty(EAST, iblockstate4.getValue(EAST)).withProperty(WEST, iblockstate4.getValue(WEST));
                            worldIn.setBlockState(blockpos3, iblockstate4, 2);
                        }
                    } else if (block == this) {
                        iblockstate4 = iblockstate;
                        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                            PropertyBool propertybool = getPropertyFor(enumfacing);
                            if (rand.nextBoolean() && state.getValue(propertybool)) {
                                iblockstate4 = iblockstate4.withProperty(propertybool, true);
                            }
                        }

                        if (iblockstate4.getValue(NORTH) || iblockstate4.getValue(EAST) || iblockstate4.getValue(SOUTH) || iblockstate4.getValue(WEST)) {
                            worldIn.setBlockState(blockpos3, iblockstate4, 2);
                        }
                    }
                }
            }
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
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }
}