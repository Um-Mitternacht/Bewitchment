package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class CursePalorPestilence extends Curse {
    public CursePalorPestilence() {
        super(new ResourceLocation(Bewitchment.MODID, "palor_pestilence"), Arrays.asList(Util.get(ModObjects.aconitum), Util.get(ModObjects.hellebore), Util.get(ModObjects.snake_venom), Util.get(ModObjects.belladonna), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST, 0.0004);
    }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        if (!target.world.isRemote) {
            target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 600));
            target.addPotionEffect(new PotionEffect(MobEffects.POISON, 600));
            return true;
        }
        return false;
    }
}
