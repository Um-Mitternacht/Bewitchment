package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionPesticide extends ModPotion {
    public PotionPesticide() {
        super("pesticide", true, 0x007f00);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (living.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)
            living.attackEntityFrom(DamageSource.MAGIC, 8 * (amplifier + 1));
    }

    @Override
    public boolean onImpact(World world, BlockPos pos, int amplifier) {
        boolean flag = false;
        int radius = amplifier + 1;
        for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
            if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
                if (world.getBlockState(pos0).getBlock() instanceof BlockWeb) {
                    world.setBlockToAir(pos0);
                    flag = true;
                }
            }
        }
        return flag;
    }
}