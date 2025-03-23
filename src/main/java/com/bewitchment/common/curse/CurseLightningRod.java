package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class CurseLightningRod extends Curse {
    public CurseLightningRod() {
        super(new ResourceLocation(Bewitchment.MODID, "lightning_rod"), Arrays.asList(Util.get("nuggetIron"), Util.get("nuggetGold"), Util.get("dustRedstone"), Util.get(ModObjects.oil_of_vitriol), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST, 0.002);
    }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        if (target.world.isRainingAt(target.getPosition()) && target.isWet()) {
            target.world.addWeatherEffect(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, true));
            target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 3f);
            return true;
        }
        return false;
    }
}
