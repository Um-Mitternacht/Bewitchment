package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionArachnophobia extends ModPotion {
    public PotionArachnophobia() {
        super("arachnophobia", true, 0xc0c0c0);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (!living.world.isRemote) {
            for (int i = 0; i < 2 * (amplifier + 1); i++) {
                EntitySpider entity = living.world.rand.nextBoolean() ? new EntitySpider(living.world) : new EntityCaveSpider(living.world);
                entity.onInitialSpawn(living.world.getDifficultyForLocation(living.getPosition()), null);
                boolean valid = false;
                for (int j = 0; j < 16; j++) {
                    if (entity.attemptTeleport(living.posX + (living.world.rand.nextInt(24) - 12), living.posY, living.posZ + (living.world.rand.nextInt(24) - 12))) {
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (20 * 120), 2));
                    entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (20 * 120), 1));
                    entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, (20 * 120)));
                    entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, (20 * 120)));
                    entity.setAttackTarget(living);
                    living.world.spawnEntity(entity);
                }
            }
        }
    }

    @Override
    public boolean onImpact(World world, BlockPos pos, int amplifier) {
        boolean flag = false;
        int radius = amplifier + 1;
        for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
            if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
                if (world.getBlockState(pos0).getBlock().isReplaceable(world, pos0)) {
                    world.setBlockState(pos0, Blocks.WEB.getDefaultState());
                    flag = true;
                }
            }
        }
        return flag;
    }
}