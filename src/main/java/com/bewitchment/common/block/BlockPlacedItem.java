package com.bewitchment.common.block;

import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions"})
public class BlockPlacedItem extends ModBlockContainer {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 0.0625, 0.75);

    public BlockPlacedItem() {
        super(null, "placed_item", Material.ROCK, SoundType.CLOTH, 0, 0, "", -1);
        setCreativeTab(null);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityPlacedItem();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOX;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.getBlockState(pos.down()).getBlockFaceShape(world, pos, EnumFacing.UP) != BlockFaceShape.SOLID) {
            breakBlock(world, pos, state);
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
        world.scheduleUpdate(to, this, 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && world.getBlockState(pos.down()).getBlockFaceShape(world, pos, EnumFacing.UP) == BlockFaceShape.SOLID;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockHorizontal.FACING);
    }

    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return ((TileEntityPlacedItem) world.getTileEntity(pos)).getInventories()[0].getStackInSlot(0);
    }
}