package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Random;

public class CurseParanoia extends Curse {


    //Curse plans
    //Random mob sounds
    //Blindness
    public int timer = 750;

    public CurseParanoia() {
        super(new ResourceLocation(Bewitchment.MODID, "paranoia"), Arrays.asList(Util.get(Items.ENDER_EYE), Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.snake_venom), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST);
    }

    @SubscribeEvent
    public void onPlaySound(SoundEvent event, EntityPlayer target) {
        Random rand = target.getRNG();
        World world = target.getEntityWorld();
        BlockPos pos = target.getPosition();
        if (target.getCapability(ExtendedPlayer.CAPABILITY, null).hasCurse(this)) {
            int i = rand.nextInt(100);
            if (timer > 0) timer--;
            if (i < 10 && timer == 0)
                world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_SCREAM, SoundCategory.HOSTILE, 1, 1);
                timer = 750;
            }
        }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 256, 1));
        return false;
    }
}
