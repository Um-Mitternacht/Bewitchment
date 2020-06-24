package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;
import java.util.Random;

public class CurseParanoia extends Curse {


    //Curse plans
    //Enderman screeching every so often
    //Shadow people can spawn nearby
    //Nausea
    public CurseParanoia() {
        super(new ResourceLocation(Bewitchment.MODID, "paranoia"), Arrays.asList(Util.get(Items.ENDER_EYE), Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.snake_venom), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST);
    }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120, 0));
        Random rand = target.getRNG();
        int i = rand.nextInt(100);
        {
            if (i < 10) {
                target.playSound(SoundEvents.ENTITY_ENDERMEN_SCREAM, 1, 1);
            }
            return false;
        }
    }
}
