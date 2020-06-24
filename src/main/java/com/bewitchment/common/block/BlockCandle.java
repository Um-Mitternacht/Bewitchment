package com.bewitchment.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
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
public class BlockCandle extends BlockCandleBase {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.38, 0, 0.38, 0.62, 0.5, 0.62);

    public BlockCandle(String name) {
        super(name, Material.CLOTH, SoundType.CLOTH, 1, 1, "", 0);
        Blocks.FIRE.setFireInfo(this, 0, 0);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOX;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.getBlockState(pos.down()).getBlock().canPlaceTorchOnTop(world.getBlockState(pos.down()), world, pos))
            world.destroyBlock(pos, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT))
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, 0, 0, 0);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
        world.scheduleUpdate(to, this, 0);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock().canPlaceTorchOnTop(world.getBlockState(pos.down()), world, pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        if (state.getValue(LIT)) {
            world.setBlockState(pos, getDefaultState().withProperty(LIT, false));
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f, false);
            return true;
        } else {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == Items.FLINT_AND_STEEL) {
                stack.damageItem(1, player);
                world.setBlockState(pos, getDefaultState().withProperty(LIT, true));
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, world.rand.nextFloat() * 0.4f + 0.8f, false);
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }

    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(LIT) ? 9 : 0;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }
}