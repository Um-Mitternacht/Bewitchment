package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FortuneCatsAndDogs extends Fortune {
    public FortuneCatsAndDogs() {
        super(new ResourceLocation(Bewitchment.MODID, "cats_and_dogs"), true, (60 * 5), (60 * 15));
    }

    @Override
    public boolean apply(EntityPlayer player) {
        if (!player.world.isRemote) {
            World world = player.world;
            world.getWorldInfo().setCleanWeatherTime(0);
            world.getWorldInfo().setRainTime(2000);
            world.getWorldInfo().setThunderTime(3000);
            world.getWorldInfo().setRaining(true);
            world.getWorldInfo().setThundering(true);
            BlockPos pos = player.getPosition();
            for (int i = 0; i < world.rand.nextInt(8) + 3; i++) {
                EntityLiving entity = world.rand.nextBoolean() ? new EntityWolf(world) : new EntityOcelot(world);
                entity.onInitialSpawn(world.getDifficultyForLocation(pos), null);
                boolean valid = false;
                for (int j = 0; j < 16; j++) {
                    if (entity.attemptTeleport(pos.getX() + (world.rand.nextInt(12) - 6), pos.getY(), pos.getZ() + (world.rand.nextInt(12) - 6))) {
                        entity.setLocationAndAngles(entity.posX, entity.posY + 24 + world.rand.nextInt(32), entity.posZ, world.rand.nextInt(360), 0);
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    world.spawnEntity(entity);
                    entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 9));
                    entity.addPotionEffect(new PotionEffect(ModPotions.wednesday, (20 * 3)));
                }
            }
        }
        return true;
    }
}