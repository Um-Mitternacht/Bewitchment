package com.bewitchment.common.potion;

import com.bewitchment.Util;
import com.bewitchment.common.potion.util.ModPotion;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionSporeCloud extends ModPotion {
    public PotionSporeCloud() {
        super("spore_cloud", true, 0x7f3f7f);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (living instanceof EntityCow && !(living instanceof EntityMooshroom))
            Util.convertEntity((EntityLiving) living, new EntityMooshroom(living.world));
    }

    @Override
    public boolean onImpact(World world, BlockPos pos, int amplifier) {
        boolean flag = false;
        int radius = 3 * (amplifier + 1);
        for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
            if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
                if (world.rand.nextInt(3) == 0) {
                    Block block = world.getBlockState(pos0).getBlock();
                    if (block == Blocks.COBBLESTONE) {
                        world.setBlockState(pos0, Blocks.MOSSY_COBBLESTONE.getDefaultState());
                        flag = true;
                    } else if (block == Blocks.STONEBRICK) {
                        world.setBlockState(pos0, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY));
                        flag = true;
                    } else if (block == ModObjects.scorned_bricks[0]) {
                        world.setBlockState(pos0, ModObjects.mossy_scorned_bricks.getDefaultState());
                        flag = true;
                    } else if (block == ModObjects.scorned_brick_fence) {
                        world.setBlockState(pos0, ModObjects.mossy_scorned_brick_fence.getDefaultState());
                        flag = true;
                    } else if (block == ModObjects.scorned_brick_stairs) {
                        world.setBlockState(pos0, ModObjects.mossy_scorned_brick_stairs.getDefaultState());
                        flag = true;
                    } else if (block == ModObjects.scorned_brick_wall) {
                        world.setBlockState(pos0, ModObjects.mossy_scorned_brick_wall.getDefaultState());
                        flag = true;
                    } else if (block == ModObjects.scorned_bricks_slab.double_slab) {
                        world.setBlockState(pos0, ModObjects.mossy_scorned_brick_slab.double_slab.getDefaultState());
                        flag = true;
                    } else if (block == ModObjects.scorned_bricks_slab) {
                        world.setBlockState(pos0, ModObjects.mossy_scorned_brick_slab.getDefaultState().withProperty(BlockSlab.HALF, world.getBlockState(pos0).getValue(BlockSlab.HALF)));
                        flag = true;
                    } else if (block instanceof BlockTallGrass) {
                        world.setBlockState(pos0, Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
                        flag = true;
                    } else if (block instanceof BlockFlower) {
                        world.setBlockState(pos0, world.rand.nextBoolean() ? Blocks.BROWN_MUSHROOM.getDefaultState() : Blocks.RED_MUSHROOM.getDefaultState());
                        flag = true;
                    } else if (block instanceof BlockDirt) {
                        world.setBlockState(pos0, Blocks.MYCELIUM.getDefaultState());
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }
}