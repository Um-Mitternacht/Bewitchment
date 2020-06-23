package com.bewitchment.common.block;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockPurifyingEarth extends ModBlock {
    public BlockPurifyingEarth() {
        super("purifying_earth", Blocks.DIRT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        for (int i = 0; i < 16; i++)
            if (rand.nextBoolean())
                world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 1, 1, 1);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase) entity;
            EnumCreatureAttribute att = living.getCreatureAttribute();
            if ((BewitchmentAPI.getSilverWeakness(living) > 1 && !BewitchmentAPI.isWerewolf(living)) || BewitchmentAPI.getColdIronWeakness(living) > 1 && !living.isBurning())
                living.setFire(25);
        }
    }
}